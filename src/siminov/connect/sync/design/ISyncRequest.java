package siminov.connect.sync.design;

import siminov.connect.service.design.IResource;

public interface ISyncRequest extends IResource {

	public String getName();
	
	public void setName(String name);
}
