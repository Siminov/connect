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

#import "SIKApplicationDescriptor.h"
#import "SIKServiceDescriptor.h"
#import "SIKISyncEvents.h"
#import "SIKINotificationEvents.h"


/**
 * It handles and provides all resources needed by SIMINOV HYBRID.
 * <p>
 * Such As: Provides HybridDescriptor, AdapterDescriptor.
 */
@interface SIKResourceManager : NSObject

/**
 * It provides ResourceManager singleton instance
 * @return ResourceManager
 */
+ (SIKResourceManager *)getInstance;

/**
 * Get application descriptor
 * @return Application Descriptor
 */
- (SIKApplicationDescriptor *)getApplicationDescriptor;

/**
 * Set application descriptor
 * @param applicationDescriptor Application Descriptor
 */
- (void)setApplicationDescriptor:(SIKApplicationDescriptor *)appDescriptor;

/**
 * Parse and get service descriptor based on path
 * @param serviceDescriptorPath Path of service descriptor
 * @return Service Descriptor
 */
- (SIKServiceDescriptor *)requiredServiceDescriptorBasedOnPath:(NSString *)serviceDescriptorPath;

/**
 * Parse and get service descriptor based on name
 * @param serviceDescriptorName Name of service descriptor
 * @return Service Descriptor
 */
- (SIKServiceDescriptor *)requiredServiceDescriptorBasedOnName:(NSString *)serviceDescriptorName;


/**
 * Parse and get service request based on service descriptor path
 * @param serviceDescriptorPath Path of service descriptor
 * @param requestName Name of request
 * @return Request
 */
- (SIKRequest *)requiredRequestBasedOnServiceDescriptorPath:(NSString *)serviceDescriptorPath requestName:(NSString *)requestName;


/**
 * Parse and get service request based on service descriptor name
 * @param serviceDescriptorName Name of service descriptor
 * @param requestName Name of request
 * @return Request
 */
- (SIKRequest *)requireRequestBasedOnServiceDescriptorName:(NSString *)serviceDescriptorName requestName:(NSString *)requestName;

/**
 * Get notification event handler
 * @return INotificationEvents instance
 */
- (id<SIKINotificationEvents>)getNotificationEventHandler;

/**
 * Get sync event handler
 * @return ISyncEvents instance
 */
- (id<SIKISyncEvents>)getSyncEventHandler;

/**
 * Get all sync descriptors
 * @return Sync Descriptors
 */
- (NSEnumerator *)getSyncDescriptors;

/**
 * Get sync descriptor
 * @param syncDescriptorName Name of sync descriptor
 * @return Sync Descriptor
 */
- (SIKSyncDescriptor *)getSyncDescriptor:(NSString *)syncDescriptorName;

@end
