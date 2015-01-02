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
import java.util.Iterator;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import siminov.connect.Constants;
import siminov.connect.model.ApplicationDescriptor;
import siminov.connect.model.ServiceDescriptor;
import siminov.connect.resource.ResourceManager;
import siminov.core.exception.PrematureEndOfParseException;
import siminov.core.exception.SiminovException;
import siminov.core.log.Log;
import siminov.core.reader.SiminovSAXDefaultHandler;
import android.content.Context;

/**
 * Exposes methods to quickly parse service descriptor defined by application.
 */
public class QuickServiceDescriptorReader extends SiminovSAXDefaultHandler implements Constants {

	private StringBuilder tempValue = new StringBuilder();
	private String finalServiceDescriptorName = null;
	
	private Context context = null;
	
	private ServiceDescriptor serviceDescriptor = null;
	
	private boolean doesMatch = false;
	private boolean isNameProperty = false;
	
	private siminov.core.resource.ResourceManager coreResourceManager = siminov.core.resource.ResourceManager.getInstance();
	private ResourceManager connectResourceManager = ResourceManager.getInstance();
	
	public QuickServiceDescriptorReader(final String findServiceDescriptorName) throws SiminovException {
		
		if(findServiceDescriptorName == null || findServiceDescriptorName.length() <= 0) {
			Log.error(getClass().getName(), "Constructor", "Invalid Service Descriptor Name Which Needs To Be Searched.");
			throw new SiminovException(getClass().getName(), "Constructor", "Invalid Service Descriptor Name Which Needs To Be Searched.");
		}
		
		this.finalServiceDescriptorName = findServiceDescriptorName;
	}
	
	public void process() throws SiminovException {
		context = coreResourceManager.getApplicationContext();
		if(context == null) {
			Log.error(getClass().getName(), "process", "Invalid Application Context found.");
			throw new SiminovException(getClass().getName(), "process", "Invalid Application Context found.");
		}


		
		ApplicationDescriptor applicationDescriptor = connectResourceManager.getApplicationDescriptor();
		Iterator<String> serviceDescriptorPaths = applicationDescriptor.getServiceDescriptorPaths();
		
		while(serviceDescriptorPaths.hasNext()) {
			String serviceDescriptorPath = serviceDescriptorPaths.next();
			
			InputStream databaseMappingDescriptorStream = null;
			try {
				databaseMappingDescriptorStream = context.getAssets().open(serviceDescriptorPath);
			} catch(IOException ioException) {
				Log.error(getClass().getName(), "process", "IOException caught while getting input stream of Service Descriptor: " + serviceDescriptorPath + ", " + ioException.getMessage());
				throw new SiminovException(getClass().getName(), "process", "IOException caught while getting input stream of Service Descriptor: " + serviceDescriptorPath + ", " + ioException.getMessage());
			}
			
			try {
				parseMessage(databaseMappingDescriptorStream);
			} catch(Exception exception) {
				Log.error(getClass().getName(), "process", "Exception caught while parsing Service Descriptor: " + serviceDescriptorPath + ", " + exception.getMessage());
				throw new SiminovException(getClass().getName(), "process", "Exception caught while parsing Service Descriptor: " + serviceDescriptorPath + ", " + exception.getMessage());
			}
			
			if(doesMatch) {

				ServiceDescriptorReader serviceDescriptor = new ServiceDescriptorReader(serviceDescriptorPath);
				this.serviceDescriptor = serviceDescriptor.getServiceDescriptor();
				
				return;
			}
		}
	}
	
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		
		tempValue = new StringBuilder();

		if(localName.equalsIgnoreCase(Constants.SERVICE_DESCRIPTOR_PROPERTY)) {
			String propertyName = attributes.getValue(SERVICE_DESCRIPTOR_PROPERTY_NAME);

			if(propertyName.equalsIgnoreCase(SERVICE_DESCRIPTOR_NAME)) {
				isNameProperty = true;
			}
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
		
		if(localName.equalsIgnoreCase(Constants.SERVICE_DESCRIPTOR_PROPERTY)) {
			
			if(isNameProperty) {
				if(tempValue.toString().equalsIgnoreCase(finalServiceDescriptorName)) {
					doesMatch = true;
				}
				
				throw new PrematureEndOfParseException(getClass().getName(), "endElement", "Service Descriptor Name: " + tempValue);
			}
		}
	}


	/**
	 * Get service descriptor
	 * @return Service Descriptor
	 */
	public ServiceDescriptor getServiceDescriptor() {
		return this.serviceDescriptor;
	}
	
	/**
	 * Set service descriptor
	 * @return Service Descriptor
	 */
	public boolean containServiceDescriptor() {
		return this.doesMatch;
	}
}
