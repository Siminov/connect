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

#import "SIKApplicationDescriptor.h"

@implementation SIKApplicationDescriptor

- (id)init {
    
    self = [super init];
    
    if(self) {

        serviceDescriptorPaths = [[NSMutableArray alloc] init];
        serviceDescriptorNamesBasedOnPath = [[NSMutableDictionary alloc] init];
        
        syncDescriptorPaths = [[NSMutableArray alloc] init];
        
        syncDescriptorsBasedOnName = [[NSMutableDictionary alloc] init];
        syncDescriptorsBasedOnPath = [[NSMutableDictionary alloc] init];
        
        notificationDescriptor = [[SIKNotificationDescriptor alloc] init];
        
        return self;
    }
    
    return self;
}


- (void)addServiceDescriptorPath:(NSString *)serviceDescriptorPath {
    [serviceDescriptorPaths addObject:serviceDescriptorPath];
}

- (void)removeServiceDescriptorPath:(NSString *)serviceDescriptorPath {
    [serviceDescriptorPaths removeObject:serviceDescriptorPath];
}

- (NSEnumerator *)getServiceDescriptorPaths {
    return [serviceDescriptorPaths objectEnumerator];
}

- (bool)containServiceDescriptorPathBasedOnName:(NSString *)serviceDescriptorName {
    return [[serviceDescriptorNamesBasedOnPath allValues] containsObject:serviceDescriptorName];
}

- (bool)containServiceDescriptorNameBasedOnPath:(NSString *)serviceDescriptorPath {
    return [[serviceDescriptorNamesBasedOnPath allValues] containsObject:serviceDescriptorPath];
}

- (NSString *)getServiceDescriptorPathBasedOnName:(NSString *)serviceDescriptorName {
    
    if([self containServiceDescriptorPathBasedOnName:serviceDescriptorName]) {
        
        NSEnumerator *serviceDescriptorAllPaths = [serviceDescriptorNamesBasedOnPath keyEnumerator];
        NSString *serviceDescriptorPath;
        while(serviceDescriptorPath = [serviceDescriptorAllPaths nextObject]) {
            
            NSString *foundServiceDescriptorName = [serviceDescriptorNamesBasedOnPath objectForKey:serviceDescriptorPath];
            if([foundServiceDescriptorName isEqualToString:serviceDescriptorName]) {
                return [serviceDescriptorNamesBasedOnPath objectForKey:serviceDescriptorPath];
            }
        }
    }
    
    return nil;
}

- (void)addServiceDescriptorNameBasedOnPath:(NSString *)serviceDescriptorPath serviceDescriptorName:(NSString *)serviceDescriptoName {
    [serviceDescriptorNamesBasedOnPath setValue:serviceDescriptoName forKey:serviceDescriptorPath];
}

- (void)removeServiceDescriptorNameBasedOnPath:(NSString *)serviceDescriptorPath {
    [serviceDescriptorNamesBasedOnPath removeObjectForKey:serviceDescriptorPath];
}


- (NSEnumerator *)getSyncDescriptorPaths {
    return [syncDescriptorPaths objectEnumerator];
}


- (void)addSyncDescriptorPath:(NSString *)syncDescriptorPath {
    [syncDescriptorPaths addObject:syncDescriptorPath];
}


- (void)removeSyncDescriptorPath:(NSString *)syncDescriptorPath {
    [syncDescriptorPaths removeObject:syncDescriptorPath];
}

- (bool)containSyncDescriptorPath:(NSString *)syncDescriptorPath {
    return [syncDescriptorPaths containsObject:syncDescriptorPath];
}

- (NSEnumerator *)getSyncDescriptors {
    return [[syncDescriptorsBasedOnName allValues] objectEnumerator];
}


- (SIKSyncDescriptor *)getSyncDescriptorBasedOnPath:(NSString *)syncDescriptorPath {
    return [syncDescriptorsBasedOnPath objectForKey:syncDescriptorPath];
}


- (SIKSyncDescriptor *)getSyncDescriptorBasedOnName:(NSString *)syncDescriptorName {
    return [syncDescriptorsBasedOnName objectForKey:syncDescriptorName];
}


- (void)addSyncDescriptor:(NSString *)syncDescriptorPath syncDescriptor:(SIKSyncDescriptor *)syncDescriptor {
    [syncDescriptorsBasedOnPath setValue:syncDescriptor forKey:syncDescriptorPath];
    [syncDescriptorsBasedOnName setValue:syncDescriptor forKey:[syncDescriptor getName]];
}

- (bool)containSyncDescriptor:(NSString *)syncDescriptorName {
    return [[syncDescriptorsBasedOnName allValues] containsObject:syncDescriptorName];
}

- (void)removeSyncDescriptor:(NSString *)syncDescriptorName {
    [syncDescriptorsBasedOnName removeObjectForKey:syncDescriptorName];
}

- (SIKNotificationDescriptor *)getNotificationDescriptor {
    return notificationDescriptor;
}

- (void)setNotificationDescriptor:(SIKNotificationDescriptor *)notificationdescriptor {
    notificationDescriptor = notificationdescriptor;
}

@end
