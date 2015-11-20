//
//  SIKINotification.h
//  Connect
//
//  Created by user on 21/04/15.
//  Copyright (c) 2015 Siminov. All rights reserved.
//

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
