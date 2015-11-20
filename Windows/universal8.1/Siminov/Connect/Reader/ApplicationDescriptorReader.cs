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
    /// Exposes methods to parse Application Descriptor information as per define in ApplicationDescriptor.xml file by application.
	///	
    /// Example:
    ///        <siminov>
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

    public class ApplicationDescriptorReader : SiminovSAXDefaultHandler
    {

	    private ApplicationDescriptor applicationDescriptor = null;
	
	    private ResourceManager resourceManager = ResourceManager.GetInstance();

	    private StringBuilder tempValue = new StringBuilder();
	    private String propertyName = null;
	
	    private NotificationDescriptor notificationDescriptor = null;	
	
	    private bool isNotificationDescriptor = false;	

	    public ApplicationDescriptorReader() 
        {
		
		    /*
		     * Parse ApplicationDescriptor.
		     */
		    Stream applicationDescriptorStream = null;
		
		    try 
            {
                applicationDescriptorStream = FileUtils.ReadFile("Assets", Core.Constants.APPLICATION_DESCRIPTOR_FILE_NAME, FileUtils.INSTALLED_FOLDER);
		    } 
            catch(IOException ioException) 
            {
			    Log.Error(this.GetType().Name, "Constructor", "IOException caught while getting input stream of application descriptor, " + ioException.Message);
			    throw new DeploymentException(this.GetType().Name, "Constructor", "IOException caught while getting input stream of application descriptor, " + ioException.Message);
		    }
		
		    try 
            {
			    ParseMessage(applicationDescriptorStream);
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
		
		    if(localName.Equals(Core.Constants.APPLICATION_DESCRIPTOR_SIMINOV, StringComparison.OrdinalIgnoreCase)) 
            {
			    applicationDescriptor = new ApplicationDescriptor();
		    } 
            else if(localName.Equals(Core.Constants.APPLICATION_DESCRIPTOR_PROPERTY, StringComparison.OrdinalIgnoreCase)) 
            {
			    InitializeProperty(attributes);
		    } 
            else if(localName.Equals(Constants.NOTIFICATION_DESCRIPTOR, StringComparison.OrdinalIgnoreCase)) 
            {

			    isNotificationDescriptor = true;
			    notificationDescriptor = new NotificationDescriptor();
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
		
		    if(localName.Equals(Core.Constants.APPLICATION_DESCRIPTOR_PROPERTY, StringComparison.OrdinalIgnoreCase)) 
            {
			    ProcessProperty();
		    } 
            else if(localName.Equals(Core.Constants.APPLICATION_DESCRIPTOR_DATABASE_DESCRIPTOR)) 
            {
			    applicationDescriptor.AddDatabaseDescriptorPath(tempValue.ToString());
		    } 
            else if(localName.Equals(Core.Constants.APPLICATION_DESCRIPTOR_EVENT_HANDLER, StringComparison.OrdinalIgnoreCase)) 
            {
			
			    if(tempValue == null || tempValue.Length <= 0) 
                {
				    return;
			    }
			
			    applicationDescriptor.AddEvent(tempValue.ToString());
		    } 
            else if(localName.Equals(Core.Constants.APPLICATION_DESCRIPTOR_LIBRARY_DESCRIPTOR, StringComparison.OrdinalIgnoreCase)) 
            {
			
			    if(tempValue == null || tempValue.Length <= 0) 
                {
				    return;
			    }
			
			    applicationDescriptor.AddLibraryDescriptorPath(tempValue.ToString());
		    } 
            else if(localName.Equals(Constants.APPLICATION_DESCRIPTOR_SERVICE_DESCRIPTOR, StringComparison.OrdinalIgnoreCase)) 
            {
			    applicationDescriptor.AddServiceDescriptorPath(tempValue.ToString());
		    } 
            else if(localName.Equals(Constants.SYNC_DESCRIPTOR, StringComparison.OrdinalIgnoreCase)) 
            {
			    applicationDescriptor.AddSyncDescriptorPath(tempValue.ToString());
		    } 
            else if(localName.Equals(Constants.NOTIFICATION_DESCRIPTOR, StringComparison.OrdinalIgnoreCase)) 
            {
			    applicationDescriptor.SetNotificationDescriptor(notificationDescriptor);
			    isNotificationDescriptor = false;
		    }
	    }

        private void InitializeProperty(IDictionary<String, String> attributes)
        {
		    propertyName = attributes[Core.Constants.APPLICATION_DESCRIPTOR_NAME];
	    }

	    private void ProcessProperty() 
        {

		    if(isNotificationDescriptor) 
            {
			    notificationDescriptor.AddProperty(propertyName, tempValue.ToString());
		    } 
            else 
            {
			    applicationDescriptor.AddProperty(propertyName, tempValue.ToString());
		    }
	    }	
	
        /// <summary>
        /// Get application descriptor object. 
        /// </summary>
        /// <returns>Application Descriptor Object</returns>
	    public ApplicationDescriptor GetApplicationDescriptor() 
        {
		    return this.applicationDescriptor;
	    }
    }
}
