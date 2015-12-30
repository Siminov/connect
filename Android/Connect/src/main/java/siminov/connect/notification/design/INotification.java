/**
 * [SIMINOV FRAMEWORK - CONNECT]
 * Copyright [2014-2016] [Siminov Software Solution LLP|support@siminov.com]
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

package siminov.connect.notification.design;

import siminov.connect.exception.NotificationException;

/**
 * It is a blue print for classes which handle the push notification 
 * It exposes APIs to handle push notification such as do registration, do unregistraton
 */
public interface INotification {

	/**
	 * Sender Id 
	 */
	public String SENDER_ID = "sender_id";
	
	/**
	 * Application Id
	 */
	public String APPLICATION_ID = "application_id";
	
	/**
	 * Registration Id
	 */
	public String REGISTRATION_ID = "registration_id";
	
	/**
	 * Message
	 */
	public String MESSAGE = "message";
	
	/**
	 * Do registration
	 * <p>
	 * 	This is used when application wants to register for push notification platform service
	 */
	public void doRegistration();

	
	/**
	 * Do unregistration
	 * <p>
	 * 	This is used when application wants to unregister for push notification platform service
	 */
	public void doUnregistration();
	
	/**
	 * This is called when application is successfully registred for push notification service
	 * @param registration Registration instance
	 */
	public void onRegistration(IRegistration registration);

	/**
	 * This is called when application is successfully unregistred for push notification service
	 * @param registration
	 */
	public void onUnregistration(IRegistration registration);
	
	/**
	 * This is called when any notification is recevied from push notification service
	 * @param message Message
	 */
	public void onNotification(IMessage message);
	
	
	/**
	 * This is called when there is any exception while handing push notification
	 * @param notificationException NotificationException instance
	 */
	public void onError(NotificationException notificationException);
}
