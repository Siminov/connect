package siminov.connect.connection;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import siminov.connect.Constants;
import siminov.connect.model.ServiceDescriptor;
import siminov.connect.model.ServiceDescriptor.API;
import siminov.connect.model.ServiceDescriptor.API.HeaderParameter;
import siminov.connect.model.ServiceDescriptor.API.QueryParameter;
import siminov.connect.resource.Resources;
import siminov.connect.service.Service;
import siminov.connect.utils.ServiceResourceUtils;
import siminov.orm.exception.SiminovException;


public class ConnectionHelper {

	private Resources resources = Resources.getInstance();

	public ConnectionRequest prepareConnectionRequest(final Service service) throws SiminovException {
		
		String url = formUrl(service);
		
		Map<String, String> queryParameters = formQueryParameters(service);
		Map<String, String> headerParameters = formHeaderParameters(service);
		
		byte[] dataStream = formDataStream(service);
		
		ConnectionRequest connectionRequest = new ConnectionRequest();
		connectionRequest.setUrl(url);
		connectionRequest.setQueryParameters(queryParameters);
		connectionRequest.setHeaderParameters(headerParameters);
		connectionRequest.setDataStream(dataStream);
		
		return connectionRequest;
	}


	private String formUrl(final Service service) throws SiminovException {
		
		String serviceName = service.getServiceName();
		String apiName = service.getAPIName();
		
		ServiceDescriptor serviceDescriptor = resources.requiredServiceDescriptorBasedOnName(serviceName);
		API api = serviceDescriptor.getApi(apiName);
		
		Map<String, String> serviceResources = service.getResources();
		
		String protocol = serviceDescriptor.getProtocol();
		
		String instance = serviceDescriptor.getInstance();
		String port = serviceDescriptor.getPort();
		
		String context = serviceDescriptor.getContext();
		String apiPath = api.getApi();
		

		protocol = (String) ServiceResourceUtils.resolve(protocol, serviceResources);
		
		instance = (String) ServiceResourceUtils.resolve(instance, serviceResources);
		port = (String) ServiceResourceUtils.resolve(port, serviceResources);
		
		context = (String) ServiceResourceUtils.resolve(context, serviceResources);
		apiPath = (String) ServiceResourceUtils.resolve(apiPath, serviceResources);

		
		StringBuilder url = new StringBuilder();

			if(protocol != null) {

				if(protocol.equalsIgnoreCase(Constants.SERVICE_DESCRIPTOR_HTTP_PROTOCOL)) {
					url.append(Constants.CONNECTION_HTTP);
				} else if(protocol.equalsIgnoreCase(Constants.SERVICE_DESCRIPTOR_HTTPS_PROTOCOL)) {
					url.append(Constants.CONNECTION_HTTPS);
				}

				url.append("://");
			}
				
			url.append(instance);

			if(port != null && port.length() > 0) {
				url.append(":" + port);
			}
			
			if(context != null && context.length() > 0) {
				url.append("/" + context);
			}
			
			if(apiPath != null && apiPath.length() > 0) {
				url.append("/" + apiPath);
			}
			
		return url.toString();
	}
	
	private Map<String, String> formQueryParameters(final Service service) throws SiminovException {
		
		String serviceName = service.getServiceName();
		String apiName = service.getAPIName();
		
		ServiceDescriptor serviceDescriptor = resources.requiredServiceDescriptorBasedOnName(serviceName);
		API api = serviceDescriptor.getApi(apiName);
		
		Map<String, String> serviceResources = service.getResources();

		
		Map<String, String> parameters = new HashMap<String, String>();
		
		Iterator<QueryParameter> queryParameters = api.getQueryParameters();
		while(queryParameters.hasNext()) {
			
			QueryParameter queryParameter = queryParameters.next();
			String value = (String) ServiceResourceUtils.resolve(queryParameter.getValue(), serviceResources);
			
			parameters.put(queryParameter.getName(), value);
		}
		
		return parameters;
	}
	
	private Map<String, String> formHeaderParameters(final Service service) throws SiminovException {

		String serviceName = service.getServiceName();
		String apiName = service.getAPIName();
		
		ServiceDescriptor serviceDescriptor = resources.requiredServiceDescriptorBasedOnName(serviceName);
		API api = serviceDescriptor.getApi(apiName);
		
		Map<String, String> serviceResources = service.getResources();

		
		Map<String, String> parameters = new HashMap<String, String>();
		
		Iterator<HeaderParameter> headerParameters = api.getHeaderParameters();
		while(headerParameters.hasNext()) {
			
			HeaderParameter headerParameter = headerParameters.next();
			String value = (String) ServiceResourceUtils.resolve(headerParameter.getValue(), serviceResources);
			
			parameters.put(headerParameter.getName(), value);
		}
		
		return parameters;
	}
	
	private byte[] formDataStream(final Service service) {
		
		String serviceName = service.getServiceName();
		String apiName = service.getAPIName();
		
		ServiceDescriptor serviceDescriptor = resources.requiredServiceDescriptorBasedOnName(serviceName);
		API api = serviceDescriptor.getApi(apiName);

		if(api.getDataStream() == null) {
			return new byte[0];
		}
		
		
		return api.getDataStream().getBytes();
	}
}
