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

import siminov.core.database.Database;

/**
 * It exposes APIs to Get and Set service request resource information
 * It helps framework to save ASYNC request in database
 */
public class ServiceRequestResource extends Database {

	private ServiceRequest serviceRequest = null;
	
	private String name = null;
	private String value = null;
	
	/**
	 * Get service request
	 * @return Service Request
	 */
	public ServiceRequest getServiceRequest() {
		return this.serviceRequest;
	}
	
	/**
	 * Set service request
	 * @param serviceRequest Service Request
	 */
	public void setServiceRequest(ServiceRequest serviceRequest) {
		this.serviceRequest = serviceRequest;
	}
	
	/**
	 * Get resource name
	 * @return Resource Name
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Set resource name
	 * @param name Resource Name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Get resource value
	 * @return Resource Value
	 */
	public String getValue() {
		return this.value;
	}
	
	/**
	 * Set resource value
	 * @param value Resource Value
	 */
	public void setValue(String value) {
		this.value = value;
	}
}
