package siminov.connect.model;

public class ServiceResource {

	private Service service = null;
	
	private String name = null;
	private String value = null;
	
	public Service getService() {
		return this.service;
	}
	
	public void setService(Service service) {
		this.service = service;
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
