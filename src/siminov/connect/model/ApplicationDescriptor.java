package siminov.connect.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ApplicationDescriptor extends siminov.orm.model.ApplicationDescriptor {

	private Collection<String> serviceDescriptorPaths = new ConcurrentLinkedQueue<String> ();
	private Map<String, String> serviceDescriptorNamesBasedOnPath = new HashMap<String, String>();

	private Map<String, SyncDescriptor> syncDescriptors = new HashMap<String, SyncDescriptor>();

	private AuthenticationDescriptor authenticationDescriptor = null;
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

	public Iterator<SyncDescriptor> getSyncDescriptors() {
		return this.syncDescriptors.values().iterator();
	}

	public SyncDescriptor getSyncDescriptor(String syncDescriptorName) {
		return this.syncDescriptors.get(syncDescriptorName);
	}

	public void addSyncDescriptor(SyncDescriptor syncDescriptor) {
		this.syncDescriptors.put(syncDescriptor.getName(), syncDescriptor);
	}

	public boolean containSyncDescriptor(String syncDescriptorName) {
		return this.syncDescriptors.containsKey(syncDescriptorName);
	}

	public void removeSyncDescriptors(String syncDescriptorName) {
		this.syncDescriptors.remove(syncDescriptorName);
	}

	public AuthenticationDescriptor getAuthenticationDescriptor() {
		return this.authenticationDescriptor;
	}

	public void setAuthenticationDescriptor(AuthenticationDescriptor authenticationDescriptor) {
		this.authenticationDescriptor = authenticationDescriptor;
	}

	public boolean containAuthenticationDescriptor() {

		if(this.authenticationDescriptor != null) {
			return true;
		}

		return false;
	}

	public NotificationDescriptor getNotificationDescriptor() {
		return this.notificationDescriptor;
	}

	public void setNotificationDescriptor(NotificationDescriptor notificationDescriptor) {
		this.notificationDescriptor = notificationDescriptor;
	}
}
