It exposes APIs to Get and Set sync exception information.

#### Android Sample: Sync Exception

```java

    public class SyncException extends Exception {

        public String getClassName() { }
        public void setClassName(String className) { }

        public String getMethodName() { }
        public void setMethodName(String methodName) { }

        public String getMessage() { }
        public void setMessage(String message) { }

    }

```

#### iOS Sample: Sync Exception

```objective-c

    @interface SIKSyncException : SICSiminovException

    - (id)initWithClassName:(NSString * const)classname methodName:(NSString * const)methodname message:(NSString * const)exceptionmessage;

    @end

```

#### Windows Sample: Sync Exception

```c#

    public class SyncException : System.Exception {

        public String GetClassName() { }
        public void SetClassName(String className) { }

        public String GetMethodName() { }
        public void SetMethodName(String methodName) { }

        public String GetMessage() { }
        public void SetMessage(String message) { }

    }

```