//
//  SIKIMessage.h
//  Connect
//
//  Created by user on 21/04/15.
//  Copyright (c) 2015 Siminov. All rights reserved.
//

#import <Foundation/Foundation.h>

/**
 * It is the blue print of class which contain push notification message
 * It exposes APIs to Get and Set push notification message
 */
@protocol SIKIMessage <NSObject>

/**
 * Get push notification message
 * @return Message
 */
- (NSString *)getMessage;

/**
 * Set push notification message
 * @param message Message
 */
- (void)setMessage:(NSString *)message;

@end
