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
