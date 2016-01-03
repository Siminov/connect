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


#import "SIKLibraryDescriptor.h"

@implementation SIKLibraryDescriptor

- (id)init {
    
    self = [super init];
    
    if(self) {
        
        serviceDescriptorPaths = [[NSMutableArray alloc] init];
        serviceDescriptorNamesBasedOnPath = [[NSMutableDictionary alloc] init];
        
        syncDescriptorPaths = [[NSMutableArray alloc] init];
        syncDescriptorNamesBasedOnPath = [[NSMutableDictionary alloc] init];
        
        return self;
    }
    
    return self;
}

- (void)addServiceDescriptorPath:(NSString *)serviceDescriptorPath {
    [serviceDescriptorPaths addObject:serviceDescriptorPath];
    [serviceDescriptorNamesBasedOnPath setValue:serviceDescriptorPath forKey:nil];
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


- (void)addSyncDescriptorPath:(NSString *)syncDescriptorPath {
    [syncDescriptorPaths addObject:syncDescriptorPath];
    [syncDescriptorNamesBasedOnPath setValue:syncDescriptorPath forKey:nil];
}

- (void)removeSyncDescriptorPath:(NSString *)syncDescriptorPath {
    [serviceDescriptorPaths removeObject:syncDescriptorPath];
}

- (NSEnumerator *)getSyncDescriptorPaths {
    return [syncDescriptorPaths objectEnumerator];
}

- (bool)containSyncDescriptorPathBasedOnName:(NSString *)syncDescriptorName {
    return [[serviceDescriptorNamesBasedOnPath allValues] containsObject:syncDescriptorName];
}

- (bool)containSyncDescriptorNameBasedOnPath:(NSString *)syncDescriptorPath {
    return [[syncDescriptorNamesBasedOnPath allValues] containsObject:syncDescriptorPath];
}

- (NSString *)getSyncDescriptorPathBasedOnName:(NSString *)syncDescriptorName {
    
    if([self containSyncDescriptorPathBasedOnName:syncDescriptorName]) {
        
        NSEnumerator *syncDescriptorAllPaths = [syncDescriptorNamesBasedOnPath keyEnumerator];
        NSString *syncDescriptorPath;
        while(syncDescriptorPath = [syncDescriptorAllPaths nextObject]) {
            
            NSString *foundSyncDescriptorName = [syncDescriptorNamesBasedOnPath objectForKey:syncDescriptorPath];
            if([foundSyncDescriptorName isEqualToString:syncDescriptorName]) {
                return [syncDescriptorNamesBasedOnPath objectForKey:syncDescriptorPath];
            }
        }
    }
    
    return nil;
}

- (void)addSyncDescriptorNameBasedOnPath:(NSString *)syncDescriptorPath syncDescriptoName:(NSString *)syncDescriptoName {
    [syncDescriptorNamesBasedOnPath setValue:syncDescriptoName forKey:syncDescriptorPath];
}


- (void)removeSyncDescriptorNameBasedOnPath:(NSString *)syncDescriptorPath {
    [syncDescriptorNamesBasedOnPath removeObjectForKey:syncDescriptorPath];
}

@end
