package siminov.connect.model;

import siminov.orm.database.Database;

public class ServiceRequestResource extends Database {

	private ServiceRequest serviceRequest = null;
	
	private String name = null;
	private String value = null;
	
	public ServiceRequest getServiceRequest() {
		return this.serviceRequest;
	}
	
	public void setServiceRequest(ServiceRequest serviceRequest) {
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
