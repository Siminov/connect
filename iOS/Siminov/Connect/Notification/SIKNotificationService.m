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
