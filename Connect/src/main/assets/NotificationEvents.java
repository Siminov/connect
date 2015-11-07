
- onRegistration
- onUnregistration
- onNotification
- onError



PUSH NOTIFICATION:
	Push, or server push, describes a style of Internet-based communication where the request for a given transaction is initiated by the publisher or central server.
	
	Connect provides push notification API through which you can watch for changes to resources. 
	
	1. OnRegistration: This is the first method to be called when application is successfully registered with push notification platform service.
	
	2. OnUnregistration: This method is called when application get unregistered on the push notification platform.
	
	3. OnNotification: This method is called when application gets any message/notification from server.
	
	4. OnError: This method is called if there is any error in process of registration/notification.