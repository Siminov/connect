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

package siminov.connect.service.design;

import siminov.connect.connection.design.IConnectionRequest;
import siminov.connect.connection.design.IConnectionResponse;
import siminov.connect.exception.ServiceException;

public interface IServiceEvents {

	public void onStart();
	
	
	public void onQueue();
	
	
	public void onPause();
	
	
	public void onResume();
	
	
	public void onFinish();
	
	
	public void onApiInvoke(final IConnectionRequest connectionRequest);
	
	
	public void onApiFinish(final IConnectionResponse connectionResponse);

	
	public void onTerminate(final ServiceException serviceException);
	
	
	public void onRestart();
}
