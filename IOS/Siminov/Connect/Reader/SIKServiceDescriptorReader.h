//
//  SIKServiceDescriptorReader.h
//  Connect
//
//  Created by user on 21/04/15.
//  Copyright (c) 2015 Siminov. All rights reserved.
//

#import <Foundation/Foundation.h>

#import "SICSiminovSAXDefaultHandler.h"
#import "SICResourceManager.h"
#import "SIKServiceDescriptor.h"
#import "SICSiminovSAXDefaultHandler.h"


/**
 * Exposes methods to parse Service Descriptor information as per define in ServiceDescriptor.xml file by application.
	<p>
 <pre>
 
 Example:
	{@code
 
	<service-descriptor>
 
        <!-- General Service Properties -->
            <!-- Mandatory Field -->
        <property name="name">name_of_service</property>
 
            <!-- Optional Field -->
        <property name="description">description_of_service</property>
 
            <!-- Optional Field (DEFAULT: HTTP) -->
        <property name="protocol">HTTP|HTTPS</property>
 
            <!-- Mandatory Field -->
        <property name="instance">address_of_instance</property>
 
            <!-- Optional Field -->
        <property name="port">port_number</property>
	
            <!-- Optional Field -->
        <property name="context">context_of_service</property>
 
        <!-- Requests -->
            <!-- Request -->
        <request>
 
            <request>
	
                <!-- General Request Properties -->
 
                    <!-- Mandatory Field -->
                <property name="name">name_of_request</property>
 
                    <!-- Mandatory Field -->
                <property name="type">GET|HEAD|POST|PUT|DELETE|TRACE|OPTIONS|CONNECT|PATCH</property>
	
                    <!-- Mandatory Field -->
                <property name="api">full_request_path</property>
	
                    <!-- Mandatory Field -->
                <property name="handler">handler_of_request</property>
 
                    <!-- Optional Field (DEFAULT: SYNC)-->
                <property name="mode">SYNC|ASYNC</property>
	
 
 
                <!-- Query Parameters -->
                    <!-- Query Parameter -->
                <query-parameters>
 
                    <query-parameter>
 
                            <!-- Mandatory Field -->
                        <property name="name">name_of_query_parameter</property>
 
                            <!-- Mandatory Field -->
                        <property name="value">value_of_query_parameter</property>
 
                    </query-parameter>
 
                </query-parameters>
 
 
                <!-- Header Parameters -->
                    <!-- Header Parameter -->
                <header-parameters>
 
                    <header-parameter>
 
                            <!-- Mandatory Field -->
                        <property name="name">name_of_header_parameter</property>
 
                            <!-- Mandatory Field -->
                        <property name="value">value_of_header_parameter</property>
 
                    </header-parameter>
 
                </header-parameters>
	
 
                <!-- Stream of Data Under Request Body -->
                    <!-- It is Optional Property -->
                <data-stream>stream_of_data</data-stream>
 
            </request>
        </requests>
 
	</service-descriptor>
	
 
 
	}
	
 </pre>
	</p>
 *
 */
@interface SIKServiceDescriptorReader : SICSiminovSAXDefaultHandler {
    
    NSMutableString *tempValue;
    SICResourceManager *resourceManager;
    
    SIKServiceDescriptor *serviceDescriptor;
    
    SIKRequest *request;
    
    SIKQueryParameter *queryParameter;
    SIKHeaderParameter *headerParameter;
    
    bool isRequest;
    bool isQueryParameter;
    bool isHeaderParameter;
    
    NSString *propertyName;

}

- (id)initWithPath:(NSString *)serviceDescriptorPath;
- (id)initWithLibraryName:(NSString *)libraryPackageName serviceDescriptorPath:(NSString *)serviceDescriptorPath;

/**
 * Get service descriptor
 * @return Service Descriptor
 */
- (SIKServiceDescriptor *)getServiceDescriptor;

@end
