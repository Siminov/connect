//
//  SIKNotificationDescriptor.m
//  Connect
//
//  Created by user on 21/04/15.
//  Copyright (c) 2015 Siminov. All rights reserved.
//

#import "SIKNotificationDescriptor.h"

@implementation SIKNotificationDescriptor

- (id)init {
    
    self = [super init];
    
    if(self) {
        
        properties = [[NSMutableDictionary alloc] init];
        
        return self;
    }
    
    return self;
}


- (NSEnumerator *)getProperties {
    return [properties keyEnumerator];
}

- (NSString *)getProperty:(NSString *)name {
    return [properties objectForKey:name];
}

- (bool)containProperty:(NSString *)name {
    return [[properties allValues] containsObject:name];
}

- (void)addProperty:(NSString *)name value:(NSString *)value {
    [properties setValue:value forKey:name];
}

- (void)removeProperty:(NSString *)name {
    [properties removeObjectForKey:name];
}

@end
