package siminov.connect.authentication.design;

import siminov.connect.authentication.Credential;
import siminov.orm.exception.SiminovException;

public interface IAuthenticationEvents {

	public void onAuthenticationStart(final Credential credential) throws SiminovException;
	
	public void onAuthenticationFinish(final Credential credential) throws SiminovException;
	
	public void onAuthenticationTerminate(final Credential credential) throws SiminovException;
}
