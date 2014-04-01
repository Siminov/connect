package siminov.connect.design.sync;

import siminov.connect.design.service.IResource;

public interface ISyncRequest extends IResource {

	public String getName();
	
	public void setName(String name);
}
