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


#import "SIKServiceRequest.h"

@implementation SIKServiceRequest


- (id)init {
    
    self = [super init];
    
    if(self) {
        
        requestId = 0;
        
        service = nil;
        request = nil;
        
        instanceOf = nil;
        
        serviceRequestResources = [[NSMutableDictionary alloc] init];
        
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

- (void)setService:(NSString *)srcv {
    service = srcv;
}

- (NSString *)getRequest {
    return request;
}

- (void)setRequest:(NSString *)rqst {
    request = rqst;
}

- (NSString *)getInstanceOf {
    return instanceOf;
}

- (void)setInstanceOf:(NSString *)instance {
    instanceOf = instance;
}

- (NSEnumerator *)getServiceRequestResources {
    return [[serviceRequestResources allValues] objectEnumerator];
}

- (SIKServiceRequestResource *)getServiceRequestResource:(NSString *)name {
    return [serviceRequestResources objectForKey:name];
}

- (void)addServiceRequestResource:(SIKServiceRequestResource *)serviceRequestResource {
    [serviceRequestResources setValue:serviceRequestResource forKey:[serviceRequestResource getName]];
}

- (void)setServiceRequestResources:(NSEnumerator *)resources {
    
    SIKServiceRequestResource *requestResource;
    while(requestResource = [resources nextObject]) {
        [serviceRequestResources setValue:requestResource forKey:[requestResource getName]];
    }
}

- (bool)containServiceRequestResource:(NSString *)name {
    return [[serviceRequestResources allValues ] containsObject:name];
}

@end
