//
//  SIKResourceManager.m
//  Connect
//
//  Created by user on 21/04/15.
//  Copyright (c) 2015 Siminov. All rights reserved.
//

#import "SIKResourceManager.h"

#import "SIKServiceDescriptorReader.h"
#import "SIKQuickServiceDescriptorReader.h"
#import "SIKEventHandler.h"


@implementation SIKResourceManager

static SIKResourceManager *resourceManager;
SIKApplicationDescriptor *applicationDescriptor;

+ (void)initialize {
    
}


+ (SIKResourceManager *)getInstance {
    
    if(!resourceManager) {
        resourceManager = [[super allocWithZone:NULL] init];
    }
    
    return resourceManager;
}


- (SIKApplicationDescriptor *)getApplicationDescriptor {
    return applicationDescriptor;
}


- (void)setApplicationDescriptor:(SIKApplicationDescriptor *)appDescriptor {
    applicationDescriptor = appDescriptor;
}


- (SIKServiceDescriptor *)requiredServiceDescriptorBasedOnPath:(NSString *)serviceDescriptorPath {
    
    SIKServiceDescriptorReader *serviceDescriptorReader = [[SIKServiceDescriptorReader alloc] initWithPath:serviceDescriptorPath];
    SIKServiceDescriptor *serviceDescriptor = [serviceDescriptorReader getServiceDescriptor];
    
    [applicationDescriptor addServiceDescriptorNameBasedOnPath:serviceDescriptorPath serviceDescriptorName:[serviceDescriptor getName]];
    
    return serviceDescriptor;
}


- (SIKServiceDescriptor *)requiredServiceDescriptorBasedOnName:(NSString *)serviceDescriptorName {
    
    if(![applicationDescriptor containServiceDescriptorPathBasedOnName:serviceDescriptorName]) {
        
        SIKQuickServiceDescriptorReader *quickServiceDescriptorReader;
        @try {
            quickServiceDescriptorReader = [[SIKQuickServiceDescriptorReader alloc] initWithClassName:serviceDescriptorName];
            [quickServiceDescriptorReader process];
        } @catch(SICSiminovException *siminovException) {
            [SICLog error:NSStringFromClass([self class]) methodName:@"requiredServiceDescriptorBasedOnName" message:@"Siminov Exception caught while getting quick service descriptor based on name."];
            @throw [[SICSiminovCriticalException alloc] initWithClassName:NSStringFromClass([self class]) methodName:@"requiredServiceDescriptorBasedOnName" message:[siminovException getMessage]];
        }
        
        
        if([quickServiceDescriptorReader containServiceDescriptor]) {
            return [quickServiceDescriptorReader getServiceDescriptor];
        }
    }
    
    NSString *serviceDescriptorPath = [applicationDescriptor getServiceDescriptorPathBasedOnName:serviceDescriptorName];
    return [self requiredServiceDescriptorBasedOnPath:serviceDescriptorPath];
}



- (SIKRequest *)requiredRequestBasedOnServiceDescriptorPath:(NSString *)serviceDescriptorPath requestName:(NSString *)requestName {
    
    SIKServiceDescriptor *serviceDescriptor = [self requiredServiceDescriptorBasedOnPath:serviceDescriptorPath];
    return [serviceDescriptor getRequest:requestName];
}


- (SIKRequest *)requireRequestBasedOnServiceDescriptorName:(NSString *)serviceDescriptorName requestName:(NSString *)requestName {
    
    SIKServiceDescriptor *serviceDescriptor = [self requiredServiceDescriptorBasedOnName:serviceDescriptorName];
    return [serviceDescriptor getRequest:requestName];
}


- (id<SIKINotificationEvents>)getNotificationEventHandler {
    return [[SIKEventHandler getInstance] getNotificationEventHandler];
}


- (id<SIKISyncEvents>)getSyncEventHandler {
    return [[SIKEventHandler getInstance] getSyncEventHandler];
}


- (NSEnumerator *)getSyncDescriptors {
    
    SIKApplicationDescriptor *applicationdescriptor = [self getApplicationDescriptor];
    return [applicationdescriptor getSyncDescriptors];
}


- (SIKSyncDescriptor *)getSyncDescriptor:(NSString *)syncDescriptorName {
    
    SIKApplicationDescriptor *applicationdescriptor = [self getApplicationDescriptor];
    return [applicationdescriptor getSyncDescriptorBasedOnName:syncDescriptorName];
}

@end
