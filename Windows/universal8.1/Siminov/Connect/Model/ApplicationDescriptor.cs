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
    /// Exposes methods to GET and SET Application Descriptor information as per define in ApplicationDescriptor.xml file by application.
    /// 
	///
    /// Example:
    ///
    ///    <siminov>
	///    
    ///        <!-- General Application Description Properties -->
    ///		
    ///            <!-- Mandatory Field -->
    ///        <property name="name">application_name</property>	
	///	
    ///            <!-- Optional Field -->
    ///        <property name="description">application_description</property>
	///	
    ///            <!-- Mandatory Field (Default is 0.0) -->
    ///        <property name="version">application_version</property>
	///
	///
	///	
    ///        <!-- Database Descriptors Used By Application (zero-to-many) -->	
    ///            <!-- Optional Field's -->
    ///        <database-descriptors>
    ///            <database-descriptor>full_path_of_database_descriptor_file</database-descriptor>
    ///        </database-descriptors>
	///		
	///
    ///        <!-- Service Descriptors -->
    ///        <service-descriptors>
	///  		
    ///                <!-- Service Descriptor -->
    ///            <service-descriptor>full_path_of_service_descriptor</service-descriptor>
	///    
    ///        </service-descriptors>
	///
	///    
	///	
    ///        <!-- Sync Descriptors -->
    ///            <!-- Sync Descriptor -->
    ///        <sync-descriptors>
	///        
    ///            <sync-descriptor>full_path_of_sync_descriptor</sync-descriptor>
	///        
    ///        </sync-descriptors>
	///    
	///
    ///        <!-- Notification Descriptor -->
    ///        <notification-descriptor>
	///        
    ///                <!-- Optional Field -->
    ///            <property name="name_of_property">value_of_property</property>
	///
    ///        </notification-descriptor>
	///    
	///		
	///	
    ///        <!-- Library Descriptors Used By Application (zero-to-many) -->
    ///            <!-- Optional Field's -->
    ///        <library-descriptors>
    ///            <library-descriptor>full_path_of_library_descriptor_file</library-descriptor>   
    ///        </librar-descriptors>
	///	
    ///			
    ///        <!-- Event Handlers Implemented By Application (zero-to-many) -->
	///	
    ///            <!-- Optional Field's -->
    ///        <event-handlers>
    ///            <event-handler>full_class_path_of_event_handler_(ISiminovHandler/IDatabaseHandler)</event-handler>
    ///        </event-handlers>
	///
    ///    </siminov>
    /// 
    /// </summary>

    public class ApplicationDescriptor : Core.Model.ApplicationDescriptor
    {
        private ICollection<String> serviceDescriptorPaths = new LinkedList<String>();
        private IDictionary<String, String> serviceDescriptorNamesBasedOnPath = new Dictionary<String, String>();

        private ICollection<String> syncDescriptorPaths = new LinkedList<String>();

        private IDictionary<String, SyncDescriptor> syncDescriptorsBasedOnName = new Dictionary<String, SyncDescriptor>();
        private IDictionary<String, SyncDescriptor> syncDescriptorsBasedOnPath = new Dictionary<String, SyncDescriptor>();

        private NotificationDescriptor notificationDescriptor = null;


        /// <summary>
        /// Add service descriptor path
        /// </summary>
        /// <param name="serviceDescriptorPath">Path of service descriptor</param>
        public void AddServiceDescriptorPath(String serviceDescriptorPath)
        {
            this.serviceDescriptorPaths.Add(serviceDescriptorPath);
        }


        /// <summary>
        /// Remove service descriptor path
        /// </summary>
        /// <param name="serviceDescriptorPath">Path of service descriptor</param>
        public void RemoveServiceDescriptorPath(String serviceDescriptorPath)
        {
            this.serviceDescriptorPaths.Remove(serviceDescriptorPath);
        }


        /// <summary>
        /// Get all service descriptor paths
        /// </summary>
        /// <returns>Path of service descriptor</returns>
        public IEnumerator<String> GetServiceDescriptorPaths()
        {
            return this.serviceDescriptorPaths.GetEnumerator();
        }


        /// <summary>
        /// Check whether service descriptor exists or not based on name 
        /// </summary>
        /// <param name="serviceDescriptorName">Name of service descriptor</param>
        /// <returns>(true/false) TRUE: If service descriptor exists | FALSE: If service descriptor does not exists</returns>
        public bool ContainServiceDescriptorPathBasedOnName(String serviceDescriptorName)
        {
            return this.serviceDescriptorNamesBasedOnPath.ContainsKey(serviceDescriptorName);
        }


        /// <summary>
        /// Check whether service descriptor exists or not based on path
        /// </summary>
        /// <param name="serviceDescriptorPath">Path of service descriptor</param>
        /// <returns>(true/false) TRUE: If service descriptor exists | FALSE: If service descriptor does not exists</returns>
        public bool ContainServiceDescriptorNameBasedOnPath(String serviceDescriptorPath)
        {
            return this.serviceDescriptorNamesBasedOnPath.ContainsKey(serviceDescriptorPath);
        }


        /// <summary>
        /// Get service descriptor path based on name
        /// </summary>
        /// <param name="serviceDescriptorName">Name of service descriptor</param>
        /// <returns>Path of service descriptor</returns>
        public String GetServiceDescriptorPathBasedOnName(String serviceDescriptorName)
        {

            if (this.ContainServiceDescriptorPathBasedOnName(serviceDescriptorName))
            {

                IEnumerator<String> serviceDescriptorPaths = this.serviceDescriptorNamesBasedOnPath.Keys.GetEnumerator();
                while (serviceDescriptorPaths.MoveNext())
                {

                    String serviceDescriptorPath = serviceDescriptorPaths.Current;

                    String foundServiceDescriptorName = this.serviceDescriptorNamesBasedOnPath[serviceDescriptorPath];
                    if (foundServiceDescriptorName.Equals(serviceDescriptorName, StringComparison.OrdinalIgnoreCase))
                    {
                        return this.serviceDescriptorNamesBasedOnPath[serviceDescriptorPath];
                    }
                }
            }

            return null;
        }


        /// <summary>
        /// Add service descriptor name based on path
        /// </summary>
        /// <param name="serviceDescriptorPath">Path of service descriptor</param>
        /// <param name="serviceDescriptoName">Name of service descriptor</param>
        public void AddServiceDescriptorNameBasedOnPath(String serviceDescriptorPath, String serviceDescriptoName)
        {
            this.serviceDescriptorNamesBasedOnPath.Add(serviceDescriptorPath, serviceDescriptoName);
        }


        /// <summary>
        /// Remove service descriptor name based on path
        /// </summary>
        /// <param name="serviceDescriptorPath">Path of service descriptor</param>
        public void RemoveServiceDescriptorNameBasedOnPath(String serviceDescriptorPath)
        {
            this.serviceDescriptorNamesBasedOnPath.Remove(serviceDescriptorPath);
        }



        /// <summary>
        /// Get sync descriptor paths
        /// </summary>
        /// <returns>Paths of sync descriptors</returns>
        public IEnumerator<String> GetSyncDescriptorPaths()
        {
            return this.syncDescriptorPaths.GetEnumerator();
        }


        /// <summary>
        /// Add sync descriptor path
        /// </summary>
        /// <param name="syncDescriptorPath">Path of sync descriptor</param>
        public void AddSyncDescriptorPath(String syncDescriptorPath)
        {
            this.syncDescriptorPaths.Add(syncDescriptorPath);
        }


        /// <summary>
        /// Remove sync descriptor path
        /// </summary>
        /// <param name="syncDescriptorPath">Path of sync descriptor</param>
        public void RemoveSyncDescriptorPath(String syncDescriptorPath)
        {
            this.syncDescriptorPaths.Remove(syncDescriptorPath);
        }


        /// <summary>
        /// Check whether sync descriptor path exists or not
        /// </summary>
        /// <param name="syncDescriptorPath">Path of sync descriptor</param>
        /// <returns>(true/false) TRUE: If sync descriptor path exists | FALSE: If sync descriptor path does not exists</returns>
        public bool ContainSyncDescriptorPath(String syncDescriptorPath)
        {
            return this.syncDescriptorPaths.Contains(syncDescriptorPath);
        }


        /// <summary>
        /// Get all sync descriptors
        /// </summary>
        /// <returns>Sync Descriptors</returns>
        public IEnumerator<SyncDescriptor> GetSyncDescriptors()
        {
            return this.syncDescriptorsBasedOnName.Values.GetEnumerator();
        }


        /// <summary>
        /// Get sync descriptor based on path
        /// </summary>
        /// <param name="syncDescriptorPath">Path of sync descriptor</param>
        /// <returns>Sync Descriptor</returns>
        public SyncDescriptor GetSyncDescriptorBasedOnPath(String syncDescriptorPath)
        {
            return this.syncDescriptorsBasedOnPath[syncDescriptorPath];
        }


        /// <summary>
        /// Get sync descriptor based on name
        /// </summary>
        /// <param name="syncDescriptorName">Name of sync descriptor</param>
        /// <returns>Sync Descriptor</returns>
        public SyncDescriptor GetSyncDescriptorBasedOnName(String syncDescriptorName)
        {
            return this.syncDescriptorsBasedOnName[syncDescriptorName];
        }


        /// <summary>
        /// Add sync descriptor
        /// </summary>
        /// <param name="syncDescriptorPath">Path of sync descriptor</param>
        /// <param name="syncDescriptor">Sync Descriptor</param>
        public void AddSyncDescriptor(String syncDescriptorPath, SyncDescriptor syncDescriptor)
        {
            this.syncDescriptorsBasedOnPath.Add(syncDescriptorPath, syncDescriptor);
            this.syncDescriptorsBasedOnName.Add(syncDescriptor.GetName(), syncDescriptor);
        }


        /// <summary>
        /// Check whether sync descriptor exists or not
        /// </summary>
        /// <param name="syncDescriptorName">Name of sync descriptor</param>
        /// <returns>(true/false) TRUE: If sync descriptor exists |  FALSE: If sync descriptor does not exists</returns>
        public bool ContainSyncDescriptor(String syncDescriptorName)
        {
            return this.syncDescriptorsBasedOnName.ContainsKey(syncDescriptorName);
        }


        /// <summary>
        /// Remove sync descriptor
        /// </summary>
        /// <param name="syncDescriptorName">Name of sync descriptor</param>
        public void RemoveSyncDescriptor(String syncDescriptorName)
        {
            this.syncDescriptorsBasedOnName.Remove(syncDescriptorName);
        }


        /// <summary>
        /// Get notification descriptor
        /// </summary>
        /// <returns>Notification Descriptor</returns>
        public NotificationDescriptor GetNotificationDescriptor()
        {
            return this.notificationDescriptor;
        }


        /// <summary>
        /// Set notification descriptor 
        /// </summary>
        /// <param name="notificationDescriptor">Notification Descriptor</param>
        public void SetNotificationDescriptor(NotificationDescriptor notificationDescriptor)
        {
            this.notificationDescriptor = notificationDescriptor;
        }
    }
}
