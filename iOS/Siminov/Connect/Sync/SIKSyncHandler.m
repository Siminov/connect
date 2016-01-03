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
