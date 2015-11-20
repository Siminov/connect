//
//  SIKSyncServiceWorker.h
//  Connect
//
//  Created by user on 21/04/15.
//  Copyright (c) 2015 Siminov. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "SIKIService.h"

/**
 * It handles all synchronous service request calls
 */
@interface SIKSyncServiceWorker : NSObject

/**
 * It process the service request
 * @param service Service instance
 */
-(void)process: (id<SIKIService>)service;

@end
