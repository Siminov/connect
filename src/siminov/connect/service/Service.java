package siminov.connect.service;

import java.util.HashMap;
import java.util.Map;

import siminov.connect.events.IServiceEvents;
import siminov.orm.exception.SiminovException;

public abstract class Service implements IServiceEvents {

	private String serviceName = null;
	private String apiName = null;
	
	
	private Map<String, String> inlineResources = new HashMap<String, String>();
	
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
	
	public Map<String, String> getInlineResources() {
		return this.inlineResources;
	}
	
	public String getInlineResource(final String key) {
		return this.inlineResources.get(key);
	}

	public void addInlineResource(final String key, final String value) {
		this.inlineResources.put(key, value);
	}
	
	public boolean containInlineResource(final String key) {
		return this.inlineResources.containsKey(key);
	}
	
	public void invoke() throws SiminovException {
		
		ServiceHandler serviceHandler = new ServiceHandler();
		serviceHandler.handle(this);
	}
}
