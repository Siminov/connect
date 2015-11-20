//
//  SIKQuickServiceDescriptorReader.h
//  Connect
//
//  Created by user on 21/04/15.
//  Copyright (c) 2015 Siminov. All rights reserved.
//

#import <Foundation/Foundation.h>

#import "SIKServiceDescriptor.h"
#import "SICSiminovSAXDefaultHandler.h"

@interface SIKQuickServiceDescriptorReader : SICSiminovSAXDefaultHandler {
    
    NSMutableString *tempValue;
    NSString *finalServiceDescriptorName;
    
    SIKServiceDescriptor *serviceDescriptor;
    bool doesMatch;
    bool isNameProperty;
}


/**
 * SIKQuickServiceDescriptorReader Constructor
 @param findServiceDescriptorBasedOnClassName Name of the service descriptor class name
 */
- (id)initWithClassName:(NSString * const)findServiceDescriptorName;


- (void)process;

/**
 * Get service descriptor
 * @return Service Descriptor
 */
- (SIKServiceDescriptor *)getServiceDescriptor;

/**
 * Set service descriptor
 * @return Service Descriptor
 */
- (bool)containServiceDescriptor;

@end
