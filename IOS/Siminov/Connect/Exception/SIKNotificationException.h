//
//  SIKNotificationException.h
//  Connect
//
//  Created by user on 21/04/15.
//  Copyright (c) 2015 Siminov. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "SICSiminovException.h"


@interface SIKNotificationException : SICSiminovException

/**
 * NotificationException Constructor
 * @param className Name of class
 * @param methodName Name of method
 * @param message Exception message
 */
- (id)initWithClassName:(NSString * const)classname methodName:(NSString * const)methodname message:(NSString * const)exceptionmessage;


@end
