package siminov.connect.notification;

import siminov.connect.design.notification.IMessage;
import siminov.connect.design.notification.INotification;
import siminov.connect.design.notification.IRegistration;
import siminov.connect.exception.NotificationException;
import siminov.connect.model.ApplicationDescriptor;
import siminov.connect.model.NotificationDescriptor;
import siminov.connect.resource.Resources;
import siminov.orm.log.Log;
import android.content.Context;
import android.content.Intent;

import com.google.android.gcm.GCMBaseIntentService;

public class NotificationService extends GCMBaseIntentService {

	private static String SENDER_ID = null;
	
	static {
		
		Resources resources = Resources.getInstance();
		
		ApplicationDescriptor applicationDescriptor = resources.getApplicationDescriptor();
		NotificationDescriptor notificationDescriptor = applicationDescriptor.getNotificationDescriptor();
		
		SENDER_ID = notificationDescriptor.getProperty(INotification.SENDER_ID);
	}
	
	
	public NotificationService() {
		super(SENDER_ID);
	}
	
	
	protected void onError(Context context, String errorId) {
		Log.debug(NotificationService.class.getName(), "onError", "Error caught, " + errorId);

		NotificationException notificationException = new NotificationException(NotificationException.class.getName(), "onError", "Error caught: " + errorId);
		
		NotificationManager notificationManager = NotificationManager.getInstance();
		notificationManager.onError(notificationException);
	}

	protected void onMessage(Context context, Intent intent) {
		
		IMessage message = new Message();
		message.setMessage(intent.getExtras().getString(INotification.MESSAGE));
		
		NotificationManager notificationManager = NotificationManager.getInstance();
		notificationManager.onNotification(message);
	}

	protected void onRegistered(Context context, String registrationId) {
		
		IRegistration registration = new Registration();
		registration.setRegistrationId(registrationId);
		
		NotificationManager notificationManager = NotificationManager.getInstance();
		notificationManager.onRegistration(registration);
	}

	protected void onUnregistered(Context context, String registrationId) {
		
		IRegistration registration = new Registration();
		registration.setRegistrationId(registrationId);
		
		NotificationManager notificationManager = NotificationManager.getInstance();
		notificationManager.onUnregistration(registration);
	}
}
