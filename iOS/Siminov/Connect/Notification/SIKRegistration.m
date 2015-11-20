//
//  SIKRegistration.m
//  Connect
//
//  Created by user on 21/04/15.
//  Copyright (c) 2015 Siminov. All rights reserved.
//

#import "SIKRegistration.h"

@implementation SIKRegistration


- (id)init {
    
    self = [super init];
    
    if(self) {
        
        registrationId = nil;
        
        return self;
    }
    
    return self;
}


- (NSString *)getRegistrationId {
    return registrationId;
}

- (void)setRegistrationId:(NSString *)regId {
    self.registrationId = regId;
}


@end
