package siminov.connect.design.connection;

import java.util.Iterator;
import java.util.Map;

public interface IConnectionRequest {

	public String getUrl();
	
	public void setUrl(String url);

	public String getProtocol();
	
	public void setProtocol(final String protocol);
	
	public String getType();
	
	public void setType(final String type);
	
	public Iterator<String> getQueryParameters();
	
	public String getQueryParameter(String key);
	
	public void setQueryParameters(Map<String, String> queryParameters);
	
	public void addQueryParameter(String key, String value);
	
	public Iterator<String> getHeaderParameters();
	
	public String getHeaderParameter(String key);

	public void setHeaderParameters(Map<String, String> headerParameters);
	
	public void addHeaderParameter(String key, String value);

	public byte[] getDataStream();
	
	public void setDataStream(byte[] dataStream);
}
