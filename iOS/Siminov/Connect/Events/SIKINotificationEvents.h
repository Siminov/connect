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
#import "SIKIMessage.h"

@class SIKNotificationException;

/**
 * It is a blue print for class which handles notification events
 */
@protocol SIKINotificationEvents <NSObject>

/**
 * This is the first method to be called when application is successfully registered with push notification platform service
 * @param registration IRegistration instance
 */
- (void)onRegistration:(id<SIKIRegistration>) registration;


/**
 * This method is called when application get unregistered on the push notification platform
 * @param registration IRegistration instance
 */
- (void)onUnregistration:(id<SIKIRegistration>) registration;


/**
 * This method is called when application gets any message/notification from server
 * @param message IMessage instance
 */
- (void)onNotification:(id<SIKIMessage>) message;


/**
 * This method is called if there is any error in process of registration/notification
 * @param notificationException If any exception occur during any notification process
 */
- (void)onError:(SIKNotificationException *)notificationException;

@end
