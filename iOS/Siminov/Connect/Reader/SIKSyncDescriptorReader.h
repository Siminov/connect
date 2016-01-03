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
