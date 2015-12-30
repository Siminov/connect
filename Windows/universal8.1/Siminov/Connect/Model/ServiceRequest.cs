///
/// [SIMINOV FRAMEWORK - CONNECT]
/// Copyright [2014-2016] [Siminov Software Solution LLP|support@siminov.com]
///
/// Licensed under the Apache License, Version 2.0 (the "License");
/// you may not use this file except in compliance with the License.
/// You may obtain a copy of the License at
///
///     http://www.apache.org/licenses/LICENSE-2.0
///
/// Unless required by applicable law or agreed to in writing, software
/// distributed under the License is distributed on an "AS IS" BASIS,
/// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
/// See the License for the specific language governing permissions and
/// limitations under the License.




using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Siminov.Connect.Model
{


    /// <summary>
    /// It exposes APIs to Get and Set service request information
    /// It helps framework to save ASYNC request in database
    /// </summary>

    public class ServiceRequest : Core.Database.Database
    {
        private long requestId;

        private String service = null;
        private String request = null;

        private String instanceOf = null;

        private IDictionary<String, ServiceRequestResource> serviceRequestResources = new Dictionary<String, ServiceRequestResource>();


        /// <summary>
        /// ServiceRequest Constructor
        /// </summary>
        public ServiceRequest()
        {
            requestId = Convert.ToInt32(new Random().NextDouble());
        }


        /// <summary>
        /// Get request id
        /// </summary>
        /// <returns>Request Id</returns>
        public long GetRequestId()
        {
            return this.requestId;
        }


        /// <summary>
        /// Set request id
        /// </summary>
        /// <param name="request">Request Id</param>
        public void SetRequestId(long request)
        {
            this.requestId = request;
        }


        /// <summary>
        /// Get service name
        /// </summary>
        /// <returns>Name of service</returns>
        public String GetService()
        {
            return this.service;
        }


        /// <summary>
        /// Set service name
        /// </summary>
        /// <param name="service">Name of service</param>
        public void SetService(String service)
        {
            this.service = service;
        }


        /// <summary>
        /// Get request name
        /// </summary>
        /// <returns>Name of request</returns>
        public String GetRequest()
        {
            return this.request;
        }


        /// <summary>
        /// Set request name
        /// </summary>
        /// <param name="request">Name of request</param>
        public void SetRequest(String request)
        {
            this.request = request;
        }


        /// <summary>
        /// Get instance of 
        /// </summary>
        /// <returns>Instance Of</returns>
        public String GetInstanceOf()
        {
            return this.instanceOf;
        }


        /// <summary>
        /// Set instance of
        /// </summary>
        /// <param name="instanceOf">Instance of</param>
        public void SetInstanceOf(String instanceOf)
        {
            this.instanceOf = instanceOf;
        }


        /// <summary>
        /// Get all resources
        /// </summary>
        /// <returns>Resources</returns>
        public IEnumerator<ServiceRequestResource> GetServiceRequestResources()
        {
            return this.serviceRequestResources.Values.GetEnumerator();
        }

        /// <summary>
        /// Get resource based on name
        /// </summary>
        /// <param name="name">Name of resource</param>
        /// <returns>ServiceRequestResource</returns>
        public ServiceRequestResource GetServiceRequestResource(String name)
        {
            return this.serviceRequestResources[name];
        }


        /// <summary>
        /// Add service request resource
        /// </summary>
        /// <param name="serviceRequestResource">Service Request Resource</param>
        public void AddServiceRequestResource(ServiceRequestResource serviceRequestResource)
        {
            this.serviceRequestResources.Add(serviceRequestResource.GetName(), serviceRequestResource);
        }


        /// <summary>
        /// Set service request resources
        /// </summary>
        /// <param name="serviceRequestResources">Service Request Resources</param>
        public void SetServiceRequestResources(IEnumerator<ServiceRequestResource> serviceRequestResources)
        {

            while (serviceRequestResources.MoveNext())
            {
                ServiceRequestResource requestResource = serviceRequestResources.Current;
                this.serviceRequestResources.Add(requestResource.GetName(), requestResource);
            }
        }


        /// <summary>
        /// Check whether service request resource exists or not
        /// </summary>
        /// <param name="name">Name of resource</param>
        /// <returns>(true/false) TRUE: If resource exists | FALSE: If resource does not exists</returns>
        public bool ContainServiceRequestResource(String name)
        {
            return this.serviceRequestResources.ContainsKey(name);
        }
    }
}
