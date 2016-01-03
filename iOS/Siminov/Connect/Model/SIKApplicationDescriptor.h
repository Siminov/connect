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

#import "SICApplicationDescriptor.h"
#import "SIKNotificationDescriptor.h"
#import "SIKSyncDescriptor.h"

/**
 * Exposes methods to GET and SET Application Descriptor information as per define in ApplicationDescriptor.xml file by application.
	<p>
 <pre>
 
 Example:
	{@code
 
	<siminov>
 
        <!-- General Application Description Properties -->
 
            <!-- Mandatory Field -->
        <property name="name">application_name</property>
 
            <!-- Optional Field -->
        <property name="description">application_description</property>
 
            <!-- Mandatory Field (Default is 0.0) -->
        <property name="version">application_version</property>
	
	
 
        <!-- Database Descriptors Used By Application (zero-to-many) -->
            <!-- Optional Field's -->
        <database-descriptors>
            <database-descriptor>full_path_of_database_descriptor_file</database-descriptor>
        </database-descriptors>
 
	
        <!-- Service Descriptors -->
        <service-descriptors>
 
            <!-- Service Descriptor -->
            <service-descriptor>full_path_of_service_descriptor</service-descriptor>
 
        </service-descriptors>
	
 
 
        <!-- Sync Descriptors -->
            <!-- Sync Descriptor -->
        <sync-descriptors>
 
            <sync-descriptor>full_path_of_sync_descriptor</sync-descriptor>
 
        </sync-descriptors>
 
	
        <!-- Notification Descriptor -->
        <notification-descriptor>
 
                <!-- Optional Field -->
            <property name="name_of_property">value_of_property</property>
	
        </notification-descriptor>
 
 
 
        <!-- Library Descriptors Used By Application (zero-to-many) -->
            <!-- Optional Field's -->
        <library-descriptors>
            <library-descriptor>full_path_of_library_descriptor_file</library-descriptor>
        </librar-descriptors>
 
 
        <!-- Event Handlers Implemented By Application (zero-to-many) -->
 
            <!-- Optional Field's -->
        <event-handlers>
            <event-handler>full_class_path_of_event_handler_(ISiminovHandler/IDatabaseHandler)</event-handler>
        </event-handlers>
	
	</siminov>
	
	}
	
 </pre>
	</p>
 *
 */
@interface SIKApplicationDescriptor : SICApplicationDescriptor {
    
    NSMutableArray *serviceDescriptorPaths;
    NSMutableDictionary *serviceDescriptorNamesBasedOnPath;
    
    NSMutableArray *syncDescriptorPaths;
    
    NSMutableDictionary *syncDescriptorsBasedOnName;
    NSMutableDictionary *syncDescriptorsBasedOnPath;
    
    SIKNotificationDescriptor *notificationDescriptor;
}

/**
 * Add service descriptor path
 * @param serviceDescriptorPath Path of service descriptor
 */
- (void)addServiceDescriptorPath:(NSString *)serviceDescriptorPath;

/**
 * Remove service descriptor path
 * @param serviceDescriptorPath Path of service descriptor
 */
- (void)removeServiceDescriptorPath:(NSString *)serviceDescriptorPath;


/**
 * Get all service descriptor paths
 * @return Path of service descriptor
 */
- (NSEnumerator *) getServiceDescriptorPaths;


/**
 * Check whether service descriptor exists or not based on name
 * @param serviceDescriptorName Name of service descriptor
 * @return (true/false) TRUE: If service descriptor exists | FALSE: If service descriptor does not exists
 */
- (bool)containServiceDescriptorPathBasedOnName:(NSString *)serviceDescriptorName;

/**
 * Check whether service descriptor exists or not based on path
 * @param serviceDescriptorPath Path of service descriptor
 * @return (true/false) TRUE: If service descriptor exists | FALSE: If service descriptor does not exists
 */
- (bool)containServiceDescriptorNameBasedOnPath:(NSString *)serviceDescriptorPath;

/**
 * Get service descriptor path based on name
 * @param serviceDescriptorName Name of service descriptor
 * @return Path of service descriptor
 */
- (NSString *)getServiceDescriptorPathBasedOnName:(NSString *)serviceDescriptorName;

/**
 * Add service descriptor name based on path
 * @param serviceDescriptorPath Path of service descriptor
 * @param serviceDescriptoName Name of service descriptor
 */
- (void)addServiceDescriptorNameBasedOnPath:(NSString *)serviceDescriptorPath serviceDescriptorName:(NSString *)serviceDescriptoName;

/**
 * Remove service descriptor name based on path
 * @param serviceDescriptorPath Path of service descriptor
 */
- (void)removeServiceDescriptorNameBasedOnPath:(NSString *)serviceDescriptorPath;


/**
 * Get sync descriptor paths
 * @return Paths of sync descriptors
 */
- (NSEnumerator *)getSyncDescriptorPaths;

/**
 * Add sync descriptor path
 * @param syncDescriptorPath Path of sync descriptor
 */
- (void)addSyncDescriptorPath:(NSString *)syncDescriptorPath;

/**
 * Remove sync descriptor path
 * @param syncDescriptorPath Path of sync descriptor
 */
- (void)removeSyncDescriptorPath:(NSString *)syncDescriptorPath;


/**
 * Check whether sync descriptor path exists or not
 * @param syncDescriptorPath Path of sync descriptor
 * @return (true/false) TRUE: If sync descriptor path exists | FALSE: If sync descriptor path does not exists
 */
- (bool)containSyncDescriptorPath:(NSString *)syncDescriptorPath;

/**
 * Get all sync descriptors
 * @return Sync Descriptors
 */
- (NSEnumerator *)getSyncDescriptors;

/**
 * Get sync descriptor based on path
 * @param syncDescriptorPath Path of sync descriptor
 * @return Sync Descriptor
 */
- (SIKSyncDescriptor *)getSyncDescriptorBasedOnPath:(NSString *)syncDescriptorPath;

/**
 * Get sync descriptor based on name
 * @param syncDescriptorName Name of sync descriptor
 * @return Sync Descriptor
 */
- (SIKSyncDescriptor *)getSyncDescriptorBasedOnName:(NSString *)syncDescriptorName;

/**
 * Add sync descriptor
 * @param syncDescriptorPath Path of sync descriptor
 * @param syncDescriptor Sync Descriptor
 */
- (void)addSyncDescriptor:(NSString *)syncDescriptorPath syncDescriptor:(SIKSyncDescriptor *)syncDescriptor;

/**
 * Check whether sync descriptor exists or not
 * @param syncDescriptorName Name of sync descriptor
 * @return (true/false) TRUE: If sync descriptor exists |  FALSE: If sync descriptor does not exists
 */
- (bool)containSyncDescriptor:(NSString *)syncDescriptorName;

/**
 * Remove sync descriptor
 * @param syncDescriptorName Name of sync descriptor
 */
- (void)removeSyncDescriptor:(NSString *)syncDescriptorName;

/**
 * Get notification descriptor
 * @return Notification Descriptor
 */
- (SIKNotificationDescriptor *)getNotificationDescriptor;

/**
 * Set notification descriptor
 * @param notificationDescriptor Notification Descriptor
 */
- (void)setNotificationDescriptor:(SIKNotificationDescriptor *)notificationDescriptor;

@end
