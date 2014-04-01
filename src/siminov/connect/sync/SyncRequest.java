package siminov.connect.sync;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import siminov.connect.design.sync.ISyncRequest;

public class SyncRequest implements ISyncRequest {

	private String name;
	
	private Map<String, Object> resources = new HashMap<String, Object>();
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Iterator<String> getResources() {
		return this.resources.keySet().iterator();
	}

	public Object getResource(String name) {
		return this.resources.get(name);
	}

	public void addResource(String name, Object value) {
		this.resources.put(name, value);
	}

	public boolean containResource(String name) {
		return this.resources.containsKey(name);
	}
}
