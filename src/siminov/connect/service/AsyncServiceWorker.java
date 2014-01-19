package siminov.connect.service;

import java.util.Iterator;

import siminov.connect.IWorker;
import siminov.connect.connection.ConnectionHelper;
import siminov.connect.connection.ConnectionRequest;
import siminov.connect.connection.ConnectionResponse;
import siminov.connect.model.ServiceDescriptor;
import siminov.connect.resource.Resources;
import siminov.connect.service.model.ServiceRequest;
import siminov.connect.service.model.ServiceRequestResource;
import siminov.connect.utils.Utils;
import siminov.orm.exception.SiminovCriticalException;
import siminov.orm.exception.SiminovException;
import siminov.orm.log.Log;
import siminov.orm.utils.ClassUtils;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class AsyncServiceWorker implements IServiceWorker, IWorker {

	private static AsyncServiceWorker asyncServiceWorker = null;
	
	private AsyncServiceWorkerThread asyncServiceWorkerThread = null;
	private Resources resources = null;
	
	private ServiceUtils serviceUtils = null;
	
	private ConnectivityChangeReceiver connectivityChangeReceiver = new ConnectivityChangeReceiver();
	
	private AsyncServiceWorker() {
		
		resources = Resources.getInstance();
		serviceUtils = new ServiceUtils();
		
		
		startWorker();
		
		/*
		 * Register Connectivity Change Receiver.
		 */
		siminov.orm.resource.Resources ormResource = siminov.orm.resource.Resources.getInstance();
		Context applicationContext = ormResource.getApplicationContext();
		
		applicationContext.registerReceiver(connectivityChangeReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
	}
	
	
	public static AsyncServiceWorker getInstance() {
		
		if(asyncServiceWorker == null) {
			asyncServiceWorker = new AsyncServiceWorker();
		}
		
		return asyncServiceWorker;
	}
	

	public void process(final IService iService) {
		
		ServiceRequest serviceRequest = serviceUtils.convert(iService);
		boolean contain = false;
		
		try {
			contain = serviceUtils.containService(serviceRequest);
		} catch(SiminovException se) {
			Log.loge(AsyncServiceWorker.class.getName(), "process", "SiminovException caught while checking exsisting service, " + se.getMessage());
			iService.onServiceTerminate(se);
			
			return;
		}
		
		if(contain) {
			return;
		}
			
		
		try {
			serviceRequest.save();
		} catch(SiminovException se) {
			Log.loge(AsyncServiceWorker.class.getName(), "process", "SiminovException caught while saving service, " + se.getMessage());
			iService.onServiceTerminate(se);
			
			return;
		}
		
		
		/*
		 * Notify Async Service Worker Thread.
		 */
		startWorker();
	}

	
	public void startWorker() {
		
		if(asyncServiceWorkerThread == null) {
			asyncServiceWorkerThread = new AsyncServiceWorkerThread();
		}
		
		asyncServiceWorkerThread.start();
	}
	
	public void stopWorker() {
		
		if(asyncServiceWorkerThread == null) {
			return;
		}
		
		
		if(!asyncServiceWorkerThread.isAlive()) {
			asyncServiceWorkerThread.start();
		}
	}
	
	public boolean isWorkerRunning() {
		
		if(asyncServiceWorkerThread == null) {
			return false;
		}
		
		return asyncServiceWorkerThread.isAlive();
	}
	
	
	private class AsyncServiceWorkerThread extends Thread {
		
		public void run() {
		
			ServiceRequest[] serviceRequests = null;
			try {
				serviceRequests = (ServiceRequest[]) new ServiceRequest().select().fetch();
			} catch(SiminovException se) {
				Log.loge(AsyncServiceWorkerThread.class.getName(), "run", "SiminovException caught while getting queue services, " + se.getMessage());
				throw new SiminovCriticalException(AsyncServiceWorkerThread.class.getName(), "run", "SiminovException caught while getting queue services, " + se.getMessage());
			}
			
			if(serviceRequests == null || serviceRequests.length <= 0) {
				return;
			}

			for(int i = 0;i < serviceRequests.length;i++) {
				ServiceRequest serviceRequest = serviceRequests[i];
				IService iService = serviceUtils.convert(serviceRequest);

				/*
				 * Check Network Connectivity.	
				 */
				if(!Utils.hasCoverage()) {
					
					try {
						this.wait();
					} catch(Exception e) {
						Log.loge(AsyncServiceWorkerThread.class.getName(), "run", "Exception caught while putting async service worker thread into wait state, " + e.getMessage());
						return;
					}
				}
				
				handle(iService);
			}
		}
		
		private void handle(final IService iService) {
			
			ConnectionRequest connectionRequest = ConnectionHelper.prepareConnectionRequest(iService);
			
			/*
			 * Service Event onServiceApiInvoke
			 */
			iService.onServiceApiInvoke(connectionRequest);
			ConnectionResponse connectionResponse = null;
			
			try {
				connectionResponse = ServiceHandler.getInstance().invokeConnection(connectionRequest);
			} catch(SiminovException se) {
				Log.loge(SyncServiceWorker.class.getName(), "process", "SiminovException caught while invoking connection, " + se.getMessage());
				
				iService.onServiceTerminate(se);
				return;
			}
			
			iService.onServiceApiFinish(connectionResponse);
		}
	}

	private class ConnectivityChangeReceiver extends BroadcastReceiver {

		public void onReceive(Context context, Intent intent) {
			
			if(intent.getExtras() == null) {
				return;
			}
			
			
			NetworkInfo networkInfo = (NetworkInfo) intent.getExtras().get(ConnectivityManager.EXTRA_NETWORK_INFO);
			if(networkInfo.isConnected()) {
				
				if(asyncServiceWorkerThread.isAlive()) {
					asyncServiceWorkerThread.interrupt();
				}
			} else {
				
				if(asyncServiceWorkerThread.isAlive()) {
					
					try {
						asyncServiceWorkerThread.wait();
					} catch(Exception e) {
						Log.loge(AsyncServiceWorkerThread.class.getName(), "onReceive", "Exception caught while putting async service worker thread into wait state because of no connectivity, " + e.getMessage());	
						return;	
					}
				}
			}
		}
	}

	
	
	private class ServiceUtils {
		
		public boolean containService(ServiceRequest serviceRequest) throws SiminovException {
			
			ServiceRequest[] serviceRequests = (ServiceRequest[]) new ServiceRequest().select().fetch();
			if(serviceRequests == null || serviceRequests.length <= 0) {
				return false;
			}
			
			for(int i = 0;i < serviceRequests.length;i++) {
				
				ServiceRequest savedServiceRequest = serviceRequests[i];
				if(serviceRequest.getService().equalsIgnoreCase(savedServiceRequest.getService())) {
					
					if(serviceRequest.getApi().equalsIgnoreCase(savedServiceRequest.getApi())) {
						
						boolean contain = true;
						
						Iterator<ServiceRequestResource> serviceRequestResources = serviceRequest.getServiceRequestResources();
						while(serviceRequestResources.hasNext()) {
							
							ServiceRequestResource serviceRequestResource = serviceRequestResources.next();
							ServiceRequestResource savedServiceRequestResource = savedServiceRequest.getServiceRequestResource(serviceRequestResource.getName());
							
							if(savedServiceRequestResource == null) {
								contain = false;
								break;
							}
							
							if(!serviceRequestResource.getName().equalsIgnoreCase(savedServiceRequestResource.getName())) {
								contain = false;
								break;
							} else if(!serviceRequestResource.getValue().equalsIgnoreCase(savedServiceRequestResource.getValue())) {
								contain = false;
								break;
							}
						}
						
						if(contain) {
							return true;
						}
					}
				} 
			}
			
			
			return false;
		}
		
		public IService convert(ServiceRequest serviceRequest) {
			
			IService iService = (IService) ClassUtils.createClassInstance(serviceRequest.getInstanceOf());
			iService.setService(serviceRequest.getService());
			iService.setApi(serviceRequest.getApi());
			
			ServiceDescriptor serviceDescriptor = resources.requiredServiceDescriptorBasedOnName(serviceRequest.getService());
			iService.setServiceDescriptor(serviceDescriptor);
			
			Iterator<ServiceRequestResource> serviceRequestResources = serviceRequest.getServiceRequestResources();
			while(serviceRequestResources.hasNext()) {
				ServiceRequestResource serviceResource = serviceRequestResources.next();
				iService.addInlineResource(serviceResource.getName(), serviceResource.getValue());
			}
			
			return iService;
		}
		
		public ServiceRequest convert(final IService iService) {
			
			ServiceRequest serviceRequest = new ServiceRequest();
			serviceRequest.setService(iService.getService());
			serviceRequest.setApi(iService.getApi());
			serviceRequest.setInstanceOf(iService.getClass().getName());
			
			Iterator<String> resources = iService.getInlineResources();
			while(resources.hasNext()) {
				String resource = resources.next();
				
				ServiceRequestResource serviceRequestResource = new ServiceRequestResource();
				serviceRequestResource.setServiceRequest(serviceRequest);
				serviceRequestResource.setName(resource);
				serviceRequestResource.setValue(iService.getInlineResource(resource));
				
				serviceRequest.addServiceRequestResource(serviceRequestResource);
			}
			
			return serviceRequest;
		}
	}
} 
