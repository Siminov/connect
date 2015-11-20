//
//  SIKResourceManager.h
//  Connect
//
//  Created by user on 21/04/15.
//  Copyright (c) 2015 Siminov. All rights reserved.
//

#import <Foundation/Foundation.h>

#import "SIKApplicationDescriptor.h"
#import "SIKServiceDescriptor.h"
#import "SIKISyncEvents.h"
#import "SIKINotificationEvents.h"

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
