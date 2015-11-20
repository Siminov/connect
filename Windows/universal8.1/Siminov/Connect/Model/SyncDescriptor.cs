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



using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Siminov.Connect.Model
{

    
    /// <summary>
    /// Exposes methods to GET and SET Sync Descriptor information as per define in SyncDescriptor.xml file by application.
    /// Example:
    ///
    ///    <sync-descriptor>
	///            
    ///            <!-- Mandatory Field -->
    ///        <property name="name">name_of_sync_handler</property>
	///			
    ///            <!-- Optional Field -->
    ///        <property name="sync_interval">sync_interval_in_millisecond</property>
	///     				
    ///            <!-- Optional Field -->
    ///                <!-- Default: SCREEN -->
    ///        <property name="type">INTERVAL|SCREEN|INTERVAL-SCREEN</property>
	///			
    ///        <!-- Services -->
    ///            <!-- Service -->
    ///        <services>
	///     		    
    ///            <service>name_of_service.name_of_api</service>
	///     		    
    ///        </services>
	///
    ///    </sync-descriptor>
    /// </summary>
    public class SyncDescriptor
    {
        private IDictionary<String, String> properties = new Dictionary<String, String>();

        private ICollection<String> serviceDescriptorNames = new List<String>();


        /// <summary>
        /// Get sync descriptor name
        /// </summary>
        /// <returns>Name of sync descriptor</returns>
        public String GetName()
        {
            return this.properties[Constants.SYNC_DESCRIPTOR_NAME];
        }


        /// <summary>
        /// Set sync descriptor name
        /// </summary>
        /// <param name="name">Name of sync descriptor</param>
        public void SetName(String name)
        {
            this.properties.Add(Constants.SYNC_DESCRIPTOR_NAME, name);
        }


        /// <summary>
        /// Get sync interval
        /// </summary>
        /// <returns>Sync Interval</returns>
        public int GetSyncInterval()
        {
            String syncInterval = this.properties[Constants.SYNC_DESCRIPTOR_REFRESH_INTERVAL];
            if (syncInterval == null || syncInterval.Length <= 0)
            {
                return 0;
            }

            return Convert.ToInt32(syncInterval);
        }


        /// <summary>
        /// Set sync interval
        /// </summary>
        /// <param name="syncInterval">Sync Interval</param>
        public void SetSyncInterval(int syncInterval)
        {
            this.properties.Add(Constants.SYNC_DESCRIPTOR_REFRESH_INTERVAL, Convert.ToString(syncInterval));
        }

        public IEnumerator<String> GetProperties()
        {
            return this.properties.Keys.GetEnumerator();
        }

        public String GetProperty(String name)
        {
            return this.properties[name];
        }

        public bool ContainProperty(String name)
        {
            return this.properties.ContainsKey(name);
        }

        public void AddProperty(String name, String value)
        {
            this.properties.Add(name, value);
        }

        public void RemoveProperty(String name)
        {
            this.properties.Remove(name);
        }


        /// <summary>
        /// Get all service descriptor names
        /// </summary>
        /// <returns>Service Descriptor Names</returns>
        public IEnumerator<String> GetServiceDescriptorNames()
        {
            return this.serviceDescriptorNames.GetEnumerator();
        }


        /// <summary>
        /// Add service descriptor name
        /// </summary>
        /// <param name="serviceDescriptorName">Name of service descriptor</param>
        public void AddServiceDescriptorName(String serviceDescriptorName)
        {
            this.serviceDescriptorNames.Add(serviceDescriptorName);
        }


        /// <summary>
        /// Remove service descriptor name
        /// </summary>
        /// <param name="serviceDescriptorName">Service Descriptor Name</param>
        public void RemoveServiceDescriptorName(String serviceDescriptorName)
        {
            this.serviceDescriptorNames.Remove(serviceDescriptorName);
        }
    }
}
