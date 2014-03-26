package siminov.connect.design.sync;

import siminov.connect.design.service.IInlineResource;

public interface ISyncRequest extends IInlineResource {

	public String getName();
	
	public void setName(String name);
}
