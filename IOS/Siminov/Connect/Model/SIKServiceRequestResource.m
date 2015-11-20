//
//  SIKServiceRequestResource.m
//  Connect
//
//  Created by user on 21/04/15.
//  Copyright (c) 2015 Siminov. All rights reserved.
//

#import "SIKServiceRequestResource.h"

@class SIKServiceRequest;

@implementation SIKServiceRequestResource


- (id)init {
    
    self = [super init];
    
    if(self) {
        
        serviceRequest = nil;
        
        name = nil;
        value = nil;
        
        return self;
    }
    
    return self;
}


- (SIKServiceRequest *)getServiceRequest {
    return serviceRequest;
}


- (void)setServiceRequest:(SIKServiceRequest *)request {
    serviceRequest = request;
}


- (NSString *)getName {
    return name;
}


- (void)setName:(NSString *)nme {
    name = nme;
}


- (NSString *)getValue {
    return value;
}

- (void)setValue:(NSString *)val {
    value = val;
}

@end
