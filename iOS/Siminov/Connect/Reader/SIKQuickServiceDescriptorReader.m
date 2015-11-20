//
//  SIKQuickServiceDescriptorReader.m
//  Connect
//
//  Created by user on 21/04/15.
//  Copyright (c) 2015 Siminov. All rights reserved.
//

#import "SIKQuickServiceDescriptorReader.h"

#import "SIKResourceManager.h"
#import "SICLog.h"
#import "SICResourceManager.h"
#import "SICFileUtils.h"
#import "SIKServiceDescriptorReader.h"

@implementation SIKQuickServiceDescriptorReader

static SICResourceManager *coreResourceManager;
static SIKResourceManager *resourceManager;

+ (void)initialize {
    coreResourceManager = [SICResourceManager getInstance];
    resourceManager = [SIKResourceManager getInstance];
}


- (id)initWithClassName:(NSString * const)findServiceName {
    
    if (self = [super init]) {
        
        if (findServiceName == nil || [findServiceName length] <= 0) {
            [SICLog error:NSStringFromClass([self class]) methodName:@"Constructor" message:@"Invalid Service Descriptor Class Name Which Needs To Be Searched."];
            @throw [[SICSiminovException alloc] initWithClassName:NSStringFromClass([self class]) methodName:@"Constructor" message:@"Invalid Entity Descriptor Class Name Which Needs To Be Searched."];
        }
        
        finalServiceDescriptorName = findServiceName;
    }
    
    return self;
}

- (void)process {
    
    SIKApplicationDescriptor *applicationDescriptor = [resourceManager getApplicationDescriptor];
    if (applicationDescriptor == nil) {
        [SICLog error:NSStringFromClass([self class]) methodName:@"process" message:@"Invalid Application Context found."];
        @throw [[SICSiminovException alloc] initWithClassName:NSStringFromClass([self class]) methodName:@"process" message:@"Invalid Application Context found."];
    }
    
    NSEnumerator *serviceDescriptorPaths = [applicationDescriptor getServiceDescriptorPaths];
    NSString *serviceDescriptorPath;
    
    while(serviceDescriptorPath = [serviceDescriptorPaths nextObject]) {
        
        NSData *serviceDescriptorStream = nil;
        @try {
            
            if ([serviceDescriptorPath hasSuffix:FILE_TYPE]) {
                
                NSUInteger index = 0;
                NSRange range = [serviceDescriptorPath rangeOfString:FILE_TYPE];
                if (range.length == 0 && range.location > serviceDescriptorPath.length) {
                    index = 0;
                } else {
                    index = range.location;
                }
                
                serviceDescriptorPath = [serviceDescriptorPath substringToIndex:index];
            }
            
            NSString *filePath = [[[SICFileUtils alloc] init] getFilePath:serviceDescriptorPath inDirectory:DIRECTORY_NAME];
            serviceDescriptorStream = [[NSFileManager defaultManager] contentsAtPath:filePath];
            
            if (serviceDescriptorStream == nil) {
                @throw [[SICDeploymentException alloc] initWithClassName:NSStringFromClass([self class]) methodName:@"Constructor" message:[NSString stringWithFormat:@"IOException caught while getting input stream of application descriptor"]];
            }

        } @catch(NSException *exception) {
            
            [SICLog error:NSStringFromClass([self class]) methodName:@"process" message:[NSString stringWithFormat:@"IOException caught while getting input stream of Service descriptor %@", [exception reason]]];
            @throw [[SICDeploymentException alloc] initWithClassName:NSStringFromClass([self class]) methodName:@"process" message:[NSString stringWithFormat:@"IOException caught while getting input stream of Service descriptor %@", [exception reason]]];
        }
        
        @try {
            [self parseMessage:serviceDescriptorStream];
        }
        @catch (NSException *exception) {
            [SICLog error:NSStringFromClass([self class]) methodName:@"process" message:[NSString stringWithFormat:@"Exception caught while parsing Service Desriptor: %@, %@", serviceDescriptorPath,[exception reason]]];
            @throw [[SICDeploymentException alloc] initWithClassName:NSStringFromClass([self class]) methodName:@"process" message:[NSString stringWithFormat:@"Exception caught while parsing Service Descriptor: %@, %@",serviceDescriptorPath,[exception reason]]];
        }
        
        if(doesMatch) {
            
            SIKServiceDescriptorReader *serviceDescriptorReader = [[SIKServiceDescriptorReader alloc]initWithPath:serviceDescriptorPath];
            serviceDescriptor = [serviceDescriptorReader getServiceDescriptor];
            
            return;
        }
    }
    
}

- (void)parser:(NSXMLParser *)parser didStartElement:(NSString *)elementName namespaceURI:(NSString *)namespaceURI qualifiedName:(NSString *)qName attributes:(NSDictionary *)attributeDict {
    tempValue = [[NSMutableString alloc] init];
    
    if([elementName caseInsensitiveCompare:SERVICE_DESCRIPTOR_PROPERTY] == NSOrderedSame) {
        NSString *propertyName = [attributeDict objectForKey:SERVICE_DESCRIPTOR_PROPERTY_NAME];
        
        if([propertyName caseInsensitiveCompare:SERVICE_DESCRIPTOR_NAME] == NSOrderedSame) {
            isNameProperty = true;
        }
    }
}

- (void)parser:(NSXMLParser *)parser foundCharacters:(NSString *)string {
    [tempValue appendString:(NSMutableString *)[string stringByTrimmingCharactersInSet:[NSCharacterSet whitespaceAndNewlineCharacterSet]]];
}

- (void)parser:(NSXMLParser *)parser didEndElement:(NSString *)elementName namespaceURI:(NSString *)namespaceURI qualifiedName:(NSString *)qName {
    
    if([elementName caseInsensitiveCompare:SERVICE_DESCRIPTOR_PROPERTY] == NSOrderedSame) {
        
        if(isNameProperty) {
            if([tempValue caseInsensitiveCompare:finalServiceDescriptorName] == NSOrderedSame) {
                doesMatch = true;
            }
            
            [parser setDelegate:nil];
            [parser abortParsing];
            parser = nil;
        }
    }
}

- (SIKServiceDescriptor *)getServiceDescriptor {
    return serviceDescriptor;
}

- (bool)containServiceDescriptor {
    return doesMatch;
}


@end
