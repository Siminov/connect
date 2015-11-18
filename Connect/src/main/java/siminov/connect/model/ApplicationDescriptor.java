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

package siminov.connect.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;


/**
 * Exposes methods to GET and SET Application Descriptor information as per define in ApplicationDescriptor.xml file by application.
	<p>
		<pre>
		
Example:
	{@code

	<siminov>
	    
		<!-- General Application Description Properties -->
		
			<!-- Mandatory Field -->
		<property name="name">application_name</property>	
		
			<!-- Optional Field -->
		<property name="description">application_description</property>
		
			<!-- Mandatory Field (Default is 0.0) -->
		<property name="version">application_version</property>
	
	
		
		<!-- Database Descriptors Used By Application (zero-to-many) -->	
			<!-- Optional Field's -->
		<database-descriptors>
			<database-descriptor>full_path_of_database_descriptor_file</database-descriptor>
		</database-descriptors>
			
	
	   	<!-- Service Descriptors -->
	    <service-descriptors>
	  		
	  			<!-- Service Descriptor -->
	        <service-descriptor>full_path_of_service_descriptor</service-descriptor>
	    
	    </service-descriptors>
	
	    
		
	    <!-- Sync Descriptors -->
	    	<!-- Sync Descriptor -->
	    <sync-descriptors>
	        
			<sync-descriptor>full_path_of_sync_descriptor</sync-descriptor>
	        
	    </sync-descriptors>
	    
	
	    <!-- Notification Descriptor -->
	    <notification-descriptor>
	        
	        	<!-- Optional Field -->
	        <property name="name_of_property">value_of_property</property>
	
	    </notification-descriptor>
	    
			
		
		<!-- Library Descriptors Used By Application (zero-to-many) -->
			<!-- Optional Field's -->
		<library-descriptors>
		 	<library-descriptor>full_path_of_library_descriptor_file</library-descriptor>   
		</librar-descriptors>
		
			
		<!-- Event Handlers Implemented By Application (zero-to-many) -->
		
			<!-- Optional Field's -->
		<event-handlers>
			<event-handler>full_class_path_of_event_handler_(ISiminovHandler/IDatabaseHandler)</event-handler>
		</event-handlers>
	
	</siminov>
	
	}
	
		</pre>
	</p>
 *
 */
public class ApplicationDescriptor extends siminov.core.model.ApplicationDescriptor {

	private Collection<String> serviceDescriptorPaths = new ConcurrentLinkedQueue<String> ();
	private Map<String, String> serviceDescriptorNamesBasedOnPath = new HashMap<String, String>();

	private Collection<String> syncDescriptorPaths = new ConcurrentLinkedQueue<String>();
	
	private Map<String, SyncDescriptor> syncDescriptorsBasedOnName = new HashMap<String, SyncDescriptor>();
	private Map<String, SyncDescriptor> syncDescriptorsBasedOnPath = new HashMap<String, SyncDescriptor>();
	
	private NotificationDescriptor notificationDescriptor = null;


	/**
	 * Add service descriptor path
	 * @param serviceDescriptorPath Path of service descriptor
	 */
	public void addServiceDescriptorPath(String serviceDescriptorPath) {
		this.serviceDescriptorPaths.add(serviceDescriptorPath);
	}

	/**
	 * Remove service descriptor path
	 * @param serviceDescriptorPath Path of service descriptor
	 */
	public void removeServiceDescriptorPath(String serviceDescriptorPath) {
		this.serviceDescriptorPaths.remove(serviceDescriptorPath);
	}

	/**
	 * Get all service descriptor paths
	 * @return Path of service descriptor
	 */
	public Iterator<String> getServiceDescriptorPaths() {
		return this.serviceDescriptorPaths.iterator();
	}

	/**
	 * Check whether service descriptor exists or not based on name 
	 * @param serviceDescriptorName Name of service descriptor
	 * @return (true/false) TRUE: If service descriptor exists | FALSE: If service descriptor does not exists
	 */
	public boolean containServiceDescriptorPathBasedOnName(String serviceDescriptorName) {
		return this.serviceDescriptorNamesBasedOnPath.containsValue(serviceDescriptorName);
	}

	/**
	 * Check whether service descriptor exists or not based on path
	 * @param serviceDescriptorPath Path of service descriptor
	 * @return (true/false) TRUE: If service descriptor exists | FALSE: If service descriptor does not exists
	 */
	public boolean containServiceDescriptorNameBasedOnPath(String serviceDescriptorPath) {
		return this.serviceDescriptorNamesBasedOnPath.containsKey(serviceDescriptorPath);
	}

	/**
	 * Get service descriptor path based on name
	 * @param serviceDescriptorName Name of service descriptor
	 * @return Path of service descriptor
	 */
	public String getServiceDescriptorPathBasedOnName(String serviceDescriptorName) {

		if(this.containServiceDescriptorPathBasedOnName(serviceDescriptorName)) {

			Iterator<String> serviceDescriptorPaths = this.serviceDescriptorNamesBasedOnPath.keySet().iterator();
			while(serviceDescriptorPaths.hasNext()) {

				String serviceDescriptorPath = serviceDescriptorPaths.next();

				String foundServiceDescriptorName = this.serviceDescriptorNamesBasedOnPath.get(serviceDescriptorPath);
				if(foundServiceDescriptorName.equalsIgnoreCase(serviceDescriptorName)) {
					return this.serviceDescriptorNamesBasedOnPath.get(serviceDescriptorPath);
				}
			}
		}

		return null;
	}

	/**
	 * Add service descriptor name based on path
	 * @param serviceDescriptorPath Path of service descriptor
	 * @param serviceDescriptoName Name of service descriptor
	 */
	public void addServiceDescriptorNameBasedOnPath(String serviceDescriptorPath, String serviceDescriptoName) {
		this.serviceDescriptorNamesBasedOnPath.put(serviceDescriptorPath, serviceDescriptoName);
	}

	/**
	 * Remove service descriptor name based on path
	 * @param serviceDescriptorPath Path of service descriptor
	 */
	public void removeServiceDescriptorNameBasedOnPath(String serviceDescriptorPath) {
		this.serviceDescriptorNamesBasedOnPath.remove(serviceDescriptorPath);
	}

	
	/**
	 * Get sync descriptor paths
	 * @return Paths of sync descriptors
	 */
	public Iterator<String> getSyncDescriptorPaths() {
		return this.syncDescriptorPaths.iterator();
	}
	
	/**
	 * Add sync descriptor path
	 * @param syncDescriptorPath Path of sync descriptor
	 */
	public void addSyncDescriptorPath(String syncDescriptorPath) {
		this.syncDescriptorPaths.add(syncDescriptorPath);
	}

	/**
	 * Remove sync descriptor path
	 * @param syncDescriptorPath Path of sync descriptor
	 */
	public void removeSyncDescriptorPath(String syncDescriptorPath) {
		this.syncDescriptorPaths.remove(syncDescriptorPath);
	}

	/**
	 * Check whether sync descriptor path exists or not
	 * @param syncDescriptorPath Path of sync descriptor
	 * @return (true/false) TRUE: If sync descriptor path exists | FALSE: If sync descriptor path does not exists
	 */
	public boolean containSyncDescriptorPath(String syncDescriptorPath) {
		return this.syncDescriptorPaths.contains(syncDescriptorPath);
	}
	
	/**
	 * Get all sync descriptors
	 * @return Sync Descriptors
	 */
	public Iterator<SyncDescriptor> getSyncDescriptors() {
		return this.syncDescriptorsBasedOnName.values().iterator();
	}

	/**
	 * Get sync descriptor based on path
	 * @param syncDescriptorPath Path of sync descriptor
	 * @return Sync Descriptor
	 */
	public SyncDescriptor getSyncDescriptorBasedOnPath(String syncDescriptorPath) {
		return this.syncDescriptorsBasedOnPath.get(syncDescriptorPath);
	}
	
	/**
	 * Get sync descriptor based on name
	 * @param syncDescriptorName Name of sync descriptor
	 * @return Sync Descriptor
	 */
	public SyncDescriptor getSyncDescriptorBasedOnName(String syncDescriptorName) {
		return this.syncDescriptorsBasedOnName.get(syncDescriptorName);
	}

	/**
	 * Add sync descriptor
	 * @param syncDescriptorPath Path of sync descriptor
	 * @param syncDescriptor Sync Descriptor
	 */
	public void addSyncDescriptor(String syncDescriptorPath, SyncDescriptor syncDescriptor) {
		this.syncDescriptorsBasedOnPath.put(syncDescriptorPath, syncDescriptor);
		this.syncDescriptorsBasedOnName.put(syncDescriptor.getName(), syncDescriptor);
	}

	/**
	 * Check whether sync descriptor exists or not
	 * @param syncDescriptorName Name of sync descriptor
	 * @return (true/false) TRUE: If sync descriptor exists |  FALSE: If sync descriptor does not exists
	 */
	public boolean containSyncDescriptor(String syncDescriptorName) {
		return this.syncDescriptorsBasedOnName.containsKey(syncDescriptorName);
	}

	/**
	 * Remove sync descriptor
	 * @param syncDescriptorName Name of sync descriptor
	 */
	public void removeSyncDescriptor(String syncDescriptorName) {
		this.syncDescriptorsBasedOnName.remove(syncDescriptorName);
	}

	/**
	 * Get notification descriptor
	 * @return Notification Descriptor
	 */
	public NotificationDescriptor getNotificationDescriptor() {
		return this.notificationDescriptor;
	}

	/**
	 * Set notification descriptor 
	 * @param notificationDescriptor Notification Descriptor
	 */
	public void setNotificationDescriptor(NotificationDescriptor notificationDescriptor) {
		this.notificationDescriptor = notificationDescriptor;
	}
}
