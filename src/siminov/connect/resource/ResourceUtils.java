/** 
 * [SIMINOV FRAMEWORK]
 * Copyright [2015] [Siminov Software Solution LLP|support@siminov.com]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 **/

package siminov.connect.resource;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.StringTokenizer;

import siminov.connect.Constants;
import siminov.connect.exception.ServiceException;
import siminov.connect.model.ServiceDescriptor;
import siminov.connect.model.ServiceDescriptor.Request;
import siminov.connect.model.ServiceDescriptor.Request.HeaderParameter;
import siminov.connect.model.ServiceDescriptor.Request.QueryParameter;
import siminov.connect.service.design.IService;
import siminov.core.exception.SiminovException;
import siminov.core.log.Log;
import siminov.core.model.IDescriptor;
import siminov.core.utils.ClassUtils;

/**
 * It is a resource utility class, it exposes APIs to get service dynamic resources
 */
public class ResourceUtils {

	/**
	 * It resolve dynamic resources
	 * @param resourceValue Value of resource
	 * @param descriptors 
	 * @return
	 * @throws ServiceException
	 */
	public static String resolve(final String resourceValue, final IDescriptor...descriptors) throws ServiceException {
		
		if(resourceValue == null) {
			return resourceValue;
		}
		
		if(resourceValue.contains(Constants.RESOURCE_OPEN_CURLY_BRACKET + Constants.RESOURCE_REFER_REFERENCE)) {

			//Find {}
			int openingCurlyBracketIndex = resourceValue.indexOf(Constants.RESOURCE_SPACE) + 1;
			
			int singleClosingCurlyBracketIndex = resourceValue.indexOf(Constants.RESOURCE_CLOSE_CURLY_BRACKET);
			int doubleClosingCurlyBracketIndex = resourceValue.indexOf(Constants.RESOURCE_CLOSE_CURLY_BRACKET + Constants.RESOURCE_CLOSE_CURLY_BRACKET);

			String resourceKey;
			
			if(doubleClosingCurlyBracketIndex != -1) {

				resourceKey = resourceValue.substring(openingCurlyBracketIndex, doubleClosingCurlyBracketIndex + 1);
				int slashIndex = resourceKey.lastIndexOf(Constants.RESOURCE_SLASH);

				//Find {-
				String resourceClass = resourceKey.substring(0, resourceKey.substring(0, slashIndex).lastIndexOf(Constants.RESOURCE_DOT));
				String resourceAPI = resourceKey.substring(resourceKey.substring(0, slashIndex).lastIndexOf(Constants.RESOURCE_DOT) + 1, resourceKey.substring(0, slashIndex).length());

				Collection<Class<?>> resourceAPIParameterTypes = new LinkedList<Class<?>>();
				Collection<String> resourceAPIParameters = new LinkedList<String>();
				
				
				//Find -}}
				String apiParameters = resourceKey.substring(slashIndex + 1, resourceKey.lastIndexOf(Constants.RESOURCE_CLOSE_CURLY_BRACKET) + 1);
				
				//Resolve all API parameters
				StringTokenizer apiParameterTokenizer = new StringTokenizer(apiParameters, Constants.RESOURCE_COMMA);
				
				while(apiParameterTokenizer.hasMoreElements()) {
					String apiParameter = apiParameterTokenizer.nextToken();
					
					resourceAPIParameterTypes.add(String.class);
					resourceAPIParameters.add(resolve(apiParameter, descriptors));
				}
				
			
				int count = 0;
				Class<?>[] apiParameterTypes = new Class<?>[resourceAPIParameters.size()];
				for(Class<?> resourceAPIParameterType : resourceAPIParameterTypes) {
					apiParameterTypes[count++] = resourceAPIParameterType;
				}
				

				Object classObject = ClassUtils.createClassInstance(resourceClass);
				String resolvedValue = null;
				try {
					resolvedValue = (String) ClassUtils.invokeMethod(classObject, resourceAPI, apiParameterTypes, resourceAPIParameters.toArray());
				} catch(SiminovException se) {
					Log.error(ResourceUtils.class.getName(), "resolve", "SiminovException caught while invoking method, RESOURCE-API: " + resourceAPI + ", " + se.getMessage());
					throw new ServiceException(ResourceUtils.class.getName(), "resolve", se.getMessage());
				}

				
				return resolve(resolvedValue, descriptors);
			} else {

				resourceKey = resourceValue.substring(openingCurlyBracketIndex, singleClosingCurlyBracketIndex);
				int dotIndex = resourceKey.lastIndexOf(Constants.RESOURCE_DOT);

				String resourceClass = resourceKey.substring(0, dotIndex);

				String resourceAPI = resourceKey.substring(resourceKey.lastIndexOf(Constants.RESOURCE_DOT) + 1, resourceKey.length());
				
				Object classObject = ClassUtils.createClassInstance(resourceClass);
				String value = null;
				try {
					value = (String) ClassUtils.getValue(classObject, resourceAPI);
				} catch(SiminovException se) {
					Log.error(ResourceUtils.class.getName(), "resolve", "SiminovException caught while getting values, RESOURCE-API: " + resourceAPI + ", " + se.getMessage());
					throw new ServiceException(ResourceUtils.class.getName(), "resolve", se.getMessage());
				}

				
				String resolvedValue = resourceValue.replace(Constants.RESOURCE_OPEN_CURLY_BRACKET + Constants.RESOURCE_REFER_REFERENCE + Constants.RESOURCE_SPACE + resourceKey + Constants.RESOURCE_CLOSE_CURLY_BRACKET, value);
				return resolve(resolvedValue, descriptors);
			}
		} else if(resourceValue.contains(Constants.RESOURCE_OPEN_CURLY_BRACKET + Constants.RESOURCE_SELF_REFERENCE)) {
			
			String key = resourceValue.substring(resourceValue.indexOf(Constants.RESOURCE_SPACE) + 1, resourceValue.indexOf(Constants.RESOURCE_CLOSE_CURLY_BRACKET));
			String value = null;
			
			
			for(IDescriptor descriptor: descriptors) {
				
				if(descriptor.containProperty(key)) {
					value = descriptor.getProperty(key);
					break;
				}
			}
			
			return resolve(value, descriptors);
		} else if(resourceValue.contains(Constants.RESOURCE_OPEN_CURLY_BRACKET + Constants.RESOURCE_REFERENCE)) {
			
			String key = resourceValue.substring(resourceValue.indexOf(Constants.RESOURCE_OPEN_CURLY_BRACKET + Constants.RESOURCE_REFERENCE) + (Constants.RESOURCE_OPEN_CURLY_BRACKET + Constants.RESOURCE_REFERENCE).length() + 1, resourceValue.indexOf(Constants.RESOURCE_CLOSE_CURLY_BRACKET));
			String value = null;
			
			for(IDescriptor descriptor: descriptors) {
				
				if(descriptor.containProperty(key)) {
					value = descriptor.getProperty(key);
					break;
				}
			}

			
			String resolvedValue = resourceValue.replace(Constants.RESOURCE_OPEN_CURLY_BRACKET + Constants.RESOURCE_REFERENCE + " " + key + Constants.RESOURCE_CLOSE_CURLY_BRACKET, value);
			return resolve(resolvedValue, descriptors);
		} 
		
		return resourceValue;
	}
	
	
	/**
	 * It resolve service descriptor instance 
	 * @param service Service instance
	 * @throws ServiceException If any exception occur while resolving dynamic resources
	 */
	public static void resolve(final IService service) throws ServiceException {

		/*
		 * Resolve Service Descriptor Properties
		 */
		ServiceDescriptor serviceDescriptor = service.getServiceDescriptor();
		
		Iterator<String> resources = service.getResources();
		while(resources.hasNext()) {
			String resourceName = resources.next();
			Object resourceValue = service.getResource(resourceName);
			
			if(resourceValue instanceof String) {
				serviceDescriptor.addProperty(resourceName, (String) resourceValue);
			}
		}
		
		
		Iterator<String> serviceDescriptorProperties = serviceDescriptor.getProperties();
		while(serviceDescriptorProperties.hasNext()) {
			
			String serviceDescriptorProperty = serviceDescriptorProperties.next();
			String serviceDescriptorValue = serviceDescriptor.getProperty(serviceDescriptorProperty);
			serviceDescriptorValue = ResourceUtils.resolve(serviceDescriptorValue, serviceDescriptor);
			
			serviceDescriptor.addProperty(serviceDescriptorProperty, serviceDescriptorValue);
		}
		
		
		/*
		 * Resolve API Properties
		 */
		Request request = serviceDescriptor.getRequest(service.getRequest());
		Iterator<String> apiProperties = request.getProperties();
		while(apiProperties.hasNext()) {
			
			String apiProperty = apiProperties.next();
			String apiValue = request.getProperty(apiProperty);
			apiValue = ResourceUtils.resolve(apiValue, serviceDescriptor, request);
			
			request.addProperty(apiProperty, apiValue);
		}

		
		/*
		 * Resolve API Query Parameters
		 */
		Iterator<QueryParameter> queryParameters = request.getQueryParameters();
		while(queryParameters.hasNext()) {
			
			QueryParameter queryParameter = queryParameters.next();
			
			String queryValue = queryParameter.getValue();
			queryValue = ResourceUtils.resolve(queryValue, serviceDescriptor, request, queryParameter);
			
			queryParameter.setValue(queryValue);
		}

		
		/*
		 * Resolve API Query Parameters
		 */
		Iterator<HeaderParameter> headerParameters = request.getHeaderParameters();
		while(headerParameters.hasNext()) {
			
			HeaderParameter headerParameter = headerParameters.next();
			
			String headerValue = headerParameter.getValue();
			headerValue = ResourceUtils.resolve(headerValue, serviceDescriptor, request, headerParameter);
			
			headerParameter.setValue(headerValue);
		}
	}
}
