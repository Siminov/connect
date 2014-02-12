package siminov.connect.authentication.design;

import siminov.connect.authentication.Credential;
import siminov.connect.exception.AuthenticationException;

public interface IAuthenticationEvents {

	public void onAuthenticationStart(final Credential credential) throws AuthenticationException;
	
	public void onAuthenticationFinish(final Credential credential) throws AuthenticationException;
	
	public void onAuthenticationTerminate(final Credential credential) throws AuthenticationException;
}
