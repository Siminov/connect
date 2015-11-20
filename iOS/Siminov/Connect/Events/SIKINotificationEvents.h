//
//  SIKINotificationEvents.h
//  Connect
//
//  Created by user on 21/04/15.
//  Copyright (c) 2015 Siminov. All rights reserved.
//

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
