//
//  SIKConnectionRequest.m
//  Connect
//
//  Created by user on 21/04/15.
//  Copyright (c) 2015 Siminov. All rights reserved.
//

#import "SIKConnectionRequest.h"
#import "SIKServiceDescriptor.h"

@implementation SIKConnectionRequest

- (id)init {
    
    self = [super init];
    if(self) {
        
        url = nil;
        
        protocol = nil;
        type = nil;
        
        queryParameters = [[NSMutableDictionary alloc] init];
        headerParameters = [[NSMutableDictionary alloc] init];
        
        dataStream = nil;
        
        return self;
    }
    
    return self;
}

- (NSString *)getUrl {
    return url;
}

- (void)setUrl:(NSString *)ul {
    url = ul;
}

- (NSString *)getProtocol {
    return protocol;
}

- (void)setProtocol:(NSString *)ptl {
    protocol = ptl;
}

- (NSString *)getType {
    return type;
}

- (void)setType:(NSString *)typ {
    type = typ;
}

- (NSEnumerator *)getQueryParameters {
    return [queryParameters keyEnumerator];
}

- (SIKQueryParameter *)getQueryParameter:(NSString *)key {
    return [queryParameters valueForKey:key];
}

- (void)addQueryParameter:(SIKQueryParameter *)queryParameter {
    [queryParameters setValue:queryParameter forKey:[queryParameter getName]];
}

- (NSEnumerator *)getHeaderParameters {
    return [headerParameters keyEnumerator];
}

- (SIKHeaderParameter *)getHeaderParameter:(NSString *)key {
    return [headerParameters valueForKey:key];
}

- (void)addHeaderParameter:(SIKHeaderParameter *)headerParameter {
    [headerParameters setValue:headerParameter forKey:[headerParameter getName]];
}

- (NSData *)getDataStream {
    return dataStream;
}

- (void)setDataStream:(NSData *)data {
    dataStream = data;
}

@end
