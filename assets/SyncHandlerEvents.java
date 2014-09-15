
- onStart
- onQueue
- onFinish
- onTerminate


SYNC 
	Using Sync mechanism your app can synchronize data in a specific interval duration with web services. 
	
	Below are the events triggered in a life cycle of Sync
	
		1. OnStart: This method is called then a Sync is started. 
				In this you can initialize resources related to Sync.
		
				Once OnStart has finished, Connect will call OnQueue.
		
		2. OnQueue: This method is called then the Sync request is added to the Queue.
			
			
		3. OnFinish: This method is called then Sync request completes its all synchronization data with web service.
		 
			
		4. OnTerminate: This method is called if there is any error/exception while synchronizing data with web service.