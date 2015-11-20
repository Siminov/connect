//
//  SIKConnectionResponse.m
//  Connect
//
//  Created by user on 21/04/15.
//  Copyright (c) 2015 Siminov. All rights reserved.
//

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
