package siminov.connect.events;

import siminov.connect.design.authorization.ICredential;
import siminov.connect.exception.AuthorizationException;

public interface IAuthenticationEvents {

	public void onAuthenticationStart(final ICredential credential) throws AuthorizationException;

	
	public void onAuthenticationFinish(final ICredential credential) throws AuthorizationException;
	
	
	public void onAuthenticationTerminate(final ICredential credential) throws AuthorizationException;
}
