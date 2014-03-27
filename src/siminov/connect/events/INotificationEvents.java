package siminov.connect.events;

import siminov.connect.design.notification.IMessage;
import siminov.connect.design.notification.IRegistration;
import siminov.connect.exception.NotificationException;


public interface INotificationEvents {

	public void onRegistration(IRegistration registration);

	public void onUnregistration(IRegistration registration);

	public void onNotification(IMessage message);
	
	public void onError(NotificationException notificationException);
}
