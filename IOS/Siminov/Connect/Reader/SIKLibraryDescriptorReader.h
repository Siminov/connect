//
//  SIKLibraryDescriptorReader.h
//  Connect
//
//  Created by user on 21/04/15.
//  Copyright (c) 2015 Siminov. All rights reserved.
//

#import <Foundation/Foundation.h>


#import "SIKLibraryDescriptor.h"
#import "SICSiminovSAXDefaultHandler.h"


/**
 * Exposes methods to parse Library Descriptor information as per define in LibraryDescriptor.xml file by application.
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
            <!-- Database Mappings -->
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
@interface SIKLibraryDescriptorReader : SICSiminovSAXDefaultHandler {

    NSString *libraryName;
    
    SIKLibraryDescriptor *libraryDescriptor;
    
    NSMutableString *tempValue;
    NSString *propertyName;
}

- (id)initWithLibraryName:(NSString * const)libraryname;

/**
 * Get library descriptor
 * @return Library Descriptor
 */
- (SIKLibraryDescriptor *)getLibraryDescriptor;


@end
