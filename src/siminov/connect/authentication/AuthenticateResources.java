package siminov.connect.authentication;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class AuthenticateResources implements IAuthenticateResources {

	private Credential credential = null;
	private Map<String, String> inlineResources = new HashMap<String, String>();
	
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

	public Credential getCredential() {
		return this.credential;
	}

	public void setCredential(Credential credential) {
		this.credential = credential;
	}
}
