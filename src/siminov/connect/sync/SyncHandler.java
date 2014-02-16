package siminov.connect.sync;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import siminov.connect.model.SyncDescriptor;
import siminov.connect.resource.Resources;

public class SyncHandler {

	private Resources resources = Resources.getInstance();
	
	private SyncWorker syncWorker = SyncWorker.getInstance();
	private Map<SyncRequest, Long> requestTimestamps = new HashMap<SyncRequest, Long>();

	private static SyncHandler syncHandler = null;
	
	private SyncHandler() {
		
	}
	
	public static SyncHandler getInstance() {
		
		if(syncHandler == null) {
			syncHandler = new SyncHandler();
		}
		
		return syncHandler;
	}
	
	public void handle(SyncRequest syncRequest) {

		SyncDescriptor syncDescriptor = resources.getSyncDescriptor(syncRequest.getName());
		
		Long requestTimestamp = requestTimestamps.get(syncRequest);
		if(requestTimestamp == null || requestTimestamp <= 0) {
			syncWorker.addRequest(syncRequest);
			requestTimestamps.put(syncRequest, new Long(Calendar.getInstance().get(Calendar.MILLISECOND)));

			return;
		}
		
		
		long syncInterval = syncDescriptor.getSyncInterval();
		long lastRefreshTimestamp = requestTimestamps.get(syncRequest.getName());
		long currentTimestamp = new Long(Calendar.getInstance().get(Calendar.MILLISECOND));
		
		long timeDifference = lastRefreshTimestamp + syncInterval;
		
		if(timeDifference < currentTimestamp) {
			syncWorker.addRequest(syncRequest);
			requestTimestamps.put(syncRequest, new Long(Calendar.getInstance().get(Calendar.MILLISECOND)));
		}
	}
}
