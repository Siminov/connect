//
//  SIKSiminov.m
//  Connect
//
//  Created by user on 21/04/15.
//  Copyright (c) 2015 Siminov. All rights reserved.
//

#import "SIKSiminov.h"
#import "SIKResourceManager.h"
#import "SICDeploymentException.h"
#import "SIKInitializer.h"
#import "SICEventHandler.h"
#import "SIKApplicationDescriptorReader.h"
#import "SICDatabaseDescriptorReader.h"
#import "SIKApplicationDescriptor.h"
#import "SIKSyncDescriptor.h"
#import "SICLog.h"
#import "SIKIWorker.h"
#import "SIKAsyncServiceWorker.h"
#import "SIKSyncDescriptorReader.h"


@implementation SIKSiminov

static bool isConnectActive = false;

static SICResourceManager *coreResourceManager;
static SIKResourceManager *connectResourceManager;


+ (void)initialize {
    coreResourceManager = [SICResourceManager getInstance];
    connectResourceManager = [SIKResourceManager getInstance];
}

+ (void)isActive {
    if(!isConnectActive && ![super getActive]) {
        @throw [[SICDeploymentException alloc] initWithClassName:NSStringFromClass([self class]) methodName:@"isActive" message:@"isActive"];
    }
}

+ (bool)getActive {
    return isConnectActive;
}

+ (void)setActive:(bool)active {
    [super setActive:true];
    isConnectActive = active;
}

+ (id<SICIInitializer>)initializer {
    return [[SIKInitializer alloc] init];
}

+ (void)start {
    if (isConnectActive) {
        return;
    }
    
    [self process];
    isConnectActive = true;
    [super setActive:true];
    
    id<SICISiminovEvents> coreEventHandler = [coreResourceManager getSiminovEventHandler];
    if (coreEventHandler != nil) {
        if ([SICSiminov isFirstTimeInitialized]) {
            [coreEventHandler onFirstTimeSiminovInitialized];
        } else {
            [coreEventHandler onSiminovInitialized];
        }
    }
}

+ (void)shutdown {
    [self isActive];
    
    [super shutdown];
}


+ (void)process {
    
    [self processApplicationDescriptor];
    [self processDatabaseDescriptors];
    [self processLibraries];
    [self processEntityDescriptors];
    
    [self processSyncDescriptors];
    [self processDatabase];
    [self processServices];
}

/**
 * It process ApplicationDescriptor.xml file defined in Application, and stores in Resource Manager.
 */
+ (void)processApplicationDescriptor {
    
    SIKApplicationDescriptorReader *applicationDescriptorParser = [[SIKApplicationDescriptorReader alloc] init];
    
    SIKApplicationDescriptor *applicationDescriptor = [applicationDescriptorParser getApplicationDescriptor];
    if(applicationDescriptor == nil) {
        [SICLog debug:NSStringFromClass([self class]) methodName:@"processApplicationDescriptor" message:@"Invalid Application Descriptor Found."];
        @throw [[SICDeploymentException alloc] initWithClassName:NSStringFromClass([self class]) methodName:@"processApplicationDescriptor" message:@"Invalid Application Descriptor Found."];
    }
    
    [coreResourceManager setApplicationDescriptor:applicationDescriptor];
    [connectResourceManager setApplicationDescriptor:applicationDescriptor];
}

/**
 * It process all DatabaseDescriptor.xml files defined by Application and stores in Resource Manager.
 */
+ (void)processDatabaseDescriptors {
    [SICSiminov processDatabaseDescriptors];
    
    SICDatabaseDescriptorReader *databaseDescriptorReader = [[SICDatabaseDescriptorReader alloc] initWithPath:DATABASE_DESSCRIPTOR_PATH];
    SICDatabaseDescriptor *databaseDescriptor = [databaseDescriptorReader getDatabaseDescriptor];
    
    SIKApplicationDescriptor *applicationDescriptor = [connectResourceManager getApplicationDescriptor];
    [applicationDescriptor addDatabaseDescriptor:DATABASE_DESSCRIPTOR_PATH databaseDescriptor:databaseDescriptor];
}

/**
 * It process all EntityDescriptor.xml file defined in Application, and stores in Resource Manager.
 */
+ (void)processEntityDescriptors {
    [super processEntityDescriptors];
}

/**
 * It process all SyncDescriptor.xml file defined in Application, and stores in Resource Manager.
 */
+ (void)processSyncDescriptors {
    
    SIKApplicationDescriptor *applicationDescriptor = [connectResourceManager getApplicationDescriptor];
    NSEnumerator *syncDescriptorPaths = [applicationDescriptor getSyncDescriptorPaths];
    
    NSString *syncDescriptorPath = nil;
    while(syncDescriptorPath = [syncDescriptorPaths nextObject]) {
        
        SIKSyncDescriptorReader *syncDescriptorReader = [[SIKSyncDescriptorReader alloc] initWithPath:syncDescriptorPath];
        [applicationDescriptor addSyncDescriptor:syncDescriptorPath syncDescriptor:[syncDescriptorReader getSyncDescriptor]];
    }
}

/**
 * It process all LibraryDescriptor.xml files defined by application, and stores in Resource Manager.
 */
+ (void)processLibraries {
    [super processLibraries];
}

/**
 * It process all DatabaseDescriptor.xml and initialize Database and stores in Resource Manager.
 */
+ (void)processDatabase {
    [super processDatabase];
}

/**
 * It starts all Service Workers
 */
+ (void)processServices {
    
    id<SIKIWorker> asyncServiceWorker = [SIKAsyncServiceWorker getInstance];
    [asyncServiceWorker startWorker];
}


@end
