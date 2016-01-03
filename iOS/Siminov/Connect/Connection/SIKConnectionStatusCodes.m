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



#import "SIKConnectionStatusCodes.h"

@implementation SIKConnectionStatusCodes


/**
 * Get status message based on status code
 * @param statusCode Status Code
 * @return Status Message
 */
- (NSString *)getStatusMessage:(int const)statusCode {
    
    /*
     1** Informational
     */
    if(statusCode == CONNECTION_STATUS_CODE_100) {
        return CONNECTION_STATUS_MESSAGE_100;
    } else if(statusCode == CONNECTION_STATUS_CODE_101) {
        return CONNECTION_STATUS_MESSAGE_101;
    } else if(statusCode == CONNECTION_STATUS_CODE_102) {
        return CONNECTION_STATUS_MESSAGE_102;
    } else if(statusCode == CONNECTION_STATUS_CODE_103) {
        return CONNECTION_STATUS_MESSAGE_103;
    } else if(statusCode == CONNECTION_STATUS_CODE_122) {
        return CONNECTION_STATUS_MESSAGE_122;
    }
    
    /*
     2** Success
     */
    else if(statusCode == CONNECTION_STATUS_CODE_200) {
        return CONNECTION_STATUS_MESSAGE_200;
    } else if(statusCode == CONNECTION_STATUS_CODE_201) {
        return CONNECTION_STATUS_MESSAGE_201;
    } else if(statusCode == CONNECTION_STATUS_CODE_202) {
        return CONNECTION_STATUS_MESSAGE_202;
    } else if(statusCode == CONNECTION_STATUS_CODE_203) {
        return CONNECTION_STATUS_MESSAGE_203;
    } else if(statusCode == CONNECTION_STATUS_CODE_204) {
        return CONNECTION_STATUS_MESSAGE_204;
    } else if(statusCode == CONNECTION_STATUS_CODE_205) {
        return CONNECTION_STATUS_MESSAGE_205;
    } else if(statusCode == CONNECTION_STATUS_CODE_206) {
        return CONNECTION_STATUS_MESSAGE_206;
    } else if(statusCode == CONNECTION_STATUS_CODE_207) {
        return CONNECTION_STATUS_MESSAGE_207;
    } else if(statusCode == CONNECTION_STATUS_CODE_208) {
        return CONNECTION_STATUS_MESSAGE_208;
    } else if(statusCode == CONNECTION_STATUS_CODE_226) {
        return CONNECTION_STATUS_MESSAGE_226;
    }
    
    /*
     3** Redirect
     */
    else if(statusCode == CONNECTION_STATUS_CODE_300) {
        return CONNECTION_STATUS_MESSAGE_300;
    } else if(statusCode == CONNECTION_STATUS_CODE_301) {
        return CONNECTION_STATUS_MESSAGE_301;
    } else if(statusCode == CONNECTION_STATUS_CODE_302) {
        return CONNECTION_STATUS_MESSAGE_302;
    } else if(statusCode == CONNECTION_STATUS_CODE_303) {
        return CONNECTION_STATUS_MESSAGE_303;
    } else if(statusCode == CONNECTION_STATUS_CODE_304) {
        return CONNECTION_STATUS_MESSAGE_304;
    } else if(statusCode == CONNECTION_STATUS_CODE_305) {
        return CONNECTION_STATUS_MESSAGE_305;
    } else if(statusCode == CONNECTION_STATUS_CODE_306) {
        return CONNECTION_STATUS_MESSAGE_306;
    } else if(statusCode == CONNECTION_STATUS_CODE_307) {
        return CONNECTION_STATUS_MESSAGE_307;
    } else if(statusCode == CONNECTION_STATUS_CODE_308) {
        return CONNECTION_STATUS_MESSAGE_308;
    }
    
    
    /*
     4** Client Error
     */
    else if(statusCode == CONNECTION_STATUS_CODE_400) {
        return CONNECTION_STATUS_MESSAGE_400;
    } else if(statusCode == CONNECTION_STATUS_CODE_401) {
        return CONNECTION_STATUS_MESSAGE_401;
    } else if(statusCode == CONNECTION_STATUS_CODE_402) {
        return CONNECTION_STATUS_MESSAGE_402;
    } else if(statusCode == CONNECTION_STATUS_CODE_403) {
        return CONNECTION_STATUS_MESSAGE_403;
    } else if(statusCode == CONNECTION_STATUS_CODE_404) {
        return CONNECTION_STATUS_MESSAGE_404;
    } else if(statusCode == CONNECTION_STATUS_CODE_405) {
        return CONNECTION_STATUS_MESSAGE_405;
    } else if(statusCode == CONNECTION_STATUS_CODE_406) {
        return CONNECTION_STATUS_MESSAGE_406;
    } else if(statusCode == CONNECTION_STATUS_CODE_407) {
        return CONNECTION_STATUS_MESSAGE_407;
    } else if(statusCode == CONNECTION_STATUS_CODE_408) {
        return CONNECTION_STATUS_MESSAGE_408;
    } else if(statusCode == CONNECTION_STATUS_CODE_409) {
        return CONNECTION_STATUS_MESSAGE_409;
    } else if(statusCode == CONNECTION_STATUS_CODE_410) {
        return CONNECTION_STATUS_MESSAGE_410;
    } else if(statusCode == CONNECTION_STATUS_CODE_411) {
        return CONNECTION_STATUS_MESSAGE_411;
    } else if(statusCode == CONNECTION_STATUS_CODE_412) {
        return CONNECTION_STATUS_MESSAGE_412;
    } else if(statusCode == CONNECTION_STATUS_CODE_413) {
        return CONNECTION_STATUS_MESSAGE_413;
    } else if(statusCode == CONNECTION_STATUS_CODE_414) {
        return CONNECTION_STATUS_MESSAGE_414;
    } else if(statusCode == CONNECTION_STATUS_CODE_415) {
        return CONNECTION_STATUS_MESSAGE_415;
    } else if(statusCode == CONNECTION_STATUS_CODE_416) {
        return CONNECTION_STATUS_MESSAGE_416;
    } else if(statusCode == CONNECTION_STATUS_CODE_417) {
        return CONNECTION_STATUS_MESSAGE_417;
    } else if(statusCode == CONNECTION_STATUS_CODE_418) {
        return CONNECTION_STATUS_MESSAGE_418;
    } else if(statusCode == CONNECTION_STATUS_CODE_420) {
        return CONNECTION_STATUS_MESSAGE_420;
    } else if(statusCode == CONNECTION_STATUS_CODE_422) {
        return CONNECTION_STATUS_MESSAGE_422;
    } else if(statusCode == CONNECTION_STATUS_CODE_423) {
        return CONNECTION_STATUS_MESSAGE_423;
    } else if(statusCode == CONNECTION_STATUS_CODE_424) {
        return CONNECTION_STATUS_MESSAGE_424;
    } else if(statusCode == CONNECTION_STATUS_CODE_426) {
        return CONNECTION_STATUS_MESSAGE_426;
    } else if(statusCode == CONNECTION_STATUS_CODE_428) {
        return CONNECTION_STATUS_MESSAGE_428;
    } else if(statusCode == CONNECTION_STATUS_CODE_429) {
        return CONNECTION_STATUS_MESSAGE_429;
    } else if(statusCode == CONNECTION_STATUS_CODE_431) {
        return CONNECTION_STATUS_MESSAGE_431;
    } else if(statusCode == CONNECTION_STATUS_CODE_444) {
        return CONNECTION_STATUS_MESSAGE_444;
    } else if(statusCode == CONNECTION_STATUS_CODE_449) {
        return CONNECTION_STATUS_MESSAGE_449;
    } else if(statusCode == CONNECTION_STATUS_CODE_450) {
        return CONNECTION_STATUS_MESSAGE_450;
    } else if(statusCode == CONNECTION_STATUS_CODE_451) {
        return CONNECTION_STATUS_MESSAGE_451;
    } else if(statusCode == CONNECTION_STATUS_CODE_499) {
        return CONNECTION_STATUS_MESSAGE_499;
    }
    
    
    /*
     5** Server Error
     */
    else if(statusCode == CONNECTION_STATUS_CODE_500) {
        return CONNECTION_STATUS_MESSAGE_500;
    } else if(statusCode == CONNECTION_STATUS_CODE_501) {
        return CONNECTION_STATUS_MESSAGE_501;
    } else if(statusCode == CONNECTION_STATUS_CODE_502) {
        return CONNECTION_STATUS_MESSAGE_502;
    } else if(statusCode == CONNECTION_STATUS_CODE_503) {
        return CONNECTION_STATUS_MESSAGE_503;
    } else if(statusCode == CONNECTION_STATUS_CODE_504) {
        return CONNECTION_STATUS_MESSAGE_504;
    } else if(statusCode == CONNECTION_STATUS_CODE_505) {
        return CONNECTION_STATUS_MESSAGE_505;
    } else if(statusCode == CONNECTION_STATUS_CODE_506) {
        return CONNECTION_STATUS_MESSAGE_506;
    } else if(statusCode == CONNECTION_STATUS_CODE_507) {
        return CONNECTION_STATUS_MESSAGE_507;
    } else if(statusCode == CONNECTION_STATUS_CODE_508) {
        return CONNECTION_STATUS_MESSAGE_508;
    } else if(statusCode == CONNECTION_STATUS_CODE_509) {
        return CONNECTION_STATUS_MESSAGE_509;
    } else if(statusCode == CONNECTION_STATUS_CODE_510) {
        return CONNECTION_STATUS_MESSAGE_510;
    } else if(statusCode == CONNECTION_STATUS_CODE_511) {
        return CONNECTION_STATUS_MESSAGE_511;
    } else if(statusCode == CONNECTION_STATUS_CODE_598) {
        return CONNECTION_STATUS_MESSAGE_598;
    } else if(statusCode == CONNECTION_STATUS_CODE_599) {
        return CONNECTION_STATUS_MESSAGE_599;
    }
    
    
    return @"";
}


@end
