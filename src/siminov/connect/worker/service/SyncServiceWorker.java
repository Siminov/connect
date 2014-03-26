package siminov.connect.worker.service;

import siminov.connect.connection.ConnectionManager;
import siminov.connect.design.connection.IConnectionResponse;
import siminov.connect.design.service.IService;
import siminov.connect.design.service.IServiceWorker;
import siminov.connect.exception.ConnectionException;
import siminov.connect.exception.ServiceException;
import siminov.orm.log.Log;

public class SyncServiceWorker implements IServiceWorker {

	public void process(final IService service) {

		IConnectionResponse connectionResponse = null;
		
		try {
			connectionResponse = ConnectionManager.getInstance().handle(service);
		} catch(ConnectionException ce) {
			Log.loge(SyncServiceWorker.class.getName(), "process", "ConnectionException caught while invoking connection, " + ce.getMessage());
			
			service.onServiceTerminate(new ServiceException(ce.getClassName(), ce.getMethodName(), ce.getMessage()));
			return;
		}
		
		service.onServiceApiFinish(connectionResponse);
	}
}
