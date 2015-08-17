**ISync** It contain events associated with sync operations.

## Android API: Sync Event

```java

    public interface ISyncEvents {

        public void onStart(ISyncRequest syncRequest);

        public void onQueue(ISyncRequest syncRequest);

        public void onFinish(ISyncRequest syncRequest);

        public void onTerminate(ISyncRequest syncRequest);
    }
```

## iOS API: Sync Event

```objective-c

    @implementation DatabaseEventHandler

        - (void)onStart:(SIKISyncRequest * const)syncRequest;

        - (void)onQueue:(SIKISyncRequest * const)syncRequest;

        - (void)onFinish:(SIKISyncRequest * const)syncRequest;

        - (void)onTerminate:(SIKISyncRequest * const)syncRequest;
    
    @end

```

## Windows API: Sync Event

```c#

    public interface ISyncEvents {

        void OnStart(ISyncRequest syncRequest);

        void OnQueue(ISyncRequest syncRequest);

        void OnFinish(ISyncRequest syncRequest);

        void OnTerminate(ISyncRequest syncRequest);
    }
```


###### 1. On Start  - [Android:onStart | iOS:onStart | Windows:OnStart]

This method is called then a Sync is started. In this you can initialize resources related to Sync. Once OnStart has finished, Connect will call OnQueue.


###### 2. On Queue - [Android:onQueue | iOS:onQueue | Windows:OnQueue]

This method is called then the Sync request is added to the Queue.


###### 3. On Finish - [Android:onFinish | iOS:onFinish | Windows:OnFinish]

This method is called then Sync request completes its all synchronization data with web service.


###### 4. On Terminate - [Android:onTerminate | iOS:onTerminate | Windows:OnTerminate]

This method is called if there is any error/exception while synchronizing data with web service.