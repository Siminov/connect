package siminov.connect.service;

import java.util.Iterator;

import siminov.connect.events.IServiceEvents;
import siminov.connect.model.ServiceDescriptor;

public interface IService extends IServiceEvents {

	public String getService();
	
	public void setService(final String service);
	
	public String getApi();
	
	public void setApi(final String api);
	
	public Iterator<String> getResources();
	
	public String getResource(final String name);

	public void addResource(final String name, final String value);
	
	public boolean containResource(final String name);
	
	public ServiceDescriptor getServiceDescriptor();
	
	public void setServiceDescriptor(final ServiceDescriptor serviceDescriptor);
	
}
