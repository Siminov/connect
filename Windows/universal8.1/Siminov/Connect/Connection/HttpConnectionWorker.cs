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
using Siminov.Connect.Exception;
using Siminov.Connect.Utils;
using Siminov.Core.Exception;
using Siminov.Core.Log;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Net.Http;
using System.Text;
using System.Threading.Tasks;

namespace Siminov.Connect.Connection
{


    /// <summary>
    /// It implements IConnection to handle HTTP requests
    /// </summary>
    public class HttpConnectionWorker : IConnection
    {
        public IConnectionResponse Get(IConnectionRequest connectionRequest) 
        {

		    if(connectionRequest == null) 
            {
			    Log.Error(typeof(HttpConnectionWorker).Name, "Get", "Invalid Connection Request.");
			    throw new ConnectionException(typeof(HttpConnectionWorker).Name, "Get", "Invalid Connection Request.");
		    }

            /*if (connectionRequest.GetUrl().Contains("get_books"))
            {
                String books = "<books><book><name>C</name><description>C Description</description><author>C Author</author><link>C Link</link></book><book><name>C Plus</name><description>C Plus Description</description><author>C Plus Author</author><link>C Plus Link</link></book><book><name>C Sharp</name><description>C Sharp Description</description><author>C Sharp Author</author><link>C Sharp Link</link></book><book><name>Java</name><description>Java Description</description><author>Java Author</author><link>Java Link</link></book><book><name>JavaScript</name><description>JavaScript Description</description><author>JavaScript Author</author><link>JavaScript Link</link></book><book><name>Swift</name><description>Swift Description</description><author>Swift Author</author><link>Swift Link</link></book><book><name>Objective C</name><description>Objective C Description</description><author>Objective C Author</author><link>Objective C Link</link></book></books>";
                Stream booksStream;

                byte[] bytes = new byte[books.Length * sizeof(char)];
                System.Buffer.BlockCopy(books.ToCharArray(), 0, bytes, 0, bytes.Length);

                try
                {
                    booksStream = Utils.Utils.ToStream(bytes);
                    return new ConnectionResponse(200, booksStream);
                }
                catch (SiminovException se)
                {

                }
            }
            else
            {
                String lessions = "<lessions><lession><name>C First Lession</name><description>C First Lession Description</description><link>C First Lession Link</link></lession><lession><name>C Second Lession</name><description>C Second Lession Description</description><link>C Second Lession Link</link></lession></lessions>";
                Stream lessionsStream;

                byte[] bytes = new byte[lessions.Length * sizeof(char)];
                System.Buffer.BlockCopy(lessions.ToCharArray(), 0, bytes, 0, bytes.Length);

                try
                {
                    lessionsStream = Utils.Utils.ToStream(bytes);
                    return new ConnectionResponse(200, lessionsStream);
                }
                catch (SiminovException se)
                {

                }
            }

            return new ConnectionResponse(200, "");*/
		

		    /*
		     * Request Parameters
		     */
		    StringBuilder url = new StringBuilder(connectionRequest.GetUrl());
		
		    IEnumerator<String> queryParameters = connectionRequest.GetQueryParameters();
		    IEnumerator<String> headerParameters = connectionRequest.GetHeaderParameters();
		
		
		    /*
		     * Make Request
		     */
            HttpClient httpClient = new HttpClient();
            HttpResponseMessage httpResponse;

		    /*
		     * Add Query Parameters
		     */
            int count = 0;
		    while(queryParameters.MoveNext()) 
            {
			
			    String parameter = queryParameters.Current;
			    Connect.Model.ServiceDescriptor.Request.QueryParameter queryParameter = connectionRequest.GetQueryParameter(parameter);
			
                if(count == 0) 
                {
                    url.Append("?");
                    url.Append(parameter + "=" + queryParameter.GetValue());
                } 
                else
                {
                    url.Append("&");
                    url.Append(parameter + "=" + queryParameter.GetValue());
                }
		    }
				
		    /*
		     * Add Header Parameters
		     */
		    while(headerParameters.MoveNext()) 
            {
			
			    String header = headerParameters.Current;
			    Connect.Model.ServiceDescriptor.Request.HeaderParameter headerParameter = connectionRequest.GetHeaderParameter(header);

                httpClient.DefaultRequestHeaders.TryAddWithoutValidation(header, headerParameter.GetValue());
		    }
		

            /* execute */
        
            try 
            {
                Task<HttpResponseMessage> httpResponseMessage = httpClient.GetAsync(url.ToString());
                httpResponseMessage.Wait();

                httpResponse = httpResponseMessage.Result; 
            } 
            catch(System.Exception exception) 
            {
        	    Log.Error(typeof(HttpConnectionWorker).Name, "Get", "Exception caught while executing request, " + exception.Message);
        	    throw new ConnectionException(typeof(HttpConnectionWorker).Name, "Get", "Exception caught while executing request, " + exception.Message);
            }


            /* return response */
            if(!httpResponse.IsSuccessStatusCode) 
            {
                return (IConnectionResponse)new ConnectionResponse(new ConnectionStatusCodes().GetStatusCode(httpResponse.StatusCode.ToString()), new ConnectionStatusCodes().GetStatusMessage(Convert.ToInt32(httpResponse.StatusCode.ToString())));
            }


            Task<Stream> repsonseStreamAsync = httpResponse.Content.ReadAsStreamAsync();
            repsonseStreamAsync.Wait();

            Stream responseStream = repsonseStreamAsync.Result;

            return (IConnectionResponse)new ConnectionResponse(new ConnectionStatusCodes().GetStatusCode(httpResponse.StatusCode.ToString()), responseStream);
	    }
	
	    public IConnectionResponse Head(IConnectionRequest connectionRequest) 
        {
		
		    if(connectionRequest == null) 
            {
			    Log.Error(typeof(HttpConnectionWorker).Name, "Head", "Invalid Connection Request.");
                throw new ConnectionException(typeof(HttpConnectionWorker).Name, "Head", "Invalid Connection Request.");
		    }


		    /*
		     * Request Parameters
		     */
		    StringBuilder url = new StringBuilder(connectionRequest.GetUrl());
		
		    IEnumerator<String> queryParameters = connectionRequest.GetQueryParameters();
		    IEnumerator<String> headerParameters = connectionRequest.GetHeaderParameters();
		
		
		    /*
		     * Make Request
		     */
            HttpClient httpClient = new HttpClient();
            HttpResponseMessage httpResponse;


		    /*
		     * Add Query Parameters
		     */
            int count = 0;
		    while(queryParameters.MoveNext()) 
            {
			
			    String parameter = queryParameters.Current;
			    Connect.Model.ServiceDescriptor.Request.QueryParameter queryParameter = connectionRequest.GetQueryParameter(parameter);

                if (count == 0)
                {
                    url.Append("?");
                    url.Append(parameter + "=" + queryParameter.GetValue());
                }
                else
                {
                    url.Append("&");
                    url.Append(parameter + "=" + queryParameter.GetValue());
                }
		    }


		    /*
		     * Add Header Parameters
		     */
		    while(headerParameters.MoveNext()) {
			
			    String header = headerParameters.Current;
			    Connect.Model.ServiceDescriptor.Request.HeaderParameter headerParameter = connectionRequest.GetHeaderParameter(header);

                httpClient.DefaultRequestHeaders.TryAddWithoutValidation(header, headerParameter.GetValue());
		    }
		

            /* execute */
            
            try 
            {
                Task<HttpResponseMessage> httpResponseMessage = null;// httpClient.SendAsync(url.ToString());
                httpResponseMessage.Wait();

                httpResponse = httpResponseMessage.Result;    	 
            } 
            catch(System.Exception exception) 
            {
                Log.Error(typeof(HttpConnectionWorker).Name, "Head", "Exception caught while executing request, " + exception.Message);
                throw new ConnectionException(typeof(HttpConnectionWorker).Name, "Head", "Exception caught while executing request, " + exception.Message);
            }


            /* return response */
            if (!httpResponse.IsSuccessStatusCode)
            {
                return (IConnectionResponse)new ConnectionResponse(new ConnectionStatusCodes().GetStatusCode(httpResponse.StatusCode.ToString()), new ConnectionStatusCodes().GetStatusMessage(Convert.ToInt32(httpResponse.StatusCode.ToString())));
            }


            Task<Stream> repsonseStreamAsync = httpResponse.Content.ReadAsStreamAsync();
            repsonseStreamAsync.Wait();

            Stream responseStream = repsonseStreamAsync.Result;

            return (IConnectionResponse)new ConnectionResponse(new ConnectionStatusCodes().GetStatusCode(httpResponse.StatusCode.ToString()), responseStream);
        }
	
	    public IConnectionResponse Post(IConnectionRequest connectionRequest) 
        {
		
		    if(connectionRequest == null) 
            {
			    Log.Error(typeof(HttpConnectionWorker).Name, "Post", "Invalid Connection Request.");
                throw new ConnectionException(typeof(HttpConnectionWorker).Name, "Post", "Invalid Connection Request.");
		    }


		    /*
		     * Request Parameters
		     */
		    StringBuilder url = new StringBuilder(connectionRequest.GetUrl());
		
		    IEnumerator<String> queryParameters = connectionRequest.GetQueryParameters();
		    IEnumerator<String> headerParameters = connectionRequest.GetHeaderParameters();
		
		    byte[] dataStream = connectionRequest.GetDataStream();
		

		
		    /*
		     * Add Query Parameters
		     */
            int count = 0;
		    while(queryParameters.MoveNext()) 
            {
			
			    String parameter = queryParameters.Current;
			    Connect.Model.ServiceDescriptor.Request.QueryParameter queryParameter = connectionRequest.GetQueryParameter(parameter);

                if (count == 0)
                {
                    url.Append("?");
                    url.Append(parameter + "=" + queryParameter.GetValue());
                }
                else
                {
                    url.Append("&");
                    url.Append(parameter + "=" + queryParameter.GetValue());
                }
		    }
		
		
		    /*
		     * Make Request
		     */
            HttpClient httpClient = new HttpClient();
            HttpResponseMessage httpResponse;
		
		
		    /*
		     * Add Header Parameters
		     */
		    while(headerParameters.MoveNext()) 
            {
			
			    String header = headerParameters.Current;
			    Connect.Model.ServiceDescriptor.Request.HeaderParameter headerParameter = connectionRequest.GetHeaderParameter(header);

                httpClient.DefaultRequestHeaders.TryAddWithoutValidation(header, headerParameter.GetValue());
		    }
		
            		
		    /* execute */
           
            try 
            {
                HttpContent httpContent = new StreamContent(Utils.Utils.ToStream(connectionRequest.GetDataStream()));
                Task<HttpResponseMessage> httpResponseMessage = httpClient.PostAsync(url.ToString(), httpContent);
                httpResponseMessage.Wait();

                httpResponse = httpResponseMessage.Result;      	 
            } 
            catch(System.Exception exception) 
            {
                Log.Error(typeof(HttpConnectionWorker).Name, "Post", "Exception caught while executing request, " + exception.Message);
                throw new ConnectionException(typeof(HttpConnectionWorker).Name, "Post", "Exception caught while executing request, " + exception.Message);
            }
                    
            
            /* return response */
            if(!httpResponse.IsSuccessStatusCode) 
            {
                return (IConnectionResponse)new ConnectionResponse(new ConnectionStatusCodes().GetStatusCode(httpResponse.StatusCode.ToString()), new ConnectionStatusCodes().GetStatusMessage(Convert.ToInt32(httpResponse.StatusCode.ToString())));
            }


            Task<Stream> repsonseStreamAsync = httpResponse.Content.ReadAsStreamAsync();
            repsonseStreamAsync.Wait();

            Stream responseStream = repsonseStreamAsync.Result;

            return (IConnectionResponse)new ConnectionResponse(new ConnectionStatusCodes().GetStatusCode(httpResponse.StatusCode.ToString()), responseStream);
	    }
	
	    public IConnectionResponse Put(IConnectionRequest connectionRequest) 
        {
		
		    if(connectionRequest == null) 
            {
			    Log.Error(typeof(HttpConnectionWorker).Name, "Put", "Invalid Connection Request.");
                throw new ConnectionException(typeof(HttpConnectionWorker).Name, "Put", "Invalid Connection Request.");
		    }



		    /*
		     * Request Parameters
		     */
		    StringBuilder url = new StringBuilder(connectionRequest.GetUrl());
		
		    IEnumerator<String> queryParameters = connectionRequest.GetQueryParameters();
		    IEnumerator<String> headerParameters = connectionRequest.GetHeaderParameters();
		
		    byte[] dataStream = connectionRequest.GetDataStream();
		
		
		    /*
		     * Add Query Parameters
		     */
            int count = 0; 
		    while(queryParameters.MoveNext()) 
            {
			
			    String parameter = queryParameters.Current;
			    Connect.Model.ServiceDescriptor.Request.QueryParameter queryParameter = connectionRequest.GetQueryParameter(parameter);

                if (count == 0)
                {
                    url.Append("?");
                    url.Append(parameter + "=" + queryParameter.GetValue());
                }
                else
                {
                    url.Append("&");
                    url.Append(parameter + "=" + queryParameter.GetValue());
                }
            }
		
		
		    /*
		     * Make Request
		     */
            HttpClient httpClient = new HttpClient();
            HttpResponseMessage httpResponse;

		    /*
		     * Add Header Parameters
		     */
		    while(headerParameters.MoveNext()) 
            {
			
			    String header = headerParameters.Current;
			    Connect.Model.ServiceDescriptor.Request.HeaderParameter headerParameter = connectionRequest.GetHeaderParameter(header);
			
	            httpClient.DefaultRequestHeaders.TryAddWithoutValidation(header, headerParameter.GetValue());
		    }
		
		
            /* execute */
           
            try 
            {
        	    HttpContent httpContent = new StreamContent(Utils.Utils.ToStream(connectionRequest.GetDataStream()));
                Task<HttpResponseMessage> httpResponseMessage = httpClient.PutAsync(url.ToString(), httpContent);
                httpResponseMessage.Wait();

                httpResponse = httpResponseMessage.Result;         	 
            } 
            catch(System.Exception exception) 
            {
                Log.Error(typeof(HttpConnectionWorker).Name, "Put", "Exception caught while executing request, " + exception.Message);
                throw new ConnectionException(typeof(HttpConnectionWorker).Name, "Put", "Exception caught while executing request, " + exception.Message);
            }



            /* return response */
            if (!httpResponse.IsSuccessStatusCode)
            {
                return (IConnectionResponse)new ConnectionResponse(new ConnectionStatusCodes().GetStatusCode(httpResponse.StatusCode.ToString()), new ConnectionStatusCodes().GetStatusMessage(Convert.ToInt32(httpResponse.StatusCode.ToString())));
            }


            Task<Stream> repsonseStreamAsync = httpResponse.Content.ReadAsStreamAsync();
            repsonseStreamAsync.Wait();

            Stream responseStream = repsonseStreamAsync.Result;

            return (IConnectionResponse)new ConnectionResponse(new ConnectionStatusCodes().GetStatusCode(httpResponse.StatusCode.ToString()), responseStream);
	    }

	    public IConnectionResponse Delete(IConnectionRequest connectionRequest) 
        {
		
		    if(connectionRequest == null) 
            {
			    Log.Error(typeof(HttpConnectionWorker).Name, "Delete", "Invalid Connection Request.");
                throw new ConnectionException(typeof(HttpConnectionWorker).Name, "Delete", "Invalid Connection Request.");
		    }

            
		    /*
		     * Request Parameters
		     */
		    StringBuilder url = new StringBuilder(connectionRequest.GetUrl());
		
		    IEnumerator<String> queryParameters = connectionRequest.GetQueryParameters();
		    IEnumerator<String> headerParameters = connectionRequest.GetHeaderParameters();
		
		
		    /*
		     * Add Query Parameters
		     */
            int count = 0;
		    while(queryParameters.MoveNext()) 
            {
			
			    String parameter = queryParameters.Current;
			    Connect.Model.ServiceDescriptor.Request.QueryParameter queryParameter = connectionRequest.GetQueryParameter(parameter);

                if (count == 0)
                {
                    url.Append("?");
                    url.Append(parameter + "=" + queryParameter.GetValue());
                }
                else
                {
                    url.Append("&");
                    url.Append(parameter + "=" + queryParameter.GetValue());
                }
            }


            /*
             * Make Request
             */
            HttpClient httpClient = new HttpClient();
            HttpResponseMessage httpResponse;
            	
		
		    /*
		     * Add Header Parameters
		     */
		    while(headerParameters.MoveNext()) 
            {
			
			    String header = headerParameters.Current;
			    Connect.Model.ServiceDescriptor.Request.HeaderParameter headerParameter = connectionRequest.GetHeaderParameter(header);

                httpClient.DefaultRequestHeaders.TryAddWithoutValidation(header, headerParameter.GetValue());
            }
		

            /* execute */
            
            try 
            {
                Task<HttpResponseMessage> httpResponseMessage = httpClient.DeleteAsync(url.ToString());
                httpResponseMessage.Wait();

                httpResponse = httpResponseMessage.Result; 
            } 
            catch(System.Exception exception) 
            {
                Log.Error(typeof(HttpConnectionWorker).Name, "Delete", "Exception caught while executing request, " + exception.Message);
                throw new ConnectionException(typeof(HttpConnectionWorker).Name, "Delete", "Exception caught while executing request, " + exception.Message);
            }



            /* return response */
            if (!httpResponse.IsSuccessStatusCode)
            {
                return (IConnectionResponse)new ConnectionResponse(new ConnectionStatusCodes().GetStatusCode(httpResponse.StatusCode.ToString()), new ConnectionStatusCodes().GetStatusMessage(Convert.ToInt32(httpResponse.StatusCode.ToString())));
            }


            Task<Stream> repsonseStreamAsync = httpResponse.Content.ReadAsStreamAsync();
            repsonseStreamAsync.Wait();

            Stream responseStream = repsonseStreamAsync.Result;

            return (IConnectionResponse)new ConnectionResponse(new ConnectionStatusCodes().GetStatusCode(httpResponse.StatusCode.ToString()), responseStream);
	    }

	    public IConnectionResponse Trace(IConnectionRequest connectionRequest) 
        {
		
		    if(connectionRequest == null) 
            {
			    Log.Error(typeof(HttpConnectionWorker).Name, "Trace", "Invalid Connection Request.");
                throw new ConnectionException(typeof(HttpConnectionWorker).Name, "Trace", "Invalid Connection Request.");
		    }


		    /*
		     * Request Parameters
		     */
		    StringBuilder url = new StringBuilder(connectionRequest.GetUrl());
		
		    IEnumerator<String> queryParameters = connectionRequest.GetQueryParameters();
		    IEnumerator<String> headerParameters = connectionRequest.GetHeaderParameters();
		
		
		    /*
		     * Add Query Parameters
		     */
            int count = 0; 
		    while(queryParameters.MoveNext()) 
            {
			
			    String parameter = queryParameters.Current;
			    Connect.Model.ServiceDescriptor.Request.QueryParameter queryParameter = connectionRequest.GetQueryParameter(parameter);

                if (count == 0)
                {
                    url.Append("?");
                    url.Append(parameter + "=" + queryParameter.GetValue());
                }
                else
                {
                    url.Append("&");
                    url.Append(parameter + "=" + queryParameter.GetValue());
                }
            }



            /*
             * Make Request
             */
            HttpClient httpClient = new HttpClient();
            HttpResponseMessage httpResponse;
		
		
		    /*
		     * Add Header Parameters
		     */
		    while(headerParameters.MoveNext()) 
            {
			
			    String header = headerParameters.Current;
			    Connect.Model.ServiceDescriptor.Request.HeaderParameter headerParameter = connectionRequest.GetHeaderParameter(header);

                httpClient.DefaultRequestHeaders.TryAddWithoutValidation(header, headerParameter.GetValue());
            }
		

            /* execute */
        
            try 
            {
                Task<HttpResponseMessage> httpResponseMessage = null;// httpClient.GetAsync(url.ToString());
                httpResponseMessage.Wait();

                httpResponse = httpResponseMessage.Result;   	 
            } 
            catch(System.Exception exception) 
            {
                Log.Error(typeof(HttpConnectionWorker).Name, "Trace", "Exception caught while executing request, " + exception.Message);
                throw new ConnectionException(typeof(HttpConnectionWorker).Name, "Trace", "Exception caught while executing request, " + exception.Message);
            }



            /* return response */
            if (!httpResponse.IsSuccessStatusCode)
            {
                return (IConnectionResponse)new ConnectionResponse(new ConnectionStatusCodes().GetStatusCode(httpResponse.StatusCode.ToString()), new ConnectionStatusCodes().GetStatusMessage(Convert.ToInt32(httpResponse.StatusCode.ToString())));
            }


            Task<Stream> repsonseStreamAsync = httpResponse.Content.ReadAsStreamAsync();
            repsonseStreamAsync.Wait();

            Stream responseStream = repsonseStreamAsync.Result;

            return (IConnectionResponse)new ConnectionResponse(new ConnectionStatusCodes().GetStatusCode(httpResponse.StatusCode.ToString()), responseStream);
	    }

	    public IConnectionResponse Options(IConnectionRequest connectionRequest) 
        {
		
		    if(connectionRequest == null) 
            {
			    Log.Error(typeof(HttpConnectionWorker).Name, "Options", "Invalid Connection Request.");
                throw new ConnectionException(typeof(HttpConnectionWorker).Name, "Options", "Invalid Connection Request.");
		    }
            
		    /*
		     * Request Parameters
		     */
		    StringBuilder url = new StringBuilder(connectionRequest.GetUrl());
		
		    IEnumerator<String> queryParameters = connectionRequest.GetQueryParameters();
		    IEnumerator<String> headerParameters = connectionRequest.GetHeaderParameters();
		
		
		    /*
		     * Add Query Parameters
		     */
            int count = 0;
		    while(queryParameters.MoveNext()) 
            {
			
			    String parameter = queryParameters.Current;
			    Connect.Model.ServiceDescriptor.Request.QueryParameter queryParameter = connectionRequest.GetQueryParameter(parameter);

                if (count == 0)
                {
                    url.Append("?");
                    url.Append(parameter + "=" + queryParameter.GetValue());
                }
                else
                {
                    url.Append("&");
                    url.Append(parameter + "=" + queryParameter.GetValue());
                }
            }


            /*
             * Make Request
             */
            HttpClient httpClient = new HttpClient();
            HttpResponseMessage httpResponse;
			
		
		    /*
		     * Add Header Parameters
		     */
		    while(headerParameters.MoveNext()) 
            {
			
			    String header = headerParameters.Current;
			    Connect.Model.ServiceDescriptor.Request.HeaderParameter headerParameter = connectionRequest.GetHeaderParameter(header);

                httpClient.DefaultRequestHeaders.TryAddWithoutValidation(header, headerParameter.GetValue());
            }
		

		
            /* execute */
        
            try 
            {
                Task<HttpResponseMessage> httpResponseMessage = null;// httpClient.GetAsync(url.ToString());
                httpResponseMessage.Wait();

                httpResponse = httpResponseMessage.Result; 
            } 
            catch(System.Exception exception) 
            {
                Log.Error(typeof(HttpConnectionWorker).Name, "Options", "Exception caught while executing request, " + exception.Message);
                throw new ConnectionException(typeof(HttpConnectionWorker).Name, "Options", "Exception caught while executing request, " + exception.Message);
            }



            /* return response */
            if (!httpResponse.IsSuccessStatusCode)
            {
                return (IConnectionResponse)new ConnectionResponse(new ConnectionStatusCodes().GetStatusCode(httpResponse.StatusCode.ToString()), new ConnectionStatusCodes().GetStatusMessage(Convert.ToInt32(httpResponse.StatusCode.ToString())));
            }


            Task<Stream> repsonseStreamAsync = httpResponse.Content.ReadAsStreamAsync();
            repsonseStreamAsync.Wait();

            Stream responseStream = repsonseStreamAsync.Result;

            return (IConnectionResponse)new ConnectionResponse(new ConnectionStatusCodes().GetStatusCode(httpResponse.StatusCode.ToString()), responseStream);
	    }

	    public IConnectionResponse Connect(IConnectionRequest connectionRequest) 
        {
		
		    if(connectionRequest == null) 
            {
			    Log.Error(typeof(HttpConnectionWorker).Name, "Connect", "Invalid Connection Request.");
                throw new ConnectionException(typeof(HttpConnectionWorker).Name, "Connect", "Invalid Connection Request.");
		    }


		    return null;
	    }

	    public IConnectionResponse Patch(IConnectionRequest connectionRequest) 
        {
		
		    if(connectionRequest == null) 
            {
			    Log.Error(typeof(HttpConnectionWorker).Name, "Patch", "Invalid Connection Request.");
                throw new ConnectionException(typeof(HttpConnectionWorker).Name, "Patch", "Invalid Connection Request.");
		    }

		    return null;
	    }

    }
}
