//
//  SIKConnectionStatusCodes.h
//  Connect
//
//  Created by user on 21/04/15.
//  Copyright (c) 2015 Siminov. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "SIKConstants.h"


@interface SIKConnectionStatusCodes : NSObject

/**
 * Get status message based on status code
 * @param statusCode Status Code
 * @return Status Message
 */
- (NSString *)getStatusMessage:(int const)statusCode;
    

@end
