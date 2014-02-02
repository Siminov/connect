package siminov.connect.service;

import java.util.Iterator;

import siminov.connect.Constants;
import siminov.connect.connection.ConnectionRequest;
import siminov.connect.connection.ConnectionResponse;
import siminov.connect.connection.IConnection;
import siminov.connect.model.ServiceDescriptor;
import siminov.connect.model.ServiceDescriptor.API;
import siminov.connect.resource.Resources;
import siminov.connect.service.design.IService;
import siminov.connect.service.design.IServiceWorker;
import siminov.connect.service.worker.AsyncServiceWorker;
import siminov.connect.service.worker.SyncServiceWorker;
import siminov.connect.utils.ResourceUtils;
import siminov.connect.utils.ServiceResourceUtils;
import siminov.orm.exception.SiminovException;


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
	
	public void handle(final IService service) throws SiminovException {

		ServiceDescriptor serviceDescriptor = service.getServiceDescriptor();
		if(serviceDescriptor == null) {
			serviceDescriptor = resources.requiredServiceDescriptorBasedOnName(service.getService());
			service.setServiceDescriptor(serviceDescriptor);
		}


		Iterator<String> inlineResources = service.getInlineResources();
		while(inlineResources.hasNext()) {
			String inlineResource = inlineResources.next();
			serviceDescriptor.addProperty(inlineResource, service.getInlineResource(inlineResource));
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

	public ConnectionResponse invokeConnection(final ConnectionRequest connectionRequest) throws SiminovException {
		
		IConnection connection = null;
		if(connectionRequest.getProtocol().equalsIgnoreCase(Constants.SERVICE_DESCRIPTOR_HTTP_PROTOCOL)) {
			connection = new siminov.connect.connection.http.Connection();
		} else if(connectionRequest.getProtocol().equalsIgnoreCase(Constants.SERVICE_DESCRIPTOR_HTTPS_PROTOCOL)) {
			connection = new siminov.connect.connection.https.Connection();
		}

		
		ConnectionResponse connectionResponse = null;
		if(connectionRequest.getType().equalsIgnoreCase(Constants.SERVICE_CONNECTION_API_GET_TYPE)) {
			connectionResponse = connection.get(connectionRequest);
		} else if(connectionRequest.getType().equalsIgnoreCase(Constants.SERVICE_CONNECTION_API_HEAD_TYPE)) {
			connectionResponse = connection.head(connectionRequest);
		} else if(connectionRequest.getType().equalsIgnoreCase(Constants.SERVICE_CONNECTION_API_POST_TYPE)) {
			connectionResponse = connection.post(connectionRequest);
		} else if(connectionRequest.getType().equalsIgnoreCase(Constants.SERVICE_CONNECTION_API_PUT_TYPE)) {
			connectionResponse = connection.put(connectionRequest);
		} else if(connectionRequest.getType().equalsIgnoreCase(Constants.SERVICE_CONNECTION_API_DELETE_TYPE)) {
			connectionResponse = connection.delete(connectionRequest);
		} else if(connectionRequest.getType().equalsIgnoreCase(Constants.SERVICE_CONNECTION_API_TRACE_TYPE)) {
			connectionResponse = connection.trace(connectionRequest);
		} else if(connectionRequest.getType().equalsIgnoreCase(Constants.SERVICE_CONNECTION_API_OPTIONS_TYPE)) {
			connectionResponse = connection.options(connectionRequest);
		} else if(connectionRequest.getType().equalsIgnoreCase(Constants.SERVICE_CONNECTION_API_CONNECT_TYPE)) {
			connectionResponse = connection.connect(connectionRequest);
		} else if(connectionRequest.getType().equalsIgnoreCase(Constants.SERVICE_CONNECTION_API_PATCH_TYPE)) {
			connectionResponse = connection.patch(connectionRequest);
		}

		
		return connectionResponse;
	}
}
