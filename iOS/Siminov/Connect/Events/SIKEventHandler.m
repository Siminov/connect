//
//  SIKEventHandler.m
//  Connect
//
//  Created by user on 21/04/15.
//  Copyright (c) 2015 Siminov. All rights reserved.
//

#import "SIKEventHandler.h"

@implementation SIKEventHandler

static SIKEventHandler *eventHandler = nil;
id<SIKINotificationEvents> notificationEventsHandler;
id<SIKISyncEvents> syncEvents;
static SICResourceManager *coreResourceManager;


+ (void)initialize {
    coreResourceManager = [SICResourceManager getInstance];
    
    NSEnumerator *events = [[coreResourceManager getApplicationDescriptor] getEvents];
    NSString *event = nil;
    
    while (event = [events nextObject]) {
        
        id object = [SICClassUtils createClassInstance:event];
        if([object conformsToProtocol: @protocol(SIKINotificationEvents)]) {
            notificationEventsHandler = (id<SIKINotificationEvents>)object;
        } else if([object conformsToProtocol: @protocol(SIKISyncEvents)]) {
            syncEvents = (id<SIKISyncEvents>)object;
        }
    }
}

+ (SIKEventHandler *)getInstance {
    
    if(!eventHandler) {
        eventHandler = [[super allocWithZone:NULL] init];
       // eventHandler = [[self alloc]init];
    }
    return eventHandler;
}


- (id<SIKINotificationEvents>)getNotificationEventHandler {
    return notificationEventsHandler;
}

- (id<SIKISyncEvents>)getSyncEventHandler {
    return syncEvents;
}

@end
