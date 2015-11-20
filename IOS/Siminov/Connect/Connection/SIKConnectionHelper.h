//
//  SIKConnectionHelper.h
//  Connect
//
//  Created by user on 21/04/15.
//  Copyright (c) 2015 Siminov. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "SIKConnectionRequest.h"
#import "SIKIService.h"

/**
 * It provides Utility APIs for the communication
 */
@interface SIKConnectionHelper : NSObject

/**
 * It build connection request instance based on the service descriptor object
 * @param service Instance of IService
 * @return IConnectionRequest instance
 */
+ (id<SIKIConnectionRequest>)prepareConnectionRequest:(id<SIKIService> const)service;

@end
