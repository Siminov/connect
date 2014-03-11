/** 
 * [SIMINOV FRAMEWORK]
 * Copyright [2013] [Siminov Software Solution LLP|support@siminov.com]
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
import siminov.connect.model.ApplicationDescriptor;
import siminov.connect.model.AuthenticationDescriptor;
import siminov.connect.model.NotificationDescriptor;
import siminov.connect.model.SyncDescriptor;
import siminov.orm.exception.DeploymentException;
import siminov.orm.log.Log;
import siminov.orm.reader.SiminovSAXDefaultHandler;
import siminov.orm.resource.Resources;
import android.content.Context;



/**
 * Exposes methods to parse Application Descriptor information as per define in ApplicationDescriptor.si.xml file by application.
	<p>
		<pre>
		
Example:
	{@code
	<core>
	
		<property name="name">SIMINOV TEMPLATE</property>	
		<property name="description">Siminov Template Application</property>
		<property name="version">0.9</property>
	
		<property name="load_initially">true</property>
	
		<!-- DATABASE-DESCRIPTORS -->
		<database-descriptors>
			<database-descriptor>DatabaseDescriptor.si.xml</database-descriptor>
		</database-descriptors>
	
		
		<!-- SIMINOV EVENTS -->
		<event-handlers>
		    <event-handler>com.core.template.events.SiminovEventHandler</event-handler>
		    <event-handler>com.core.template.events.DatabaseEventHandler</event-handler>
		</event-handlers>
			
	</core>
	}
	
		</pre>
	</p>
 *
 */
public class ApplicationDescriptorReader extends SiminovSAXDefaultHandler implements siminov.orm.Constants, Constants {

	private ApplicationDescriptor applicationDescriptor = null;
	
	private Resources resources = Resources.getInstance();

	private StringBuilder tempValue = new StringBuilder();
	private String propertyName = null;
	
	private SyncDescriptor syncDescriptor = null;

	private AuthenticationDescriptor authenticationDescriptor = null;

	private NotificationDescriptor notificationDescriptor = null;	
	
	private boolean isSyncDesriptor = false;
	private boolean isAuthenticationDescriptor = false;
	private boolean isNotificationDescriptor = false;	

	public ApplicationDescriptorReader() {
		
		Context context = resources.getApplicationContext();
		if(context == null) {
			Log.loge(getClass().getName(), "Constructor", "Invalid Application Context found.");
			throw new DeploymentException(getClass().getName(), "Constructor", "Invalid Application Context found.");
		}

		/*
		 * Parse ApplicationDescriptor.
		 */
		InputStream applicationDescriptorStream = null;
		
		try {
			applicationDescriptorStream = context.getAssets().open(APPLICATION_DESCRIPTOR_FILE_NAME);
		} catch(IOException ioException) {
			Log.loge(getClass().getName(), "Constructor", "IOException caught while getting input stream of application descriptor, " + ioException.getMessage());
			throw new DeploymentException(getClass().getName(), "Constructor", "IOException caught while getting input stream of application descriptor, " + ioException.getMessage());
		}
		
		try {
			parseMessage(applicationDescriptorStream);
		} catch(Exception exception) {
			Log.loge(getClass().getName(), "Constructor", "Exception caught while parsing APPLICATION-DESCRIPTOR, " + exception.getMessage());
			throw new DeploymentException(getClass().getName(), "Constructor", "Exception caught while parsing APPLICATION-DESCRIPTOR, " + exception.getMessage());
		}
	}
	
	
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
		
		tempValue = new StringBuilder();
		
		if(localName.equalsIgnoreCase(APPLICATION_DESCRIPTOR_SIMINOV)) {
			applicationDescriptor = new ApplicationDescriptor();
		} else if(localName.equalsIgnoreCase(APPLICATION_DESCRIPTOR_PROPERTY)) {
			initializeProperty(attributes);
		} else if(localName.equalsIgnoreCase(CONNECT_DESCRIPTOR_AUTHENTICATION_DESCRIPTOR)) {

			authenticationDescriptor = new AuthenticationDescriptor();
			isAuthenticationDescriptor = true;
		} else if(localName.equalsIgnoreCase(SYNC_DESCRIPTOR)) {

			isSyncDesriptor = true;
			syncDescriptor = new SyncDescriptor();
		} else if(localName.equalsIgnoreCase(NOTIFICATION_DESCRIPTOR)) {

			isNotificationDescriptor = true;
			notificationDescriptor = new NotificationDescriptor();
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
			processProperty();
		} else if(localName.equalsIgnoreCase(APPLICATION_DESCRIPTOR_DATABASE_DESCRIPTOR)) {
			applicationDescriptor.addDatabaseDescriptorPath(tempValue.toString());
		} else if(localName.equalsIgnoreCase(APPLICATION_DESCRIPTOR_EVENT_HANDLER)) {
			
			if(tempValue == null || tempValue.length() <= 0) {
				return;
			}
			
			applicationDescriptor.addEvent(tempValue.toString());
		} else if(localName.equalsIgnoreCase(APPLICATION_DESCRIPTOR_LIBRARY)) {
			
			if(tempValue == null || tempValue.length() <= 0) {
				return;
			}
			
			applicationDescriptor.addLibrary(tempValue.toString());
		} else if(localName.equalsIgnoreCase(CONNECT_DESCRIPTOR_SERVICE_DESCRIPTOR)) {
			applicationDescriptor.addServiceDescriptorPath(tempValue.toString());
		} else if(localName.equalsIgnoreCase(CONNECT_DESCRIPTOR_AUTHENTICATION_DESCRIPTOR)) {
			applicationDescriptor.setAuthenticationDescriptor(authenticationDescriptor);
			isAuthenticationDescriptor = false;
		} else if(localName.equalsIgnoreCase(SYNC_DESCRIPTOR)) {
			applicationDescriptor.addSyncDescriptor(syncDescriptor);
		} else if(localName.equalsIgnoreCase(SYNC_DESCRIPTOR_SERVICE)) {
			syncDescriptor.addService(tempValue.toString());
		} else if(localName.equalsIgnoreCase(NOTIFICATION_DESCRIPTOR)) {
			applicationDescriptor.setNotificationDescriptor(notificationDescriptor);
		}
	}
	
	private void initializeProperty(final Attributes attributes) {
		propertyName = attributes.getValue(APPLICATION_DESCRIPTOR_NAME);
	}

	private void processProperty() {

		if(isNotificationDescriptor) {
			notificationDescriptor.addProperty(propertyName, tempValue.toString());
		} else if(isAuthenticationDescriptor) {
			authenticationDescriptor.addProperty(propertyName, tempValue.toString());
		} else if(isSyncDesriptor) {
			syncDescriptor.addProperty(propertyName, tempValue.toString());
		} else {
			applicationDescriptor.addProperty(propertyName, tempValue.toString());
		}
	}	
	
	/**
	 * Get application descriptor object. 
	 * @return Application Descriptor Object.
	 */
	public ApplicationDescriptor getApplicationDescriptor() {
		return this.applicationDescriptor;
	}
}
