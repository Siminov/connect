package siminov.connect.refresh;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import siminov.connect.model.RefreshDescriptor;
import siminov.connect.resource.Resources;

public class RefreshHandler {

	private Resources resources = Resources.getInstance();
	
	private RefreshWorker refreshWorker = RefreshWorker.getInstance();
	private Map<RefreshRequest, Long> requestTimestamps = new HashMap<RefreshRequest, Long>();

	private static RefreshHandler refreshHandler = null;
	
	private RefreshHandler() {
		
	}
	
	public static RefreshHandler getInstance() {
		
		if(refreshHandler == null) {
			refreshHandler = new RefreshHandler();
		}
		
		return refreshHandler;
	}
	
	public void handle(RefreshRequest refreshRequest) {

		RefreshDescriptor refreshDescriptor = resources.getRefreshDescriptor(refreshRequest.getName());
		
		Long requestTimestamp = requestTimestamps.get(refreshRequest);
		if(requestTimestamp == null || requestTimestamp <= 0) {
			refreshWorker.addRequest(refreshRequest);
			requestTimestamps.put(refreshRequest, new Long(Calendar.getInstance().get(Calendar.MILLISECOND)));

			return;
		}
		
		
		long refreshInterval = refreshDescriptor.getRefreshInterval();
		long lastRefreshTimestamp = requestTimestamps.get(refreshRequest.getName());
		long currentTimestamp = new Long(Calendar.getInstance().get(Calendar.MILLISECOND));
		
		long timeDifference = lastRefreshTimestamp + refreshInterval;
		
		if(timeDifference < currentTimestamp) {
			refreshWorker.addRequest(refreshRequest);
			requestTimestamps.put(refreshRequest, new Long(Calendar.getInstance().get(Calendar.MILLISECOND)));
		}
	}
}
