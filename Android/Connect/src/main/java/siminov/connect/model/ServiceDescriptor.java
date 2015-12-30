/**
 * [SIMINOV FRAMEWORK - CONNECT]
 * Copyright [2014-2016] [Siminov Software Solution LLP|support@siminov.com]
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

package siminov.connect.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import siminov.connect.Constants;
import siminov.core.model.IDescriptor;



/**
 * Exposes methods to GET and SET Service Descriptor information as per define in ServiceDescriptor.xml file by application.
 *	<p>
		<pre>
		
Example:
	{@code
	
	<service-descriptor>
	    
		<!-- General Service Properties -->
	    	<!-- Mandatory Field -->
	    <property name="name">name_of_service</property>
	    
	    	<!-- Optional Field -->
	    <property name="description">description_of_service</property>
	    
	    	<!-- Optional Field (DEFAULT: HTTP) -->
	    <property name="protocol">HTTP|HTTPS</property>
	    
	    	<!-- Mandatory Field -->
	    <property name="instance">address_of_instance</property>
	    
	    	<!-- Optional Field -->
	    <property name="port">port_number</property>
	
	    	<!-- Optional Field -->
	    <property name="context">context_of_service</property>
	         
		<!-- Requests -->
			<!-- Request -->
	    <request>
	            
			<request>
	
				<!-- General Request Properties -->
	         
		        	<!-- Mandatory Field -->
		       	<property name="name">name_of_request</property>
	         
	         		<!-- Mandatory Field -->
	         	<property name="type">GET|HEAD|POST|PUT|DELETE|TRACE|OPTIONS|CONNECT|PATCH</property>
	
	         		<!-- Mandatory Field -->
	         	<property name="api">full_request_path</property>
	
	         		<!-- Mandatory Field -->
	         	<property name="handler">handler_of_request</property>
	         	
	      			<!-- Optional Field (DEFAULT: SYNC)-->
	      		<property name="mode">SYNC|ASYNC</property>
	
	      		
	      					
	      		<!-- Query Parameters -->
	      			<!-- Query Parameter -->
	      		<query-parameters>
	          
	      			<query-parameter>
	      			    
	      			    <!-- Mandatory Field -->
	      			    <property name="name">name_of_query_parameter</property>
	      			    
	      			    <!-- Mandatory Field -->
	      			    <property name="value">value_of_query_parameter</property>
	      			    
	      			</query-parameter>
	      		
	      		</query-parameters>
	      
	      
	      		<!-- Header Parameters -->
	      			<!-- Header Parameter -->
	      		<header-parameters>
	          
	      			<header-parameter>
	      			    
	      			    <!-- Mandatory Field -->
	      			    <property name="name">name_of_header_parameter</property>
	      			    
	      			    <!-- Mandatory Field -->
	      			    <property name="value">value_of_header_parameter</property>
	      			    
	      			</header-parameter>
	      		
	        	</header-parameters>
	
	      		
	      		<!-- Stream of Data Under Request Body -->
	      			<!-- It is Optional Property -->
	      		<data-stream>stream_of_data</data-stream>	
	     	
	     	</request>
	    </requests>
	    
	</service-descriptor>

	}
	
		</pre>
	</p>
 */
public class ServiceDescriptor implements IDescriptor {
		
	private Map<String, String> properties = new HashMap<String, String> ();
	private Map<String, Request> requests = new HashMap<String, Request> ();
	
	/**
	 * Get service descriptor name
	 * @return Name of service descriptor
	 */
	public String getName() {
		return this.properties.get(Constants.SERVICE_DESCRIPTOR_NAME);
	}
	
	/**
	 * Set service descriptor name
	 * @param name Name of service descriptor
	 */
	public void setName(final String name) {
		this.properties.put(Constants.SERVICE_DESCRIPTOR_NAME, name);
	}
	
	/**
	 * Get description
	 * @return Description
	 */
	public String getDescription() {
		return this.properties.get(Constants.SERVICE_DESCRIPTOR_DESCRIPTION);
	}
	
	/**
	 * Set description
	 * @param description Description
	 */
	public void setDescription(final String description) {
		this.properties.put(Constants.SERVICE_DESCRIPTOR_DESCRIPTION, description);
	}
	
	/**
	 * Get protocol
	 * @return Protocol
	 */
	public String getProtocol() {
		return this.properties.get(Constants.SERVICE_DESCRIPTOR_PROTOCOL);
	}
	
	/**
	 * Set protocol
	 * @param protocol Protocol
	 */
	public void setProtocol(final String protocol) {
		this.properties.put(Constants.SERVICE_DESCRIPTOR_PROTOCOL, protocol);
	}
	
	/**
	 * Get instance
	 * @return Instance
	 */
	public String getInstance() {
		return this.properties.get(Constants.SERVICE_DESCRIPTOR_INSTANCE);
	}
	
	/**
	 * Set instance
	 * @param instance Instance
	 */
	public void setInstance(final String instance) {
		this.properties.put(Constants.SERVICE_DESCRIPTOR_INSTANCE, instance);
	}
	
	/**
	 * Get port
	 * @return Port
	 */
	public String getPort() {
		return this.properties.get(Constants.SERVICE_DESCRIPTOR_PORT);
	}
	
	/**
	 * Set port
	 * @param port Port
	 */
	public void setPort(final String port) {
		this.properties.put(Constants.SERVICE_DESCRIPTOR_PORT, port);
	}

	/**
	 * Get context
	 * @return Context
	 */
	public String getContext() {
		return this.properties.get(Constants.SERVICE_DESCRIPTOR_CONTEXT);
	}
	
	/**
	 * Set context 
	 * @param context Context
	 */
	public void setContext(String context) {
		this.properties.put(Constants.SERVICE_DESCRIPTOR_CONTEXT, context);
	}
	
	public Iterator<String> getProperties() {
		return this.properties.keySet().iterator();
	}
	
	public String getProperty(String name) {
		return this.properties.get(name);
	}

	public boolean containProperty(String name) {
		return this.properties.containsKey(name);
	}
	
	public void addProperty(String name, String value) {
		this.properties.put(name, value);
	}
	
	public void removeProperty(String name) {
		this.properties.remove(name);
	}

	/**
	 * Get all Request
	 * @return Request
	 */
	public Iterator<Request> getRequest() {
		return this.requests.values().iterator();
	}
	
	/**
	 * Get Request
	 * @param name Name of request 
	 * @return Request
	 */
	public Request getRequest(final String name) {
		return this.requests.get(name);
	}
	
	/**
	 * Add request
	 * @param request Request instance
	 */
	public void addRequest(final Request request) {
		this.requests.put(request.getName(), request);
	}
	
	/**
	 * Check whether request exists or not 
	 * @param name Name of request
	 * @return (true/false) TRUE: If request exists | FALSE: If request does not exists
	 */
	public boolean containRequest(final String name) {
		return this.containRequest(name);
	}
	
	/**
	 * Remove request 
	 * @param request Request instance
	 */
	public void removeRequest(final Request request) {
		this.requests.remove(request);
	}
	
	/**
	 * It defines the structure Web Service request	
	 * It exposes API to Get and Set request information
	 */
	public static class Request implements IDescriptor {
	
		private Map<String, String> properties = new HashMap<String, String>();

		private Map<String, QueryParameter> queryParameters = new HashMap<String, QueryParameter> ();
		private Map<String, HeaderParameter> headerParameters = new HashMap<String, HeaderParameter> ();
		
		private String dataStream = null;
		
		
		/**
		 * Get name of request
		 * @return Name of request
		 */
		public String getName() {
			return this.properties.get(Constants.SERVICE_DESCRIPTOR_REQUEST_NAME);
		}
		
		/**
		 * Set name of request
		 * @param name Name of request
		 */
		public void setName(final String name) {
			this.properties.put(Constants.SERVICE_DESCRIPTOR_REQUEST_NAME, name);
		}
		
		/**
		 * Get type of request
		 * @return Type of request
		 */
		public String getType() {
			return this.properties.get(Constants.SERVICE_DESCRIPTOR_REQUEST_TYPE);
		}
		
		/**
		 * Set type of request
		 * @param type Type of request
		 */
		public void setType(final String type) {
			this.properties.put(Constants.SERVICE_DESCRIPTOR_REQUEST_TYPE, type);
		}
		
		/**
		 * Get API name
		 * @return Name of API
		 */
		public String getApi() {
			return this.properties.get(Constants.SERVICE_DESCRIPTOR_REQUEST_API);
		}
		
		/**
		 * Set API name
		 * @param api Name of API
		 */
		public void setApi(final String api) {
			this.properties.put(Constants.SERVICE_DESCRIPTOR_REQUEST_API, api);
		}

		/**
		 * Get handler
		 * @return Handler
		 */
		public String getHandler() {
			return this.properties.get(Constants.SERVICE_DESCRIPTOR_REQUEST_HANDLER);
		}
		
		/**
		 * Set Handler
		 * @param handler Handler
		 */
		public void setHandler(String handler) {
			this.properties.put(Constants.SERVICE_DESCRIPTOR_REQUEST_HANDLER, handler);
		}
		
		/**
		 * Get mode of request
		 * @return Mode of request
		 */
		public String getMode() {
			return this.properties.get(Constants.SERVICE_DESCRIPTOR_REQUEST_MODE);
		}
		
		/**
		 * Set mode of request
		 * @param mode Mode of request
		 */
		public void setMode(final String mode) {
			this.properties.put(Constants.SERVICE_DESCRIPTOR_REQUEST_MODE, mode);
		}
		
		/**
		 * Get data stream
		 * @return Data Stream
		 */
		public String getDataStream() {
			return this.dataStream;
		}
		
		/**
		 * Set data stream
		 * @param dataStream Data Stream
		 */
		public void setDataStream(final String dataStream) {
			this.dataStream = dataStream;
		}
		
		public Iterator<String> getProperties() {
			return this.properties.keySet().iterator();
		}
		
		public String getProperty(String name) {
			return this.properties.get(name);
		}

		public boolean containProperty(String name) {
			return this.properties.containsKey(name);
		}
		
		public void addProperty(String name, String value) {
			this.properties.put(name, value);
		}
		
		public void removeProperty(String name) {
			this.properties.remove(name);
		}

		/**
		 * Get all query parameters
		 * @return Query Parameters
		 */
		public Iterator<QueryParameter> getQueryParameters() {
			return this.queryParameters.values().iterator();
		}
		
		/**
		 * Get query parameter
		 * @param name Name of query parameter
		 * @return Query Parameter
		 */
		public QueryParameter getQueryParameter(final String name) {
			return this.queryParameters.get(name);
		}
		
		/**
		 * Check whether query parameter exists or not
		 * @param name Name of query parameter
		 * @return (true/false) TRUE: If query parameter exists | FALSE: If query parameter does not exists
		 */
		public boolean containQueryParameter(final String name) {
			return this.queryParameters.containsKey(name);
		}
		
		/**
		 * Add query parameter
		 * @param queryParameter Query Parameter
		 */
		public void addQueryParameter(final QueryParameter queryParameter) {
			this.queryParameters.put(queryParameter.getName(), queryParameter);
		}

		/**
		 * Remove query parameter
		 * @param queryParameter Query Parameter
		 */
		public void removeQueryParameter(final QueryParameter queryParameter) {
			this.queryParameters.remove(queryParameter);
		}
		
		/**
		 * Get all header parameters
		 * @return Header Parameters
		 */
		public Iterator<HeaderParameter> getHeaderParameters() {
			return this.headerParameters.values().iterator();
		}
		
		/**
		 * Get header parameter
		 * @param name Name of header parameter
		 * @return Header Parameter
		 */
		public HeaderParameter getHeaderParameter(final String name) {
			return this.headerParameters.get(name);
		}
		
		/**
		 * Add header parameter
		 * @param headerParameter Header Parameter
		 */
		public void addHeaderParameter(final HeaderParameter headerParameter) {
			this.headerParameters.put(headerParameter.getName(), headerParameter);
		}
		
		/**
		 * Check whether header parameter exists or not
		 * @param name Name of header parameter
		 * @return (true/false) TRUE: If header parameter exists | FALSE: If header parameter does not exists
		 */
		public boolean containHeaderParameter(final String name) {
			return this.headerParameters.containsKey(name);
		}
		
		/**
		 * Remove header parameter
		 * @param headerParameter Header Parameter
		 */
		public void removeHeaderParameter(final HeaderParameter headerParameter) {
			this.queryParameters.remove(headerParameter);
		}

		/**
		 * It exposes APIs to Get and Set query parameter
		 */
		public static class QueryParameter implements IDescriptor {
			
			private Map<String, String> properties = new HashMap<String, String> (); 

			/**
			 * Get query parameter name
			 * @return Name of query parameter
			 */
			public String getName() {
				return this.properties.get(Constants.SERVICE_DESCRIPTOR_REQUEST_QUERY_PARAMETER_NAME);
			}
			
			/**
			 * Set name of query parameter
			 * @param name Name of query parameter
			 */
			public void setName(final String name) {
				this.properties.put(Constants.SERVICE_DESCRIPTOR_REQUEST_QUERY_PARAMETER_NAME, name);
			}
			
			/**
			 * Get value of query parameter
			 * @return Value of query parameter
			 */
			public String getValue() {
				return this.properties.get(Constants.SERVICE_DESCRIPTOR_REQUEST_QUERY_PARAMETER_VALUE);
			}
			
			/**
			 * Set values of query parameter
			 * @param value Query Parameter
			 */
			public void setValue(final String value) {
				this.properties.put(Constants.SERVICE_DESCRIPTOR_REQUEST_QUERY_PARAMETER_VALUE, value);
			}

			public Iterator<String> getProperties() {
				return this.properties.keySet().iterator();
			}
			
			public String getProperty(String name) {
				return this.properties.get(name);
			}

			public boolean containProperty(String name) {
				return this.properties.containsKey(name);
			}
			
			public void addProperty(String name, String value) {
				this.properties.put(name, value);
			}
			
			public void removeProperty(String name) {
				this.properties.remove(name);
			}
		}
		
		
		/**
		 * It exposes APIs to Get and Set header parameter
		 */
		public static class HeaderParameter implements IDescriptor {
			
			private Map<String, String> properties = new HashMap<String, String> (); 

			/**
			 * Get name of header parameter
			 * @return Name of header parameter
			 */
			public String getName() {
				return this.properties.get(Constants.SERVICE_DESCRIPTOR_REQUEST_HEADER_PARAMETER_NAME);
			}
			
			/**
			 * Set name of header parameter
			 * @param name Name of header parameter
			 */
			public void setName(final String name) {
				this.properties.put(Constants.SERVICE_DESCRIPTOR_REQUEST_HEADER_PARAMETER_NAME, name);
			}
			
			/**
			 * Get value of header parameter
			 * @return Value of header parameter
			 */
			public String getValue() {
				return this.properties.get(Constants.SERVICE_DESCRIPTOR_REQUEST_HEADER_PARAMETER_VALUE);
			}
			
			/**
			 * Set value of header parameter
			 * @param value Value of header parameter
			 */
			public void setValue(final String value) {
				this.properties.put(Constants.SERVICE_DESCRIPTOR_REQUEST_HEADER_PARAMETER_VALUE, value);
			}

			public Iterator<String> getProperties() {
				return this.properties.keySet().iterator();
			}
			
			public String getProperty(String name) {
				return this.properties.get(name);
			}

			public boolean containProperty(String name) {
				return this.properties.containsKey(name);
			}
			
			public void addProperty(String name, String value) {
				this.properties.put(name, value);
			}
			
			public void removeProperty(String name) {
				this.properties.remove(name);
			}
		}
	}
}
