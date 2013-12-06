package siminov.connect.utils;

import java.util.Iterator;

import siminov.connect.Constants;
import siminov.connect.model.ServiceDescriptor;
import siminov.connect.model.ServiceDescriptor.API;
import siminov.connect.model.ServiceDescriptor.API.HeaderParameter;
import siminov.connect.model.ServiceDescriptor.API.QueryParameter;
import siminov.connect.service.Service;
import siminov.orm.exception.SiminovException;
import siminov.orm.utils.ClassUtils;

public class ServiceResourceUtils {

	public static void resolve(final ServiceDescriptor serviceDescriptor, final Service service) throws SiminovException {

		/*
		 * Resolve Service Descriptor Properties
		 */
		Iterator<String> serviceDescriptorProperties = serviceDescriptor.getProperties();
		while(serviceDescriptorProperties.hasNext()) {
			
			String serviceDescriptorProperty = serviceDescriptorProperties.next();
			String serviceDescriptorValue = resolve(serviceDescriptorProperty, serviceDescriptor, service);
			
			serviceDescriptor.addProperty(serviceDescriptorProperty, serviceDescriptorValue);
		}
		
		
		/*
		 * Resolve API Properties
		 */
		API api = serviceDescriptor.getApi(service.getAPIName());
		Iterator<String> apiProperties = api.getProperties();
		while(apiProperties.hasNext()) {
			
			String apiProperty = apiProperties.next();
			String apiValue = resolve(apiProperty, serviceDescriptor, service);
		
			api.addProperty(apiProperty, apiValue);
		}

		
		/*
		 * Resolve API Query Parameters
		 */
		Iterator<QueryParameter> queryParameters = api.getQueryParameters();
		while(queryParameters.hasNext()) {
			
			QueryParameter queryParameter = queryParameters.next();
			
			String queryProperty = queryParameter.getName();
			String queryValue = resolve(queryProperty, serviceDescriptor, service);
			
			queryParameter.setValue(queryValue);
		}

		
		/*
		 * Resolve API Query Parameters
		 */
		Iterator<HeaderParameter> headerParameters = api.getHeaderParameters();
		while(headerParameters.hasNext()) {
			
			HeaderParameter headerParameter = headerParameters.next();
			
			String headerProperty = headerParameter.getName();
			String headerValue = resolve(headerProperty, serviceDescriptor, service);
			
			headerParameter.setValue(headerValue);
		}
	}

	private static String resolve(final String resourceName, final ServiceDescriptor serviceDescriptor, final Service service) throws SiminovException {

		String resourceValue = null;
		
		boolean containProperty = serviceDescriptor.containProperty(resourceName);
		if(containProperty) {
			resourceValue = serviceDescriptor.getProperty(resourceName);
		} else {
			
			API api = serviceDescriptor.getApi(service.getAPIName());
			if(api.containProperty(resourceName)) {
				resourceValue = api.getProperty(resourceName);	
			}
		}
		
		
		if(resourceValue == null) {
			return resourceValue;
		}
		
		
		
		if(resourceValue.contains(Constants.SERVICE_RESOURCE_HASH + Constants.SERVICE_RESOURCE_OPEN_CURLY_BRACKET)) {

			String serviceResourceKey = resourceValue.substring(resourceValue.indexOf(Constants.SERVICE_RESOURCE_HASH + Constants.SERVICE_RESOURCE_OPEN_CURLY_BRACKET) + 2, resourceValue.indexOf(Constants.SERVICE_RESOURCE_CLOSE_CURLY_BRACKET));
			
			String serviceResourceClass = serviceResourceKey.substring(0, serviceResourceKey.lastIndexOf(Constants.SERVICE_RESOURCE_DOT));
			String serviceResourceAPI = serviceResourceKey.substring(serviceResourceKey.lastIndexOf(Constants.SERVICE_RESOURCE_DOT) + 1, serviceResourceKey.length());
			
			Object classObject = ClassUtils.createClassInstance(serviceResourceClass);
			String serviceResourceValue = (String) ClassUtils.getValue(classObject, serviceResourceAPI);
		
			resourceValue = resourceValue.replace(Constants.SERVICE_RESOURCE_HASH + Constants.SERVICE_RESOURCE_OPEN_CURLY_BRACKET + serviceResourceKey + Constants.SERVICE_RESOURCE_CLOSE_CURLY_BRACKET, serviceResourceValue);
			resolve(resourceValue, serviceDescriptor, service);
		} else if(resourceValue.contains(Constants.SERVICE_RESOURCE_OPEN_CURLY_BRACKET)) {
			
			String serviceResourceKey = resourceValue.substring(resourceValue.indexOf(Constants.SERVICE_RESOURCE_OPEN_CURLY_BRACKET) + 1, resourceValue.indexOf(Constants.SERVICE_RESOURCE_CLOSE_CURLY_BRACKET));
			String serviceResourceValue = serviceDescriptor.getProperty(serviceResourceKey);
			
			String resolved = resourceValue.replace(Constants.SERVICE_RESOURCE_OPEN_CURLY_BRACKET + serviceResourceKey + Constants.SERVICE_RESOURCE_CLOSE_CURLY_BRACKET, serviceResourceValue);
			resolve(resolved, serviceDescriptor, service);
		}
		
		return resourceValue;
	}
	
}
