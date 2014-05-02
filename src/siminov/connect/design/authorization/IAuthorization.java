package siminov.connect.design.authorization;

import org.apache.http.client.methods.HttpRequestBase;

import siminov.connect.exception.AuthorizationException;

public interface IAuthorization {

	public void doAuthentication(final ICredential credential) throws AuthorizationException;
	
	
	public void doSignature(final HttpRequestBase httpRequestBase) throws AuthorizationException;
}
