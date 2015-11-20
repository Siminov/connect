//
//  SIKNotificationService.h
//  Connect
//
//  Created by user on 21/04/15.
//  Copyright (c) 2015 Siminov. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "SIKResourceManager.h"

@interface SIKNotificationService : NSObject {
    
    SIKResourceManager *resourceManager;
    
    SIKApplicationDescriptor *applicationDescriptor;
    SIKNotificationDescriptor *notificationDescriptor;
}

@end
