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
using System.Collections.Concurrent;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Siminov.Connect.Model
{

    /// <summary>
    /// Exposes methods to GET and SET Service Descriptor information as per define in ServiceDescriptor.xml file by application.
    /// Example:
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
    ///            <!-- Optional Field -->
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
    /// </summary>
    public class ServiceDescriptor : Core.Model.IDescriptor
    {
        private IDictionary<String, String> properties = new ConcurrentDictionary<String, String>();
        private IDictionary<String, Request> requests = new ConcurrentDictionary<String, Request>();
	

        /// <summary>
        /// Get service descriptor name
        /// </summary>
        /// <returns>Name of service descriptor</returns>
	    public String GetName() 
        {
		    return this.properties[Constants.SERVICE_DESCRIPTOR_NAME];
	    }
	

        /// <summary>
        /// Set service descriptor name
        /// </summary>
        /// <param name="name">Name of service descriptor</param>
	    public void SetName(String name) 
        {
		    this.properties.Add(Constants.SERVICE_DESCRIPTOR_NAME, name);
	    }
	

        /// <summary>
        /// Get description
        /// </summary>
        /// <returns>Description</returns>
	    public String GetDescription() 
        {
		    return this.properties[Constants.SERVICE_DESCRIPTOR_DESCRIPTION];
	    }
	

        /// <summary>
        /// Set description
        /// </summary>
        /// <param name="description">Description</param>
	    public void SetDescription(String description) 
        {
		    this.properties.Add(Constants.SERVICE_DESCRIPTOR_DESCRIPTION, description);
	    }
	

        /// <summary>
        /// Get protocol
        /// </summary>
        /// <returns>Protocol</returns>
	    public String GetProtocol() 
        {
		    return this.properties[Constants.SERVICE_DESCRIPTOR_PROTOCOL];
	    }
	

        /// <summary>
        /// Set protocol
        /// </summary>
        /// <param name="protocol">Protocol</param>
	    public void SetProtocol(String protocol) 
        {
		    this.properties.Add(Constants.SERVICE_DESCRIPTOR_PROTOCOL, protocol);
	    }
	

        /// <summary>
        /// Get instance
        /// </summary>
        /// <returns>Instance</returns>
	    public String GetInstance() 
        {
		    return this.properties[Constants.SERVICE_DESCRIPTOR_INSTANCE];
	    }
	

        /// <summary>
        /// Set instance
        /// </summary>
        /// <param name="instance">Instance</param>
	    public void SetInstance(String instance) 
        {
		    this.properties.Add(Constants.SERVICE_DESCRIPTOR_INSTANCE, instance);
	    }
	

        /// <summary>
        /// Get port
        /// </summary>
        /// <returns>Port</returns>
	    public String GetPort() 
        {
		    return this.properties[Constants.SERVICE_DESCRIPTOR_PORT];
	    }
	

        /// <summary>
        /// Set port
        /// </summary>
        /// <param name="port">Port</param>
	    public void SetPort(String port) 
        {
		    this.properties.Add(Constants.SERVICE_DESCRIPTOR_PORT, port);
	    }


        /// <summary>
        /// Get context
        /// </summary>
        /// <returns>Context</returns>
	    public String GetContext() 
        {
		    return this.properties[Constants.SERVICE_DESCRIPTOR_CONTEXT];
	    }
	

        /// <summary>
        /// Set context 
        /// </summary>
        /// <param name="context">Context</param>
	    public void SetContext(String context) 
        {
		    this.properties.Add(Constants.SERVICE_DESCRIPTOR_CONTEXT, context);
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
            if(this.properties.ContainsKey(name)) 
            {
                this.properties[name] = value;
            }
            else
            {
                this.properties.Add(name, value);
            }
	    }
	
	    public void RemoveProperty(String name) 
        {
		    this.properties.Remove(name);
	    }


        /// <summary>
        /// Get all Request
        /// </summary>
        /// <returns>Request</returns>
	    public IEnumerator<Request> GetRequest() 
        {
		    return this.requests.Values.GetEnumerator();
	    }
	

        /// <summary>
        /// Get Request
        /// </summary>
        /// <param name="name">Name of request </param>
        /// <returns>Request</returns>
	    public Request GetRequest(String name) 
        {
		    return this.requests[name];
	    }
	

        /// <summary>
        /// Add request
        /// </summary>
        /// <param name="request">Request instance</param>
	    public void AddRequest(Request request) 
        {
		    this.requests.Add(request.GetName(), request);
	    }
	

        /// <summary>
        /// Check whether request exists or not 
        /// </summary>
        /// <param name="name">Name of request</param>
        /// <returns>(true/false) TRUE: If request exists | FALSE: If request does not exists</returns>
	    public bool ContainRequest(String name) 
        {
		    return this.ContainRequest(name);
	    }
	

        /// <summary>
        /// Remove request 
        /// </summary>
        /// <param name="request">Request instance</param>
	    public void RemoveRequest(Request request) 
        {
		    this.requests.Remove(request.GetName());
	    }
	

        /// <summary>
        /// It defines the structure Web Service request	
        /// It exposes API to Get and Set request information
        /// </summary>
	    public class Request : Core.Model.IDescriptor 
        {
	
		    private IDictionary<String, String> properties = new Dictionary<String, String>();

		    private IDictionary<String, QueryParameter> queryParameters = new Dictionary<String, QueryParameter> ();
		    private IDictionary<String, HeaderParameter> headerParameters = new Dictionary<String, HeaderParameter> ();
		
		    private String dataStream = null;
		
		

            /// <summary>
            /// Get name of request
            /// </summary>
            /// <returns>Name of request</returns>
		    public String GetName() 
            {
			    return this.properties[Constants.SERVICE_DESCRIPTOR_REQUEST_NAME];
		    }
		

            /// <summary>
            /// Set name of request
            /// </summary>
            /// <param name="name">Name of request</param>
		    public void SetName(String name) 
            {
			    this.properties.Add(Constants.SERVICE_DESCRIPTOR_REQUEST_NAME, name);
		    }
		

            /// <summary>
            /// Get type of request
            /// </summary>
            /// <returns>Type of request</returns>
		    public String GetType() 
            {
			    return this.properties[Constants.SERVICE_DESCRIPTOR_REQUEST_TYPE];
		    }
		

            /// <summary>
            /// Set type of request
            /// </summary>
            /// <param name="type">Type of request</param>
		    public void SetType(String type) 
            {
			    this.properties.Add(Constants.SERVICE_DESCRIPTOR_REQUEST_TYPE, type);
		    }
		

            /// <summary>
            /// Get API name
            /// </summary>
            /// <returns>Name of API</returns>
		    public String GetApi() 
            {
			    return this.properties[Constants.SERVICE_DESCRIPTOR_REQUEST_API];
		    }
		

            /// <summary>
            /// Set API name
            /// </summary>
            /// <param name="api">Name of API</param>
		    public void SetApi(String api) 
            {
			    this.properties.Add(Constants.SERVICE_DESCRIPTOR_REQUEST_API, api);
		    }


            /// <summary>
            /// Get handler
            /// </summary>
            /// <returns>Handler</returns>
		    public String GetHandler() 
            {
			    return this.properties[Constants.SERVICE_DESCRIPTOR_REQUEST_HANDLER];
		    }
		

            /// <summary>
            /// Set Handler
            /// </summary>
            /// <param name="handler">Handler</param>
		    public void SetHandler(String handler) 
            {
			    this.properties.Add(Constants.SERVICE_DESCRIPTOR_REQUEST_HANDLER, handler);
		    }
		

            /// <summary>
            /// Get mode of request
            /// </summary>
            /// <returns>Mode of request</returns>
		    public String GetMode() 
            {
			    return this.properties[Constants.SERVICE_DESCRIPTOR_REQUEST_MODE];
		    }
		

            /// <summary>
            /// Set mode of request
            /// </summary>
            /// <param name="mode">Mode of request</param>
		    public void SetMode(String mode) {
			    this.properties.Add(Constants.SERVICE_DESCRIPTOR_REQUEST_MODE, mode);
		    }
		

            /// <summary>
            /// Get data stream
            /// </summary>
            /// <returns>Data Stream</returns>
		    public String GetDataStream() 
            {
			    return this.dataStream;
		    }
		

            /// <summary>
            /// Set data stream
            /// </summary>
            /// <param name="dataStream">Data Stream</param>
		    public void SetDataStream(String dataStream) 
            {
			    this.dataStream = dataStream;
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
                if (this.properties.ContainsKey(name)) 
                {
                    this.properties[name] = value;
                }
                else
                {
                    this.properties.Add(name, value);
                }
		    }
		
		    public void RemoveProperty(String name) 
            {
			    this.properties.Remove(name);
		    }


            /// <summary>
            /// Get all query parameters
            /// </summary>
            /// <returns>Query Parameters</returns>
		    public IEnumerator<QueryParameter> GetQueryParameters() 
            {
			    return this.queryParameters.Values.GetEnumerator();
		    }
		

            /// <summary>
            /// Get query parameter
            /// </summary>
            /// <param name="name">Name of query parameter</param>
            /// <returns>Query Parameter</returns>
		    public QueryParameter GetQueryParameter(String name) 
            {
			    return this.queryParameters[name];
		    }
		

            /// <summary>
            /// Check whether query parameter exists or not
            /// </summary>
            /// <param name="name">Name of query parameter</param>
            /// <returns>(true/false) TRUE: If query parameter exists | FALSE: If query parameter does not exists</returns>
		    public bool ContainQueryParameter(String name) 
            {
			    return this.queryParameters.ContainsKey(name);
		    }
		

            /// <summary>
            /// Add query parameter
            /// </summary>
            /// <param name="queryParameter">Query Parameter</param>
		    public void AddQueryParameter(QueryParameter queryParameter) 
            {
			    this.queryParameters.Add(queryParameter.GetName(), queryParameter);
		    }


            /// <summary>
            /// Remove query parameter
            /// </summary>
            /// <param name="queryParameter">Query Parameter</param>
		    public void RemoveQueryParameter(QueryParameter queryParameter) 
            {
			    this.queryParameters.Remove(queryParameter.GetName());
		    }
		

            /// <summary>
            /// Get all header parameters
            /// </summary>
            /// <returns>Header Parameters</returns>
		    public IEnumerator<HeaderParameter> GetHeaderParameters() 
            {
			    return this.headerParameters.Values.GetEnumerator();
		    }
		

            /// <summary>
            /// Get header parameter
            /// </summary>
            /// <param name="name">Name of header parameter</param>
            /// <returns>Header Parameter</returns>
		    public HeaderParameter GetHeaderParameter(String name) 
            {
			    return this.headerParameters[name];
		    }
		

            /// <summary>
            /// Add header parameter
            /// </summary>
            /// <param name="headerParameter">Header Parameter</param>
		    public void AddHeaderParameter(HeaderParameter headerParameter) 
            {
			    this.headerParameters.Add(headerParameter.GetName(), headerParameter);
		    }
		

            /// <summary>
            /// Check whether header parameter exists or not
            /// </summary>
            /// <param name="name">Name of header parameter</param>
            /// <returns>(true/false) TRUE: If header parameter exists | FALSE: If header parameter does not exists</returns>
		    public bool ContainHeaderParameter(String name) 
            {
			    return this.headerParameters.ContainsKey(name);
		    }
		

            /// <summary>
            /// Remove header parameter
            /// </summary>
            /// <param name="headerParameter">Header Parameter</param>
		    public void RemoveHeaderParameter(HeaderParameter headerParameter) 
            {
			    this.queryParameters.Remove(headerParameter.GetName());
		    }


            /// <summary>
            /// It exposes APIs to Get and Set query parameter
            /// </summary>
		    public class QueryParameter : Core.Model.IDescriptor 
            {
			
			    private IDictionary<String, String> properties = new Dictionary<String, String> (); 
			

                /// <summary>
                /// Get query parameter name
                /// </summary>
                /// <returns>Name of query parameter</returns>
			    public String GetName() 
                {
				    return this.properties[Constants.SERVICE_DESCRIPTOR_REQUEST_QUERY_PARAMETER_NAME];
			    }
			

                /// <summary>
                /// Set name of query parameter
                /// </summary>
                /// <param name="name">Name of query parameter</param>
			    public void SetName(String name) 
                {
                    this.properties.Add(Constants.SERVICE_DESCRIPTOR_REQUEST_QUERY_PARAMETER_NAME, name);
			    }
			

                /// <summary>
                /// Get value of query parameter
                /// </summary>
                /// <returns>Value of query parameter</returns>
			    public String GetValue() 
                {
				    return this.properties[Constants.SERVICE_DESCRIPTOR_REQUEST_QUERY_PARAMETER_VALUE];
			    }
			

                /// <summary>
                /// Set values of query parameter
                /// </summary>
                /// <param name="value">Query Parameter</param>
			    public void SetValue(String value) 
                {
                    this.properties[Constants.SERVICE_DESCRIPTOR_REQUEST_QUERY_PARAMETER_VALUE] = value;
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
		    }
		
		

            /// <summary>
            /// It exposes APIs to Get and Set header parameter
            /// </summary>
		    public class HeaderParameter : Core.Model.IDescriptor 
            {
			
			    private IDictionary<String, String> properties = new Dictionary<String, String> (); 


                /// <summary>
                /// Get name of header parameter
                /// </summary>
                /// <returns>Name of header parameter</returns>
			    public String GetName() 
                {
				    return this.properties[Constants.SERVICE_DESCRIPTOR_REQUEST_HEADER_PARAMETER_NAME];
			    }
			

                /// <summary>
                /// Set name of header parameter
                /// </summary>
                /// <param name="name">Name of header parameter</param>
			    public void SetName(String name) 
                {
                    this.properties.Add(Constants.SERVICE_DESCRIPTOR_REQUEST_HEADER_PARAMETER_NAME, name);
			    }
			

                /// <summary>
                /// Get value of header parameter
                /// </summary>
                /// <returns>Value of header parameter</returns>
			    public String GetValue() 
                {
                    return this.properties[Constants.SERVICE_DESCRIPTOR_REQUEST_HEADER_PARAMETER_VALUE];
			    }
			

                /// <summary>
                /// Set value of header parameter
                /// </summary>
                /// <param name="value">Value of header parameter</param>
			    public void SetValue(String value) 
                {
                    this.properties[Constants.SERVICE_DESCRIPTOR_REQUEST_HEADER_PARAMETER_VALUE] = value;
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
		    }
	    }
    }
}
