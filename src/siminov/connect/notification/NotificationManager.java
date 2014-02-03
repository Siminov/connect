package siminov.connect.notification;

import siminov.connect.model.ConnectDescriptor;
import siminov.connect.model.NotificationDescriptor;
import siminov.connect.notification.design.IMessage;
import siminov.connect.notification.design.INotification;
import siminov.connect.notification.design.INotificationEvents;
import siminov.connect.notification.design.IRegistration;
import siminov.connect.resource.Resources;
import siminov.orm.exception.SiminovCriticalException;
import siminov.orm.log.Log;
import android.content.Context;

import com.google.android.gcm.GCMRegistrar;

public class NotificationManager implements INotification {

	private static NotificationManager notificationManager = null;

	private siminov.orm.resource.Resources ormResources = siminov.orm.resource.Resources.getInstance();
	private Resources connectResources = Resources.getInstance();
	
	
	private NotificationManager() {

	}
	
	public static NotificationManager getInstance() {
		
		if(notificationManager == null) {
			notificationManager = new NotificationManager();
		}
		
		return notificationManager;
	}
	
	public void doRegistration() {

		ConnectDescriptor connectDescriptor = connectResources.getConnectDescriptor();
		NotificationDescriptor notificationDescriptor = connectDescriptor.getNotificationDescriptor();

		Context applicationContext = ormResources.getApplicationContext();
		
        GCMRegistrar.checkDevice(applicationContext);
        GCMRegistrar.checkManifest(applicationContext);
        
        String registrationId = GCMRegistrar.getRegistrationId(applicationContext);
        if (registrationId == null || registrationId.equals("")) {
        	
        	String senderId = notificationDescriptor.getProperty(SENDER_ID);
        	if(senderId == null || senderId.length() <= 0) {
        		Log.loge(NotificationManager.class.getName(), "doRegistration", "INVALID SENDER ID.");
        		throw new SiminovCriticalException(NotificationManager.class.getName(), "doRegistration", "INVALID SENDER ID.");
        	}
        	
        	
            GCMRegistrar.register(applicationContext, senderId);
        } else {

        	IRegistration registration = new Registration();
        	registration.setRegistrationId(GCMRegistrar.getRegistrationId(applicationContext));
        	
        	onRegistration(registration);
        }
	}

	public void onRegistration(IRegistration registration) {

		Context applicationContext = ormResources.getApplicationContext();
		
    	if(!GCMRegistrar.isRegisteredOnServer(applicationContext)) {
    		
			/*
			 * Invoke registration event handler if registered
			 */
			
			INotificationEvents notificationEventsHandler = connectResources.getNotificationEventHandler();
			if(notificationEventsHandler != null) {
				notificationEventsHandler.onRegistration(registration);
			}
    	}
	}

	public void doUnregisteration() {
		
		Context applicationContext = ormResources.getApplicationContext();

		if(!GCMRegistrar.isRegisteredOnServer(applicationContext)) {
			return;
        }
		
		onUnregistration(new Registration());
	}


	public void onUnregistration(IRegistration registration) {
		
		Context applicationContext = ormResources.getApplicationContext();
		
    	if(GCMRegistrar.isRegisteredOnServer(applicationContext)) {
    		
			/*
			 * Invoke registration event handler if registered
			 */
			
			INotificationEvents notificationEventsHandler = connectResources.getNotificationEventHandler();
			if(notificationEventsHandler != null) {
				notificationEventsHandler.onUnregistration(registration);
			}
    	}
	}

	public void onNotification(IMessage message) {
		
		Context applicationContext = ormResources.getApplicationContext();
		
    	if(!GCMRegistrar.isRegisteredOnServer(applicationContext)) {
    		
			/*
			 * Invoke notification event handler if registered
			 */
			
			INotificationEvents notificationEventsHandler = connectResources.getNotificationEventHandler();
			if(notificationEventsHandler != null) {
				notificationEventsHandler.onNotification(message);
			}
    	}
	}

	public void onNotificationRemoved(IMessage message) {
		
		/*
		 * Invoke notification event handler if registered
		 */
		
		INotificationEvents notificationEventsHandler = connectResources.getNotificationEventHandler();
		if(notificationEventsHandler != null) {
			notificationEventsHandler.onNotification(message);
		}
	}
}
