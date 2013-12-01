package siminov.connect.service;

import java.util.HashMap;
import java.util.Map;

import siminov.connect.events.IServiceEvents;

public abstract class Service implements IServiceEvents {

	private String serviceName = null;
	private String apiName = null;
	
	
	private Map<String, String> resources = new HashMap<String, String>();
	
	
	public Service() {
		
	}
	
	public String getServiceName() {
		return this.serviceName;
	}
	
	public void setServiceName(final String serviceName) {
		this.serviceName = serviceName;
	}
	
	public String getAPIName() {
		return this.apiName;
	}
	
	public void setAPIName(final String apiName) {
		this.apiName = apiName;
	}
	
	public Map<String, String> getResources() {
		return this.resources;
	}
	
	public String getResource(final String key) {
		return this.resources.get(key);
	}

	public void addResource(final String key, final String value) {
		this.resources.put(key, value);
	}
	
	public boolean containResource(final String key) {
		return this.resources.containsKey(key);
	}
	
	public void invoke() {
		
		ServiceHandler serviceHandler = new ServiceHandler();
		serviceHandler.handle(this);
	}
}
