///
/// [SIMINOV FRAMEWORK - CONNECT]
/// Copyright [2014-2016] [Siminov Software Solution LLP|support@siminov.com]
///
/// Licensed under the Apache License, Version 2.0 (the "License");
/// you may not use this file except in compliance with the License.
/// You may obtain a copy of the License at
///
///     http://www.apache.org/licenses/LICENSE-2.0
///
/// Unless required by applicable law or agreed to in writing, software
/// distributed under the License is distributed on an "AS IS" BASIS,
/// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
/// See the License for the specific language governing permissions and
/// limitations under the License.
///

#import <Foundation/Foundation.h>

#import "SIKISyncRequest.h"
#import "SIKResourceManager.h"
#import "SIKSyncWorker.h"
#import "SIKISyncRequest.h"


/**
 * It handles all request related to sync
 */
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
