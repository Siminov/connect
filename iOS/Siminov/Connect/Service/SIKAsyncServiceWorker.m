//
//  SIKAsyncServiceWorker.m
//  Connect
//
//  Created by user on 21/04/15.
//  Copyright (c) 2015 Siminov. All rights reserved.
//

#import "SIKAsyncServiceWorker.h"
#import "SICResourceManager.h"
#import "SIKIService.h"
#import "SIKServiceRequest.h"
#import "SIKServiceException.h"
#import "Reachability.h"
#import "SIKIConnectionResponse.h"
#import "SIKConnectionManager.h"
#import "SIKConnectionException.h"
#import "SICIDatabase.h"
#import "SIKResourceManager.h"
#import "SIKResourceUtils.h"

static SIKAsyncServiceWorker *asyncServiceWorker = nil;
static SIKResourceManager *resourceManager = nil;
static SIKAsyncServiceWorkerHelper *serviceUtils = nil;
static SIKAsyncServiceWorkerThread *asyncServiceWorkerThread = nil;

static bool INTERNET_ACTIVE;
static Reachability * hostReachability;

/**
 * It is the helper class for AsyncServiceWorker
 * It helps to convert request database instance to IService instance and vis versa
 */
@interface SIKAsyncServiceWorkerHelper (PrivateMethods)

@end

@implementation SIKAsyncServiceWorkerHelper

/**
 * Check whether it contains the requested service or not
 * @param service Service
 * @return (true/false) TRUE: If service request already exists | FALSE: If service does not exists
 * @throws ServiceException If there is any exception while checking for request
 */
- (BOOL)containService:(SIKServiceRequest*) service {
    
    NSArray *services = nil;
    @try {
        services = [[[[SIKServiceRequest alloc]init] select] execute];
    } @catch(SICDatabaseException *de) {
        
        [SICLog error:NSStringFromClass([self class]) methodName:@"containService" message:[NSString stringWithFormat:@"DatabaseException caught while getting services from database, %@", [de reason]]];
        @throw [[SIKServiceException alloc]initWithClassName:NSStringFromClass([self class]) methodName:@"containService" message:[de reason]];
    }
    
    
    if(services == nil || services.count <= 0) {
        return false;
    }
    
    for(int i = 0;i < services.count;i++) {
        
        SIKServiceRequest *savedService = services[i];
        if([[service getService] caseInsensitiveCompare:[savedService getService]] == NSOrderedSame) {
            
            if([[service getRequest] caseInsensitiveCompare:[savedService getRequest]] == NSOrderedSame) {
                
                bool contain = true;
                
                NSEnumerator *serviceRequestResources = [service getServiceRequestResources];
                SIKServiceRequestResource *serviceResource;
                while(serviceResource = [serviceRequestResources nextObject]) {
                    
                    SIKServiceRequestResource *savedRequestResource = [savedService getServiceRequestResource:[serviceResource getName ]];
                    
                    if(savedRequestResource == nil) {
                        contain = false;
                        break;
                    }
                    
                    if(![[serviceResource getName] caseInsensitiveCompare:[savedRequestResource getName]] == NSOrderedSame) {
                        contain = false;
                        break;
                    } else if(![[serviceResource getValue] caseInsensitiveCompare:[savedRequestResource getValue]] == NSOrderedSame) {
                        contain = false;
                        break;
                    }
                }
                
                if(contain) {
                    return true;
                }
            }
        }
    }
    return false;
}

/**
 * Converts the service request database instance to IService instance
 * @param service ServiceRequest instance
 * @return IService instance
 * @throws ServiceException If any exception occur while converting the instance
 */
- (id<SIKIService>) convertToService:(SIKServiceRequest *)service {
    
    id<SIKIService> iService = (id<SIKIService>)[SICClassUtils createClassInstance:[service getInstanceOf]];
    [iService setRequestId:[service getRequestId]];
    [iService setService:[service getService]];
    [iService setRequest:[service getRequest]];
    
    NSEnumerator *serviceRequestResources = [service getServiceRequestResources];
    SIKServiceRequestResource *serviceResource;
    
    while(serviceResource = [serviceRequestResources nextObject]) {
        [iService addResource:[serviceResource getName] value:[serviceResource getValue]];
    }
    
    SIKServiceDescriptor *serviceDescriptor = [resourceManager requiredServiceDescriptorBasedOnName:[service getService]];
    [iService setServiceDescriptor:serviceDescriptor];
    
    [SIKResourceUtils resolve:iService];
    return iService;
}

/**
 * It converts IService instance to database service request instance
 * @param iService IService instance
 * @return ServiceRequest Instance
 */
- (SIKServiceRequest*) convertToRequest:(id<SIKIService>)iService {
    
    SIKServiceRequest *serviceRequest = [[SIKServiceRequest alloc]init];
    [serviceRequest setRequestId:[iService getRequestId]];
    [serviceRequest setService:[iService getService]];
    [serviceRequest setRequest:[iService getRequest]];
    [serviceRequest setInstanceOf:NSStringFromClass([iService class])];
    
    NSEnumerator *resources = [iService getResources];
    NSString *resourceName;
    
    while(resourceName = [resources nextObject]) {
        
        id resourceValue = [iService getResource:resourceName];
        
        if(![resourceValue isKindOfClass:[NSString class]]) {
            continue;
        }
        
        SIKServiceRequestResource *serviceRequestResource = [[SIKServiceRequestResource alloc]init];
        [serviceRequestResource setServiceRequest:serviceRequest ];
        [serviceRequestResource setName:resourceName];
        [serviceRequestResource setValue:(NSString*)resourceValue];
        
        [serviceRequest addServiceRequestResource:serviceRequestResource];
    }
    
    return serviceRequest;
}

@end

/**
 * It is the inner class of Async service worker which processes all the requests in the queue
 */

@interface SIKAsyncServiceWorkerThread (PrivateMethods)

@end

@implementation SIKAsyncServiceWorkerThread

-(void)main
{
    while (true) {
        
        NSArray *services = nil;
        
        @try {
            services = [[[[SIKServiceRequest alloc] init] select] execute];
        } @catch (SICSiminovException *exception) {
            [SICLog error:NSStringFromClass([self class]) methodName:@"run" message:[NSString stringWithFormat:@"SiminovException caught while getting queue services, %@", [exception reason]]];
            @throw [[SICSiminovCriticalException alloc] initWithClassName:NSStringFromClass([self class]) methodName:@"run" message:[NSString stringWithFormat:@"SiminovException caught while getting queue services, %@", [exception reason]]];
        }
        
        if (services == nil || services.count <=0) {
            [[SIKAsyncServiceWorker getInstance] stopWorker];
            return;
        }
        
        for (SIKServiceRequest *service in services) {
            id<SIKIService> iService = nil;
            
            @try {
                iService = [serviceUtils convertToService:service];
            } @catch (SIKServiceException *exception) {
                [SICLog error:NSStringFromClass([self class]) methodName:@"run" message:[NSString stringWithFormat:@"SiminovException caught while converting service to iService, %@", [exception reason]]];
                return;
            }
            
            /*
             * Check Network Connectivity.
             */
            
            if (!INTERNET_ACTIVE) {
                @try {
                    
                }
                @catch (NSException *exception) {
                    [SICLog error:NSStringFromClass([self class]) methodName:@"run" message:[NSString stringWithFormat:@"Exception caught while putting async service worker thread into wait state, %@", [exception reason]]];
                    return;
                }
            }
            
            /*
             * Service Resumed
             */
            [iService onResume];
            [self handle:iService];
            
        }
    }
    [[SIKAsyncServiceWorker getInstance] stopWorker];
}

- (void)handle: (id<SIKIService>)iService {
    
    id<SIKIConnectionResponse> connectionResponse = nil;
    
    @try {
        connectionResponse = [[SIKConnectionManager getInstance] handle:iService];
    }
    @catch (SIKConnectionException *exception) {
        [SICLog error:NSStringFromClass([self class]) methodName:@"handle" message:[NSString stringWithFormat:@"SiminovException caught while invoking connection, %@", [exception reason]]];
        
        [iService onTerminate:[[SIKServiceException alloc]initWithClassName:[exception getClassName] methodName:[exception getMethodName] message:[exception getMessage]]];
        return;
    }
    
    [iService onRequestFinish:connectionResponse];
    
    id<SICIDatabase> serviceRequest = [serviceUtils convertToRequest:iService];
    
    @try {
        [[serviceRequest delete] execute];
    }
    @catch (SICDatabaseException *exception) {
        [SICLog error:NSStringFromClass([self class]) methodName:@"handle" message:[NSString stringWithFormat:@"Database Exception caught while deleting service request from database, %@", [exception reason]]];
        @throw [[SICSiminovCriticalException alloc]initWithClassName:NSStringFromClass([self class]) methodName:@"handle" message:[NSString stringWithFormat:@"Database Exception caught while deleting service request from database, %@", [exception reason]]];
    }
    
}

@end

@implementation SIKAsyncServiceWorker

- (id)init {
    self = [super init];
    
    if(self) {
        resourceManager = [SIKResourceManager getInstance];
        serviceUtils = [[SIKAsyncServiceWorkerHelper alloc]init];
        [self startWorker];
        
        return self;
    }
    
    return self;
}

+ (SIKAsyncServiceWorker *)getInstance {
    
    if(!asyncServiceWorker) {
        asyncServiceWorker = [[super allocWithZone:NULL] init];
    }
    
    return asyncServiceWorker;
}

+ (void)registerForReachabilityNotifications {
    //Notifications have to be registered on the main run loop [run loops of threads terminate alongside the thread].
    dispatch_async(dispatch_get_main_queue(),^{
        [[NSNotificationCenter defaultCenter] addObserver:self
                                                 selector:@selector(updateInterfaceWithReachability:)
                                                     name:kReachabilityChangedNotification
                                                   object:nil];
        
        if(hostReachability == nil){
            NSString *remoteHostName = @"www.apple.com";
            hostReachability = [Reachability reachabilityWithHostName:remoteHostName];
        }
        [hostReachability startNotifier];
    });
}

+ (void) updateInterfaceWithReachability:(NSNotification *)note {
    // called when host reachability status changes
    NetworkStatus internetStatus = [hostReachability currentReachabilityStatus];
    
    switch (internetStatus)
    {
        case NotReachable:
        {
            INTERNET_ACTIVE = NO;
            [[[self class] alloc] startWorker];
            
            break;
        }
        case ReachableViaWiFi:
        {
            INTERNET_ACTIVE = YES;
            [[[self class] alloc] stopWorker];
            
            break;
        }
        case ReachableViaWWAN:
        {
            INTERNET_ACTIVE = YES;
            [[[self class] alloc] startWorker];
            
            break;
        }
    }
}

-(void) addRequest:(id<SIKIRequest>)request {
    id<SIKIService> iService = (id<SIKIService>)request;
    SIKServiceRequest *service = [serviceUtils convertToRequest:iService];
    
    BOOL contain = false;
    
    @try {
        contain = [serviceUtils containService:service];
    }
    @catch (SIKServiceException *se) {
        [SICLog error:NSStringFromClass([self class]) methodName:@"process" message:[NSString stringWithFormat:@"ServiceException caught while checking exsisting service, %@", [se reason]]];
        [iService onTerminate:se];
        
        return;
    }
    
    if (contain) {
        return;
    }
    
    @try {
        [service save];
    }
    @catch (SICSiminovException *se) {
        [SICLog error:NSStringFromClass([self class]) methodName:@"process" message:[NSString stringWithFormat:@"ServiceException caught while saving service, %@", [se reason]]];
        [iService onTerminate:[[SIKServiceException alloc]initWithClassName:NSStringFromClass([self class]) methodName:@"process" message:[se reason]]];
        
        return;
    }
    
    /*
     * Service Queued
     */
    [iService onQueue];
    /*
     * Service Paused
     */
    [iService onPause];
    
    
    /*
     * Notify Async Service Worker Thread.
     */
    [self startWorker];
}

- (void)removeRequest:(id<SIKIRequest> const)service {
    
}

- (BOOL)containsRequest:(id<SIKIRequest> const)request {
    
    id<SIKIService> service = (id<SIKIService>)request;
    SIKServiceRequest *serviceRequest = [serviceUtils convertToRequest:service];
    
    @try {
        return [serviceUtils containService:serviceRequest];
    } @catch(SIKServiceException *se) {
        [SICLog error:NSStringFromClass([self class]) methodName:@"containsRequest" message:[NSString stringWithFormat:@"ServiceException caught while checking exsisting service %@", [se reason]]];
        
        [service onTerminate:se];
        
        return false;
    }
}

- (void) startWorker {
    if(asyncServiceWorkerThread == nil) {
        asyncServiceWorkerThread = [[SIKAsyncServiceWorkerThread alloc]init];
    }
    
    if(asyncServiceWorkerThread && ![asyncServiceWorkerThread isExecuting]) {
        [asyncServiceWorkerThread start];
    }
}

-(void)stopWorker {
    
    if(asyncServiceWorkerThread == nil) {
        return;
    }
    
    @try {
        [asyncServiceWorkerThread cancel];
        asyncServiceWorkerThread = nil;
    } @catch(NSException *e) {
        [SICLog error:NSStringFromClass([self class]) methodName:@"stop" message:[NSString stringWithFormat:@"ServiceException caught while stopping async service worder thread, %@", [e reason]]];
        
        return;
    }
}

- (BOOL)isWorkerRunning {
    if(asyncServiceWorkerThread == nil) {
        return false;
    }
    
    return [asyncServiceWorkerThread isExecuting];
}

@end
