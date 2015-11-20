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
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Siminov.Connect.Connection
{


    /// <summary>
    /// It is a POJO class, which contains information for the service request
    /// </summary>
    public class ConnectionRequest : IConnectionRequest
    {
        private String url;

	    private String protocol;
	    private String type;
	
	    private IDictionary<String, Connect.Model.ServiceDescriptor.Request.QueryParameter> queryParameters = new Dictionary<String, Connect.Model.ServiceDescriptor.Request.QueryParameter>();
	    private IDictionary<String, Connect.Model.ServiceDescriptor.Request.HeaderParameter> headerParameters = new Dictionary<String, Connect.Model.ServiceDescriptor.Request.HeaderParameter>();
	
	    private byte[] dataStream = null;

	
	    public String GetUrl() 
        {
		    return this.url;
	    }
	
	    public void SetUrl(String url) 
        {
		    this.url = url;
	    }

	    public String GetProtocol() 
        {
		    return this.protocol;
	    }
	
	    public void SetProtocol(String protocol) 
        {
		    this.protocol = protocol;
	    }
	
	    public String GetType() 
        {
		    return this.type;
	    }
	
	    public void SetType(String type) 
        {
		    this.type = type;
	    }
	
	    public IEnumerator<String> GetQueryParameters() 
        {
		    return this.queryParameters.Keys.GetEnumerator();
	    }
	
	    public Connect.Model.ServiceDescriptor.Request.QueryParameter GetQueryParameter(String key) 
        {
		    return this.queryParameters[key];
	    }
	
	    public void AddQueryParameter(Connect.Model.ServiceDescriptor.Request.QueryParameter queryParameter) 
        {
		    this.queryParameters.Add(queryParameter.GetName(), queryParameter);
	    }
	
	    public IEnumerator<String> GetHeaderParameters() 
        {
		    return this.headerParameters.Keys.GetEnumerator();
	    }
	
	    public Connect.Model.ServiceDescriptor.Request.HeaderParameter GetHeaderParameter(String key) 
        {
		    return this.headerParameters[key];
	    }

	    public void AddHeaderParameter(Connect.Model.ServiceDescriptor.Request.HeaderParameter headerParameter) 
        {
		    this.headerParameters.Add(headerParameter.GetName(), headerParameter);
	    }

	    public byte[] GetDataStream() 
        {
		    return this.dataStream;
	    }
	
	    public void SetDataStream(byte[] dataStream) 
        {
		    this.dataStream = dataStream;
	    }

    }
}
