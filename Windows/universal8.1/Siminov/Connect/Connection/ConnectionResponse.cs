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



using Siminov.Connect.Connection.Design;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Siminov.Connect.Connection
{
    public class ConnectionResponse : IConnectionResponse
    {
        private int statusCode;
        private String statusMessage;

        private Stream response;


        /// <summary>
        /// ConnectionResponse Constructor
        /// </summary>
        /// <param name="statusCode">Status Code</param>
        /// <param name="statusMessage">Status Message</param>
        public ConnectionResponse(int statusCode, String statusMessage)
        {
            this.statusCode = statusCode;
            this.statusMessage = statusMessage;
        }


        /// <summary>
        /// ConnectionResponse Constructor
        /// </summary>
        /// <param name="statusCode">Status Code</param>
        /// <param name="response">Stream</param>
        public ConnectionResponse(int statusCode, Stream response)
        {
            this.statusCode = statusCode;
            this.response = response;
        }

        public int GetStatusCode()
        {
            return this.statusCode;
        }

        public void SetStatusCode(int statusCode)
        {
            this.statusCode = statusCode;
        }

        public String GetStatusMessage()
        {
            return this.statusMessage;
        }

        public void SetStatusMessage(String statusMessage)
        {
            this.statusMessage = statusMessage;
        }

        public Stream GetResponse()
        {
            return this.response;
        }

        public void SetResponse(Stream response)
        {
            this.response = response;
        }
    }
}
