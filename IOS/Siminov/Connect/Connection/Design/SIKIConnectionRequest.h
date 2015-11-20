//
//  SIKIConnectionRequest.h
//  Connect
//
//  Created by user on 21/04/15.
//  Copyright (c) 2015 Siminov. All rights reserved.
//

#import <Foundation/Foundation.h>

@class SIKQueryParameter;
@class SIKHeaderParameter;

/**
 * It contain the blue print for the connection request object
 */
@protocol SIKIConnectionRequest <NSObject>
/**
 * Get request URL
 * @return URL
 */
- (NSString *)getUrl;

/**
 * Set request URL
 * @param url URL
 */
- (void)setUrl:(NSString *)url;

/**
 * Get request protocol
 * @return Protocol (HTTP/HTTPS)
 */
- (NSString *)getProtocol;

/**
 * Set request protocol
 * @param protocol Protocol
 */
- (void)setProtocol:(NSString *)protocol;


/**
 * Get request type
 * @return Type
 */
- (NSString *)getType;

/**
 * Set request type
 * @param type Request Type
 */
- (void)setType:(NSString *)type;

/**
 * Get all query parameters
 * @return Query Parameters
 */
- (NSEnumerator *)getQueryParameters;

/**
 * Get query parameter
 * @param key Name of query parameter
 * @return Query Parameter
 */
- (SIKQueryParameter *)getQueryParameter:(NSString *)key;

/**
 * Add query parameter
 * @param queryParameter Query Parameter
 */
- (void)addQueryParameter:(SIKQueryParameter *)queryParameter;

/**
 * Get all header parameters
 * @return Header Parameter
 */
- (NSEnumerator *)getHeaderParameters;

/**
 * Get header parameter
 * @param key Name of header parameter
 * @return Header Parameter
 */
- (SIKHeaderParameter *)getHeaderParameter:(NSString *)key;

/**
 * Add header parameter
 * @param headerParameter Header Parameter
 */
- (void)addHeaderParameter:(SIKHeaderParameter *)headerParameter;

/**
 * Get data stream
 * @return Data Stream
 */
- (NSData *)getDataStream;

/**
 * Set data stream
 * @param dataStream Data Stream
 */
- (void)setDataStream:(NSData *)dataStream;

@end
