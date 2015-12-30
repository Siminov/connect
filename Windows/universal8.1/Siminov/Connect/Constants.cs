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



using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Siminov.Connect
{

    /// <summary>
    /// Contains Constant values used in the framework
    /// All constants referred in the framework are defined in this class
    /// </summary>
    public abstract class Constants
    {

        /// <summary>
        /// Connect Database Descriptor Path
        /// </summary>
        public static readonly String DATABASE_DESSCRIPTOR_PATH = "Siminov/Connect/Resource/DatabaseDescriptor.xml";


        //Application Descriptor Constants

        /// <summary>
        /// Application Descriptor Service Descriptor
        /// </summary>
        public static readonly String APPLICATION_DESCRIPTOR_SERVICE_DESCRIPTOR = "service-descriptor";


        //Sync Descriptor Constants

        /// <summary>
        /// Sync Descriptor
        /// </summary>
        public static readonly String SYNC_DESCRIPTOR = "sync-descriptor";

        /// <summary>
        /// Sync Descriptor Service Descriptor
        /// </summary>
        public static readonly String SYNC_DESCRIPTOR_SERVICE_DESCRIPTOR = "service-descriptor";

        /// <summary>
        /// Sync Descriptor Name
        /// </summary>
        public static readonly String SYNC_DESCRIPTOR_NAME = "name";

        /// <summary>
        /// Sync Descriptor Refresh Interval
        /// </summary>
        public static readonly String SYNC_DESCRIPTOR_REFRESH_INTERVAL = "interval";

        /// <summary>
        /// Sync Descriptor Property
        /// </summary>
        public static readonly String SYNC_DESCRIPTOR_PROPERTY = "property";

        /// <summary>
        /// Sync Descriptor Service Separator
        /// </summary>
        public static readonly String SYNC_DESCRIPTOR_SERVICE_SEPARATOR = ".";

        //Notification Descriptor Constants

        /// <summary>
        /// Notification Descriptor
        /// </summary>
        public static readonly String NOTIFICATION_DESCRIPTOR = "notification-descriptor";


        //Service Descriptor Constants

        /// <summary>
        /// Service Descriptor Request
        /// </summary>
        public static readonly String SERVICE_DESCRIPTOR_REQUESTS = "requests";

        /// <summary>
        /// Service Descriptor Request
        /// </summary>
        public static readonly String SERVICE_DESCRIPTOR_REQUEST = "request";

        /// <summary>
        /// Service Descriptor Property
        /// </summary>
        public static readonly String SERVICE_DESCRIPTOR_PROPERTY = "property";

        /// <summary>
        /// Service Descriptor Property Name
        /// </summary>
        public static readonly String SERVICE_DESCRIPTOR_PROPERTY_NAME = "name";

        /// <summary>
        /// Service Descriptor Request Query Parameter
        /// </summary>
        public static readonly String SERVICE_DESCRIPTOR_REQUEST_QUERY_PARAMETER = "query-parameter";

        /// <summary>
        /// Service Descriptor Request Query Parameter Name 
        /// </summary>
        public static readonly String SERVICE_DESCRIPTOR_REQUEST_QUERY_PARAMETER_NAME = "name";

        /// <summary>
        /// Service Descriptor Request Query Parameter Value
        /// </summary>
        public static readonly String SERVICE_DESCRIPTOR_REQUEST_QUERY_PARAMETER_VALUE = "value";

        /// <summary>
        /// Service Descriptor Request Header Parameter
        /// </summary>
        public static readonly String SERVICE_DESCRIPTOR_REQUEST_HEADER_PARAMETER = "header-parameter";

        /// <summary>
        /// Service Descriptor Request Header Parameter Name 
        /// </summary>
        public static readonly String SERVICE_DESCRIPTOR_REQUEST_HEADER_PARAMETER_NAME = "name";

        /// <summary>
        /// Service Descriptor Request Header Parameter Value
        /// </summary>
        public static readonly String SERVICE_DESCRIPTOR_REQUEST_HEADER_PARAMETER_VALUE = "value";

        /// <summary>
        /// Service Descriptor Name
        /// </summary>
        public static readonly String SERVICE_DESCRIPTOR_NAME = "name";

        /// <summary>
        /// Service Descriptor Description
        /// </summary>
        public static readonly String SERVICE_DESCRIPTOR_DESCRIPTION = "description";

        /// <summary>
        /// Service Descriptor Protocol
        /// </summary>
        public static readonly String SERVICE_DESCRIPTOR_PROTOCOL = "protocol";

        /// <summary>
        /// Service Descriptor Instance
        /// </summary>
        public static readonly String SERVICE_DESCRIPTOR_INSTANCE = "instance";

        /// <summary>
        /// Service Descriptor Port
        /// </summary>
        public static readonly String SERVICE_DESCRIPTOR_PORT = "port";

        /// <summary>
        /// Service Descriptor Context
        /// </summary>
        public static readonly String SERVICE_DESCRIPTOR_CONTEXT = "context";

        /// <summary>
        /// Service Descriptor HTTP Protocol
        /// </summary>
        public static readonly String SERVICE_DESCRIPTOR_HTTP_PROTOCOL = "http";

        /// <summary>
        /// Service Descriptor HTTPS Protocol
        /// </summary>
        public static readonly String SERVICE_DESCRIPTOR_HTTPS_PROTOCOL = "https";


        /// <summary>
        /// Service Descriptor Request GET Type
        /// </summary>
        public static readonly String SERVICE_DESCRIPTOR_REQUEST_GET_TYPE = "GET";

        /// <summary>
        /// Service Descriptor Request HEAD Type
        /// </summary>
        public static readonly String SERVICE_DESCRIPTOR_REQUEST_HEAD_TYPE = "HEAD";

        /// <summary>
        /// Service Descriptor Request POST Type
        /// </summary>
        public static readonly String SERVICE_DESCRIPTOR_REQUEST_POST_TYPE = "POST";

        /// <summary>
        /// Service Descriptor Request PUT Type
        /// </summary>
        public static readonly String SERVICE_DESCRIPTOR_REQUEST_PUT_TYPE = "PUT";

        /// <summary>
        /// Service Descriptor Request DELETE Type
        /// </summary>
        public static readonly String SERVICE_DESCRIPTOR_REQUEST_DELETE_TYPE = "DELETE";

        /// <summary>
        /// Service Descriptor Request TRACE Type
        /// </summary>
        public static readonly String SERVICE_DESCRIPTOR_REQUEST_TRACE_TYPE = "TRACE";

        /// <summary>
        /// Service Descriptor Request OPTIONS Type
        /// </summary>
        public static readonly String SERVICE_DESCRIPTOR_REQUEST_OPTIONS_TYPE = "OPTIONS";

        /// <summary>
        /// Service Descriptor Request CONNECT
        /// </summary>
        public static readonly String SERVICE_DESCRIPTOR_REQUEST_CONNECT_TYPE = "CONNECT";

        /// <summary>
        /// Service Descriptor Request PATCH Type
        /// </summary>
        public static readonly String SERVICE_DESCRIPTOR_REQUEST_PATCH_TYPE = "PATCH";


        /// <summary>
        /// Service Descriptor Request Name
        /// </summary>
        public static readonly String SERVICE_DESCRIPTOR_REQUEST_NAME = "name";

        /// <summary>
        /// Service Descriptor Request Type
        /// </summary>
        public static readonly String SERVICE_DESCRIPTOR_REQUEST_TYPE = "type";

        /// <summary>
        /// Service Descriptor Request API
        /// </summary>
        public static readonly String SERVICE_DESCRIPTOR_REQUEST_API = "api";

        /// <summary>
        /// Service Descriptor Request Mode
        /// </summary>
        public static readonly String SERVICE_DESCRIPTOR_REQUEST_MODE = "mode";

        /// <summary>
        /// Service Descriptor Request Data Stream
        /// </summary>
        public static readonly String SERVICE_DESCRIPTOR_REQUEST_DATA_STREAM = "data-stream";



        /// <summary>
        /// Library Descriptor File Name
        /// </summary>
        public static readonly String LIBRARY_DESCRIPTOR_FILE_NAME = "LibraryDescriptor.xml";

        /// <summary>
        /// Library Descriptor Property
        /// </summary>
        public static readonly String LIBRARY_DESCRIPTOR_PROPERTY = "property";

        /// <summary>
        /// Library Descriptor Service Description
        /// </summary>
        public static readonly String LIBRARY_DESCRIPTOR_SERVICE_DESCRIPTOR = "service-descriptor";

        /// <summary>
        /// Library Descriptor Property 
        /// </summary>
        public static readonly String LIBRARY_DESCRIPTOR_PROPERTY_NAME = "name";

        /// <summary>
        /// Service Descriptor Request Handler
        /// </summary>
        public static readonly String SERVICE_DESCRIPTOR_REQUEST_HANDLER = "handler";

        /// <summary>
        /// Service Descriptor Request Sync Request Mode
        /// </summary>
        public static readonly String SERVICE_DESCRIPTOR_REQUEST_SYNC_REQUEST_MODE = "SYNC";

        /// <summary>
        /// Service Descriptor Request ASYNC Request Mode
        /// </summary>
        public static readonly String SERVICE_DESCRIPTOR_REQUEST_ASYNC_REQUEST_MODE = "ASYNC";


        /// <summary>
        /// Connection HTTP
        /// </summary>
        public static readonly String CONNECTION_HTTP = "http";

        /// <summary>
        /// Connection HTTPS
        /// </summary>
        public static readonly String CONNECTION_HTTPS = "https";

        /// <summary>
        /// Connection Query Parameter Keyword
        /// </summary>
        public static readonly String CONNECTION_QUERY_PARAMETER_KEYWORD = "?";


        //Resource Constants

        /// <summary>
        /// Resource Open Curly Bracket
        /// </summary>
        public static readonly String RESOURCE_OPEN_CURLY_BRACKET = "{";

        /// <summary>
        /// Resource Close Curly Bracket
        /// </summary>
        public static readonly String RESOURCE_CLOSE_CURLY_BRACKET = "}";

        /// <summary>
        /// Resource Slash
        /// </summary>
        public static readonly String RESOURCE_SLASH = "-";

        /// <summary>
        /// Resource Comma
        /// </summary>
        public static readonly String RESOURCE_COMMA = ",";

        /// <summary>
        /// Resource Space
        /// </summary>
        public static readonly String RESOURCE_SPACE = " ";

        /// <summary>
        /// Resource Reference
        /// </summary>
        public static readonly String RESOURCE_REFERENCE = "@resource";

        /// <summary>
        /// Resource Self Reference
        /// </summary>
        public static readonly String RESOURCE_SELF_REFERENCE = "@self";

        /// <summary>
        /// Resource Refer Reference
        /// </summary>
        public static readonly String RESOURCE_REFER_REFERENCE = "@refer";

        /// <summary>
        /// Resource DOT
        /// </summary>
        public static readonly String RESOURCE_DOT = ".";

        /// <summary>
        /// Conneciton Status Code 0
        /// </summary>
        public static readonly int CONNECTION_STATUS_CODE_0 = 0;

        /// <summary>
        /// Conneciton Response Code 202
        /// </summary>
        public static readonly String CONNECTION_RESPONSE_CODE_202 = "Accepted";

        /// <summary>
        /// Connection Response Code 300
        /// </summary>
        public static readonly String CONNECTION_RESPONSE_CODE_300 = "Ambiguous";

        /// <summary>
        /// Connection Response Code 502
        /// </summary>
        public static readonly String CONNECTION_RESPONSE_CODE_502 = "BadGateway";

        /// <summary>
        /// Connection Response Code 400
        /// </summary>
        public static readonly String CONNECTION_RESPONSE_CODE_400 = "BadRequest";

        /// <summary>
        /// Connection Response Code 409
        /// </summary>
        public static readonly String CONNECTION_RESPONSE_CODE_409 = "Conflict";

        /// <summary>
        /// Connection Response Code 100
        /// </summary>
        public static readonly String CONNECTION_RESPONSE_CODE_100 = "Continue";

        /// <summary>
        /// Connection Response Code 201
        /// </summary>
        public static readonly String CONNECTION_RESPONSE_CODE_201 = "Created";

        /// <summary>
        /// Connection Response Code 417
        /// </summary>
        public static readonly String CONNECTION_RESPONSE_CODE_417 = "ExpectationFailed";

        /// <summary>
        /// Connection Response Code 403
        /// </summary>
        public static readonly String CONNECTION_RESPONSE_CODE_403 = "Forbidden";

        /// <summary>
        /// Connection Response Code 302
        /// </summary>
        public static readonly String CONNECTION_RESPONSE_CODE_302 = "Found";

        /// <summary>
        /// Connection Response Code 504
        /// </summary>
        public static readonly String CONNECTION_RESPONSE_CODE_504 = "GatewayTimeout";

        /// <summary>
        /// Connection Response Code 410
        /// </summary>
        public static readonly String CONNECTION_RESPONSE_CODE_410 = "Gone";

        /// <summary>
        /// Connection Response Code 505
        /// </summary>
        public static readonly String CONNECTION_RESPONSE_CODE_505 = "HttpVersionNotSupported";

        /// <summary>
        /// Connection Response Code 500
        /// </summary>
        public static readonly String CONNECTION_RESPONSE_CODE_500 = "InternalServerError";

        /// <summary>
        /// Connection Response Code 411
        /// </summary>
        public static readonly String CONNECTION_RESPONSE_CODE_411 = "LengthRequired";

        /// <summary>
        /// Connection Response Code 405
        /// </summary>
        public static readonly String CONNECTION_RESPONSE_CODE_405 = "MethodNotAllowed";

        /// <summary>
        /// Connection Response Code 301
        /// </summary>
        public static readonly String CONNECTION_RESPONSE_CODE_301 = "Moved";

        /// <summary>
        /// Connection Response Code 204
        /// </summary>
        public static readonly String CONNECTION_RESPONSE_CODE_204 = "NoContent";

        /// <summary>
        /// Connection Response Code 203
        /// </summary>
        public static readonly String CONNECTION_RESPONSE_CODE_203 = "NonAuthoritativeInformation";

        /// <summary>
        /// Connection Response Code 406
        /// </summary>
        public static readonly String CONNECTION_RESPONSE_CODE_406 = "NotAcceptable";

        /// <summary>
        /// Connection Response Code 404
        /// </summary>
        public static readonly String CONNECTION_RESPONSE_CODE_404 = "NotFound";

        /// <summary>
        /// Connection Response Code 501
        /// </summary>
        public static readonly String CONNECTION_RESPONSE_CODE_501 = "NotImplemented";

        /// <summary>
        /// Connection Response Code 304
        /// </summary>
        public static readonly String CONNECTION_RESPONSE_CODE_304 = "NotModified";

        /// <summary>
        /// Connection Response Code 200
        /// </summary>
        public static readonly String CONNECTION_RESPONSE_CODE_200 = "OK";

        /// <summary>
        /// Connection Response Code 206
        /// </summary>
        public static readonly String CONNECTION_RESPONSE_CODE_206 = "PartialContent";

        /// <summary>
        /// Connection Response Code 402
        /// </summary>
        public static readonly String CONNECTION_RESPONSE_CODE_402 = "PaymentRequired";

        /// <summary>
        /// Connection Response Code 412
        /// </summary>
        public static readonly String CONNECTION_RESPONSE_CODE_412 = "PreconditionFailed";

        /// <summary>
        /// Connection Response Code 407
        /// </summary>
        public static readonly String CONNECTION_RESPONSE_CODE_407 = "ProxyAuthenticationRequired";

        /// <summary>
        /// Connection Response Code 307
        /// </summary>
        public static readonly String CONNECTION_RESPONSE_CODE_307 = "RedirectKeepVerb";

        /// <summary>
        /// Connection Response Code 303
        /// </summary>
        public static readonly String CONNECTION_RESPONSE_CODE_303 = "RedirectMethod";

        /// <summary>
        /// Connection Response Code 416
        /// </summary>
        public static readonly String CONNECTION_RESPONSE_CODE_416 = "RequestedRangeNotSatisfiable";

        /// <summary>
        /// Connection Response Code 413
        /// </summary>
        public static readonly String CONNECTION_RESPONSE_CODE_413 = "RequestEntityTooLarge";

        /// <summary>
        /// Connection Response Code 408
        /// </summary>
        public static readonly String CONNECTION_RESPONSE_CODE_408 = "RequestTimeout";

        /// <summary>
        /// Connection Response Code 414
        /// </summary>
        public static readonly String CONNECTION_RESPONSE_CODE_414 = "RequestUriTooLong";

        /// <summary>
        /// Connection Response Code 205
        /// </summary>
        public static readonly String CONNECTION_RESPONSE_CODE_205 = "ResetContent";
        
        /// <summary>
        /// Connection Response Code 503
        /// </summary>
        public static readonly String CONNECTION_RESPONSE_CODE_503 = "ServiceUnavailable";

        /// <summary>
        /// Connection Response Code 101
        /// </summary>
        public static readonly String CONNECTION_RESPONSE_CODE_101 = "SwitchingProtocols";

        /// <summary>
        /// Connection Response Code 401
        /// </summary>
        public static readonly String CONNECTION_RESPONSE_CODE_401 = "Unauthorized";

        /// <summary>
        /// Connection Response Code 415
        /// </summary>
        public static readonly String CONNECTION_RESPONSE_CODE_415 = "UnsupportedMediaType";

        /// <summary>
        /// Connection Response Code 306
        /// </summary>
        public static readonly String CONNECTION_RESPONSE_CODE_306 = "Unused";

        /// <summary>
        /// Connection Response Code 426
        /// </summary>
        public static readonly String CONNECTION_RESPONSE_CODE_426 = "UpgradeRequired";

        /// <summary>
        /// Connection Response Code 305
        /// </summary>
        public static readonly String CONNECTION_RESPONSE_CODE_305 = "UseProxy";



        /**
            1** Informational
        */

        /// <summary>
        /// Connection Status Code 100
        /// </summary>
        public static readonly int CONNECTION_STATUS_CODE_100 = 100;

        /// <summary>
        /// Connection Status Code 101
        /// </summary>
        public static readonly int CONNECTION_STATUS_CODE_101 = 101;

        /// <summary>
        /// Connection Status Code 102
        /// </summary>
        public static readonly int CONNECTION_STATUS_CODE_102 = 102;

        /// <summary>
        /// Connection Status Code 103
        /// </summary>
        public static readonly int CONNECTION_STATUS_CODE_103 = 103;

        /// <summary>
        /// Connection Status Code 122
        /// </summary>
        public static readonly int CONNECTION_STATUS_CODE_122 = 122;

        /// <summary>
        /// Connection Status Message 100
        /// </summary>
        public static readonly String CONNECTION_STATUS_MESSAGE_100 = "Client should continue with request.";

        /// <summary>
        /// Connection Status Message 101
        /// </summary>
        public static readonly String CONNECTION_STATUS_MESSAGE_101 = "Server is switching protocols.";

        /// <summary>
        /// Connection Status Message 102
        /// </summary>
        public static readonly String CONNECTION_STATUS_MESSAGE_102 = "Server has received and is processing the request.";

        /// <summary>
        /// Connection Status Message 103
        /// </summary>
        public static readonly String CONNECTION_STATUS_MESSAGE_103 = "Resume aborted PUT or POST requests.";

        /// <summary>
        /// Connection Status Message 122
        /// </summary>
        public static readonly String CONNECTION_STATUS_MESSAGE_122 = "URI is longer than a maximum of 2083 characters.";



        /**
            2** Success
        */

        /// <summary>
        /// Connection Status Code 200
        /// </summary>
        public static readonly int CONNECTION_STATUS_CODE_200 = 200;

        /// <summary>
        /// Connection Status Code 201
        /// </summary>
        public static readonly int CONNECTION_STATUS_CODE_201 = 201;

        /// <summary>
        /// Connection Status Code 202
        /// </summary>
        public static readonly int CONNECTION_STATUS_CODE_202 = 202;

        /// <summary>
        /// Connection Status Code 203
        /// </summary>
        public static readonly int CONNECTION_STATUS_CODE_203 = 203;

        /// <summary>
        /// Connection Status Code 204
        /// </summary>
        public static readonly int CONNECTION_STATUS_CODE_204 = 204;

        /// <summary>
        /// Connection Status Code 205
        /// </summary>
        public static readonly int CONNECTION_STATUS_CODE_205 = 205;

        /// <summary>
        /// Connection Status Code 206
        /// </summary>
        public static readonly int CONNECTION_STATUS_CODE_206 = 206;

        /// <summary>
        /// Connection Status Code 207
        /// </summary>
        public static readonly int CONNECTION_STATUS_CODE_207 = 207;

        /// <summary>
        /// Connection Status Code 208
        /// </summary>
        public static readonly int CONNECTION_STATUS_CODE_208 = 208;

        /// <summary>
        /// Connection Status Code 226
        /// </summary>
        public static readonly int CONNECTION_STATUS_CODE_226 = 226;

        /// <summary>
        /// Connection Status Message 200
        /// </summary>
        public static readonly String CONNECTION_STATUS_MESSAGE_200 = "Standard response for successful HTTP requests.";

        /// <summary>
        /// Connection Status Message 201
        /// </summary>
        public static readonly String CONNECTION_STATUS_MESSAGE_201 = "Request has been fulfilled; new resource created.";

        /// <summary>
        /// Connection Status Message 202
        /// </summary>
        public static readonly String CONNECTION_STATUS_MESSAGE_202 = "Request accepted, processing pending.";

        /// <summary>
        /// Connection Status Message 203
        /// </summary>
        public static readonly String CONNECTION_STATUS_MESSAGE_203 = "Request processed, information may be from another source.";

        /// <summary>
        /// Connection Status Message 204
        /// </summary>
        public static readonly String CONNECTION_STATUS_MESSAGE_204 = "Request processed, no content returned.";

        /// <summary>
        /// Connection Status Message 205
        /// </summary>
        public static readonly String CONNECTION_STATUS_MESSAGE_205 = "Request processed, no content returned, reset document view.";

        /// <summary>
        /// Connection Status Message 206
        /// </summary>
        public static readonly String CONNECTION_STATUS_MESSAGE_206 = "Partial resource return due to request header.";

        /// <summary>
        /// Connection Status Message 207
        /// </summary>
        public static readonly String CONNECTION_STATUS_MESSAGE_207 = "XMLl, can contain multiple separate responses.";

        /// <summary>
        /// Connection Status Message 208
        /// </summary>
        public static readonly String CONNECTION_STATUS_MESSAGE_208 = "Results previously returned.";

        /// <summary>
        /// Connection Status Message 226
        /// </summary>
        public static readonly String CONNECTION_STATUS_MESSAGE_226 = "Request fulfilled, response is instance-manipulations.";



        /**
            3** Redirect
        */

        /// <summary>
        /// Connection Status Code 300
        /// </summary>
        public static readonly int CONNECTION_STATUS_CODE_300 = 300;

        /// <summary>
        /// Connection Status Code 301
        /// </summary>
        public static readonly int CONNECTION_STATUS_CODE_301 = 301;

        /// <summary>
        /// Connection Status Code 302
        /// </summary>
        public static readonly int CONNECTION_STATUS_CODE_302 = 302;

        /// <summary>
        /// Connection Status Code 303
        /// </summary>
        public static readonly int CONNECTION_STATUS_CODE_303 = 303;

        /// <summary>
        /// Connection Status Code 304
        /// </summary>
        public static readonly int CONNECTION_STATUS_CODE_304 = 304;

        /// <summary>
        /// Connection Status Code 305
        /// </summary>
        public static readonly int CONNECTION_STATUS_CODE_305 = 305;

        /// <summary>
        /// Connection Status Code 306
        /// </summary>
        public static readonly int CONNECTION_STATUS_CODE_306 = 306;

        /// <summary>
        /// Connection Statas Code 307
        /// </summary>
        public static readonly int CONNECTION_STATUS_CODE_307 = 307;

        /// <summary>
        /// Connection Status Code 308
        /// </summary>
        public static readonly int CONNECTION_STATUS_CODE_308 = 308;


        /// <summary>
        /// Connection Status Message 300
        /// </summary>
        public static readonly String CONNECTION_STATUS_MESSAGE_300 = "Multiple options for the resource delivered.";

        /// <summary>
        /// Connection Status Message 301
        /// </summary>
        public static readonly String CONNECTION_STATUS_MESSAGE_301 = "This and all future requests directed to the given URI.";

        /// <summary>
        /// Connection Status Message 302
        /// </summary>
        public static readonly String CONNECTION_STATUS_MESSAGE_302 = "Temporary response to request found via alternative URI.";

        /// <summary>
        /// Connection Status Message 303
        /// </summary>
        public static readonly String CONNECTION_STATUS_MESSAGE_303 = "Permanent response to request found via alternative URI.";

        /// <summary>
        /// Connection Status Message 304
        /// </summary>
        public static readonly String CONNECTION_STATUS_MESSAGE_304 = "Resource has not been modified since last requested.";

        /// <summary>
        /// Connection Status Message 305
        /// </summary>
        public static readonly String CONNECTION_STATUS_MESSAGE_305 = "Content located elsewhere, retrieve from there.";

        /// <summary>
        /// Connection Status Message 306
        /// </summary>
        public static readonly String CONNECTION_STATUS_MESSAGE_306 = "Subsequent requests should use the specified proxy.";

        /// <summary>
        /// Connection Status Message 307
        /// </summary>
        public static readonly String CONNECTION_STATUS_MESSAGE_307 = "Connect again to different URI as provided.";

        /// <summary>
        /// Connection Status Message 308
        /// </summary>
        public static readonly String CONNECTION_STATUS_MESSAGE_308 = "Resumable HTTP Requests.";



        /**
            4** Client Error
        */

        /// <summary>
        /// Connection Status Code 400
        /// </summary>
        public static readonly int CONNECTION_STATUS_CODE_400 = 400;

        /// <summary>
        /// Connection Status Code 401
        /// </summary>
        public static readonly int CONNECTION_STATUS_CODE_401 = 401;

        /// <summary>
        /// Connection Status Code 402
        /// </summary>
        public static readonly int CONNECTION_STATUS_CODE_402 = 402;

        /// <summary>
        /// Connection Status Code 403
        /// </summary>
        public static readonly int CONNECTION_STATUS_CODE_403 = 403;

        /// <summary>
        /// Connection Status Code 404
        /// </summary>
        public static readonly int CONNECTION_STATUS_CODE_404 = 404;

        /// <summary>
        /// Connection Status Code 405
        /// </summary>
        public static readonly int CONNECTION_STATUS_CODE_405 = 405;

        /// <summary>
        /// Connection Status Code 406
        /// </summary>
        public static readonly int CONNECTION_STATUS_CODE_406 = 406;

        /// <summary>
        /// Connection Status Code 407
        /// </summary>
        public static readonly int CONNECTION_STATUS_CODE_407 = 407;

        /// <summary>
        /// Connection Status Code 408
        /// </summary>
        public static readonly int CONNECTION_STATUS_CODE_408 = 408;

        /// <summary>
        /// Connection Status Code 409
        /// </summary>
        public static readonly int CONNECTION_STATUS_CODE_409 = 409;

        /// <summary>
        /// Connection Status Code 410
        /// </summary>
        public static readonly int CONNECTION_STATUS_CODE_410 = 410;

        /// <summary>
        /// Connection Status Code 411
        /// </summary>
        public static readonly int CONNECTION_STATUS_CODE_411 = 411;

        /// <summary>
        /// Connection Status Code 412
        /// </summary>
        public static readonly int CONNECTION_STATUS_CODE_412 = 412;

        /// <summary>
        /// Connection Status Code 413
        /// </summary>
        public static readonly int CONNECTION_STATUS_CODE_413 = 413;

        /// <summary>
        /// Connection Status Code 414
        /// </summary>
        public static readonly int CONNECTION_STATUS_CODE_414 = 414;

        /// <summary>
        /// Connection Status Code 415
        /// </summary>
        public static readonly int CONNECTION_STATUS_CODE_415 = 415;

        /// <summary>
        /// Connection Status Code 416
        /// </summary>
        public static readonly int CONNECTION_STATUS_CODE_416 = 416;

        /// <summary>
        /// Connection Status Code 417
        /// </summary>
        public static readonly int CONNECTION_STATUS_CODE_417 = 417;

        /// <summary>
        /// Connection Status Code 418
        /// </summary>
        public static readonly int CONNECTION_STATUS_CODE_418 = 418;

        /// <summary>
        /// Connection Status Code 420
        /// </summary>
        public static readonly int CONNECTION_STATUS_CODE_420 = 420;

        /// <summary>
        /// Connection Status Code 422
        /// </summary>
        public static readonly int CONNECTION_STATUS_CODE_422 = 422;

        /// <summary>
        /// Connection Status Code 423
        /// </summary>
        public static readonly int CONNECTION_STATUS_CODE_423 = 423;

        /// <summary>
        /// Connection Status Code 424
        /// </summary>
        public static readonly int CONNECTION_STATUS_CODE_424 = 424;

        /// <summary>
        /// Connection Status Code 426
        /// </summary>
        public static readonly int CONNECTION_STATUS_CODE_426 = 426;

        /// <summary>
        /// Connection Status Code 428
        /// </summary>
        public static readonly int CONNECTION_STATUS_CODE_428 = 428;

        /// <summary>
        /// Connection Status Code 429
        /// </summary>
        public static readonly int CONNECTION_STATUS_CODE_429 = 429;

        /// <summary>
        /// Connection Status Code 431
        /// </summary>
        public static readonly int CONNECTION_STATUS_CODE_431 = 431;

        /// <summary>
        /// Connection Status Code 444
        /// </summary>
        public static readonly int CONNECTION_STATUS_CODE_444 = 444;

        /// <summary>
        /// Connection Status Code 449
        /// </summary>
        public static readonly int CONNECTION_STATUS_CODE_449 = 449;

        /// <summary>
        /// Connection Status Code 450
        /// </summary>
        public static readonly int CONNECTION_STATUS_CODE_450 = 450;

        /// <summary>
        /// Connection Status Code 451
        /// </summary>
        public static readonly int CONNECTION_STATUS_CODE_451 = 451;

        /// <summary>
        /// Connection Status Code 499
        /// </summary>
        public static readonly int CONNECTION_STATUS_CODE_499 = 499;


        /// <summary>
        /// Connection Status Message 400
        /// </summary>
        public static readonly String CONNECTION_STATUS_MESSAGE_400 = "Request cannot be fulfilled due to bad syntax.";

        /// <summary>
        /// Connection Status Message 401
        /// </summary>
        public static readonly String CONNECTION_STATUS_MESSAGE_401 = "Authentication is possible but has failed.";

        /// <summary>
        /// Connection Status Message 402
        /// </summary>
        public static readonly String CONNECTION_STATUS_MESSAGE_402 = "Payment required, reserved for future use.";

        /// <summary>
        /// Connection Status Message 403
        /// </summary>
        public static readonly String CONNECTION_STATUS_MESSAGE_403 = "Server refuses to respond to request.";

        /// <summary>
        /// Connection Status Message 404
        /// </summary>
        public static readonly String CONNECTION_STATUS_MESSAGE_404 = "Requested resource could not be found.";

        /// <summary>
        /// Connection Status Message 405
        /// </summary>
        public static readonly String CONNECTION_STATUS_MESSAGE_405 = "Request method not supported by that resource.";

        /// <summary>
        /// Connection Status Message 406
        /// </summary>
        public static readonly String CONNECTION_STATUS_MESSAGE_406 = "Content not acceptable according to the Accept headers.";

        /// <summary>
        /// Connection Status Message 407
        /// </summary>
        public static readonly String CONNECTION_STATUS_MESSAGE_407 = "Client must first authenticate itself with the proxy.";

        /// <summary>
        /// Connection Status Message 408
        /// </summary>
        public static readonly String CONNECTION_STATUS_MESSAGE_408 = "Server timed out waiting for the request.";

        /// <summary>
        /// Connection Status Message 409
        /// </summary>
        public static readonly String CONNECTION_STATUS_MESSAGE_409 = "Request could not be processed because of conflict.";

        /// <summary>
        /// Connection Status Message 410
        /// </summary>
        public static readonly String CONNECTION_STATUS_MESSAGE_410 = "Resource is no longer available and will not be available again.";

        /// <summary>
        /// Connection Status Message 411
        /// </summary>
        public static readonly String CONNECTION_STATUS_MESSAGE_411 = "Request did not specify the length of its content.";

        /// <summary>
        /// Connection Status Message 412
        /// </summary>
        public static readonly String CONNECTION_STATUS_MESSAGE_412 = "Server does not meet request preconditions.";

        /// <summary>
        /// Connection Status Message 413
        /// </summary>
        public static readonly String CONNECTION_STATUS_MESSAGE_413 = "Request is larger than the server is willing or able to process.";

        /// <summary>
        /// Connection Status Message 414
        /// </summary>
        public static readonly String CONNECTION_STATUS_MESSAGE_414 = "URI provided was too long for the server to process.";

        /// <summary>
        /// Connection Status Message 415
        /// </summary>
        public static readonly String CONNECTION_STATUS_MESSAGE_415 = "Server does not support media type.";

        /// <summary>
        /// Connection Status Message 416
        /// </summary>
        public static readonly String CONNECTION_STATUS_MESSAGE_416 = "Client has asked for unprovidable portion of the file.";

        /// <summary>
        /// Connection Status Message 417
        /// </summary>
        public static readonly String CONNECTION_STATUS_MESSAGE_417 = "Server cannot meet requirements of Expect request.";

        /// <summary>
        /// Connection Status Message 418
        /// </summary>
        public static readonly String CONNECTION_STATUS_MESSAGE_418 = "I'm a teapot.";

        /// <summary>
        /// Connection Status Message 420
        /// </summary>
        public static readonly String CONNECTION_STATUS_MESSAGE_420 = "Twitter rate limiting.";

        /// <summary>
        /// Connection Status Message 422
        /// </summary>
        public static readonly String CONNECTION_STATUS_MESSAGE_422 = "Request unable to be followed due to semantic errors.";

        /// <summary>
        /// Connection Status Message 423
        /// </summary>
        public static readonly String CONNECTION_STATUS_MESSAGE_423 = "Resource that is being accessed is locked.";

        /// <summary>
        /// Connection Status Message 424
        /// </summary>
        public static readonly String CONNECTION_STATUS_MESSAGE_424 = "Request failed due to failure of a previous request.";

        /// <summary>
        /// Connection Status Message 426
        /// </summary>
        public static readonly String CONNECTION_STATUS_MESSAGE_426 = "Client should switch to a different protocol.";

        /// <summary>
        /// Connection Status Message 428
        /// </summary>
        public static readonly String CONNECTION_STATUS_MESSAGE_428 = "Origin server requires the request to be conditional.";

        /// <summary>
        /// Connection Status Message 429
        /// </summary>
        public static readonly String CONNECTION_STATUS_MESSAGE_429 = "User has sent too many requests in a given amount of.";

        /// <summary>
        /// Connection Status Message 431
        /// </summary>
        public static readonly String CONNECTION_STATUS_MESSAGE_431 = "Server is unwilling to process the request.";

        /// <summary>
        /// Connection Status Message 444
        /// </summary>
        public static readonly String CONNECTION_STATUS_MESSAGE_444 = "Server returns no information and closes the connection.";

        /// <summary>
        /// Connection Status Message 449
        /// </summary>
        public static readonly String CONNECTION_STATUS_MESSAGE_449 = "Request should be retried after performing action.";

        /// <summary>
        /// Connection Status Message 450
        /// </summary>
        public static readonly String CONNECTION_STATUS_MESSAGE_450 = "Windows Parental Controls blocking access to webpage.";

        /// <summary>
        /// Connection Status Message 451
        /// </summary>
        public static readonly String CONNECTION_STATUS_MESSAGE_451 = "The server cannot reach the client's mailbox.";

        /// <summary>
        /// Connection Status Message 499
        /// </summary>
        public static readonly String CONNECTION_STATUS_MESSAGE_499 = "Connection closed by client while HTTP server is processing.";



        /**
            5** Server Error
        */

        /// <summary>
        /// Connection Status Code 500
        /// </summary>
        public static readonly int CONNECTION_STATUS_CODE_500 = 500;

        /// <summary>
        /// Connection Status Code 501
        /// </summary>
        public static readonly int CONNECTION_STATUS_CODE_501 = 501;

        /// <summary>
        /// Connection Status Code 502
        /// </summary>
        public static readonly int CONNECTION_STATUS_CODE_502 = 502;

        /// <summary>
        /// Connection Status Code 503
        /// </summary>
        public static readonly int CONNECTION_STATUS_CODE_503 = 503;

        /// <summary>
        /// Connection Status Code 504
        /// </summary>
        public static readonly int CONNECTION_STATUS_CODE_504 = 504;

        /// <summary>
        /// Connection Status Code 505
        /// </summary>
        public static readonly int CONNECTION_STATUS_CODE_505 = 505;

        /// <summary>
        /// Connection Status Code 506
        /// </summary>
        public static readonly int CONNECTION_STATUS_CODE_506 = 506;

        /// <summary>
        /// Connection Status Code 507
        /// </summary>
        public static readonly int CONNECTION_STATUS_CODE_507 = 507;

        /// <summary>
        /// Connection Status Code 508
        /// </summary>
        public static readonly int CONNECTION_STATUS_CODE_508 = 508;

        /// <summary>
        /// Connection Status Code 509
        /// </summary>
        public static readonly int CONNECTION_STATUS_CODE_509 = 509;

        /// <summary>
        /// Connection Status Code 510
        /// </summary>
        public static readonly int CONNECTION_STATUS_CODE_510 = 510;

        /// <summary>
        /// Connection Status Code 511
        /// </summary>
        public static readonly int CONNECTION_STATUS_CODE_511 = 511;

        /// <summary>
        /// Connection Status Code 598
        /// </summary>
        public static readonly int CONNECTION_STATUS_CODE_598 = 598;

        /// <summary>
        /// Connection Status Code 599
        /// </summary>
        public static readonly int CONNECTION_STATUS_CODE_599 = 599;


        /// <summary>
        /// Connection Status Message 500
        /// </summary>
        public static readonly String CONNECTION_STATUS_MESSAGE_500 = "Generic error message.";

        /// <summary>
        /// Connection Status Message 501
        /// </summary>
        public static readonly String CONNECTION_STATUS_MESSAGE_501 = "Server does not recognise method or lacks ability to fulfill.";

        /// <summary>
        /// Connection Status Message 502
        /// </summary>
        public static readonly String CONNECTION_STATUS_MESSAGE_502 = "Server received an invalid response from upstream server.";

        /// <summary>
        /// Connection Status Message 503
        /// </summary>
        public static readonly String CONNECTION_STATUS_MESSAGE_503 = "Server is currently unavailable.";

        /// <summary>
        /// Connection Status Message 504
        /// </summary>
        public static readonly String CONNECTION_STATUS_MESSAGE_504 = "Gateway did not receive response from upstream server.";

        /// <summary>
        /// Connection Status Message 505
        /// </summary>
        public static readonly String CONNECTION_STATUS_MESSAGE_505 = "Server does not support the HTTP protocol version.";

        /// <summary>
        /// Connection Status Message 506
        /// </summary>
        public static readonly String CONNECTION_STATUS_MESSAGE_506 = "Content negotiation for the request results in a circular.";

        /// <summary>
        /// Connection Status Message 507
        /// </summary>
        public static readonly String CONNECTION_STATUS_MESSAGE_507 = "Server is unable to store the representation.";

        /// <summary>
        /// Connection Status Message 508
        /// </summary>
        public static readonly String CONNECTION_STATUS_MESSAGE_508 = "Server detected an infinite loop while processing the request.";

        /// <summary>
        /// Connection Status Message 509
        /// </summary>
        public static readonly String CONNECTION_STATUS_MESSAGE_509 = "Bandwidth limit exceeded.";

        /// <summary>
        /// Connection Status Message 510
        /// </summary>
        public static readonly String CONNECTION_STATUS_MESSAGE_510 = "Further extensions to the request are required.";

        /// <summary>
        /// Connection Status Message 511
        /// </summary>
        public static readonly String CONNECTION_STATUS_MESSAGE_511 = "Client needs to authenticate to gain network access.";

        /// <summary>
        /// Connection Status Message 598
        /// </summary>
        public static readonly String CONNECTION_STATUS_MESSAGE_598 = "Network read timeout behind the proxy.";

        /// <summary>
        /// Connection Status Message 599
        /// </summary>
        public static readonly String CONNECTION_STATUS_MESSAGE_599 = "Network connect timeout behind the proxy.";

    }
}
