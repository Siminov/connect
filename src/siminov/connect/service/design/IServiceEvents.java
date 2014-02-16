package siminov.connect.service.design;

import siminov.connect.connection.ConnectionRequest;
import siminov.connect.connection.ConnectionResponse;
import siminov.connect.exception.ServiceException;

public interface IServiceEvents {

	public void onServiceStart();
	
	
	public void onServiceQueue();
	
	
	public void onServicePause();
	
	
	public void onServiceResume();
	
	
	public void onServiceFinish();
	
	
	public void onServiceApiInvoke(final ConnectionRequest connectionRequest);
	
	
	public void onServiceApiFinish(final ConnectionResponse connectionResponse);

	
	public void onServiceTerminate(final ServiceException serviceException);
}
