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



using Siminov.Connect.Exception;
using Siminov.Connect.Notification.Design;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Siminov.Connect.Events
{


    /// <summary>
    /// It is a blue print for class which handles notification events
    /// </summary>
    public interface INotificationEvents
    {

        /// <summary>
        /// This is the first method to be called when application is successfully registered with push notification platform service
        /// </summary>
        /// <param name="registration">IRegistration instance</param>
        void OnRegistration(IRegistration registration);



        /// <summary>
        /// This method is called when application get unregistered on the push notification platform
        /// </summary>
        /// <param name="registration">IRegistration instance</param>
        void OnUnregistration(IRegistration registration);



        /// <summary>
        /// This method is called when application gets any message/notification from server
        /// </summary>
        /// <param name="message">IMessage instance</param>
        void OnNotification(IMessage message);



        /// <summary>
        /// This method is called if there is any error in process of registration/notification
        /// </summary>
        /// <param name="notificationException">If any exception occur during any notification process</param>
        void OnError(NotificationException notificationException);

    }
}
