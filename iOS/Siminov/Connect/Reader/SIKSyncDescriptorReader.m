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


#import "SIKSyncDescriptorReader.h"
#import "SICFileUtils.h"

@implementation SIKSyncDescriptorReader


- (id)init {
    self = [super init];
    
    if(self) {
        resourceManager = [SICResourceManager getInstance];
        
        tempValue = [[NSMutableString alloc] init];
        syncDescriptor = [[SIKSyncDescriptor alloc] init];
        
        return self;
    }
    
    return self;
}


- (id)initWithPath:(NSString * const)syncDescriptorPath {
    [self parse:syncDescriptorPath];
    return self;
}


- (void)parse:(NSString *)syncDescriptorPath {
    
    /*
     * Parse Service Descriptor.
     */
    NSData *applicationDescriptorStream = nil;
    NSString *syncDescriptorPathName = nil;
    
    @try {
        
        if ([syncDescriptorPath hasSuffix:FILE_TYPE]) {
            
            NSUInteger index = 0;
            NSRange range = [syncDescriptorPath rangeOfString:FILE_TYPE];
            if (range.length == 0 && range.location > syncDescriptorPath.length) {
                index = 0;
            } else {
                index = range.location;
            }
            
            syncDescriptorPathName = [syncDescriptorPath substringToIndex:index];
        }
        
        NSString *filePath = [[[SICFileUtils alloc] init] getFilePath:syncDescriptorPathName inDirectory:DIRECTORY_NAME];
        applicationDescriptorStream = [[NSFileManager defaultManager] contentsAtPath:filePath];
        
        if (applicationDescriptorStream == nil) {
            @throw [[SICDeploymentException alloc] initWithClassName:NSStringFromClass([self class]) methodName:@"Constructor" message:[NSString stringWithFormat:@"IOException caught while getting input stream of service descriptor"]];
        }
    }
    @catch (NSException *exception) {
        [SICLog error:NSStringFromClass([self class]) methodName:@"Constructor" message:[NSString stringWithFormat:@"IOException caught while getting input stream of service descriptor %@", [exception reason]]];
        @throw [[SICDeploymentException alloc] initWithClassName:NSStringFromClass([self class]) methodName:@"Constructor" message:[NSString stringWithFormat:@"IOException caught while getting input stream of service descriptor %@", [exception reason]]];
    }
    
    @try {
        [self parseMessage:applicationDescriptorStream];
    }
    @catch (NSException *exception) {
        [SICLog error:NSStringFromClass([self class]) methodName:@"Constructor" message:[NSString stringWithFormat:@"Exception caught while parsing APPLICATION-DESCRIPTOR, %@", [exception reason]]];
        @throw [[SICDeploymentException alloc] initWithClassName:NSStringFromClass([self class]) methodName:@"Constructor" message:[NSString stringWithFormat:@"Exception caught while parsing APPLICATION-DESCRIPTOR %@", [exception reason]]];
    }
}


- (void)parser:(NSXMLParser *)parser didStartElement:(NSString *)elementName namespaceURI:(NSString *)namespaceURI qualifiedName:(NSString *)qName attributes:(NSDictionary *)attributeDict {
    
    tempValue = [[NSMutableString alloc] init];
    
    if([elementName caseInsensitiveCompare:SYNC_DESCRIPTOR_PROPERTY] == NSOrderedSame) {
        propertyName = [attributeDict objectForKey:APPLICATION_DESCRIPTOR_NAME];
    } else if([elementName caseInsensitiveCompare:SYNC_DESCRIPTOR] == NSOrderedSame) {
        syncDescriptor = [[SIKSyncDescriptor alloc] init];
    }
}

- (void)parser:(NSXMLParser *)parser foundCharacters:(NSString *)string {
    [tempValue appendString:(NSMutableString *)[string stringByTrimmingCharactersInSet:[NSCharacterSet whitespaceAndNewlineCharacterSet]]];
}


- (void)parser:(NSXMLParser *)parser didEndElement:(NSString *)elementName namespaceURI:(NSString *)namespaceURI qualifiedName:(NSString *)qName {
    
    if([elementName caseInsensitiveCompare:APPLICATION_DESCRIPTOR_PROPERTY] == NSOrderedSame) {
        [syncDescriptor addProperty:propertyName value:(NSString *)tempValue];
    } else if([elementName caseInsensitiveCompare:SYNC_DESCRIPTOR_SERVICE_DESCRIPTOR] == NSOrderedSame) {
        [syncDescriptor addServiceDescriptorName:(NSString *)tempValue];
    }
}

- (SIKSyncDescriptor *)getSyncDescriptor {
    return syncDescriptor;
}


@end

