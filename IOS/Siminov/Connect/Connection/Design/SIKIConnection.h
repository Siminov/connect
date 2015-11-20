//
//  SIKIConnection.h
//  Connect
//
//  Created by user on 21/04/15.
//  Copyright (c) 2015 Siminov. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "SIKIConnectionResponse.h"
#import "SIKIConnectionRequest.h"

/**
 * It is a blue print for connection provider classes
 * It exposes API to interact with server
 */
@protocol SIKIConnection <NSObject>

/**
 * It is to handle HTTP GET Method
 * @param connectionRequest Connection Request Instance
 * @return IConnectionResponse instance
 * @throws ConnectionException If any exception occur while making GET request
 */
- (id<SIKIConnectionResponse>)get:(id<SIKIConnectionRequest> const) connectionRequest;


/**
 * It is to handle HTTP HEAD Method
 * @param connectionRequest Connection Request Instance
 * @return IConnectionResponse instance
 * @throws ConnectionException If any exception occur while making HEAD request
 */
- (id<SIKIConnectionResponse>)head:(id<SIKIConnectionRequest> const)connectionRequest;


/**
 * It is to handle HTTP POST Method
 * @param connectionRequest Connection Request Instance
 * @return IConnectionResponse instance
 * @throws ConnectionException If any exception occur while making POST request
 */
- (id<SIKIConnectionResponse>)post:(id<SIKIConnectionRequest> const)connectionRequest;


/**
 * It is to handle HTTP PUT Method
 * @param connectionRequest Connection Request Instance
 * @return IConnectionResponse instance
 * @throws ConnectionException If any exception occur while making PUT request
 */
- (id<SIKIConnectionResponse>)put:(id<SIKIConnectionRequest> const)connectionRequest;


/**
 * It is to handle HTTP DELETE Method
 * @param connectionRequest Connection Request Instance
 * @return IConnectionResponse instance
 * @throws ConnectionException If any exception occur while making DELETE request
 */
- (id<SIKIConnectionResponse>)delete:(id<SIKIConnectionRequest> const)connectionRequest;


/**
 * It is to handle HTTP TRACE Method
 * @param connectionRequest Connection Request Instance
 * @return IConnectionResponse instance
 * @throws ConnectionException If any exception occur while making TRACE request
 */
- (id<SIKIConnectionResponse>)trace:(id<SIKIConnectionRequest> const)connectionRequest;


/**
 * It is to handle HTTP OPTIONS Method
 * @param connectionRequest Connection Request Instance
 * @return IConnectionResponse instance
 * @throws ConnectionException If any exception occur while making OPTIONS request
 */
- (id<SIKIConnectionResponse>)options:(id<SIKIConnectionRequest> const)connectionRequest;


/**
 * It is to handle HTTP CONNECT Method
 * @param connectionRequest Connection Request Instance
 * @return IConnectionResponse instance
 * @throws ConnectionException If any exception occur while making CONNECT request
 */
- (id<SIKIConnectionResponse>)connect:(id<SIKIConnectionRequest> const)connectionRequest;


/**
 * It is to handle HTTP PATCH Method
 * @param connectionRequest Connection Request Instance
 * @return IConnectionResponse instance
 * @throws ConnectionException If any exception occur while making PATCH request
 */
- (id<SIKIConnectionResponse>)patch:(id<SIKIConnectionRequest> const)connectionRequest;

@end
