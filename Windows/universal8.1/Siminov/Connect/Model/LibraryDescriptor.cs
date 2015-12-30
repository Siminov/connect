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
    /// Exposes methods to GET and SET Library Descriptor information as per define in LibraryDescriptor.xml file by application.
    /// Example:
    ///    <library-descriptor>
	///
    ///        <!-- General Properties Of Library -->
	///    
    ///        <!-- Mandatory Field -->
    ///        <property name="name">name_of_library</property>
	///	
    ///        <!-- Optional Field -->
    ///        <property name="description">description_of_library</property>
	///
	///	
	///	
    ///        <!-- Entity Descriptor Needed Under This Library Descriptor -->
	///	
    ///        <!-- Optional Field -->
    ///            <!-- Entity Descriptor -->
    ///        <entity-descriptors>
    ///            <entity-descriptor>name_of_database_descriptor.full_path_of_entity_descriptor_file</entity-descriptor>
    ///        </entity-descriptors>
	///	 
	///	
    ///        <!-- Service Descriptors -->
	///		
    ///        <!-- Optional Field -->
    ///            <!-- Service Descriptor -->
    ///        <service-descriptors>
    ///            <service-descriptor>full_path_of_service-descriptor_file</service-descriptor>
    ///        </service-descriptors>
	///	
	///	
    ///        <!-- Sync Descriptors -->
	///	
    ///        <!-- Optional Field -->
    ///            <!-- Sync Descriptor -->
    ///        <sync-descriptors>
    ///            <sync-descriptor>full_path_of_sync_descriptor_file</sync-descriptor>
    ///        </sync-descriptors>
	///	
	///	
    ///    </library-descriptor>
    /// </summary>

    public class LibraryDescriptor : Core.Model.LibraryDescriptor
    {
        private ICollection<String> serviceDescriptorPaths = new LinkedList<String>();
        private IDictionary<String, String> serviceDescriptorNamesBasedOnPath = new Dictionary<String, String>();

        private ICollection<String> syncDescriptorPaths = new LinkedList<String>();
        private IDictionary<String, String> syncDescriptorNamesBasedOnPath = new Dictionary<String, String>();


        /// <summary>
        /// Add service descriptor path
        /// </summary>
        /// <param name="serviceDescriptorPath">Path of service descriptor</param>
        public void AddServiceDescriptorPath(String serviceDescriptorPath)
        {
            this.serviceDescriptorPaths.Add(serviceDescriptorPath);
            this.serviceDescriptorNamesBasedOnPath.Add(serviceDescriptorPath, null);
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
        /// <returns>Service descriptor paths</returns>
        public IEnumerator<String> GetServiceDescriptorPaths()
        {
            return this.serviceDescriptorPaths.GetEnumerator();
        }


        /// <summary>
        /// Check whether service descriptor path based on name
        /// </summary>
        /// <param name="serviceDescriptorName">Name of service descriptor</param>
        /// <returns>(true/false) TRUE: If service descriptor path exists | FALSE: If service descriptor does not exists</returns>
        public bool ContainServiceDescriptorPathBasedOnName(String serviceDescriptorName)
        {
            return this.serviceDescriptorNamesBasedOnPath.ContainsKey(serviceDescriptorName);
        }


        /// <summary>
        /// Check whether service descriptor name exists based on path
        /// </summary>
        /// <param name="serviceDescriptorPath">Path of service descriptor</param>
        /// <returns>(true/false) TRUE: If service descriptor name exists | FALSE: If service descriptor name does not exists</returns>
        public bool ContainServiceDescriptorNameBasedOnPath(String serviceDescriptorPath)
        {
            return this.serviceDescriptorNamesBasedOnPath.ContainsKey(serviceDescriptorPath);
        }

        /// <summary>
        /// Get service descriptor path based on name
        /// </summary>
        /// <param name="serviceDescriptorName">Name of serivce descriptor</param>
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
        /// Add sync descriptor path
        /// </summary>
        /// <param name="syncDescriptorPath">Path of sync descriptor</param>
        public void AddSyncDescriptorPath(String syncDescriptorPath)
        {
            this.syncDescriptorPaths.Add(syncDescriptorPath);
            this.syncDescriptorNamesBasedOnPath.Add(syncDescriptorPath, null);
        }


        /// <summary>
        /// Remove sync descriptor path
        /// </summary>
        /// <param name="syncDescriptorPath">Path of sync descriptor</param>
        public void RemoveSyncDescriptorPath(String syncDescriptorPath)
        {
            this.serviceDescriptorPaths.Remove(syncDescriptorPath);
        }


        /// <summary>
        /// Get all sync descriptor paths
        /// </summary>
        /// <returns>Sync descriptor paths</returns>
        public IEnumerator<String> GetSyncDescriptorPaths()
        {
            return this.syncDescriptorPaths.GetEnumerator();
        }


        /// <summary>
        /// Check whether sync descriptor path based on name
        /// </summary>
        /// <param name="syncDescriptorName">Name of sync descriptor</param>
        /// <returns>(true/false) TRUE: If sync descriptor path exists | FALSE: If sync descriptor does not exists</returns>
        public bool ContainSyncDescriptorPathBasedOnName(String syncDescriptorName)
        {
            return this.serviceDescriptorNamesBasedOnPath.ContainsKey(syncDescriptorName);
        }


        /// <summary>
        /// Check whether sync descriptor name exists based on path
        /// </summary>
        /// <param name="syncDescriptorPath">Path of sync descriptor</param>
        /// <returns>(true/false) TRUE: If sync descriptor name exists | FALSE: If sync descriptor name does not exists</returns>
        public bool ContainSyncDescriptorNameBasedOnPath(String syncDescriptorPath)
        {
            return this.syncDescriptorNamesBasedOnPath.ContainsKey(syncDescriptorPath);
        }


        /// <summary>
        /// Get sync descriptor path based on name
        /// </summary>
        /// <param name="syncDescriptorName">Name of sync descriptor</param>
        /// <returns>Path of sync descriptor</returns>
        public String GetSyncDescriptorPathBasedOnName(String syncDescriptorName)
        {

            if (this.ContainSyncDescriptorPathBasedOnName(syncDescriptorName))
            {

                IEnumerator<String> syncDescriptorPaths = this.syncDescriptorNamesBasedOnPath.Keys.GetEnumerator();
                while (syncDescriptorPaths.MoveNext())
                {

                    String syncDescriptorPath = syncDescriptorPaths.Current;

                    String foundSyncDescriptorName = this.syncDescriptorNamesBasedOnPath[syncDescriptorPath];
                    if (foundSyncDescriptorName.Equals(syncDescriptorName, StringComparison.OrdinalIgnoreCase))
                    {
                        return this.syncDescriptorNamesBasedOnPath[syncDescriptorPath];
                    }
                }
            }

            return null;
        }


        /// <summary>
        /// Add sync descriptor name based on path
        /// </summary>
        /// <param name="syncDescriptorPath">Path of sync descriptor</param>
        /// <param name="syncDescriptoName">Name of sync descriptor</param>
        public void AddSyncDescriptorNameBasedOnPath(String syncDescriptorPath, String syncDescriptoName)
        {
            this.syncDescriptorNamesBasedOnPath.Add(syncDescriptorPath, syncDescriptoName);
        }


        /// <summary>
        /// Remove sync descriptor name based on path
        /// </summary>
        /// <param name="syncDescriptorPath">Path of sync descriptor</param>
        public void RemoveSyncDescriptorNameBasedOnPath(String syncDescriptorPath)
        {
            this.syncDescriptorNamesBasedOnPath.Remove(syncDescriptorPath);
        }
    }
}
