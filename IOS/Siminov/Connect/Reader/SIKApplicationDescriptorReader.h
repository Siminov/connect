//
//  SIKApplicationDescriptorReader.h
//  Connect
//
//  Created by user on 21/04/15.
//  Copyright (c) 2015 Siminov. All rights reserved.
//

#import <Foundation/Foundation.h>

#import "SIKApplicationDescriptor.h"
#import "SICResourceManager.h"
#import "SICSiminovSAXDefaultHandler.h"


/**
 * Exposes methods to parse Application Descriptor information as per define in ApplicationDescriptor.xml file by application.
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
@interface SIKApplicationDescriptorReader : SICSiminovSAXDefaultHandler {
    
    SIKApplicationDescriptor *applicationDescriptor;
    
    SICResourceManager *resourceManager;
    
    NSMutableString *tempValue;
    NSString *propertyName;
    
    SIKNotificationDescriptor *notificationDescriptor;
    
    bool isNotificationDescriptor;
}

/**
 * Get application descriptor object. 
 * @return Application Descriptor Object.
 */
- (SIKApplicationDescriptor *)getApplicationDescriptor;

@end
