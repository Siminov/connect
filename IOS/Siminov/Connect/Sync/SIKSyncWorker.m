//
//  SIKSyncWorker.m
//  Connect
//
//  Created by user on 21/04/15.
//  Copyright (c) 2015 Siminov. All rights reserved.
//

#import "SIKSyncWorker.h"
#import "SIKISyncRequest.h"
#import "SIKISyncEvents.h"
#import "SICClassUtils.h"
#import "SIKIService.h"

static NSMutableArray *syncRequests;
static SIKResourceManager *resourceManager;
static SIKSyncWorker *syncWorker = nil;

@interface SyncWorkerThread : NSThread

@end

@implementation SyncWorkerThread

SyncWorkerThread *syncWorkerThread = nil;

-(void)main {
    
    NSEnumerator *requests = [syncRequests objectEnumerator];
    
    id<SIKISyncRequest> syncRequest;
    while (syncRequest = [requests nextObject]) {
        /*
         * Fire Sync Started Event
         */
        id<SIKISyncEvents> syncEventHandler = [resourceManager getSyncEventHandler];
        if (syncEventHandler != nil) {
            [syncEventHandler onStart:syncRequest];
        }
        
        /*
         * Process Request
         */
        
        SIKSyncDescriptor *refreshDescriptor = [resourceManager getSyncDescriptor:[syncRequest getName]];
        
        NSEnumerator *services = [refreshDescriptor getServiceDescriptorNames];
        NSString *service;
        
        while(service = [services nextObject]) {
            
            NSUInteger lastIndex = 0;
            NSRange range = [service rangeOfString:SYNC_DESCRIPTOR_SERVICE_SEPARATOR];
            if (range.length == 0 && range.location > service.length) {
                lastIndex = 0;
            } else {
                lastIndex = range.location;
            }
            
            NSString *serviceName = [service substringWithRange:NSMakeRange(0, lastIndex)];
            NSString *requestName = [service substringFromIndex:lastIndex+1];
            
            SIKServiceDescriptor *serviceDescriptor = [resourceManager requiredServiceDescriptorBasedOnName:serviceName];
            SIKRequest *api = [serviceDescriptor getRequest:requestName];
            
            NSString *apiHandler = [api getHandler];
            
            id<SIKIService> serviceHandler = [SICClassUtils createClassInstance:apiHandler];
            [serviceHandler setServiceDescriptor:serviceDescriptor];
            
            NSEnumerator *resources = [syncRequest getResources];
            NSString *resourceName;
            
            while(resourceName = [resources nextObject]) {
                
                id resourceValue = [syncRequest getResource:resourceName];
                [serviceHandler addResource:resourceName value:resourceValue];
            }
            
            [serviceHandler invoke];
        }
        
        /*
         * Fire Sync Started Event
         */
        if(syncEventHandler != nil) {
            [syncEventHandler onFinish:syncRequest];
        }
        
        [syncRequests removeObject:syncRequest];
    }
    
    [syncWorkerThread cancel];
    syncWorkerThread = nil;
}

@end

@implementation SIKSyncWorker

-(id) init
{
    self = [super init];
    
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        resourceManager = [SIKResourceManager getInstance];
        syncRequests = [[NSMutableArray alloc]init];
    });
    
    return self;
}

/**
 * It provides singleton instance of SyncWorker
 * @return Singleton instance of SyncWorker
 */
+ (SIKSyncWorker *)getInstance {
    
    if(syncWorker == nil) {
        syncWorker = [[SIKSyncWorker alloc]init];
    }
    
    return syncWorker;
}


- (void)startWorker {
    
    if(syncWorkerThread == nil) {
        syncWorkerThread = [[SyncWorkerThread alloc]init];
    }
    [syncWorkerThread start];
}

- (void)stopWorker {
    
    if(syncWorkerThread == nil) {
        return;
    }
    
    if(![syncWorkerThread isExecuting]) {
        [syncWorkerThread start];
    }
}

- (BOOL)isWorkerRunning {
    
    if(syncWorkerThread == nil) {
        return false;
    }
    
    return [syncWorkerThread isExecuting];
}

- (void)addRequest:(id<SIKIRequest>)request {
    
    id<SIKISyncRequest> syncRequest = (id<SIKISyncRequest>)request;
    if([self containsRequest:syncRequest]) {
        return;
    }
    
    [syncRequests addObject:syncRequest];
    
    /*
     * Fire Sync Queued Event
     */
    id<SIKISyncEvents> syncEventHandler = [resourceManager getSyncEventHandler];
    if(syncEventHandler != nil) {
        [syncEventHandler onQueue:syncRequest];
    }
    
    if(![self isWorkerRunning]) {
        [self startWorker];
    }
}

- (BOOL)containsRequest:(id<SIKIRequest>)refreshRequest {
    return [syncRequests containsObject:refreshRequest];
}

- (void)removeRequest:(id<SIKIRequest>)refreshRequest {
    [syncRequests removeObject:refreshRequest];
}

@end
