/** 
 * [SIMINOV FRAMEWORK]
 * Copyright [2015] [Siminov Software Solution LLP|support@siminov.com]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 **/



using Siminov.Connect.Connection.Design;
using Siminov.Connect.Exception;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Siminov.Connect.Service.Design
{

    /// <summary>
    /// It exposes APIs to handle service events
    /// </summary>
    public interface IServiceEvents
    {

        /// <summary>
		///    This is the first method to be called when a service is created. 
		///
		///    <p>
		///    OnStart is always overridden to perform any startup initializations that may be required by a Service such as:
		///
		///    <p>
		///    <ui>
		///	    <li> Initializing variables
		///	    <li> Binding static data to service
		///	    <li> Binding related screen to service
		///    </ui>
		///    <p>
        ///    Once OnStart has finished, Connect will call OnServiceQueue if Service is in ASYNC mode else OnServiceApiInvoke.	
        /// 
        /// </summary>
	    void OnStart();
	
	
        /// <summary>
        /// This method is called when the service is put in the queue for the execution. 
        /// </summary>
	    void OnQueue();
	
	
        /// <summary>
		///    This method is called when there is no network. Services should override this method if they need to:
        ///
		///    <p>
		///    <ui>
		///	    <li> Commit unsaved changes to persistent data
		///	    <li> Destroy or clean up other objects consuming resources
		///	    <li> Display any relevant alerts or dialogs
        ///    </ui>
        /// 
        /// </summary>
	    void OnPause();
	
        /// <summary>
        /// The Connect calls this method when the Service is ready to start executing. 
		///    <p>
		///    Services should override this method to perform tasks such as:		
		///	
		///    <p>
		///    <ui>	
		///	    <li> Display any relevant alerts or dialogs
		///	    <li> Wire up external event handlers
		///	    <li> Listening for GPS updates
        ///    <ui>

        /// </summary>
	    void OnResume();
	
        /// <summary>
        /// This is the final method that is called on a Service instance before it’s destroyed and completely removed from memory.
		///    <p>
        ///    There will be no lifecycle methods called after the Activity has been destroyed.
        /// </summary>
	    void OnFinish();
	
	
        /// <summary>
        /// This method is called before Service calls Web Service Request. 
        /// </summary>
        /// <param name="connectionRequest">IConnectionRequest instance</param>
	    void OnRequestInvoke(IConnectionRequest connectionRequest);
	
	
        /// <summary>
        /// This method is called after Web Service Request is executed.
        /// </summary>
        /// <param name="connectionResponse">IConnectionResponse instance</param>
	    void OnRequestFinish(IConnectionResponse connectionResponse);

	
        /// <summary>
	    /// This method is called when there is any exception while executing the service. 
		///    <p>
        ///    Once this is called the service will be terminated and release from the memory.
        /// </summary>
        /// <param name="serviceException"></param>
	    void OnTerminate(ServiceException serviceException);
    }
}
