//
//  SIKServiceDescriptorReader.m
//  Connect
//
//  Created by user on 21/04/15.
//  Copyright (c) 2015 Siminov. All rights reserved.
//

#import "SIKServiceDescriptorReader.h"
#import "SIKConstants.h"
#import "SICFileUtils.h"

@implementation SIKServiceDescriptorReader

- (id)initWithPath:(NSString *)serviceDescriptorPath {
    
    tempValue = [[NSMutableString alloc] init];
    resourceManager = [SICResourceManager getInstance];
    
    serviceDescriptor = [[SIKServiceDescriptor alloc] init];

    [self parse:serviceDescriptorPath];
    return self;
}

- (id)initWithLibraryName:(NSString *)libraryPackageName serviceDescriptorPath:(NSString *)serviceDescriptorPath {
 
    /*
     * Parse Adapter.
     */
    NSData *adapterStream = nil;
    
    @try {
        
        libraryPackageName = [libraryPackageName stringByReplacingOccurrencesOfString:@"." withString:@"/"];
        
        NSString *filePath = [[[SICFileUtils alloc] init] getFilePath:[NSString stringWithFormat:@"%@/%@",libraryPackageName,serviceDescriptorPath] inDirectory:DIRECTORY_NAME];
        adapterStream = [[NSFileManager defaultManager] contentsAtPath:filePath];
        
        if (adapterStream == nil) {
            @throw [[SICDeploymentException alloc] initWithClassName:NSStringFromClass([self class]) methodName:@"Constructor" message:[NSString stringWithFormat:@"IOException caught while getting input stream of konnect descriptor"]];
        }
    }
    @catch (NSException *exception) {
        [SICLog error:NSStringFromClass([self class]) methodName:@"Constructor" message:[NSString stringWithFormat:@"IOException caught while getting input stream of konnect descriptor %@", [exception reason]]];
        @throw [[SICDeploymentException alloc] initWithClassName:NSStringFromClass([self class]) methodName:@"Constructor" message:[NSString stringWithFormat:@"IOException caught while getting input stream of konnect descriptor %@", [exception reason]]];
    }
    
    @try {
        [self parseMessage:adapterStream];
    }
    @catch (NSException *exception) {
        [SICLog error:NSStringFromClass([self class]) methodName:@"Constructor" message:[NSString stringWithFormat:@"Exception caught while parsing KONNECT-DESCRIPTOR, %@", [exception reason]]];
        @throw [[SICDeploymentException alloc] initWithClassName:NSStringFromClass([self class]) methodName:@"Constructor" message:[NSString stringWithFormat:@"Exception caught while parsing KONNECT-DESCRIPTOR %@", [exception reason]]];
    }
    
    return self;
}

-(void)parse:(NSString *)fileName {
   
    /*
     * Parse Service Descriptor.
     */
    NSData *serviceDescriptorStream = nil;
    
    @try {
        
        NSString *filePath = [[[SICFileUtils alloc] init] getFilePath:fileName inDirectory:DIRECTORY_NAME];
        serviceDescriptorStream = [[NSFileManager defaultManager] contentsAtPath:filePath];
        
        if (serviceDescriptorStream == nil) {
            @throw [[SICDeploymentException alloc] initWithClassName:NSStringFromClass([self class]) methodName:@"Constructor" message:[NSString stringWithFormat:@"IOException caught while getting input stream of service descriptor"]];
        }
    }
    @catch (NSException *exception) {
        [SICLog error:NSStringFromClass([self class]) methodName:@"Constructor" message:[NSString stringWithFormat:@"IOException caught while getting input stream of service descriptor %@", [exception reason]]];
        @throw [[SICDeploymentException alloc] initWithClassName:NSStringFromClass([self class]) methodName:@"Constructor" message:[NSString stringWithFormat:@"IOException caught while getting input stream of service descriptor %@", [exception reason]]];
    }
    
    @try {
        [self parseMessage:serviceDescriptorStream];
    }
    @catch (NSException *exception) {
        [SICLog error:NSStringFromClass([self class]) methodName:@"Constructor" message:[NSString stringWithFormat:@"Exception caught while parsing APPLICATION-DESCRIPTOR, %@", [exception reason]]];
        @throw [[SICDeploymentException alloc] initWithClassName:NSStringFromClass([self class]) methodName:@"Constructor" message:[NSString stringWithFormat:@"Exception caught while parsing APPLICATION-DESCRIPTOR %@", [exception reason]]];
    }
}

- (void)parser:(NSXMLParser *)parser didStartElement:(NSString *)elementName namespaceURI:(NSString *)namespaceURI qualifiedName:(NSString *)qName attributes:(NSDictionary *)attributeDict {
    
    tempValue = [[NSMutableString alloc] init];

    if([elementName caseInsensitiveCompare:SERVICE_DESCRIPTOR_PROPERTY] == NSOrderedSame) {
        propertyName = [attributeDict objectForKey:SERVICE_DESCRIPTOR_PROPERTY_NAME];
    } else if([elementName caseInsensitiveCompare:SERVICE_DESCRIPTOR_REQUEST] == NSOrderedSame) {
        
        request = [[SIKRequest alloc] init];
        isRequest = true;
    } else if([elementName caseInsensitiveCompare:SERVICE_DESCRIPTOR_REQUEST_QUERY_PARAMETER] == NSOrderedSame) {
        
        queryParameter = [[SIKQueryParameter alloc] init];
        isQueryParameter = true;
    } else if([elementName caseInsensitiveCompare:SERVICE_DESCRIPTOR_REQUEST_HEADER_PARAMETER] == NSOrderedSame) {
        
        headerParameter = [[SIKHeaderParameter alloc] init];
        isHeaderParameter = true;
    }
}

- (void)parser:(NSXMLParser *)parser foundCharacters:(NSString *)string {
    [tempValue appendString:(NSMutableString *)[string stringByTrimmingCharactersInSet:[NSCharacterSet whitespaceAndNewlineCharacterSet]]];
}


- (void)parser:(NSXMLParser *)parser didEndElement:(NSString *)elementName namespaceURI:(NSString *)namespaceURI qualifiedName:(NSString *)qName {
    
    if([elementName caseInsensitiveCompare:SERVICE_DESCRIPTOR_PROPERTY] == NSOrderedSame) {
        [self processProperty];
    } else if([elementName caseInsensitiveCompare:SERVICE_DESCRIPTOR_REQUEST] == NSOrderedSame) {
        
        [serviceDescriptor addRequest:request];
        
        request = nil;
        isRequest = false;
    } else if([elementName caseInsensitiveCompare:SERVICE_DESCRIPTOR_REQUEST_QUERY_PARAMETER] == NSOrderedSame) {
        [request addQueryParameter:queryParameter];
        
        queryParameter = nil;
        isQueryParameter = false;
    } else if([elementName caseInsensitiveCompare:SERVICE_DESCRIPTOR_REQUEST_HEADER_PARAMETER] == NSOrderedSame) {
        [request addHeaderParameter:headerParameter];
        
        headerParameter = nil;
        isHeaderParameter = false;
    } else if([elementName caseInsensitiveCompare:SERVICE_DESCRIPTOR_REQUEST_DATA_STREAM] == NSOrderedSame) {
        
        [request setDataStream:(NSString *)tempValue];
    }
}


- (void)processProperty {
    
    if(isQueryParameter) {
        [queryParameter addProperty:propertyName value:(NSString *)tempValue];
    } else if(isHeaderParameter) {
        [headerParameter addProperty:propertyName value:(NSString *)tempValue];
    } else if(isRequest) {
        [request addProperty:propertyName value:(NSString *)tempValue];
    } else {
        [serviceDescriptor addProperty:propertyName value:(NSString *)tempValue];
    }
}

- (SIKServiceDescriptor *)getServiceDescriptor {
    return serviceDescriptor;
}


@end
