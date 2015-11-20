//
//  SIKSyncDescriptor.h
//  Connect
//
//  Created by user on 21/04/15.
//  Copyright (c) 2015 Siminov. All rights reserved.
//

#import <Foundation/Foundation.h>


#import "SICIDescriptor.h"
#import "SIKConstants.h"

/**
 * Exposes methods to GET and SET Sync Descriptor information as per define in SyncDescriptor.xml file by application.
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
 
        <!-- Services -->
            <!-- Service -->
        <services>
 
            <service>name_of_service.name_of_api</service>
 
        </services>
	
	</sync-descriptor>
	
	}
	
 </pre>
	</p>
 *
 */
@interface SIKSyncDescriptor : NSObject<SICIDescriptor> {
    
    NSMutableDictionary *properties;
    
    NSMutableArray *serviceDescriptorNames;
}

/**
 * Get sync descriptor name
 * @return Name of sync descriptor
 */
- (NSString *)getName;

/**
 * Set sync descriptor name
 * @param name Name of sync descriptor
 */
- (void)setName:(NSString *)name;

/**
 * Get sync interval
 * @return Sync Interval
 */
- (long)getSyncInterval;

/**
 * Set sync interval
 * @param syncInterval Sync Interval
 */
- (void)setSyncInterval:(long)syncInterval;

/**
 * Get all service descriptor names
 * @return Service Descriptor Names
 */
- (NSEnumerator *)getServiceDescriptorNames;

/**
 * Add service descriptor name
 * @param serviceDescriptorName Name of service descriptor
 */
- (void)addServiceDescriptorName:(NSString *)serviceDescriptorName;

/**
 * Remove service descriptor name
 * @param serviceDescriptorName Service Descriptor Name
 */
- (void)removeServiceDescriptorName:(NSString *)serviceDescriptorName;

@end
