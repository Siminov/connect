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

#import "SIKIRequest.h"
#import "SIKIServiceEvents.h"
#import "SIKIResource.h"


/**
 * It provides blue print for classes which wants to implement service
 */
@protocol SIKIService <SIKIRequest, SIKIServiceEvents, SIKIResource>

/**
 * Get service request id
 * @return Request id
 */
- (long)getRequestId;


/**
 * Set service request id
 * @param requestId Request Id
 */
- (void)setRequestId:(long)requestId;


/**
 * Get service
 * @return Service
 */
- (NSString *)getService;


/**
 * Add service name
 * @param service Name of service
 */
- (void)setService:(NSString *)service;


/**
 * Get request name
 * @return Name of request
 */
- (NSString *)getRequest;


/**
 * Set request name
 * @param request Name of request
 */
- (void)setRequest:(NSString *)request;


/**
 * Get service descriptor
 * @return Service Descriptor
 */
- (SIKServiceDescriptor *)getServiceDescriptor;


/**
 * Set service descriptor
 * @param serviceDescriptor Service Descriptor
 */
- (void)setServiceDescriptor:(SIKServiceDescriptor *)serviceDescriptor;


/**
 * Invoke the service
 */
- (void)invoke;


/**
 * Terminate the service
 */
- (void)terminate;

@end
