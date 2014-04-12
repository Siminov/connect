package siminov.connect.connection;

import siminov.connect.Constants;
import siminov.connect.design.connection.IConnection;
import siminov.connect.design.connection.IConnectionRequest;
import siminov.connect.design.connection.IConnectionResponse;
import siminov.connect.design.service.IService;
import siminov.connect.exception.ConnectionException;

public class ConnectionManager {

	private static ConnectionManager connectionManager = null;

	private IConnection httpConnection = null;
	private IConnection httpsConnection = null;
	
	private ConnectionManager() {
		
		httpConnection = new siminov.connect.worker.connection.HttpConnectionWorker();
		httpsConnection = new siminov.connect.worker.connection.HttpsConnectionWorker();
	}
	
	public static ConnectionManager getInstance() {
		
		if(connectionManager == null) {
			connectionManager = new ConnectionManager();
		}
		
		return connectionManager;
	}
	
	public IConnectionResponse handle(final IService service) throws ConnectionException {
		
		IConnectionRequest connectionRequest = ConnectionHelper.prepareConnectionRequest(service);
		
		/*
		 * Service Event onServiceApiInvoke
		 */
		service.onServiceApiInvoke(connectionRequest);
		
		IConnection connection = null;
		if(connectionRequest.getProtocol().equalsIgnoreCase(Constants.SERVICE_DESCRIPTOR_HTTP_PROTOCOL)) {
			connection = httpConnection;
		} else if(connectionRequest.getProtocol().equalsIgnoreCase(Constants.SERVICE_DESCRIPTOR_HTTPS_PROTOCOL)) {
			connection = httpsConnection;
		}

		
		IConnectionResponse connectionResponse = null;
		if(connectionRequest.getType().equalsIgnoreCase(Constants.SERVICE_CONNECTION_API_GET_TYPE)) {
			connectionResponse = connection.get(connectionRequest);
		} else if(connectionRequest.getType().equalsIgnoreCase(Constants.SERVICE_CONNECTION_API_HEAD_TYPE)) {
			connectionResponse = connection.head(connectionRequest);
		} else if(connectionRequest.getType().equalsIgnoreCase(Constants.SERVICE_CONNECTION_API_POST_TYPE)) {
			connectionResponse = connection.post(connectionRequest);
		} else if(connectionRequest.getType().equalsIgnoreCase(Constants.SERVICE_CONNECTION_API_PUT_TYPE)) {
			connectionResponse = connection.put(connectionRequest);
		} else if(connectionRequest.getType().equalsIgnoreCase(Constants.SERVICE_CONNECTION_API_DELETE_TYPE)) {
			connectionResponse = connection.delete(connectionRequest);
		} else if(connectionRequest.getType().equalsIgnoreCase(Constants.SERVICE_CONNECTION_API_TRACE_TYPE)) {
			connectionResponse = connection.trace(connectionRequest);
		} else if(connectionRequest.getType().equalsIgnoreCase(Constants.SERVICE_CONNECTION_API_OPTIONS_TYPE)) {
			connectionResponse = connection.options(connectionRequest);
		} else if(connectionRequest.getType().equalsIgnoreCase(Constants.SERVICE_CONNECTION_API_CONNECT_TYPE)) {
			connectionResponse = connection.connect(connectionRequest);
		} else if(connectionRequest.getType().equalsIgnoreCase(Constants.SERVICE_CONNECTION_API_PATCH_TYPE)) {
			connectionResponse = connection.patch(connectionRequest);
		}

		return connectionResponse;
	}
}