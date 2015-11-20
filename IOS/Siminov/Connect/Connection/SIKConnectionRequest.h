//
//  SIKConnectionRequest.h
//  Connect
//
//  Created by user on 21/04/15.
//  Copyright (c) 2015 Siminov. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "SIKIConnectionRequest.h"

/**
 * It is a POJO class, which contains information for the service request
 */
@interface SIKConnectionRequest : NSObject <SIKIConnectionRequest> {
    NSString *url;
    
    NSString *protocol;
    NSString *type;
    
    NSMutableDictionary *queryParameters;
    NSMutableDictionary *headerParameters;
    
    NSData *dataStream;
}

@end
