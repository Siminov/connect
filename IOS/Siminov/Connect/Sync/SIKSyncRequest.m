//
//  SIKSyncRequest.m
//  Connect
//
//  Created by user on 21/04/15.
//  Copyright (c) 2015 Siminov. All rights reserved.
//

#import "SIKSyncRequest.h"

@implementation SIKSyncRequest

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

- (void)setRequestId:(long)val {
    requestId = val;
}

- (NSString *)getName {
    return name;
}

- (void)setName:(NSString *)nme {
    name = nme;
}

- (NSEnumerator *)getResources {
    return [[resources allKeys] objectEnumerator];
}

- (id)getResource:(NSString *)nme {
    return [resources objectForKey:nme];
}

- (void)addResource:(NSString *)nme value:(id)val {
    [resources setValue:val forKey:nme];
}

- (bool)containResource:(NSString *)nme {
    return [resources objectForKey:nme];
}

@end
