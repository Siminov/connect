//
//  SIKSyncDescriptor.m
//  Connect
//
//  Created by user on 21/04/15.
//  Copyright (c) 2015 Siminov. All rights reserved.
//

#import "SIKSyncDescriptor.h"

#import "SIKConstants.h"

@implementation SIKSyncDescriptor

- (id)init {
    
    self = [super init];
    
    if(self) {
        
        properties = [[NSMutableDictionary alloc] init];
        
        serviceDescriptorNames = [[NSMutableArray alloc] init];
        
        return self;
    }
    
    return self;
}


- (NSString *)getName {
    return [properties objectForKey:SYNC_DESCRIPTOR_NAME];
}

- (void)setName:(NSString *)name {
    [properties setValue:name forKey:SYNC_DESCRIPTOR_NAME];
}

- (long)getSyncInterval {
    
    NSString *syncInterval = [properties objectForKey:SYNC_DESCRIPTOR_REFRESH_INTERVAL];
    if(syncInterval == nil || [syncInterval length] <= 0) {
        return 0;
    }
    
    return [syncInterval longLongValue];
}

- (void)setSyncInterval:(long)syncInterval {
    [properties setValue:[[NSNumber numberWithLong:syncInterval] stringValue] forKey:SYNC_DESCRIPTOR_REFRESH_INTERVAL];
}

- (NSEnumerator *)getProperties {
    return [[properties allKeys] objectEnumerator];
}

- (NSString *)getProperty:(NSString *)name {
    return [properties objectForKey:name];
}

- (bool)containProperty:(NSString *)name {
    return [[properties allKeys] containsObject:name];
}

- (void)addProperty:(NSString *)name value:(NSString *)value {
    [properties setValue:value forKey:name];
}

- (void)removeProperty:(NSString *)name {
    [properties removeObjectForKey:name];
}

- (NSEnumerator *)getServiceDescriptorNames {
    return [serviceDescriptorNames objectEnumerator];
}

- (void)addServiceDescriptorName:(NSString *)serviceDescriptorName {
    [serviceDescriptorNames addObject:serviceDescriptorName];
}

- (void)removeServiceDescriptorName:(NSString *)serviceDescriptorName {
    [serviceDescriptorNames removeObject:serviceDescriptorName];
}

@end
