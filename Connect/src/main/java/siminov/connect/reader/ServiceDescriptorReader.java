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

package siminov.connect.reader;

import java.io.IOException;
import java.io.InputStream;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import siminov.connect.Constants;
import siminov.connect.model.ServiceDescriptor;
import siminov.connect.model.ServiceDescriptor.Request;
import siminov.connect.model.ServiceDescriptor.Request.HeaderParameter;
import siminov.connect.model.ServiceDescriptor.Request.QueryParameter;
import siminov.core.exception.DeploymentException;
import siminov.core.log.Log;
import siminov.core.reader.SiminovSAXDefaultHandler;
import siminov.core.resource.ResourceManager;
import android.content.Context;

/**
 * Exposes methods to parse Service Descriptor information as per define in ServiceDescriptor.si.xml file by application.
	<p>
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
 *
 */
public class ServiceDescriptorReader extends SiminovSAXDefaultHandler implements Constants {

	private StringBuilder tempValue = new StringBuilder();
	private ResourceManager resourceManager = ResourceManager.getInstance();
	
	private ServiceDescriptor serviceDescriptor = new ServiceDescriptor();
	
	private Request request = null;
	
	private QueryParameter queryParameter = null;
	private HeaderParameter headerParameter = null;
	
	private boolean isRequest = false;
	private boolean isQueryParameter = false;
	private boolean isHeaderParameter = false;

	private String propertyName = null;

	public ServiceDescriptorReader(String serviceDescriptorPath) {
		parse(serviceDescriptorPath);
	}
	
	public ServiceDescriptorReader(String libraryPackageName, String serviceDescriptorPath) {
		
		Context context = resourceManager.getApplicationContext();
		if(context == null) {
			Log.error(getClass().getName(), "Constructor", "Invalid context found.");
			throw new DeploymentException(getClass().getName(), "Constructor", "Invalid context found.");
		}

		/*
		 * Parse Adapter.
		 */
		InputStream adapterStream = null;
		
		try {
			adapterStream = getClass().getClassLoader().getResourceAsStream(libraryPackageName.replace(".", "/") + "/" + serviceDescriptorPath);
		} catch(Exception exception) {
			Log.debug(getClass().getName(), "Constructor", "IOException caught while getting input stream of konnect descriptor, " + exception.getMessage());
			throw new DeploymentException(getClass().getName(), "Constructor", "IOException caught while getting input stream of konnect descriptor, " + exception.getMessage());
		}
		
		try {
			parseMessage(adapterStream);
		} catch(Exception exception) {
			Log.error(getClass().getName(), "Constructor", "Exception caught while parsing KONNECT-DESCRIPTOR, " + exception.getMessage());
			throw new DeploymentException(getClass().getName(), "Constructor", "Exception caught while parsing KONNECT-DESCRIPTOR, " + exception.getMessage());
		}
	}
	
	private void parse(String fileName) {

		Context context = resourceManager.getApplicationContext();
		if(context == null) {
			Log.error(getClass().getName(), "Constructor", "Invalid context found.");
			throw new DeploymentException(getClass().getName(), "Constructor", "Invalid context found.");
		}

		/*
		 * Parse Service Descriptor.
		 */
		InputStream applicationDescriptorStream = null;
		
		try {
			applicationDescriptorStream = context.getAssets().open(fileName);
		} catch(IOException ioException) {
			Log.debug(getClass().getName(), "Constructor", "IOException caught while getting input stream of service descriptor, " + ioException.getMessage());
			throw new DeploymentException(getClass().getName(), "Constructor", "IOException caught while getting input stream of service descriptor, " + ioException.getMessage());
		}
		
		try {
			parseMessage(applicationDescriptorStream);
		} catch(Exception exception) {
			Log.error(getClass().getName(), "Constructor", "Exception caught while parsing APPLICATION-DESCRIPTOR, " + exception.getMessage());
			throw new DeploymentException(getClass().getName(), "Constructor", "Exception caught while parsing APPLICATION-DESCRIPTOR, " + exception.getMessage());
		}
	}
	
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

		tempValue = new StringBuilder();

		if(localName.equalsIgnoreCase(SERVICE_DESCRIPTOR_PROPERTY)) {
			propertyName = attributes.getValue(SERVICE_DESCRIPTOR_PROPERTY_NAME);
		} else if(localName.equalsIgnoreCase(SERVICE_DESCRIPTOR_REQUEST)) {
			
			request = new ServiceDescriptor.Request();
			isRequest = true;
		} else if(localName.equalsIgnoreCase(SERVICE_DESCRIPTOR_REQUEST_QUERY_PARAMETER)) {
		
			queryParameter = new QueryParameter();
			isQueryParameter = true;
		} else if(localName.equalsIgnoreCase(SERVICE_DESCRIPTOR_REQUEST_HEADER_PARAMETER)) {
			
			headerParameter = new HeaderParameter();
			isHeaderParameter = true;
		}
	}
	
	public void characters(char[] ch, int start, int length) throws SAXException {
		String value = new String(ch,start,length);
		
		if(value == null || value.length() <= 0 || value.equalsIgnoreCase(siminov.core.Constants.NEW_LINE)) {
			return;
		}
		
		value = value.trim();
		tempValue.append(value);
	}

	public void endElement(String uri, String localName, String qName) throws SAXException {
		
		if(localName.equalsIgnoreCase(SERVICE_DESCRIPTOR_PROPERTY)) {
			processProperty();
		} else if(localName.equalsIgnoreCase(SERVICE_DESCRIPTOR_REQUEST)) {
			
			serviceDescriptor.addRequest(request);
			
			request = null;
			isRequest = false;
		} else if(localName.equalsIgnoreCase(SERVICE_DESCRIPTOR_REQUEST_QUERY_PARAMETER)) {
			request.addQueryParameter(queryParameter);
			
			queryParameter = null;
			isQueryParameter = false;
		} else if(localName.equalsIgnoreCase(SERVICE_DESCRIPTOR_REQUEST_HEADER_PARAMETER)) {
			request.addHeaderParameter(headerParameter);
			
			headerParameter = null;
			isHeaderParameter = false;
		} else if(localName.equalsIgnoreCase(SERVICE_DESCRIPTOR_REQUEST_DATA_STREAM)) {
			
			request.setDataStream(tempValue.toString());
		}
	}
	
	private void processProperty() {
		
		if(isQueryParameter) {
			queryParameter.addProperty(propertyName, tempValue.toString());			
		} else if(isHeaderParameter) {
			headerParameter.addProperty(propertyName, tempValue.toString());
		} else if(isRequest) {
			request.addProperty(propertyName, tempValue.toString());
		} else {
			serviceDescriptor.addProperty(propertyName, tempValue.toString());
		}
	}

	/**
	 * Get service descriptor
	 * @return Service Descriptor
	 */
	public ServiceDescriptor getServiceDescriptor() {
		return this.serviceDescriptor;
	}
	
}