/**
 * [SIMINOV FRAMEWORK - CONNECT]
 * Copyright [2014-2016] [Siminov Software Solution LLP|support@siminov.com]
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

package siminov.connect.resource;

import java.util.Iterator;

import siminov.connect.events.EventHandler;
import siminov.connect.events.INotificationEvents;
import siminov.connect.events.ISyncEvents;
import siminov.connect.model.ApplicationDescriptor;
import siminov.connect.model.ServiceDescriptor;
import siminov.connect.model.ServiceDescriptor.Request;
import siminov.connect.model.SyncDescriptor;
import siminov.connect.reader.QuickServiceDescriptorReader;
import siminov.connect.reader.ServiceDescriptorReader;
import siminov.core.exception.SiminovCriticalException;
import siminov.core.exception.SiminovException;
import siminov.core.log.Log;


/**
 * It handles and provides all resources needed by SIMINOV.
 * <p>
 * Such As: Provides Application Descriptor, Service Descriptor, Library Descriptor, Sync Descriptor, Notification Descriptor.
 */
public class ResourceManager {

	private static ResourceManager resources = null;
	
	private ApplicationDescriptor applicationDescriptor = null;
	
	/**
	 * ResourceManager private constructor
	 */
	private ResourceManager() {
		
	}
	
	/**
	 * It provides ResourceManager singleton instance
	 * @return ResourceManager
	 */
	public static ResourceManager getInstance() {
		
		if(resources == null) {
			resources = new ResourceManager();
		}
		
		return resources;
	}

	/**
	 * Get application descriptor
	 * @return Application Descriptor
	 */
	public ApplicationDescriptor getApplicationDescriptor() {
		return this.applicationDescriptor;
	}
	
	/**
	 * Set application descriptor
	 * @param applicationDescriptor Application Descriptor
	 */
	public void setApplicationDescriptor(final ApplicationDescriptor applicationDescriptor) {
		this.applicationDescriptor = applicationDescriptor;
	}
	
	/**
	 * Parse and get service descriptor based on path
	 * @param serviceDescriptorPath Path of service descriptor
	 * @return Service Descriptor
	 */
	public ServiceDescriptor requiredServiceDescriptorBasedOnPath(final String serviceDescriptorPath) {
		
		ServiceDescriptorReader serviceDescriptorReader = new ServiceDescriptorReader(serviceDescriptorPath);
		ServiceDescriptor serviceDescriptor = serviceDescriptorReader.getServiceDescriptor();
		
		applicationDescriptor.addServiceDescriptorNameBasedOnPath(serviceDescriptorPath, serviceDescriptor.getName());
		
		return serviceDescriptor;
	}

	/**
	 * Parse and get service descriptor based on name
	 * @param serviceDescriptorName Name of service descriptor
	 * @return Service Descriptor
	 */
	public ServiceDescriptor requiredServiceDescriptorBasedOnName(final String serviceDescriptorName) {
		
		if(!applicationDescriptor.containServiceDescriptorPathBasedOnName(serviceDescriptorName)) {

			QuickServiceDescriptorReader quickServiceDescriptorReader;
			try {
				quickServiceDescriptorReader = new QuickServiceDescriptorReader(serviceDescriptorName);
				quickServiceDescriptorReader.process();
			} catch(SiminovException siminovException) {
				Log.error(ResourceManager.class.getName(), "requiredServiceDescriptorBasedOnName", "Siminov Exception caught while getting quick service descriptor based on name, " + serviceDescriptorName);
				throw new SiminovCriticalException(ResourceManager.class.getName(), "requiredServiceDescriptorBasedOnName", siminovException.getMessage());
			}
			
			
			if(quickServiceDescriptorReader.containServiceDescriptor()) {
				return quickServiceDescriptorReader.getServiceDescriptor();
			}
		}
		
		
		String serviceDescriptorPath = applicationDescriptor.getServiceDescriptorPathBasedOnName(serviceDescriptorName);
		return requiredServiceDescriptorBasedOnPath(serviceDescriptorPath);
	}

		
	/**
	 * Parse and get service request based on service descriptor path
	 * @param serviceDescriptorPath Path of service descriptor
	 * @param requestName Name of request
	 * @return Request
	 */
	public Request requiredRequestBasedOnServiceDescriptorPath(final String serviceDescriptorPath, final String requestName) {
		
		ServiceDescriptor serviceDescriptor = this.requiredServiceDescriptorBasedOnPath(serviceDescriptorPath);
		return serviceDescriptor.getRequest(requestName);
	}
	
	/**
	 * Parse and get service request based on service descriptor name
	 * @param serviceDescriptorName Name of service descriptor
	 * @param requestName Name of request
	 * @return Request
	 */
	public Request requireRequestBasedOnServiceDescriptorName(final String serviceDescriptorName, final String requestName) {
		
		ServiceDescriptor serviceDescriptor = this.requiredServiceDescriptorBasedOnName(serviceDescriptorName);
		return serviceDescriptor.getRequest(requestName);
	}
	
	/**
	 * Get notification event handler
	 * @return INotificationEvents instance
	 */
	public INotificationEvents getNotificationEventHandler() {
		return EventHandler.getInstance().getNotificationEventHandler();
	}
	
	/**
	 * Get sync event handler
	 * @return ISyncEvents instance
	 */
	public ISyncEvents getSyncEventHandler() {
		return EventHandler.getInstance().getSyncEventHandler();
	}
	
	/**
	 * Get all sync descriptors
	 * @return Sync Descriptors
	 */
	public Iterator<SyncDescriptor> getSyncDescriptors() {
		
		ApplicationDescriptor applicationDescriptor = getApplicationDescriptor();
		return applicationDescriptor.getSyncDescriptors();
	}
	
	/**
	 * Get sync descriptor
	 * @param syncDescriptorName Name of sync descriptor
	 * @return Sync Descriptor
	 */
	public SyncDescriptor getSyncDescriptor(final String syncDescriptorName) {
		
		ApplicationDescriptor applicationDescriptor = getApplicationDescriptor();
		return applicationDescriptor.getSyncDescriptorBasedOnName(syncDescriptorName);
	}
}
