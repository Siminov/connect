package siminov.connect.connection;

import java.io.InputStream;

public class ConnectionResponse {

	private int statusCode;
	private String statusMessage;

	private InputStream response;
	
	public ConnectionResponse(int statusCode, String statusMessage) {
		this.statusCode = statusCode;
		this.statusMessage = statusMessage;
	}

	public ConnectionResponse(int statusCode, InputStream response) {
		this.statusCode = statusCode;
		this.response = response;
	}
	
	
	public int getStatusCode() {
		return this.statusCode;
	}
	
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	
	public String getStatusMessage() {
		return this.statusMessage;
	}
	
	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}
	
	public InputStream getResponse() {
		return this.response;
	}
	
	public void setResponse(InputStream response) {
		this.response = response;
	}
}
