package siminov.connect.authentication;

import siminov.orm.resource.IInlineResource;

public interface IAuthenticateResources extends IInlineResource {

	public Credential getCredential();
	
	public void setCredential(final Credential credential);
	
}
