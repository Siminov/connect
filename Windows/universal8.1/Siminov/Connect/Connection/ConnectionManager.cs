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
using Siminov.Connect.Service.Design;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Siminov.Connect.Connection
{


    /// <summary>
    /// It exposes API to manager connection 
    /// </summary>
    public class ConnectionManager
    {

        private static ConnectionManager connectionManager = null;

	    private IConnection httpConnection = null;
	    private IConnection httpsConnection = null;
	

        /// <summary>
        /// ConnectionManager Construction
        /// </summary>
	    private ConnectionManager() 
        {
		
		    httpConnection = new Connect.Connection.HttpConnectionWorker();
		    httpsConnection = new Connect.Connection.HttpsConnectionWorker();
	    }
	

        /// <summary>
        /// It provides an singleton instance of ConnectionManager class.
        /// </summary>
        /// <returns>ConnectionManager Instance</returns>
	    public static ConnectionManager GetInstance() 
        {
		
		    if(connectionManager == null) 
            {
			    connectionManager = new ConnectionManager();
		    }
		
		    return connectionManager;
	    }
	
	

        /// <summary>
        /// It handles the service request
        /// </summary>
        /// <param name="service">Service instance</param>
        /// <returns>IConnectionResponse instance</returns>
        /// <exception cref="Siminov.Connect.Exception.ConnectionException">If any exception occur while executing service request</exception>
	    public IConnectionResponse Handle(IService service) 
        {
		
		    IConnectionRequest connectionRequest = ConnectionHelper.PrepareConnectionRequest(service);
		
		    /*
		     * Service Event onServiceApiInvoke
		     */
		    service.OnRequestInvoke(connectionRequest);
		
		    IConnection connection = null;
		    if(connectionRequest.GetProtocol().Equals(Constants.SERVICE_DESCRIPTOR_HTTP_PROTOCOL, StringComparison.OrdinalIgnoreCase)) 
            {
			    connection = httpConnection;
		    } 
            else if(connectionRequest.GetProtocol().Equals(Constants.SERVICE_DESCRIPTOR_HTTPS_PROTOCOL, StringComparison.OrdinalIgnoreCase)) 
            {
			    connection = httpsConnection;
		    }

		
		    IConnectionResponse connectionResponse = null;
		    if(connectionRequest.GetType().Equals(Constants.SERVICE_DESCRIPTOR_REQUEST_GET_TYPE, StringComparison.OrdinalIgnoreCase)) 
            {
			    connectionResponse = connection.Get(connectionRequest);
		    }
            else if (connectionRequest.GetType().Equals(Constants.SERVICE_DESCRIPTOR_REQUEST_HEAD_TYPE, StringComparison.OrdinalIgnoreCase)) 
            {
			    connectionResponse = connection.Head(connectionRequest);
		    }
            else if (connectionRequest.GetType().Equals(Constants.SERVICE_DESCRIPTOR_REQUEST_POST_TYPE, StringComparison.OrdinalIgnoreCase)) 
            {
			    connectionResponse = connection.Post(connectionRequest);
		    }
            else if (connectionRequest.GetType().Equals(Constants.SERVICE_DESCRIPTOR_REQUEST_PUT_TYPE, StringComparison.OrdinalIgnoreCase)) 
            {
			    connectionResponse = connection.Put(connectionRequest);
		    }
            else if (connectionRequest.GetType().Equals(Constants.SERVICE_DESCRIPTOR_REQUEST_DELETE_TYPE, StringComparison.OrdinalIgnoreCase)) 
            {
			    connectionResponse = connection.Delete(connectionRequest);
		    }
            else if (connectionRequest.GetType().Equals(Constants.SERVICE_DESCRIPTOR_REQUEST_TRACE_TYPE, StringComparison.OrdinalIgnoreCase)) 
            {
			    connectionResponse = connection.Trace(connectionRequest);
		    }
            else if (connectionRequest.GetType().Equals(Constants.SERVICE_DESCRIPTOR_REQUEST_OPTIONS_TYPE, StringComparison.OrdinalIgnoreCase)) 
            {
			    connectionResponse = connection.Options(connectionRequest);
		    }
            else if (connectionRequest.GetType().Equals(Constants.SERVICE_DESCRIPTOR_REQUEST_CONNECT_TYPE, StringComparison.OrdinalIgnoreCase)) 
            {
			    connectionResponse = connection.Connect(connectionRequest);
		    }
            else if (connectionRequest.GetType().Equals(Constants.SERVICE_DESCRIPTOR_REQUEST_PATCH_TYPE, StringComparison.OrdinalIgnoreCase)) 
            {
			    connectionResponse = connection.Patch(connectionRequest);
		    }

		    return connectionResponse;
	    }
    }
}
