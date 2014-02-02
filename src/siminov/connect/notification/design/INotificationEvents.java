package siminov.connect.notification.design;


public interface INotificationEvents {

	public void onRegistration(IRegistration registration);

	public void onUnregistration(IRegistration registration);

	public void onNotification(IMessage message);

	public void onNotificationRemoved(IMessage message);
}
