package siminov.connect.connection;

import java.util.HashMap;
import java.util.Map;

public class ConnectionRequest {

	private String url;
	
	private Map<String, String> queryParameters = new HashMap<String, String>();
	private Map<String, String> headerParameters = new HashMap<String, String>();
	
	private byte[] dataStream = null;
	
	
	public String getUrl() {
		return this.url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}

	public Map<String, String> getQueryParameters() {
		return this.queryParameters;
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
	
	public Map<String, String> getHeaderParameters() {
		return this.headerParameters;
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
