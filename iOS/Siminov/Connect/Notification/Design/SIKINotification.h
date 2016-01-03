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
///



#import <Foundation/Foundation.h>
#import "SIKIRegistration.h"

/**
 * It is a blue print for classes which handle the push notification
 * It exposes APIs to handle push notification such as do registration, do unregistraton
 */
@protocol SIKINotification <NSObject>

///**
// * Sender Id
// */
//- (NSString *) SENDER_ID = @"sender_id";
//
///**
// * Application Id
// */
//- (NSString *) APPLICATION_ID = @"application_id";
//
///**
// * Registration Id
// */
//- (NSString *) REGISTRATION_ID = @"registration_id";
//
///**
// * Message
// */
//- (NSString *) MESSAGE = @"message";

/**
 * Do registration
 * <p>
 * 	This is used when application wants to register for push notification platform service
 */
- (void)doRegistration;


/**
 * Do unregistration
 * <p>
 * 	This is used when application wants to unregister for push notification platform service
 */
- (void)doUnregistration;

/**
 * This is called when application is successfully registred for push notification service
 * @param registration Registration instance
 */
- (void)onRegistration:(id<SIKIRegistration>)registration;

/**
 * This is called when application is successfully unregistred for push notification service
 * @param registration
 */
- (void)onUnregistration:(id<SIKIRegistration>)registration;

/**
 * This is called when any notification is recevied from push notification service
 * @param message Message
 */
- (void)onNotification:(id<SIKIMessage>)message;


/**
 * This is called when there is any exception while handing push notification
 * @param notificationException NotificationException instance
 */
- (void)onError:(SIKNotificationException *)notificationException;

@end
