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


#import "SIKINotificationEvents.h"
#import "SIKISyncEvents.h"
#import "SICResourceManager.h"


/**
 * It provides the event handler instances.
 */
@interface SIKEventHandler : NSObject

/**
 * It provides EventHandler singleton instance
 * @return EventHandler singleton instance
 */
+ (SIKEventHandler *)getInstance;

/**
 * Get notification event handler
 * @return NotificationEventHandler instance
 */
- (id<SIKINotificationEvents>)getNotificationEventHandler;

/**
 * Get sync event handler instance
 * @return SyncEventHandler instance
 */
- (id<SIKISyncEvents>)getSyncEventHandler;

@end
