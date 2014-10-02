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

import java.util.Iterator;

import siminov.connect.exception.ServiceException;
import siminov.connect.model.ServiceDescriptor;
import siminov.connect.model.ServiceDescriptor.Request;
import siminov.connect.model.ServiceDescriptor.Request.HeaderParameter;
import siminov.connect.model.ServiceDescriptor.Request.QueryParameter;
import siminov.connect.service.NameValuePair;
import siminov.connect.service.design.IService;

public class ServiceResourceUtils {

	public static void resolve(final IService service) throws ServiceException {

		/*
		 * Resolve Service Descriptor Properties
		 */
		ServiceDescriptor serviceDescriptor = service.getServiceDescriptor();
		
		Iterator<NameValuePair> resources = service.getResources();
		while(resources.hasNext()) {
			NameValuePair resource = resources.next();
			Object resourceValue = resource.getValue();
			
			if(resourceValue instanceof String) {
				serviceDescriptor.addProperty(resource.getName(), (String) resourceValue);
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
