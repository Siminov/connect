package siminov.connect.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

import siminov.orm.model.IDescriptor;

public class ConnectDescriptor implements IDescriptor {

	private Map<String, String> properties = new HashMap<String, String> (); 

	private Collection<String> serviceDescriptorPaths = new ConcurrentLinkedQueue<String> ();
	private Map<String, String> serviceDescriptorNamesBasedOnPath = new HashMap<String, String>();

	private Map<String, RefreshDescriptor> refreshDescriptors = new HashMap<String, RefreshDescriptor>();
	
	private AuthenticationDescriptor authenticationDescriptor = null;
	
	public Iterator<String> getProperties() {
		return this.properties.keySet().iterator();
	}
	
	public String getProperty(String name) {
		return this.properties.get(name);
	}

	public boolean containProperty(String name) {
		return this.properties.containsKey(name);
	}
	
	public void addProperty(String name, String value) {
		this.properties.put(name, value);
	}
	
	public void removeProperty(String name) {
		this.properties.remove(name);
	}

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
	
	public Iterator<RefreshDescriptor> getRefreshDescriptors() {
		return this.refreshDescriptors.values().iterator();
	}
	
	public RefreshDescriptor getRefreshDescriptor(String refreshDescriptorName) {
		return this.refreshDescriptors.get(refreshDescriptorName);
	}
	
	public void addRefreshDescriptor(RefreshDescriptor refreshDescriptor) {
		this.refreshDescriptors.put(refreshDescriptor.getName(), refreshDescriptor);
	}
	
	public boolean containRefreshDescriptor(String refreshDescriptorName) {
		return this.refreshDescriptors.containsKey(refreshDescriptorName);
	}
	
	public void removeRefreshDescriptors(String refreshDescriptorName) {
		this.refreshDescriptors.remove(refreshDescriptorName);
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
}
