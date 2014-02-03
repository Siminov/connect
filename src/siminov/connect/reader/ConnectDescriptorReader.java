package siminov.connect.reader;

import java.io.IOException;
import java.io.InputStream;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import siminov.connect.Constants;
import siminov.connect.model.AuthenticationDescriptor;
import siminov.connect.model.ConnectDescriptor;
import siminov.connect.model.NotificationDescriptor;
import siminov.connect.model.RefreshDescriptor;
import siminov.orm.exception.DeploymentException;
import siminov.orm.log.Log;
import siminov.orm.reader.SiminovSAXDefaultHandler;
import siminov.orm.resource.Resources;
import android.content.Context;

public class ConnectDescriptorReader extends SiminovSAXDefaultHandler implements Constants {

	private StringBuilder tempValue = new StringBuilder();
	private Resources resources = Resources.getInstance();
	
	private ConnectDescriptor connectDescriptor = new ConnectDescriptor();
	private RefreshDescriptor refreshDescriptor = null;
	
	private AuthenticationDescriptor authenticationDescriptor = null;

	private NotificationDescriptor notificationDescriptor = null;
	
	private String propertyName = null;
	
	private boolean isRefreshDesriptor = false;
	private boolean isAuthenticationDescriptor = false;
	private boolean isNotificationDescriptor = false;
	
	public ConnectDescriptorReader() {
		parse(CONNECT_DESCRIPTOR_FILE_NAME);
	}

	public ConnectDescriptorReader(String konnectPath) {
		parse(konnectPath);
	}
	
	public ConnectDescriptorReader(String libraryPackageName, String konnectPath) {
		
		Context context = resources.getApplicationContext();
		if(context == null) {
			Log.loge(getClass().getName(), "Constructor", "Invalid context found.");
			throw new DeploymentException(getClass().getName(), "Constructor", "Invalid context found.");
		}

		/*
		 * Parse Adapter.
		 */
		InputStream adapterStream = null;
		
		try {
			adapterStream = getClass().getClassLoader().getResourceAsStream(libraryPackageName.replace(".", "/") + "/" + konnectPath);
		} catch(Exception exception) {
			Log.logd(getClass().getName(), "Constructor", "IOException caught while getting input stream of konnect descriptor, " + exception.getMessage());
			throw new DeploymentException(getClass().getName(), "Constructor", "IOException caught while getting input stream of konnect descriptor, " + exception.getMessage());
		}
		
		try {
			parseMessage(adapterStream);
		} catch(Exception exception) {
			Log.loge(getClass().getName(), "Constructor", "Exception caught while parsing KONNECT-DESCRIPTOR, " + exception.getMessage());
			throw new DeploymentException(getClass().getName(), "Constructor", "Exception caught while parsing KONNECT-DESCRIPTOR, " + exception.getMessage());
		}
	}
	
	private void parse(String fileName) {

		Context context = resources.getApplicationContext();
		if(context == null) {
			Log.loge(getClass().getName(), "Constructor", "Invalid context found.");
			throw new DeploymentException(getClass().getName(), "Constructor", "Invalid context found.");
		}

		/*
		 * Parse HybridDescriptor.
		 */
		InputStream applicationDescriptorStream = null;
		
		try {
			applicationDescriptorStream = context.getAssets().open(fileName);
		} catch(IOException ioException) {
			Log.logd(getClass().getName(), "Constructor", "IOException caught while getting input stream of application descriptor, " + ioException.getMessage());
			
			//Ignore If Hybrid Descriptor Not Defined.
			
			return;
		}
		
		try {
			parseMessage(applicationDescriptorStream);
		} catch(Exception exception) {
			Log.loge(getClass().getName(), "Constructor", "Exception caught while parsing APPLICATION-DESCRIPTOR, " + exception.getMessage());
			throw new DeploymentException(getClass().getName(), "Constructor", "Exception caught while parsing APPLICATION-DESCRIPTOR, " + exception.getMessage());
		}
	}
	
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

		tempValue = new StringBuilder();

		if(localName.equalsIgnoreCase(CONNECT_DESCRIPTOR_PROPERTY)) {
			initializeProperty(attributes);
		} else if(localName.equalsIgnoreCase(CONNECT_DESCRIPTOR_AUTHENTICATION_DESCRIPTOR)) {
			authenticationDescriptor = new AuthenticationDescriptor();
			isAuthenticationDescriptor = true;
		} else if(localName.equalsIgnoreCase(REFRESH_DESCRIPTOR)) {
			
			isRefreshDesriptor = true;
			refreshDescriptor = new RefreshDescriptor();
		} else if(localName.equalsIgnoreCase(NOTIFICATION_DESCRIPTOR)) {
			
			isNotificationDescriptor = true;
			notificationDescriptor = new NotificationDescriptor();
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
		
		if(localName.equalsIgnoreCase(CONNECT_DESCRIPTOR_PROPERTY)) {
			processProperty();
		} else if(localName.equalsIgnoreCase(CONNECT_DESCRIPTOR_SERVICE_DESCRIPTOR)) {
			connectDescriptor.addServiceDescriptorPath(tempValue.toString());
		} else if(localName.equalsIgnoreCase(CONNECT_DESCRIPTOR_AUTHENTICATION_DESCRIPTOR)) {
			connectDescriptor.setAuthenticationDescriptor(authenticationDescriptor);
			isAuthenticationDescriptor = false;
		} else if(localName.equalsIgnoreCase(REFRESH_DESCRIPTOR)) {
			connectDescriptor.addRefreshDescriptor(refreshDescriptor);
		} else if(localName.equalsIgnoreCase(REFRESH_DESCRIPTOR_SERVICE)) {
			refreshDescriptor.addService(tempValue.toString());
		} else if(localName.equalsIgnoreCase(NOTIFICATION_DESCRIPTOR)) {
			connectDescriptor.setNotificationDescriptor(notificationDescriptor);
		}
	}
	
	private void initializeProperty(Attributes attributes) {
		propertyName = attributes.getValue(CONNECT_DESCRIPTOR_PROPERTY_NAME);
	}
	
	private void processProperty() {
		
		if(isNotificationDescriptor) {
			notificationDescriptor.addProperty(propertyName, tempValue.toString());
		} else if(isAuthenticationDescriptor) {
			authenticationDescriptor.addProperty(propertyName, tempValue.toString());
		} else if(isRefreshDesriptor) {
			refreshDescriptor.addProperty(propertyName, tempValue.toString());
		} else {
			connectDescriptor.addProperty(propertyName, tempValue.toString());
		}
	}

	public ConnectDescriptor getConnectDescriptor() {
		return this.connectDescriptor;
	}
}
