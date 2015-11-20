//
//  SIKIServiceEvents.h
//  Connect
//
//  Created by user on 21/04/15.
//  Copyright (c) 2015 Siminov. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "SIKServiceException.h"

@protocol SIKIConnectionRequest;
@protocol SIKIConnectionResponse;

/**
 * It exposes APIs to handle service events
 */
@protocol SIKIServiceEvents <NSObject>

/**
 This is the first method to be called when a service is created.
 
 <p>
 OnStart is always overridden to perform any startup initializations that may be required by a Service such as:
 
 <p>
 <ui>
 <li> Initializing variables
 <li> Binding static data to service
 <li> Binding related screen to service
 </ui>
 <p>
 Once OnStart has finished, Connect will call OnServiceQueue if Service is in ASYNC mode else OnServiceApiInvoke.
 *
 */
- (void)onStart;


/**
 * This method is called when the service is put in the queue for the execution.
 */
- (void)onQueue;


/**
 This method is called when there is no network. Services should override this method if they need to:
 
 <p>
 <ui>
 <li> Commit unsaved changes to persistent data
 <li> Destroy or clean up other objects consuming resources
 <li> Display any relevant alerts or dialogs
 </ui>
 *
 */
- (void)onPause;


/**
 The Connect calls this method when the Service is ready to start executing.
 <p>
 Services should override this method to perform tasks such as:
 
 <p>
 <ui>
 <li> Display any relevant alerts or dialogs
 <li> Wire up external event handlers
 <li> Listening for GPS updates
 <ui>
 *
 */
- (void)onResume;


/**
 * This is the final method that is called on a Service instance before it�s destroyed and completely removed from memory.
 <p>
 There will be no lifecycle methods called after the Activity has been destroyed.
 */
- (void)onFinish;


/**
 * This method is called before Service calls Web Service Request.
 * @param connectionRequest IConnectionRequest instance
 */
- (void)onRequestInvoke:(id<SIKIConnectionRequest>)connectionRequest;


/**
 * This method is called after Web Service Request is executed.
 * @param connectionResponse IConnectionResponse instance
 */
- (void)onRequestFinish:(id<SIKIConnectionResponse>)connectionResponse;


/**
 * This method is called when there is any exception while executing the service.
 <p>
 Once this is called the service will be terminated and release from the memory.
 * @param serviceException
 */
- (void)onTerminate:(SIKServiceException *)serviceException;

@end
