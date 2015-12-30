/**
 * [SIMINOV FRAMEWORK - CONNECT]
 * Copyright [2014-2016] [Siminov Software Solution LLP|support@siminov.com]
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

package siminov.connect.connection.design;

import java.io.InputStream;

/**
 * It is a blue print for connection response
 * It exposes API to get connection response information
 */
public interface IConnectionResponse {

	/**
	 * Get connection status code
	 * @return Status Code
	 */
	public int getStatusCode();
	
	/**
	 * Set status code
	 * @param statusCode Status Code
	 */
	public void setStatusCode(int statusCode);
	
	/**
	 * Get status message
	 * @return Status Message
	 */
	public String getStatusMessage();
	
	/**
	 * Set status message
	 * @param statusMessage Status Message
	 */
	public void setStatusMessage(String statusMessage);
	
	/**
	 * Get Response
	 * @return Response
	 */
	public InputStream getResponse();
	
	/**
	 * Set Response
	 * @param response Response
	 */
	public void setResponse(InputStream response);
}
