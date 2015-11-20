//
//  SIKIService.h
//  Connect
//
//  Created by user on 21/04/15.
//  Copyright (c) 2015 Siminov. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "SIKServiceDescriptor.h"

#import "SIKIRequest.h"
#import "SIKIServiceEvents.h"
#import "SIKIResource.h"


/**
 * It provides blue print for classes which wants to implement service
 */
@protocol SIKIService <SIKIRequest, SIKIServiceEvents, SIKIResource>

/**
 * Get service request id
 * @return Request id
 */
- (long)getRequestId;


/**
 * Set service request id
 * @param requestId Request Id
 */
- (void)setRequestId:(long)requestId;


/**
 * Get service
 * @return Service
 */
- (NSString *)getService;


/**
 * Add service name
 * @param service Name of service
 */
- (void)setService:(NSString *)service;


/**
 * Get request name
 * @return Name of request
 */
- (NSString *)getRequest;


/**
 * Set request name
 * @param request Name of request
 */
- (void)setRequest:(NSString *)request;


/**
 * Get service descriptor
 * @return Service Descriptor
 */
- (SIKServiceDescriptor *)getServiceDescriptor;


/**
 * Set service descriptor
 * @param serviceDescriptor Service Descriptor
 */
- (void)setServiceDescriptor:(SIKServiceDescriptor *)serviceDescriptor;


/**
 * Invoke the service
 */
- (void)invoke;


/**
 * Terminate the service
 */
- (void)terminate;

@end
