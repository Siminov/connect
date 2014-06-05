package siminov.connect.service.design;

import java.util.Iterator;

import siminov.connect.service.NameValuePair;

public interface IResource {

	public Iterator<NameValuePair> getResources();
	

	public NameValuePair getResource(final String name);

	
	public void addResource(final NameValuePair nameValuePair);
	
	
	public boolean containResource(final NameValuePair nameValuePair);
}
