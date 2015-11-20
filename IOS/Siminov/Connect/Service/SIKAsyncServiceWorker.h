//
//  SIKAsyncServiceWorker.h
//  Connect
//
//  Created by user on 21/04/15.
//  Copyright (c) 2015 Siminov. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "SIKIWorker.h"

@interface SIKAsyncServiceWorkerHelper : NSObject

@end

@interface SIKAsyncServiceWorkerThread : NSThread

@end

@interface SIKAsyncServiceWorker : NSObject<SIKIWorker> {
    
}

/**
 * It provides AsyncServiceWorker singleton instance
 * @return AsyncServiceWorker singleton instance
 */
+ (SIKAsyncServiceWorker *)getInstance;


@end
