//
//  SIKConnectionResponse.h
//  Connect
//SIK
//  Created by user on 21/04/15.
//  Copyright (c) 2015 Siminov. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "SIKIConnectionResponse.h"

@interface SIKConnectionResponse : NSObject <SIKIConnectionResponse> {
    int statusCode;
    NSString * statusMessage;
    NSData * response;
}

/**
 * ConnectionResponse Constructor
 * @param statusCode Status Code
 * @param statusMessage Status Message
 */
- (id)initWithStatusCode:(int)code statusMessage:(NSString *)message;

/**
 * ConnectionResponse Constructor
 * @param statusCode Status Code
 * @param response InputStream
 */
- (id)initWithStatusCode:(int)code inputStream:(NSData *)resp;

@end
