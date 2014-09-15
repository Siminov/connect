package siminov.connect.events;

import siminov.connect.sync.design.ISyncRequest;

public interface ISyncEvents {

	public void onStart(ISyncRequest syncRequest);

	
	public void onQueue(ISyncRequest syncRequest);

	
	public void onFinish(ISyncRequest syncRequest);

	
	public void onTerminate(ISyncRequest syncRequest);
}
