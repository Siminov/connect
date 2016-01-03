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
