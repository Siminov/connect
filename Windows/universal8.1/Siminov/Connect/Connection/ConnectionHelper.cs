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



using Siminov.Connect.Connection.Design;
using Siminov.Connect.Model;
using Siminov.Connect.Service.Design;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Siminov.Connect.Connection
{


    /// <summary>
    /// It provides Utility APIs for the communication
    /// </summary>
    public class ConnectionHelper
    {

        /// <summary>
        /// It build connection request instance based on the service descriptor object
        /// </summary>
        /// <param name="service">Instance of IService</param>
        /// <returns>IConnectionRequest instance</returns>
	    public static IConnectionRequest PrepareConnectionRequest(IService service) 
        {

		    /*
		     * Resolve All Referring Resources
		     */
		    ServiceDescriptor serviceDescriptor = service.GetServiceDescriptor();
		    Connect.Model.ServiceDescriptor.Request request = serviceDescriptor.GetRequest(service.GetRequest());
		
		    String url = FormUrl(service);
		
		    IEnumerator<Connect.Model.ServiceDescriptor.Request.QueryParameter> queryParameters = FormQueryParameters(service);
		    IEnumerator<Connect.Model.ServiceDescriptor.Request.HeaderParameter> headerParameters = FormHeaderParameters(service);
		
		    byte[] dataStream = FormDataStream(service);
		
		    IConnectionRequest connectionRequest = new ConnectionRequest();
		    connectionRequest.SetUrl(url);
		    connectionRequest.SetProtocol(serviceDescriptor.GetProtocol());
		    connectionRequest.SetType(request.GetType());
		
		    while(queryParameters.MoveNext()) 
            {
			    connectionRequest.AddQueryParameter(queryParameters.Current);
		    }
		
		    while(headerParameters.MoveNext()) 
            {
			    connectionRequest.AddHeaderParameter(headerParameters.Current);
		    }

		    connectionRequest.SetDataStream(dataStream);

		    return connectionRequest;
	    }


	    private static String FormUrl(IService service) 
        {
		
		    ServiceDescriptor serviceDescriptor = service.GetServiceDescriptor();
		
		    String requestName = service.GetRequest();
		    Connect.Model.ServiceDescriptor.Request request = serviceDescriptor.GetRequest(requestName);
		
		    String protocol = serviceDescriptor.GetProtocol();
		
		    String instance = serviceDescriptor.GetInstance();
		    String port = serviceDescriptor.GetPort();
		
		    String context = serviceDescriptor.GetContext();
		    String apiPath = request.GetApi();
		
		    StringBuilder url = new StringBuilder();

			    if(protocol != null) 
                {

				    if(protocol.Equals(Constants.SERVICE_DESCRIPTOR_HTTP_PROTOCOL, StringComparison.OrdinalIgnoreCase)) 
                    {
					    url.Append(Constants.CONNECTION_HTTP);
				    } else if(protocol.Equals(Constants.SERVICE_DESCRIPTOR_HTTPS_PROTOCOL, StringComparison.OrdinalIgnoreCase)) 
                    {
					    url.Append(Constants.CONNECTION_HTTPS);
				    }

				    url.Append("://");
			    }
				
			    url.Append(instance);

			    if(port != null && port.Length > 0) 
                {
				    url.Append(":" + port);
			    }
			
			    if(context != null && context.Length > 0) 
                {
				    url.Append("/" + context);
			    }
			
			    if(apiPath != null && apiPath.Length > 0) 
                {
				    url.Append("/" + apiPath);
			    }
			
		    return url.ToString();
	    }
	
	    private static IEnumerator<Connect.Model.ServiceDescriptor.Request.QueryParameter> FormQueryParameters(IService service) 
        {
		
		    ServiceDescriptor serviceDescriptor = service.GetServiceDescriptor();
		
		    String requestName = service.GetRequest();
		    Connect.Model.ServiceDescriptor.Request request = serviceDescriptor.GetRequest(requestName);
		
		    return request.GetQueryParameters();
	    }
	
	    private static IEnumerator<Connect.Model.ServiceDescriptor.Request.HeaderParameter> FormHeaderParameters(IService service) 
        {

		    ServiceDescriptor serviceDescriptor = service.GetServiceDescriptor();
		
		    String requestName = service.GetRequest();
		    Connect.Model.ServiceDescriptor.Request request = serviceDescriptor.GetRequest(requestName);
		
		    return request.GetHeaderParameters();
	    }
	
	    private static byte[] FormDataStream(IService service) 
        {
		
		    ServiceDescriptor serviceDescriptor = service.GetServiceDescriptor();
		
		    String requestName = service.GetRequest();
		    Connect.Model.ServiceDescriptor.Request request = serviceDescriptor.GetRequest(requestName);

		    if(request.GetDataStream() == null) 
            {
			    return new byte[0];
		    }


            return System.Text.Encoding.UTF8.GetBytes(request.GetDataStream()); 
	    }
    }
}
