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



using Siminov.Connect.Events;
using Siminov.Connect.Exception;
using Siminov.Connect.Model;
using Siminov.Connect.Notification.Design;
using Siminov.Connect.Resource;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Siminov.Connect.Notification
{


    /// <summary>
    /// It exposes APIs to deal with push notification
    /// </summary>
    public class NotificationManager : INotification
    {
        private static NotificationManager notificationManager = null;

	    private Core.Resource.ResourceManager coreResourceManager = Core.Resource.ResourceManager.GetInstance();
	    private ResourceManager connectResourceManager = ResourceManager.GetInstance();
	
	

        /// <summary>
        /// Private NotificationManager Constructor
        /// </summary>
	    private NotificationManager() 
        {

	    }
	

        /// <summary>
        /// It provides singleton instance of NotificationManager
        /// </summary>
        /// <returns>NotificationManager singleton instance</returns>
	    public static NotificationManager GetInstance() 
        {
		
		    if(notificationManager == null) 
            {
			    notificationManager = new NotificationManager();
		    }
		
		    return notificationManager;
	    }
	
	    public void DoRegistration() 
        {

		    /*ApplicationDescriptor applicationDescriptor = connectResourceManager.GetApplicationDescriptor();
		    NotificationDescriptor notificationDescriptor = applicationDescriptor.GetNotificationDescriptor();

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
            }*/
	    }

	    public void OnRegistration(IRegistration registration) 
        {

		    INotificationEvents notificationEventsHandler = connectResourceManager.GetNotificationEventHandler();
		    if(notificationEventsHandler != null) 
            {
			    notificationEventsHandler.OnRegistration(registration);
		    }
	    }

	    public void DoUnregistration() 
        {
		    OnUnregistration(new Registration());
	    }


	    public void OnUnregistration(IRegistration registration) 
        {
		
		    INotificationEvents notificationEventsHandler = connectResourceManager.GetNotificationEventHandler();
		    if(notificationEventsHandler != null) 
            {
			    notificationEventsHandler.OnUnregistration(registration);
		    }
	    }

	    public void OnNotification(IMessage message) 
        {
		
		    INotificationEvents notificationEventsHandler = connectResourceManager.GetNotificationEventHandler();
		    if(notificationEventsHandler != null) 
            {
			    notificationEventsHandler.OnNotification(message);
		    }
	    }
	
	    public void OnError(NotificationException notificationException) 
        {
		
		    INotificationEvents notificationEventsHandler = connectResourceManager.GetNotificationEventHandler();
		    if(notificationEventsHandler != null) 
            {
			    notificationEventsHandler.OnError(notificationException);
		    }
	    }
    }
}
