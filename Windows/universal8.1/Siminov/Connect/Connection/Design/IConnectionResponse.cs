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



using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Siminov.Connect.Connection.Design
{


    /// <summary>
    /// It is a blue print for connection response
    /// It exposes API to get connection response information
    /// </summary>
    public interface IConnectionResponse
    {

        /// <summary>
        /// Get connection status code
        /// </summary>
        /// <returns>Status Code</returns>
        int GetStatusCode();


        /// <summary>
        /// Set status code
        /// </summary>
        /// <param name="statusCode">Status Code</param>
        void SetStatusCode(int statusCode);


        /// <summary>
        /// Get status message
        /// </summary>
        /// <returns>Status Message</returns>
        String GetStatusMessage();


        /// <summary>
        /// Set status message
        /// </summary>
        /// <param name="statusMessage">Status Message</param>
        void SetStatusMessage(String statusMessage);


        /// <summary>
        /// Get Response
        /// </summary>
        /// <returns>Response</returns>
        Stream GetResponse();


        /// <summary>
        /// Set Response
        /// </summary>
        /// <param name="response">Response</param>
        void SetResponse(Stream response);

    }
}
