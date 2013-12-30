package siminov.connect.service.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import siminov.orm.database.Database;

public class Request extends Database {

	private long requestId;
	
	private String service = null;
	private String api = null;
	
	private String instanceOf = null;
	
	private Map<String, RequestResource> serviceResources = new HashMap<String, RequestResource>();
	
	public Request() {
		requestId = new Random().nextLong();
	}
	
	public long getRequestId() {
		return this.requestId;
	}
	
	public void setRequestId(long request) {
		this.requestId = request;
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
	
	public String getInstanceOf() {
		return this.instanceOf;
	}
	
	public void setInstanceOf(String instanceOf) {
		this.instanceOf = instanceOf;
	}
	
	public Iterator<RequestResource> getServiceResources() {
		return this.serviceResources.values().iterator();
	}
	
	public RequestResource getServiceResource(String name) {
		return this.serviceResources.get(name);
	}
	
	public void addServiceResource(RequestResource serviceResource) {
		this.serviceResources.put(serviceResource.getName(), serviceResource);
	}

	public void setServiceResources(Iterator<RequestResource> serviceResources) {
		
		while(serviceResources.hasNext()) {
			RequestResource serviceResource = serviceResources.next();
			this.serviceResources.put(serviceResource.getName(), serviceResource);
		}
	}
	
	public boolean containServiceResource(String name) {
		return this.serviceResources.containsValue(name);
	}
}
