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
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Siminov.Connect.Notification.Design
{


    /// <summary>
    /// It is a blue print for classes which handle the push notification 
    /// It exposes APIs to handle push notification such as do registration, do unregistraton
    /// </summary>
    public interface INotification
    {

        /// <summary>
        /// Do registration
        /// This is used when application wants to register for push notification platform service
        /// </summary>
        void DoRegistration();



        /// <summary>
        /// Do unregistration
        /// This is used when application wants to unregister for push notification platform service
        /// </summary>
        void DoUnregistration();


        /// <summary>
        /// This is called when application is successfully registred for push notification service
        /// </summary>
        /// <param name="registration">Registration instance</param>
        void OnRegistration(IRegistration registration);


        /// <summary>
        /// This is called when application is successfully unregistred for push notification service
        /// </summary>
        /// <param name="registration">Registration</param>
        void OnUnregistration(IRegistration registration);


        /// <summary>
        /// This is called when any notification is recevied from push notification service
        /// </summary>
        /// <param name="message">Message</param>
        void OnNotification(IMessage message);



        /// <summary>
        /// This is called when there is any exception while handing push notification
        /// </summary>
        /// <param name="notificationException">NotificationException instance</param>
        void OnError(NotificationException notificationException);

    }
}
