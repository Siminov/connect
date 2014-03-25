package siminov.connect.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import siminov.connect.Constants;
import siminov.orm.model.IDescriptor;


public class AuthorizationDescriptor implements IDescriptor {
	
	private Map<String, String> properties = new HashMap<String, String>();
	
	public String getType() {
		return this.properties.get(Constants.AUTHORIZATION_DESCRIPTOR_TYPE);
	}
	
	public void setType(String type) {
		this.properties.put(Constants.AUTHORIZATION_DESCRIPTOR_TYPE, type);
	}
	
	public String getProvider() {
		return this.properties.get(Constants.AUTHORIZATION_DESCRIPTOR_PROVIDER);
	}
	
	public void setProvider(String provider) {
		this.properties.put(Constants.AUTHORIZATION_DESCRIPTOR_PROVIDER, provider);
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
}
