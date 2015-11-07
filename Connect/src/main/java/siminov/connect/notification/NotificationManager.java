/** 
 * [SIMINOV FRAMEWORK]
 * Copyright [2015] [Siminov Software Solution LLP|support@siminov.com]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 **/

package siminov.connect.notification;

import siminov.connect.events.INotificationEvents;
import siminov.connect.exception.NotificationException;
import siminov.connect.model.ApplicationDescriptor;
import siminov.connect.model.NotificationDescriptor;
import siminov.connect.notification.design.IMessage;
import siminov.connect.notification.design.INotification;
import siminov.connect.notification.design.IRegistration;
import siminov.connect.resource.ResourceManager;
import siminov.core.exception.SiminovCriticalException;
import siminov.core.log.Log;
import android.content.Context;

import com.google.android.gcm.GCMRegistrar;

/**
 * It exposes APIs to deal with push notification
 */
public class NotificationManager implements INotification {

	private static NotificationManager notificationManager = null;

	private siminov.core.resource.ResourceManager coreResourceManager = siminov.core.resource.ResourceManager.getInstance();
	private ResourceManager connectResourceManager = ResourceManager.getInstance();
	
	
	/**
	 * Private NotificationManager Constructor
	 */
	private NotificationManager() {

	}
	
	/**
	 * It provides singleton instance of NotificationManager
	 * @return NotificationManager singleton instance
	 */
	public static NotificationManager getInstance() {
		
		if(notificationManager == null) {
			notificationManager = new NotificationManager();
		}
		
		return notificationManager;
	}
	
	public void doRegistration() {

		ApplicationDescriptor applicationDescriptor = connectResourceManager.getApplicationDescriptor();
		NotificationDescriptor notificationDescriptor = applicationDescriptor.getNotificationDescriptor();

		Context applicationContext = coreResourceManager.getApplicationContext();
		
        GCMRegistrar.checkDevice(applicationContext);
        GCMRegistrar.checkManifest(applicationContext);
        
        String registrationId = GCMRegistrar.getRegistrationId(applicationContext);
        if (registrationId == null || registrationId.equals("")) {
        	
        	String senderId = notificationDescriptor.getProperty(SENDER_ID);
        	if(senderId == null || senderId.length() <= 0) {
        		Log.error(NotificationManager.class.getName(), "doRegistration", "INVALID SENDER ID.");
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

		INotificationEvents notificationEventsHandler = connectResourceManager.getNotificationEventHandler();
		if(notificationEventsHandler != null) {
			notificationEventsHandler.onRegistration(registration);
		}
	}

	public void doUnregistration() {
		onUnregistration(new Registration());
	}


	public void onUnregistration(IRegistration registration) {
		
		INotificationEvents notificationEventsHandler = connectResourceManager.getNotificationEventHandler();
		if(notificationEventsHandler != null) {
			notificationEventsHandler.onUnregistration(registration);
		}
	}

	public void onNotification(IMessage message) {
		
		INotificationEvents notificationEventsHandler = connectResourceManager.getNotificationEventHandler();
		if(notificationEventsHandler != null) {
			notificationEventsHandler.onNotification(message);
		}
	}
	
	public void onError(NotificationException notificationException) {
		
		INotificationEvents notificationEventsHandler = connectResourceManager.getNotificationEventHandler();
		if(notificationEventsHandler != null) {
			notificationEventsHandler.onError(notificationException);
		}
	}
}
