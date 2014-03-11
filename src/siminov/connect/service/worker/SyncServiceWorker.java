package siminov.connect.service.worker;

import siminov.connect.connection.ConnectionManager;
import siminov.connect.connection.ConnectionResponse;
import siminov.connect.exception.ConnectionException;
import siminov.connect.exception.ServiceException;
import siminov.connect.service.design.IService;
import siminov.connect.service.design.IServiceWorker;
import siminov.orm.log.Log;

public class SyncServiceWorker implements IServiceWorker {

	public void process(final IService service) {

		ConnectionResponse connectionResponse = null;
		
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
