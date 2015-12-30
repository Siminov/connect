///
/// [SIMINOV FRAMEWORK - CONNECT]
/// Copyright [2014-2016] [Siminov Software Solution LLP|support@siminov.com]
///
/// Licensed under the Apache License, Version 2.0 (the "License");
/// you may not use this file except in compliance with the License.
/// You may obtain a copy of the License at
///
///     http://www.apache.org/licenses/LICENSE-2.0
///
/// Unless required by applicable law or agreed to in writing, software
/// distributed under the License is distributed on an "AS IS" BASIS,
/// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
/// See the License for the specific language governing permissions and
/// limitations under the License.



using Siminov.Connect.Connection;
using Siminov.Connect.Connection.Design;
using Siminov.Connect.Exception;
using Siminov.Connect.Model;
using Siminov.Connect.Resource;
using Siminov.Connect.Service.Design;
using Siminov.Core.Database.Design;
using Siminov.Core.Exception;
using Siminov.Core.Log;
using Siminov.Core.Utils;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Siminov.Connect.Service
{

    /// <summary>
    /// It provides implementation for IWorker.
    /// It processes all service ASYNC requests
    /// </summary>
    public class AsyncServiceWorker : IWorker
    {
        
	    private static AsyncServiceWorker asyncServiceWorker = null;
	
	    private Task asyncServiceWorkerThread = null;
	    private static ResourceManager resourceManager = null;
	
	    private static AsyncServiceWorkerHelper serviceUtils = null;
	
	    private ConnectivityChangeReceiver connectivityChangeReceiver = new ConnectivityChangeReceiver();

        /// <summary>
        /// AsyncServiceWorker Constructor
        /// </summary>
	    private AsyncServiceWorker() 
        {
		
		    resourceManager = ResourceManager.GetInstance();
		    serviceUtils = new AsyncServiceWorkerHelper();
		
		    StartWorker();
		
		    /*
		     * Register Connectivity Change Receiver.
		     */
		    //Core.Resource.ResourceManager coreResourceManager = Core.Resource.ResourceManager.GetInstance();
		
		    //applicationContext.registerReceiver(connectivityChangeReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
	    }
	
        /// <summary>
        /// It provides AsyncServiceWorker singleton instance
        /// </summary>
        /// <returns>AsyncServiceWorker singleton instance</returns>
	    public static AsyncServiceWorker GetInstance() 
        {
		
		    if(asyncServiceWorker == null) 
            {
			    asyncServiceWorker = new AsyncServiceWorker();
		    }
		
		    return asyncServiceWorker;
	    }
	
	    public void AddRequest(IRequest request) 
        {
		
		    IService iService = (IService) request;
		
		    Connect.Model.ServiceRequest service = serviceUtils.Convert(iService);
		    bool contain = false;
		
		    try 
            {
			    contain = serviceUtils.ContainService(service);
		    } 
            catch(ServiceException se) 
            {
			    Log.Error(typeof(AsyncServiceWorker).Name, "Process", "ServiceException caught while checking exsisting service, " + se.GetMessage());
			    iService.OnTerminate(se);
			
			    return;
		    }
		
		    if(contain) 
            {
			    return;
		    }
			
		
		    try 
            {
			    service.Save();
		    } 
            catch(SiminovException se) 
            {
			    Log.Error(typeof(AsyncServiceWorker).Name, "Process", "SiminovException caught while saving service, " + se.GetMessage());
			    iService.OnTerminate(new ServiceException(typeof(AsyncServiceWorker).Name, "Process", se.GetMessage()));
			
			    return;
		    }
		
		
		    /*
		     * Service Queued
		     */
		    iService.OnQueue();
		    /*
		     * Service Paused
		     */
		    iService.OnPause();
		
		
		    /*
		     * Notify Async Service Worker Thread.
		     */
		    StartWorker();
	    }

	    public void RemoveRequest(IRequest service) 
        {
		
	    }
	
	    public bool ContainsRequest(IRequest request) 
        {
		
		    IService service = (IService) request;
		    Connect.Model.ServiceRequest serviceRequest = serviceUtils.Convert(service);
		
		    try 
            {
			    return serviceUtils.ContainService(serviceRequest);
		    } 
            catch(ServiceException se) 
            {
			    Log.Error(typeof(AsyncServiceWorker).Name, "ContainsRequest", "ServiceException caught while checking exsisting service, " + se.GetMessage());
			    service.OnTerminate(se);
			
			    return false;
		    }
	    }
	
	    public void StartWorker() 
        {

            if (asyncServiceWorkerThread == null || asyncServiceWorkerThread.IsCompleted || asyncServiceWorkerThread.IsCanceled || asyncServiceWorkerThread.IsFaulted) 
            {
			    asyncServiceWorkerThread = new Task(new Action(HandleRequests));
		    }
            else
            {
                return;
            }
		
		    //if(!asyncServiceWorkerThread.Status. != TaskStatus.Running) 
            {
			    asyncServiceWorkerThread.Start();
		    }
	    }
	
	    public void StopWorker() 
        {
		
		    if(asyncServiceWorkerThread == null) 
            {
			    return;
		    }
		
		    try 
            {
			    //asyncServiceWorkerThread.interrupt();
			    asyncServiceWorkerThread = null;
		    } 
            catch(System.Exception e) 
            {
                Log.Error(this.GetType().Name, "Stop", "Exception caught while stopping async service worder thread, " + e.Message);
			    return;
		    }
	    }
	
	    public bool IsWorkerRunning() 
        {
		
		    if(asyncServiceWorkerThread == null) 
            {
			    return false;
		    }
		
		    return asyncServiceWorkerThread.IsCompleted;
	    }
	
        /// <summary>
        /// It is the inner class of Async service worker which processes all the requests in the queue
        /// </summary>
		public void HandleRequests() 
        {
		
			Connect.Model.ServiceRequest[] services = null;
			try 
            {
                services = (Connect.Model.ServiceRequest[]) new Connect.Model.ServiceRequest().Select().Execute();
			} 
            catch(SiminovException se) 
            {
                Log.Error(this.GetType().Name, "HandleRequests", "SiminovException caught while getting queue services, " + se.GetMessage());
                throw new SiminovCriticalException(this.GetType().Name, "HandleRequests", "SiminovException caught while getting queue services, " + se.GetMessage());
			}
			
			if(services == null || services.Length <= 0) 
            {
				
				AsyncServiceWorker.GetInstance().StopWorker();
				return;
			}

			for(int i = 0;i < services.Length;i++) 
            {
				Connect.Model.ServiceRequest service = services[i];
				IService iService = null;
				
				try 
                {
					iService = serviceUtils.Convert(service);
				} 
                catch(ServiceException se) 
                {
                    Log.Error(this.GetType().Name, "HandleRequests", "ServiceException caught while converting service to iService, " + se.GetMessage());
					return;
				}

				
	    		/*
				* Check Network Connectivity.	
				*/
				if(!Utils.Utils.HasCoverage()) 
                {
					
					try 
                    {
						asyncServiceWorkerThread.Wait();
					} 
                    catch(System.Exception e) 
                    {
                        Log.Error(this.GetType().Name, "HandleRequests", "Exception caught while putting async service worker thread into wait state, " + e.Message);
						return;
					}
				}
				
				
				/*
				    * Service Resumed
				    */
				iService.OnResume();
				Handle(iService);
			}
			
			AsyncServiceWorker.GetInstance().StopWorker();
		}
		
		private void Handle(IService iService) 
        {
			
			IConnectionResponse connectionResponse = null;
			
			try 
            {
				connectionResponse = ConnectionManager.GetInstance().Handle(iService);
			} 
            catch(ConnectionException se) 
            {
				Log.Error(typeof(AsyncServiceWorker).Name, "Handle", "SiminovException caught while invoking connection, " + se.GetMessage());
				
				iService.OnTerminate(new ServiceException(se.GetClassName(), se.GetMethodName(), se.GetMessage()));
				return;
			}
			
			iService.OnRequestFinish(connectionResponse);
			
			IDatabase serviceRequest = serviceUtils.Convert(iService);
			
			try 
            {
				serviceRequest.Delete().Execute();
			} 
            catch(DatabaseException de) 
            {
				Log.Error(typeof(AsyncServiceWorker).Name, "Handle", "Database Exception caught while deleting service request from database, " + de.GetMessage());
				throw new SiminovCriticalException(typeof(AsyncServiceWorker).Name, "Handle", "Database Exception caught while deleting service request from database, " + de.GetMessage());
			}
		}

	    private class ConnectivityChangeReceiver //: BroadcastReceiver 
        {

		    /*public void onReceive(Context context, Intent intent) 
            {
			
			    if(intent.getExtras() == null) 
                {
				    return;
			    }
			
			
			    NetworkInfo networkInfo = (NetworkInfo) intent.getExtras().get(ConnectivityManager.EXTRA_NETWORK_INFO);
			    if(networkInfo.isConnected()) 
                {

				    if(asyncServiceWorkerThread != null && asyncServiceWorkerThread.isAlive()) 
                    {
					    asyncServiceWorkerThread.interrupt();
				    }
			    } 
                else 
                {
				
				    if(asyncServiceWorkerThread != null && asyncServiceWorkerThread.isAlive()) 
                    {
					
					    try 
                        {
						    asyncServiceWorkerThread.wait();
					    } 
                        catch(System.Exception e) 
                        {
						    Log.Error(typeof(AsyncServiceWorkerThread).Name, "onReceive", "Exception caught while putting async service worker thread into wait state because of no connectivity, " + e.GetMessage());	
						    return;	
					    }
				    }
			    }
		    }*/
	    }

	
        /// <summary>
        /// It is the helper class for AsyncServiceWorker
        /// It helps to convert request database instance to IService instance and vis versa
        /// </summary>
	    private class AsyncServiceWorkerHelper 
        {
		
		    /**
		     * Check whether it contains the requested service or not
		     * @param service Service
		     * @return (true/false) TRUE: If service request already exists | FALSE: If service does not exists
		     * @throws ServiceException If there is any exception while checking for request
		     */
		    public bool ContainService(Connect.Model.ServiceRequest service) 
            {
			
			    Connect.Model.ServiceRequest[] services = null;
			    try 
                {
                    services = (Connect.Model.ServiceRequest[]) new Connect.Model.ServiceRequest().Select().Execute();
			    } 
                catch(DatabaseException de) 
                {
				    Log.Error(typeof(AsyncServiceWorker).Name, "ContainService", "DatabaseException caught while getting services from database, " + de.GetMessage());
				    throw new ServiceException(typeof(AsyncServiceWorker).Name, "ContainService", de.GetMessage());
			    }
			
			
			    if(services == null || services.Length <= 0) 
                {
				    return false;
			    }
			
			    for(int i = 0;i < services.Length;i++) 
                {
				
				    Connect.Model.ServiceRequest savedService = services[i];
				    if(service.GetService().Equals(savedService.GetService(), StringComparison.OrdinalIgnoreCase)) 
                    {
					
					    if(service.GetRequest().Equals(savedService.GetRequest(), StringComparison.OrdinalIgnoreCase)) 
                        {
						
						    bool contain = true;
						
						    IEnumerator<ServiceRequestResource> serviceRequestResources = service.GetServiceRequestResources();
						    while(serviceRequestResources.MoveNext()) 
                            {
							
							    ServiceRequestResource serviceResource = serviceRequestResources.Current;
							    ServiceRequestResource savedRequestResource = savedService.GetServiceRequestResource(serviceResource.GetName());
							
							    if(savedRequestResource == null) 
                                {
								    contain = false;
								    break;
							    }
							
							    if(!serviceResource.GetName().Equals(savedRequestResource.GetName(), StringComparison.OrdinalIgnoreCase)) 
                                {
								    contain = false;
								    break;
							    } 
                                else if(!serviceResource.GetValue().Equals(savedRequestResource.GetValue(), StringComparison.OrdinalIgnoreCase)) 
                                {
								    contain = false;
								    break;
							    }
						    }
						
						    if(contain) 
                            {
							    return true;
						    }
					    }
				    } 
			    }
			
			
			    return false;
		    }
		
            /// <summary>
            /// Converts the service request database instance to IService instance
            /// </summary>
            /// <param name="service">ServiceRequest instance</param>
            /// <returns>IService instance</returns>
            /// <exception cref="Siminov.Connect.Exception.ServiceException">ServiceException If any exception occur while converting the instance</exception>
		    public IService Convert(Connect.Model.ServiceRequest service) 
            {
			
			    IService iService = (IService) ClassUtils.CreateClassInstance(service.GetInstanceOf());
			    iService.SetRequestId(service.GetRequestId());
			    iService.SetService(service.GetService());
			    iService.SetRequest(service.GetRequest());
			
			    IEnumerator<ServiceRequestResource> serviceRequestResources = service.GetServiceRequestResources();
			    while(serviceRequestResources.MoveNext()) 
                {
				    ServiceRequestResource serviceResource = serviceRequestResources.Current;
				    iService.AddResource(serviceResource.GetName(), serviceResource.GetValue());
			    }

			    ServiceDescriptor serviceDescriptor = resourceManager.RequiredServiceDescriptorBasedOnName(service.GetService());
			    iService.SetServiceDescriptor(serviceDescriptor);
			
			    ResourceUtils.Resolve(iService);

			    return iService;
		    }
		

            /// <summary>
            /// It converts IService instance to database service request instance
            /// </summary>
            /// <param name="iService">IService instance</param>
            /// <returns>ServiceRequest Instance</returns>
		    public Connect.Model.ServiceRequest Convert(IService iService) 
            {
			
			    Connect.Model.ServiceRequest serviceRequest = new Connect.Model.ServiceRequest();
			    serviceRequest.SetRequestId(iService.GetRequestId());
			    serviceRequest.SetService(iService.GetService());
			    serviceRequest.SetRequest(iService.GetRequest());
			    serviceRequest.SetInstanceOf(iService.GetType().Name);
			
			    IEnumerator<String> resources = iService.GetResources();
			    while(resources.MoveNext()) 
                {
				    String resourceName = resources.Current;
				    Object resourceValue = iService.GetResource(resourceName);
				
				    if(!(resourceValue is String)) 
                    {
					    continue;
				    }
				
				    ServiceRequestResource serviceRequestResource = new ServiceRequestResource();
				    serviceRequestResource.SetServiceRequest(serviceRequest);
				    serviceRequestResource.SetName(resourceName);
				    serviceRequestResource.SetValue((String) resourceValue);
				
				    serviceRequest.AddServiceRequestResource(serviceRequestResource);
			    }
			
			    return serviceRequest;
		    }
	    }
    }
}
