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
    /// It exposes APIs to Get and Set service request resource information
    /// It helps framework to save ASYNC request in database
    /// </summary>

    public class ServiceRequestResource : Core.Database.Database
    {
        private ServiceRequest serviceRequest = null;

        private String name = null;
        private String value = null;


        /// <summary>
        /// Get service request
        /// </summary>
        /// <returns>Service Request</returns>
        public ServiceRequest GetServiceRequest()
        {
            return this.serviceRequest;
        }


        /// <summary>
        /// Set service request
        /// </summary>
        /// <param name="serviceRequest">Service Request</param>
        public void SetServiceRequest(ServiceRequest serviceRequest)
        {
            this.serviceRequest = serviceRequest;
        }


        /// <summary>
        /// Get resource name
        /// </summary>
        /// <returns>Resource Name</returns>
        public String GetName()
        {
            return this.name;
        }


        /// <summary>
        /// Set resource name
        /// </summary>
        /// <param name="name">Resource Name</param>
        public void SetName(String name)
        {
            this.name = name;
        }


        /// <summary>
        /// Get resource value
        /// </summary>
        /// <returns>Resource Value</returns>
        public String GetValue()
        {
            return this.value;
        }


        /// <summary>
        /// Set resource value
        /// </summary>
        /// <param name="value">Resource Value</param>
        public void SetValue(String value)
        {
            this.value = value;
        }
    }
}
