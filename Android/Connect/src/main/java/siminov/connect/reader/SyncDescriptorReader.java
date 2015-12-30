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

package siminov.connect.reader;

import java.io.IOException;
import java.io.InputStream;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import siminov.connect.Constants;
import siminov.connect.model.SyncDescriptor;
import siminov.core.exception.DeploymentException;
import siminov.core.log.Log;
import siminov.core.reader.SiminovSAXDefaultHandler;
import siminov.core.resource.ResourceManager;
import android.content.Context;


/**
 * Exposes methods to parse Sync Descriptor information as per define in SyncDescriptor.xml file by application.
	<p>
		<pre>
		
Example:
	{@code
		<sync-descriptor>
		            
				<!-- Mandatory Field -->
			<property name="name">name_of_sync_handler</property>
					
				<!-- Optional Field -->
			<property name="sync_interval">sync_interval_in_millisecond</property>
		     				
				<!-- Optional Field -->
					<!-- Default: SCREEN -->
			<property name="type">INTERVAL|SCREEN|INTERVAL-SCREEN</property>
					
			<!-- Service Descriptors -->
				<!-- Service Descriptor -->
			<service-descriptors>
		     		    
			    <service-descriptor>name_of_service_descriptor.name_of_api</service-descriptor>
		     		    
			</service-descriptors>
		
		</sync-descriptor>

	}
	
		</pre>
	</p>
 *
 */
public class SyncDescriptorReader extends SiminovSAXDefaultHandler implements siminov.core.Constants, Constants {

	private ResourceManager resourceManager = ResourceManager.getInstance();

	private StringBuilder tempValue = new StringBuilder();
	private String propertyName = null;
	
	private SyncDescriptor syncDescriptor = null;

	public SyncDescriptorReader(String syncDescriptorPath) {
		parse(syncDescriptorPath);
	}

	private void parse(String syncDescriptorPath) {
		
		Context context = resourceManager.getApplicationContext();
		if(context == null) {
			Log.error(getClass().getName(), "Constructor", "Invalid context found.");
			throw new DeploymentException(getClass().getName(), "Constructor", "Invalid context found.");
		}

		/*
		 * Parse Sync Descriptor.
		 */
		InputStream applicationDescriptorStream = null;
		
		try {
			applicationDescriptorStream = context.getAssets().open(syncDescriptorPath);
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
		super.startElement(uri, localName, qName, attributes);
		
		tempValue = new StringBuilder();
		
		if(localName.equalsIgnoreCase(SYNC_DESCRIPTOR_PROPERTY)) {
			propertyName = attributes.getValue(APPLICATION_DESCRIPTOR_NAME);
		} else if(localName.equalsIgnoreCase(SYNC_DESCRIPTOR)) {
			syncDescriptor = new SyncDescriptor();
		}
	}
	
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		
		String value = new String(ch,start,length);
		
		if(value == null || value.length() <= 0 || value.equalsIgnoreCase(NEW_LINE)) {
			return;
		}
		
		value = value.trim();
		tempValue.append(value);
	}

	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		
		if(localName.equalsIgnoreCase(APPLICATION_DESCRIPTOR_PROPERTY)) {
			syncDescriptor.addProperty(propertyName, tempValue.toString());
		} else if(localName.equalsIgnoreCase(SYNC_DESCRIPTOR_SERVICE_DESCRIPTOR)) {
			syncDescriptor.addServiceDescriptorName(tempValue.toString());
		}
	}
	

	/**
	 * Get application descriptor object. 
	 * @return Application Descriptor Object.
	 */
	public SyncDescriptor getSyncDescriptor() {
		return this.syncDescriptor;
	}
}
