//
//  SIKServiceRequest.m
//  Connect
//
//  Created by user on 21/04/15.
//  Copyright (c) 2015 Siminov. All rights reserved.
//

#import "SIKServiceRequest.h"

@implementation SIKServiceRequest


- (id)init {
    
    self = [super init];
    
    if(self) {
        
        requestId = 0;
        
        service = nil;
        request = nil;
        
        instanceOf = nil;
        
        serviceRequestResources = [[NSMutableDictionary alloc] init];
        
        return self;
    }
    
    return self;
}


- (long)getRequestId {
    return requestId;
}

- (void)setRequestId:(long)rqst {
    requestId = rqst;
}

- (NSString *)getService {
    return service;
}

- (void)setService:(NSString *)srcv {
    service = srcv;
}

- (NSString *)getRequest {
    return request;
}

- (void)setRequest:(NSString *)rqst {
    request = rqst;
}

- (NSString *)getInstanceOf {
    return instanceOf;
}

- (void)setInstanceOf:(NSString *)instance {
    instanceOf = instance;
}

- (NSEnumerator *)getServiceRequestResources {
    return [[serviceRequestResources allValues] objectEnumerator];
}

- (SIKServiceRequestResource *)getServiceRequestResource:(NSString *)name {
    return [serviceRequestResources objectForKey:name];
}

- (void)addServiceRequestResource:(SIKServiceRequestResource *)serviceRequestResource {
    [serviceRequestResources setValue:serviceRequestResource forKey:[serviceRequestResource getName]];
}

- (void)setServiceRequestResources:(NSEnumerator *)resources {
    
    SIKServiceRequestResource *requestResource;
    while(requestResource = [resources nextObject]) {
        [serviceRequestResources setValue:requestResource forKey:[requestResource getName]];
    }
}

- (bool)containServiceRequestResource:(NSString *)name {
    return [[serviceRequestResources allValues ] containsObject:name];
}

@end
