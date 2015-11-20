//
//  SIKISyncRequest.h
//  Connect
//
//  Created by user on 21/04/15.
//  Copyright (c) 2015 Siminov. All rights reserved.
//

#import <Foundation/Foundation.h>

#import "SIKIRequest.h"
#import "SIKIResource.h"


/**
 * It is a blue print for classes which wants to contain sync request information.
 * It exposes APIs to Get and Set sync request details
 */
@protocol SIKISyncRequest <NSObject, SIKIRequest, SIKIResource>

- (long)getRequestId;

- (void)setRequestId:(long)val;

/**
 * Get sync request name
 * @return Name of sync request
 */
- (NSString *)getName;


/**
 * Set sync request name
 * @param name Name of sync request
 */
- (void)setName:(NSString *)nme;

@end
