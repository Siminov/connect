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

package siminov.connect.connection.design;

import siminov.connect.exception.ConnectionException;

/**
 * It is a blue print for connection provider classes
 * It exposes API to interact with server
 */
public interface IConnection {

	/**
	 * It is to handle HTTP GET Method
	 * @param connectionRequest Connection Request Instance
	 * @return IConnectionResponse instance
	 * @throws ConnectionException If any exception occur while making GET request
	 */
	public IConnectionResponse get(final IConnectionRequest connectionRequest) throws ConnectionException;

	
	/**
	 * It is to handle HTTP HEAD Method
	 * @param connectionRequest Connection Request Instance
	 * @return IConnectionResponse instance
	 * @throws ConnectionException If any exception occur while making HEAD request
	 */
	public IConnectionResponse head(final IConnectionRequest connectionRequest) throws ConnectionException;
	
	
	/**
	 * It is to handle HTTP POST Method
	 * @param connectionRequest Connection Request Instance
	 * @return IConnectionResponse instance
	 * @throws ConnectionException If any exception occur while making POST request
	 */
	public IConnectionResponse post(final IConnectionRequest connectionRequest) throws ConnectionException;
	
	
	/**
	 * It is to handle HTTP PUT Method
	 * @param connectionRequest Connection Request Instance
	 * @return IConnectionResponse instance
	 * @throws ConnectionException If any exception occur while making PUT request
	 */
	public IConnectionResponse put(final IConnectionRequest connectionRequest) throws ConnectionException;

	
	/**
	 * It is to handle HTTP DELETE Method
	 * @param connectionRequest Connection Request Instance
	 * @return IConnectionResponse instance
	 * @throws ConnectionException If any exception occur while making DELETE request
	 */
	public IConnectionResponse delete(final IConnectionRequest connectionRequest) throws ConnectionException;

	
	/**
	 * It is to handle HTTP TRACE Method
	 * @param connectionRequest Connection Request Instance
	 * @return IConnectionResponse instance
	 * @throws ConnectionException If any exception occur while making TRACE request
	 */
	public IConnectionResponse trace(final IConnectionRequest connectionRequest) throws ConnectionException;

	
	/**
	 * It is to handle HTTP OPTIONS Method
	 * @param connectionRequest Connection Request Instance
	 * @return IConnectionResponse instance
	 * @throws ConnectionException If any exception occur while making OPTIONS request
	 */
	public IConnectionResponse options(final IConnectionRequest connectionRequest) throws ConnectionException;

	
	/**
	 * It is to handle HTTP CONNECT Method
	 * @param connectionRequest Connection Request Instance
	 * @return IConnectionResponse instance
	 * @throws ConnectionException If any exception occur while making CONNECT request
	 */
	public IConnectionResponse connect(final IConnectionRequest connectionRequest) throws ConnectionException;

	
	/**
	 * It is to handle HTTP PATCH Method
	 * @param connectionRequest Connection Request Instance
	 * @return IConnectionResponse instance
	 * @throws ConnectionException If any exception occur while making PATCH request
	 */
	public IConnectionResponse patch(final IConnectionRequest connectionRequest) throws ConnectionException;
}
