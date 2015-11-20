//
//  SIKConnectionHelper.m
//  Connect
//
//  Created by user on 21/04/15.
//  Copyright (c) 2015 Siminov. All rights reserved.
//

#import "SIKConnectionHelper.h"
#import "SIKConstants.h"

@implementation SIKConnectionHelper

/**
 * It build connection request instance based on the service descriptor object
 * @param service Instance of IService
 * @return IConnectionRequest instance
 */
+ (id<SIKIConnectionRequest>)prepareConnectionRequest:(id<SIKIService> const)service {
    
    /*
     * Resolve All Referring Resources
     */
    SIKServiceDescriptor *serviceDescriptor = [service getServiceDescriptor];
    SIKRequest *request = [serviceDescriptor getRequest:[service getRequest]];
    
    NSString *url = [self formUrl:service];
    
    NSEnumerator *queryParameters = [self formQueryParameters:service];
    NSEnumerator *headerParameters = [self formHeaderParameters:service];
    
    NSData *dataStream = [self formDataStream:service];
    
    id<SIKIConnectionRequest> connectionRequest = [[SIKConnectionRequest alloc] init];
    [connectionRequest setUrl:url];
    [connectionRequest setProtocol:[serviceDescriptor getProtocol]];
    [connectionRequest setType:[request getType]];
    
    SIKQueryParameter *queryParameter;
    while(queryParameter = [queryParameters nextObject]) {
        [connectionRequest addQueryParameter:queryParameter];
    }
    
    SIKHeaderParameter *headerParameter;
    while(headerParameter = [headerParameters nextObject]) {
        [connectionRequest addHeaderParameter:headerParameter];
    }
    
    [connectionRequest setDataStream:dataStream];
    
    return connectionRequest;
}


+ (NSString *)formUrl:(id<SIKIService> const)service {
    
    SIKServiceDescriptor *serviceDescriptor = [service getServiceDescriptor];
    
    NSString *requestName = [service getRequest];
    SIKRequest *request = [serviceDescriptor getRequest:requestName];
    
    NSString *protocol = [serviceDescriptor getProtocol];
    
    NSString *instance = [serviceDescriptor getInstance];
    NSString *port = [serviceDescriptor getPort];
    
    NSString *context = [serviceDescriptor getContext];
    NSString *apiPath = [request getApi];
    
    NSMutableString *url = [[NSMutableString alloc] init];
    
    if(protocol != nil) {
        
        if([protocol caseInsensitiveCompare:SERVICE_DESCRIPTOR_HTTP_PROTOCOL] == NSOrderedSame) {
            [url appendFormat:CONNECTION_HTTP];
        } else if([protocol caseInsensitiveCompare:SERVICE_DESCRIPTOR_HTTPS_PROTOCOL] == NSOrderedSame) {
            [url appendFormat:CONNECTION_HTTPS];
        }
        
        [url appendFormat:@"://"];
    }
				
    [url appendString:instance];
    
    if(port != nil && [port length] > 0) {
        [url appendString:[NSString stringWithFormat:@":%@",port]];
    }
    
    if(context != nil && [context length] > 0) {
        [url appendString:[NSString stringWithFormat:@"/%@",context]];
    }
    
    if(apiPath != nil && [apiPath length] > 0) {
        [url appendString:[NSString stringWithFormat:@"/%@",apiPath]];
    }
    
    return (NSString *)url;
}

+ (NSEnumerator *)formQueryParameters:(id<SIKIService> const)service {
    
    SIKServiceDescriptor *serviceDescriptor = [service getServiceDescriptor];
    
    NSString *requestName = [service getRequest];
    SIKRequest *request = [serviceDescriptor getRequest:requestName];
    
    return [request getQueryParameters];
}

+ (NSEnumerator *)formHeaderParameters:(id<SIKIService> const)service {
    
    SIKServiceDescriptor *serviceDescriptor = [service getServiceDescriptor];
    
    NSString *requestName = [service getRequest];
    SIKRequest *request = [serviceDescriptor getRequest:requestName];
    
    return [request getHeaderParameters];
}

+ (NSData *)formDataStream:(id<SIKIService> const)service {
    
    SIKServiceDescriptor *serviceDescriptor = [service getServiceDescriptor];
    
    NSString *requestName = [service getRequest];
    SIKRequest *request = [serviceDescriptor getRequest:requestName];
    
    if([request getDataStream] == nil || [request getDataStream].length<=0) {
        return [[NSData alloc]init];
    }
    
    return [[request getDataStream] dataUsingEncoding:NSUTF8StringEncoding];
}

@end
