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

package siminov.connect.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import siminov.orm.database.Database;

public class ServiceRequest extends Database {

	private long requestId;
	
	private String service = null;
	private String api = null;
	
	private String instanceOf = null;
	
	private Map<String, ServiceRequestResource> serviceRequestResources = new HashMap<String, ServiceRequestResource>();
	
	public ServiceRequest() {
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
	
	public void setService(String service) {
		this.service = service;
	}
	
	public String getApi() {
		return this.api;
	}
	
	public void setApi(String api) {
		this.api = api;
	}
	
	public String getInstanceOf() {
		return this.instanceOf;
	}
	
	public void setInstanceOf(String instanceOf) {
		this.instanceOf = instanceOf;
	}
	
	public Iterator<ServiceRequestResource> getServiceRequestResources() {
		return this.serviceRequestResources.values().iterator();
	}
	
	public ServiceRequestResource getServiceRequestResource(String name) {
		return this.serviceRequestResources.get(name);
	}
	
	public void addServiceRequestResource(ServiceRequestResource serviceRequestResource) {
		this.serviceRequestResources.put(serviceRequestResource.getName(), serviceRequestResource);
	}

	public void setServiceRequestResources(Iterator<ServiceRequestResource> serviceRequestResources) {
		
		while(serviceRequestResources.hasNext()) {
			ServiceRequestResource requestResource = serviceRequestResources.next();
			this.serviceRequestResources.put(requestResource.getName(), requestResource);
		}
	}
	
	public boolean containServiceRequestResource(String name) {
		return this.serviceRequestResources.containsValue(name);
	}
}
