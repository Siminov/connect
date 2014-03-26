package siminov.connect.design.authorization;

import java.util.Iterator;

public interface ICredentialManager {

	public boolean isAnyActiveCredential();	
	
	public ICredential getActiveCredential();	
	
	public Iterator<ICredential> getCredentials();
}
