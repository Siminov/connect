package siminov.connect.design.connection;

import java.util.Iterator;

import siminov.connect.model.ServiceDescriptor.API.HeaderParameter;
import siminov.connect.model.ServiceDescriptor.API.QueryParameter;

public interface IConnectionRequest {

	public String getUrl();
	
	public void setUrl(String url);

	public String getProtocol();
	
	public void setProtocol(final String protocol);
	
	public String getType();
	
	public void setType(final String type);
	
	public Iterator<String> getQueryParameters();
	
	public QueryParameter getQueryParameter(String key);
	
	public void addQueryParameter(QueryParameter queryParameter);
	
	public Iterator<String> getHeaderParameters();
	
	public HeaderParameter getHeaderParameter(String key);

	public void addHeaderParameter(HeaderParameter headerParameter);

	public byte[] getDataStream();
	
	public void setDataStream(byte[] dataStream);
}
