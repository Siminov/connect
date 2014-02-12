package siminov.connect.authentication.design;

import org.apache.http.client.methods.HttpRequestBase;

import siminov.connect.exception.AuthenticationException;

public interface IAuthenticate {

	public void doAuthorization() throws AuthenticationException;
	
	
	public void doSignature(final HttpRequestBase httpRequestBase) throws AuthenticationException;
}
