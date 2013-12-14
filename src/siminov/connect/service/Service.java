package siminov.connect.service;

import java.util.HashMap;
import java.util.Map;

import siminov.connect.model.ServiceDescriptor;
import siminov.orm.exception.SiminovException;
import siminov.orm.log.Log;

public abstract class Service implements IService {

	private String serviceId;
	
	private String serviceName = null;
	private String apiName = null;
	
	
	private Map<String, String> resources = new HashMap<String, String>();
	
	private ServiceDescriptor serviceDescriptor = null;
	
	
	public Service() {
		serviceId = java.util.UUID.randomUUID().toString();
	}

	public String getServiceId() {
		return this.serviceId;
	}
	
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
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

	public ServiceDescriptor getServiceDescriptor() {
		return this.serviceDescriptor;
	}
	
	public void setServiceDescriptor(final ServiceDescriptor serviceDescriptor) {
		this.serviceDescriptor = serviceDescriptor;
	}
	
	public void invoke() {
		
		ServiceHandler serviceHandler = ServiceHandler.getInstance();
		try {
			serviceHandler.handle(this);
		} catch(SiminovException se) {
			Log.loge(Service.class.getName(), "invoke", "SiminovException caught while invoking service, " + se.getMessage());
			
			this.onServiceTerminate(se);
		}
	}
}
