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


#import "SIKConnectionResponse.h"

@implementation SIKConnectionResponse

- (id)init {
    
    self = [super init];
    if(self) {
        statusCode = 0;
        statusMessage = nil;
        response = nil;
        return self;
    }
    
    return self;
}

- (id)initWithStatusCode:(int)code statusMessage:(NSString *)message {
    if (self) {
        statusCode = code;
        statusMessage = message;
    }
    return self;
}

- (id)initWithStatusCode:(int)code inputStream:(NSData *)resp {
    if (self) {
        statusCode = code;
        response = resp;
    }
    return self;
}

- (int)getStatusCode {
    return statusCode;
}

- (void)setStatusCode:(int)code {
    statusCode = code;
}

- (NSString *)getStatusMessage {
    return statusMessage;
}

- (void)setStatusMessage:(NSString *)message {
    statusMessage = message;
}

- (NSData *)getResponse {
    return response;
}

- (void)setResponse:(NSData *)rsp {
    response = rsp;
}

@end
