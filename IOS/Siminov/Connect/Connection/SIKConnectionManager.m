//
//  SIKConnectionManager.m
//  Connect
//
//  Created by user on 21/04/15.
//  Copyright (c) 2015 Siminov. All rights reserved.
//

#import "SIKConnectionManager.h"
#import "SIKHttpConnectionWorker.h"
#import "SIKHttpsConnectionWorker.h"
#import "SIKConnectionHelper.h"
#import "SIKConstants.h"

static SIKConnectionManager *connectionManager = nil;

@implementation SIKConnectionManager

/**
 * ConnectionManager Construction
 */
- (id)init {
    
    self = [super init];
    if(self) {
        
        httpConnection = [[SIKHttpConnectionWorker alloc] init];
        httpsConnection = [[SIKHttpsConnectionWorker alloc] init];
        
        return self;
    }
    
    return self;
}

+ (SIKConnectionManager *)getInstance {
    
    if(!connectionManager) {
        connectionManager = [[super allocWithZone:NULL] init];
    }
    return connectionManager;
}

- (id<SIKIConnectionResponse>)handle:(id<SIKIService> const)service {
    
    id<SIKIConnectionRequest> connectionRequest = [SIKConnectionHelper prepareConnectionRequest:service];
    
    /*
     * Service Event onServiceApiInvoke
     */
    
    //[service onRequestInvoke:connectionRequest];
    
    id<SIKIConnection> connection = nil;
    if([[connectionRequest getProtocol] caseInsensitiveCompare:SERVICE_DESCRIPTOR_HTTP_PROTOCOL] == NSOrderedSame) {
        connection = httpConnection;
    } else if([[connectionRequest getProtocol] caseInsensitiveCompare:SERVICE_DESCRIPTOR_HTTPS_PROTOCOL] == NSOrderedSame) {
        connection = httpsConnection;
    }
    
    id<SIKIConnectionResponse> connectionResponse = nil;
    if([[connectionRequest getType] caseInsensitiveCompare:SERVICE_DESCRIPTOR_REQUEST_GET_TYPE] == NSOrderedSame) {
        connectionResponse = [connection get:connectionRequest];
    } else if([[connectionRequest getType] caseInsensitiveCompare:SERVICE_DESCRIPTOR_REQUEST_HEAD_TYPE] == NSOrderedSame) {
        connectionResponse = [connection head:connectionRequest];
    } else if([[connectionRequest getType] caseInsensitiveCompare:SERVICE_DESCRIPTOR_REQUEST_POST_TYPE] == NSOrderedSame) {
        connectionResponse = [connection post:connectionRequest];
    } else if([[connectionRequest getType] caseInsensitiveCompare:SERVICE_DESCRIPTOR_REQUEST_PUT_TYPE] == NSOrderedSame) {
        connectionResponse = [connection put:connectionRequest];
    } else if([[connectionRequest getType] caseInsensitiveCompare:SERVICE_DESCRIPTOR_REQUEST_DELETE_TYPE] == NSOrderedSame) {
        connectionResponse = [connection delete:connectionRequest];
    } else if([[connectionRequest getType] caseInsensitiveCompare:SERVICE_DESCRIPTOR_REQUEST_TRACE_TYPE] == NSOrderedSame) {
        connectionResponse = [connection trace:connectionRequest];
    } else if([[connectionRequest getType] caseInsensitiveCompare:SERVICE_DESCRIPTOR_REQUEST_OPTIONS_TYPE] == NSOrderedSame) {
        connectionResponse = [connection options:connectionRequest];
    } else if([[connectionRequest getType] caseInsensitiveCompare:SERVICE_DESCRIPTOR_REQUEST_CONNECT_TYPE] == NSOrderedSame) {
        connectionResponse = [connection connect:connectionRequest];
    } else if([[connectionRequest getType] caseInsensitiveCompare:SERVICE_DESCRIPTOR_REQUEST_PATCH_TYPE] == NSOrderedSame) {
        connectionResponse = [connection patch:connectionRequest];
    }
    
    return connectionResponse;
}

@end
