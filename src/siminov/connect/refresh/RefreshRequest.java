package siminov.connect.refresh;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import siminov.connect.service.design.IInlineResource;

public class RefreshRequest implements IInlineResource {

	private String name;

	private Map<String, String> inlineResources = new HashMap<String, String>();
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Iterator<String> getInlineResources() {
		return this.inlineResources.keySet().iterator();
	}

	public String getInlineResource(String name) {
		return this.inlineResources.get(name);
	}

	public void addInlineResource(String name, String value) {
		this.inlineResources.put(name, value);
	}

	public boolean containInlineResource(String name) {
		return this.inlineResources.containsKey(name);
	}
}
