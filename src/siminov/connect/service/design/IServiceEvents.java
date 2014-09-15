package siminov.connect.service.design;

import siminov.connect.connection.design.IConnectionRequest;
import siminov.connect.connection.design.IConnectionResponse;
import siminov.connect.exception.ServiceException;

public interface IServiceEvents {

	public void onStart();
	
	
	public void onQueue();
	
	
	public void onPause();
	
	
	public void onResume();
	
	
	public void onFinish();
	
	
	public void onApiInvoke(final IConnectionRequest connectionRequest);
	
	
	public void onApiFinish(final IConnectionResponse connectionResponse);

	
	public void onTerminate(final ServiceException serviceException);
	
	
	public void onRestart();
}
