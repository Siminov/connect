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

#import "SIKService.h"

#import "SICLog.h"
#import "SIKServiceException.h"
#import "SIKServiceHandler.h"

@implementation SIKService

- (id)init {
    
    self = [super init];
    
    if(self) {
        
        resources = [[NSMutableDictionary alloc] init];
        return self;
    }
    
    return self;
}

- (long)getRequestId {
    return requestId;
}

- (void)setRequestId:(long)rqst {
    requestId = rqst;
}

- (NSString *)getService {
    return service;
}

- (void)setService:(NSString *)srv {
    service = srv;
}

- (NSString *)getRequest {
    return request;
}

- (void)setRequest:(NSString *)reqst {
    request = reqst;
}

- (NSEnumerator *)getResources {
    return [[resources allKeys] objectEnumerator];
}

- (id)getResource:(NSString *)name {
    return [resources objectForKey:name];
}

- (void)addResource:(NSString *)name value:(id)value {
    [resources setObject:value forKey:name];
}

- (bool)containResource:(NSString *)name {
    return [[resources allKeys] containsObject:name];
}

- (SIKServiceDescriptor *)getServiceDescriptor {
    return serviceDescriptor;
}

- (void)setServiceDescriptor:(SIKServiceDescriptor *)servicedescriptor {
    serviceDescriptor = servicedescriptor;
}

- (void)invoke {
    
    [self onStart];
    
    SIKServiceHandler *serviceHandler = [SIKServiceHandler getInstance];
    @try {
        [serviceHandler handle:self];
    } @catch(SIKServiceException *se) {
        [SICLog error:NSStringFromClass([self class]) methodName:@"invoke" message:[NSString stringWithFormat:@"ServiceException caught while invoking service, %@",[se getMessage]]];
        
        [self onTerminate:se];
    }
}

- (void)terminate {
    
}

- (void)onStart {

}

- (void)onQueue {

}

- (void)onPause {

}

- (void)onResume {

}

- (void)onFinish {

}

- (void)onRequestInvoke:(id<SIKIConnectionRequest>)connectionRequest {

}

- (void)onRequestFinish:(id<SIKIConnectionResponse>)connectionResponse {

}

- (void)onTerminate:(SIKServiceException *)serviceException {

}

@end
