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
import siminov.connect.IWorker;
import siminov.connect.exception.ServiceException;
import siminov.connect.model.ServiceDescriptor;
import siminov.connect.model.ServiceDescriptor.Request;
import siminov.connect.resource.ResourceManager;
import siminov.connect.resource.ResourceUtils;
import siminov.connect.resource.ServiceResourceUtils;
import siminov.connect.service.design.IService;


public class ServiceHandler {

	private static ServiceHandler serviceHandler = null;
	
	private SyncServiceWorker syncServiceWorker = null;
	private IWorker asyncServiceWorker = null;
	
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


		Iterator<String> resources = service.getResources();
		while(resources.hasNext()) {
			String resourceName = resources.next();
			Object resourceValue = service.getResource(resourceName);
			
			if(resourceValue instanceof String) {
				serviceDescriptor.addProperty(resourceName, (String) resourceValue);
			}
		}

		
		Request request = serviceDescriptor.getRequest(service.getRequest());
		String mode = ResourceUtils.resolve(request.getMode(), serviceDescriptor);
		
		if(mode.equalsIgnoreCase(Constants.SERVICE_DESCRIPTOR_REQUEST_SYNC_REQUEST_MODE)) {

			ServiceResourceUtils.resolve(service);
			syncServiceWorker.process(service);
		} else if(mode.equalsIgnoreCase(Constants.SERVICE_DESCRIPTOR_REQUEST_ASYNC_REQUEST_MODE)) {
			asyncServiceWorker.addRequest(service);
		}
	}
}
