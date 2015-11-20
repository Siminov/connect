//
//  SIKSyncWorker.h
//  Connect
//
//  Created by user on 21/04/15.
//  Copyright (c) 2015 Siminov. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "SIKResourceManager.h"
#import "SIKIWorker.h"

/**
 * It provides implementation for Sync IWorker
 * It processes all sync requests
 */
@interface SIKSyncWorker : NSObject <SIKIWorker>

@end
