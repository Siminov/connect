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




using Siminov.Connect.Events;
using Siminov.Connect.Model;
using Siminov.Connect.Reader;
using Siminov.Core.Exception;
using Siminov.Core.Log;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Siminov.Connect.Resource
{

    /// <summary>
    /// It handles and provides all resources needed by SIMINOV.
    /// Such As: Provides Application Descriptor, Service Descriptor, Library Descriptor, Sync Descriptor, Notification Descriptor.
    /// </summary>
    public class ResourceManager
    {
        private static ResourceManager resources = null;
	
	    private ApplicationDescriptor applicationDescriptor = null;
	
        /// <summary>
        /// ResourceManager private constructor
        /// </summary>
	    private ResourceManager() 
        {
		
	    }
	

        /// <summary>
        /// It provides ResourceManager singleton instance
        /// </summary>
        /// <returns>ResourceManager</returns>
	    public static ResourceManager GetInstance() 
        {
		
		    if(resources == null) 
            {
			    resources = new ResourceManager();
		    }
		
		    return resources;
	    }


        /// <summary>
        /// Get application descriptor
        /// </summary>
        /// <returns>Application Descriptor</returns>
	    public ApplicationDescriptor GetApplicationDescriptor() 
        {
		    return this.applicationDescriptor;
	    }
	

        /// <summary>
        /// Set application descriptor
        /// </summary>
        /// <param name="applicationDescriptor">Application Descriptor</param>
	    public void SetApplicationDescriptor(ApplicationDescriptor applicationDescriptor) 
        {
		    this.applicationDescriptor = applicationDescriptor;
	    }
	

        /// <summary>
        /// Parse and get service descriptor based on path
        /// </summary>
        /// <param name="serviceDescriptorPath">Path of service descriptor</param>
        /// <returns>Service Descriptor</returns>
	    public ServiceDescriptor RequiredServiceDescriptorBasedOnPath(String serviceDescriptorPath) 
        {
		
		    ServiceDescriptorReader serviceDescriptorReader = new ServiceDescriptorReader(serviceDescriptorPath);
		    ServiceDescriptor serviceDescriptor = serviceDescriptorReader.GetServiceDescriptor();
		
		    applicationDescriptor.AddServiceDescriptorNameBasedOnPath(serviceDescriptorPath, serviceDescriptor.GetName());
		
		    return serviceDescriptor;
	    }


        /// <summary>
        /// Parse and get service descriptor based on name
        /// </summary>
        /// <param name="serviceDescriptorName">Name of service descriptor</param>
        /// <returns>Service Descriptor</returns>
	    public ServiceDescriptor RequiredServiceDescriptorBasedOnName(String serviceDescriptorName) 
        {
		
		    if(!applicationDescriptor.ContainServiceDescriptorPathBasedOnName(serviceDescriptorName)) 
            {

			    QuickServiceDescriptorReader quickServiceDescriptorReader;
			    try 
                {
				    quickServiceDescriptorReader = new QuickServiceDescriptorReader(serviceDescriptorName);
				    quickServiceDescriptorReader.Process();
			    } 
                catch(SiminovException siminovException) 
                {
				    Log.Error(typeof(ResourceManager).Name, "RequiredServiceDescriptorBasedOnName", "Siminov Exception caught while getting quick service descriptor based on name, " + serviceDescriptorName);
				    throw new SiminovCriticalException(typeof(ResourceManager).Name, "RequiredServiceDescriptorBasedOnName", siminovException.GetMessage());
			    }
			
			
			    if(quickServiceDescriptorReader.ContainServiceDescriptor()) 
                {
				    return quickServiceDescriptorReader.GetServiceDescriptor();
			    }
		    }
		
		
		    String serviceDescriptorPath = applicationDescriptor.GetServiceDescriptorPathBasedOnName(serviceDescriptorName);
		    return RequiredServiceDescriptorBasedOnPath(serviceDescriptorPath);
	    }


        /// <summary>
        /// Parse and get service request based on service descriptor path
        /// </summary>
        /// <param name="serviceDescriptorPath">Path of service descriptor</param>
        /// <param name="requestName">Name of request</param>
        /// <returns>Request</returns>
	    public Connect.Model.ServiceDescriptor.Request RequiredRequestBasedOnServiceDescriptorPath(String serviceDescriptorPath, String requestName) 
        {
		
		    ServiceDescriptor serviceDescriptor = this.RequiredServiceDescriptorBasedOnPath(serviceDescriptorPath);
		    return serviceDescriptor.GetRequest(requestName);
	    }
	

        /// <summary>
        /// Parse and get service request based on service descriptor name
        /// </summary>
        /// <param name="serviceDescriptorName">Name of service descriptor</param>
        /// <param name="requestName">Name of request</param>
        /// <returns>Request</returns>
	    public Connect.Model.ServiceDescriptor.Request RequireRequestBasedOnServiceDescriptorName(String serviceDescriptorName, String requestName) 
        {
		
		    ServiceDescriptor serviceDescriptor = this.RequiredServiceDescriptorBasedOnName(serviceDescriptorName);
		    return serviceDescriptor.GetRequest(requestName);
	    }
	

        /// <summary>
        /// Get notification event handler
        /// </summary>
        /// <returns>INotificationEvents instance</returns>
	    public INotificationEvents GetNotificationEventHandler() 
        {
		    return Connect.Events.EventHandler.GetInstance().GetNotificationEventHandler();
	    }
	

        /// <summary>
        /// Get sync event handler
        /// </summary>
        /// <returns>ISyncEvents instance</returns>
	    public ISyncEvents GetSyncEventHandler() 
        {
		    return Connect.Events.EventHandler.GetInstance().GetSyncEventHandler();
	    }
	

        /// <summary>
        /// Get all sync descriptors
        /// </summary>
        /// <returns>Sync Descriptors</returns>
	    public IEnumerator<SyncDescriptor> GetSyncDescriptors() 
        {
		
		    ApplicationDescriptor applicationDescriptor = GetApplicationDescriptor();
		    return applicationDescriptor.GetSyncDescriptors();
	    }
	

        /// <summary>
        /// Get sync descriptor
        /// </summary>
        /// <param name="syncDescriptorName">Name of sync descriptor</param>
        /// <returns>Sync Descriptor</returns>
	    public SyncDescriptor GetSyncDescriptor(String syncDescriptorName) 
        {
		
		    ApplicationDescriptor applicationDescriptor = GetApplicationDescriptor();
		    return applicationDescriptor.GetSyncDescriptorBasedOnName(syncDescriptorName);
	    }

    }
}
