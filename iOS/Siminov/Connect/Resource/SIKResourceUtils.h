//
//  SIKResourceUtils.h
//  Connect
//
//  Created by user on 21/04/15.
//  Copyright (c) 2015 Siminov. All rights reserved.
//

#import <Foundation/Foundation.h>

#import "SIKIService.h"

/**
 * It is a resource utility class, it exposes APIs to get service dynamic resources
 */
@interface SIKResourceUtils : NSObject

/**
 * It resolve dynamic resources
 * @param resourceValue Value of resource
 * @param descriptors
 * @return
 * @throws ServiceException
 */
+ (NSString *)resolve:(NSString *)resourceValue descriptors:(NSArray * const)descriptors;

/**
 * It resolve service descriptor instance
 * @param service Service instance
 * @throws ServiceException If any exception occur while resolving dynamic resources
 */
+ (void)resolve:(id<SIKIService>)service;

@end
