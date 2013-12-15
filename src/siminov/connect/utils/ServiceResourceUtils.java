package siminov.connect.utils;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.StringTokenizer;

import siminov.connect.Constants;
import siminov.connect.model.ServiceDescriptor;
import siminov.connect.model.ServiceDescriptor.API;
import siminov.connect.model.ServiceDescriptor.API.HeaderParameter;
import siminov.connect.model.ServiceDescriptor.API.QueryParameter;
import siminov.connect.service.IService;
import siminov.orm.exception.SiminovException;
import siminov.orm.utils.ClassUtils;

public class ServiceResourceUtils {

	public static void resolve(final IService service) throws SiminovException {

		/*
		 * Resolve Service Descriptor Properties
		 */
		ServiceDescriptor serviceDescriptor = service.getServiceDescriptor();
		
		Iterator<String> serviceDescriptorProperties = serviceDescriptor.getProperties();
		while(serviceDescriptorProperties.hasNext()) {
			
			String serviceDescriptorProperty = serviceDescriptorProperties.next();
			String serviceDescriptorValue = serviceDescriptor.getProperty(serviceDescriptorProperty);
			serviceDescriptorValue = resolve(service, serviceDescriptorProperty, serviceDescriptorValue);
			
			serviceDescriptor.addProperty(serviceDescriptorProperty, serviceDescriptorValue);
		}
		
		
		/*
		 * Resolve API Properties
		 */
		API api = serviceDescriptor.getApi(service.getApi());
		Iterator<String> apiProperties = api.getProperties();
		while(apiProperties.hasNext()) {
			
			String apiProperty = apiProperties.next();
			String apiValue = api.getProperty(apiProperty);
			apiValue = resolve(service, apiProperty, apiValue);
			
			api.addProperty(apiProperty, apiValue);
		}

		
		/*
		 * Resolve API Query Parameters
		 */
		Iterator<QueryParameter> queryParameters = api.getQueryParameters();
		while(queryParameters.hasNext()) {
			
			QueryParameter queryParameter = queryParameters.next();
			
			String queryProperty = queryParameter.getName();
			String queryValue = queryParameter.getValue();
			queryValue = resolve(service, queryProperty, queryValue);
			
			queryParameter.setValue(queryValue);
		}

		
		/*
		 * Resolve API Query Parameters
		 */
		Iterator<HeaderParameter> headerParameters = api.getHeaderParameters();
		while(headerParameters.hasNext()) {
			
			HeaderParameter headerParameter = headerParameters.next();
			
			String headerProperty = headerParameter.getName();
			String headerValue = headerParameter.getValue();
			headerValue = resolve(service, headerProperty, headerValue);
			
			headerParameter.setValue(headerValue);
		}
	}

	private static String resolve(final IService service, final String resourceName, final String resourceValue) throws SiminovException {

		if(resourceValue == null) {
			return resourceValue;
		}
		
		
		ServiceDescriptor serviceDescriptor = service.getServiceDescriptor();
		
		if(resourceValue.contains(Constants.SERVICE_RESOURCE_HASH + Constants.SERVICE_RESOURCE_OPEN_CURLY_BRACKET)) {

			//Find {}
			int openingCurlyBracketIndex = resourceValue.indexOf(Constants.SERVICE_RESOURCE_HASH + Constants.SERVICE_RESOURCE_OPEN_CURLY_BRACKET) + 2;
			
			int singleClosingCurlyBracketIndex = resourceValue.indexOf(Constants.SERVICE_RESOURCE_CLOSE_CURLY_BRACKET);
			int doubleClosingCurlyBracketIndex = resourceValue.indexOf(Constants.SERVICE_RESOURCE_CLOSE_CURLY_BRACKET + Constants.SERVICE_RESOURCE_CLOSE_CURLY_BRACKET);

			String serviceResourceKey;
			
			if(doubleClosingCurlyBracketIndex != -1) {
				serviceResourceKey = resourceValue.substring(openingCurlyBracketIndex, doubleClosingCurlyBracketIndex + 1);
			} else {
				serviceResourceKey = resourceValue.substring(openingCurlyBracketIndex, singleClosingCurlyBracketIndex);
			}
			

			//Find first index of -
			int slashIndex = serviceResourceKey.lastIndexOf(Constants.SERVICE_RESOURCE_SLASH);
			//Find first index of .
			int dotIndex = serviceResourceKey.lastIndexOf(Constants.SERVICE_RESOURCE_DOT);

			String serviceResourceClass;
			if(slashIndex != -1 && slashIndex < dotIndex) {
				//Find {-
				
				serviceResourceClass = serviceResourceKey.substring(0, serviceResourceKey.substring(0, slashIndex).lastIndexOf(Constants.SERVICE_RESOURCE_DOT));
				String serviceResourceAPI = serviceResourceKey.substring(serviceResourceKey.substring(0, slashIndex).lastIndexOf(Constants.SERVICE_RESOURCE_DOT) + 1, serviceResourceKey.substring(0, slashIndex).length());

				Collection<Class<?>> serviceResourceAPIParameterTypes = new LinkedList<Class<?>>();
				Collection<String> serviceResourceAPIParameters = new LinkedList<String>();
				
				
				//Find -}}
				String apiParameters = serviceResourceKey.substring(slashIndex + 1, serviceResourceKey.lastIndexOf(Constants.SERVICE_RESOURCE_CLOSE_CURLY_BRACKET) + 1);
				
				//Resolve all API parameters
				StringTokenizer apiParameterTokenizer = new StringTokenizer(apiParameters, Constants.SERVICE_RESOURCE_COMMA);
				
				while(apiParameterTokenizer.hasMoreElements()) {
					String apiParameter = apiParameterTokenizer.nextToken();
					
					serviceResourceAPIParameterTypes.add(String.class);
					serviceResourceAPIParameters.add(resolve(service, resourceName, apiParameter));
				}
				
			
				int count = 0;
				Class<?>[] apiParameterTypes = new Class<?>[serviceResourceAPIParameters.size()];
				for(Class<?> serviceResourceAPIParameterType : serviceResourceAPIParameterTypes) {
					apiParameterTypes[count++] = serviceResourceAPIParameterType;
				}
				

				Object classObject = ClassUtils.createClassInstance(serviceResourceClass);
				String resolvedValue = (String) ClassUtils.invokeMethod(classObject, serviceResourceAPI, apiParameterTypes, serviceResourceAPIParameters.toArray());
				
				return resolve(service, resourceName, resolvedValue);
			} else if(dotIndex != -1) {
				serviceResourceClass = serviceResourceKey.substring(0, dotIndex);

				String serviceResourceAPI = serviceResourceKey.substring(serviceResourceKey.lastIndexOf(Constants.SERVICE_RESOURCE_DOT) + 1, serviceResourceKey.length());
				
				Object classObject = ClassUtils.createClassInstance(serviceResourceClass);
				String serviceResourceValue = (String) ClassUtils.getValue(classObject, serviceResourceAPI);
			
				String resolvedValue = resourceValue.replace(Constants.SERVICE_RESOURCE_HASH + Constants.SERVICE_RESOURCE_OPEN_CURLY_BRACKET + serviceResourceKey + Constants.SERVICE_RESOURCE_CLOSE_CURLY_BRACKET, serviceResourceValue);
				return resolve(service, resourceName, resolvedValue);
			}
		} else if(resourceValue.contains(Constants.SERVICE_RESOURCE_OPEN_CURLY_BRACKET + Constants.SERVICE_RESOURCE_SELF_REFERENCE + Constants.SERVICE_RESOURCE_DOT)) {
			
			String serviceResourceKey = resourceValue.substring(resourceValue.indexOf(Constants.SERVICE_RESOURCE_OPEN_CURLY_BRACKET + Constants.SERVICE_RESOURCE_SELF_REFERENCE + Constants.SERVICE_RESOURCE_DOT) + 1 + (Constants.SERVICE_RESOURCE_SELF_REFERENCE + Constants.SERVICE_RESOURCE_DOT).length(), resourceValue.indexOf(Constants.SERVICE_RESOURCE_CLOSE_CURLY_BRACKET));
			String serviceResourceValue = null;
			
			if(serviceDescriptor.containProperty(serviceResourceKey)) {
				serviceResourceValue = serviceDescriptor.getProperty(serviceResourceKey);
			} else {
				
				API api = serviceDescriptor.getApi(service.getApi());
				if(api.containProperty(serviceResourceKey)) {
					serviceResourceValue = api.getProperty(serviceResourceKey);
				} else if(api.containQueryParameter(serviceResourceKey)) {
					serviceResourceValue = api.getQueryParameter(serviceResourceKey).getValue();
				} else if(api.containHeaderParameter(serviceResourceKey)) {
					serviceResourceValue = api.getHeaderParameter(serviceResourceKey).getValue();
				}
			}
			
			return resolve(service, serviceResourceKey, serviceResourceValue);
		} else if(resourceValue.contains(Constants.SERVICE_RESOURCE_OPEN_CURLY_BRACKET)) {
			
			String serviceResourceKey = resourceValue.substring(resourceValue.indexOf(Constants.SERVICE_RESOURCE_OPEN_CURLY_BRACKET) + 1, resourceValue.indexOf(Constants.SERVICE_RESOURCE_CLOSE_CURLY_BRACKET));
			String serviceResourceValue = service.getResource(serviceResourceKey);
			
			String resolvedValue = resourceValue.replace(Constants.SERVICE_RESOURCE_OPEN_CURLY_BRACKET + serviceResourceKey + Constants.SERVICE_RESOURCE_CLOSE_CURLY_BRACKET, serviceResourceValue);
			return resolve(service, resourceName, resolvedValue);
		} 
		
		return resourceValue;
	}
}
