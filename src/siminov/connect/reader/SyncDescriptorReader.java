package siminov.connect.reader;

import java.io.IOException;
import java.io.InputStream;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import siminov.connect.Constants;
import siminov.connect.model.SyncDescriptor;
import siminov.orm.exception.DeploymentException;
import siminov.orm.log.Log;
import siminov.orm.reader.SiminovSAXDefaultHandler;
import siminov.orm.resource.Resources;
import android.content.Context;

public class SyncDescriptorReader extends SiminovSAXDefaultHandler implements siminov.orm.Constants, Constants {

	private Resources resources = Resources.getInstance();

	private StringBuilder tempValue = new StringBuilder();
	private String propertyName = null;
	
	private SyncDescriptor syncDescriptor = null;

	public SyncDescriptorReader(String syncDescriptorPath) {
		parse(syncDescriptorPath);
	}

	private void parse(String syncDescriptorPath) {
		
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
			applicationDescriptorStream = context.getAssets().open(syncDescriptorPath);
		} catch(IOException ioException) {
			Log.logd(getClass().getName(), "Constructor", "IOException caught while getting input stream of service descriptor, " + ioException.getMessage());
			throw new DeploymentException(getClass().getName(), "Constructor", "IOException caught while getting input stream of service descriptor, " + ioException.getMessage());
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
			syncDescriptor.addService(tempValue.toString());
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
