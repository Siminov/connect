package siminov.connect.resource;

import siminov.connect.model.ConnectDescriptor;
import siminov.connect.model.ServiceDescriptor;
import siminov.connect.model.ServiceDescriptor.API;
import siminov.connect.reader.QuickServiceDescriptorReader;
import siminov.connect.reader.ServiceDescriptorReader;
import siminov.orm.exception.SiminovCriticalException;
import siminov.orm.exception.SiminovException;
import siminov.orm.log.Log;

public class Resources {

	private static Resources resources = null;
	
	private ConnectDescriptor connectDescriptor = null;
	
	private Resources() {
		
	}
	
	public static Resources getInstance() {
		
		if(resources == null) {
			resources = new Resources();
		}
		
		return resources;
	}

	public ConnectDescriptor getConnectDescriptor() {
		return this.connectDescriptor;
	}
	
	public void setConnectDescriptor(ConnectDescriptor connectDescriptor) {
		this.connectDescriptor = connectDescriptor;
	}
	
	public ServiceDescriptor requiredServiceDescriptorBasedOnPath(String serviceDescriptorPath) {
		
		ServiceDescriptorReader serviceDescriptorReader = new ServiceDescriptorReader(serviceDescriptorPath);
		ServiceDescriptor serviceDescriptor = serviceDescriptorReader.getServiceDescriptor();
		
		connectDescriptor.addServiceDescriptorNameBasedOnPath(serviceDescriptorPath, serviceDescriptor.getName());
		
		return serviceDescriptor;
	}
	
	public ServiceDescriptor requiredServiceDescriptorBasedOnName(String serviceDescriptorName) {
		
		if(!connectDescriptor.containServiceDescriptorPathBasedOnName(serviceDescriptorName)) {

			QuickServiceDescriptorReader quickServiceDescriptorReader;
			try {
				quickServiceDescriptorReader = new QuickServiceDescriptorReader(serviceDescriptorName);
				quickServiceDescriptorReader.process();
			} catch(SiminovException siminovException) {
				Log.loge(Resources.class.getName(), "requiredServiceDescriptorBasedOnName", "Siminov Exception caught while getting quick service descriptor based on name, " + serviceDescriptorName);
				throw new SiminovCriticalException(Resources.class.getName(), "requiredServiceDescriptorBasedOnName", siminovException.getMessage());
			}
			
			
			if(quickServiceDescriptorReader.containServiceDescriptor()) {
				return quickServiceDescriptorReader.getServiceDescriptor();
			}
		}
		
		
		String serviceDescriptorPath = connectDescriptor.getServiceDescriptorPathBasedOnName(serviceDescriptorName);
		return requiredServiceDescriptorBasedOnPath(serviceDescriptorPath);
	}

		
	public API requiredAPIBasedOnServiceDescriptorPath(String serviceDescriptorPath, String apiName) {
		
		ServiceDescriptor serviceDescriptor = this.requiredServiceDescriptorBasedOnPath(serviceDescriptorPath);
		return serviceDescriptor.getApi(apiName);
	}
	
	public API requireAPIBasedOnServiceDescriptorName(String serviceDescriptorName, String apiName) {
		
		ServiceDescriptor serviceDescriptor = this.requiredServiceDescriptorBasedOnName(serviceDescriptorName);
		return serviceDescriptor.getApi(apiName);
	}
}
