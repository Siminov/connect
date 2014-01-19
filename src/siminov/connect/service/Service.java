package siminov.connect.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import siminov.connect.model.ServiceDescriptor;
import siminov.orm.exception.SiminovException;
import siminov.orm.log.Log;

public abstract class Service implements IService {

	private long requestId;
	
	private String service = null;
	private String api = null;

	private Map<String, String> inlineResources = new HashMap<String, String>();
	
	private ServiceDescriptor serviceDescriptor = null;

	public Service() {
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
	
	public void setService(final String service) {
		this.service = service;
	}
	
	public String getApi() {
		return this.api;
	}
	
	public void setApi(final String api) {
		this.api = api;
	}
	
	public Iterator<String> getInlineResources() {
		return this.inlineResources.keySet().iterator();
	}
	
	public String getInlineResource(final String name) {
		return this.inlineResources.get(name);
	}

	public void addInlineResource(final String name, final String value) {
		this.inlineResources.put(name, value);
	}
	
	public boolean containInlineResource(final String name) {
		return this.inlineResources.containsKey(name);
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
