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

package siminov.connect.service.design;

import siminov.connect.IRequest;
import siminov.connect.model.ServiceDescriptor;


/**
 * It provides blue print for classes which wants to implement service 
 */
public interface IService extends IRequest, IServiceEvents, IResource {

	/**
	 * Get service request id
	 * @return Request id
	 */
	public long getRequestId();

	
	/**
	 * Set service request id
	 * @param requestId Request Id
	 */
	public void setRequestId(long requestId);
	
	
	/**
	 * Get service 
	 * @return Service
	 */
	public String getService();
	
	
	/**
	 * Add service name
	 * @param service Name of service
	 */
	public void setService(final String service);
	
	
	/**
	 * Get request name
	 * @return Name of request
	 */
	public String getRequest();
	
	
	/**
	 * Set request name
	 * @param request Name of request
	 */
	public void setRequest(final String request);
	
	
	/**
	 * Get service descriptor
	 * @return Service Descriptor
	 */
	public ServiceDescriptor getServiceDescriptor();
	
	
	/**
	 * Set service descriptor
	 * @param serviceDescriptor Service Descriptor
	 */
	public void setServiceDescriptor(final ServiceDescriptor serviceDescriptor);

	
	/**
	 * Invoke the service
	 */
	public void invoke();
	
	
	/**
	 * Terminate the service
	 */
	public void terminate();
}
