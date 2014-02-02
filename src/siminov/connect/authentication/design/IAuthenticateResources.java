package siminov.connect.authentication.design;

import siminov.connect.authentication.Credential;
import siminov.connect.resource.IInlineResource;


public interface IAuthenticateResources extends IInlineResource {

	public Credential getCredential();
	
	public void setCredential(final Credential credential);
	
}
