package siminov.connect.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import siminov.connect.design.service.IService;
import siminov.connect.exception.ServiceException;
import siminov.connect.model.ServiceDescriptor;
import siminov.orm.log.Log;

public abstract class Service implements IService {

	private long requestId;
	
	private String service = null;
	private String api = null;

	private Map<String, Object> resources = new HashMap<String, Object>();
	
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
	
	public Iterator<String> getResources() {
		return this.resources.keySet().iterator();
	}
	
	public Object getResource(final String name) {
		return this.resources.get(name);
	}

	public void addResource(final String name, final Object value) {
		this.resources.put(name, value);
	}
	
	public boolean containResource(final String name) {
		return this.resources.containsKey(name);
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
		} catch(ServiceException se) {
			Log.loge(Service.class.getName(), "invoke", "ServiceException caught while invoking service, " + se.getMessage());
			
			this.onServiceTerminate(se);
		}
	}
}
