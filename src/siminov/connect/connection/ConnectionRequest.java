package siminov.connect.connection;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import siminov.connect.design.connection.IConnectionRequest;
import siminov.connect.model.ServiceDescriptor.API.HeaderParameter;
import siminov.connect.model.ServiceDescriptor.API.QueryParameter;

public class ConnectionRequest implements IConnectionRequest {

	private String url;

	private String protocol;
	private String type;
	
	private Map<String, QueryParameter> queryParameters = new HashMap<String, QueryParameter>();
	private Map<String, HeaderParameter> headerParameters = new HashMap<String, HeaderParameter>();
	
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
	
	public QueryParameter getQueryParameter(String key) {
		return this.queryParameters.get(key);
	}
	
	public void addQueryParameter(QueryParameter queryParameter) {
		this.queryParameters.put(queryParameter.getName(), queryParameter);
	}
	
	public Iterator<String> getHeaderParameters() {
		return this.headerParameters.keySet().iterator();
	}
	
	public HeaderParameter getHeaderParameter(String key) {
		return this.headerParameters.get(key);
	}

	public void addHeaderParameter(HeaderParameter headerParameter) {
		this.headerParameters.put(headerParameter.getName(), headerParameter);
	}

	public byte[] getDataStream() {
		return this.dataStream;
	}
	
	public void setDataStream(byte[] dataStream) {
		this.dataStream = dataStream;
	}
}
