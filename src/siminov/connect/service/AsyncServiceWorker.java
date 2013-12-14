package siminov.connect.service;

public class AsyncServiceWorker implements IServiceWorker {

	private AsyncServiceWorkerThread asyncServiceWorkerThread = null;
	
	public AsyncServiceWorker() {
		asyncServiceWorkerThread = new AsyncServiceWorkerThread();
		asyncServiceWorkerThread.start();
	}
	
	public void process(final IService service) {
		
	}
	
	private class AsyncServiceWorkerThread extends Thread {
		
		public void run() {
			
		}
	}
	
}
