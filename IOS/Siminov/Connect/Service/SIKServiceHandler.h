//
//  SIKServiceHandler.h
//  Connect
//
//  Created by user on 21/04/15.
//  Copyright (c) 2015 Siminov. All rights reserved.
//

#import <Foundation/Foundation.h>

#import "SIKSyncServiceWorker.h"
#import "SIKIWorker.h"
#import "SIKResourceManager.h"
#import "SIKIService.h"

/**
 * It is a singleton class which handles all service requests
 */
@interface SIKServiceHandler : NSObject {
    
    SIKSyncServiceWorker *syncServiceWorker;
    id<SIKIWorker> asyncServiceWorker;
    
    SIKResourceManager *resourceManager;
}

/**
 * It provides singleton instance of ServiceHandler class
 * @return ServiceHandler singleton instance
 */
+ (SIKServiceHandler *)getInstance;

/**
 * It handles the service request
 * @param service Service instance
 * @throws ServiceException If any exception occur while handling the service request
 */
- (void)handle:(id<SIKIService>)service;

@end
