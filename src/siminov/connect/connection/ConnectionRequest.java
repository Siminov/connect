package siminov.connect.connection;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import siminov.connect.design.connection.IConnectionRequest;

public class ConnectionRequest implements IConnectionRequest {

	private String url;

	private String protocol;
	private String type;
	
	private Map<String, String> queryParameters = new HashMap<String, String>();
	private Map<String, String> headerParameters = new HashMap<String, String>();
	
	private byte[] dataStream = null;
	
	
	public String getUrl() {
		return this.url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}

	public String getProtocol() {
		return this.protocol;
	}
	
	public void setProtocol(final String protocol) {
		this.protocol = protocol;
	}
	
	public String getType() {
		return this.type;
	}
	
	public void setType(final String type) {
		this.type = type;
	}
	
	public Iterator<String> getQueryParameters() {
		return this.queryParameters.keySet().iterator();
	}
	
	public String getQueryParameter(String key) {
		return this.queryParameters.get(key);
	}
	
	public void setQueryParameters(Map<String, String> queryParameters) {
		this.queryParameters = queryParameters;
	}
	
	public void addQueryParameter(String key, String value) {
		this.queryParameters.put(key, value);
	}
	
	public Iterator<String> getHeaderParameters() {
		return this.headerParameters.keySet().iterator();
	}
	
	public String getHeaderParameter(String key) {
		return this.headerParameters.get(key);
	}

	public void setHeaderParameters(Map<String, String> headerParameters) {
		this.headerParameters = headerParameters;
	}
	
	public void addHeaderParameter(String key, String value) {
		this.headerParameters.put(key, value);
	}

	public byte[] getDataStream() {
		return this.dataStream;
	}
	
	public void setDataStream(byte[] dataStream) {
		this.dataStream = dataStream;
	}
}
