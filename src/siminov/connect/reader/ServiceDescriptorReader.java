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
import siminov.connect.model.ServiceDescriptor.API;
import siminov.connect.model.ServiceDescriptor.API.HeaderParameter;
import siminov.connect.model.ServiceDescriptor.API.QueryParameter;
import siminov.orm.exception.DeploymentException;
import siminov.orm.log.Log;
import siminov.orm.reader.SiminovSAXDefaultHandler;
import siminov.orm.resource.ResourceManager;
import android.content.Context;

public class ServiceDescriptorReader extends SiminovSAXDefaultHandler implements Constants {

	private StringBuilder tempValue = new StringBuilder();
	private ResourceManager resourceManager = ResourceManager.getInstance();
	
	private ServiceDescriptor serviceDescriptor = new ServiceDescriptor();
	
	private API api = null;
	
	private QueryParameter queryParameter = null;
	private HeaderParameter headerParameter = null;
	
	private boolean isApi = false;

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
		 * Parse HybridDescriptor.
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
			initializeProperty(attributes);
		} else if(localName.equalsIgnoreCase(SERVICE_DESCRIPTOR_API)) {
			
			api = new ServiceDescriptor.API();
			isApi = true;
		} else if(localName.equalsIgnoreCase(SERVICE_DESCRIPTOR_API_QUERY_PARAMETER)) {
		
			queryParameter = new QueryParameter();
			queryParameter.setName(attributes.getValue(SERVICE_DESCRIPTOR_API_QUERY_PARAMETER_NAME_ATTRIBUTE));
			queryParameter.setValue(tempValue.toString());
			
			api.addQueryParameter(queryParameter);
		} else if(localName.equalsIgnoreCase(SERVICE_DESCRIPTOR_API_HEADER_PARAMETER)) {
			
			headerParameter = new HeaderParameter();
			headerParameter.setName(attributes.getValue(SERVICE_DESCRIPTOR_API_HEADER_PARAMETER_NAME_ATTRIBUTE));
			headerParameter.setValue(tempValue.toString());
			
			api.addHeaderParameter(headerParameter);
		}
	}
	
	public void characters(char[] ch, int start, int length) throws SAXException {
		String value = new String(ch,start,length);
		
		if(value == null || value.length() <= 0 || value.equalsIgnoreCase(siminov.orm.Constants.NEW_LINE)) {
			return;
		}
		
		value = value.trim();
		tempValue.append(value);
	}

	public void endElement(String uri, String localName, String qName) throws SAXException {
		
		if(localName.equalsIgnoreCase(SERVICE_DESCRIPTOR_PROPERTY)) {
			processProperty();
		} else if(localName.equalsIgnoreCase(SERVICE_DESCRIPTOR_API)) {
			
			serviceDescriptor.addApi(api);
			
			api = null;
			isApi = false;
		} else if(localName.equalsIgnoreCase(SERVICE_DESCRIPTOR_API_QUERY_PARAMETER)) {
			
			queryParameter.setValue(tempValue.toString());
			api.addQueryParameter(queryParameter);
		} else if(localName.equalsIgnoreCase(SERVICE_DESCRIPTOR_API_HEADER_PARAMETER)) {
			
			headerParameter.setValue(tempValue.toString());
			api.addHeaderParameter(headerParameter);
		} else if(localName.equalsIgnoreCase(SERVICE_DESCRIPTOR_API_DATA_STREAM)) {
			
			api.setDataStream(tempValue.toString());
		}
	}
	
	private void initializeProperty(Attributes attributes) {
		propertyName = attributes.getValue(SERVICE_DESCRIPTOR_PROPERTY_NAME);
	}
	
	private void processProperty() {
		
		if(isApi) {
			api.addProperty(propertyName, tempValue.toString());
		} else {
			serviceDescriptor.addProperty(propertyName, tempValue.toString());
		}
	}

	public ServiceDescriptor getServiceDescriptor() {
		return this.serviceDescriptor;
	}
	
}