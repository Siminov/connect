package siminov.connect.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

import siminov.connect.Constants;

public class ConnectDescriptor implements Constants {

	private Map<String, String> properties = new HashMap<String, String> (); 

	private Collection<String> serviceDescriptorPaths = new ConcurrentLinkedQueue<String> ();
	private Map<String, String> serviceDescriptorNamesBasedOnPath = new HashMap<String, String>();
	
	private Map<String, LibraryDescriptor> librariesBasedOnPath = new HashMap<String, LibraryDescriptor>();
	private Map<String, LibraryDescriptor> librariesBasedOnName = new HashMap<String, LibraryDescriptor>();
	
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
	
	public Iterator<String> getLibraryPaths() {
		return this.librariesBasedOnPath.keySet().iterator();
	}
	
	public void addLibraryPath(final String libraryPath) {
		this.librariesBasedOnPath.put(libraryPath, null);
	}
	
	public Iterator<LibraryDescriptor> getLibraries() {
		return this.librariesBasedOnName.values().iterator();
	}
	
	public LibraryDescriptor getLibraryDescriptorBasedOnName(final String libraryName) {
		return this.librariesBasedOnName.get(libraryName);
	}
	
	public LibraryDescriptor getLibraryDescriptorBasedOnPath(final String libraryPath) {
		return this.librariesBasedOnPath.get(libraryPath);
	}
	
	public void addLibrary(final String libraryPath, final LibraryDescriptor libraryDescriptor) {
		this.librariesBasedOnPath.put(libraryPath, libraryDescriptor);
		this.librariesBasedOnName.put(libraryDescriptor.getName(), libraryDescriptor);
	}
	
	public boolean containLibraryBasedOnName(final String libraryName) {
		return this.librariesBasedOnName.containsKey(libraryName);
	}
	
	public boolean containLibraryBasedOnPath(final String libraryPath) {
		return this.librariesBasedOnPath.containsKey(libraryPath);
	}
	
}
