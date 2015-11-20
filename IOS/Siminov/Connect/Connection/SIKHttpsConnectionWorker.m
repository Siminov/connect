//
//  SIKHttpsConnectionWorker.m
//  Connect
//
//  Created by user on 21/04/15.
//  Copyright (c) 2015 Siminov. All rights reserved.
//

#import "SIKHttpsConnectionWorker.h"
#import "SICLog.h"
#import "SIKConnectionException.h"
#import "SIKConnectionResponse.h"
#import "SIKConnectionStatusCodes.h"
#import "SIKServiceDescriptor.h"

@implementation SIKHttpsConnectionWorker

- (id<SIKIConnectionResponse>)get:(id<SIKIConnectionRequest> const)connectionRequest {
    
    if(connectionRequest == nil) {
        [SICLog error:NSStringFromClass([self class]) methodName:@"get" message:@"Invalid Connection Request."];
        @throw [[SIKConnectionException alloc] initWithClassName:NSStringFromClass([self class]) methodName:@"get" message:@"Invalid Connection Request."];
    }
    
    /*
     * Request Parameters
     */
    
    NSEnumerator *queryParameters = [connectionRequest getQueryParameters];
    NSEnumerator *headerParameters = [connectionRequest getHeaderParameters];
    
    /*
     * Make Request
     */
    
    NSMutableString *url = [[NSMutableString alloc]initWithFormat:@"%@",[connectionRequest getUrl]];
    
    NSURLComponents *components = [[NSURLComponents alloc] init];
    
    /*
     * Add Query Parameters
     */
    
    NSMutableArray *httpParams = NSMutableArray.array;
    
    for (NSString *parameter in queryParameters) {
        
        SIKQueryParameter *queryParameter = [connectionRequest getQueryParameter:parameter];
        NSURLQueryItem *screenNameItem = [NSURLQueryItem queryItemWithName:parameter value:[queryParameter getValue]];
        [httpParams addObject:screenNameItem];
    }
    
    components.queryItems = (NSArray*)httpParams;
    
    if(![(NSString *)url hasSuffix:CONNECTION_QUERY_PARAMETER_KEYWORD]) {
        [url appendFormat:CONNECTION_QUERY_PARAMETER_KEYWORD];
    }
    
    [url appendString:[components string]];
    
    NSURL *URL = [NSURL URLWithString:(NSString*)url];
    NSMutableURLRequest *request = [NSMutableURLRequest requestWithURL:URL];
    // Set request type
    request.HTTPMethod = @"GET";
    
    /*
     * Add Header Parameters
     */
    
    NSString *header = nil;
    while (header = [headerParameters nextObject]) {
        SIKHeaderParameter *headerParameter = [connectionRequest getHeaderParameter:header];
        [request setValue:[headerParameter getValue] forHTTPHeaderField:header];
    }
    
    // Create url connection and fire request
    NSURLResponse *httpResponse = nil;
    NSError *error = nil;
    
    /* execute */
    NSData *inputStream;
    @try {
        inputStream = [NSURLConnection sendSynchronousRequest:request returningResponse:&httpResponse error:&error];
    }
    @catch (NSException *exception) {
        [SICLog error:NSStringFromClass([self class]) methodName:@"get" message:[NSString stringWithFormat:@"Exception caught while executing request, %@",[exception reason]]];
        
        @throw [[SIKConnectionException alloc] initWithClassName:NSStringFromClass([self class]) methodName:@"get" message:[NSString stringWithFormat:@"Exception caught while executing request, %@",[exception reason]]];
    }
    
    /* return response */
    int responseCode = (int)[(NSHTTPURLResponse*)httpResponse statusCode];
    if (responseCode != 200) {
        return (id<SIKIConnectionResponse>)[[SIKConnectionResponse alloc]initWithStatusCode:responseCode statusMessage:[[[SIKConnectionStatusCodes alloc] init] getStatusMessage:responseCode]];
    }
    
    return [[SIKConnectionResponse alloc]initWithStatusCode:responseCode inputStream:inputStream];
}

- (id<SIKIConnectionResponse>)head:(id<SIKIConnectionRequest> const) connectionRequest {
    
    if(connectionRequest == nil) {
        [SICLog error:NSStringFromClass([self class]) methodName:@"head" message:@"Invalid Connection Request."];
        @throw [[SIKConnectionException alloc] initWithClassName:NSStringFromClass([self class]) methodName:@"head" message:@"Invalid Connection Request."];
    }
    
    /*
     * Request Parameters
     */
    
    NSEnumerator *queryParameters = [connectionRequest getQueryParameters];
    NSEnumerator *headerParameters = [connectionRequest getHeaderParameters];
    
    /*
     * Make Request
     */
    NSMutableString *url = [[NSMutableString alloc]initWithFormat:@"%@",[connectionRequest getUrl]];
    
    NSURLComponents *components = [[NSURLComponents alloc] init];
    
    /*
     * Add Query Parameters
     */
    NSMutableArray *httpParams = NSMutableArray.array;
    
    for (NSString *parameter in queryParameters) {
        
        SIKQueryParameter *queryParameter = [connectionRequest getQueryParameter:parameter];
        NSURLQueryItem *screenNameItem = [NSURLQueryItem queryItemWithName:parameter value:[queryParameter getValue]];
        [httpParams addObject:screenNameItem];
    }
    
    components.queryItems = (NSArray*)httpParams;
    
    if(![(NSString *)url hasSuffix:CONNECTION_QUERY_PARAMETER_KEYWORD]) {
        [url appendFormat:CONNECTION_QUERY_PARAMETER_KEYWORD];
    }
    
    [url appendString:[components string]];
    
    NSURL *URL = [NSURL URLWithString:(NSString*)url];
    NSMutableURLRequest *request = [NSMutableURLRequest requestWithURL:URL];
    // Set request type
    request.HTTPMethod = @"HEAD";
    
    /*
     * Add Header Parameters
     */
    NSString *header = nil;
    while (header = [headerParameters nextObject]) {
        SIKHeaderParameter *headerParameter = [connectionRequest getHeaderParameter:header];
        [request setValue:[headerParameter getValue] forHTTPHeaderField:header];
    }
    
    // Create url connection and fire request
    NSURLResponse *httpResponse = nil;
    NSError *error = nil;
    
    /* execute */
    NSData *inputStream;
    @try {
        inputStream = [NSURLConnection sendSynchronousRequest:request returningResponse:&httpResponse error:&error];
    }
    @catch (NSException *exception) {
        [SICLog error:NSStringFromClass([self class]) methodName:@"head" message:[NSString stringWithFormat:@"Exception caught while executing request, %@",[exception reason]]];
        
        @throw [[SIKConnectionException alloc] initWithClassName:NSStringFromClass([self class]) methodName:@"head" message:[NSString stringWithFormat:@"Exception caught while executing request, %@",[exception reason]]];
    }
    
    /* return response */
    int responseCode = (int)[(NSHTTPURLResponse*)httpResponse statusCode];
    if (responseCode != 200) {
        return (id<SIKIConnectionResponse>)[[SIKConnectionResponse alloc]initWithStatusCode:responseCode statusMessage:[[[SIKConnectionStatusCodes alloc] init] getStatusMessage:responseCode]];
    }
    
    return [[SIKConnectionResponse alloc]initWithStatusCode:responseCode inputStream:inputStream];
}

- (id<SIKIConnectionResponse>)post:(id<SIKIConnectionRequest> const)connectionRequest {
    
    if(connectionRequest == nil) {
        [SICLog error:NSStringFromClass([self class]) methodName:@"post" message:@"Invalid Connection Request."];
        @throw [[SIKConnectionException alloc] initWithClassName:NSStringFromClass([self class]) methodName:@"post" message:@"Invalid Connection Request."];
    }
    
    /*
     * Request Parameters
     */
    NSEnumerator *queryParameters = [connectionRequest getQueryParameters];
    NSEnumerator *headerParameters = [connectionRequest getHeaderParameters];
    
    /*
     * Make Request
     */
    NSMutableString *url = [[NSMutableString alloc]initWithFormat:@"%@",[connectionRequest getUrl]];
    
    NSURLComponents *components = [[NSURLComponents alloc] init];
    
    /*
     * Add Query Parameters
     */
    NSMutableArray *httpParams = NSMutableArray.array;
    
    for (NSString *parameter in queryParameters) {
        
        SIKQueryParameter *queryParameter = [connectionRequest getQueryParameter:parameter];
        NSURLQueryItem *screenNameItem = [NSURLQueryItem queryItemWithName:parameter value:[queryParameter getValue]];
        [httpParams addObject:screenNameItem];
    }
    
    components.queryItems = (NSArray*)httpParams;
    
    if(![(NSString *)url hasSuffix:CONNECTION_QUERY_PARAMETER_KEYWORD]) {
        [url appendFormat:CONNECTION_QUERY_PARAMETER_KEYWORD];
    }
    
    [url appendString:[components string]];
    
    NSURL *URL = [NSURL URLWithString:(NSString*)url];
    NSMutableURLRequest *request = [NSMutableURLRequest requestWithURL:URL];
    // Set request type
    request.HTTPMethod = @"POST";
    
    /*
     * Add Header Parameters
     */
    NSString *header = nil;
    while (header = [headerParameters nextObject]) {
        SIKHeaderParameter *headerParameter = [connectionRequest getHeaderParameter:header];
        [request setValue:[headerParameter getValue] forHTTPHeaderField:header];
    }
    
    [request setHTTPBody:[connectionRequest getDataStream]];
    
    // Create url connection and fire request
    NSURLResponse *httpResponse = nil;
    NSError *error = nil;
    
    /* execute */
    NSData *inputStream;
    @try {
        inputStream = [NSURLConnection sendSynchronousRequest:request returningResponse:&httpResponse error:&error];
    }
    @catch (NSException *exception) {
        [SICLog error:NSStringFromClass([self class]) methodName:@"post" message:[NSString stringWithFormat:@"Exception caught while executing request, %@",[exception reason]]];
        
        @throw [[SIKConnectionException alloc] initWithClassName:NSStringFromClass([self class]) methodName:@"post" message:[NSString stringWithFormat:@"Exception caught while executing request, %@",[exception reason]]];
    }
    
    /* return response */
    int responseCode = (int)[(NSHTTPURLResponse*)httpResponse statusCode];
    if (responseCode != 200) {
        return (id<SIKIConnectionResponse>)[[SIKConnectionResponse alloc]initWithStatusCode:responseCode statusMessage:[[[SIKConnectionStatusCodes alloc] init] getStatusMessage:responseCode]];
    }
    
    return [[SIKConnectionResponse alloc]initWithStatusCode:responseCode inputStream:inputStream];
}

- (id<SIKIConnectionResponse>)put:(id<SIKIConnectionRequest> const)connectionRequest {
    
    if(connectionRequest == nil) {
        [SICLog error:NSStringFromClass([self class]) methodName:@"put" message:@"Invalid Connection Request."];
        @throw [[SIKConnectionException alloc] initWithClassName:NSStringFromClass([self class]) methodName:@"put" message:@"Invalid Connection Request."];
    }
    
    /*
     * Request Parameters
     */
    NSEnumerator *queryParameters = [connectionRequest getQueryParameters];
    NSEnumerator *headerParameters = [connectionRequest getHeaderParameters];
    
    /*
     * Make Request
     */
    
    NSMutableString *url = [[NSMutableString alloc]initWithFormat:@"%@",[connectionRequest getUrl]];
    
    NSURLComponents *components = [[NSURLComponents alloc] init];
    
    /*
     * Add Query Parameters
     */
    NSMutableArray *httpParams = NSMutableArray.array;
    
    for (NSString *parameter in queryParameters) {
        
        SIKQueryParameter *queryParameter = [connectionRequest getQueryParameter:parameter];
        NSURLQueryItem *screenNameItem = [NSURLQueryItem queryItemWithName:parameter value:[queryParameter getValue]];
        [httpParams addObject:screenNameItem];
    }
    
    components.queryItems = (NSArray*)httpParams;
    
    if(![(NSString *)url hasSuffix:CONNECTION_QUERY_PARAMETER_KEYWORD]) {
        [url appendFormat:CONNECTION_QUERY_PARAMETER_KEYWORD];
    }
    
    [url appendString:[components string]];
    
    NSURL *URL = [NSURL URLWithString:(NSString*)url];
    NSMutableURLRequest *request = [NSMutableURLRequest requestWithURL:URL];
    // Set request type
    request.HTTPMethod = @"PUT";
    
    /*
     * Add Header Parameters
     */
    NSString *header = nil;
    while (header = [headerParameters nextObject]) {
        SIKHeaderParameter *headerParameter = [connectionRequest getHeaderParameter:header];
        [request setValue:[headerParameter getValue] forHTTPHeaderField:header];
    }
    
    [request setHTTPBody:[connectionRequest getDataStream]];
    
    // Create url connection and fire request
    NSURLResponse *httpResponse = nil;
    NSError *error = nil;
    
    /* execute */
    NSData *inputStream;
    @try {
        inputStream = [NSURLConnection sendSynchronousRequest:request returningResponse:&httpResponse error:&error];
    }
    @catch (NSException *exception) {
        [SICLog error:NSStringFromClass([self class]) methodName:@"put" message:[NSString stringWithFormat:@"Exception caught while executing request, %@",[exception reason]]];
        
        @throw [[SIKConnectionException alloc] initWithClassName:NSStringFromClass([self class]) methodName:@"put" message:[NSString stringWithFormat:@"Exception caught while executing request, %@",[exception reason]]];
    }
    
    /* return response */
    int responseCode = (int)[(NSHTTPURLResponse*)httpResponse statusCode];
    if (responseCode != 200) {
        return (id<SIKIConnectionResponse>)[[SIKConnectionResponse alloc]initWithStatusCode:responseCode statusMessage:[[[SIKConnectionStatusCodes alloc] init] getStatusMessage:responseCode]];
    }
    
    return [[SIKConnectionResponse alloc]initWithStatusCode:responseCode inputStream:inputStream];
}

- (id<SIKIConnectionResponse>)delete:(id<SIKIConnectionRequest> const)connectionRequest {
    
    if(connectionRequest == nil) {
        [SICLog error:NSStringFromClass([self class]) methodName:@"delete" message:@"Invalid Connection Request."];
        @throw [[SIKConnectionException alloc] initWithClassName:NSStringFromClass([self class]) methodName:@"delete" message:@"Invalid Connection Request."];
    }
    
    /*
     * Request Parameters
     */
    
    NSEnumerator *queryParameters = [connectionRequest getQueryParameters];
    NSEnumerator *headerParameters = [connectionRequest getHeaderParameters];
    
    /*
     * Make Request
     */
    
    NSMutableString *url = [[NSMutableString alloc]initWithFormat:@"%@",[connectionRequest getUrl]];
    
    NSURLComponents *components = [[NSURLComponents alloc] init];
    
    /*
     * Add Query Parameters
     */
    
    NSMutableArray *httpParams = NSMutableArray.array;
    
    for (NSString *parameter in queryParameters) {
        
        SIKQueryParameter *queryParameter = [connectionRequest getQueryParameter:parameter];
        NSURLQueryItem *screenNameItem = [NSURLQueryItem queryItemWithName:parameter value:[queryParameter getValue]];
        [httpParams addObject:screenNameItem];
    }
    
    components.queryItems = (NSArray*)httpParams;
    
    if(![(NSString *)url hasSuffix:CONNECTION_QUERY_PARAMETER_KEYWORD]) {
        [url appendFormat:CONNECTION_QUERY_PARAMETER_KEYWORD];
    }
    
    [url appendString:[components string]];
    
    NSURL *URL = [NSURL URLWithString:(NSString*)url];
    NSMutableURLRequest *request = [NSMutableURLRequest requestWithURL:URL];
    // Set request type
    request.HTTPMethod = @"DELETE";
    
    /*
     * Add Header Parameters
     */
    
    NSString *header = nil;
    while (header = [headerParameters nextObject]) {
        SIKHeaderParameter *headerParameter = [connectionRequest getHeaderParameter:header];
        [request setValue:[headerParameter getValue] forHTTPHeaderField:header];
    }
    
    // Create url connection and fire request
    NSURLResponse *httpResponse = nil;
    NSError *error = nil;
    
    /* execute */
    NSData *inputStream;
    @try {
        inputStream = [NSURLConnection sendSynchronousRequest:request returningResponse:&httpResponse error:&error];
    }
    @catch (NSException *exception) {
        [SICLog error:NSStringFromClass([self class]) methodName:@"delete" message:[NSString stringWithFormat:@"Exception caught while executing request, %@",[exception reason]]];
        
        @throw [[SIKConnectionException alloc] initWithClassName:NSStringFromClass([self class]) methodName:@"delete" message:[NSString stringWithFormat:@"Exception caught while executing request, %@",[exception reason]]];
    }
    
    /* return response */
    int responseCode = (int)[(NSHTTPURLResponse*)httpResponse statusCode];
    if (responseCode != 200) {
        return (id<SIKIConnectionResponse>)[[SIKConnectionResponse alloc]initWithStatusCode:responseCode statusMessage:[[[SIKConnectionStatusCodes alloc] init] getStatusMessage:responseCode]];
    }
    
    return [[SIKConnectionResponse alloc]initWithStatusCode:responseCode inputStream:inputStream];
}

- (id<SIKIConnectionResponse>)trace:(id<SIKIConnectionRequest> const)connectionRequest {
    
    if(connectionRequest == nil) {
        [SICLog error:NSStringFromClass([self class]) methodName:@"trace" message:@"Invalid Connection Request."];
        @throw [[SIKConnectionException alloc] initWithClassName:NSStringFromClass([self class]) methodName:@"trace" message:@"Invalid Connection Request."];
    }
    
    /*
     * Request Parameters
     */
    
    NSEnumerator *queryParameters = [connectionRequest getQueryParameters];
    NSEnumerator *headerParameters = [connectionRequest getHeaderParameters];
    
    /*
     * Make Request
     */
    
    NSMutableString *url = [[NSMutableString alloc]initWithFormat:@"%@",[connectionRequest getUrl]];
    
    NSURLComponents *components = [[NSURLComponents alloc] init];
    
    /*
     * Add Query Parameters
     */
    
    NSMutableArray *httpParams = NSMutableArray.array;
    
    for (NSString *parameter in queryParameters) {
        
        SIKQueryParameter *queryParameter = [connectionRequest getQueryParameter:parameter];
        NSURLQueryItem *screenNameItem = [NSURLQueryItem queryItemWithName:parameter value:[queryParameter getValue]];
        [httpParams addObject:screenNameItem];
    }
    
    components.queryItems = (NSArray*)httpParams;
    
    if(![(NSString *)url hasSuffix:CONNECTION_QUERY_PARAMETER_KEYWORD]) {
        [url appendFormat:CONNECTION_QUERY_PARAMETER_KEYWORD];
    }
    
    [url appendString:[components string]];
    
    NSURL *URL = [NSURL URLWithString:(NSString*)url];
    NSMutableURLRequest *request = [NSMutableURLRequest requestWithURL:URL];
    // Set request type
    request.HTTPMethod = @"TRACE";
    
    /*
     * Add Header Parameters
     */
    
    NSString *header = nil;
    while (header = [headerParameters nextObject]) {
        SIKHeaderParameter *headerParameter = [connectionRequest getHeaderParameter:header];
        [request setValue:[headerParameter getValue] forHTTPHeaderField:header];
    }
    
    // Create url connection and fire request
    NSURLResponse *httpResponse = nil;
    NSError *error = nil;
    
    /* execute */
    NSData *inputStream;
    @try {
        inputStream = [NSURLConnection sendSynchronousRequest:request returningResponse:&httpResponse error:&error];
    }
    @catch (NSException *exception) {
        [SICLog error:NSStringFromClass([self class]) methodName:@"trace" message:[NSString stringWithFormat:@"Exception caught while executing request, %@",[exception reason]]];
        
        @throw [[SIKConnectionException alloc] initWithClassName:NSStringFromClass([self class]) methodName:@"trace" message:[NSString stringWithFormat:@"Exception caught while executing request, %@",[exception reason]]];
    }
    
    /* return response */
    int responseCode = (int)[(NSHTTPURLResponse*)httpResponse statusCode];
    if (responseCode != 200) {
        return (id<SIKIConnectionResponse>)[[SIKConnectionResponse alloc]initWithStatusCode:responseCode statusMessage:[[[SIKConnectionStatusCodes alloc] init] getStatusMessage:responseCode]];
    }
    
    return [[SIKConnectionResponse alloc]initWithStatusCode:responseCode inputStream:inputStream];
}

- (id<SIKIConnectionResponse>)options:(id<SIKIConnectionRequest> const)connectionRequest {
    
    if(connectionRequest == nil) {
        [SICLog error:NSStringFromClass([self class]) methodName:@"options" message:@"Invalid Connection Request."];
        @throw [[SIKConnectionException alloc] initWithClassName:NSStringFromClass([self class]) methodName:@"options" message:@"Invalid Connection Request."];
    }
    
    /*
     * Request Parameters
     */
    
    NSEnumerator *queryParameters = [connectionRequest getQueryParameters];
    NSEnumerator *headerParameters = [connectionRequest getHeaderParameters];
    
    /*
     * Make Request
     */
    
    NSMutableString *url = [[NSMutableString alloc]initWithFormat:@"%@",[connectionRequest getUrl]];
    
    NSURLComponents *components = [[NSURLComponents alloc] init];
    
    /*
     * Add Query Parameters
     */
    
    NSMutableArray *httpParams = NSMutableArray.array;
    
    for (NSString *parameter in queryParameters) {
        
        SIKQueryParameter *queryParameter = [connectionRequest getQueryParameter:parameter];
        NSURLQueryItem *screenNameItem = [NSURLQueryItem queryItemWithName:parameter value:[queryParameter getValue]];
        [httpParams addObject:screenNameItem];
    }
    
    components.queryItems = (NSArray*)httpParams;
    
    if(![(NSString *)url hasSuffix:CONNECTION_QUERY_PARAMETER_KEYWORD]) {
        [url appendFormat:CONNECTION_QUERY_PARAMETER_KEYWORD];
    }
    
    [url appendString:[components string]];
    
    NSURL *URL = [NSURL URLWithString:(NSString*)url];
    NSMutableURLRequest *request = [NSMutableURLRequest requestWithURL:URL];
    // Set request type
    request.HTTPMethod = @"DELETE";
    
    /*
     * Add Header Parameters
     */
    
    NSString *header = nil;
    while (header = [headerParameters nextObject]) {
        SIKHeaderParameter *headerParameter = [connectionRequest getHeaderParameter:header];
        [request setValue:[headerParameter getValue] forHTTPHeaderField:header];
    }
    
    // Create url connection and fire request
    NSURLResponse *httpResponse = nil;
    NSError *error = nil;
    
    /* execute */
    NSData *inputStream;
    @try {
        inputStream = [NSURLConnection sendSynchronousRequest:request returningResponse:&httpResponse error:&error];
    }
    @catch (NSException *exception) {
        [SICLog error:NSStringFromClass([self class]) methodName:@"options" message:[NSString stringWithFormat:@"Exception caught while executing request, %@",[exception reason]]];
        
        @throw [[SIKConnectionException alloc] initWithClassName:NSStringFromClass([self class]) methodName:@"options" message:[NSString stringWithFormat:@"Exception caught while executing request, %@",[exception reason]]];
    }
    
    /* return response */
    int responseCode = (int)[(NSHTTPURLResponse*)httpResponse statusCode];
    if (responseCode != 200) {
        return (id<SIKIConnectionResponse>)[[SIKConnectionResponse alloc]initWithStatusCode:responseCode statusMessage:[[[SIKConnectionStatusCodes alloc] init] getStatusMessage:responseCode]];
    }
    
    return [[SIKConnectionResponse alloc]initWithStatusCode:responseCode inputStream:inputStream];
}

- (id<SIKIConnectionResponse>)connect:(id<SIKIConnectionRequest> const) connectionRequest {
    
    if(connectionRequest == nil) {
        [SICLog error:NSStringFromClass([self class]) methodName:@"connect"  message:@"Invalid Connection Request."];
        @throw [[SIKConnectionException alloc] initWithClassName:NSStringFromClass([self class]) methodName:@"connect" message:@"Invalid Connection Request."];
    }
    return nil;
}

- (id<SIKIConnectionResponse>)patch:(id<SIKIConnectionRequest> const) connectionRequest {
    
    if(connectionRequest == nil) {
        [SICLog error:NSStringFromClass([self class]) methodName:@"patch" message:@"Invalid Connection Request."];
        @throw [[SIKConnectionException alloc] initWithClassName:NSStringFromClass([self class]) methodName:@"patch" message:@"Invalid Connection Request."];
    }
    return nil;
}

@end
