package siminov.connect.events;

import siminov.connect.design.sync.ISyncRequest;

public interface ISyncEvents {

	public void onSyncStarted(ISyncRequest syncRequest);

	
	public void onSyncQueued(ISyncRequest syncRequest);

	
	public void onSyncRemoved(ISyncRequest syncRequest);

	
	public void onSyncTerminated(ISyncRequest syncRequest);
}
