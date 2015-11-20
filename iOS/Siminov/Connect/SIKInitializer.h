//
//  SIKInitializer.h
//  Connect
//
//  Created by user on 21/04/15.
//  Copyright (c) 2015 Siminov. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "SICInitializer.h"

/**
 * It implements IInitializer Interface.
 * It handle initialization of framework.
 */
@interface SIKInitializer: NSObject <SICIInitializer> {
    NSMutableArray *parameters;
}

@end