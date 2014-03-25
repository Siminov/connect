package siminov.connect.authorization.design;

import org.apache.http.client.methods.HttpRequestBase;

import siminov.connect.exception.AuthorizationException;

public interface IAuthorization {

	public void doAuthentication() throws AuthorizationException;
	
	
	public void doSignature(final HttpRequestBase httpRequestBase) throws AuthorizationException;
}
