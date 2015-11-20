//
//  SIKIConnectionResponse.h
//  Connect
//
//  Created by user on 21/04/15.
//  Copyright (c) 2015 Siminov. All rights reserved.
//

#import <Foundation/Foundation.h>

/**
 * It is a blue print for connection response
 * It exposes API to get connection response information
 */
@protocol SIKIConnectionResponse <NSObject>

/**
 * Get connection status code
 * @return Status Code
 */
- (int)getStatusCode;

/**
 * Set status code
 * @param statusCode Status Code
 */
- (void)setStatusCode:(int)statusCode;

/**
 * Get status message
 * @return Status Message
 */
- (NSString *)getStatusMessage;

/**
 * Set status message
 * @param statusMessage Status Message
 */
- (void)setStatusMessage:(NSString *)statusMessage;

/**
 * Get Response
 * @return Response
 */
- (NSData *)getResponse;

/**
 * Set Response
 * @param response Response
 */
- (void)setResponse:(NSData *)response;

@end
