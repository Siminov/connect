package siminov.connect.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import siminov.connect.Constants;
import siminov.orm.model.IDescriptor;

public class RefreshDescriptor implements IDescriptor {

	private Map<String, String> properties = new HashMap<String, String> ();
	
	private Collection<String> services = new ArrayList<String>();
	
	public String getName() {
		return this.properties.get(Constants.REFRESH_DESCRIPTOR_NAME);
	}
	
	public void setName(String name) {
		this.properties.get(name);
	}
	
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
	
	public Iterator<String> getServices() {
		return this.services.iterator();
	}
	
	public void addService(String service) {
		this.services.add(service);
	}
	
	public void removeService(String service) {
		this.services.remove(service);
	}
}
