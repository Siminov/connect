package siminov.connect.service.worker;

import siminov.connect.connection.ConnectionHelper;
import siminov.connect.connection.ConnectionManager;
import siminov.connect.connection.ConnectionRequest;
import siminov.connect.connection.ConnectionResponse;
import siminov.connect.exception.ConnectionException;
import siminov.connect.service.design.IService;
import siminov.connect.service.design.IServiceWorker;
import siminov.orm.exception.SiminovException;
import siminov.orm.log.Log;

public class SyncServiceWorker implements IServiceWorker {

	public void process(final IService service) {

		ConnectionRequest connectionRequest = ConnectionHelper.prepareConnectionRequest(service);
		
		/*
		 * Service Event onServiceApiInvoke
		 */
		service.onServiceApiInvoke(connectionRequest);
		ConnectionResponse connectionResponse = null;
		
		try {
			connectionResponse = ConnectionManager.getInstance().handle(connectionRequest);
		} catch(ConnectionException se) {
			Log.loge(SyncServiceWorker.class.getName(), "process", "SiminovException caught while invoking connection, " + se.getMessage());
			
			service.onServiceTerminate(new SiminovException(se.getClassName(), se.getMethodName(), se.getMessage()));
			return;
		}
		
		service.onServiceApiFinish(connectionResponse);
	}
}
