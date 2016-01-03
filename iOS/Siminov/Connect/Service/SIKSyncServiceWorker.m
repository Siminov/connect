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


#import "SIKSyncServiceWorker.h"
#import "SIKIConnectionResponse.h"
#import "SIKConnectionManager.h"
#import "SICLog.h"
#import "SIKServiceException.h"

@implementation SIKSyncServiceWorker

-(void)process: (id<SIKIService>)service {
    
    id<SIKIConnectionResponse> connectionResponse = nil;
    
    @try {
        connectionResponse = [[SIKConnectionManager getInstance] handle:service];
    }
    @catch (NSException *exception) {
        [SICLog error:NSStringFromClass([self class]) methodName:@"process" message:[NSString stringWithFormat:@"ConnectionException caught while invoking connection, %@",[exception reason]]];
        [service onTerminate:[[SIKServiceException alloc] initWithClassName:NSStringFromClass([self class]) methodName:@"process" message:[NSString stringWithFormat:@"ConnectionException caught while invoking connection, %@",[exception reason]]]];
        return;
    }
    [service onRequestFinish:connectionResponse];
   
}

@end
