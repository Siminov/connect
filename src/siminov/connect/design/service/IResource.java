package siminov.connect.design.service;

import java.util.Iterator;

public interface IResource {

	public Iterator<String> getResources();
	

	public Object getResource(final String name);

	
	public void addResource(final String name, final Object value);
	
	
	public boolean containResource(final String name);
}
