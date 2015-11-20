//
//  SIKIResource.h
//  Connect
//
//  Created by user on 21/04/15.
//  Copyright (c) 2015 Siminov. All rights reserved.
//

#import <Foundation/Foundation.h>

/**
 * It exposes APIs to Get and Set resources
 */
@protocol SIKIResource <NSObject>


/**
 * Get all resources
 * @return Resources
 */
- (NSEnumerator *)getResources;


/**
 * Get resource based on name
 * @param name Name of resource
 * @return Resource
 */
- (id)getResource:(NSString *)name;


/**
 * Add resource
 * @param name Name of resource
 * @param value Value of resource
 */
- (void)addResource:(NSString *)name value:(id)value;


/**
 * Check whether it contains resource or not
 * @param name Name of resource
 * @return (true/false) TRUE: If resource exists | FALSE: If resource does not exists
 */
- (bool)containResource:(NSString *)name;

@end
