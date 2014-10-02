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

package siminov.connect.events;

import siminov.connect.sync.design.ISyncRequest;

/**
 * It is a blue print for class which handles sync events
 */
public interface ISyncEvents {

	/**
	 * This method is called then a Sync is started.
	 * <p> In this you can initialize resources related to Sync.
	 * <p> Once OnStart has finished, Connect will call OnQueue.
	 * @param syncRequest ISyncRequest instance
	 */
	public void onStart(ISyncRequest syncRequest);

	
	/**
	 * This method is called then the Sync request is added to the Queue.
	 * @param syncRequest ISyncRequest instance
	 */
	public void onQueue(ISyncRequest syncRequest);

	
	/**
	 * This method is called then Sync request completes its all synchronization data with web service.
	 * @param syncRequest ISyncRequest instance
	 */
	public void onFinish(ISyncRequest syncRequest);

	
	/**
	 * This method is called if there is any error/exception while synchronizing data with web service
	 * @param syncRequest ISyncRequest instance
	 */
	public void onTerminate(ISyncRequest syncRequest);
}
