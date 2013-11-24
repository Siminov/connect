package siminov.connect.service;

import org.apache.http.client.methods.HttpGet;

import siminov.connect.Constants;
import siminov.connect.connection.ConnectionHelper;
import siminov.connect.connection.ConnectionRequest;
import siminov.connect.connection.ConnectionResponse;
import siminov.connect.connection.IConnection;
import siminov.connect.model.ServiceDescriptor;
import siminov.connect.model.ServiceDescriptor.API;
import siminov.connect.resource.Resources;
import siminov.orm.exception.SiminovException;

public class ServiceHandler {

	private Resources resources = Resources.getInstance();
	
	public void handle(final Service service) throws SiminovException {
		
		String serviceName = service.getServiceName();
		String apiName = service.getAPIName();
		
		ServiceDescriptor serviceDescriptor = resources.requiredServiceDescriptorBasedOnName(serviceName);
		API api = serviceDescriptor.getApi(apiName);
		
		String requestMode = api.getMode();
		if(requestMode.equalsIgnoreCase(Constants.SERVICE_DESCRIPTOR_API_SYNC_REQUEST_MODE)) {
			this.handleSynchronousRequest(service);
		} else if(requestMode.equalsIgnoreCase(Constants.SERVICE_DESCRIPTOR_API_ASYNC_REQUEST_MODE)) {
			this.handleAsynchronousRequest(service);
		}
	}
	
	
	private void handleSynchronousRequest(final Service service) throws SiminovException {
		
		String serviceName = service.getServiceName();
		String apiName = service.getAPIName();
		
		ServiceDescriptor serviceDescriptor = resources.requiredServiceDescriptorBasedOnName(serviceName);
		API api = serviceDescriptor.getApi(apiName);

		ConnectionRequest connectionRequest = new ConnectionHelper().prepareConnectionRequest(service);
		
		IConnection connection = null;
		if(serviceDescriptor.getProtocol().equalsIgnoreCase(Constants.SERVICE_DESCRIPTOR_HTTP_PROTOCOL)) {
			connection = new siminov.connect.connection.http.Connection();
		} else if(serviceDescriptor.getProtocol().equalsIgnoreCase(Constants.SERVICE_DESCRIPTOR_HTTPS_PROTOCOL)) {
			connection = new siminov.connect.connection.https.Connection();
		}
		

		
		/*
		 * Invoke Service Invoke API Call
		 */
		service.onServiceApiInvoke(connectionRequest);
		
		
		ConnectionResponse connectionResponse = null;
		if(api.getType().equalsIgnoreCase(Constants.SERVICE_CONNECTION_API_GET_TYPE)) {
			connectionResponse = connection.get(connectionRequest);
		} else if(api.getType().equalsIgnoreCase(Constants.SERVICE_CONNECTION_API_HEAD_TYPE)) {
			connectionResponse = connection.head(connectionRequest);
		} else if(api.getType().equalsIgnoreCase(Constants.SERVICE_CONNECTION_API_POST_TYPE)) {
			connectionResponse = connection.post(connectionRequest);
		} else if(api.getType().equalsIgnoreCase(Constants.SERVICE_CONNECTION_API_PUT_TYPE)) {
			connectionResponse = connection.put(connectionRequest);
		} else if(api.getType().equalsIgnoreCase(Constants.SERVICE_CONNECTION_API_DELETE_TYPE)) {
			connectionResponse = connection.delete(connectionRequest);
		} else if(api.getType().equalsIgnoreCase(Constants.SERVICE_CONNECTION_API_TRACE_TYPE)) {
			connectionResponse = connection.trace(connectionRequest);
		} else if(api.getType().equalsIgnoreCase(Constants.SERVICE_CONNECTION_API_OPTIONS_TYPE)) {
			connectionResponse = connection.options(connectionRequest);
		} else if(api.getType().equalsIgnoreCase(Constants.SERVICE_CONNECTION_API_CONNECT_TYPE)) {
			connectionResponse = connection.connect(connectionRequest);
		} else if(api.getType().equalsIgnoreCase(Constants.SERVICE_CONNECTION_API_PATCH_TYPE)) {
			connectionResponse = connection.patch(connectionRequest);
		}
		

		/*
		 * Invoke Service API Finish
		 */
		service.onServiceApiFinish(connectionResponse);
		
		
		/*
		 * Invoke Service API Stop
		 */
		service.onServiceFinish();

	}
	
	
	private void handleAsynchronousRequest(final Service service) {
		
	}
	
}
