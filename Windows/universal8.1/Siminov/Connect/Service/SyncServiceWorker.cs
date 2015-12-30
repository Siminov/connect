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




using Siminov.Connect.Connection;
using Siminov.Connect.Connection.Design;
using Siminov.Connect.Exception;
using Siminov.Connect.Service.Design;
using Siminov.Core.Log;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Siminov.Connect.Service
{

    /// <summary>
    /// It handles all synchronous service request calls
    /// </summary>
    public class SyncServiceWorker
    {
	
        /// <summary>
        /// It process the service request
        /// </summary>
        /// <param name="service">Service instance</param>
	    public void Process(IService service) 
        {

		    IConnectionResponse connectionResponse = null;
		
		    try 
            {
			    connectionResponse = ConnectionManager.GetInstance().Handle(service);
		    } 
            catch(ConnectionException ce) 
            {
			    Log.Error(typeof(SyncServiceWorker).Name, "Process", "ConnectionException caught while invoking connection, " + ce.GetMessage());
			
			    service.OnTerminate(new ServiceException(ce.GetClassName(), ce.GetMethodName(), ce.GetMessage()));
			    return;
		    }
		
		    service.OnRequestFinish(connectionResponse);
	    }
    }
}
