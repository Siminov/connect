package siminov.connect.connection;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import siminov.connect.Constants;
import siminov.connect.model.ServiceDescriptor;
import siminov.connect.model.ServiceDescriptor.API;
import siminov.connect.model.ServiceDescriptor.API.HeaderParameter;
import siminov.connect.model.ServiceDescriptor.API.QueryParameter;
import siminov.connect.service.Service;
import siminov.connect.utils.ServiceResourceUtils;
import siminov.orm.exception.SiminovException;


public class ConnectionHelper {

	public ConnectionRequest prepareConnectionRequest(final ServiceDescriptor serviceDescriptor, final Service service) throws SiminovException {

		/*
		 * Resolve All Referring Resources
		 */
		ServiceResourceUtils.resolve(serviceDescriptor, service);
		
		
		String url = formUrl(serviceDescriptor, service);
		
		Map<String, String> queryParameters = formQueryParameters(serviceDescriptor, service);
		Map<String, String> headerParameters = formHeaderParameters(serviceDescriptor, service);
		
		byte[] dataStream = formDataStream(serviceDescriptor, service);
		
		ConnectionRequest connectionRequest = new ConnectionRequest();
		connectionRequest.setUrl(url);
		connectionRequest.setQueryParameters(queryParameters);
		connectionRequest.setHeaderParameters(headerParameters);
		connectionRequest.setDataStream(dataStream);
		
		return connectionRequest;
	}


	private String formUrl(final ServiceDescriptor serviceDescriptor, Service service) throws SiminovException {
		
		String apiName = service.getAPIName();
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
	
	private Map<String, String> formQueryParameters(final ServiceDescriptor serviceDescriptor, final Service service) throws SiminovException {
		
		String apiName = service.getAPIName();
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
	
	private Map<String, String> formHeaderParameters(final ServiceDescriptor serviceDescriptor, final Service service) throws SiminovException {

		String apiName = service.getAPIName();
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
	
	private byte[] formDataStream(final ServiceDescriptor serviceDescriptor, final Service service) {
		
		String apiName = service.getAPIName();
		API api = serviceDescriptor.getApi(apiName);

		if(api.getDataStream() == null) {
			return new byte[0];
		}
		
		
		return api.getDataStream().getBytes();
	}
}
