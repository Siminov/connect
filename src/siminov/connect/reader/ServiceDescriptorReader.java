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
import siminov.orm.resource.Resources;
import android.content.Context;

public class ServiceDescriptorReader extends SiminovSAXDefaultHandler implements Constants {

	private String tempValue = null;
	private Resources resources = Resources.getInstance();
	
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
			adapterStream = getClass().getClassLoader().getResourceAsStream(libraryPackageName.replace(".", "/") + "/" + serviceDescriptorPath);
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

		tempValue = "";

		if(localName.equalsIgnoreCase(SERVICE_DESCRIPTOR_PROPERTY)) {
			initializeProperty(attributes);
		} else if(localName.equalsIgnoreCase(SERVICE_DESCRIPTOR_API)) {
			
			api = new ServiceDescriptor.API();
			isApi = true;
		} else if(localName.equalsIgnoreCase(SERVICE_DESCRIPTOR_API_QUERY_PARAMETER)) {
		
			queryParameter = new QueryParameter();
			queryParameter.setName(attributes.getValue(SERVICE_DESCRIPTOR_API_QUERY_PARAMETER_NAME_ATTRIBUTE));
			queryParameter.setValue(tempValue);
			
			api.addQueryParameter(queryParameter);
		} else if(localName.equalsIgnoreCase(SERVICE_DESCRIPTOR_API_HEADER_PARAMETER)) {
			
			headerParameter = new HeaderParameter();
			headerParameter.setName(attributes.getValue(SERVICE_DESCRIPTOR_API_HEADER_PARAMETER_NAME_ATTRIBUTE));
			headerParameter.setValue(tempValue);
			
			api.addHeaderParameter(headerParameter);
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
		
		if(localName.equalsIgnoreCase(SERVICE_DESCRIPTOR_PROPERTY)) {
			processProperty();
		} else if(localName.equalsIgnoreCase(SERVICE_DESCRIPTOR_API)) {
			
			serviceDescriptor.addApi(api);
			
			api = null;
			isApi = false;
		} else if(localName.equalsIgnoreCase(SERVICE_DESCRIPTOR_API_QUERY_PARAMETER)) {
			
			queryParameter.setValue(tempValue);
			api.addQueryParameter(queryParameter);
		} else if(localName.equalsIgnoreCase(SERVICE_DESCRIPTOR_API_HEADER_PARAMETER)) {
			
			headerParameter.setValue(tempValue);
			api.addHeaderParameter(headerParameter);
		} else if(localName.equalsIgnoreCase(SERVICE_DESCRIPTOR_API_DATA_STREAM)) {
			
			api.setDataStream(tempValue);
		}
	}
	
	private void initializeProperty(Attributes attributes) {
		propertyName = attributes.getValue(CONNECT_DESCRIPTOR_PROPERTY_NAME);
	}
	
	private void processProperty() {
		
		if(isApi) {
			api.addProperty(propertyName, tempValue);
		} else {
			serviceDescriptor.addProperty(propertyName, tempValue);
		}
	}

	public ServiceDescriptor getServiceDescriptor() {
		return this.serviceDescriptor;
	}
	
}