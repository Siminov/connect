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

import siminov.orm.resource.ResourceManager;
import siminov.orm.utils.ClassUtils;


public class EventHandler {

	private static EventHandler eventHandler = null;
	
	private INotificationEvents notificationEventsHandler = null;
	private ISyncEvents syncEvents = null;
	
	private ResourceManager ormResourceManager = ResourceManager.getInstance();
	
	private EventHandler() {
		
		Iterator<String> events = ormResourceManager.getApplicationDescriptor().getEvents();
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
	
	public static EventHandler getInstance() {
		if(eventHandler == null) {
			eventHandler = new EventHandler();
		}
		
		return eventHandler;
	}

	public INotificationEvents getNotificationEventHandler() {
		return this.notificationEventsHandler;
	}
	
	public ISyncEvents getSyncEventHandler() {
		return this.syncEvents;
	}
}
