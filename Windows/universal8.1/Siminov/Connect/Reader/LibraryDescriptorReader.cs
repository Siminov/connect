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
    ///      * Exposes methods to parse Library Descriptor information as per define in LibraryDescriptor.xml file by application.
	///	
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
    ///
    /// </summary>
    public class LibraryDescriptorReader : SiminovSAXDefaultHandler
    {

	    private StringBuilder tempValue = new StringBuilder();
	    private LibraryDescriptor libraryDescriptor = new LibraryDescriptor();
	
	    private String propertyName = "";
	
	    public LibraryDescriptorReader(String libraryName) 
        {
		    if(libraryName == null || libraryName.Length <= 0) 
            {
			    Log.Error(this.GetType().Name, "Constructor", "Invalid Library Name Found.");
			    throw new SiminovException(this.GetType().Name, "Constructor", "Invalid Library Name Found.");
		    }
		
            Stream libraryDescriptorStream = FileUtils.SearchFile(libraryName, Constants.LIBRARY_DESCRIPTOR_FILE_NAME, FileUtils.INSTALLED_FOLDER);
		    if(libraryDescriptorStream == null) 
            {
			    Log.Error(this.GetType().Name, "Constructor", "Invalid Library Descriptor Stream Found, LIBRARY-NAME: " + libraryName + ", PATH: " + libraryName.Replace(".", "/") + "/" + Constants.LIBRARY_DESCRIPTOR_FILE_NAME);
			    throw new SiminovException(this.GetType().Name, "Constructor", "Invalid Library Descriptor Stream Found, LIBRARY-NAME: " + libraryName + ", PATH: " + libraryName.Replace(".", "/") + "/" + Constants.LIBRARY_DESCRIPTOR_FILE_NAME);
		    }
		
		    try 
            {
			    ParseMessage(libraryDescriptorStream);
		    } 
            catch(System.Exception exception) 
            {
			    Log.Error(this.GetType().Name, "Constructor", "Exception caught while parsing LIBRARY-DESCRIPTOR: " + libraryName + ", " + exception.Message);
			    throw new SiminovException(this.GetType().Name, "Constructor", "Exception caught while parsing LIBRARY-DESCRIPTOR: " + libraryName + ", " + exception.Message);
		    }
	    }
	
        public override void StartElement(XmlReader reader, IDictionary<String, String> attributes)
        {		

            String localName = reader.Name;
		    tempValue = new StringBuilder();
		
		    if(localName.Equals(Constants.LIBRARY_DESCRIPTOR_PROPERTY, StringComparison.OrdinalIgnoreCase)) 
            {
			    InitializeProperty(attributes);
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
		
		    if(localName.Equals(Constants.LIBRARY_DESCRIPTOR_PROPERTY, StringComparison.OrdinalIgnoreCase)) 
            {
			    libraryDescriptor.AddProperty(propertyName, tempValue.ToString());
		    } 
            else if(localName.Equals(Constants.LIBRARY_DESCRIPTOR_SERVICE_DESCRIPTOR, StringComparison.OrdinalIgnoreCase)) 
            {
			    libraryDescriptor.AddServiceDescriptorPath(tempValue.ToString());
		    }
	    }

        private void InitializeProperty(IDictionary<String, String> attributes) 
        {
		    propertyName = attributes[Constants.LIBRARY_DESCRIPTOR_PROPERTY_NAME];
	    }
	

        /// <summary>
        /// Get library descriptor
        /// </summary>
        /// <returns>Library Descriptor</returns>
	    public LibraryDescriptor GetLibraryDescriptor() 
        {
		    return this.libraryDescriptor;
	    }

    }
}
