package siminov.connect.service;

public class NameValuePair {

	private String name = null;
	private Object value = null;
	
	public NameValuePair(String name, Object value) {
		this.name = name;
		this.value = value;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Object getValue() {
		return this.value;
	}
	
	public void setValue(Object value) {
		this.value = value;
	}
}
