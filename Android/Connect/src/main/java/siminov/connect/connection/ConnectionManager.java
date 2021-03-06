/**
 * [SIMINOV FRAMEWORK - CONNECT]
 * Copyright [2014-2016] [Siminov Software Solution LLP|support@siminov.com]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 **/

package siminov.connect.connection;

import siminov.connect.Constants;
import siminov.connect.connection.design.IConnection;
import siminov.connect.connection.design.IConnectionRequest;
import siminov.connect.connection.design.IConnectionResponse;
import siminov.connect.exception.ConnectionException;
import siminov.connect.service.design.IService;


/**
 * It exposes API to manager connection 
 *
 */
public class ConnectionManager {

	private static ConnectionManager connectionManager = null;

	private IConnection httpConnection = null;
	private IConnection httpsConnection = null;
	
	/**
	 * ConnectionManager Construction
	 */
	private ConnectionManager() {
		
		httpConnection = new siminov.connect.connection.HttpConnectionWorker();
		httpsConnection = new siminov.connect.connection.HttpsConnectionWorker();
	}
	
	/**
	 * It provides an singleton instance of ConnectionManager class.
	 * @return ConnectionManager Instance
	 */
	public static ConnectionManager getInstance() {
		
		if(connectionManager == null) {
			connectionManager = new ConnectionManager();
		}
		
		return connectionManager;
	}
	
	
	/**
	 * It handles the service request
	 * @param service Service instance
	 * @return IConnectionResponse instance
	 * @throws ConnectionException If any exception occur while executing service request
	 */
	public IConnectionResponse handle(final IService service) throws ConnectionException {
		
		IConnectionRequest connectionRequest = ConnectionHelper.prepareConnectionRequest(service);
		
		/*
		 * Service Event onServiceApiInvoke
		 */
		service.onRequestInvoke(connectionRequest);
		
		IConnection connection = null;
		if(connectionRequest.getProtocol().equalsIgnoreCase(Constants.SERVICE_DESCRIPTOR_HTTP_PROTOCOL)) {
			connection = httpConnection;
		} else if(connectionRequest.getProtocol().equalsIgnoreCase(Constants.SERVICE_DESCRIPTOR_HTTPS_PROTOCOL)) {
			connection = httpsConnection;
		}

		
		IConnectionResponse connectionResponse = null;
		if(connectionRequest.getType().equalsIgnoreCase(Constants.SERVICE_DESCRIPTOR_REQUEST_GET_TYPE)) {
			connectionResponse = connection.get(connectionRequest);
		} else if(connectionRequest.getType().equalsIgnoreCase(Constants.SERVICE_DESCRIPTOR_REQUEST_HEAD_TYPE)) {
			connectionResponse = connection.head(connectionRequest);
		} else if(connectionRequest.getType().equalsIgnoreCase(Constants.SERVICE_DESCRIPTOR_REQUEST_POST_TYPE)) {
			connectionResponse = connection.post(connectionRequest);
		} else if(connectionRequest.getType().equalsIgnoreCase(Constants.SERVICE_DESCRIPTOR_REQUEST_PUT_TYPE)) {
			connectionResponse = connection.put(connectionRequest);
		} else if(connectionRequest.getType().equalsIgnoreCase(Constants.SERVICE_DESCRIPTOR_REQUEST_DELETE_TYPE)) {
			connectionResponse = connection.delete(connectionRequest);
		} else if(connectionRequest.getType().equalsIgnoreCase(Constants.SERVICE_DESCRIPTOR_REQUEST_TRACE_TYPE)) {
			connectionResponse = connection.trace(connectionRequest);
		} else if(connectionRequest.getType().equalsIgnoreCase(Constants.SERVICE_DESCRIPTOR_REQUEST_OPTIONS_TYPE)) {
			connectionResponse = connection.options(connectionRequest);
		} else if(connectionRequest.getType().equalsIgnoreCase(Constants.SERVICE_DESCRIPTOR_REQUEST_CONNECT_TYPE)) {
			connectionResponse = connection.connect(connectionRequest);
		} else if(connectionRequest.getType().equalsIgnoreCase(Constants.SERVICE_DESCRIPTOR_REQUEST_PATCH_TYPE)) {
			connectionResponse = connection.patch(connectionRequest);
		}

		return connectionResponse;
	}
}
