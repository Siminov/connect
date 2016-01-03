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

#import "SICLibraryDescriptor.h"


/**
 * Exposes methods to GET and SET Library Descriptor information as per define in LibraryDescriptor.xml file by application.
	<p>
 <pre>
 
 Example:
	{@code
 
	<library-descriptor>
	
        <!-- General Properties Of Library -->
 
            <!-- Mandatory Field -->
        <property name="name">name_of_library</property>
 
            <!-- Optional Field -->
        <property name="description">description_of_library</property>
	
 
 
        <!-- Entity Descriptor Needed Under This Library Descriptor -->
 
            <!-- Optional Field -->
            <!-- Entity Descriptor -->
        <entity-descriptors>
            <entity-descriptor>name_of_database_descriptor.full_path_of_entity_descriptor_file</entity-descriptor>
        </entity-descriptors>
 
 
        <!-- Service Descriptors -->
 
            <!-- Optional Field -->
            <!-- Service Descriptor -->
        <service-descriptors>
            <service-descriptor>full_path_of_service-descriptor_file</service-descriptor>
        </service-descriptors>
 
 
        <!-- Sync Descriptors -->
 
            <!-- Optional Field -->
            <!-- Sync Descriptor -->
        <sync-descriptors>
            <sync-descriptor>full_path_of_sync_descriptor_file</sync-descriptor>
        </sync-descriptors>
 
 
    </library-descriptor>
	}
	
 </pre>
	</p>
 *
 */
@interface SIKLibraryDescriptor : SICLibraryDescriptor {
    
    NSMutableArray *serviceDescriptorPaths;
    NSMutableDictionary *serviceDescriptorNamesBasedOnPath;
    
    NSMutableArray *syncDescriptorPaths;
    NSMutableDictionary *syncDescriptorNamesBasedOnPath;

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
 * @return Service descriptor paths
 */
- (NSEnumerator *)getServiceDescriptorPaths;

/**
 * Check whether service descriptor path based on name
 * @param serviceDescriptorName Name of service descriptor
 * @return (true/false) TRUE: If service descriptor path exists | FALSE: If service descriptor does not exists
 */
- (bool)containServiceDescriptorPathBasedOnName:(NSString *)serviceDescriptorName;

/**
 * Check whether service descriptor name exists based on path
 * @param serviceDescriptorPath Path of service descriptor
 * @return (true/false) TRUE: If service descriptor name exists | FALSE: If service descriptor name does not exists
 */
- (bool)containServiceDescriptorNameBasedOnPath:(NSString *)serviceDescriptorPath;

/**
 * Get service descriptor path based on name
 * @param serviceDescriptorName Name of serivce descriptor
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
 * Get all sync descriptor paths
 * @return Sync descriptor paths
 */
- (NSEnumerator *)getSyncDescriptorPaths;

/**
 * Check whether sync descriptor path based on name
 * @param syncDescriptorName Name of sync descriptor
 * @return (true/false) TRUE: If sync descriptor path exists | FALSE: If sync descriptor does not exists
 */
- (bool)containSyncDescriptorPathBasedOnName:(NSString *)syncDescriptorName;

/**
 * Check whether sync descriptor name exists based on path
 * @param syncDescriptorPath Path of sync descriptor
 * @return (true/false) TRUE: If sync descriptor name exists | FALSE: If sync descriptor name does not exists
 */
- (bool)containSyncDescriptorNameBasedOnPath:(NSString *)syncDescriptorPath;

/**
 * Get sync descriptor path based on name
 * @param syncDescriptorName Name of sync descriptor
 * @return Path of sync descriptor
 */
- (NSString *)getSyncDescriptorPathBasedOnName:(NSString *)syncDescriptorName;

/**
 * Add sync descriptor name based on path
 * @param syncDescriptorPath Path of sync descriptor
 * @param syncDescriptoName Name of sync descriptor
 */
- (void)addSyncDescriptorNameBasedOnPath:(NSString *)syncDescriptorPath syncDescriptoName:(NSString *)syncDescriptoName;


/**
 * Remove sync descriptor name based on path
 * @param syncDescriptorPath Path of sync descriptor
 */
- (void)removeSyncDescriptorNameBasedOnPath:(NSString *)syncDescriptorPath;

@end
