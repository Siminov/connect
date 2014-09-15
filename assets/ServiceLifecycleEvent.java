

- onStart()

- onQueue()
- onPause()
- onResume()

- onFinish()

- onApiInvoke(ConnectionRequest)
- onApiFinish(ConnectionResponse)

- onTerminate(SiminovException)
- onRestart()



SERVICE 
An Service represents a single service worker. If is an Connect component that can preform RESTful web service call in the background and does not provide a user interface.
A Service is bind to a single RESTful web service API. 


SERVICE LIFECYCLE
Services are a fundamental building block of Connect Framework and they can exist in a number of different states. 
The service lifecycle begins with instantiation and ends with destruction, and includes many states in between. When a service changes state, the appropriate lifecycle 
event method is called, notifing the service of the implending state change and allowing it to execute code in order to adapt to that change. 

Within the lifecycle callback methods, you can declare how your service behaves when their is any event on the service. 

	
	OVERVIEW
		Services are an unusual programming concept specific to Siminov Connect. The service lifecycle is implemented as a collection of methods the application calls 
		throughout the lifecycle of an service. These methods allow developers to implement the functionality that is necessary to satisfy the state and resource management requirements of their applications.
		
		It is extremely important for the application developer to analyze the requirements of each activity to determine which methods exposed by the activity lifecycle need to be implemented. 
		Failure to do this can result in application instability, inconsistant data.
		
		
	SERVICE LIFECYCLE
		The Service lifecycle comprises a collection of methods exposed within the Service class that provide the developer with a resource management framework. 
		This framework allows developers to meet the unique state management requirements of each service within an application and properly handle resource management.
		
		
		SERVICE STATES
							
																SERIVCE LIFECYCLE DIAGRAM
			
			These states can be broken into 4 main group as follows:
				
				1. Active or Running - Services are considered action or running if they are executing or processing.
				
				2. Paused - When the device goes to sleep, or there is no network communication, the service is considered paused. 
						Paused services are still alive, that is, they maintain all state and member information, and remain attached to the application. 
				
				3. Stopped/Background - 
				
				4. Resume - It is possible for a service that is anywhere from paused to stopped in the lifecycle to be removed from memory by application. 
			
				
				
			SERVICE LIFECYCLE METHODS
			
																SERVICE LIFECYCLE METHODS DIAGRAM
					
				As a developer, you can handle state changes by overriding these methods within a service. It’s important to note, however, 
				that all lifecycle methods may be called on the UI thread/Non UI thread and may block the OS from performing the next piece of UI work, 
				such as hiding the current screen, displaying a new screen, etc. As such, code in these methods should be as brief as possible to make an application feel well performing. 
				Any long-running tasks should be executed in ASYNC Service mode.
																
				1. onStart: This is the first method to be called when a service is created. 
						OnStart is always overridden to perform any startup initializations that may be required by a Service such as:

						- Initializing variables
						- Binding static data to service
						- Binding related screen to service
				
						Once OnStart has finished, Connect will call OnServiceQueue if Service is in ASYNC mode else OnServiceApiInvoke.	
						
					
				2. onQueue: 
								
				
				3. onPause: This method is called when there is no network. Services should override this method if they need to:
						
						- Commit unsaved changes to persistent data
						- Destroy or clean up other objects consuming resources
						- Display any relevant alerts or dialogs
					
				4. onResume: The Connect calls this method when the Service is ready to start executing. 
						Services should override this method to perform tasks such as:		
							
						- Display any relevant alerts or dialogs
						- Wire up external event handlers
						- Listening for GPS updates
				
				5. onFinish: This is the final method that is called on a Service instance before it’s destroyed and completely removed from memory.
						There will be no lifecycle methods called after the Activity has been destroyed.
				
				
				6. onApiInvoke(ConnectionRequest): This method is called before Service calls Web Service API. 
				
				
				7. onApiFinish(ConnectionResponse): This method is called after Web Service API is executed.
				
				
				8. onTerminate(SiminovException): This method is called when there is any exception while executing the service. 
						Once this is called the service will be terminated and release from the memory.
				
				
				9. invoke(): 
					
					
				10. finish();	
						