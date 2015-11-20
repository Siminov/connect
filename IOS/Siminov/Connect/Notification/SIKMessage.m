//
//  SIKMessage.m
//  Connect
//
//  Created by user on 21/04/15.
//  Copyright (c) 2015 Siminov. All rights reserved.
//

#import "SIKMessage.h"

@implementation SIKMessage


- (id)init {
    
    self = [super init];
    
    if(self) {
        
        message = nil;
        
        return self;
    }
    
    return self;
}


- (NSString *)getMessage {
    return message;
}

- (void)setMessage:(NSString *)msg {
    message = msg;
}

@end
