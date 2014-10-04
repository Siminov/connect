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


package siminov.connect;


/**
 * It is a blue print for classes which want to perform background processes
 * It exposes APIs to start, stop worker
 */
public interface IWorker {

	/**
	 * Start the worker to handle the requests
	 */
	public void startWorker();
	
	
	/**
	 * Stop the worker
	 */
	public void stopWorker();

	
	/**
	 * Check whether worker is running or not
	 * @return (true/false) TRUE: If worker is running | FALSE: If worker is not running
	 */
	public boolean isWorkerRunning();
	
	
	
	/**
	 * Add request
	 * @param request IRequest
	 */
	public void addRequest(IRequest request);
	
	
	/**
	 * Remove request
	 * @param request IRequest
	 */
	public void removeRequest(IRequest request);
	
	
	/**
	 * Check whether it contains request or not
	 * @param request (true/false) TRUE: If it contains the request | FALSE: If it does not contains the request
	 */
	public boolean containsRequest(IRequest request);
}
