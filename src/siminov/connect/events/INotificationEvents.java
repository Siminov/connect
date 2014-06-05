package siminov.connect.events;

import siminov.connect.exception.NotificationException;
import siminov.connect.notification.design.IMessage;
import siminov.connect.notification.design.IRegistration;


public interface INotificationEvents {

	public void onRegistration(IRegistration registration);

	public void onUnregistration(IRegistration registration);

	public void onNotification(IMessage message);
	
	public void onError(NotificationException notificationException);
}
