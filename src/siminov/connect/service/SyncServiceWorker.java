package siminov.connect.service;

import siminov.connect.connection.ConnectionManager;
import siminov.connect.connection.design.IConnectionResponse;
import siminov.connect.exception.ConnectionException;
import siminov.connect.exception.ServiceException;
import siminov.connect.service.design.IService;
import siminov.connect.service.design.IServiceWorker;
import siminov.orm.log.Log;

public class SyncServiceWorker implements IServiceWorker {

	public void process(final IService service) {

		IConnectionResponse connectionResponse = null;
		
		try {
			connectionResponse = ConnectionManager.getInstance().handle(service);
		} catch(ConnectionException ce) {
			Log.error(SyncServiceWorker.class.getName(), "process", "ConnectionException caught while invoking connection, " + ce.getMessage());
			
			service.onTerminate(new ServiceException(ce.getClassName(), ce.getMethodName(), ce.getMessage()));
			return;
		}
		
		service.onApiFinish(connectionResponse);
	}
}
