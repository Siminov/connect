It contain events associated with notification.

## Android API: Notification Event 

```java

    public interface INotificationEvents {

        public void onRegistration(IRegistration registration);

        public void onUnregistration(IRegistration registration);

        public void onNotification(IMessage message);
	
        public void onError(NotificationException notificationException);
    }

```

## iOS API: Notification Event

```objective-c

    @protocol SIKINotificationEvents <NSObject>

    - (void)onRegistration:(id<SIKIRegistration>)registration;

    - (void)onUnregistration:(id<SIKIRegistration>)registration;

    - (void)onNotification:(id<SIKIMessage>)message;
	 
    - (void)onError:(SIKNotificationException *)notificationException;

    @end

```

## Windows API: Notification Event

```c#

    public interface INotificationEvents {

        void OnRegistration(IRegistration registration);

        void OnUnregistration(IRegistration registration);

        void OnNotification(IMessage message);
	
        void OnError(NotificationException notificationException);
    }

```


###### 1. On Registration  - [Android:onRegistration | iOS:onRegistration | Windows:OnRegistration]

This is the first method to be called when application is successfully registered with push notification platform service.


###### 2. On Unregistration - [Android:onUnregistration | iOS:onUnregistration | Windows:OnUnregistration]

This method is called when application get unregistered on the push notification platform.


###### 3. On Notification - [Android:onNotification | iOS:onNotification | Windows:OnNotification]

This method is called when application gets any message/notification from server.


###### 4. On Error - [Android:onError | iOS:onError | Windows:OnError]

This method is called if there is any error in process of registration/notification.


###### 4. On Terminate - [Android:onTerminate | iOS:onTerminate | Windows:OnTerminate]

This method is called if there is any error/exception while synchronizing data with web service.