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
 * Exposes methods to GET and SET Library Descriptor information as per define in LibraryDescriptor.xml file by application.
	<p>
		<pre>
		
Example:
	{@code

	<library-descriptor>
	
	    <!-- General Properties Of Library -->
	    
	    <!-- Mandatory Field -->
		<property name="name">name_of_library</property>
		
		<!-- Optional Field -->
		<property name="description">description_of_library</property>
	
		
		
		<!-- Entity Descriptors Needed Under This Library Descriptor -->
		
		<!-- Optional Field -->
			<!-- Entity Descriptor -->
		<entity-descriptors>
			<entity-descriptor>name_of_database_descriptor.full_path_of_entity_descriptor_file</entity-descriptor>
		</entity-descriptors>
		 
		
		<!-- Service Descriptors -->
			
		<!-- Optional Field -->
			<!-- Service Descriptor -->
		<service-descriptors>
		    <service-descriptor>full_path_of_service-descriptor_file</service-descriptor>
		</service-descriptors>
		
		
		<!-- Sync Descriptors -->
		
		<!-- Optional Field -->
			<!-- Sync Descriptor -->
		<sync-descriptors>
		    <sync-descriptor>full_path_of_sync_descriptor_file</sync-descriptor>
		</sync-descriptors>
		
		
	</library-descriptor>
	}
	
		</pre>
	</p>
 *
 */
public class LibraryDescriptor extends siminov.core.model.LibraryDescriptor {

	private Collection<String> serviceDescriptorPaths = new ConcurrentLinkedQueue<String> ();
	private Map<String, String> serviceDescriptorNamesBasedOnPath = new HashMap<String, String>();
	
	private Collection<String> syncDescriptorPaths = new ConcurrentLinkedQueue<String> ();
	private Map<String, String> syncDescriptorNamesBasedOnPath = new HashMap<String, String>();

	/**
	 * Add service descriptor path
	 * @param serviceDescriptorPath Path of service descriptor
	 */
	public void addServiceDescriptorPath(String serviceDescriptorPath) {
		this.serviceDescriptorPaths.add(serviceDescriptorPath);
		this.serviceDescriptorNamesBasedOnPath.put(serviceDescriptorPath, null);
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
	 * @return Service descriptor paths
	 */
	public Iterator<String> getServiceDescriptorPaths() {
		return this.serviceDescriptorPaths.iterator();
	}

	/**
	 * Check whether service descriptor path based on name
	 * @param serviceDescriptorName Name of service descriptor
	 * @return (true/false) TRUE: If service descriptor path exists | FALSE: If service descriptor does not exists
	 */
	public boolean containServiceDescriptorPathBasedOnName(String serviceDescriptorName) {
		return this.serviceDescriptorNamesBasedOnPath.containsValue(serviceDescriptorName);
	}
	
	/**
	 * Check whether service descriptor name exists based on path
	 * @param serviceDescriptorPath Path of service descriptor
	 * @return (true/false) TRUE: If service descriptor name exists | FALSE: If service descriptor name does not exists
	 */
	public boolean containServiceDescriptorNameBasedOnPath(String serviceDescriptorPath) {
		return this.serviceDescriptorNamesBasedOnPath.containsKey(serviceDescriptorPath);
	}
	
	/**
	 * Get service descriptor path based on name
	 * @param serviceDescriptorName Name of serivce descriptor
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
	 * Add sync descriptor path
	 * @param syncDescriptorPath Path of sync descriptor
	 */
	public void addSyncDescriptorPath(String syncDescriptorPath) {
		this.syncDescriptorPaths.add(syncDescriptorPath);
		this.syncDescriptorNamesBasedOnPath.put(syncDescriptorPath, null);
	}
	
	/**
	 * Remove sync descriptor path
	 * @param syncDescriptorPath Path of sync descriptor
	 */
	public void removeSyncDescriptorPath(String syncDescriptorPath) {
		this.serviceDescriptorPaths.remove(syncDescriptorPath);
	}
	
	/**
	 * Get all sync descriptor paths
	 * @return Sync descriptor paths
	 */
	public Iterator<String> getSyncDescriptorPaths() {
		return this.syncDescriptorPaths.iterator();
	}

	/**
	 * Check whether sync descriptor path based on name
	 * @param syncDescriptorName Name of sync descriptor
	 * @return (true/false) TRUE: If sync descriptor path exists | FALSE: If sync descriptor does not exists
	 */
	public boolean containSyncDescriptorPathBasedOnName(String syncDescriptorName) {
		return this.serviceDescriptorNamesBasedOnPath.containsValue(syncDescriptorName);
	}
	
	/**
	 * Check whether sync descriptor name exists based on path
	 * @param syncDescriptorPath Path of sync descriptor
	 * @return (true/false) TRUE: If sync descriptor name exists | FALSE: If sync descriptor name does not exists
	 */
	public boolean containSyncDescriptorNameBasedOnPath(String syncDescriptorPath) {
		return this.syncDescriptorNamesBasedOnPath.containsKey(syncDescriptorPath);
	}
	
	/**
	 * Get sync descriptor path based on name
	 * @param syncDescriptorName Name of sync descriptor
	 * @return Path of sync descriptor
	 */
	public String getSyncDescriptorPathBasedOnName(String syncDescriptorName) {
		
		if(this.containSyncDescriptorPathBasedOnName(syncDescriptorName)) {
			
			Iterator<String> syncDescriptorPaths = this.syncDescriptorNamesBasedOnPath.keySet().iterator();
			while(syncDescriptorPaths.hasNext()) {
				
				String syncDescriptorPath = syncDescriptorPaths.next();
				
				String foundSyncDescriptorName = this.syncDescriptorNamesBasedOnPath.get(syncDescriptorPath);
				if(foundSyncDescriptorName.equalsIgnoreCase(syncDescriptorName)) {
					return this.syncDescriptorNamesBasedOnPath.get(syncDescriptorPath);
				}
			}
		}
		
		return null;
	}
	
	/**
	 * Add sync descriptor name based on path
	 * @param syncDescriptorPath Path of sync descriptor
	 * @param syncDescriptoName Name of sync descriptor
	 */
	public void addSyncDescriptorNameBasedOnPath(String syncDescriptorPath, String syncDescriptoName) {
		this.syncDescriptorNamesBasedOnPath.put(syncDescriptorPath, syncDescriptoName);
	}
	
	
	/**
	 * Remove sync descriptor name based on path
	 * @param syncDescriptorPath Path of sync descriptor
	 */
	public void removeSyncDescriptorNameBasedOnPath(String syncDescriptorPath) {
		this.syncDescriptorNamesBasedOnPath.remove(syncDescriptorPath);
	}
}
