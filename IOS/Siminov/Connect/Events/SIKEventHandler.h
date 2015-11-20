//
//  SIKEventHandler.h
//  Connect
//
//  Created by user on 21/04/15.
//  Copyright (c) 2015 Siminov. All rights reserved.
//

#import <Foundation/Foundation.h>


#import "SIKINotificationEvents.h"
#import "SIKISyncEvents.h"
#import "SICResourceManager.h"


/**
 * It provides the event handler instances.
 */
@interface SIKEventHandler : NSObject

/**
 * It provides EventHandler singleton instance
 * @return EventHandler singleton instance
 */
+ (SIKEventHandler *)getInstance;

/**
 * Get notification event handler
 * @return NotificationEventHandler instance
 */
- (id<SIKINotificationEvents>)getNotificationEventHandler;

/**
 * Get sync event handler instance
 * @return SyncEventHandler instance
 */
- (id<SIKISyncEvents>)getSyncEventHandler;

@end
