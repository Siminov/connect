//
//  SIKSyncHandler.m
//  Connect
//
//  Created by user on 21/04/15.
//  Copyright (c) 2015 Siminov. All rights reserved.
//

#import "SIKSyncHandler.h"

#import "SIKISyncRequest.h"

@implementation SIKSyncHandler

static SIKResourceManager *resourceManager;

static SIKSyncWorker *syncWorker;
static NSMutableDictionary *requestTimestamps;

static SIKSyncHandler *synHandler;

-(id)init {
    
    self = [super init];
    
    if (self) {
        resourceManager = [[SIKResourceManager alloc] init];
        
        syncWorker = [[SIKSyncWorker alloc] init];
        requestTimestamps = [[NSMutableDictionary alloc] init];
    }
    
    return self;
}

+ (SIKSyncHandler *)getInstance {
    
    if(!synHandler) {
        synHandler = [[super allocWithZone:NULL] init];
    }
    
    return synHandler;
}



- (void)handle:(id<SIKISyncRequest>)syncRequest {
    
    SIKSyncDescriptor *syncDescriptor = [resourceManager getSyncDescriptor:[syncRequest getName]];
    
    long requestTimestamp = (long) [requestTimestamps objectForKey:syncRequest];
    if(requestTimestamp <= 0) {
        [syncWorker addRequest:syncRequest];
        [requestTimestamps setValue:[NSNumber numberWithLong:(long long)([[NSDate date] timeIntervalSince1970] * 1000.0)] forKey:[syncRequest getName]];
        
        return;
    }
    
    
    long syncInterval = [syncDescriptor getSyncInterval];
    long lastRefreshTimestamp = (long)[requestTimestamps objectForKey:[syncRequest getName]];
    long currentTimestamp = (long long)([[NSDate date] timeIntervalSince1970] * 1000.0);
    
    long timeDifference = lastRefreshTimestamp + syncInterval;
    
    if(timeDifference < currentTimestamp) {
        [syncWorker addRequest:syncRequest];
        [requestTimestamps setValue:[NSNumber numberWithLong:(long long)([[NSDate date] timeIntervalSince1970] * 1000.0)] forKey:[syncRequest getName]];
    }
}


- (void)remove:(id<SIKISyncRequest>)syncRequest {
    
}


- (bool)contain:(id<SIKISyncRequest>) syncRequest {
    return false;
}

@end
