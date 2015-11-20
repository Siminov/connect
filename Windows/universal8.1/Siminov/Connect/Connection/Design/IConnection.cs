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



using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Siminov.Connect.Connection.Design
{


    /// <summary>
    /// It is a blue print for connection provider classes
    /// It exposes API to interact with server
    /// </summary>
    public interface IConnection
    {

        /// <summary>
        /// It is to handle HTTP GET Method
        /// </summary>
        /// <param name="connectionRequest">Connection Request Instance</param>
        /// <returns>IConnectionResponse instance</returns>
        /// <exception cref="Siminov.Connect.Exception.ConnectionException">If any exception occur while making GET request</exception>
	    IConnectionResponse Get(IConnectionRequest connectionRequest);

	

        /// <summary>
        /// It is to handle HTTP HEAD Method
        /// </summary>
        /// <param name="connectionRequest">Connection Request Instance</param>
        /// <returns>IConnectionResponse instance</returns>
        /// <exception cref="Siminov.Connect.Exception.ConnectionException">If any exception occur while making HEAD request</exception>
	    IConnectionResponse Head(IConnectionRequest connectionRequest);
	
	

        /// <summary>
        /// It is to handle HTTP POST Method
        /// </summary>
        /// <param name="connectionRequest">Connection Request Instance</param>
        /// <returns>IConnectionResponse instance</returns>
        /// <exception cref="Siminov.Connect.Exception.ConnectionException">If any exception occur while making POST request</exception>
	    IConnectionResponse Post(IConnectionRequest connectionRequest);
	
	

        /// <summary>
        /// It is to handle HTTP PUT Method
        /// </summary>
        /// <param name="connectionRequest">Connection Request Instance</param>
        /// <returns>IConnectionResponse instance</returns>
        /// <exception cref="Siminov.Connect.Excpetion.ConnectionException">If any exception occur while making PUT request</exception>
	    IConnectionResponse Put(IConnectionRequest connectionRequest);

	

        /// <summary>
        /// It is to handle HTTP DELETE Method
        /// </summary>
        /// <param name="connectionRequest">Connection Request Instance</param>
        /// <returns>IConnectionResponse instance</returns>
        /// <exception cref="Siminov.Connect.Exception.ConnectionException">If any exception occur while making DELETE request</exception>
	    IConnectionResponse Delete(IConnectionRequest connectionRequest);


        /// <summary>
        /// It is to handle HTTP TRACE Method
        /// </summary>
        /// <param name="connectionRequest">Connection Request Instance</param>
        /// <returns>IConnectionResponse instance</returns>
        /// <exception cref="Siminov.Connect.Exception.ConnectionException">If any exception occur while making TRACE request</exception>
	    IConnectionResponse Trace(IConnectionRequest connectionRequest);

	

        /// <summary>
        /// It is to handle HTTP OPTIONS Method
        /// </summary>
        /// <param name="connectionRequest">Connection Request Instance</param>
        /// <returns>IConnectionResponse instance</returns>
        /// <exception cref="Siminov.Connect.Exception.ConnectionException">If any exception occur while making OPTIONS request</exception>
	    IConnectionResponse Options(IConnectionRequest connectionRequest);

	

        /// <summary>
        /// It is to handle HTTP CONNECT Method
        /// </summary>
        /// <param name="connectionRequest">Connection Request Instance</param>
        /// <returns>IConnectionResponse instance</returns>
        /// <exception cref="Siminov.Connect.Exception.ConnectionException">If any exception occur while making CONNECT request</exception>
	    IConnectionResponse Connect(IConnectionRequest connectionRequest);

	

        /// <summary>
        /// It is to handle HTTP PATCH Method
        /// </summary>
        /// <param name="connectionRequest">Connection Request Instance</param>
        /// <returns>IConnectionResponse instance</returns>
        /// <exception cref="Siminov.Connect.Exception.ConnectionException">If any exception occur while making PATCH request</exception>
	    IConnectionResponse Patch(IConnectionRequest connectionRequest);

    }
}
