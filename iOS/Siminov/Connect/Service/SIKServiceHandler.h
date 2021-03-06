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

#import "SIKSyncServiceWorker.h"
#import "SIKIWorker.h"
#import "SIKResourceManager.h"
#import "SIKIService.h"

/**
 * It is a singleton class which handles all service requests
 */
@interface SIKServiceHandler : NSObject {
    
    SIKSyncServiceWorker *syncServiceWorker;
    id<SIKIWorker> asyncServiceWorker;
    
    SIKResourceManager *resourceManager;
}

/**
 * It provides singleton instance of ServiceHandler class
 * @return ServiceHandler singleton instance
 */
+ (SIKServiceHandler *)getInstance;

/**
 * It handles the service request
 * @param service Service instance
 * @throws ServiceException If any exception occur while handling the service request
 */
- (void)handle:(id<SIKIService>)service;

@end
