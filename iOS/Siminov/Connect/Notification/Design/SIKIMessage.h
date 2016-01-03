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
