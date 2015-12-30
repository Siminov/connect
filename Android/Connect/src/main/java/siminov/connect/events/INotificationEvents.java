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


package siminov.connect.events;

import siminov.connect.exception.NotificationException;
import siminov.connect.notification.design.IMessage;
import siminov.connect.notification.design.IRegistration;

/**
 * It is a blue print for class which handles notification events
 */
public interface INotificationEvents {

	/**
	 * This is the first method to be called when application is successfully registered with push notification platform service
	 * @param registration IRegistration instance
	 */
	public void onRegistration(IRegistration registration);

	
	/**
	 * This method is called when application get unregistered on the push notification platform
	 * @param registration IRegistration instance
	 */
	public void onUnregistration(IRegistration registration);

	
	/**
	 * This method is called when application gets any message/notification from server
	 * @param message IMessage instance
	 */
	public void onNotification(IMessage message);
	
	
	/**
	 * This method is called if there is any error in process of registration/notification
	 * @param notificationException If any exception occur during any notification process
	 */
	public void onError(NotificationException notificationException);
}
