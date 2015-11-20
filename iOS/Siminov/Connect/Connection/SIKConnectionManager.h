//
//  SIKConnectionManager.h
//  Connect
//
//  Created by user on 21/04/15.
//  Copyright (c) 2015 Siminov. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "SIKIConnection.h"
#import "SIKIService.h"

/**
 * It exposes API to manager connection
 *
 */
@interface SIKConnectionManager : NSObject {
    id<SIKIConnection> httpConnection;
    id<SIKIConnection> httpsConnection;
}

/**
 * It provides an singleton instance of ConnectionManager class.
 * @return ConnectionManager Instance
 */
+ (SIKConnectionManager *)getInstance;

/**
 * It handles the service request
 * @param service Service instance
 * @return IConnectionResponse instance
 * @throws ConnectionException If any exception occur while executing service request
 */
- (id<SIKIConnectionResponse>)handle:(id<SIKIService> const)service;

@end
