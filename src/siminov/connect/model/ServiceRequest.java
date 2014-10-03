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

/**
 * It exposes APIs to Get and Set service request information
 * It helps framework to save ASYNC request in database
 */
public class ServiceRequest extends Database {

	private long requestId;
	
	private String service = null;
	private String request = null;
	
	private String instanceOf = null;
	
	private Map<String, ServiceRequestResource> serviceRequestResources = new HashMap<String, ServiceRequestResource>();
	
	/**
	 * ServiceRequest Constructor
	 */
	public ServiceRequest() {
		requestId = new Random().nextLong();
	}
	
	/**
	 * Get request id
	 * @return Request Id
	 */
	public long getRequestId() {
		return this.requestId;
	}
	
	/**
	 * Set request id
	 * @param request Request Id
	 */
	public void setRequestId(long request) {
		this.requestId = request;
	}
	
	/**
	 * Get service name
	 * @return Name of service
	 */
	public String getService() {
		return this.service;
	}
	
	/**
	 * Set service name
	 * @param service Name of service
	 */
	public void setService(String service) {
		this.service = service;
	}
	
	/**
	 * Get request name
	 * @return Name of request
	 */
	public String getRequest() {
		return this.request;
	}
	
	/**
	 * Set request name
	 * @param request Name of request
	 */
	public void setRequest(String request) {
		this.request = request;
	}
	
	/**
	 * Get instance of 
	 * @return Instance Of
	 */
	public String getInstanceOf() {
		return this.instanceOf;
	}
	
	/**
	 * Set instance of
	 * @param instanceOf Instance of
	 */
	public void setInstanceOf(String instanceOf) {
		this.instanceOf = instanceOf;
	}
	
	/**
	 * Get all resources
	 * @return Resources
	 */
	public Iterator<ServiceRequestResource> getServiceRequestResources() {
		return this.serviceRequestResources.values().iterator();
	}
	
	/**
	 * Get resource based on name
	 * @param name Name of resource
	 * @return ServiceRequestResource
	 */
	public ServiceRequestResource getServiceRequestResource(String name) {
		return this.serviceRequestResources.get(name);
	}
	
	/**
	 * Add service request resource
	 * @param serviceRequestResource Service Request Resource
	 */
	public void addServiceRequestResource(ServiceRequestResource serviceRequestResource) {
		this.serviceRequestResources.put(serviceRequestResource.getName(), serviceRequestResource);
	}

	/**
	 * Set service request resources
	 * @param serviceRequestResources Service Request Resources
	 */
	public void setServiceRequestResources(Iterator<ServiceRequestResource> serviceRequestResources) {
		
		while(serviceRequestResources.hasNext()) {
			ServiceRequestResource requestResource = serviceRequestResources.next();
			this.serviceRequestResources.put(requestResource.getName(), requestResource);
		}
	}
	
	/**
	 * Check whether service request resource exists or not
	 * @param name Name of resource
	 * @return (true/false) TRUE: If resource exists | FALSE: If resource does not exists
	 */
	public boolean containServiceRequestResource(String name) {
		return this.serviceRequestResources.containsValue(name);
	}
}
