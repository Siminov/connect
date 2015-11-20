//
//  SIKNotificationManager.m
//  Connect
//
//  Created by user on 21/04/15.
//  Copyright (c) 2015 Siminov. All rights reserved.
//

#import "SIKNotificationManager.h"
#import "SIKRegistration.h"



@implementation SIKNotificationManager

static SIKNotificationManager *notificationManager = nil;
static SICResourceManager *coreResourceManager;
static SIKResourceManager *connectResourceManager;

/**
 * Private NotificationManager Constructor
 */
- (id)init {
    
    self = [super init];
    
    if(self) {
        coreResourceManager = [SICResourceManager getInstance];
        connectResourceManager = [SIKResourceManager getInstance];
        
        return self;
    }
    
    return self;
}

+ (SIKNotificationManager *)getInstance {
    
    if(!notificationManager) {
        notificationManager = [[super allocWithZone:NULL] init];
    }
    return notificationManager;
}

- (void)doRegistration {
    
}

- (void)onRegistration:(id<SIKIRegistration>)registration {
    
    id<SIKINotificationEvents> notificationEventsHandler = [connectResourceManager getNotificationEventHandler];
    if(notificationEventsHandler != nil) {
        [notificationEventsHandler onRegistration:registration];
    }
}

- (void)doUnregistration {
    [self onUnregistration:[[SIKRegistration alloc] init]];
}


- (void)onUnregistration:(id<SIKIRegistration>)registration {
    
    id<SIKINotificationEvents> notificationEventsHandler = [connectResourceManager getNotificationEventHandler];
    if(notificationEventsHandler != nil) {
        [notificationEventsHandler onUnregistration:registration];
    }
}

- (void)onNotification:(id<SIKIMessage>)message {
    
    id<SIKINotificationEvents> notificationEventsHandler = [connectResourceManager getNotificationEventHandler];
    if(notificationEventsHandler != nil) {
        [notificationEventsHandler onNotification:message];
    }
}

- (void)onError:(SIKNotificationException *)notificationException {
    
    id<SIKINotificationEvents> notificationEventsHandler = [connectResourceManager getNotificationEventHandler];
    if(notificationEventsHandler != nil) {
        [notificationEventsHandler onError:notificationException];
    }
}

@end
