package siminov.connect.events;

import siminov.connect.connection.ConnectionRequest;
import siminov.connect.connection.ConnectionResponse;
import siminov.orm.exception.SiminovException;

public interface IServiceEvents {

	public void onServiceStart();
	
	
	public void onServiceQueue();
	
	
	public void onServicePause();
	
	
	public void onServiceResume();
	
	
	public void onServiceFinish();
	
	
	public void onServiceApiInvoke(final ConnectionRequest connectionRequest);
	
	
	public void onServiceApiFinish(final ConnectionResponse connectionResponse);

	
	public void onServiceTerminate(final SiminovException siminovException);
	
}
