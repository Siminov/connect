///
/// [SIMINOV FRAMEWORK - CONNECT]
/// Copyright [2014-2016] [Siminov Software Solution LLP|support@siminov.com]
///
/// Licensed under the Apache License, Version 2.0 (the "License");
/// you may not use this file except in compliance with the License.
/// You may obtain a copy of the License at
///
///     http://www.apache.org/licenses/LICENSE-2.0
///
/// Unless required by applicable law or agreed to in writing, software
/// distributed under the License is distributed on an "AS IS" BASIS,
/// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
/// See the License for the specific language governing permissions and
/// limitations under the License.



using Siminov.Connect.Exception;
using Siminov.Connect.Model;
using Siminov.Connect.Notification.Design;
using Siminov.Connect.Resource;
using Siminov.Core.Log;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Siminov.Connect.Notification
{
    public class NotificationService
    {
        private static String SENDER_ID = null;
	
		private ResourceManager resourceManager = ResourceManager.GetInstance();
		
		private ApplicationDescriptor applicationDescriptor = null;
        private NotificationDescriptor notificationDescriptor = null;

        //private String SENDER_ID = "";//notificationDescriptor.getProperty(INotification.SENDER_ID);
	
	    public NotificationService() 
            //: base(SENDER_ID)
        {
		    applicationDescriptor = resourceManager.GetApplicationDescriptor();
		    notificationDescriptor = applicationDescriptor.GetNotificationDescriptor();
        }
	
	
	    protected void OnError(/*Context context, */String errorId) 
        {
		    Log.Debug(typeof(NotificationService).Name, "onError", "Error caught, " + errorId);

		    NotificationException notificationException = new NotificationException(typeof(NotificationException).Name, "onError", "Error caught: " + errorId);
		
		    NotificationManager notificationManager = NotificationManager.GetInstance();
		    notificationManager.OnError(notificationException);
	    }

	    protected void OnMessage(/*Context context, Intent intent*/) 
        {
		
		    IMessage message = new Message();
		    message.SetMessage(""/*intent.getExtras().getString(INotification.MESSAGE)*/);
		
		    NotificationManager notificationManager = NotificationManager.GetInstance();
		    notificationManager.OnNotification(message);
	    }

	    protected void OnRegistered(/*Context context, */String registrationId) 
        {
		
		    IRegistration registration = new Registration();
		    registration.SetRegistrationId(registrationId);
		
		    NotificationManager notificationManager = NotificationManager.GetInstance();
		    notificationManager.OnRegistration(registration);
	    }

	    protected void OnUnregistered(/*Context context, */String registrationId) 
        {
		
		    IRegistration registration = new Registration();
		    registration.SetRegistrationId(registrationId);
		
		    NotificationManager notificationManager = NotificationManager.GetInstance();
		    notificationManager.OnUnregistration(registration);
	    }

    }
}
