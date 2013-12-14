package siminov.connect.service;

import java.util.Map;

import siminov.connect.events.IServiceEvents;
import siminov.connect.model.ServiceDescriptor;

public interface IService extends IServiceEvents {

	public String getServiceId();

	public void setServiceId(final String serviceId);
	
	public String getServiceName();
	
	public void setServiceName(final String serviceName);
	
	public String getAPIName();
	
	public void setAPIName(final String apiName);
	
	public Map<String, String> getResources();
	
	public String getResource(final String key);

	public void addResource(final String key, final String value);
	
	public boolean containResource(final String key);
	
	public ServiceDescriptor getServiceDescriptor();
	
	public void setServiceDescriptor(final ServiceDescriptor serviceDescriptor);
	
}
