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


public interface IConnection {

	public IConnectionResponse get(final IConnectionRequest connectionRequest) throws ConnectionException;

	
	public IConnectionResponse head(final IConnectionRequest connectionRequest) throws ConnectionException;
	
	
	public IConnectionResponse post(final IConnectionRequest connectionRequest) throws ConnectionException;
	
	
	public IConnectionResponse put(final IConnectionRequest connectionRequest) throws ConnectionException;

	
	public IConnectionResponse delete(final IConnectionRequest connectionRequest) throws ConnectionException;

	
	public IConnectionResponse trace(final IConnectionRequest connectionRequest) throws ConnectionException;

	
	public IConnectionResponse options(final IConnectionRequest connectionRequest) throws ConnectionException;

	
	public IConnectionResponse connect(final IConnectionRequest connectionRequest) throws ConnectionException;

	
	public IConnectionResponse patch(final IConnectionRequest connectionRequest) throws ConnectionException;
}
