//
//  SIKServiceDescriptor.m
//  Connect
//
//  Created by user on 21/04/15.
//  Copyright (c) 2015 Siminov. All rights reserved.
//

#import "SIKServiceDescriptor.h"

#import "SIKConstants.h"

@implementation SIKServiceDescriptor

- (id)init {
    
    self = [super init];
    
    if(self) {
        
        properties = [[NSMutableDictionary alloc] init];
        requests = [[NSMutableDictionary alloc] init];
        
        return self;
    }
    
    return self;
}


- (NSString *)getName {
    return [properties objectForKey:SERVICE_DESCRIPTOR_NAME];
}


- (void)setName:(NSString *)name {
    [properties setValue:name forKey:SERVICE_DESCRIPTOR_NAME];
}


- (NSString *)getDescription {
    return [properties objectForKey:SERVICE_DESCRIPTOR_DESCRIPTION];
}


- (void)setDescription:(NSString *)description {
    [properties setValue:description forKey:SERVICE_DESCRIPTOR_DESCRIPTION];
}


- (NSString *)getProtocol {
    return [properties objectForKey:SERVICE_DESCRIPTOR_PROTOCOL];
}


- (void)setProtocol:(NSString *)protocol {
    [properties setValue:protocol forKey:SERVICE_DESCRIPTOR_PROTOCOL];
}


- (NSString *)getInstance {
    return [properties objectForKey:SERVICE_DESCRIPTOR_INSTANCE];
}


- (void)setInstance:(NSString *)instance {
    [properties setValue:instance forKey:SERVICE_DESCRIPTOR_INSTANCE];
}


- (NSString *)getPort {
    return [properties objectForKey:SERVICE_DESCRIPTOR_PORT];
}


- (void)setPort:(NSString *)port {
    [properties setValue:port forKey:SERVICE_DESCRIPTOR_PORT];
}


- (NSString *)getContext {
    return [properties objectForKey:SERVICE_DESCRIPTOR_CONTEXT];
}


- (void)setContext:(NSString *)context {
    [properties setValue:context forKey:SERVICE_DESCRIPTOR_CONTEXT];
}

- (NSEnumerator *)getProperties {
    return [[properties allKeys] objectEnumerator];
}

- (NSString *)getProperty:(NSString *)name {
    return [properties objectForKey:name];
}

- (bool)containProperty:(NSString *)name {
    return [[properties allKeys] containsObject:name];
}

- (void)addProperty:(NSString *)name value:(NSString *)value {
    [properties setObject:value forKey:name];
}

- (void)removeProperty:(NSString *)name {
    [properties removeObjectForKey:name];
}


- (NSEnumerator *)getRequest {
    return [[requests allValues] objectEnumerator];
}


- (SIKRequest *)getRequest:(NSString *)name {
    return [requests objectForKey:name];
}


- (void)addRequest:(SIKRequest *)request {
    [requests setValue:request forKey:[request getName]];
}


- (bool)containRequest:(NSString *)name {
    return [self containRequest:name];
}


- (void)removeRequest:(SIKRequest *)request {
    [requests removeObjectForKey:request];
}

@end

@implementation SIKRequest
    
- (id)init {
    
    self = [super init];
    
    if(self) {
        
        properties = [[NSMutableDictionary alloc] init];
        
        queryParameters = [[NSMutableDictionary alloc] init];
        headerParameters = [[NSMutableDictionary alloc] init];
        
        dataStream = [[NSData alloc] init];
        
        return self;
    }
    
    return self;
}


- (NSString *)getName {
    return [properties objectForKey:SERVICE_DESCRIPTOR_REQUEST_NAME];
}


- (void)setName:(NSString *)name {
    [properties setValue:name forKey:SERVICE_DESCRIPTOR_REQUEST_NAME];
}


- (NSString *)getType {
    return [properties objectForKey:SERVICE_DESCRIPTOR_REQUEST_TYPE];
}


- (void)setType:(NSString *)type {
    [properties setValue:type forKey:SERVICE_DESCRIPTOR_REQUEST_TYPE];
}


- (NSString *)getApi {
    return [properties objectForKey:SERVICE_DESCRIPTOR_REQUEST_API];
}


- (void)setApi:(NSString *)api {
    [properties setValue:api forKey:SERVICE_DESCRIPTOR_REQUEST_API];
}


- (NSString *)getHandler {
    return [properties objectForKey:SERVICE_DESCRIPTOR_REQUEST_HANDLER];
}


- (void)setHandler:(NSString *)handler {
    [properties setValue:handler forKey:SERVICE_DESCRIPTOR_REQUEST_HANDLER];
}


- (NSString *)getMode {
    return [properties objectForKey:SERVICE_DESCRIPTOR_REQUEST_MODE];
}


- (void)setMode:(NSString *)mode {
    [properties setValue:mode forKey:SERVICE_DESCRIPTOR_REQUEST_MODE];
}


- (NSData *)getDataStream {
    return dataStream;
}


- (void)setDataStream:(NSData *)data {
    dataStream = data;
}

- (NSEnumerator *)getProperties {
    return [[properties allKeys] objectEnumerator];
}

- (NSString *)getProperty:(NSString *)name {
    return [properties objectForKey:name];
}

- (bool)containProperty:(NSString *)name {
    return [[properties allKeys] containsObject:name];
}

- (void)addProperty:(NSString *)name value:(NSString *)value {
    [properties setValue:value forKey:name];
}

- (void)removeProperty:(NSString *)name {
    [properties removeObjectForKey:name];
}


- (NSEnumerator *)getQueryParameters {
    return [[queryParameters allValues] objectEnumerator];
}


- (SIKQueryParameter *)getQueryParameter:(NSString *)name {
    return [queryParameters objectForKey:name];
}


- (bool)containQueryParameter:(NSString *)name {
    return [[queryParameters allKeys] containsObject:name];
}


- (void)addQueryParameter:(SIKQueryParameter *)queryParameter {
    [queryParameters setValue:queryParameter forKey:[queryParameter getName]];
}


- (void)removeQueryParameter:(SIKQueryParameter *)queryParameter {
    [queryParameters removeObjectForKey:[queryParameter getName]];
}


- (NSEnumerator *)getHeaderParameters {
    return [[headerParameters allValues] objectEnumerator];
}


- (SIKHeaderParameter *)getHeaderParameter:(NSString *)name {
    return [headerParameters objectForKey:name];
}


- (void)addHeaderParameter:(SIKHeaderParameter *)headerParameter {
    [headerParameters setValue:headerParameter forKey:[headerParameter getName]];
}


- (bool)containHeaderParameter:(NSString *)name {
    return [[headerParameters allKeys] containsObject:name];
}


- (void)removeHeaderParameter:(SIKHeaderParameter *)headerParameter {
    [queryParameters removeObjectForKey:[headerParameter getName]];
}

@end


@implementation SIKQueryParameter

- (id)init {
    
    self = [super init];
    
    if(self) {
        properties = [[NSMutableDictionary alloc] init];
        
        return self;
    }
    
    return self;
}


- (NSString *)getName {
    return [properties objectForKey:SERVICE_DESCRIPTOR_REQUEST_QUERY_PARAMETER_NAME];
}

- (void)setName:(NSString *)name {
    [properties setValue:name forKey:SERVICE_DESCRIPTOR_REQUEST_QUERY_PARAMETER_NAME];
}

- (NSString *)getValue {
    return [properties objectForKey:SERVICE_DESCRIPTOR_REQUEST_QUERY_PARAMETER_VALUE];
}

- (void)setValue:(NSString *)value {
    [properties setValue:value forKey:SERVICE_DESCRIPTOR_REQUEST_QUERY_PARAMETER_VALUE];
}

- (NSEnumerator *)getProperties {
    return [[properties allKeys] objectEnumerator];
}

- (NSString *)getProperty:(NSString *)nme {
    return [properties objectForKey:nme];
}

- (bool)containProperty:(NSString *)nme {
    return [[properties allKeys] containsObject:nme];
}

- (void)addProperty:(NSString *)nme value:(NSString *)val {
    [properties setValue:val forKey:nme];
}

- (void)removeProperty:(NSString *)nme {
    [properties removeObjectForKey:nme];
}

@end
    


@implementation SIKHeaderParameter

- (id)init {
    
    self = [super init];
    
    if(self) {
        properties = [[NSMutableDictionary alloc] init];
        
        return self;
    }
    
    return self;
}


- (NSString *)getName {
    return [properties objectForKey:SERVICE_DESCRIPTOR_REQUEST_HEADER_PARAMETER_NAME];
}

- (void)setName:(NSString *)name {
    [properties setValue:name forKey:SERVICE_DESCRIPTOR_REQUEST_HEADER_PARAMETER_NAME];
}

- (NSString *)getValue {
    return [properties objectForKey:SERVICE_DESCRIPTOR_REQUEST_HEADER_PARAMETER_VALUE];
}

- (void)setValue:(NSString *)value {
    [properties setValue:value forKey:SERVICE_DESCRIPTOR_REQUEST_HEADER_PARAMETER_VALUE];
}

- (NSEnumerator *)getProperties {
    return [[properties allKeys] objectEnumerator];
}

- (NSString *)getProperty:(NSString *)nme {
    return [properties objectForKey:nme];
}

- (bool)containProperty:(NSString *)nme {
    return [[properties allKeys] containsObject:nme];
}

- (void)addProperty:(NSString *)nme value:(NSString *)val {
    [properties setValue:val forKey:nme];
}

- (void)removeProperty:(NSString *)nme {
    [properties removeObjectForKey:nme];
}

@end
