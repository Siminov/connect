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

import siminov.connect.exception.NotificationException;
import siminov.connect.model.ApplicationDescriptor;
import siminov.connect.model.NotificationDescriptor;
import siminov.connect.notification.design.IMessage;
import siminov.connect.notification.design.INotification;
import siminov.connect.notification.design.IRegistration;
import siminov.connect.resource.ResourceManager;
import siminov.orm.log.Log;
import android.content.Context;
import android.content.Intent;

import com.google.android.gcm.GCMBaseIntentService;

/**
 * It extends GRCBaseIntentService to handle push notification events
 */
public class NotificationService extends GCMBaseIntentService {

	private static String SENDER_ID = null;
	
	static {
		
		ResourceManager resourceManager = ResourceManager.getInstance();
		
		ApplicationDescriptor applicationDescriptor = resourceManager.getApplicationDescriptor();
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
