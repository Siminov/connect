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

package siminov.connect.service;

import java.util.Iterator;

import siminov.connect.Constants;
import siminov.connect.exception.ServiceException;
import siminov.connect.model.ServiceDescriptor;
import siminov.connect.model.ServiceDescriptor.API;
import siminov.connect.resource.ResourceManager;
import siminov.connect.resource.ResourceUtils;
import siminov.connect.resource.ServiceResourceUtils;
import siminov.connect.service.design.IService;
import siminov.connect.service.design.IServiceWorker;


public class ServiceHandler {

	private static ServiceHandler serviceHandler = null;
	
	private IServiceWorker syncServiceWorker = null;
	private IServiceWorker asyncServiceWorker = null;
	
	private ResourceManager resourceManager = null;
	
	private ServiceHandler() {
		
		syncServiceWorker =  new SyncServiceWorker();
		asyncServiceWorker = AsyncServiceWorker.getInstance();
		
		resourceManager = ResourceManager.getInstance();
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
			serviceDescriptor = resourceManager.requiredServiceDescriptorBasedOnName(service.getService());
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
