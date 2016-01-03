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


#import "SIKNotificationDescriptor.h"

@implementation SIKNotificationDescriptor

- (id)init {
    
    self = [super init];
    
    if(self) {
        
        properties = [[NSMutableDictionary alloc] init];
        
        return self;
    }
    
    return self;
}


- (NSEnumerator *)getProperties {
    return [properties keyEnumerator];
}

- (NSString *)getProperty:(NSString *)name {
    return [properties objectForKey:name];
}

- (bool)containProperty:(NSString *)name {
    return [[properties allValues] containsObject:name];
}

- (void)addProperty:(NSString *)name value:(NSString *)value {
    [properties setValue:value forKey:name];
}

- (void)removeProperty:(NSString *)name {
    [properties removeObjectForKey:name];
}

@end
