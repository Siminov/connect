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
