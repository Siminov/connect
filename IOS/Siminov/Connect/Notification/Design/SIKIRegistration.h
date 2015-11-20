//
//  SIKIRegistration.h
//  Connect
//
//  Created by user on 21/04/15.
//  Copyright (c) 2015 Siminov. All rights reserved.
//

#import <Foundation/Foundation.h>

/**
 * It is a blue print for classes which contain push notification information
 * It exposes APIs to Get and Set push notification information
 */
@protocol SIKIRegistration <NSObject>

/**
 * Get push notification registration id
 * @return Registration Id
 */
- (NSString *)getRegistrationId;

/**
 * Set push notification registration id
 * @param registrationId Registration Id
 */
- (void)setRegistrationId:(NSString *)registrationId;

@end
