//
//  SIKService.h
//  Connect
//
//  Created by user on 21/04/15.
//  Copyright (c) 2015 Siminov. All rights reserved.
//

#import <Foundation/Foundation.h>


#import "SIKIService.h"
#import "SIKServiceDescriptor.h"

/**
 * It exposes APIs to Get and Set service information by extending IService
 */
@interface SIKService : NSObject <SIKIService> {
    
    long requestId;
    
    NSString *service;
    NSString *request;
    
    NSMutableDictionary *resources;
    
    SIKServiceDescriptor *serviceDescriptor;
}

@end
