package siminov.connect.reader;

import java.io.IOException;
import java.io.InputStream;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import siminov.connect.Constants;
import siminov.connect.model.ConnectDescriptor;
import siminov.orm.exception.DeploymentException;
import siminov.orm.log.Log;
import siminov.orm.reader.SiminovSAXDefaultHandler;
import siminov.orm.resource.Resources;
import android.content.Context;

public class ConnectDescriptorReader extends SiminovSAXDefaultHandler implements Constants {

	private String tempValue = null;
	private Resources resources = Resources.getInstance();
	
	private ConnectDescriptor connectDescriptor = new ConnectDescriptor();
	private String propertyName = null;

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

		tempValue = "";

		if(localName.equalsIgnoreCase(CONNECT_DESCRIPTOR_PROPERTY)) {
			initializeProperty(attributes);
		}
	}
	
	public void characters(char[] ch, int start, int length) throws SAXException {
		tempValue = new String(ch,start,length);
		
		if(tempValue == null || tempValue.length() <= 0) {
			return;
		}
		
		tempValue.trim();
	}

	public void endElement(String uri, String localName, String qName) throws SAXException {
		
		if(localName.equalsIgnoreCase(CONNECT_DESCRIPTOR_PROPERTY)) {
			processProperty();
		} else if(localName.equalsIgnoreCase(CONNECT_DESCRIPTOR_SERVICE_DESCRIPTOR)) {
			connectDescriptor.addServiceDescriptorPath(tempValue);
		}
	}
	
	private void initializeProperty(Attributes attributes) {
		propertyName = attributes.getValue(CONNECT_DESCRIPTOR_PROPERTY_NAME);
	}
	
	private void processProperty() {
		connectDescriptor.addProperty(propertyName, tempValue);
	}

	public ConnectDescriptor getConnectDescriptor() {
		return this.connectDescriptor;
	}
	
}
