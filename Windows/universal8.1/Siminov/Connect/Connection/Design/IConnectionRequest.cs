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
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Siminov.Connect.Connection.Design
{


    /// <summary>
    /// It contain the blue print for the connection request object
    /// </summary>
    public interface IConnectionRequest
    {

        /// <summary>
        /// Get request URL
        /// </summary>
        /// <returns>URL</returns>
	    String GetUrl();
	

        /// <summary>
        /// Set request URL
        /// </summary>
        /// <param name="url">URL</param>
	    void SetUrl(String url);


        /// <summary>
        /// Get request protocol
        /// </summary>
        /// <returns>Protocol (HTTP/HTTPS)</returns>
	    String GetProtocol();
	

        /// <summary>
        /// Set request protocol
        /// </summary>
        /// <param name="protocol">Protocol</param>
	    void SetProtocol(String protocol);
	
	

        /// <summary>
        /// Get request type 
        /// </summary>
        /// <returns>Type</returns>
	    String GetType();
	

        /// <summary>
        /// Set request type
        /// </summary>
        /// <param name="type">Request Type</param>
	    void SetType(String type);
	

        /// <summary>
        /// Get all query parameters
        /// </summary>
        /// <returns>Query Parameters</returns>
	    IEnumerator<String> GetQueryParameters();
	

        /// <summary>
        /// Get query parameter
        /// </summary>
        /// <param name="key">Name of query parameter</param>
        /// <returns>Query Parameter</returns>
	    Connect.Model.ServiceDescriptor.Request.QueryParameter GetQueryParameter(String key);
	

        /// <summary>
        /// Add query parameter
        /// </summary>
        /// <param name="queryParameter">Query Parameter</param>
	    void AddQueryParameter(Connect.Model.ServiceDescriptor.Request.QueryParameter queryParameter);
	

        /// <summary>
        /// Get all header parameters
        /// </summary>
        /// <returns>Header Parameter</returns>
	    IEnumerator<String> GetHeaderParameters();
	

        /// <summary>
        /// Get header parameter
        /// </summary>
        /// <param name="key">Name of header parameter</param>
        /// <returns>Header Parameter</returns>
	    Connect.Model.ServiceDescriptor.Request.HeaderParameter GetHeaderParameter(String key);


        /// <summary>
        /// Add header parameter
        /// </summary>
        /// <param name="headerParameter">Header Parameter</param>
	    void AddHeaderParameter(Connect.Model.ServiceDescriptor.Request.HeaderParameter headerParameter);


        /// <summary>
        /// Get data stream
        /// </summary>
        /// <returns>Data Stream</returns>
	    byte[] GetDataStream();
	

        /// <summary>
        /// Set data stream
        /// </summary>
        /// <param name="dataStream">Data Stream</param>
	    void SetDataStream(byte[] dataStream);

    }
}
