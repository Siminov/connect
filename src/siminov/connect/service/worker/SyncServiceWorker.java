package siminov.connect.service.worker;

import siminov.connect.connection.ConnectionHelper;
import siminov.connect.connection.ConnectionRequest;
import siminov.connect.connection.ConnectionResponse;
import siminov.connect.service.IService;
import siminov.connect.service.IServiceWorker;
import siminov.connect.service.ServiceHandler;
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
			connectionResponse = ServiceHandler.getInstance().invokeConnection(connectionRequest);
		} catch(SiminovException se) {
			Log.loge(SyncServiceWorker.class.getName(), "process", "SiminovException caught while invoking connection, " + se.getMessage());
			
			service.onServiceTerminate(se);
			return;
		}
		
		service.onServiceApiFinish(connectionResponse);
	}
}