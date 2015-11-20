//
//  SIKISyncEvents.h
//  Connect
//
//  Created by user on 21/04/15.
//  Copyright (c) 2015 Siminov. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "SIKISyncRequest.h"

/**
 * It is a blue print for class which handles sync events
 */
@protocol SIKISyncEvents <NSObject>

/**
 * This method is called then a Sync is started.
 * <p> In this you can initialize resources related to Sync.
 * <p> Once OnStart has finished, Connect will call OnQueue.
 * @param syncRequest ISyncRequest instance
 */
- (void)onStart:(id<SIKISyncRequest>)syncRequest;


/**
 * This method is called then the Sync request is added to the Queue.
 * @param syncRequest ISyncRequest instance
 */
- (void)onQueue:(id<SIKISyncRequest>)syncRequest;


/**
 * This method is called then Sync request completes its all synchronization data with web service.
 * @param syncRequest ISyncRequest instance
 */
- (void)onFinish:(id<SIKISyncRequest>)syncRequest;


/**
 * This method is called if there is any error/exception while synchronizing data with web service
 * @param syncRequest ISyncRequest instance
 */
- (void)onTerminate:(id<SIKISyncRequest>)syncRequest;

@end
