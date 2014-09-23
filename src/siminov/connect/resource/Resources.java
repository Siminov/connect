package siminov.connect.resource;

import java.util.Iterator;

import siminov.connect.events.EventHandler;
import siminov.connect.events.INotificationEvents;
import siminov.connect.events.ISyncEvents;
import siminov.connect.model.ApplicationDescriptor;
import siminov.connect.model.ServiceDescriptor;
import siminov.connect.model.ServiceDescriptor.API;
import siminov.connect.model.SyncDescriptor;
import siminov.connect.reader.QuickServiceDescriptorReader;
import siminov.connect.reader.ServiceDescriptorReader;
import siminov.orm.exception.SiminovCriticalException;
import siminov.orm.exception.SiminovException;
import siminov.orm.log.Log;

public class Resources {

	private static Resources resources = null;
	
	private ApplicationDescriptor applicationDescriptor = null;
	
	private Resources() {
		
	}
	
	public static Resources getInstance() {
		
		if(resources == null) {
			resources = new Resources();
		}
		
		return resources;
	}

	public ApplicationDescriptor getApplicationDescriptor() {
		return this.applicationDescriptor;
	}
	
	public void setApplicationDescriptor(final ApplicationDescriptor applicationDescriptor) {
		this.applicationDescriptor = applicationDescriptor;
	}
	
	public ServiceDescriptor requiredServiceDescriptorBasedOnPath(final String serviceDescriptorPath) {
		
		ServiceDescriptorReader serviceDescriptorReader = new ServiceDescriptorReader(serviceDescriptorPath);
		ServiceDescriptor serviceDescriptor = serviceDescriptorReader.getServiceDescriptor();
		
		applicationDescriptor.addServiceDescriptorNameBasedOnPath(serviceDescriptorPath, serviceDescriptor.getName());
		
		return serviceDescriptor;
	}

	public ServiceDescriptor requiredServiceDescriptorBasedOnName(final String serviceDescriptorName) {
		
		if(!applicationDescriptor.containServiceDescriptorPathBasedOnName(serviceDescriptorName)) {

			QuickServiceDescriptorReader quickServiceDescriptorReader;
			try {
				quickServiceDescriptorReader = new QuickServiceDescriptorReader(serviceDescriptorName);
				quickServiceDescriptorReader.process();
			} catch(SiminovException siminovException) {
				Log.error(Resources.class.getName(), "requiredServiceDescriptorBasedOnName", "Siminov Exception caught while getting quick service descriptor based on name, " + serviceDescriptorName);
				throw new SiminovCriticalException(Resources.class.getName(), "requiredServiceDescriptorBasedOnName", siminovException.getMessage());
			}
			
			
			if(quickServiceDescriptorReader.containServiceDescriptor()) {
				return quickServiceDescriptorReader.getServiceDescriptor();
			}
		}
		
		
		String serviceDescriptorPath = applicationDescriptor.getServiceDescriptorPathBasedOnName(serviceDescriptorName);
		return requiredServiceDescriptorBasedOnPath(serviceDescriptorPath);
	}

		
	
	public API requiredAPIBasedOnServiceDescriptorPath(final String serviceDescriptorPath, final String apiName) {
		
		ServiceDescriptor serviceDescriptor = this.requiredServiceDescriptorBasedOnPath(serviceDescriptorPath);
		return serviceDescriptor.getApi(apiName);
	}
	
	public API requireAPIBasedOnServiceDescriptorName(final String serviceDescriptorName, final String apiName) {
		
		ServiceDescriptor serviceDescriptor = this.requiredServiceDescriptorBasedOnName(serviceDescriptorName);
		return serviceDescriptor.getApi(apiName);
	}
	
	public INotificationEvents getNotificationEventHandler() {
		return EventHandler.getInstance().getNotificationEventHandler();
	}
	
	public ISyncEvents getSyncEventHandler() {
		return EventHandler.getInstance().getSyncEventHandler();
	}
	
	public Iterator<SyncDescriptor> getSyncDescriptors() {
		
		ApplicationDescriptor applicationDescriptor = getApplicationDescriptor();
		return applicationDescriptor.getSyncDescriptors();
	}
	
	public SyncDescriptor getSyncDescriptor(final String syncDescriptorName) {
		
		ApplicationDescriptor applicationDescriptor = getApplicationDescriptor();
		return applicationDescriptor.getSyncDescriptorBasedOnName(syncDescriptorName);
	}
}
