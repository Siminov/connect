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



using Siminov.Connect.Model;
using Siminov.Connect.Resource;
using Siminov.Connect.Service.Design;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Siminov.Connect.Service
{

    /// <summary>
    /// It is a singleton class which handles all service requests
    /// </summary>
    public class ServiceHandler
    {

	    private static ServiceHandler serviceHandler = null;
	
	    private SyncServiceWorker syncServiceWorker = null;
	    private IWorker asyncServiceWorker = null;
	
	    private ResourceManager resourceManager = null;
	
        /// <summary>
        /// ServiceHandler Private Constructor
        /// </summary>
	    private ServiceHandler() 
        {
		
		    syncServiceWorker =  new SyncServiceWorker();
		    asyncServiceWorker = AsyncServiceWorker.GetInstance();
		
		    resourceManager = ResourceManager.GetInstance();
	    }
	
        /// <summary>
        /// It provides singleton instance of ServiceHandler class
        /// </summary>
        /// <returns>ServiceHandler singleton instance</returns>
	    public static ServiceHandler GetInstance() 
        {
		
		    if(serviceHandler == null) 
            {
			    serviceHandler = new ServiceHandler();
		    }
		
		    return serviceHandler;
	    }
	
        /// <summary>
        /// It handles the service request
        /// </summary>
        /// <param name="service">Service instance</param>
        /// <exception cref="Siminov.Connect.Exception.ServiceException">If any exception occur while handling the service request</exception>
	    public void Handle(IService service) 
        {

		    ServiceDescriptor serviceDescriptor = service.GetServiceDescriptor();
		    if(serviceDescriptor == null) 
            {
			    serviceDescriptor = resourceManager.RequiredServiceDescriptorBasedOnName(service.GetService());
			    service.SetServiceDescriptor(serviceDescriptor);
		    }


		    IEnumerator<String> resources = service.GetResources();
		    while(resources.MoveNext()) 
            {
			    String resourceName = resources.Current;
			    Object resourceValue = service.GetResource(resourceName);
			
			    if(resourceValue is String) 
                {
				    serviceDescriptor.AddProperty(resourceName, (String) resourceValue);
			    }
		    }

		
		    Connect.Model.ServiceDescriptor.Request request = serviceDescriptor.GetRequest(service.GetRequest());
            var serviceDescriptors = new List<Core.Model.IDescriptor>();
            serviceDescriptors.Add(serviceDescriptor);

		    String mode = ResourceUtils.Resolve(request.GetMode(), serviceDescriptors.ToArray());
		
		    if(mode.Equals(Constants.SERVICE_DESCRIPTOR_REQUEST_SYNC_REQUEST_MODE, StringComparison.OrdinalIgnoreCase)) 
            {

			    ResourceUtils.Resolve(service);
			    syncServiceWorker.Process(service);
		    } 
            else if(mode.Equals(Constants.SERVICE_DESCRIPTOR_REQUEST_ASYNC_REQUEST_MODE, StringComparison.OrdinalIgnoreCase)) 
            {
			    asyncServiceWorker.AddRequest(service);
		    }
	    }
    }
}
