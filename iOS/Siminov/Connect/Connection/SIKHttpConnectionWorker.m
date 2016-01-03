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


#import "SIKHttpConnectionWorker.h"
#import "SICLog.h"
#import "SIKConnectionException.h"
#import "SIKConnectionResponse.h"
#import "SIKConnectionStatusCodes.h"
#import "SIKServiceDescriptor.h"

@implementation SIKHttpConnectionWorker

- (id<SIKIConnectionResponse>)get:(id<SIKIConnectionRequest> const)connectionRequest {
    
    NSString *uri = [connectionRequest getUrl];
    /*id<SIKIConnectionResponse> connectionResponse = [[SIKConnectionResponse alloc] init];
    [connectionResponse setStatusCode:200];
    
    if([uri containsString:@"get_books"]) {
        NSString *books = @"<books><book><name>C</name><description>C Description</description><author>C Author</author><link>C Link</link></book><book><name>C Plus</name><description>C Plus Description</description><author>C Plus Author</author><link>C Plus Link</link></book><book><name>C Sharp</name><description>C Sharp Description</description><author>C Sharp Author</author><link>C Sharp Link</link></book><book><name>Java</name><description>Java Description</description><author>Java Author</author><link>Java Link</link></book><book><name>JavaScript</name><description>JavaScript Description</description><author>JavaScript Author</author><link>JavaScript Link</link></book><book><name>Swift</name><description>Swift Description</description><author>Swift Author</author><link>Swift Link</link></book><book><name>Objective C</name><description>Objective C Description</description><author>Objective C Author</author><link>Objective C Link</link></book></books>";
        [connectionResponse setResponse:[books dataUsingEncoding:NSUTF8StringEncoding]];
    } else {
        NSString *lessions = @"<lessions><lession><name>C First Lession</name><description>C First Lession Description</description><link>C First Lession Link</link></lession><lession><name>C Second Lession</name><description>C Second Lession Description</description><link>C Second Lession Link</link></lession></lessions>";
        [connectionResponse setResponse:[lessions dataUsingEncoding:NSUTF8StringEncoding]];
    }
    
    return connectionResponse;*/
    
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
        //[url appendFormat:CONNECTION_QUERY_PARAMETER_KEYWORD];
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
