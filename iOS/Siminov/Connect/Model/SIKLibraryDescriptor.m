//
//  SIKLibraryDescriptor.m
//  Connect
//
//  Created by user on 21/04/15.
//  Copyright (c) 2015 Siminov. All rights reserved.
//

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
