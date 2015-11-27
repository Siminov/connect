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


#if __MOBILE__
#define XAMARIN
#endif

#if !__MOBILE__
#define WINDOWS
#endif


using Siminov.Connect.Model;
using Siminov.Connect.Resource;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.IO;
using Siminov.Core.Utils;
using Siminov.Core.Log;
using Siminov.Core.Exception;
using Siminov.Core.Reader;
using System.Xml;

namespace Siminov.Connect.Reader
{


    /// <summary>
    ///     Exposes methods to parse Service Descriptor information as per define in ServiceDescriptor.xml file by application.
	///		
    ///    <service-descriptor>
	///    
    ///        <!-- General Service Properties -->
    ///            <!-- Mandatory Field -->
    ///        <property name="name">name_of_service</property>
	///    
    ///            <!-- Optional Field -->
    ///        <property name="description">description_of_service</property>
	///    
    ///            <!-- Optional Field (DEFAULT: HTTP) -->
    ///        <property name="protocol">HTTP|HTTPS</property>
	///    
    ///            <!-- Mandatory Field -->
    ///        <property name="instance">address_of_instance</property>
	///    
    ///             <!-- Optional Field -->
    ///        <property name="port">port_number</property>
	///
    ///            <!-- Optional Field -->
    ///        <property name="context">context_of_service</property>
	///         
    ///        <!-- Requests -->
    ///            <!-- Request -->
    ///        <request>
	///            
    ///            <request>
	///
    ///                <!-- General Request Properties -->
	///         
    ///                    <!-- Mandatory Field -->
    ///                <property name="name">name_of_request</property>
	///         
    ///                    <!-- Mandatory Field -->
    ///                <property name="type">GET|HEAD|POST|PUT|DELETE|TRACE|OPTIONS|CONNECT|PATCH</property>
	///
    ///                    <!-- Mandatory Field -->
    ///                <property name="api">full_request_path</property>
	///
    ///                    <!-- Mandatory Field -->
    ///                <property name="handler">handler_of_request</property>
	///         	
    ///                    <!-- Optional Field (DEFAULT: SYNC)-->
    ///                <property name="mode">SYNC|ASYNC</property>
	///
	///      		
	///      					
    ///                <!-- Query Parameters -->
    ///                    <!-- Query Parameter -->
    ///                <query-parameters>
	///          
    ///                    <query-parameter>
	///      			    
    ///                        <!-- Mandatory Field -->
    ///                        <property name="name">name_of_query_parameter</property>
	///      			    
    ///                        <!-- Mandatory Field -->
    ///                        <property name="value">value_of_query_parameter</property>
	///      			    
    ///                    </query-parameter>
	///      		
    ///                </query-parameters>
	///      
	///      
    ///                <!-- Header Parameters -->
    ///                    <!-- Header Parameter -->
    ///                <header-parameters>
	///          
    ///                    <header-parameter>
	///      			    
    ///                        <!-- Mandatory Field -->
    ///                        <property name="name">name_of_header_parameter</property>
	///      			    
    ///                        <!-- Mandatory Field -->
    ///                        <property name="value">value_of_header_parameter</property>
	///      			    
    ///                    </header-parameter>
	///      		
    ///                </header-parameters>
	///
	///      		
    ///                <!-- Stream of Data Under Request Body -->
    ///                    <!-- It is Optional Property -->
    ///                <data-stream>stream_of_data</data-stream>	
	///     	
    ///            </request>
    ///        </requests>
	///    
    ///    </service-descriptor>
    ///
    /// </summary>
    public class ServiceDescriptorReader : SiminovSAXDefaultHandler
    {

	    private StringBuilder tempValue = new StringBuilder();
	    private ResourceManager resourceManager = ResourceManager.GetInstance();
	
	    private ServiceDescriptor serviceDescriptor = new ServiceDescriptor();
	
	    private Connect.Model.ServiceDescriptor.Request request = null;
	
	    private Connect.Model.ServiceDescriptor.Request.QueryParameter queryParameter = null;
	    private Connect.Model.ServiceDescriptor.Request.HeaderParameter headerParameter = null;
	
	    private bool isRequest = false;
        private bool isQueryParameter = false;
        private bool isHeaderParameter = false;

	    private String propertyName = null;

	    public ServiceDescriptorReader(String serviceDescriptorPath) 
        {
		    Parse(serviceDescriptorPath);
	    }
	
	    public ServiceDescriptorReader(String libraryPackageName, String serviceDescriptorPath) 
        {

            String serviceDescriptorFileName = null;
            String serviceDescriptorFilePath = null;

            serviceDescriptorFilePath = serviceDescriptorPath.Substring(0, serviceDescriptorPath.LastIndexOf("/"));
            serviceDescriptorFileName = serviceDescriptorPath.Substring(serviceDescriptorPath.LastIndexOf("/") + 1, (serviceDescriptorPath.Length - serviceDescriptorPath.LastIndexOf("/")) - 1);

		    /*
		     * Parse Adapter.
		     */
            Stream serviceDescriptorStream = null;
		
		    try 
            {
                
                #if XAMARIN
                        serviceDescriptorStream = FileUtils.ReadFileFromEmbeddedResources("Assets." + serviceDescriptorFilePath + "." + serviceDescriptorFileName);
				        if(serviceDescriptorStream == null) 
				        {
					        serviceDescriptorStream = FileUtils.ReadFileFromEmbeddedResources(serviceDescriptorFilePath + "." + serviceDescriptorFileName);					
				        }
                #elif WINDOWS
                    serviceDescriptorStream = FileUtils.SearchFile(serviceDescriptorFilePath, serviceDescriptorFileName, FileUtils.INSTALLED_FOLDER);
                #endif

		    } 
            catch(System.Exception exception) 
            {
			    Log.Debug(this.GetType().Name, "Constructor", "IOException caught while getting input stream of konnect descriptor, " + exception.Message);
			    throw new DeploymentException(this.GetType().Name, "Constructor", "IOException caught while getting input stream of konnect descriptor, " + exception.Message);
		    }
		
		    try 
            {
                ParseMessage(serviceDescriptorStream);
		    } 
            catch(System.Exception exception) 
            {
			    Log.Error(this.GetType().Name, "Constructor", "Exception caught while parsing KONNECT-DESCRIPTOR, " + exception.Message);
			    throw new DeploymentException(this.GetType().Name, "Constructor", "Exception caught while parsing KONNECT-DESCRIPTOR, " + exception.Message);
		    }
	    }
	
	    private void Parse(String fileName) 
        {

            String serviceDescriptorFileName = null;
            String serviceDescriptorFilePath = null;

            serviceDescriptorFilePath = fileName.Substring(0, fileName.LastIndexOf("/"));
            serviceDescriptorFileName = fileName.Substring(fileName.LastIndexOf("/") + 1, (fileName.Length - fileName.LastIndexOf("/")) - 1);
            
            /*
             * Parse Service Descriptor.
             */
            Stream serviceDescriptorStream = null;
		
		    try 
            {
                #if XAMARIN
				    serviceDescriptorStream = FileUtils.ReadFileFromEmbeddedResources("Assets." + serviceDescriptorFilePath + "." + serviceDescriptorFileName);
				    if(serviceDescriptorStream == null) 
				    {
					    serviceDescriptorStream = FileUtils.ReadFileFromEmbeddedResources(serviceDescriptorFilePath + "." + serviceDescriptorFileName);					
				    }
                #elif WINDOWS
                    serviceDescriptorStream = FileUtils.SearchFile(serviceDescriptorFilePath, serviceDescriptorFileName, FileUtils.INSTALLED_FOLDER);
                #endif
            } 
            catch(IOException ioException) 
            {
			    Log.Debug(this.GetType().Name, "Constructor", "IOException caught while getting input stream of service descriptor, " + ioException.Message);
			    throw new DeploymentException(this.GetType().Name, "Constructor", "IOException caught while getting input stream of service descriptor, " + ioException.Message);
		    }
		
		    //try 
            {
                ParseMessage(serviceDescriptorStream);
		    } 
            //catch(System.Exception exception) 
            //{
			//    Log.Error(this.GetType().Name, "Constructor", "Exception caught while parsing APPLICATION-DESCRIPTOR, " + exception.Message);
			//    throw new DeploymentException(this.GetType().Name, "Constructor", "Exception caught while parsing APPLICATION-DESCRIPTOR, " + exception.Message);
		    //}
	    }
	
        public override void StartElement(XmlReader reader, IDictionary<String, String> attributes)
        {
            String localName = reader.Name;   
            tempValue = new StringBuilder();

		    if(localName.Equals(Constants.SERVICE_DESCRIPTOR_PROPERTY, StringComparison.OrdinalIgnoreCase)) 
            {
                propertyName = attributes[Constants.SERVICE_DESCRIPTOR_PROPERTY_NAME];
            } 
            else if(localName.Equals(Constants.SERVICE_DESCRIPTOR_REQUEST, StringComparison.OrdinalIgnoreCase)) 
            {
			    request = new ServiceDescriptor.Request();
			    isRequest = true;
		    } 
            else if(localName.Equals(Constants.SERVICE_DESCRIPTOR_REQUEST_QUERY_PARAMETER, StringComparison.OrdinalIgnoreCase)) 
            {
			    queryParameter = new Connect.Model.ServiceDescriptor.Request.QueryParameter();
                isQueryParameter = true;
		    } 
            else if(localName.Equals(Constants.SERVICE_DESCRIPTOR_REQUEST_HEADER_PARAMETER, StringComparison.OrdinalIgnoreCase)) 
            {
			    headerParameter = new Connect.Model.ServiceDescriptor.Request.HeaderParameter();
                isHeaderParameter = true;
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
		
		    if(localName.Equals(Constants.SERVICE_DESCRIPTOR_PROPERTY, StringComparison.OrdinalIgnoreCase)) 
            {
			    ProcessProperty();
		    } 
            else if(localName.Equals(Constants.SERVICE_DESCRIPTOR_REQUEST, StringComparison.OrdinalIgnoreCase)) 
            {
			    serviceDescriptor.AddRequest(request);
			
			    request = null;
			    isRequest = false;
		    } 
            else if(localName.Equals(Constants.SERVICE_DESCRIPTOR_REQUEST_QUERY_PARAMETER, StringComparison.OrdinalIgnoreCase)) 
            {			
			    request.AddQueryParameter(queryParameter);

                queryParameter = null;
                isQueryParameter = false;
		    } 
            else if(localName.Equals(Constants.SERVICE_DESCRIPTOR_REQUEST_HEADER_PARAMETER, StringComparison.OrdinalIgnoreCase)) 
            {			
			    request.AddHeaderParameter(headerParameter);

                headerParameter = null;
                isHeaderParameter = false;
		    } 
            else if(localName.Equals(Constants.SERVICE_DESCRIPTOR_REQUEST_DATA_STREAM, StringComparison.OrdinalIgnoreCase)) 
            {
			    request.SetDataStream(tempValue.ToString());
		    }
	    }

	    private void ProcessProperty() 
        {
		
            if(isQueryParameter) 
            {
                queryParameter.AddProperty(propertyName, tempValue.ToString());
            }
            else if(isHeaderParameter) 
            {
                headerParameter.AddProperty(propertyName, tempValue.ToString());
            }
		    else if(isRequest) 
            {
			    request.AddProperty(propertyName, tempValue.ToString());
		    } 
            else 
            {
			    serviceDescriptor.AddProperty(propertyName, tempValue.ToString());
		    }
	    }


        /// <summary>
        /// Get service descriptor
        /// </summary>
        /// <returns>Service Descriptor</returns>
	    public ServiceDescriptor GetServiceDescriptor() 
        {
		    return this.serviceDescriptor;
	    }
    }
}
