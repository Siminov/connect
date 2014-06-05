package siminov.connect.authorization.design;

import java.util.Iterator;

public interface ICredentialManager {

	public boolean isAnyActiveCredential();	
	

	public ICredential getActiveCredential();	

	
	public void setActiveCredential(final ICredential credential);
	
	
	public Iterator<ICredential> getCredentials();
}
