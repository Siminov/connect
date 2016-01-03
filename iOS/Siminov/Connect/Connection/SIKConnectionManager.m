///
/// [SIMINOV FRAMEWORK - CONNECT]
/// Copyright [2014-2016] [Siminov Software Solution LLP|support@siminov.com]
///
/// Licensed under the Apache License, Version 2.0 (the "License");
/// you may not use this file except in compliance with the License.
/// You may obtain a copy of the License at
///
///     http://www.apache.org/licenses/LICENSE-2.0
///
/// Unless required by applicable law or agreed to in writing, software
/// distributed under the License is distributed on an "AS IS" BASIS,
/// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
/// See the License for the specific language governing permissions and
/// limitations under the License.
///


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
