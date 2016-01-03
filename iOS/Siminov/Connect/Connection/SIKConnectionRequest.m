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


#import "SIKConnectionRequest.h"
#import "SIKServiceDescriptor.h"

@implementation SIKConnectionRequest

- (id)init {
    
    self = [super init];
    if(self) {
        
        url = nil;
        
        protocol = nil;
        type = nil;
        
        queryParameters = [[NSMutableDictionary alloc] init];
        headerParameters = [[NSMutableDictionary alloc] init];
        
        dataStream = nil;
        
        return self;
    }
    
    return self;
}

- (NSString *)getUrl {
    return url;
}

- (void)setUrl:(NSString *)ul {
    url = ul;
}

- (NSString *)getProtocol {
    return protocol;
}

- (void)setProtocol:(NSString *)ptl {
    protocol = ptl;
}

- (NSString *)getType {
    return type;
}

- (void)setType:(NSString *)typ {
    type = typ;
}

- (NSEnumerator *)getQueryParameters {
    return [queryParameters keyEnumerator];
}

- (SIKQueryParameter *)getQueryParameter:(NSString *)key {
    return [queryParameters valueForKey:key];
}

- (void)addQueryParameter:(SIKQueryParameter *)queryParameter {
    [queryParameters setValue:queryParameter forKey:[queryParameter getName]];
}

- (NSEnumerator *)getHeaderParameters {
    return [headerParameters keyEnumerator];
}

- (SIKHeaderParameter *)getHeaderParameter:(NSString *)key {
    return [headerParameters valueForKey:key];
}

- (void)addHeaderParameter:(SIKHeaderParameter *)headerParameter {
    [headerParameters setValue:headerParameter forKey:[headerParameter getName]];
}

- (NSData *)getDataStream {
    return dataStream;
}

- (void)setDataStream:(NSData *)data {
    dataStream = data;
}

@end
