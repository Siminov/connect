package siminov.connect.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import siminov.connect.Constants;
import siminov.orm.database.Database;

public class Service extends Database implements Constants {
	
	private long id;
	
	private String service = null;
	private String api = null;
	
	private Map<String, ServiceResource> serviceResources = new HashMap<String, ServiceResource>();
	
	public long getId() {
		return this.id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getService() {
		return this.service;
	}
	
	public void setService(String service) {
		this.service = service;
	}
	
	public String getApi() {
		return this.api;
	}
	
	public void setApi(String api) {
		this.api = api;
	}
	
	public Iterator<ServiceResource> getServiceResources() {
		return this.serviceResources.values().iterator();
	}
	
	public ServiceResource getServiceResource(String name) {
		return this.serviceResources.get(name);
	}
	
	public void addServiceResource(ServiceResource serviceResource) {
		this.serviceResources.put(serviceResource.getName(), serviceResource);
	}
	
	public boolean containServiceResource(String name) {
		return this.serviceResources.containsValue(name);
	}
}
