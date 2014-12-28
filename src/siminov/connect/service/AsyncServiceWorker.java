/** 
 * [SIMINOV FRAMEWORK]
 * Copyright [2015] [Siminov Software Solution LLP|support@siminov.com]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 **/

package siminov.connect.service;

import java.util.Iterator;

import siminov.connect.IRequest;
import siminov.connect.IWorker;
import siminov.connect.connection.ConnectionManager;
import siminov.connect.connection.design.IConnectionResponse;
import siminov.connect.exception.ConnectionException;
import siminov.connect.exception.ServiceException;
import siminov.connect.model.ServiceDescriptor;
import siminov.connect.model.ServiceRequestResource;
import siminov.connect.resource.ResourceManager;
import siminov.connect.resource.ResourceUtils;
import siminov.connect.service.design.IService;
import siminov.connect.utils.Utils;
import siminov.core.database.design.IDatabase;
import siminov.core.exception.DatabaseException;
import siminov.core.exception.SiminovCriticalException;
import siminov.core.exception.SiminovException;
import siminov.core.log.Log;
import siminov.core.utils.ClassUtils;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * It provides implementation for IWorker.
 * It processes all service ASYNC requests
 */
public class AsyncServiceWorker implements IWorker {

	private static AsyncServiceWorker asyncServiceWorker = null;
	
	private AsyncServiceWorkerThread asyncServiceWorkerThread = null;
	private ResourceManager resourceManager = null;
	
	private AsyncServiceWorkerHelper serviceUtils = null;
	
	private ConnectivityChangeReceiver connectivityChangeReceiver = new ConnectivityChangeReceiver();

	/**
	 * AsyncServiceWorker Constructor
	 */
	private AsyncServiceWorker() {
		
		resourceManager = ResourceManager.getInstance();
		serviceUtils = new AsyncServiceWorkerHelper();
		
		startWorker();
		
		/*
		 * Register Connectivity Change Receiver.
		 */
		siminov.core.resource.ResourceManager ormResourceManager = siminov.core.resource.ResourceManager.getInstance();
		Context applicationContext = ormResourceManager.getApplicationContext();
		
		applicationContext.registerReceiver(connectivityChangeReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
	}
	
	
	/**
	 * It provides AsyncServiceWorker singleton instance
	 * @return AsyncServiceWorker singleton instance
	 */
	public static AsyncServiceWorker getInstance() {
		
		if(asyncServiceWorker == null) {
			asyncServiceWorker = new AsyncServiceWorker();
		}
		
		return asyncServiceWorker;
	}
	
	public void addRequest(final IRequest request) {
		
		IService iService = (IService) request;
		
		siminov.connect.model.ServiceRequest service = serviceUtils.convert(iService);
		boolean contain = false;
		
		try {
			contain = serviceUtils.containService(service);
		} catch(ServiceException se) {
			Log.error(AsyncServiceWorker.class.getName(), "process", "ServiceException caught while checking exsisting service, " + se.getMessage());
			iService.onTerminate(se);
			
			return;
		}
		
		if(contain) {
			return;
		}
			
		
		try {
			service.save();
		} catch(SiminovException se) {
			Log.error(AsyncServiceWorker.class.getName(), "process", "SiminovException caught while saving service, " + se.getMessage());
			iService.onTerminate(new ServiceException(AsyncServiceWorker.class.getName(), "process", se.getMessage()));
			
			return;
		}
		
		
		/*
		 * Service Queued
		 */
		iService.onQueue();
		/*
		 * Service Paused
		 */
		iService.onPause();
		
		
		/*
		 * Notify Async Service Worker Thread.
		 */
		startWorker();
	}

	public void removeRequest(final IRequest service) {
		
	}
	
	public boolean containsRequest(final IRequest request) {
		
		IService service = (IService) request;
		siminov.connect.model.ServiceRequest serviceRequest = serviceUtils.convert(service);
		
		try {
			return serviceUtils.containService(serviceRequest);
		} catch(ServiceException se) {
			Log.error(AsyncServiceWorker.class.getName(), "containsRequest", "ServiceException caught while checking exsisting service, " + se.getMessage());
			service.onTerminate(se);
			
			return false;
		}
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
			Log.error(AsyncServiceWorkerThread.class.getName(), "stop", "Exception caught while stopping async service worder thread, " + e.getMessage());
			return;
		}
	}
	
	public boolean isWorkerRunning() {
		
		if(asyncServiceWorkerThread == null) {
			return false;
		}
		
		return asyncServiceWorkerThread.isAlive();
	}
	
	/**
	 * It is the inner class of Async service worker which processes all the requests in the queue
	 */
	private class AsyncServiceWorkerThread extends Thread {
		
		public void run() {
		
			siminov.connect.model.ServiceRequest[] services = null;
			try {
				services = new siminov.connect.model.ServiceRequest().select().execute();
			} catch(SiminovException se) {
				Log.error(AsyncServiceWorkerThread.class.getName(), "run", "SiminovException caught while getting queue services, " + se.getMessage());
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
					Log.error(AsyncServiceWorkerThread.class.getName(), "run", "ServiceException caught while converting service to iService, " + se.getMessage());
					return;
				}

				
				/*
				 * Check Network Connectivity.	
				 */
				if(!Utils.hasCoverage()) {
					
					try {
						this.wait();
					} catch(Exception e) {
						Log.error(AsyncServiceWorkerThread.class.getName(), "run", "Exception caught while putting async service worker thread into wait state, " + e.getMessage());
						return;
					}
				}
				
				
				/*
				 * Service Resumed
				 */
				iService.onResume();
				handle(iService);
			}
			
			AsyncServiceWorker.getInstance().stopWorker();
		}
		
		private void handle(final IService iService) {
			
			IConnectionResponse connectionResponse = null;
			
			try {
				connectionResponse = ConnectionManager.getInstance().handle(iService);
			} catch(ConnectionException se) {
				Log.error(AsyncServiceWorker.class.getName(), "handle", "SiminovException caught while invoking connection, " + se.getMessage());
				
				iService.onTerminate(new ServiceException(se.getClassName(), se.getMethodName(), se.getMessage()));
				return;
			}
			
			iService.onRequestFinish(connectionResponse);
			
			IDatabase serviceRequest = serviceUtils.convert(iService);
			
			try {
				serviceRequest.delete().execute();
			} catch(DatabaseException de) {
				Log.error(AsyncServiceWorker.class.getName(), "handle", "Database Exception caught while deleting service request from database, " + de.getMessage());
				throw new SiminovCriticalException(AsyncServiceWorker.class.getName(), "handle", "Database Exception caught while deleting service request from database, " + de.getMessage());
			}
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
						Log.error(AsyncServiceWorkerThread.class.getName(), "onReceive", "Exception caught while putting async service worker thread into wait state because of no connectivity, " + e.getMessage());	
						return;	
					}
				}
			}
		}
	}

	
	/**
	 * It is the helper class for AsyncServiceWorker
	 * It helps to convert request database instance to IService instance and vis versa
	 */
	private class AsyncServiceWorkerHelper {
		
		/**
		 * Check whether it contains the requested service or not
		 * @param service Service
		 * @return (true/false) TRUE: If service request already exists | FALSE: If service does not exists
		 * @throws ServiceException If there is any exception while checking for request
		 */
		public boolean containService(siminov.connect.model.ServiceRequest service) throws ServiceException {
			
			siminov.connect.model.ServiceRequest[] services = null;
			try {
				services = new siminov.connect.model.ServiceRequest().select().execute();
			} catch(DatabaseException de) {
				Log.error(AsyncServiceWorker.class.getName(), "containService", "DatabaseException caught while getting services from database, " + de.getMessage());
				throw new ServiceException(AsyncServiceWorker.class.getName(), "containService", de.getMessage());
			}
			
			
			if(services == null || services.length <= 0) {
				return false;
			}
			
			for(int i = 0;i < services.length;i++) {
				
				siminov.connect.model.ServiceRequest savedService = services[i];
				if(service.getService().equalsIgnoreCase(savedService.getService())) {
					
					if(service.getRequest().equalsIgnoreCase(savedService.getRequest())) {
						
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
		
		/**
		 * Converts the service request database instance to IService instance
		 * @param service ServiceRequest instance
		 * @return IService instance
		 * @throws ServiceException If any exception occur while converting the instance
		 */
		public IService convert(siminov.connect.model.ServiceRequest service) throws ServiceException {
			
			IService iService = (IService) ClassUtils.createClassInstance(service.getInstanceOf());
			iService.setRequestId(service.getRequestId());
			iService.setService(service.getService());
			iService.setRequest(service.getRequest());
			
			Iterator<ServiceRequestResource> serviceRequestResources = service.getServiceRequestResources();
			while(serviceRequestResources.hasNext()) {
				ServiceRequestResource serviceResource = serviceRequestResources.next();
				iService.addResource(serviceResource.getName(), serviceResource.getValue());
			}

			ServiceDescriptor serviceDescriptor = resourceManager.requiredServiceDescriptorBasedOnName(service.getService());
			iService.setServiceDescriptor(serviceDescriptor);
			
			ResourceUtils.resolve(iService);

			return iService;
		}
		
		/**
		 * It converts IService instance to database service request instance
		 * @param iService IService instance
		 * @return ServiceRequest Instance
		 */
		public siminov.connect.model.ServiceRequest convert(final IService iService) {
			
			siminov.connect.model.ServiceRequest serviceRequest = new siminov.connect.model.ServiceRequest();
			serviceRequest.setRequestId(iService.getRequestId());
			serviceRequest.setService(iService.getService());
			serviceRequest.setRequest(iService.getRequest());
			serviceRequest.setInstanceOf(iService.getClass().getName());
			
			Iterator<String> resources = iService.getResources();
			while(resources.hasNext()) {
				String resourceName = resources.next();
				Object resourceValue = iService.getResource(resourceName);
				
				if(!(resourceValue instanceof String)) {
					continue;
				}
				
				ServiceRequestResource serviceRequestResource = new ServiceRequestResource();
				serviceRequestResource.setServiceRequest(serviceRequest);
				serviceRequestResource.setName(resourceName);
				serviceRequestResource.setValue((String) resourceValue);
				
				serviceRequest.addServiceRequestResource(serviceRequestResource);
			}
			
			return serviceRequest;
		}
	}
} 
