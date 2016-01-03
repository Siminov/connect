///
/// [SIMINOV FRAMEWORK - CONNECT]
/// Copyright [2014-2016] [Siminov Software Solution LLP|support@siminov.com]
///
/// Licensed under the Apache License, Version 2.0 (the "License");
/// you may not use this file except in compliance with the License.
/// You may obtain a copy of the License at
///
///     http://www.apache.org/licenses/LICENSE-2.0
///
/// Unless required by applicable law or agreed to in writing, software
/// distributed under the License is distributed on an "AS IS" BASIS,
/// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
/// See the License for the specific language governing permissions and
/// limitations under the License.
///

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
