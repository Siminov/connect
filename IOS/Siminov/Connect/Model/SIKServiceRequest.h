//
//  SIKServiceRequest.h
//  Connect
//
//  Created by user on 21/04/15.
//  Copyright (c) 2015 Siminov. All rights reserved.
//

#import <Foundation/Foundation.h>

#import "SIKServiceRequestResource.h"

#import "SICDatabase.h"

/**
 * It exposes APIs to Get and Set service request information
 * It helps framework to save ASYNC request in database
 */
@interface SIKServiceRequest : SICDatabase {
    
    long requestId;
    
    NSString *service;
    NSString *request;
    
    NSString *instanceOf;
    
    NSMutableDictionary *serviceRequestResources;
    
}

/**
 * Get request id
 * @return Request Id
 */
- (long)getRequestId;

/**
 * Set request id
 * @param request Request Id
 */
- (void)setRequestId:(long)request;

/**
 * Get service name
 * @return Name of service
 */
- (NSString *)getService;

/**
 * Set service name
 * @param service Name of service
 */
- (void)setService:(NSString *)srcv;

/**
 * Get request name
 * @return Name of request
 */
- (NSString *)getRequest;

/**
 * Set request name
 * @param request Name of request
 */
- (void)setRequest:(NSString *)rqst;

/**
 * Get instance of
 * @return Instance Of
 */
- (NSString *)getInstanceOf;

/**
 * Set instance of
 * @param instanceOf Instance of
 */
- (void)setInstanceOf:(NSString *)instance;

/**
 * Get all resources
 * @return Resources
 */
- (NSEnumerator *)getServiceRequestResources;

/**
 * Get resource based on name
 * @param name Name of resource
 * @return ServiceRequestResource
 */
- (SIKServiceRequestResource *)getServiceRequestResource:(NSString *)name;

/**
 * Add service request resource
 * @param serviceRequestResource Service Request Resource
 */
- (void)addServiceRequestResource:(SIKServiceRequestResource *)serviceRequestResource;

/**
 * Set service request resources
 * @param serviceRequestResources Service Request Resources
 */
- (void)setServiceRequestResources:(NSEnumerator *)serviceRequestResources;

/**
 * Check whether service request resource exists or not
 * @param name Name of resource
 * @return (true/false) TRUE: If resource exists | FALSE: If resource does not exists
 */
- (bool)containServiceRequestResource:(NSString *)nme;

@end
