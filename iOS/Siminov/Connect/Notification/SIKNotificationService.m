//
//  SIKNotificationService.m
//  Connect
//
//  Created by user on 21/04/15.
//  Copyright (c) 2015 Siminov. All rights reserved.
//

#import "SIKNotificationService.h"
#import "SICLog.h"
#import "SIKNotificationManager.h"
#import "SIKMessage.h"
#import "SIKIRegistration.h"
#import "SIKRegistration.h"
#import "SIKNotificationException.h"

@implementation SIKNotificationService


- (id)init {
    
    self = [super init];
    
    if(self) {
        
        resourceManager = [SIKResourceManager getInstance];
        
        applicationDescriptor = [resourceManager getApplicationDescriptor];
        notificationDescriptor = [applicationDescriptor getNotificationDescriptor];
        
        return self;
    }
    
    return self;
}

- (void)onError:(NSString *)errorId {
    [SICLog debug:NSStringFromClass([self class]) methodName:@"onError" message:@"Error caught"];
    
    SIKNotificationException *notificationException = [[SIKNotificationException alloc] initWithClassName:NSStringFromClass([self class]) methodName:@"onError" message:@"Error caught: "];
    
    SIKNotificationManager *notificationManager = [SIKNotificationManager getInstance];
    [notificationManager onError:notificationException];
}

- (void)onMessage {
    
    id<SIKIMessage> message = [[SIKMessage alloc] init];
    [message setMessage:@""];
    
    SIKNotificationManager *notificationManager = [SIKNotificationManager getInstance];
    [notificationManager onNotification:message];
}

- (void)onRegistered:(NSString *)registrationId {
    
    id<SIKIRegistration> registration = [[SIKRegistration alloc] init];
    [registration setRegistrationId:registrationId];
    
    SIKNotificationManager *notificationManager = [SIKNotificationManager getInstance];
    [notificationManager onRegistration:registration];
}

- (void)onUnregistered:(NSString *)registrationId {
    
    id<SIKIRegistration> registration = [[SIKRegistration alloc] init];
    [registration setRegistrationId:registrationId];
    
    SIKNotificationManager *notificationManager = [SIKNotificationManager getInstance];
    [notificationManager onUnregistration:registration];
}

@end
