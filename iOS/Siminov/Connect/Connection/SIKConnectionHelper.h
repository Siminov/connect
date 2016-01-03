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
#import "SIKConnectionRequest.h"
#import "SIKIService.h"

/**
 * It provides Utility APIs for the communication
 */
@interface SIKConnectionHelper : NSObject

/**
 * It build connection request instance based on the service descriptor object
 * @param service Instance of IService
 * @return IConnectionRequest instance
 */
+ (id<SIKIConnectionRequest>)prepareConnectionRequest:(id<SIKIService> const)service;

@end
