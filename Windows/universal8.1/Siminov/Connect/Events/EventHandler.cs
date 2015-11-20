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



using Siminov.Connect.Resource;
using Siminov.Core.Utils;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Siminov.Connect.Events
{


    /// <summary>
    /// It provides the event handler instances.
    /// </summary>
    public class EventHandler
    {
        private static EventHandler eventHandler = null;
	
	    private INotificationEvents notificationEventsHandler = null;
	    private ISyncEvents syncEvents = null;
	
	    private ResourceManager coreResourceManager = ResourceManager.GetInstance();
	

        /// <summary>
        /// Private EventHandler Constructor
        /// </summary>
	    private EventHandler() 
        {
		
		    IEnumerator<String> events = coreResourceManager.GetApplicationDescriptor().GetEvents();
		    while(events.MoveNext()) 
            {
			    String eventName = events.Current;
			
			    Object objectInstance = ClassUtils.CreateClassInstance(eventName);
			    if(objectInstance is INotificationEvents) 
                {
				    notificationEventsHandler = (INotificationEvents) objectInstance;
			    } 
                else if(objectInstance is ISyncEvents) 
                {
                    syncEvents = (ISyncEvents) objectInstance;
			    }
		    }
	    }
	

        /// <summary>
        /// It provides EventHandler singleton instance
        /// </summary>
        /// <returns>EventHandler singleton instance</returns>
	    public static EventHandler GetInstance() 
        {
		    if(eventHandler == null) 
            {
			    eventHandler = new EventHandler();
		    }
		
		    return eventHandler;
	    }


        /// <summary>
        /// Get notification event handler
        /// </summary>
        /// <returns>NotificationEventHandler instance</returns>
	    public INotificationEvents GetNotificationEventHandler() 
        {
		    return this.notificationEventsHandler;
	    }
	

        /// <summary>
        /// Get sync event handler instance
        /// </summary>
        /// <returns>SyncEventHandler instance</returns>
	    public ISyncEvents GetSyncEventHandler() 
        {
		    return this.syncEvents;
	    }

    }
}
