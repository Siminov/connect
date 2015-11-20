//
//  SIKSyncHandler.h
//  Connect
//
//  Created by user on 21/04/15.
//  Copyright (c) 2015 Siminov. All rights reserved.
//

#import <Foundation/Foundation.h>

#import "SIKISyncRequest.h"
#import "SIKResourceManager.h"
#import "SIKSyncWorker.h"
#import "SIKISyncRequest.h"


@interface SIKSyncHandler : NSObject {
    
}

/**
 * It provides singleton instance of SyncHandler
 * @return Singleton instance of SyncHandler
 */
+ (SIKSyncHandler *)getInstance;


/**
 * Handles sync request
 * @param syncRequest Sync Request
 */
- (void)handle:(id<SIKISyncRequest>)syncRequest;


/**
 * Removes sync request
 * @param syncRequest Sync Request
 */
- (void)remove:(id<SIKISyncRequest>)syncRequest;

/**
 * Check whether it contains sync request or not
 * @param syncRequest Sync Request
 * @return (true/false) TRUE: If it contains sync request | FALSE: If it does not contains request
 */
- (bool)contain:(id<SIKISyncRequest>)syncRequest;

@end
