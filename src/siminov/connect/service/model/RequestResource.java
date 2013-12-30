package siminov.connect.service.model;

public class RequestResource {

	private Request serviceRequest = null;
	
	private String name = null;
	private String value = null;
	
	public Request getServiceRequest() {
		return this.serviceRequest;
	}
	
	public void setServiceRequest(Request serviceRequest) {
		this.serviceRequest = serviceRequest;
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
