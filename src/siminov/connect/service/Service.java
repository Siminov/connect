package siminov.connect.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import siminov.connect.exception.ServiceException;
import siminov.connect.model.ServiceDescriptor;
import siminov.connect.service.design.IService;
import siminov.orm.log.Log;

public abstract class Service implements IService {

	private long requestId;
	
	private String service = null;
	private String api = null;

	private Map<String, NameValuePair> resources = new HashMap<String, NameValuePair>();
	
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
	
	public Iterator<NameValuePair> getResources() {
		return this.resources.values().iterator();
	}
	
	public Object getResource(final String name) {
		return this.resources.get(name).getValue();
	}

	public void addResource(final NameValuePair nameValuePair) {
		this.resources.put(nameValuePair.getName(), nameValuePair);
	}
	
	public boolean containResource(final NameValuePair nameValuePair) {
		return this.resources.containsKey(nameValuePair.getName());
	}

	public ServiceDescriptor getServiceDescriptor() {
		return this.serviceDescriptor;
	}
	
	public void setServiceDescriptor(final ServiceDescriptor serviceDescriptor) {
		this.serviceDescriptor = serviceDescriptor;
	}
	
	public void invoke() {

		this.onStart();
		
		ServiceHandler serviceHandler = ServiceHandler.getInstance();
		try {
			serviceHandler.handle(this);
		} catch(ServiceException se) {
			Log.error(Service.class.getName(), "invoke", "ServiceException caught while invoking service, " + se.getMessage());
			
			this.onTerminate(se);
		}
	}
	
	public void terminate() {
		
	}
}
