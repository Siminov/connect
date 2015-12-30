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



using Siminov.Connect;
using Siminov.Connect.Events;
using Siminov.Connect.Model;
using Siminov.Connect.Resource;
using Siminov.Connect.Service.Design;
using Siminov.Connect.Sync.Design;
using Siminov.Core.Utils;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace connect.Siminov.Connect.Sync
{

    /// <summary>
    /// It provides implementation for Sync IWorker
    /// It processes all sync requests
    /// </summary>
    public class SyncWorker : IWorker
    {

	    private static SyncWorker syncWorker = null;
	    private static Task syncWorkerThread = null;
	
	    private static ICollection<ISyncRequest> syncRequests = new LinkedList<ISyncRequest>();
	
	    private static ResourceManager resourceManager = ResourceManager.GetInstance();
	
        /// <summary>
        /// SyncWorker Private Constructor
        /// </summary>
	    private SyncWorker() 
        {
		
	    }

        /// <summary>
        /// It provides singleton instance of SyncWorker
        /// </summary>
        /// <returns>Singleton instance of SyncWorker</returns>
	    public static SyncWorker GetInstance() 
        {
		
		    if(syncWorker == null) 
            {
			    syncWorker = new SyncWorker();
		    }
		
		    return syncWorker;
	    }
	
	
	    public void StartWorker() 
        {
		
		    if(syncWorkerThread == null) 
            {
			    syncWorkerThread = new Task(new Action(HandleRequests));
		    }
		
		    syncWorkerThread.Start();
	    }
	
	    public void StopWorker() 
        {
		
		    if(syncWorkerThread == null) 
            {
			    return;
		    }
		
		
		    //if(!syncWorkerThread.IsAlive()) 
            {
			    syncWorkerThread.Start();
		    }
	    }
	
	    public bool IsWorkerRunning() 
        {
		
		    if(syncWorkerThread == null) 
            {
			    return false;
		    }
		
		    return syncWorkerThread.IsCompleted;
	    }


        public void RemoveRequest(IRequest request)
        {

        }


	    public void HandleRequests() 
        {
			
			IEnumerator<ISyncRequest> requests = syncRequests.GetEnumerator();
            ICollection<ISyncRequest> removeRequests = new List<ISyncRequest>();

			while(requests.MoveNext()) 
            {
				
				ISyncRequest syncRequest = requests.Current;

				/*
				    * Fire Sync Started Event
				    */
				ISyncEvents syncEventHandler = resourceManager.GetSyncEventHandler();
				if(syncEventHandler != null) 
                {
					syncEventHandler.OnStart(syncRequest);
				}
				
				
				/*
				    * Process Request
				    */
				SyncDescriptor refreshDescriptor = resourceManager.GetSyncDescriptor(syncRequest.GetName());
				
				IEnumerator<String> services = refreshDescriptor.GetServiceDescriptorNames();
				while(services.MoveNext()) 
                {
					
					String service = services.Current;
					
					String serviceName = service.Substring(0, service.IndexOf(Constants.SYNC_DESCRIPTOR_SERVICE_SEPARATOR));
                    String requestName = service.Substring(service.IndexOf(Constants.SYNC_DESCRIPTOR_SERVICE_SEPARATOR) + 1, service.Length - (service.IndexOf(Constants.SYNC_DESCRIPTOR_SERVICE_SEPARATOR) + 1));

					
					ServiceDescriptor serviceDescriptor = resourceManager.RequiredServiceDescriptorBasedOnName(serviceName);
					ServiceDescriptor.Request api = serviceDescriptor.GetRequest(requestName);
					
					String apiHandler = api.GetHandler();
					
					IService serviceHandler = (IService) ClassUtils.CreateClassInstance(apiHandler);
					serviceHandler.SetServiceDescriptor(serviceDescriptor);

					IEnumerator<String> resources = syncRequest.GetResources();
					while(resources.MoveNext()) 
                    {
						String resourceName = resources.Current;
						Object resourceValue = syncRequest.GetResource(resourceName);
						
						serviceHandler.AddResource(resourceName, resourceValue);
					}
					
					
					serviceHandler.Invoke();
				}
				
				
				/*
				    * Fire Sync Started Event
				    */
				if(syncEventHandler != null) {
					syncEventHandler.OnFinish(syncRequest);
				}
				
				
				removeRequests.Add(syncRequest);
			}
			
			foreach(ISyncRequest removeRequest in removeRequests) 
            {
                syncRequests.Remove(removeRequest);
            }

			syncWorkerThread = null;
		}
	

	    public void AddRequest(IRequest request) 
        {
		
		    ISyncRequest syncRequest = (ISyncRequest) request;
		    if(ContainsRequest(syncRequest)) 
            {
			    return;
		    }
		
		
		    syncRequests.Add(syncRequest);

		    /*
		     * Fire Sync Queued Event
		     */
		    ISyncEvents syncEventHandler = resourceManager.GetSyncEventHandler();
		    if(syncEventHandler != null) 
            {
			    syncEventHandler.OnQueue(syncRequest);
		    }
		
		
		    if(!IsWorkerRunning()) 
            {
			    StartWorker();
		    }
	    }
	
	    public bool ContainsRequest(IRequest refreshRequest) 
        {
		    return syncRequests.Contains(refreshRequest);
	    }
	
	    public void removeRequest(IRequest refreshRequest) 
        {
		    syncRequests.Remove((ISyncRequest) refreshRequest);
	    }
    }
}
