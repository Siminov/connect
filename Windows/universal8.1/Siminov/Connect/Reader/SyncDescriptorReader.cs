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


#if __MOBILE__
#define XAMARIN
#endif

#if !__MOBILE__
#define WINDOWS
#endif


using Siminov.Connect.Model;
using Siminov.Connect.Resource;
using Siminov.Core.Exception;
using Siminov.Core.Log;
using Siminov.Core.Reader;
using Siminov.Core.Utils;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Xml;

namespace Siminov.Connect.Reader
{


    /// <summary>
    /// Exposes methods to parse Sync Descriptor information as per define in SyncDescriptor.xml file by application.
    ///	
    /// Example:
    ///     <sync-descriptor>
	///	            
    ///                <!-- Mandatory Field -->
    ///            <property name="name">name_of_sync_handler</property>
	///				
    ///                <!-- Optional Field -->
    ///            <property name="sync_interval">sync_interval_in_millisecond</property>
	///	     				
    ///                <!-- Optional Field -->
    ///                    <!-- Default: SCREEN -->
    ///            <property name="type">INTERVAL|SCREEN|INTERVAL-SCREEN</property>
	///				
    ///            <!-- Service Descriptors -->
    ///                <!-- Service Descriptor -->
    ///            <service-descriptors>
	///	     		    
    ///                <service-descriptor>name_of_service_descriptor.name_of_api</service-descriptor>
	///	     		    
    ///            </service-descriptors>
	///	
    ///        </sync-descriptor>
    /// 
    /// </summary>
    public class SyncDescriptorReader : SiminovSAXDefaultHandler
    {
        private ResourceManager resourceManager = ResourceManager.GetInstance();

	    private StringBuilder tempValue = new StringBuilder();
	    private String propertyName = null;
	
	    private SyncDescriptor syncDescriptor = null;

	    public SyncDescriptorReader(String syncDescriptorPath) 
        {
		    Parse(syncDescriptorPath);
	    }

	    private void Parse(String syncDescriptorPath) 
        {
		
		    /*
		     * Parse Sync Descriptor.
		     */
            String syncDescriptorFileName = null;
            String syncDescriptorFilePath = null;

            syncDescriptorFilePath = syncDescriptorPath.Substring(0, syncDescriptorPath.LastIndexOf("/"));
            syncDescriptorFileName = syncDescriptorPath.Substring(syncDescriptorPath.LastIndexOf("/") + 1, (syncDescriptorPath.Length - syncDescriptorPath.LastIndexOf("/")) - 1);

		    Stream syncDescriptorStream = null;
		
		    try 
            {
                
                #if XAMARIN
                        syncDescriptorStream = FileUtils.ReadFileFromEmbeddedResources("Assets." + syncDescriptorFilePath + "." + syncDescriptorFileName);
				        if(syncDescriptorStream == null) 
				        {
					        syncDescriptorStream = FileUtils.ReadFileFromEmbeddedResources(syncDescriptorFilePath + "." + syncDescriptorFileName);					
				        }
                #elif WINDOWS
                    syncDescriptorStream = FileUtils.SearchFile(syncDescriptorFilePath, syncDescriptorFileName, FileUtils.INSTALLED_FOLDER);
                #endif
		    } 
            catch(IOException ioException) 
            {
			    Log.Debug(this.GetType().Name, "Constructor", "IOException caught while getting input stream of service descriptor, " + ioException.Message);
			    throw new DeploymentException(this.GetType().Name, "Constructor", "IOException caught while getting input stream of service descriptor, " + ioException.Message);
		    }
		
		    try 
            {
			    ParseMessage(syncDescriptorStream);
		    } 
            catch(System.Exception exception) 
            {
			    Log.Error(this.GetType().Name, "Constructor", "Exception caught while parsing APPLICATION-DESCRIPTOR, " + exception.Message);
			    throw new DeploymentException(this.GetType().Name, "Constructor", "Exception caught while parsing APPLICATION-DESCRIPTOR, " + exception.Message);
		    }
	    }
	
	
        public override void StartElement(XmlReader reader, IDictionary<String, String> attributes)
        {		

            String localName = reader.Name;
		    tempValue = new StringBuilder();
		
		    if(localName.Equals(Constants.SYNC_DESCRIPTOR_PROPERTY)) 
            {
			    propertyName = attributes[Core.Constants.APPLICATION_DESCRIPTOR_NAME];
		    } 
            else if(localName.Equals(Constants.SYNC_DESCRIPTOR, StringComparison.OrdinalIgnoreCase)) 
            {
			    syncDescriptor = new SyncDescriptor();
		    }
	    }
	
        public override void Characters(String value)
        {
		    if (value == null || value.Length <= 0 || value.Equals(Core.Constants.NEW_LINE, StringComparison.OrdinalIgnoreCase))
            {
                return;
            }

            value = value.Trim();
            tempValue.Append(value);
	    }

        public override void EndElement(String localName)
        {
            		
		    if(localName.Equals(Core.Constants.APPLICATION_DESCRIPTOR_PROPERTY)) 
            {
			    syncDescriptor.AddProperty(propertyName, tempValue.ToString());
		    } 
            else if(localName.Equals(Constants.SYNC_DESCRIPTOR_SERVICE_DESCRIPTOR)) 
            {
			    syncDescriptor.AddServiceDescriptorName(tempValue.ToString());
		    }
	    }
	


        /// <summary>
        /// Get application descriptor object. 
        /// </summary>
        /// <returns>Application Descriptor Object</returns>
	    public SyncDescriptor GetSyncDescriptor() 
        {
		    return this.syncDescriptor;
	    }
    }
}
