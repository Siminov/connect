package siminov.connect.service.design;

import siminov.connect.connection.design.IConnectionRequest;
import siminov.connect.connection.design.IConnectionResponse;
import siminov.connect.exception.ServiceException;

public interface IServiceEvents {

	public void onServiceStart();
	
	
	public void onServiceQueue();
	
	
	public void onServicePause();
	
	
	public void onServiceResume();
	
	
	public void onServiceFinish();
	
	
	public void onServiceApiInvoke(final IConnectionRequest connectionRequest);
	
	
	public void onServiceApiFinish(final IConnectionResponse connectionResponse);

	
	public void onServiceTerminate(final ServiceException serviceException);
}
