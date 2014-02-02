package siminov.connect.connection;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import siminov.connect.Constants;
import siminov.connect.model.ServiceDescriptor;
import siminov.connect.model.ServiceDescriptor.API;
import siminov.connect.model.ServiceDescriptor.API.HeaderParameter;
import siminov.connect.model.ServiceDescriptor.API.QueryParameter;
import siminov.connect.service.design.IService;

public class ConnectionHelper {

	public static ConnectionRequest prepareConnectionRequest(final IService service) {

		/*
		 * Resolve All Referring Resources
		 */
		ServiceDescriptor serviceDescriptor = service.getServiceDescriptor();
		API api = serviceDescriptor.getApi(service.getApi());
		
		String url = formUrl(service);
		
		Map<String, String> queryParameters = formQueryParameters(service);
		Map<String, String> headerParameters = formHeaderParameters(service);
		
		byte[] dataStream = formDataStream(service);
		
		ConnectionRequest connectionRequest = new ConnectionRequest();
		connectionRequest.setUrl(url);
		connectionRequest.setProtocol(serviceDescriptor.getProtocol());
		connectionRequest.setType(api.getType());
		connectionRequest.setQueryParameters(queryParameters);
		connectionRequest.setHeaderParameters(headerParameters);
		connectionRequest.setDataStream(dataStream);
		
		return connectionRequest;
	}


	private static String formUrl(final IService service) {
		
		ServiceDescriptor serviceDescriptor = service.getServiceDescriptor();
		
		String apiName = service.getApi();
		API api = serviceDescriptor.getApi(apiName);
		
		String protocol = serviceDescriptor.getProtocol();
		
		String instance = serviceDescriptor.getInstance();
		String port = serviceDescriptor.getPort();
		
		String context = serviceDescriptor.getContext();
		String apiPath = api.getApi();
		
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
	
	private static Map<String, String> formQueryParameters(final IService service) {
		
		ServiceDescriptor serviceDescriptor = service.getServiceDescriptor();
		
		String apiName = service.getApi();
		API api = serviceDescriptor.getApi(apiName);
		
		Map<String, String> parameters = new HashMap<String, String>();
		
		Iterator<QueryParameter> queryParameters = api.getQueryParameters();
		while(queryParameters.hasNext()) {
			
			QueryParameter queryParameter = queryParameters.next();
			String value = queryParameter.getValue();
			
			parameters.put(queryParameter.getName(), value);
		}
		
		return parameters;
	}
	
	private static Map<String, String> formHeaderParameters(final IService service) {

		ServiceDescriptor serviceDescriptor = service.getServiceDescriptor();
		
		String apiName = service.getApi();
		API api = serviceDescriptor.getApi(apiName);
		
		Map<String, String> parameters = new HashMap<String, String>();
		
		Iterator<HeaderParameter> headerParameters = api.getHeaderParameters();
		while(headerParameters.hasNext()) {
			
			HeaderParameter headerParameter = headerParameters.next();
			String value = headerParameter.getValue();
			
			parameters.put(headerParameter.getName(), value);
		}
		
		return parameters;
	}
	
	private static byte[] formDataStream(final IService service) {
		
		ServiceDescriptor serviceDescriptor = service.getServiceDescriptor();
		
		String apiName = service.getApi();
		API api = serviceDescriptor.getApi(apiName);

		if(api.getDataStream() == null) {
			return new byte[0];
		}
		
		
		return api.getDataStream().getBytes();
	}
}
