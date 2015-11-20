//
//  SIKConnectionException.h
//  Connect
//
//  Created by user on 21/04/15.
//  Copyright (c) 2015 Siminov. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "SICSiminovException.h"


@interface SIKConnectionException : SICSiminovException

/**
 * Database Exception Constructor
 * @param classname Name of class
 * @param methodname Name of method
 * @param exceptionmessage Exception Messsage
 */
- (id)initWithClassName:(NSString * const)classname methodName:(NSString * const)methodname message:(NSString * const)exceptionmessage;


@end
