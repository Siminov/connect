package siminov.connect.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import siminov.connect.events.IServiceEvents;
import siminov.connect.model.ServiceDescriptor;
import siminov.connect.model.ServiceDescriptor.API;
import siminov.orm.exception.SiminovException;
import siminov.orm.log.Log;

public abstract class Service implements IServiceEvents {

	private String serviceName = null;
	private String apiName = null;
	
	
	private Map<String, String> inlineParameters = new HashMap<String, String>();
	
	public String getServiceName() {
		return this.serviceName;
	}
	
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	
	public String getAPIName() {
		return this.apiName;
	}
	
	public void setAPIName(String apiName) {
		this.apiName = apiName;
	}
	
	public Iterator<String> getInlineParameters() {
		return this.inlineParameters.values().iterator();
	}
	
	public String getInlineParameter(String key) {
		return this.inlineParameters.get(key);
	}
	
	public void invoke() {
		
		ServiceHandler serviceHandler = new ServiceHandler();
		try {
			serviceHandler.handle(this);
		} catch(SiminovException siminovException) {
			Log.loge(Service.class.getName(), "invoke", "Siminov Exception caught while processing service request, " + siminovException.getMessage());
			
			
		}
	}
	

	
	/**
	 * Service Events
	 */
	
	public abstract void onServiceStarted(final ServiceDescriptor serviceDescriptor);
	
	
	public abstract void onServiceQueued(final ServiceDescriptor serviceDescriptor);
	
	
	public void onServiceStopped(final ServiceDescriptor serviceDescriptor) {
		
	}
	
	
	public void onServiceApiInvoked(final ServiceDescriptor serviceDescriptor, final API api) {
		
	}
	
	
	public void onServiceApiFinished(final ServiceDescriptor serviceDescriptor, final API api) {
		
	}
	

	public void onServiceTerminated(final ServiceDescriptor serviceDescriptor) {
		
	}
}
