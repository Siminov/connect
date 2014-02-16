package siminov.connect.sync;

import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;

import siminov.connect.Constants;
import siminov.connect.model.SyncDescriptor;
import siminov.connect.model.ServiceDescriptor;
import siminov.connect.model.ServiceDescriptor.API;
import siminov.connect.resource.Resources;
import siminov.connect.service.design.IService;
import siminov.connect.worker.IWorker;
import siminov.orm.utils.ClassUtils;

public class SyncWorker implements IWorker {

	private static SyncWorker syncWorker = null;
	private SyncWorkerThread syncWorkerThread = null;
	
	private Collection<SyncRequest> syncRequests = new ConcurrentLinkedQueue<SyncRequest>();
	
	private Resources resources = Resources.getInstance();
	
	private SyncWorker() {
		
	}

	public static SyncWorker getInstance() {
		
		if(syncWorker == null) {
			syncWorker = new SyncWorker();
		}
		
		return syncWorker;
	}
	
	
	public void startWorker() {
		
		if(syncWorkerThread == null) {
			syncWorkerThread = new SyncWorkerThread();
		}
		
		syncWorkerThread.start();
	}
	
	public void stopWorker() {
		
		if(syncWorkerThread == null) {
			return;
		}
		
		
		if(!syncWorkerThread.isAlive()) {
			syncWorkerThread.start();
		}
	}
	
	public boolean isWorkerRunning() {
		
		if(syncWorkerThread == null) {
			return false;
		}
		
		return syncWorkerThread.isAlive();
	}
	
	
	private class SyncWorkerThread extends Thread {
		
		public void run() {
			
			Iterator<SyncRequest> requests = syncRequests.iterator();
			while(requests.hasNext()) {
				
				SyncRequest syncRequest = requests.next();
				SyncDescriptor refreshDescriptor = resources.getSyncDescriptor(syncRequest.getName());
				
				Iterator<String> services = refreshDescriptor.getServices();
				while(services.hasNext()) {
					
					String service = services.next();
					
					String serviceName = service.substring(0, service.indexOf(Constants.SYNC_DESCRIPTOR_SERVICE_SEPARATOR));
					String apiName = service.substring(service.indexOf(Constants.SYNC_DESCRIPTOR_SERVICE_SEPARATOR) + 1, service.length());

					
					ServiceDescriptor serviceDescriptor = resources.requiredServiceDescriptorBasedOnName(serviceName);
					API api = serviceDescriptor.getApi(apiName);
					
					String apiHandler = api.getHandler();
					
					IService serviceHandler = (IService) ClassUtils.createClassInstance(apiHandler);
					serviceHandler.setServiceDescriptor(serviceDescriptor);

					Iterator<String> inlineResources = syncRequest.getInlineResources();
					while(inlineResources.hasNext()) {
						String inlineResource = inlineResources.next();
						String inlineResourceValue = syncRequest.getInlineResource(inlineResource);
						
						serviceHandler.addInlineResource(inlineResource, inlineResourceValue);
					}
					
					
					serviceHandler.invoke();
				}
				
				
				syncRequests.remove(syncRequest);
			}
			
			
			syncWorkerThread = null;
		}
	}
	

	public void addRequest(final SyncRequest refreshRequest) {
		
		if(containRequest(refreshRequest)) {
			return;
		}
		
		
		this.syncRequests.add(refreshRequest);
	
		if(!isWorkerRunning()) {
			startWorker();
		}
	}
	
	public boolean containRequest(final SyncRequest refreshRequest) {
		return this.syncRequests.contains(refreshRequest);
	}
	
	public void removeRequest(final SyncRequest refreshRequest) {
		this.syncRequests.remove(refreshRequest);
	}
}
