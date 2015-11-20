//
//  SIKSiminov.h
//  Connect
//
//  Created by user on 21/04/15.
//  Copyright (c) 2015 Siminov. All rights reserved.
//

#import <Foundation/Foundation.h>

#import "SICSiminov.h"
#import "SICIInitializer.h"


/**
 * Exposes methods to deal with SIMINOV FRAMEWORK.
 *	<p>
 *		Such As
 *		<p>
 *			1. Initializer: Entry point to the SIMINOV.
 *		</p>
 *
 *		<p>
 *			2. Shutdown: Exit point from the SIMINOV.
 *		</p>
 *	</p>
 */
@interface SIKSiminov: SICSiminov

+ (bool)getActive;
+ (void)setActive:(bool)active;

+ (void)processSyncDescriptors;
+ (void)processServices;

@end
