package siminov.connect.authentication.design;

import org.apache.http.client.methods.HttpRequestBase;

import siminov.orm.exception.SiminovException;

public interface IAuthenticate {

	public void doInitialization(final IAuthenticateResources authenticateResources);
	
	public void doAuthorization() throws SiminovException;
	
	public void doSignature(final HttpRequestBase httpRequestBase) throws SiminovException;
}
