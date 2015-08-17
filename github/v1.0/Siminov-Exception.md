This is a general exception, which is thrown through basic Siminov API, if any exception occur while performing any tasks.

#### Android API: Siminov Exception

```java

    public class SiminovException extends Exception {

        public String getClassName() { }
        public void setClassName(String className) { }

        public String getMethodName() { }
        public void setMethodName(String methodName) { }

        public String getMessage() { }
        public void setMessage(String message) { }

    }

```

#### iOS API: Siminov Exception

```objective-c

    @interface SICSiminovException: NSException <SICIException> {
        NSString *className;
        NSString *methodName;
        NSString *message;
    }

    - (id)initWithClassName:(NSString *)classname methodName:(NSString *)methodname message:(NSString *)exceptionmessage;

    - (NSString *)getClassName;

    - (void)setClassName:(NSString * const)className;

    - (NSString *)getMethodName;

    - (void)setMethodName:(NSString * const)methodName;

    - (NSString *)getMessage;

    - (void)setMessage:(NSString * const)message;

    @end

```


#### Windows API: Siminov Exception

```c#

    public class SiminovException : System.Exception {

        public String GetClassName() { }
        public void SetClassName(String className) { }

        public String GetMethodName() { }
        public void SetMethodName(String methodName) { }

        public String GetMessage() { }
        public void SetMessage(String message) { }

    }

```