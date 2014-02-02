package siminov.connect.notification.design;


public interface INotification {

	public String SENDER_ID = "sender_id";
	public String APPLICATION_ID = "application_id";
	public String REGISTRATION_ID = "registration_id";
	public String MESSAGE = "message";
	
	public void doRegistration();

	public void doUnregisteration();
	
	public void onRegistration(IRegistration registration);

	public void onUnregistration(IRegistration registration);
	
	public void onNotification(IMessage message);
	
	public void onNotificationRemoved(IMessage message);
}
