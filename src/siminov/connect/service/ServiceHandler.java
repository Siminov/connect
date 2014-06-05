package siminov.connect.service;

import java.util.Iterator;

import siminov.connect.Constants;
import siminov.connect.exception.ServiceException;
import siminov.connect.model.ServiceDescriptor;
import siminov.connect.model.ServiceDescriptor.API;
import siminov.connect.resource.ResourceUtils;
import siminov.connect.resource.Resources;
import siminov.connect.resource.ServiceResourceUtils;
import siminov.connect.service.design.IService;
import siminov.connect.service.design.IServiceWorker;


public class ServiceHandler {

	private static ServiceHandler serviceHandler = null;
	
	private IServiceWorker syncServiceWorker = null;
	private IServiceWorker asyncServiceWorker = null;
	
	private Resources resources = null;
	
	private ServiceHandler() {
		
		syncServiceWorker =  new SyncServiceWorker();
		asyncServiceWorker = AsyncServiceWorker.getInstance();
		
		resources = Resources.getInstance();
	}
	
	public static ServiceHandler getInstance() {
		
		if(serviceHandler == null) {
			serviceHandler = new ServiceHandler();
		}
		
		return serviceHandler;
	}
	
	public void handle(final IService service) throws ServiceException {

		ServiceDescriptor serviceDescriptor = service.getServiceDescriptor();
		if(serviceDescriptor == null) {
			serviceDescriptor = resources.requiredServiceDescriptorBasedOnName(service.getService());
			service.setServiceDescriptor(serviceDescriptor);
		}


		Iterator<NameValuePair> resources = service.getResources();
		while(resources.hasNext()) {
			NameValuePair resource = resources.next();
			Object resourceValue = resource.getValue();
			
			if(resourceValue instanceof String) {
				serviceDescriptor.addProperty(resource.getName(), (String) resourceValue);
			}
		}

		
		API api = serviceDescriptor.getApi(service.getApi());
		String mode = ResourceUtils.resolve(api.getMode(), serviceDescriptor);
		
		if(mode.equalsIgnoreCase(Constants.SERVICE_DESCRIPTOR_API_SYNC_REQUEST_MODE)) {

			ServiceResourceUtils.resolve(service);
			syncServiceWorker.process(service);
		} else if(mode.equalsIgnoreCase(Constants.SERVICE_DESCRIPTOR_API_ASYNC_REQUEST_MODE)) {
			asyncServiceWorker.process(service);
		}
	}
}
