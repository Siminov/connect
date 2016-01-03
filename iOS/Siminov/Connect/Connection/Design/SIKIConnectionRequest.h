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
