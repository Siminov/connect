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

#import "SICDatabase.h"

@class SIKServiceRequest;

/**
 * It exposes APIs to Get and Set service request resource information
 * It helps framework to save ASYNC request in database
 */
@interface SIKServiceRequestResource : SICDatabase {
    
    SIKServiceRequest *serviceRequest;
    
    NSString *name;
    NSString *value;

}

/**
 * Get service request
 * @return Service Request
 */
- (SIKServiceRequest *)getServiceRequest;

/**
 * Set service request
 * @param serviceRequest Service Request
 */
- (void)setServiceRequest:(SIKServiceRequest *)serviceRequest;

/**
 * Get resource name
 * @return Resource Name
 */
- (NSString *)getName;

/**
 * Set resource name
 * @param name Resource Name
 */
- (void)setName:(NSString *)name;

/**
 * Get resource value
 * @return Resource Value
 */
- (NSString *)getValue;

/**
 * Set resource value
 * @param value Resource Value
 */
- (void)setValue:(NSString *)value;

@end
