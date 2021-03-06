/**
 * [SIMINOV FRAMEWORK - CONNECT]
 * Copyright [2014-2016] [Siminov Software Solution LLP|support@siminov.com]
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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import siminov.connect.exception.ServiceException;
import siminov.connect.model.ServiceDescriptor;
import siminov.connect.service.design.IService;
import siminov.core.log.Log;

/**
 * It exposes APIs to Get and Set service information by extending IService
 */
public abstract class Service implements IService {

	private long requestId;
	
	private String service = null;
	private String request = null;

	private Map<String, Object> resources = new HashMap<String, Object>();
	
	private ServiceDescriptor serviceDescriptor = null;

	/**
	 * Service Constructor
	 */
	public Service() {
		requestId = new Random().nextLong();
	}

	public long getRequestId() {
		return this.requestId;
	}
	
	public void setRequestId(long request) {
		this.requestId = request;
	}
	
	public String getService() {
		return this.service;
	}
	
	public void setService(final String service) {
		this.service = service;
	}
	
	public String getRequest() {
		return this.request;
	}
	
	public void setRequest(final String request) {
		this.request = request;
	}
	
	public Iterator<String> getResources() {
		return this.resources.keySet().iterator();
	}
	
	public Object getResource(final String name) {
		return this.resources.get(name);
	}

	public void addResource(final String name, final Object value) {
		this.resources.put(name, value);
	}
	
	public boolean containResource(final String name) {
		return this.resources.containsKey(name);
	}

	public ServiceDescriptor getServiceDescriptor() {
		return this.serviceDescriptor;
	}
	
	public void setServiceDescriptor(final ServiceDescriptor serviceDescriptor) {
		this.serviceDescriptor = serviceDescriptor;
	}
	
	public void invoke() {

		this.onStart();
		
		ServiceHandler serviceHandler = ServiceHandler.getInstance();
		try {
			serviceHandler.handle(this);
		} catch(ServiceException se) {
			Log.error(Service.class.getName(), "invoke", "ServiceException caught while invoking service, " + se.getMessage());
			
			this.onTerminate(se);
		}
	}
	
	public void terminate() {
		
	}
}
