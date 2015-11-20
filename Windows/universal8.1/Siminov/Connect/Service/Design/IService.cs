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



using Siminov.Connect.Model;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Siminov.Connect.Service.Design
{

    /// <summary>
    /// It provides blue print for classes which wants to implement service 
    /// </summary>
    public interface IService : IResource, IRequest, IServiceEvents 
    {

        /// <summary>
        /// Get service request id
        /// </summary>
        /// <returns>Request id</returns>
	    long GetRequestId();

	
        /// <summary>
        /// Set service request id
        /// </summary>
        /// <param name="requestId">Request Id</param>
	    void SetRequestId(long requestId);
	
	
        /// <summary>
        /// Get service 
        /// </summary>
        /// <returns>Service</returns>
	    String GetService();
	
	
        /// <summary>
        /// Add service name
        /// </summary>
        /// <param name="service">Name of service</param>
	    void SetService(String service);
	
	
        /// <summary>
        /// Get request name
        /// </summary>
        /// <returns>Name of request</returns>
	    String GetRequest();
	
	
        /// <summary>
        /// Set request name
        /// </summary>
        /// <param name="request">Name of request</param>
	    void SetRequest(String request);
	
	
        /// <summary>
        /// Get service descriptor
        /// </summary>
        /// <returns>Service Descriptor</returns>
	    ServiceDescriptor GetServiceDescriptor();
	
	
        /// <summary>
        /// Set service descriptor
        /// </summary>
        /// <param name="serviceDescriptor">Service Descriptor</param>
	    void SetServiceDescriptor(ServiceDescriptor serviceDescriptor);

	
        /// <summary>
        /// Invoke the service
        /// </summary>
	    void Invoke();
	
	
        /// <summary>
        /// Terminate the service
        /// </summary>
	    void Terminate();
    }
}
