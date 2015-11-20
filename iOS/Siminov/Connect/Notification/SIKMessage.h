//
//  SIKMessage.h
//  Connect
//
//  Created by user on 21/04/15.
//  Copyright (c) 2015 Siminov. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "SIKIMessage.h"

/**
 * It implements IMessage to Get and Set push notification message
 */
@interface SIKMessage : NSObject <SIKIMessage> {
    NSString *message;
}

@end
