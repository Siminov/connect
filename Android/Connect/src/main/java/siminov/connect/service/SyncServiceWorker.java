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

package siminov.connect.service;

import siminov.connect.connection.ConnectionManager;
import siminov.connect.connection.design.IConnectionResponse;
import siminov.connect.exception.ConnectionException;
import siminov.connect.exception.ServiceException;
import siminov.connect.service.design.IService;
import siminov.core.log.Log;

/**
 * It handles all synchronous service request calls
 */
public class SyncServiceWorker {

	
	/**
	 * It process the service request
	 * @param service Service instance
	 */
	public void process(final IService service) {

		IConnectionResponse connectionResponse = null;
		
		try {
			connectionResponse = ConnectionManager.getInstance().handle(service);
		} catch(ConnectionException ce) {
			Log.error(SyncServiceWorker.class.getName(), "process", "ConnectionException caught while invoking connection, " + ce.getMessage());
			
			service.onTerminate(new ServiceException(ce.getClassName(), ce.getMethodName(), ce.getMessage()));
			return;
		}
		
		service.onRequestFinish(connectionResponse);
	}
}
