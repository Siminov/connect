package siminov.connect.notification;

import siminov.connect.design.notification.IMessage;

public class Message implements IMessage {

	private String message = null;
	
	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
