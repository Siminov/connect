//
//  SIKInitializer.m
//  Connect
//
//  Created by user on 21/04/15.
//  Copyright (c) 2015 Siminov. All rights reserved.
//


#import "SIKInitializer.h"
#import "SICResourceManager.h"
#import "SIKSiminov.h"

@implementation SIKInitializer

static SICResourceManager *resourceManager = nil;

-(id)init {
    
    self = [super init];
    
    if(self) {
        resourceManager = [SICResourceManager getInstance];
        parameters = [[NSMutableArray alloc] init];
        
        return self;
    }
    
    return self;
}

- (void)addParameter:(id)object {
    [parameters addObject: object];
}

- (void)initialize {
    [SIKSiminov start];
}

@end
