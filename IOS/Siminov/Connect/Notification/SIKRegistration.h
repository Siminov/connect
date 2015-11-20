//
//  SIKRegistration.h
//  Connect
//
//  Created by user on 21/04/15.
//  Copyright (c) 2015 Siminov. All rights reserved.
//

#import <Foundation/Foundation.h>

#import "SIKIRegistration.h"

/**
 * It implements IRegsitration to Get and Set push notification registration id
 */
@interface SIKRegistration : NSObject<SIKIRegistration> {
    NSString * registrationId;
}

@end
