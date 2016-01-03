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

#import "SIKServiceHandler.h"
#import "SIKAsyncServiceWorker.h"
#import "SIKResourceUtils.h"

@implementation SIKServiceHandler

static SIKServiceHandler *serviceHandler = nil;

- (id)init {
    
    self = [super init];
    if(self) {
        
        syncServiceWorker = [[SIKSyncServiceWorker alloc]init];
        asyncServiceWorker = [[SIKAsyncServiceWorker alloc]init];
        resourceManager = [SIKResourceManager getInstance];
        return self;
    }
    
    return self;
}

+ (SIKServiceHandler *)getInstance {
    
    if(!serviceHandler) {
        serviceHandler = [[super allocWithZone:NULL] init];
    }
    return serviceHandler;
}

/**
 * It handles the service request
 * @param service Service instance
 * @throws ServiceException If any exception occur while handling the service request
 */
- (void)handle:(id<SIKIService>)service {
    
    SIKServiceDescriptor *serviceDescriptor = [service getServiceDescriptor];
    if(serviceDescriptor == nil) {
        serviceDescriptor = [resourceManager requiredServiceDescriptorBasedOnName:[service getService]];
        [service setServiceDescriptor:serviceDescriptor];
    }
    
    
    NSEnumerator *resources = [service getResources];
    NSString *resourceName;
    
    while(resourceName = [resources nextObject]) {
        id resourceValue = [service getResource:resourceName];
        
        if([resourceValue isKindOfClass:[NSString class]]) {
            [serviceDescriptor addProperty:resourceName value:resourceValue];
        }
    }
    
    SIKRequest *request = [serviceDescriptor getRequest:[service getRequest]];
    NSString *mode = [SIKResourceUtils resolve:[request getMode] descriptors:(NSArray *)serviceDescriptor];
    
    if([mode caseInsensitiveCompare:SERVICE_DESCRIPTOR_REQUEST_SYNC_REQUEST_MODE] == NSOrderedSame) {
        
        [SIKResourceUtils resolve:service];
        
        [syncServiceWorker process:service];
    } else if([mode caseInsensitiveCompare:SERVICE_DESCRIPTOR_REQUEST_ASYNC_REQUEST_MODE] == NSOrderedSame) {
        [asyncServiceWorker addRequest:service];
    }
}

@end
