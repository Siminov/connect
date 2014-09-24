/** 
 * [SIMINOV FRAMEWORK]
 * Copyright [2015] [Siminov Software Solution LLP|support@siminov.com]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 **/

package siminov.connect.sync;

import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;

import siminov.connect.Constants;
import siminov.connect.IWorker;
import siminov.connect.events.ISyncEvents;
import siminov.connect.model.ServiceDescriptor;
import siminov.connect.model.ServiceDescriptor.API;
import siminov.connect.model.SyncDescriptor;
import siminov.connect.resource.Resources;
import siminov.connect.service.NameValuePair;
import siminov.connect.service.design.IService;
import siminov.connect.sync.design.ISyncRequest;
import siminov.orm.utils.ClassUtils;

public class SyncWorker implements IWorker {

	private static SyncWorker syncWorker = null;
	private SyncWorkerThread syncWorkerThread = null;
	
	private Collection<ISyncRequest> syncRequests = new ConcurrentLinkedQueue<ISyncRequest>();
	
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
			
			Iterator<ISyncRequest> requests = syncRequests.iterator();
			while(requests.hasNext()) {
				
				ISyncRequest syncRequest = requests.next();

				/*
				 * Fire Sync Started Event
				 */
				ISyncEvents syncEventHandler = resources.getSyncEventHandler();
				if(syncEventHandler != null) {
					syncEventHandler.onStart(syncRequest);
				}
				
				
				/*
				 * Process Request
				 */
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

					Iterator<NameValuePair> resources = syncRequest.getResources();
					while(resources.hasNext()) {
						serviceHandler.addResource(resources.next());
					}
					
					
					serviceHandler.invoke();
				}
				
				
				/*
				 * Fire Sync Started Event
				 */
				if(syncEventHandler != null) {
					syncEventHandler.onFinish(syncRequest);
				}
				
				
				syncRequests.remove(syncRequest);
			}
			
			
			syncWorkerThread = null;
		}
	}
	

	public void addRequest(final ISyncRequest syncRequest) {
		
		if(containRequest(syncRequest)) {
			return;
		}
		
		
		this.syncRequests.add(syncRequest);

		/*
		 * Fire Sync Queued Event
		 */
		ISyncEvents syncEventHandler = resources.getSyncEventHandler();
		if(syncEventHandler != null) {
			syncEventHandler.onQueue(syncRequest);
		}
		
		
		if(!isWorkerRunning()) {
			startWorker();
		}
	}
	
	public boolean containRequest(final ISyncRequest refreshRequest) {
		return this.syncRequests.contains(refreshRequest);
	}
	
	public void removeRequest(final ISyncRequest refreshRequest) {
		this.syncRequests.remove(refreshRequest);
	}
}
