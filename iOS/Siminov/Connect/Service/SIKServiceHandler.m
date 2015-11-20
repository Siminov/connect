//
//  SIKServiceHandler.m
//  Connect
//
//  Created by user on 21/04/15.
//  Copyright (c) 2015 Siminov. All rights reserved.
//

#import "SIKServiceHandler.h"
#import "SIKAsyncServiceWorker.h"
#import "SIKResourceUtils.h"

@implementation SIKServiceHandler

static SIKServiceHandler *serviceHandler = nil;

- (id)init {
    
    self = [super init];
    if(self) {
        
        syncServiceWorker = [[SIKSyncServiceWorker alloc]init];
        asyncServiceWorker = [[SIKAsyncServiceWorker alloc]init];
        resourceManager = [SIKResourceManager getInstance];
        return self;
    }
    
    return self;
}

+ (SIKServiceHandler *)getInstance {
    
    if(!serviceHandler) {
        serviceHandler = [[super allocWithZone:NULL] init];
    }
    return serviceHandler;
}

/**
 * It handles the service request
 * @param service Service instance
 * @throws ServiceException If any exception occur while handling the service request
 */
- (void)handle:(id<SIKIService>)service {
    
    SIKServiceDescriptor *serviceDescriptor = [service getServiceDescriptor];
    if(serviceDescriptor == nil) {
        serviceDescriptor = [resourceManager requiredServiceDescriptorBasedOnName:[service getService]];
        [service setServiceDescriptor:serviceDescriptor];
    }
    
    
    NSEnumerator *resources = [service getResources];
    NSString *resourceName;
    
    while(resourceName = [resources nextObject]) {
        id resourceValue = [service getResource:resourceName];
        
        if([resourceValue isKindOfClass:[NSString class]]) {
            [serviceDescriptor addProperty:resourceName value:resourceValue];
        }
    }
    
    SIKRequest *request = [serviceDescriptor getRequest:[service getRequest]];
    NSString *mode = [SIKResourceUtils resolve:[request getMode] descriptors:(NSArray *)serviceDescriptor];
    
    if([mode caseInsensitiveCompare:SERVICE_DESCRIPTOR_REQUEST_SYNC_REQUEST_MODE] == NSOrderedSame) {
        
        [SIKResourceUtils resolve:service];
        
        [syncServiceWorker process:service];
    } else if([mode caseInsensitiveCompare:SERVICE_DESCRIPTOR_REQUEST_ASYNC_REQUEST_MODE] == NSOrderedSame) {
        [asyncServiceWorker addRequest:service];
    }
}

@end
