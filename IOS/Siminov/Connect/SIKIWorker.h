//
//  SIKIWorker.h
//  Connect
//
//  Created by user on 21/04/15.
//  Copyright (c) 2015 Siminov. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "SIKIRequest.h"

/**
 * It is a blue print for classes which want to perform background processes
 * It exposes APIs to start, stop worker
 */
@protocol SIKIWorker <NSObject>

/**
 * Start the worker to handle the requests
 */
- (void)startWorker;

/**
 * Stop the worker
 */
- (void)stopWorker;

/**
 * Check whether worker is running or not
 * @return (true/false) TRUE: If worker is running | FALSE: If worker is not running
 */
- (BOOL)isWorkerRunning;

/**
 * Add request
 * @param request IRequest
 */
- (void)addRequest:(id<SIKIRequest>)request;

/**
 * Remove request
 * @param request IRequest
 */
- (void)removeRequest:(id<SIKIRequest>)request;

/**
 * Check whether it contains request or not
 * @param request (true/false) TRUE: If it contains the request | FALSE: If it does not contains the request
 */
- (BOOL)containsRequest:(id<SIKIRequest>)request;


@end
