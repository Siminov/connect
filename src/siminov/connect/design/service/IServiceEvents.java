package siminov.connect.design.service;

import siminov.connect.design.connection.IConnectionRequest;
import siminov.connect.design.connection.IConnectionResponse;
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
