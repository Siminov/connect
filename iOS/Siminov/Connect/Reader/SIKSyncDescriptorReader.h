//
//  SIKSyncDescriptorReader.h
//  Connect
//
//  Created by user on 21/04/15.
//  Copyright (c) 2015 Siminov. All rights reserved.
//

#import <Foundation/Foundation.h>

#import "SICResourceManager.h"
#import "SIKSyncDescriptor.h"
#import "SICSiminovSAXDefaultHandler.h"


/**
 * Exposes methods to parse Sync Descriptor information as per define in SyncDescriptor.xml file by application.
	<p>
 <pre>
 
 Example:
	{@code
 
    <sync-descriptor>
 
            <!-- Mandatory Field -->
        <property name="name">name_of_sync_handler</property>
 
            <!-- Optional Field -->
        <property name="sync_interval">sync_interval_in_millisecond</property>
 
            <!-- Optional Field -->
            <!-- Default: SCREEN -->
        <property name="type">INTERVAL|SCREEN|INTERVAL-SCREEN</property>
 
            <!-- Service Descriptors -->
            <!-- Service Descriptor -->
        <service-descriptors>
 
            <service-descriptor>name_of_service_descriptor.name_of_api</service-descriptor>
 
        </service-descriptors>
 
    </sync-descriptor>
 
	}
	
 </pre>
	</p>
 *
 */
@interface SIKSyncDescriptorReader : SICSiminovSAXDefaultHandler {
    
    SICResourceManager *resourceManager;
    
    NSMutableString *tempValue;
    NSString *propertyName;
    
    SIKSyncDescriptor *syncDescriptor;
}

- (id)initWithPath:(NSString * const)syncDescriptorPath;

/**
 * Get application descriptor object.
 * @return Application Descriptor Object.
 */
- (SIKSyncDescriptor *)getSyncDescriptor;

@end
