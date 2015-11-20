//
//  SIKHttpConnectionWorker.h
//  Connect
//
//  Created by user on 21/04/15.
//  Copyright (c) 2015 Siminov. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "SIKIConnectionResponse.h"
#import "SIKIConnectionRequest.h"
#import "SIKIConnection.h"

/**
 * It implements IConnection to handle HTTP requests
 */
@interface SIKHttpConnectionWorker : NSObject <SIKIConnection>

@end
