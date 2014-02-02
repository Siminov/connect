package siminov.connect.refresh;

import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;

import siminov.connect.Constants;
import siminov.connect.IWorker;
import siminov.connect.model.RefreshDescriptor;
import siminov.connect.model.ServiceDescriptor;
import siminov.connect.model.ServiceDescriptor.API;
import siminov.connect.resource.Resources;
import siminov.connect.service.design.IService;
import siminov.orm.utils.ClassUtils;

public class RefreshWorker implements IWorker {

	private static RefreshWorker refreshWorker = null;
	private RefreshWorkerThread refreshWorkerThread = null;
	
	private Collection<RefreshRequest> refreshRequests = new ConcurrentLinkedQueue<RefreshRequest>();
	
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
			
			Iterator<RefreshRequest> requests = refreshRequests.iterator();
			while(requests.hasNext()) {
				
				RefreshRequest refreshRequest = requests.next();
				RefreshDescriptor refreshDescriptor = resources.getRefreshDescriptor(refreshRequest.getName());
				
				Iterator<String> services = refreshDescriptor.getServices();
				while(services.hasNext()) {
					
					String service = services.next();
					
					String serviceName = service.substring(0, service.indexOf(Constants.REFRESH_DESCRIPTOR_SERVICE_SEPARATOR));
					String apiName = service.substring(service.indexOf(Constants.REFRESH_DESCRIPTOR_SERVICE_SEPARATOR) + 1, service.length());

					
					ServiceDescriptor serviceDescriptor = resources.requiredServiceDescriptorBasedOnName(serviceName);
					API api = serviceDescriptor.getApi(apiName);
					
					String apiHandler = api.getHandler();
					
					IService serviceHandler = (IService) ClassUtils.createClassInstance(apiHandler);
					serviceHandler.setServiceDescriptor(serviceDescriptor);

					Iterator<String> inlineResources = refreshRequest.getInlineResources();
					while(inlineResources.hasNext()) {
						String inlineResource = inlineResources.next();
						String inlineResourceValue = refreshRequest.getInlineResource(inlineResource);
						
						serviceHandler.addInlineResource(inlineResource, inlineResourceValue);
					}
					
					
					serviceHandler.invoke();
				}
				
				
				refreshRequests.remove(refreshRequest);
			}
			
			
			refreshWorkerThread = null;
		}
	}
	

	public void addRequest(final RefreshRequest refreshRequest) {
		
		if(containRequest(refreshRequest)) {
			return;
		}
		
		
		this.refreshRequests.add(refreshRequest);
	
		if(!isWorkerRunning()) {
			startWorker();
		}
	}
	
	public boolean containRequest(final RefreshRequest refreshRequest) {
		return this.refreshRequests.contains(refreshRequest);
	}
	
	public void removeRequest(final RefreshRequest refreshRequest) {
		this.refreshRequests.remove(refreshRequest);
	}
}
