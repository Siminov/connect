package siminov.connect.service.worker;

import java.util.Iterator;

import siminov.connect.connection.ConnectionHelper;
import siminov.connect.connection.ConnectionRequest;
import siminov.connect.connection.ConnectionResponse;
import siminov.connect.model.ServiceDescriptor;
import siminov.connect.resource.Resources;
import siminov.connect.service.IService;
import siminov.connect.service.IServiceWorker;
import siminov.connect.service.ServiceHandler;
import siminov.connect.service.model.RequestResource;
import siminov.connect.utils.ServiceResourceUtils;
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

public class AsyncServiceWorker implements IServiceWorker {

	private static AsyncServiceWorker asyncServiceWorker = null;
	
	private AsyncServiceWorkerThread asyncServiceWorkerThread = null;
	private Resources resources = null;
	
	private ServiceUtils serviceUtils = null;
	
	private ConnectivityChangeReceiver connectivityChangeReceiver = new ConnectivityChangeReceiver();
	
	private AsyncServiceWorker() {
		
		resources = Resources.getInstance();
		serviceUtils = new ServiceUtils();
		
		start();
		
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
		
		siminov.connect.service.model.Request service = serviceUtils.convert(iService);
		boolean contain = false;
		
		try {
			contain = serviceUtils.containService(service);
		} catch(SiminovException se) {
			Log.loge(AsyncServiceWorker.class.getName(), "process", "SiminovException caught while checking exsisting service, " + se.getMessage());
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
			iService.onServiceTerminate(se);
			
			return;
		}
		
		
		/*
		 * Notify Async Service Worker Thread.
		 */
		start();
	}

	private void start() {
		
		if(asyncServiceWorkerThread == null) {
			asyncServiceWorkerThread = new AsyncServiceWorkerThread();
		}
		
		if(!asyncServiceWorkerThread.isAlive()) {
			asyncServiceWorkerThread.start();
		}
	}
	
	private void stop() {
		
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
	
	
	private class AsyncServiceWorkerThread extends Thread {
		
		public void run() {
		
			siminov.connect.service.model.Request[] services = null;
			try {
				services = (siminov.connect.service.model.Request[]) new siminov.connect.service.model.Request().select().fetch();
			} catch(SiminovException se) {
				Log.loge(AsyncServiceWorkerThread.class.getName(), "run", "SiminovException caught while getting queue services, " + se.getMessage());
				throw new SiminovCriticalException(AsyncServiceWorkerThread.class.getName(), "run", "SiminovException caught while getting queue services, " + se.getMessage());
			}
			
			if(services == null || services.length <= 0) {
				
				AsyncServiceWorker.getInstance().stop();
				return;
			}

			for(int i = 0;i < services.length;i++) {
				siminov.connect.service.model.Request service = services[i];
				IService iService = null;
				
				try {
					iService = serviceUtils.convert(service);
				} catch(SiminovException se) {
					Log.loge(AsyncServiceWorkerThread.class.getName(), "run", "SiminovException caught while converting service to iService, " + se.getMessage());
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
			
			AsyncServiceWorker.getInstance().stop();
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
		
		public boolean containService(siminov.connect.service.model.Request service) throws SiminovException {
			
			siminov.connect.service.model.Request[] services = (siminov.connect.service.model.Request[]) new siminov.connect.service.model.Request().select().fetch();
			if(services == null || services.length <= 0) {
				return false;
			}
			
			for(int i = 0;i < services.length;i++) {
				
				siminov.connect.service.model.Request savedService = services[i];
				if(service.getService().equalsIgnoreCase(savedService.getService())) {
					
					if(service.getApi().equalsIgnoreCase(savedService.getApi())) {
						
						boolean contain = true;
						
						Iterator<RequestResource> serviceResources = service.getServiceResources();
						while(serviceResources.hasNext()) {
							
							RequestResource serviceResource = serviceResources.next();
							RequestResource savedServiceResource = savedService.getServiceResource(serviceResource.getName());
							
							if(savedServiceResource == null) {
								contain = false;
								break;
							}
							
							if(!serviceResource.getName().equalsIgnoreCase(savedServiceResource.getName())) {
								contain = false;
								break;
							} else if(!serviceResource.getValue().equalsIgnoreCase(savedServiceResource.getValue())) {
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
		
		public IService convert(siminov.connect.service.model.Request service) throws SiminovException {
			
			IService iService = (IService) ClassUtils.createClassInstance(service.getInstanceOf());
			iService.setRequestId(service.getRequestId());
			iService.setService(service.getService());
			iService.setApi(service.getApi());
			
			Iterator<RequestResource> serviceResources = service.getServiceResources();
			while(serviceResources.hasNext()) {
				RequestResource serviceResource = serviceResources.next();
				iService.addResource(serviceResource.getName(), serviceResource.getValue());
			}

			ServiceDescriptor serviceDescriptor = resources.requiredServiceDescriptorBasedOnName(service.getService());
			iService.setServiceDescriptor(serviceDescriptor);
			
			ServiceResourceUtils.resolve(iService);

			return iService;
		}
		
		public siminov.connect.service.model.Request convert(final IService iService) {
			
			siminov.connect.service.model.Request service = new siminov.connect.service.model.Request();
			service.setRequestId(iService.getRequestId());
			service.setService(iService.getService());
			service.setApi(iService.getApi());
			service.setInstanceOf(iService.getClass().getName());
			
			Iterator<String> resources = iService.getResources();
			while(resources.hasNext()) {
				String resource = resources.next();
				
				RequestResource serviceRequestResource = new RequestResource();
				serviceRequestResource.setServiceRequest(service);
				serviceRequestResource.setName(resource);
				serviceRequestResource.setValue(iService.getResource(resource));
				
				service.addServiceResource(serviceRequestResource);
			}
			
			return service;
		}
	}
} 
