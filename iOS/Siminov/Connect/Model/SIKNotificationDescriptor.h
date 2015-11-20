//
//  SIKNotificationDescriptor.h
//  Connect
//
//  Created by user on 21/04/15.
//  Copyright (c) 2015 Siminov. All rights reserved.
//

#import <Foundation/Foundation.h>

#import "SICIDescriptor.h"

/**
 * Exposes methods to GET and SET Notification Descriptor information as per define in ApplicationDescriptor.xml file by application.
 *
 *	<p>
 <pre>
 
 Example:
	{@code
 
	<siminov>
 
        <!-- Notification Descriptor -->
        <notification-descriptor>
 
                <!-- Optional Field -->
            <property name="name_of_property">value_of_property</property>
	
        </notification-descriptor>
 
	</siminov>
	
	}
	
 </pre>
	</p>
 */
@interface SIKNotificationDescriptor : NSObject <SICIDescriptor> {
    NSMutableDictionary *properties;
}

@end
