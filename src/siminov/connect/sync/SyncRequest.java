package siminov.connect.sync;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import siminov.connect.service.NameValuePair;
import siminov.connect.sync.design.ISyncRequest;

public class SyncRequest implements ISyncRequest {

	private String name;
	
	private Map<String, NameValuePair> resources = new HashMap<String, NameValuePair>();
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Iterator<NameValuePair> getResources() {
		return this.resources.values().iterator();
	}

	public Object getResource(String name) {
		return this.resources.get(name).getValue();
	}

	public void addResource(final NameValuePair nameValuePair) {
		this.resources.put(nameValuePair.getName(), nameValuePair);
	}

	public boolean containResource(final NameValuePair nameValuePair) {
		return this.resources.containsKey(nameValuePair.getName());
	}
}
