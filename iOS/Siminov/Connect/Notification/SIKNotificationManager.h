//
//  SIKNotificationManager.h
//  Connect
//
//  Created by user on 21/04/15.
//  Copyright (c) 2015 Siminov. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "SIKIRegistration.h"
#import "SICResourceManager.h"
#import "SIKResourceManager.h"
#import "SIKIMessage.h"
#import "SIKINotification.h"

@interface SIKNotificationManager : NSObject <SIKINotification>

/**
 * It provides singleton instance of NotificationManager
 * @return NotificationManager singleton instance
 */
+ (SIKNotificationManager *)getInstance;

@end
