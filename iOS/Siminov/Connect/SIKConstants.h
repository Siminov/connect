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



/**
 * Contains Constant values used in the framework
 * All constants referred in the framework are defined in this class
 */


#import <Foundation/Foundation.h>

/**
 * Connect Database Descriptor Path
 */
static NSString * const DATABASE_DESSCRIPTOR_PATH = @"Siminov.Connect.Resource.DatabaseDescriptor.xml";


//Application Descriptor Constants

/**
 * Application Descriptor Service Descriptor
 */
static NSString * const APPLICATION_DESCRIPTOR_SERVICE_DESCRIPTOR = @"service-descriptor";


//Sync Descriptor Constants

/**
 * Sync Descriptor
 */
static NSString * const SYNC_DESCRIPTOR = @"sync-descriptor";

/**
 * Sync Descriptor Service Descriptor
 */
static NSString * const SYNC_DESCRIPTOR_SERVICE_DESCRIPTOR = @"service-descriptor";

/**
 * Sync Descriptor Name
 */
static NSString * const SYNC_DESCRIPTOR_NAME = @"name";

/**
 * Sync Descriptor Refresh Interval
 */
static NSString * const SYNC_DESCRIPTOR_REFRESH_INTERVAL = @"interval";

/**
 * Sync Descriptor Property
 */
static NSString * const SYNC_DESCRIPTOR_PROPERTY = @"property";

/**
 * Sync Descriptor Service Separator
 */
static NSString * const SYNC_DESCRIPTOR_SERVICE_SEPARATOR = @".";

//Notification Descriptor Constants

/**
 * Notification Descriptor
 */
static NSString * const NOTIFICATION_DESCRIPTOR = @"notification-descriptor";


//Service Descriptor Constants

/**
 * Service Descriptor Request
 */
static NSString * const SERVICE_DESCRIPTOR_REQUESTS = @"requests";

/**
 * Service Descriptor Request
 */
static NSString * const SERVICE_DESCRIPTOR_REQUEST = @"request";

/**
 * Service Descriptor Property
 */
static NSString * const SERVICE_DESCRIPTOR_PROPERTY = @"property";

/**
 * Service Descriptor Property Name
 */
static NSString * const SERVICE_DESCRIPTOR_PROPERTY_NAME = @"name";

/**
 * Service Descriptor Request Query Parameter
 */
static NSString * const SERVICE_DESCRIPTOR_REQUEST_QUERY_PARAMETER = @"query-parameter";

/**
 * Service Descriptor Request Query Parameter Name
 */
static NSString * const SERVICE_DESCRIPTOR_REQUEST_QUERY_PARAMETER_NAME = @"name";

/**
 * Service Descriptor Request Query Parameter Value
 */
static NSString * const SERVICE_DESCRIPTOR_REQUEST_QUERY_PARAMETER_VALUE = @"value";


/**
 * Service Descriptor Request Header Parameter
 */
static NSString * const SERVICE_DESCRIPTOR_REQUEST_HEADER_PARAMETER = @"header-parameter";

/**
 * Service Descriptor Request Header Parameter Name Attribute
 */
static NSString * const SERVICE_DESCRIPTOR_REQUEST_HEADER_PARAMETER_NAME = @"name";

/**
 * Service Descriptor Request Header Parameter Value
 */
static NSString * const SERVICE_DESCRIPTOR_REQUEST_HEADER_PARAMETER_VALUE = @"value";


/**
 * Service Descriptor Name
 */
static NSString * const SERVICE_DESCRIPTOR_NAME = @"name";

/**
 * Service Descriptor Description
 */
static NSString * const SERVICE_DESCRIPTOR_DESCRIPTION = @"description";

/**
 * Service Descriptor Protocol
 */
static NSString * const SERVICE_DESCRIPTOR_PROTOCOL = @"protocol";

/**
 * Service Descriptor Instance
 */
static NSString * const SERVICE_DESCRIPTOR_INSTANCE = @"instance";

/**
 * Service Descriptor Port
 */
static NSString * const SERVICE_DESCRIPTOR_PORT = @"port";

/**
 * Service Descriptor Context
 */
static NSString * const SERVICE_DESCRIPTOR_CONTEXT = @"context";

/**
 * Service Descriptor HTTP Protocol
 */
static NSString * const SERVICE_DESCRIPTOR_HTTP_PROTOCOL = @"http";

/**
 * Service Descriptor HTTPS Protocol
 */
static NSString * const SERVICE_DESCRIPTOR_HTTPS_PROTOCOL = @"https";


/**
 * Service Descriptor Request GET Type
 */
static NSString * const SERVICE_DESCRIPTOR_REQUEST_GET_TYPE = @"GET";

/**
 * Service Descriptor Request HEAD Type
 */
static NSString * const SERVICE_DESCRIPTOR_REQUEST_HEAD_TYPE = @"HEAD";

/**
 * Service Descriptor Request POST Type
 */
static NSString * const SERVICE_DESCRIPTOR_REQUEST_POST_TYPE = @"POST";

/**
 * Service Descriptor Request PUT Type
 */
static NSString * const SERVICE_DESCRIPTOR_REQUEST_PUT_TYPE = @"PUT";

/**
 * Service Descriptor Request DELETE Type
 */
static NSString * const SERVICE_DESCRIPTOR_REQUEST_DELETE_TYPE = @"DELETE";

/**
 * Service Descriptor Request TRACE Type
 */
static NSString * const SERVICE_DESCRIPTOR_REQUEST_TRACE_TYPE = @"TRACE";

/**
 * Service Descriptor Request OPTIONS Type
 */
static NSString * const SERVICE_DESCRIPTOR_REQUEST_OPTIONS_TYPE = @"OPTIONS";

/**
 * Service Descriptor Request CONNECT
 */
static NSString * const SERVICE_DESCRIPTOR_REQUEST_CONNECT_TYPE = @"CONNECT";

/**
 * Service Descriptor Request PATCH Type
 */
static NSString * const SERVICE_DESCRIPTOR_REQUEST_PATCH_TYPE = @"PATCH";


/**
 * Service Descriptor Request Name
 */
static NSString * const SERVICE_DESCRIPTOR_REQUEST_NAME = @"name";

/**
 * Service Descriptor Request Type
 */
static NSString * const SERVICE_DESCRIPTOR_REQUEST_TYPE = @"type";

/**
 * Service Descriptor Request API
 */
static NSString * const SERVICE_DESCRIPTOR_REQUEST_API = @"api";

/**
 * Service Descriptor Request Mode
 */
static NSString * const SERVICE_DESCRIPTOR_REQUEST_MODE = @"mode";

/**
 * Service Descriptor Request Data Stream
 */
static NSString * const SERVICE_DESCRIPTOR_REQUEST_DATA_STREAM = @"data-stream";


/**
 * Library Descriptor Service Description
 */
static NSString * const LIBRARY_DESCRIPTOR_SERVICE_DESCRIPTOR = @"service-descriptor";

/**
 * Library Descriptor Property
 */
static NSString * const LIBRARY_DESCRIPTOR_PROPERTY_NAME = @"name";

/**
 * Service Descriptor Request Handler
 */
static NSString * const SERVICE_DESCRIPTOR_REQUEST_HANDLER = @"handler";

/**
 * Service Descriptor Request Sync Request Mode
 */
static NSString * const SERVICE_DESCRIPTOR_REQUEST_SYNC_REQUEST_MODE = @"SYNC";

/**
 * Service Descriptor Request ASYNC Request Mode
 */
static NSString * const SERVICE_DESCRIPTOR_REQUEST_ASYNC_REQUEST_MODE = @"ASYNC";


/**
 * Connection HTTP
 */
static NSString * const CONNECTION_HTTP = @"http";

/**
 * Connection HTTPS
 */
static NSString * const CONNECTION_HTTPS = @"https";

/**
 * Connection Query Parameter Keyword
 */
static NSString * const CONNECTION_QUERY_PARAMETER_KEYWORD = @"?";


//Resource Constants

/**
 * Resource Open Curly Bracket
 */
static NSString * const RESOURCE_OPEN_CURLY_BRACKET = @"{";

/**
 * Resource Close Curly Bracket
 */
static NSString * const RESOURCE_CLOSE_CURLY_BRACKET = @"}";

/**
 * Resource Slash
 */
static NSString * const RESOURCE_SLASH = @"-";

/**
 * Resource Comma
 */
static NSString * const RESOURCE_COMMA = @",";

/**
 * Resource Space
 */
static NSString * const RESOURCE_SPACE = @" ";

/**
 * Resource Reference
 */
static NSString * const RESOURCE_REFERENCE = @"@resource";

/**
 * Resource Self Reference
 */
static NSString * const RESOURCE_SELF_REFERENCE = @"@self";

/**
 * Resource Refer Reference
 */
static NSString * const RESOURCE_REFER_REFERENCE = @"@refer";

/**
 * Resource DOT
 */
static NSString * const RESOURCE_DOT = @".";


/**
 1** Informational
	*/

/**
 * Connection Status Code 100
 */
static int const CONNECTION_STATUS_CODE_100 = 100;

/**
 * Connection Status Code 101
 */
static int const CONNECTION_STATUS_CODE_101 = 101;

/**
 * Connection Status Code 102
 */
static int const CONNECTION_STATUS_CODE_102 = 102;

/**
 * Connection Status Code 103
 */
static int const CONNECTION_STATUS_CODE_103 = 103;

/**
 * Connection Status Code 122
 */
static int const CONNECTION_STATUS_CODE_122 = 122;

/**
 * Connection Status Message 100
 */
static NSString * const CONNECTION_STATUS_MESSAGE_100 = @"Client should continue with request.";

/**
 * Connection Status Message 101
 */
static NSString * const CONNECTION_STATUS_MESSAGE_101 = @"Server is switching protocols.";

/**
 * Connection Status Message 102
 */
static NSString * const CONNECTION_STATUS_MESSAGE_102 = @"Server has received and is processing the request.";

/**
 * Connection Status Message 103
 */
static NSString * const CONNECTION_STATUS_MESSAGE_103 = @"Resume aborted PUT or POST requests.";

/**
 * Connection Status Message 122
 */
static NSString * const CONNECTION_STATUS_MESSAGE_122 = @"URI is longer than a maximum of 2083 characters.";



/**
 2** Success
	*/

/**
 * Connection Status Code 200
 */
static int const CONNECTION_STATUS_CODE_200 = 200;

/**
 * Connection Status Code 201
 */
static int const CONNECTION_STATUS_CODE_201 = 201;

/**
 * Connection Status Code 202
 */
static int const CONNECTION_STATUS_CODE_202 = 202;

/**
 * Connection Status Code 203
 */
static int const CONNECTION_STATUS_CODE_203 = 203;

/**
 * Connection Status Code 204
 */
static int const CONNECTION_STATUS_CODE_204 = 204;

/**
 * Connection Status Code 205
 */
static int const CONNECTION_STATUS_CODE_205 = 205;

/**
 * Connection Status Code 206
 */
static int const CONNECTION_STATUS_CODE_206 = 206;

/**
 * Connection Status Code 207
 */
static int const CONNECTION_STATUS_CODE_207 = 207;

/**
 * Connection Status Code 208
 */
static int const CONNECTION_STATUS_CODE_208 = 208;

/**
 * Connection Status Code 226
 */
static int const CONNECTION_STATUS_CODE_226 = 226;

/**
 * Connection Status Message 200
 */
static NSString * const CONNECTION_STATUS_MESSAGE_200 = @"Standard response for successful HTTP requests.";

/**
 * Connection Status Message 201
 */
static NSString * const CONNECTION_STATUS_MESSAGE_201 = @"Request has been fulfilled; new resource created.";

/**
 * Connection Status Message 202
 */
static NSString * const CONNECTION_STATUS_MESSAGE_202 = @"Request accepted, processing pending.";

/**
 * Connection Status Message 203
 */
static NSString * const CONNECTION_STATUS_MESSAGE_203 = @"Request processed, information may be from another source.";

/**
 * Connection Status Message 204
 */
static NSString * const CONNECTION_STATUS_MESSAGE_204 = @"Request processed, no content returned.";

/**
 * Connection Status Message 205
 */
static NSString * const CONNECTION_STATUS_MESSAGE_205 = @"Request processed, no content returned, reset document view.";

/**
 * Connection Status Message 206
 */
static NSString * const CONNECTION_STATUS_MESSAGE_206 = @"Partial resource return due to request header.";

/**
 * Connection Status Message 207
 */
static NSString * const CONNECTION_STATUS_MESSAGE_207 = @"XMLl, can contain multiple separate responses.";

/**
 * Connection Status Message 208
 */
static NSString * const CONNECTION_STATUS_MESSAGE_208 = @"Results previously returned.";

/**
 * Connection Status Message 226
 */
static NSString * const CONNECTION_STATUS_MESSAGE_226 = @"Request fulfilled, response is instance-manipulations.";



/**
 3** Redirect
	*/

/**
 * Connection Status Code 300
 */
static int const CONNECTION_STATUS_CODE_300 = 300;

/**
 * Connection Status Code 301
 */
static int const CONNECTION_STATUS_CODE_301 = 301;

/**
 * Connection Status Code 302
 */
static int const CONNECTION_STATUS_CODE_302 = 302;

/**
 * Connection Status Code 303
 */
static int const CONNECTION_STATUS_CODE_303 = 303;

/**
 * Connection Status Code 304
 */
static int const CONNECTION_STATUS_CODE_304 = 304;

/**
 * Connection Status Code 305
 */
static int const CONNECTION_STATUS_CODE_305 = 305;

/**
 * Connection Status Code 306
 */
static int const CONNECTION_STATUS_CODE_306 = 306;

/**
 * Connection Statas Code 307
 */
static int const CONNECTION_STATUS_CODE_307 = 307;

/**
 * Connection Status Code 308
 */
static int const CONNECTION_STATUS_CODE_308 = 308;


/**
 * Connection Status Message 300
 */
static NSString * const CONNECTION_STATUS_MESSAGE_300 = @"Multiple options for the resource delivered.";

/**
 * Connection Status Message 301
 */
static NSString * const CONNECTION_STATUS_MESSAGE_301 = @"This and all future requests directed to the given URI.";

/**
 * Connection Status Message 302
 */
static NSString * const CONNECTION_STATUS_MESSAGE_302 = @"Temporary response to request found via alternative URI.";

/**
 * Connection Status Message 303
 */
static NSString * const CONNECTION_STATUS_MESSAGE_303 = @"Permanent response to request found via alternative URI.";

/**
 * Connection Status Message 304
 */
static NSString * const CONNECTION_STATUS_MESSAGE_304 = @"Resource has not been modified since last requested.";

/**
 * Connection Status Message 305
 */
static NSString * const CONNECTION_STATUS_MESSAGE_305 = @"Content located elsewhere, retrieve from there.";

/**
 * Connection Status Message 306
 */
static NSString * const CONNECTION_STATUS_MESSAGE_306 = @"Subsequent requests should use the specified proxy.";

/**
 * Connection Status Message 307
 */
static NSString * const CONNECTION_STATUS_MESSAGE_307 = @"Connect again to different URI as provided.";

/**
 * Connection Status Message 308
 */
static NSString * const CONNECTION_STATUS_MESSAGE_308 = @"Resumable HTTP Requests.";



/**
 4** Client Error
	*/

/**
 * Connection Status Code 400
 */
static int const CONNECTION_STATUS_CODE_400 = 400;

/**
 * Connection Status Code 401
 */
static int const CONNECTION_STATUS_CODE_401 = 401;

/**
 * Connection Status Code 402
 */
static int const CONNECTION_STATUS_CODE_402 = 402;

/**
 * Connection Status Code 403
 */
static int const CONNECTION_STATUS_CODE_403 = 403;

/**
 * Connection Status Code 404
 */
static int const CONNECTION_STATUS_CODE_404 = 404;

/**
 * Connection Status Code 405
 */
static int const CONNECTION_STATUS_CODE_405 = 405;

/**
 * Connection Status Code 406
 */
static int const CONNECTION_STATUS_CODE_406 = 406;

/**
 * Connection Status Code 407
 */
static int const CONNECTION_STATUS_CODE_407 = 407;

/**
 * Connection Status Code 408
 */
static int const CONNECTION_STATUS_CODE_408 = 408;

/**
 * Connection Status Code 409
 */
static int const CONNECTION_STATUS_CODE_409 = 409;

/**
 * Connection Status Code 410
 */
static int const CONNECTION_STATUS_CODE_410 = 410;

/**
 * Connection Status Code 411
 */
static int const CONNECTION_STATUS_CODE_411 = 411;

/**
 * Connection Status Code 412
 */
static int const CONNECTION_STATUS_CODE_412 = 412;

/**
 * Connection Status Code 413
 */
static int const CONNECTION_STATUS_CODE_413 = 413;

/**
 * Connection Status Code 414
 */
static int const CONNECTION_STATUS_CODE_414 = 414;

/**
 * Connection Status Code 415
 */
static int const CONNECTION_STATUS_CODE_415 = 415;

/**
 * Connection Status Code 416
 */
static int const CONNECTION_STATUS_CODE_416 = 416;

/**
 * Connection Status Code 417
 */
static int const CONNECTION_STATUS_CODE_417 = 417;

/**
 * Connection Status Code 418
 */
static int const CONNECTION_STATUS_CODE_418 = 418;

/**
 * Connection Status Code 420
 */
static int const CONNECTION_STATUS_CODE_420 = 420;

/**
 * Connection Status Code 422
 */
static int const CONNECTION_STATUS_CODE_422 = 422;

/**
 * Connection Status Code 423
 */
static int const CONNECTION_STATUS_CODE_423 = 423;

/**
 * Connection Status Code 424
 */
static int const CONNECTION_STATUS_CODE_424 = 424;

/**
 * Connection Status Code 426
 */
static int const CONNECTION_STATUS_CODE_426 = 426;

/**
 * Connection Status Code 428
 */
static int const CONNECTION_STATUS_CODE_428 = 428;

/**
 * Connection Status Code 429
 */
static int const CONNECTION_STATUS_CODE_429 = 429;

/**
 * Connection Status Code 431
 */
static int const CONNECTION_STATUS_CODE_431 = 431;

/**
 * Connection Status Code 444
 */
static int const CONNECTION_STATUS_CODE_444 = 444;

/**
 * Connection Status Code 449
 */
static int const CONNECTION_STATUS_CODE_449 = 449;

/**
 * Connection Status Code 450
 */
static int const CONNECTION_STATUS_CODE_450 = 450;

/**
 * Connection Status Code 451
 */
static int const CONNECTION_STATUS_CODE_451 = 451;

/**
 * Connection Status Code 499
 */
static int const CONNECTION_STATUS_CODE_499 = 499;


/**
 * Connection Status Message 400
 */
static NSString * const CONNECTION_STATUS_MESSAGE_400 = @"Request cannot be fulfilled due to bad syntax.";

/**
 * Connection Status Message 401
 */
static NSString * const CONNECTION_STATUS_MESSAGE_401 = @"Authentication is possible but has failed.";

/**
 * Connection Status Message 402
 */
static NSString * const CONNECTION_STATUS_MESSAGE_402 = @"Payment required, reserved for future use.";

/**
 * Connection Status Message 403
 */
static NSString * const CONNECTION_STATUS_MESSAGE_403 = @"Server refuses to respond to request.";

/**
 * Connection Status Message 404
 */
static NSString * const CONNECTION_STATUS_MESSAGE_404 = @"Requested resource could not be found.";

/**
 * Connection Status Message 405
 */
static NSString * const CONNECTION_STATUS_MESSAGE_405 = @"Request method not supported by that resource.";

/**
 * Connection Status Message 406
 */
static NSString * const CONNECTION_STATUS_MESSAGE_406 = @"Content not acceptable according to the Accept headers.";

/**
 * Connection Status Message 407
 */
static NSString * const CONNECTION_STATUS_MESSAGE_407 = @"Client must first authenticate itself with the proxy.";

/**
 * Connection Status Message 408
 */
static NSString * const CONNECTION_STATUS_MESSAGE_408 = @"Server timed out waiting for the request.";

/**
 * Connection Status Message 409
 */
static NSString * const CONNECTION_STATUS_MESSAGE_409 = @"Request could not be processed because of conflict.";

/**
 * Connection Status Message 410
 */
static NSString * const CONNECTION_STATUS_MESSAGE_410 = @"Resource is no longer available and will not be available again.";

/**
 * Connection Status Message 411
 */
static NSString * const CONNECTION_STATUS_MESSAGE_411 = @"Request did not specify the length of its content.";

/**
 * Connection Status Message 412
 */
static NSString * const CONNECTION_STATUS_MESSAGE_412 = @"Server does not meet request preconditions.";

/**
 * Connection Status Message 413
 */
static NSString * const CONNECTION_STATUS_MESSAGE_413 = @"Request is larger than the server is willing or able to process.";

/**
 * Connection Status Message 414
 */
static NSString * const CONNECTION_STATUS_MESSAGE_414 = @"URI provided was too long for the server to process.";

/**
 * Connection Status Message 415
 */
static NSString * const CONNECTION_STATUS_MESSAGE_415 = @"Server does not support media type.";

/**
 * Connection Status Message 416
 */
static NSString * const CONNECTION_STATUS_MESSAGE_416 = @"Client has asked for unprovidable portion of the file.";

/**
 * Connection Status Message 417
 */
static NSString * const CONNECTION_STATUS_MESSAGE_417 = @"Server cannot meet requirements of Expect request.";

/**
 * Connection Status Message 418
 */
static NSString * const CONNECTION_STATUS_MESSAGE_418 = @"I'm a teapot.";

/**
 * Connection Status Message 420
 */
static NSString * const CONNECTION_STATUS_MESSAGE_420 = @"Twitter rate limiting.";

/**
 * Connection Status Message 422
 */
static NSString * const CONNECTION_STATUS_MESSAGE_422 = @"Request unable to be followed due to semantic errors.";

/**
 * Connection Status Message 423
 */
static NSString * const CONNECTION_STATUS_MESSAGE_423 = @"Resource that is being accessed is locked.";

/**
 * Connection Status Message 424
 */
static NSString * const CONNECTION_STATUS_MESSAGE_424 = @"Request failed due to failure of a previous request.";

/**
 * Connection Status Message 426
 */
static NSString * const CONNECTION_STATUS_MESSAGE_426 = @"Client should switch to a different protocol.";

/**
 * Connection Status Message 428
 */
static NSString * const CONNECTION_STATUS_MESSAGE_428 = @"Origin server requires the request to be conditional.";

/**
 * Connection Status Message 429
 */
static NSString * const CONNECTION_STATUS_MESSAGE_429 = @"User has sent too many requests in a given amount of.";

/**
 * Connection Status Message 431
 */
static NSString * const CONNECTION_STATUS_MESSAGE_431 = @"Server is unwilling to process the request.";

/**
 * Connection Status Message 444
 */
static NSString * const CONNECTION_STATUS_MESSAGE_444 = @"Server returns no information and closes the connection.";

/**
 * Connection Status Message 449
 */
static NSString * const CONNECTION_STATUS_MESSAGE_449 = @"Request should be retried after performing action.";

/**
 * Connection Status Message 450
 */
static NSString * const CONNECTION_STATUS_MESSAGE_450 = @"Windows Parental Controls blocking access to webpage.";

/**
 * Connection Status Message 451
 */
static NSString * const CONNECTION_STATUS_MESSAGE_451 = @"The server cannot reach the client's mailbox.";

/**
 * Connection Status Message 499
 */
static NSString * const CONNECTION_STATUS_MESSAGE_499 = @"Connection closed by client while HTTP server is processing.";



/**
 5** Server Error
	*/

/**
 * Connection Status Code 500
 */
static int const CONNECTION_STATUS_CODE_500 = 500;

/**
 * Connection Status Code 501
 */
static int const CONNECTION_STATUS_CODE_501 = 501;

/**
 * Connection Status Code 502
 */
static int const CONNECTION_STATUS_CODE_502 = 502;

/**
 * Connection Status Code 503
 */
static int const CONNECTION_STATUS_CODE_503 = 503;

/**
 * Connection Status Code 504
 */
static int const CONNECTION_STATUS_CODE_504 = 504;

/**
 * Connection Status Code 505
 */
static int const CONNECTION_STATUS_CODE_505 = 505;

/**
 * Connection Status Code 506
 */
static int const CONNECTION_STATUS_CODE_506 = 506;

/**
 * Connection Status Code 507
 */
static int const CONNECTION_STATUS_CODE_507 = 507;

/**
 * Connection Status Code 508
 */
static int const CONNECTION_STATUS_CODE_508 = 508;

/**
 * Connection Status Code 509
 */
static int const CONNECTION_STATUS_CODE_509 = 509;

/**
 * Connection Status Code 510
 */
static int const CONNECTION_STATUS_CODE_510 = 510;

/**
 * Connection Status Code 511
 */
static int const CONNECTION_STATUS_CODE_511 = 511;

/**
 * Connection Status Code 598
 */
static int const CONNECTION_STATUS_CODE_598 = 598;

/**
 * Connection Status Code 599
 */
static int const CONNECTION_STATUS_CODE_599 = 599;


/**
 * Connection Status Message 500
 */
static NSString * const CONNECTION_STATUS_MESSAGE_500 = @"Generic error message.";

/**
 * Connection Status Message 501
 */
static NSString * const CONNECTION_STATUS_MESSAGE_501 = @"Server does not recognise method or lacks ability to fulfill.";

/**
 * Connection Status Message 502
 */
static NSString * const CONNECTION_STATUS_MESSAGE_502 = @"Server received an invalid response from upstream server.";

/**
 * Connection Status Message 503
 */
static NSString * const CONNECTION_STATUS_MESSAGE_503 = @"Server is currently unavailable.";

/**
 * Connection Status Message 504
 */
static NSString * const CONNECTION_STATUS_MESSAGE_504 = @"Gateway did not receive response from upstream server.";

/**
 * Connection Status Message 505
 */
static NSString * const CONNECTION_STATUS_MESSAGE_505 = @"Server does not support the HTTP protocol version.";

/**
 * Connection Status Message 506
 */
static NSString * const CONNECTION_STATUS_MESSAGE_506 = @"Content negotiation for the request results in a circular.";

/**
 * Connection Status Message 507
 */
static NSString * const CONNECTION_STATUS_MESSAGE_507 = @"Server is unable to store the representation.";

/**
 * Connection Status Message 508
 */
static NSString * const CONNECTION_STATUS_MESSAGE_508 = @"Server detected an infinite loop while processing the request.";

/**
 * Connection Status Message 509
 */
static NSString * const CONNECTION_STATUS_MESSAGE_509 = @"Bandwidth limit exceeded.";

/**
 * Connection Status Message 510
 */
static NSString * const CONNECTION_STATUS_MESSAGE_510 = @"Further extensions to the request are required.";

/**
 * Connection Status Message 511
 */
static NSString * const CONNECTION_STATUS_MESSAGE_511 = @"Client needs to authenticate to gain network access.";

/**
 * Connection Status Message 598
 */
static NSString * const CONNECTION_STATUS_MESSAGE_598 = @"Network read timeout behind the proxy.";

/**
 * Connection Status Message 599
 */
static NSString * const CONNECTION_STATUS_MESSAGE_599 = @"Network connect timeout behind the proxy.";

