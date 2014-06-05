package siminov.connect.connection.design;

import java.io.InputStream;

public interface IConnectionResponse {

	public int getStatusCode();
	
	public void setStatusCode(int statusCode);
	
	public String getStatusMessage();
	
	public void setStatusMessage(String statusMessage);
	
	public InputStream getResponse();
	
	public void setResponse(InputStream response);
}
