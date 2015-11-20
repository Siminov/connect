//
//  SIKResourceUtils.m
//  Connect
//
//  Created by user on 21/04/15.
//  Copyright (c) 2015 Siminov. All rights reserved.
//

#import "SIKResourceUtils.h"
#import "SIKConstants.h"
#import "SICClassUtils.h"
#import "SICIDescriptor.h"

@implementation SIKResourceUtils


+ (NSString *)resolve:(NSString *)resourceValue descriptors:(NSArray * const)descriptors {
    
    if(resourceValue == nil) {
        return resourceValue;
    }
    
    if([resourceValue containsString:[NSString stringWithFormat:@"%@%@",RESOURCE_OPEN_CURLY_BRACKET,RESOURCE_REFER_REFERENCE]]) {
        
        //Find {}
        
        NSUInteger bracketLocation = [resourceValue rangeOfString:RESOURCE_SPACE].location;
        NSUInteger openingCurlyBracketIndex = bracketLocation + 1;
        
        NSUInteger singleClosingCurlyBracketIndex = [resourceValue rangeOfString:RESOURCE_SPACE].location;
        NSUInteger doubleClosingCurlyBracketIndex = [resourceValue rangeOfString:[NSString stringWithFormat:@"%@%@",RESOURCE_CLOSE_CURLY_BRACKET,RESOURCE_CLOSE_CURLY_BRACKET]].location;
        
        NSString *resourceKey;
        
        if(doubleClosingCurlyBracketIndex != -1) {
            
            resourceKey = [resourceValue substringFromIndex:doubleClosingCurlyBracketIndex+1];
            NSUInteger slashIndex = [resourceKey rangeOfString:RESOURCE_SLASH options:NSBackwardsSearch].location;
            
            //Find {-
            NSString *resourceClass = [resourceKey substringWithRange:NSMakeRange(0,[[resourceKey substringWithRange:NSMakeRange(0, slashIndex)] rangeOfString:RESOURCE_DOT options:NSBackwardsSearch].location)];
            NSString *resourceAPI = [resourceKey substringWithRange:NSMakeRange([[resourceKey substringWithRange:NSMakeRange(0, slashIndex)] rangeOfString:RESOURCE_DOT options:NSBackwardsSearch].location + 1,[resourceKey substringWithRange:NSMakeRange(0, slashIndex)].length)];
            
            NSMutableArray *resourceAPIParameterTypes = [[NSMutableArray alloc]init];
            NSMutableArray *resourceAPIParameters = [[NSMutableArray alloc]init];
            
            //Find -}}
            NSString *apiParameters = [resourceKey substringWithRange:NSMakeRange(slashIndex + 1, [resourceKey rangeOfString:RESOURCE_CLOSE_CURLY_BRACKET options:NSBackwardsSearch].location + 1)];
            
            //Resolve all API parameters
            NSEnumerator* apiParameterTokenizer = [[apiParameters componentsSeparatedByString:RESOURCE_COMMA] objectEnumerator];
            NSString *apiParameter = nil;
            
            while(apiParameter = [apiParameterTokenizer nextObject]) {
                
                [resourceAPIParameterTypes addObject:[NSString class]];
                [resourceAPIParameters addObject:[self resolve:apiParameter descriptors:descriptors]];
            }
            
            NSMutableArray *apiParameterTypes = [[NSMutableArray alloc]initWithCapacity:[resourceAPIParameters count]];
            
            for(id resourceAPIParameterType in resourceAPIParameterTypes) {
                [apiParameterTypes addObject:resourceAPIParameterType];
            }
            
            id classObject = [SICClassUtils createClassInstance:resourceClass];
            NSString *resolvedValue = nil;
            @try {
                resolvedValue = (NSString*)[SICClassUtils invokeMethodBasedOnMethodName:classObject methodName:resourceAPI parameterTypes:apiParameterTypes parameters:(NSArray *)resourceAPIParameters];
            } @catch(SICSiminovException *se) {
                
                [SICLog error:NSStringFromClass([self class]) methodName:@"resolve" message:[NSString stringWithFormat:@"SiminovException caught while invoking method, RESOURCE-API: %@, %@",resourceAPI,[se reason]]];
                @throw [[SIKServiceException alloc] initWithClassName:NSStringFromClass([self class]) methodName:@"resolve" message:[se reason]];
            }
            
            return [self resolve:resolvedValue descriptors:descriptors];
    
        } else {
            
            resourceKey = [resourceValue substringWithRange:NSMakeRange(openingCurlyBracketIndex, singleClosingCurlyBracketIndex)];
            NSUInteger dotIndex = [resourceKey rangeOfString:RESOURCE_DOT options:NSBackwardsSearch].location;
            
            NSString *resourceClass = [resourceKey substringWithRange:NSMakeRange(0,dotIndex)];
            NSString *resourceAPI = [resourceKey substringFromIndex:dotIndex+1];
            
            id classObject = [SICClassUtils createClassInstance:resourceClass];
            NSString *value = nil;
            
            @try {
                value = (NSString *)[SICClassUtils getValue:classObject methodName:resourceAPI];
            } @catch(SICSiminovException *se) {
                [SICLog error:NSStringFromClass([self class]) methodName:@"resolve" message:[NSString stringWithFormat:@"SiminovException caught while getting values, RESOURCE-API: %@, %@",resourceAPI,[se reason]]];
                @throw [[SIKServiceException alloc] initWithClassName:NSStringFromClass([self class]) methodName:@"resolve" message:[se reason]];
            }
            
            NSString *resolvedValue = [resourceValue stringByReplacingOccurrencesOfString:[NSString stringWithFormat:@"%@%@%@%@%@",RESOURCE_OPEN_CURLY_BRACKET,RESOURCE_REFER_REFERENCE,RESOURCE_SPACE,RESOURCE_CLOSE_CURLY_BRACKET,resourceKey] withString:value];
            
            return [self resolve:resolvedValue descriptors:descriptors];
        }
    } else if([resourceValue containsString:[NSString stringWithFormat:@"%@%@",RESOURCE_OPEN_CURLY_BRACKET,RESOURCE_SELF_REFERENCE]]) {

        NSString *key = [resourceValue substringWithRange:NSMakeRange([resourceValue rangeOfString:RESOURCE_SPACE].location+1,[resourceValue rangeOfString:RESOURCE_CLOSE_CURLY_BRACKET].location)];
        NSString *value = nil;
        
        for(id<SICIDescriptor> descriptor in descriptors) {
            
            if([descriptor containProperty:key]) {
                value = [descriptor getProperty:key];
                break;
            }
        }
        return [self resolve:value descriptors:descriptors];

    } else if([resourceValue containsString:[NSString stringWithFormat:@"%@%@",RESOURCE_OPEN_CURLY_BRACKET,RESOURCE_REFERENCE]]) {
        
        NSString *key = [resourceValue substringWithRange:NSMakeRange([[NSString stringWithFormat:@"%@%@",RESOURCE_OPEN_CURLY_BRACKET,RESOURCE_REFERENCE] length] + 1,[resourceValue rangeOfString:RESOURCE_CLOSE_CURLY_BRACKET].location - [[NSString stringWithFormat:@"%@%@",RESOURCE_OPEN_CURLY_BRACKET,RESOURCE_REFERENCE] length] - 1)];
        
        NSString *value = nil;
        
        for(id<SICIDescriptor> descriptor in descriptors) {
            
            if([descriptor containProperty:key]) {
                value = [descriptor getProperty:key];
                break;
            }
        }
        
        NSString *resolvedValue = [resourceValue stringByReplacingOccurrencesOfString:[NSString stringWithFormat:@"%@%@ %@%@",RESOURCE_OPEN_CURLY_BRACKET,RESOURCE_REFERENCE, key, RESOURCE_CLOSE_CURLY_BRACKET] withString:value];
        return [self resolve:resolvedValue descriptors:descriptors];
    }
    
    return resourceValue;
}

+ (void)resolve:(id<SIKIService>) service {
    
    /*
     * Resolve Service Descriptor Properties
     */
    
    SIKServiceDescriptor *serviceDescriptor = [service getServiceDescriptor];
    
    NSEnumerator *resources = [service getResources];
    NSString *resourceName;
    
    while(resourceName = [resources nextObject]) {
        id resourceValue = [service getResource:resourceName];
        
        if([resourceValue isKindOfClass:[NSString class]]) {
            [serviceDescriptor addProperty:resourceName value:(NSString *)resourceValue];
        }
    }
    
    NSEnumerator *serviceDescriptorProperties = [serviceDescriptor getProperties];
    NSString *serviceDescriptorProperty = nil;
    
    while(serviceDescriptorProperty = [serviceDescriptorProperties nextObject]) {
        
        NSString *serviceDescriptorValue = [serviceDescriptor getProperty:serviceDescriptorProperty];
       
        serviceDescriptorValue = [self resolve:serviceDescriptorValue descriptors:[NSArray arrayWithObject:serviceDescriptor]];
        [serviceDescriptor addProperty:serviceDescriptorProperty value:serviceDescriptorValue];
    }
    
    
    /*
     * Resolve API Properties
     */
    SIKRequest *request = [serviceDescriptor getRequest:[service getRequest]];
    NSEnumerator *apiProperties = [request getProperties];
    
    NSString *apiProperty = nil;
    while(apiProperty = [apiProperties nextObject]) {
        
        NSString *apiValue = [request getProperty:apiProperty];
        apiValue = [self resolve:apiValue descriptors:[NSArray arrayWithObjects:serviceDescriptor,request,nil]];
        
        [request addProperty:apiProperty value:apiValue];
    }
    
    
    /*
     * Resolve API Query Parameters
     */
    NSEnumerator *queryParameters = [request getQueryParameters];
    SIKQueryParameter *queryParameter = nil;
    while(queryParameter = [queryParameters nextObject]) {
        
        NSString *queryValue = [queryParameter getValue];
        queryValue = [self resolve:queryValue descriptors:[NSArray arrayWithObjects:serviceDescriptor,request, nil]];
        [queryParameter setValue:queryValue];
    }
    
    
    /*
     * Resolve API Query Parameters
     */
    NSEnumerator *headerParameters = [request getHeaderParameters];
    SIKHeaderParameter *headerParameter = nil;
        
    while(headerParameter = [headerParameters nextObject]) {
        
        NSString *headerValue = [headerParameter getValue];
        headerValue = [self resolve:headerValue descriptors:[NSArray arrayWithObjects:serviceDescriptor,request, nil]];
        [headerParameter setValue:headerValue];
    }
}

@end
