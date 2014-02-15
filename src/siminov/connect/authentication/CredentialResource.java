package siminov.connect.authentication;


public class CredentialResource {

	private Credential credential = null;
	
	private String name = null;
	private String value = null;
	
	public Credential getCredential() {
		return this.credential;
	}
	
	public void setCredential(Credential credential) {
		this.credential = credential;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getValue() {
		return this.value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
}
