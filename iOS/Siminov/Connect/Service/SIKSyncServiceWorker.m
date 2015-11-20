//
//  SIKSyncServiceWorker.m
//  Connect
//
//  Created by user on 21/04/15.
//  Copyright (c) 2015 Siminov. All rights reserved.
//

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
