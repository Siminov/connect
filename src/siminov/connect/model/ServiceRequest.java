package siminov.connect.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import siminov.orm.database.Database;

public class ServiceRequest extends Database {

	private long requestId;
	
	private String service = null;
	private String api = null;
	
	private String instanceOf = null;
	
	private Map<String, ServiceRequestResource> serviceRequestResources = new HashMap<String, ServiceRequestResource>();
	
	public ServiceRequest() {
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
	
	public Iterator<ServiceRequestResource> getServiceRequestResources() {
		return this.serviceRequestResources.values().iterator();
	}
	
	public ServiceRequestResource getServiceRequestResource(String name) {
		return this.serviceRequestResources.get(name);
	}
	
	public void addServiceRequestResource(ServiceRequestResource serviceRequestResource) {
		this.serviceRequestResources.put(serviceRequestResource.getName(), serviceRequestResource);
	}

	public void setServiceRequestResources(Iterator<ServiceRequestResource> serviceRequestResources) {
		
		while(serviceRequestResources.hasNext()) {
			ServiceRequestResource requestResource = serviceRequestResources.next();
			this.serviceRequestResources.put(requestResource.getName(), requestResource);
		}
	}
	
	public boolean containServiceRequestResource(String name) {
		return this.serviceRequestResources.containsValue(name);
	}
}
