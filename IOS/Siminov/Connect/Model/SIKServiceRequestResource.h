//
//  SIKServiceRequestResource.h
//  Connect
//
//  Created by user on 21/04/15.
//  Copyright (c) 2015 Siminov. All rights reserved.
//

#import <Foundation/Foundation.h>

#import "SICDatabase.h"

@class SIKServiceRequest;

/**
 * It exposes APIs to Get and Set service request resource information
 * It helps framework to save ASYNC request in database
 */
@interface SIKServiceRequestResource : SICDatabase {
    
    SIKServiceRequest *serviceRequest;
    
    NSString *name;
    NSString *value;

}

/**
 * Get service request
 * @return Service Request
 */
- (SIKServiceRequest *)getServiceRequest;

/**
 * Set service request
 * @param serviceRequest Service Request
 */
- (void)setServiceRequest:(SIKServiceRequest *)serviceRequest;

/**
 * Get resource name
 * @return Resource Name
 */
- (NSString *)getName;

/**
 * Set resource name
 * @param name Resource Name
 */
- (void)setName:(NSString *)name;

/**
 * Get resource value
 * @return Resource Value
 */
- (NSString *)getValue;

/**
 * Set resource value
 * @param value Resource Value
 */
- (void)setValue:(NSString *)value;

@end
