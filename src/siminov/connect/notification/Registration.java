package siminov.connect.notification;

import siminov.connect.design.notification.IRegistration;

public class Registration implements IRegistration {

	private String registrationId = null;
	
	public String getRegistrationId() {
		return this.registrationId;
	}

	public void setRegistrationId(final String registrationId) {
		this.registrationId = registrationId;
	}
}
