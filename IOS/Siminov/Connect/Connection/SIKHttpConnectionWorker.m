//
//  SIKHttpConnectionWorker.m
//  Connect
//
//  Created by user on 21/04/15.
//  Copyright (c) 2015 Siminov. All rights reserved.
//

#import "SIKHttpConnectionWorker.h"
#import "SICLog.h"
#import "SIKConnectionException.h"
#import "SIKConnectionResponse.h"
#import "SIKConnectionStatusCodes.h"
#import "SIKServiceDescriptor.h"

@implementation SIKHttpConnectionWorker

- (id<SIKIConnectionResponse>)get:(id<SIKIConnectionRequest> const)connectionRequest {
    
    NSString *uri = [connectionRequest getUrl];
    id<SIKIConnectionResponse> connectionResponse = [[SIKConnectionResponse alloc] init];
    [connectionResponse setStatusCode:200];
    
    if([uri containsString:@"get_liquors"]) {
        NSString *liquors = @"<liquors><liquor><name>Gin</name><description>Gin is a spirit made with juniper berries.</description><histroy>By the 11th century, Italian monks were flavoring crudely distilled spirits with juniper berries. During the Black Death, this drink was used, although ineffectively, as a remedy. As the science of distillation advanced from the Middle Ages into the Renaissance period, juniper was one of many botanicals employed by virtue of its perfume, flavour, and purported medicinal properties</histroy><link>http://en.wikipedia.org/wiki/Gin</link><alchol_content>Gin has a minimum alcohol content of 40 percent in the US (80 proof).</alchol_content></liquor><liquor><name>Rum</name><description>Rum is a distilled liquid made from molasses and sugarcane juice.</description><histroy>The first distillation of rum originated in the Caribbean in the 17th centry.</histroy><link>http://en.wikipedia.org/wiki/Rum</link><alchol_content>Rum typically has an alcohol content in the range of 20 to 80 percent by volume (40 to 160 proof).</alchol_content></liquor><liquor><name>Tequila</name><description>Tequila is a spirit made with Blue Agave.</description><histroy>The first distillation of tequila originated in Mexico in the 16th centry.</histroy><link>http://en.wikipedia.org/wiki/Tequila</link><alchol_content>Tequila typically has an alcohol content in the range of 35 to 55 percent by volume (70 to 110 proof).</alchol_content></liquor><liquor><name>Vodka</name><description>Vodka is a colorless liquid that contains a mixture of water and distilled ethanol. Vodka is made from a fermented substance such as potatoes, rye, wheat or sugar beet molasses.</description><histroy>Vodka originated in Russia inthe 14th centry.</histroy><link>http://en.wikipedia.org/wiki/Vodka</link><alchol_content>Vodka typically has an alcohol content in the range of 35 to 50 percent by volume (70 to 100 proof).</alchol_content></liquor><liquor><name>Whiskey</name><description>Whiskey (or whisky) is a distilled liquid made from fermented grain mash.</description><histroy>Distillation spread from Ireland to Scotland (about one hundred years later) and originated from the European continent in the later medieval centuries</histroy><link>http://en.wikipedia.org/wiki/Whisky</link><alchol_content>Whiskey has varying proof levels depending on the specific whiskey. Read more: http://wiki.answers.com/Q/What_percentage_of_alcohol_is_in_whisky#ixzz25n6vmBTZ</alchol_content></liquor><liquor><name>Beer</name><description>Beer is produced by the saccharification of starch and fermentation of the resulting sugar.</description><histroy>Beer is one of the worlds oldest prepared beverages, possibly dating back to the early Neolithic or 9500 BC, when cereal was first farmed, and is recorded in the written history of ancient Egypt and Mesopotamia.</histroy><link>http://en.wikipedia.org/wiki/Beer</link><alchol_content>A beer that is 4.0 percent by volume is about 3.2 percent by weight</alchol_content></liquor><liquor><name>Wine</name><description>Wine is typically made of fermented grape juice.</description><histroy>Wine dates back to 6000 BC and is believed to have originated in the area now considered Iran and Georgia.</histroy><link>http://en.wikipedia.org/wiki/Wine</link><alchol_content>100 grams (g) of wine is equivalent to 100 milliliters (mL) or 3.4 fluid ounces (fl oz.) of wine. 10.6 g of alcohol in 3.4 fl ounces is 13 percent alcohol by volume.</alchol_content></liquor></liquors>";
        [connectionResponse setResponse:[liquors dataUsingEncoding:NSUTF8StringEncoding]];
    } else {
        NSString *liquorBrands = @"<brands><brand><name>The Botanist</name><country>Islay</country><description>The Botanist is a brand of gin produced by distilling nine botanicals into the alcohol through direct boiling followed by passing the vapors through a basket containing an additional 22 herbal ingredients.</description><link>http://www.bruichladdich.com</link></brand><brand><name>Tanqueray</name><country>United Kingtom</country><description>Tanqueray is a form of London dry gin, a spirit produced through double-distillation with botanicals added to the spirit during the second distillation phase. Tanqueray was first distilled in 1830 in England, with production moved to Scotland after World War II.</description><link>http://www.tanqueray.com/</link></brand></brands>";
        [connectionResponse setResponse:[liquorBrands dataUsingEncoding:NSUTF8StringEncoding]];
    }
    
    return connectionResponse;
    
    /*if(connectionRequest == nil) {
        [SICLog error:NSStringFromClass([self class]) methodName:@"get" message:@"Invalid Connection Request."];
        @throw [[SIKConnectionException alloc] initWithClassName:NSStringFromClass([self class]) methodName:@"get" message:@"Invalid Connection Request."];
    }
    
    /*
     * Request Parameters
     */
    
    /*NSEnumerator *queryParameters = [connectionRequest getQueryParameters];
    NSEnumerator *headerParameters = [connectionRequest getHeaderParameters];
    
    /*
     * Make Request
     */
    
    /*NSMutableString *url = [[NSMutableString alloc]initWithFormat:@"%@",[connectionRequest getUrl]];
    
    NSURLComponents *components = [[NSURLComponents alloc] init];
    
    /*
     * Add Query Parameters
     */
    
    /*NSMutableArray *httpParams = NSMutableArray.array;
    
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
    
    /*NSString *header = nil;
    while (header = [headerParameters nextObject]) {
        SIKHeaderParameter *headerParameter = [connectionRequest getHeaderParameter:header];
        [request setValue:[headerParameter getValue] forHTTPHeaderField:header];
    }
    
    // Create url connection and fire request
    NSURLResponse *httpResponse = nil;
    NSError *error = nil;
    
    /* execute */
    /*NSData *inputStream;
    @try {
        inputStream = [NSURLConnection sendSynchronousRequest:request returningResponse:&httpResponse error:&error];
    }
    @catch (NSException *exception) {
        [SICLog error:NSStringFromClass([self class]) methodName:@"get" message:[NSString stringWithFormat:@"Exception caught while executing request, %@",[exception reason]]];
        
        @throw [[SIKConnectionException alloc] initWithClassName:NSStringFromClass([self class]) methodName:@"get" message:[NSString stringWithFormat:@"Exception caught while executing request, %@",[exception reason]]];
    }
    
    /* return response */
    /*int responseCode = (int)[(NSHTTPURLResponse*)httpResponse statusCode];
    if (responseCode != 200) {
        return (id<SIKIConnectionResponse>)[[SIKConnectionResponse alloc]initWithStatusCode:responseCode statusMessage:[[[SIKConnectionStatusCodes alloc] init] getStatusMessage:responseCode]];
    }
    
    return [[SIKConnectionResponse alloc]initWithStatusCode:responseCode inputStream:inputStream];*/
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
