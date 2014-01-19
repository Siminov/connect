package siminov.connect.refresh;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import siminov.connect.Constants;
import siminov.connect.IWorker;
import siminov.connect.model.RefreshDescriptor;
import siminov.connect.resource.Resources;

public class RefreshWorker implements IWorker {

	private static RefreshWorker refreshWorker = null;
	private RefreshWorkerThread refreshWorkerThread = null;
	
	private Map<String, RefreshRequest> refreshRequests = new HashMap<String, RefreshRequest>();
	
	private Resources resources = Resources.getInstance();
	
	private RefreshWorker() {
		
	}

	public static RefreshWorker getInstance() {
		
		if(refreshWorker == null) {
			refreshWorker = new RefreshWorker();
		}
		
		return refreshWorker;
	}
	
	
	public void startWorker() {
		
		if(refreshWorkerThread == null) {
			refreshWorkerThread = new RefreshWorkerThread();
		}
		
		refreshWorkerThread.start();
	}
	
	public void stopWorker() {
		
		if(refreshWorkerThread == null) {
			return;
		}
		
		
		if(!refreshWorkerThread.isAlive()) {
			refreshWorkerThread.start();
		}
	}
	
	public boolean isWorkerRunning() {
		
		if(refreshWorkerThread == null) {
			return false;
		}
		
		return refreshWorkerThread.isAlive();
	}
	
	
	private class RefreshWorkerThread extends Thread {
		
		public void run() {
			
			Iterator<String> requestKeys = refreshRequests.keySet().iterator();
			while(requestKeys.hasNext()) {
				
				RefreshRequest refreshRequest = refreshRequests.get(requestKeys.next());
				RefreshDescriptor refreshDescriptor = resources.getRefreshDescriptor(refreshRequest.getName());
				
				Iterator<String> services = refreshDescriptor.getServices();
				while(services.hasNext()) {
					
					String service = services.next();
					
					String serviceName = service.substring(0, service.indexOf(Constants.REFRESH_DESCRIPTOR_SERVICE_SEPARATOR));
					String apiName = service.substring(service.indexOf(Constants.REFRESH_DESCRIPTOR_SERVICE_SEPARATOR) + 1, service.length());
					
				}
			}
		}
	}
	

	public void addRequest(final RefreshRequest refreshRequest) {
		
		if(containRequest(refreshRequest)) {
			return;
		}
		
		
		this.refreshRequests.put(refreshRequest.getName(), refreshRequest);
	
		if(!isWorkerRunning()) {
			startWorker();
		}
	}
	
	public boolean containRequest(final RefreshRequest refreshRequest) {
		return this.refreshRequests.containsKey(refreshRequest.getName());
	}
	
	public void removeRequest(final RefreshRequest refreshRequest) {
		this.refreshRequests.remove(refreshRequest).getName();
	}
}
