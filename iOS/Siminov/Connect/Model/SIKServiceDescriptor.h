//
//  SIKServiceDescriptor.h
//  Connect
//
//  Created by user on 21/04/15.
//  Copyright (c) 2015 Siminov. All rights reserved.
//

#import <Foundation/Foundation.h>


#import "SICIDescriptor.h"

@class SIKRequest, SIKQueryParameter, SIKHeaderParameter;

/**
 * Exposes methods to GET and SET Service Descriptor information as per define in ServiceDescriptor.xml file by application.
 *	<p>
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
 */
@interface SIKServiceDescriptor : NSObject <SICIDescriptor> {
    NSMutableDictionary *properties;
    NSMutableDictionary *requests;
}

/**
 * Get service descriptor name
 * @return Name of service descriptor
 */
- (NSString *)getName;

/**
 * Set service descriptor name
 * @param name Name of service descriptor
 */
- (void)setName:(NSString *)name;

/**
 * Get description
 * @return Description
 */
- (NSString *)getDescription;

/**
 * Set description
 * @param description Description
 */
- (void)setDescription:(NSString *)description;

/**
 * Get protocol
 * @return Protocol
 */
- (NSString *)getProtocol;

/**
 * Set protocol
 * @param protocol Protocol
 */
- (void)setProtocol:(NSString *)protocol;

/**
 * Get instance
 * @return Instance
 */
- (NSString *)getInstance;

/**
 * Set instance
 * @param instance Instance
 */
- (void)setInstance:(NSString *)instance;

/**
 * Get port
 * @return Port
 */
- (NSString *)getPort;

/**
 * Set port
 * @param port Port
 */
- (void)setPort:(NSString *)port;

/**
 * Get context
 * @return Context
 */
- (NSString *)getContext;

/**
 * Set context
 * @param context Context
 */
- (void)setContext:(NSString *)context;

/**
 * Get all Request
 * @return Request
 */
- (NSEnumerator *)getRequest;

/**
 * Get Request
 * @param name Name of request
 * @return Request
 */
- (SIKRequest *)getRequest:(NSString *)name;

/**
 * Add request
 * @param request Request instance
 */
- (void)addRequest:(SIKRequest *)request;

/**
 * Check whether request exists or not
 * @param name Name of request
 * @return (true/false) TRUE: If request exists | FALSE: If request does not exists
 */
- (bool)containRequest:(NSString *)name;

/**
 * Remove request
 * @param request Request instance
 */
- (void)removeRequest:(SIKRequest *)request;

@end


/**
 * It defines the structure Web Service request
 * It exposes API to Get and Set request information
 */
@interface SIKRequest : NSObject <SICIDescriptor> {
    
    NSMutableDictionary *properties;
    
    NSMutableDictionary *queryParameters;
    NSMutableDictionary *headerParameters;
    
    NSData *dataStream;
}

/**
 * Get name of request
 * @return Name of request
 */
- (NSString *)getName;

/**
 * Set name of request
 * @param name Name of request
 */
- (void)setName:(NSString *)name;

/**
 * Get type of request
 * @return Type of request
 */
- (NSString *)getType;

/**
 * Set type of request
 * @param type Type of request
 */
- (void)setType:(NSString *)type;

/**
 * Get API name
 * @return Name of API
 */
- (NSString *)getApi;

/**
 * Set API name
 * @param api Name of API
 */
- (void)setApi:(NSString *)api;

/**
 * Get handler
 * @return Handler
 */
- (NSString *)getHandler;

/**
 * Set Handler
 * @param handler Handler
 */
- (void)setHandler:(NSString *)handler;

/**
 * Get mode of request
 * @return Mode of request
 */
- (NSString *)getMode;

/**
 * Set mode of request
 * @param mode Mode of request
 */
- (void)setMode:(NSString *)mode;

/**
 * Get data stream
 * @return Data Stream
 */
- (NSString *)getDataStream;

/**
 * Set data stream
 * @param dataStream Data Stream
 */
- (void)setDataStream:(NSString *)data;

/**
 * Get all query parameters
 * @return Query Parameters
 */
- (NSEnumerator *)getQueryParameters;

/**
 * Get query parameter
 * @param name Name of query parameter
 * @return Query Parameter
 */
- (SIKQueryParameter *)getQueryParameter:(NSString *)name;

/**
 * Check whether query parameter exists or not
 * @param name Name of query parameter
 * @return (true/false) TRUE: If query parameter exists | FALSE: If query parameter does not exists
 */
- (bool)containQueryParameter:(NSString *)name;

/**
 * Add query parameter
 * @param queryParameter Query Parameter
 */
- (void)addQueryParameter:(SIKQueryParameter *)queryParameter;

/**
 * Remove query parameter
 * @param queryParameter Query Parameter
 */
- (void)removeQueryParameter:(SIKQueryParameter *)queryParameter;

/**
 * Get all header parameters
 * @return Header Parameters
 */
- (NSEnumerator *)getHeaderParameters;

/**
 * Get header parameter
 * @param name Name of header parameter
 * @return Header Parameter
 */
- (SIKHeaderParameter *)getHeaderParameter:(NSString *)name;

/**
 * Add header parameter
 * @param headerParameter Header Parameter
 */
- (void)addHeaderParameter:(SIKHeaderParameter *)headerParameter;

/**
 * Check whether header parameter exists or not
 * @param name Name of header parameter
 * @return (true/false) TRUE: If header parameter exists | FALSE: If header parameter does not exists
 */
- (bool)containHeaderParameter:(NSString *)name;

/**
 * Remove header parameter
 * @param headerParameter Header Parameter
 */
- (void)removeHeaderParameter:(SIKHeaderParameter *)headerParameter;

@end


/**
 * It exposes APIs to Get and Set query parameter
 */
@interface SIKQueryParameter : NSObject <SICIDescriptor> {
    
    NSMutableDictionary *properties;
}

/**
 * Get query parameter name
 * @return Name of query parameter
 */
- (NSString *)getName;

/**
 * Set name of query parameter
 * @param name Name of query parameter
 */
- (void)setName:(NSString *)name;

/**
 * Get value of query parameter
 * @return Value of query parameter
 */
- (NSString *)getValue;

/**
 * Set values of query parameter
 * @param value Query Parameter
 */
- (void)setValue:(NSString *)value;

@end
    

/**
 * It exposes APIs to Get and Set header parameter
 */
@interface SIKHeaderParameter : NSObject <SICIDescriptor> {
    
    NSMutableDictionary *properties;
}
    
/**
 * Get name of header parameter
 * @return Name of header parameter
 */
- (NSString *)getName;

/**
 * Set name of header parameter
 * @param name Name of header parameter
 */
- (void)setName:(NSString *)name;

/**
 * Get value of header parameter
 * @return Value of header parameter
 */
- (NSString *)getValue;

/**
 * Set value of header parameter
 * @param value Value of header parameter
 */
- (void)setValue:(NSString *)value;

@end
