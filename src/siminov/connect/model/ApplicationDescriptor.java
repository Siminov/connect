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

public class ApplicationDescriptor extends siminov.orm.model.ApplicationDescriptor {

	private Collection<String> serviceDescriptorPaths = new ConcurrentLinkedQueue<String> ();
	private Map<String, String> serviceDescriptorNamesBasedOnPath = new HashMap<String, String>();

	private Collection<String> syncDescriptorPaths = new ConcurrentLinkedQueue<String>();
	
	private Map<String, SyncDescriptor> syncDescriptorsBasedOnName = new HashMap<String, SyncDescriptor>();
	private Map<String, SyncDescriptor> syncDescriptorsBasedOnPath = new HashMap<String, SyncDescriptor>();
	
	private NotificationDescriptor notificationDescriptor = null;


	public void addServiceDescriptorPath(String serviceDescriptorPath) {
		this.serviceDescriptorPaths.add(serviceDescriptorPath);
	}

	public void removeServiceDescriptorPath(String serviceDescriptorPath) {
		this.serviceDescriptorPaths.remove(serviceDescriptorPath);
	}

	public Iterator<String> getServiceDescriptorPaths() {
		return this.serviceDescriptorPaths.iterator();
	}

	public boolean containServiceDescriptorPathBasedOnName(String serviceDescriptorName) {
		return this.serviceDescriptorNamesBasedOnPath.containsValue(serviceDescriptorName);
	}

	public boolean containServiceDescriptorNameBasedOnPath(String serviceDescriptorPath) {
		return this.serviceDescriptorNamesBasedOnPath.containsKey(serviceDescriptorPath);
	}

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

	public void addServiceDescriptorNameBasedOnPath(String serviceDescriptorPath, String serviceDescriptoName) {
		this.serviceDescriptorNamesBasedOnPath.put(serviceDescriptorPath, serviceDescriptoName);
	}

	public void removeServiceDescriptorNameBasedOnPath(String serviceDescriptorPath) {
		this.serviceDescriptorNamesBasedOnPath.remove(serviceDescriptorPath);
	}

	
	public Iterator<String> getSyncDescriptorPaths() {
		return this.syncDescriptorPaths.iterator();
	}
	
	public void addSyncDescriptorPath(String syncDescriptorPath) {
		this.syncDescriptorPaths.add(syncDescriptorPath);
	}

	public void removeSyncDescriptorPath(String syncDescriptorPath) {
		this.syncDescriptorPaths.remove(syncDescriptorPath);
	}

	public boolean containSyncDescriptorPath(String syncDescriptorPath) {
		return this.syncDescriptorPaths.contains(syncDescriptorPath);
	}
	
	public Iterator<SyncDescriptor> getSyncDescriptors() {
		return this.syncDescriptorsBasedOnName.values().iterator();
	}

	public SyncDescriptor getSyncDescriptorBasedOnPath(String syncDescriptorPath) {
		return this.syncDescriptorsBasedOnPath.get(syncDescriptorPath);
	}
	
	public SyncDescriptor getSyncDescriptorBasedOnName(String syncDescriptorName) {
		return this.syncDescriptorsBasedOnName.get(syncDescriptorName);
	}

	public void addSyncDescriptor(String syncDescriptorPath, SyncDescriptor syncDescriptor) {
		this.syncDescriptorsBasedOnPath.put(syncDescriptorPath, syncDescriptor);
		this.syncDescriptorsBasedOnName.put(syncDescriptor.getName(), syncDescriptor);
	}

	public boolean containSyncDescriptor(String syncDescriptorName) {
		return this.syncDescriptorsBasedOnName.containsKey(syncDescriptorName);
	}

	public void removeSyncDescriptors(String syncDescriptorName) {
		this.syncDescriptorsBasedOnName.remove(syncDescriptorName);
	}

	public NotificationDescriptor getNotificationDescriptor() {
		return this.notificationDescriptor;
	}

	public void setNotificationDescriptor(NotificationDescriptor notificationDescriptor) {
		this.notificationDescriptor = notificationDescriptor;
	}
}
