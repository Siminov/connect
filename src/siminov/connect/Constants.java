package siminov.connect;


public interface Constants {

	//Connect Descriptor
	public String CONNECT_DESCRIPTOR_FILE_NAME = "ConnectDescriptor.si.xml";
	
	public String CONNECT_DESCRIPTOR_PROPERTY_NAME = "name";
	public String CONNECT_DESCRIPTOR_PROPERTY = "property";

	public String CONNECT_DESCRIPTOR_SERVICE_DESCRIPTORS = "service-descriptors";
	public String CONNECT_DESCRIPTOR_SERVICE_DESCRIPTOR = "service-descriptor";
	
	public String SERVICE_DESCRIPTOR_APIS = "apis";
	public String SERVICE_DESCRIPTOR_API = "api";
	

	public String SERVICE_DESCRIPTOR_PROPERTY = "property";
	public String SERVICE_DESCRIPTOR_PROPERTY_NAME = "name";
	
	public String SERVICE_DESCRIPTOR_API_QUERY_PARAMETERS = "query-parameters";
	public String SERVICE_DESCRIPTOR_API_QUERY_PARAMETER = "query-parameter";
	public String SERVICE_DESCRIPTOR_API_QUERY_PARAMETER_NAME_ATTRIBUTE = "name";
	
	public String SERVICE_DESCRIPTOR_API_HEADER_PARAMETERS = "header-parameters";
	public String SERVICE_DESCRIPTOR_API_HEADER_PARAMETER = "header-parameter";
	public String SERVICE_DESCRIPTOR_API_HEADER_PARAMETER_NAME_ATTRIBUTE = "name";
	
	
	public String SERVICE_DESCRIPTOR_NAME = "name";
	public String SERVICE_DESCRIPTOR_DESCRIPTION = "description";
	public String SERVICE_DESCRIPTOR_PROTOCOL = "protocol";
	public String SERVICE_DESCRIPTOR_INSTANCE = "instance";
	public String SERVICE_DESCRIPTOR_PORT = "port";
	public String SERVICE_DESCRIPTOR_CONTEXT = "context";

	public String SERVICE_DESCRIPTOR_HTTP_PROTOCOL = "http";
	public String SERVICE_DESCRIPTOR_HTTPS_PROTOCOL = "https";
	
	
	public String SERVICE_CONNECTION_API_GET_TYPE = "GET";
	public String SERVICE_CONNECTION_API_HEAD_TYPE = "HEAD";
	public String SERVICE_CONNECTION_API_POST_TYPE = "POST";
	public String SERVICE_CONNECTION_API_PUT_TYPE = "PUT";
	public String SERVICE_CONNECTION_API_DELETE_TYPE = "DELETE";
	public String SERVICE_CONNECTION_API_TRACE_TYPE = "TRACE";
	public String SERVICE_CONNECTION_API_OPTIONS_TYPE = "OPTIONS";
	public String SERVICE_CONNECTION_API_CONNECT_TYPE = "CONNECT";
	public String SERVICE_CONNECTION_API_PATCH_TYPE = "PATCH";
	
	
	public String SERVICE_DESCRIPTOR_API_NAME = "name";
	public String SERVICE_DESCRIPTOR_API_TYPE = "type";
	public String SERVICE_DESCRIPTOR_API_API = "api";
	public String SERVICE_DESCRIPTOR_API_MODE = "mode";
	public String SERVICE_DESCRIPTOR_API_DATA_STREAM = "data-stream";
	
	public String CONNECT_LIBRARY_DESCRIPTOR_FILE_NAME = "LibraryDescriptor.si.xml";
	public String CONNECT_LIBRARY_DESCRIPTOR_PROPERTY = "property";
	
	public String CONNECT_LIBRARY_DESCRIPTOR_SERVICE_DESCRIPTOR = "service-descriptor";
	public String CONNECT_LIBRARY_DESCRIPTOR_PROPERTY_NAME = "name";

	public String CONNECT_DESCRIPTOR_LIBRARY = "library";
	public String CONNECT_DESCRIPTOR_LIBRARY_PATH = "path";


	public String SERVICE_DESCRIPTOR_API_SYNC_REQUEST_MODE = "SYNC";
	public String SERVICE_DESCRIPTOR_API_ASYNC_REQUEST_MODE = "ASYNC";
	

	
	/**
	 * Inline Resource Constants
	 */
	public String SERVICE_RESOURCE_HASH = "#";
	public String SERVICE_RESOURCE_OPEN_CURLY_BRACKET = "{";
	public String SERVICE_RESOURCE_CLOSE_CURLY_BRACKET = "}";
	public String SERVICE_RESOURCE_DOT = ".";
	
	
	public String CONNECTION_HTTP = "http";
	public String CONNECTION_HTTPS = "https";
	
	/**
		1** Informational
	*/
	public int CONNECTION_STATUS_CODE_100 = 100;
	public int CONNECTION_STATUS_CODE_101 = 101;
	public int CONNECTION_STATUS_CODE_102 = 102;
	public int CONNECTION_STATUS_CODE_103 = 103;
	public int CONNECTION_STATUS_CODE_122 = 122;
	
	public String CONNECTION_STATUS_MESSAGE_100 = "Client should continue with request.";
	public String CONNECTION_STATUS_MESSAGE_101 = "Server is switching protocols.";
	public String CONNECTION_STATUS_MESSAGE_102 = "Server has received and is processing the request.";
	public String CONNECTION_STATUS_MESSAGE_103 = "Resume aborted PUT or POST requests.";
	public String CONNECTION_STATUS_MESSAGE_122 = "URI is longer than a maximum of 2083 characters.";
	
	
	
	/**
		2** Success
	*/
	public int CONNECTION_STATUS_CODE_200 = 200;
	public int CONNECTION_STATUS_CODE_201 = 201;
	public int CONNECTION_STATUS_CODE_202 = 202;
	public int CONNECTION_STATUS_CODE_203 = 203;
	public int CONNECTION_STATUS_CODE_204 = 204;
	public int CONNECTION_STATUS_CODE_205 = 205;
	public int CONNECTION_STATUS_CODE_206 = 206;
	public int CONNECTION_STATUS_CODE_207 = 207;
	public int CONNECTION_STATUS_CODE_208 = 208;
	public int CONNECTION_STATUS_CODE_226 = 226;
	
	public String CONNECTION_STATUS_MESSAGE_200 = "Standard response for successful HTTP requests.";
	public String CONNECTION_STATUS_MESSAGE_201 = "Request has been fulfilled; new resource created.";
	public String CONNECTION_STATUS_MESSAGE_202 = "Request accepted, processing pending.";
	public String CONNECTION_STATUS_MESSAGE_203 = "Request processed, information may be from another source.";
	public String CONNECTION_STATUS_MESSAGE_204 = "Request processed, no content returned.";
	public String CONNECTION_STATUS_MESSAGE_205 = "Request processed, no content returned, reset document view.";
	public String CONNECTION_STATUS_MESSAGE_206 = "Partial resource return due to request header.";
	public String CONNECTION_STATUS_MESSAGE_207 = "XMLl, can contain multiple separate responses.";
	public String CONNECTION_STATUS_MESSAGE_208 = "Results previously returned.";
	public String CONNECTION_STATUS_MESSAGE_226 = "Request fulfilled, response is instance-manipulations.";
	
	
	
	/**
		3** Redirect
	*/
	public int CONNECTION_STATUS_CODE_300 = 300;
	public int CONNECTION_STATUS_CODE_301 = 301;
	public int CONNECTION_STATUS_CODE_302 = 302;
	public int CONNECTION_STATUS_CODE_303 = 303;
	public int CONNECTION_STATUS_CODE_304 = 304;
	public int CONNECTION_STATUS_CODE_305 = 305;
	public int CONNECTION_STATUS_CODE_306 = 306;
	public int CONNECTION_STATUS_CODE_307 = 307;
	public int CONNECTION_STATUS_CODE_308 = 308;
	
	public String CONNECTION_STATUS_MESSAGE_300 = "Multiple options for the resource delivered.";
	public String CONNECTION_STATUS_MESSAGE_301 = "This and all future requests directed to the given URI.";
	public String CONNECTION_STATUS_MESSAGE_302 = "Temporary response to request found via alternative URI.";
	public String CONNECTION_STATUS_MESSAGE_303 = "Permanent response to request found via alternative URI.";
	public String CONNECTION_STATUS_MESSAGE_304 = "Resource has not been modified since last requested.";
	public String CONNECTION_STATUS_MESSAGE_305 = "Content located elsewhere, retrieve from there.";
	public String CONNECTION_STATUS_MESSAGE_306 = "Subsequent requests should use the specified proxy.";
	public String CONNECTION_STATUS_MESSAGE_307 = "Connect again to different URI as provided.";
	public String CONNECTION_STATUS_MESSAGE_308 = "Resumable HTTP Requests.";
	
	
	
	/**
		4** Client Error
	*/
	public int CONNECTION_STATUS_CODE_400 = 400;
	public int CONNECTION_STATUS_CODE_401 = 401;
	public int CONNECTION_STATUS_CODE_402 = 402;
	public int CONNECTION_STATUS_CODE_403 = 403;
	public int CONNECTION_STATUS_CODE_404 = 404;
	public int CONNECTION_STATUS_CODE_405 = 405;
	public int CONNECTION_STATUS_CODE_406 = 406;
	public int CONNECTION_STATUS_CODE_407 = 407;
	public int CONNECTION_STATUS_CODE_408 = 408;
	public int CONNECTION_STATUS_CODE_409 = 409;
	public int CONNECTION_STATUS_CODE_410 = 410;
	public int CONNECTION_STATUS_CODE_411 = 411;
	public int CONNECTION_STATUS_CODE_412 = 412;
	public int CONNECTION_STATUS_CODE_413 = 413;
	public int CONNECTION_STATUS_CODE_414 = 414;
	public int CONNECTION_STATUS_CODE_415 = 415;
	public int CONNECTION_STATUS_CODE_416 = 416;
	public int CONNECTION_STATUS_CODE_417 = 417;
	public int CONNECTION_STATUS_CODE_418 = 418;
	public int CONNECTION_STATUS_CODE_420 = 420;
	public int CONNECTION_STATUS_CODE_422 = 422;
	public int CONNECTION_STATUS_CODE_423 = 423;
	public int CONNECTION_STATUS_CODE_424 = 424;
	public int CONNECTION_STATUS_CODE_426 = 426;
	public int CONNECTION_STATUS_CODE_428 = 428;
	public int CONNECTION_STATUS_CODE_429 = 429;
	public int CONNECTION_STATUS_CODE_431 = 431;
	public int CONNECTION_STATUS_CODE_444 = 444;
	public int CONNECTION_STATUS_CODE_449 = 449;
	public int CONNECTION_STATUS_CODE_450 = 450;
	public int CONNECTION_STATUS_CODE_451 = 451;
	public int CONNECTION_STATUS_CODE_499 = 499;
	
	public String CONNECTION_STATUS_MESSAGE_400 = "Request cannot be fulfilled due to bad syntax.";
	public String CONNECTION_STATUS_MESSAGE_401 = "Authentication is possible but has failed.";
	public String CONNECTION_STATUS_MESSAGE_402 = "Payment required, reserved for future use.";
	public String CONNECTION_STATUS_MESSAGE_403 = "Server refuses to respond to request.";
	public String CONNECTION_STATUS_MESSAGE_404 = "Requested resource could not be found.";
	public String CONNECTION_STATUS_MESSAGE_405 = "Request method not supported by that resource.";
	public String CONNECTION_STATUS_MESSAGE_406 = "Content not acceptable according to the Accept headers.";
	public String CONNECTION_STATUS_MESSAGE_407 = "Client must first authenticate itself with the proxy.";
	public String CONNECTION_STATUS_MESSAGE_408 = "Server timed out waiting for the request.";
	public String CONNECTION_STATUS_MESSAGE_409 = "Request could not be processed because of conflict.";
	public String CONNECTION_STATUS_MESSAGE_410 = "Resource is no longer available and will not be available again.";
	public String CONNECTION_STATUS_MESSAGE_411 = "Request did not specify the length of its content.";
	public String CONNECTION_STATUS_MESSAGE_412 = "Server does not meet request preconditions.";
	public String CONNECTION_STATUS_MESSAGE_413 = "Request is larger than the server is willing or able to process.";
	public String CONNECTION_STATUS_MESSAGE_414 = "URI provided was too long for the server to process.";
	public String CONNECTION_STATUS_MESSAGE_415 = "Server does not support media type.";
	public String CONNECTION_STATUS_MESSAGE_416 = "Client has asked for unprovidable portion of the file.";
	public String CONNECTION_STATUS_MESSAGE_417 = "Server cannot meet requirements of Expect request.";
	public String CONNECTION_STATUS_MESSAGE_418 = "I'm a teapot.";
	public String CONNECTION_STATUS_MESSAGE_420 = "Twitter rate limiting.";
	public String CONNECTION_STATUS_MESSAGE_422 = "Request unable to be followed due to semantic errors.";
	public String CONNECTION_STATUS_MESSAGE_423 = "Resource that is being accessed is locked.";
	public String CONNECTION_STATUS_MESSAGE_424 = "Request failed due to failure of a previous request.";
	public String CONNECTION_STATUS_MESSAGE_426 = "Client should switch to a different protocol.";
	public String CONNECTION_STATUS_MESSAGE_428 = "Origin server requires the request to be conditional.";
	public String CONNECTION_STATUS_MESSAGE_429 = "User has sent too many requests in a given amount of.";
	public String CONNECTION_STATUS_MESSAGE_431 = "Server is unwilling to process the request.";
	public String CONNECTION_STATUS_MESSAGE_444 = "Server returns no information and closes the connection.";
	public String CONNECTION_STATUS_MESSAGE_449 = "Request should be retried after performing action.";
	public String CONNECTION_STATUS_MESSAGE_450 = "Windows Parental Controls blocking access to webpage.";
	public String CONNECTION_STATUS_MESSAGE_451 = "The server cannot reach the client's mailbox.";
	public String CONNECTION_STATUS_MESSAGE_499 = "Connection closed by client while HTTP server is processing.";
	
	
	
	/**
		5** Server Error
	*/	
	public int CONNECTION_STATUS_CODE_500 = 500;
	public int CONNECTION_STATUS_CODE_501 = 501;
	public int CONNECTION_STATUS_CODE_502 = 502;
	public int CONNECTION_STATUS_CODE_503 = 503;
	public int CONNECTION_STATUS_CODE_504 = 504;
	public int CONNECTION_STATUS_CODE_505 = 505;
	public int CONNECTION_STATUS_CODE_506 = 506;
	public int CONNECTION_STATUS_CODE_507 = 507;
	public int CONNECTION_STATUS_CODE_508 = 508;
	public int CONNECTION_STATUS_CODE_509 = 509;
	public int CONNECTION_STATUS_CODE_510 = 510;
	public int CONNECTION_STATUS_CODE_511 = 511;
	public int CONNECTION_STATUS_CODE_598 = 598;
	public int CONNECTION_STATUS_CODE_599 = 599;
	
	public String CONNECTION_STATUS_MESSAGE_500 = "Generic error message.";
	public String CONNECTION_STATUS_MESSAGE_501 = "Server does not recognise method or lacks ability to fulfill.";
	public String CONNECTION_STATUS_MESSAGE_502 = "Server received an invalid response from upstream server.";
	public String CONNECTION_STATUS_MESSAGE_503 = "Server is currently unavailable.";
	public String CONNECTION_STATUS_MESSAGE_504 = "Gateway did not receive response from upstream server.";
	public String CONNECTION_STATUS_MESSAGE_505 = "Server does not support the HTTP protocol version.";
	public String CONNECTION_STATUS_MESSAGE_506 = "Content negotiation for the request results in a circular.";
	public String CONNECTION_STATUS_MESSAGE_507 = "Server is unable to store the representation.";
	public String CONNECTION_STATUS_MESSAGE_508 = "Server detected an infinite loop while processing the request.";
	public String CONNECTION_STATUS_MESSAGE_509 = "Bandwidth limit exceeded.";
	public String CONNECTION_STATUS_MESSAGE_510 = "Further extensions to the request are required.";
	public String CONNECTION_STATUS_MESSAGE_511 = "Client needs to authenticate to gain network access.";
	public String CONNECTION_STATUS_MESSAGE_598 = "Network read timeout behind the proxy.";
	public String CONNECTION_STATUS_MESSAGE_599 = "Network connect timeout behind the proxy.";
	
}
