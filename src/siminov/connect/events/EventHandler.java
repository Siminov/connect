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


package siminov.connect.events;

import java.util.Iterator;

import siminov.core.resource.ResourceManager;
import siminov.core.utils.ClassUtils;


/**
 * It provides the event handler instances.
 */
public class EventHandler {

	private static EventHandler eventHandler = null;
	
	private INotificationEvents notificationEventsHandler = null;
	private ISyncEvents syncEvents = null;
	
	private ResourceManager coreResourceManager = ResourceManager.getInstance();
	
	/**
	 * Private EventHandler Constructor
	 */
	private EventHandler() {
		
		Iterator<String> events = coreResourceManager.getApplicationDescriptor().getEvents();
		while(events.hasNext()) {
			String event = events.next();
			
			Object object = ClassUtils.createClassInstance(event);
			if(object instanceof INotificationEvents) {
				notificationEventsHandler = (INotificationEvents) object;
			} else if(object instanceof ISyncEvents) {
				syncEvents = (ISyncEvents) object;
			}
		}
	}
	
	/**
	 * It provides EventHandler singleton instance
	 * @return EventHandler singleton instance
	 */
	public static EventHandler getInstance() {
		if(eventHandler == null) {
			eventHandler = new EventHandler();
		}
		
		return eventHandler;
	}

	/**
	 * Get notification event handler
	 * @return NotificationEventHandler instance
	 */
	public INotificationEvents getNotificationEventHandler() {
		return this.notificationEventsHandler;
	}
	
	/**
	 * Get sync event handler instance
	 * @return SyncEventHandler instance
	 */
	public ISyncEvents getSyncEventHandler() {
		return this.syncEvents;
	}
}
