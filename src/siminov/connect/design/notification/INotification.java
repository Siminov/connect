package siminov.connect.design.notification;


public interface INotification {

	public String SENDER_ID = "sender_id";
	public String APPLICATION_ID = "application_id";
	public String REGISTRATION_ID = "registration_id";
	public String MESSAGE = "message";
	
	public void doRegistration();

	public void doUnregistration();
	
	public void onRegistration(IRegistration registration);

	public void onUnregistration(IRegistration registration);
	
	public void onNotification(IMessage message);
}
