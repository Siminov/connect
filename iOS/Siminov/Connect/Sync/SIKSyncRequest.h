//
//  SIKSyncRequest.h
//  Connect
//
//  Created by user on 21/04/15.
//  Copyright (c) 2015 Siminov. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "SIKISyncRequest.h"

@interface SIKSyncRequest : NSObject <SIKISyncRequest> {
    
    long requestId;
    NSString *name;
    NSMutableDictionary *resources;
}

@end
