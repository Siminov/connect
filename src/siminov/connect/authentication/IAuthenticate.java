package siminov.connect.authentication;

import org.apache.http.client.methods.HttpRequestBase;

import siminov.orm.exception.SiminovException;

public interface IAuthenticate {

	public void doAuthorization(final Credential credential) throws SiminovException;
	
	public void doSignature(final HttpRequestBase httpRequestBase) throws SiminovException;
}
