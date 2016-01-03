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

#import "SIKResourceManager.h"

#import "SIKServiceDescriptorReader.h"
#import "SIKQuickServiceDescriptorReader.h"
#import "SIKEventHandler.h"


@implementation SIKResourceManager

static SIKResourceManager *resourceManager;
SIKApplicationDescriptor *applicationDescriptor;

+ (void)initialize {
    
}


+ (SIKResourceManager *)getInstance {
    
    if(!resourceManager) {
        resourceManager = [[super allocWithZone:NULL] init];
    }
    
    return resourceManager;
}


- (SIKApplicationDescriptor *)getApplicationDescriptor {
    return applicationDescriptor;
}


- (void)setApplicationDescriptor:(SIKApplicationDescriptor *)appDescriptor {
    applicationDescriptor = appDescriptor;
}


- (SIKServiceDescriptor *)requiredServiceDescriptorBasedOnPath:(NSString *)serviceDescriptorPath {
    
    SIKServiceDescriptorReader *serviceDescriptorReader = [[SIKServiceDescriptorReader alloc] initWithPath:serviceDescriptorPath];
    SIKServiceDescriptor *serviceDescriptor = [serviceDescriptorReader getServiceDescriptor];
    
    [applicationDescriptor addServiceDescriptorNameBasedOnPath:serviceDescriptorPath serviceDescriptorName:[serviceDescriptor getName]];
    
    return serviceDescriptor;
}


- (SIKServiceDescriptor *)requiredServiceDescriptorBasedOnName:(NSString *)serviceDescriptorName {
    
    if(![applicationDescriptor containServiceDescriptorPathBasedOnName:serviceDescriptorName]) {
        
        SIKQuickServiceDescriptorReader *quickServiceDescriptorReader;
        @try {
            quickServiceDescriptorReader = [[SIKQuickServiceDescriptorReader alloc] initWithClassName:serviceDescriptorName];
            [quickServiceDescriptorReader process];
        } @catch(SICSiminovException *siminovException) {
            [SICLog error:NSStringFromClass([self class]) methodName:@"requiredServiceDescriptorBasedOnName" message:@"Siminov Exception caught while getting quick service descriptor based on name."];
            @throw [[SICSiminovCriticalException alloc] initWithClassName:NSStringFromClass([self class]) methodName:@"requiredServiceDescriptorBasedOnName" message:[siminovException getMessage]];
        }
        
        
        if([quickServiceDescriptorReader containServiceDescriptor]) {
            return [quickServiceDescriptorReader getServiceDescriptor];
        }
    }
    
    NSString *serviceDescriptorPath = [applicationDescriptor getServiceDescriptorPathBasedOnName:serviceDescriptorName];
    return [self requiredServiceDescriptorBasedOnPath:serviceDescriptorPath];
}



- (SIKRequest *)requiredRequestBasedOnServiceDescriptorPath:(NSString *)serviceDescriptorPath requestName:(NSString *)requestName {
    
    SIKServiceDescriptor *serviceDescriptor = [self requiredServiceDescriptorBasedOnPath:serviceDescriptorPath];
    return [serviceDescriptor getRequest:requestName];
}


- (SIKRequest *)requireRequestBasedOnServiceDescriptorName:(NSString *)serviceDescriptorName requestName:(NSString *)requestName {
    
    SIKServiceDescriptor *serviceDescriptor = [self requiredServiceDescriptorBasedOnName:serviceDescriptorName];
    return [serviceDescriptor getRequest:requestName];
}


- (id<SIKINotificationEvents>)getNotificationEventHandler {
    return [[SIKEventHandler getInstance] getNotificationEventHandler];
}


- (id<SIKISyncEvents>)getSyncEventHandler {
    return [[SIKEventHandler getInstance] getSyncEventHandler];
}


- (NSEnumerator *)getSyncDescriptors {
    
    SIKApplicationDescriptor *applicationdescriptor = [self getApplicationDescriptor];
    return [applicationdescriptor getSyncDescriptors];
}


- (SIKSyncDescriptor *)getSyncDescriptor:(NSString *)syncDescriptorName {
    
    SIKApplicationDescriptor *applicationdescriptor = [self getApplicationDescriptor];
    return [applicationdescriptor getSyncDescriptorBasedOnName:syncDescriptorName];
}

@end
