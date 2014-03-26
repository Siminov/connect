package siminov.connect.worker.service;

import java.util.Iterator;

import siminov.connect.connection.ConnectionManager;
import siminov.connect.design.connection.IConnectionResponse;
import siminov.connect.design.service.IService;
import siminov.connect.design.service.IServiceWorker;
import siminov.connect.exception.ConnectionException;
import siminov.connect.exception.ServiceException;
import siminov.connect.model.ServiceDescriptor;
import siminov.connect.model.ServiceRequestResource;
import siminov.connect.resource.Resources;
import siminov.connect.resource.ServiceResourceUtils;
import siminov.connect.utils.Utils;
import siminov.connect.worker.IWorker;
import siminov.orm.exception.DatabaseException;
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

public class AsyncServiceWorker implements IWorker, IServiceWorker {

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
		
		siminov.connect.model.ServiceRequest service = serviceUtils.convert(iService);
		boolean contain = false;
		
		try {
			contain = serviceUtils.containService(service);
		} catch(ServiceException se) {
			Log.loge(AsyncServiceWorker.class.getName(), "process", "ServiceException caught while checking exsisting service, " + se.getMessage());
			iService.onServiceTerminate(se);
			
			return;
		}
		
		if(contain) {
			return;
		}
			
		
		try {
			service.save();
		} catch(SiminovException se) {
			Log.loge(AsyncServiceWorker.class.getName(), "process", "SiminovException caught while saving service, " + se.getMessage());
			iService.onServiceTerminate(new ServiceException(AsyncServiceWorker.class.getName(), "process", se.getMessage()));
			
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
		
		if(!asyncServiceWorkerThread.isAlive()) {
			asyncServiceWorkerThread.start();
		}
	}
	
	public void stopWorker() {
		
		if(asyncServiceWorkerThread == null) {
			return;
		}
		
		try {
			asyncServiceWorkerThread.interrupt();
			asyncServiceWorkerThread = null;
		} catch(Exception e) {
			Log.loge(AsyncServiceWorkerThread.class.getName(), "stop", "Exception caught while stopping async service worder thread, " + e.getMessage());
			return;
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
		
			siminov.connect.model.ServiceRequest[] services = null;
			try {
				services = (siminov.connect.model.ServiceRequest[]) new siminov.connect.model.ServiceRequest().select().fetch();
			} catch(SiminovException se) {
				Log.loge(AsyncServiceWorkerThread.class.getName(), "run", "SiminovException caught while getting queue services, " + se.getMessage());
				throw new SiminovCriticalException(AsyncServiceWorkerThread.class.getName(), "run", "SiminovException caught while getting queue services, " + se.getMessage());
			}
			
			if(services == null || services.length <= 0) {
				
				AsyncServiceWorker.getInstance().stopWorker();
				return;
			}

			for(int i = 0;i < services.length;i++) {
				siminov.connect.model.ServiceRequest service = services[i];
				IService iService = null;
				
				try {
					iService = serviceUtils.convert(service);
				} catch(ServiceException se) {
					Log.loge(AsyncServiceWorkerThread.class.getName(), "run", "ServiceException caught while converting service to iService, " + se.getMessage());
					return;
				}

				
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
			
			AsyncServiceWorker.getInstance().stopWorker();
		}
		
		private void handle(final IService iService) {
			
			IConnectionResponse connectionResponse = null;
			
			try {
				connectionResponse = ConnectionManager.getInstance().handle(iService);
			} catch(ConnectionException se) {
				Log.loge(SyncServiceWorker.class.getName(), "process", "SiminovException caught while invoking connection, " + se.getMessage());
				
				iService.onServiceTerminate(new ServiceException(se.getClassName(), se.getMethodName(), se.getMessage()));
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

				if(asyncServiceWorkerThread != null && asyncServiceWorkerThread.isAlive()) {
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
		
		public boolean containService(siminov.connect.model.ServiceRequest service) throws ServiceException {
			
			siminov.connect.model.ServiceRequest[] services = null;
			try {
				services = (siminov.connect.model.ServiceRequest[]) new siminov.connect.model.ServiceRequest().select().fetch();
			} catch(DatabaseException de) {
				Log.loge(AsyncServiceWorker.class.getName(), "containService", "DatabaseException caught while getting services from database, " + de.getMessage());
				throw new ServiceException(AsyncServiceWorker.class.getName(), "containService", de.getMessage());
			}
			
			
			if(services == null || services.length <= 0) {
				return false;
			}
			
			for(int i = 0;i < services.length;i++) {
				
				siminov.connect.model.ServiceRequest savedService = services[i];
				if(service.getService().equalsIgnoreCase(savedService.getService())) {
					
					if(service.getApi().equalsIgnoreCase(savedService.getApi())) {
						
						boolean contain = true;
						
						Iterator<ServiceRequestResource> serviceRequestResources = service.getServiceRequestResources();
						while(serviceRequestResources.hasNext()) {
							
							ServiceRequestResource serviceResource = serviceRequestResources.next();
							ServiceRequestResource savedRequestResource = savedService.getServiceRequestResource(serviceResource.getName());
							
							if(savedRequestResource == null) {
								contain = false;
								break;
							}
							
							if(!serviceResource.getName().equalsIgnoreCase(savedRequestResource.getName())) {
								contain = false;
								break;
							} else if(!serviceResource.getValue().equalsIgnoreCase(savedRequestResource.getValue())) {
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
		
		public IService convert(siminov.connect.model.ServiceRequest service) throws ServiceException {
			
			IService iService = (IService) ClassUtils.createClassInstance(service.getInstanceOf());
			iService.setRequestId(service.getRequestId());
			iService.setService(service.getService());
			iService.setApi(service.getApi());
			
			Iterator<ServiceRequestResource> serviceRequestResources = service.getServiceRequestResources();
			while(serviceRequestResources.hasNext()) {
				ServiceRequestResource serviceResource = serviceRequestResources.next();
				iService.addInlineResource(serviceResource.getName(), serviceResource.getValue());
			}

			ServiceDescriptor serviceDescriptor = resources.requiredServiceDescriptorBasedOnName(service.getService());
			iService.setServiceDescriptor(serviceDescriptor);
			
			ServiceResourceUtils.resolve(iService);

			return iService;
		}
		
		public siminov.connect.model.ServiceRequest convert(final IService iService) {
			
			siminov.connect.model.ServiceRequest serviceRequest = new siminov.connect.model.ServiceRequest();
			serviceRequest.setRequestId(iService.getRequestId());
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
