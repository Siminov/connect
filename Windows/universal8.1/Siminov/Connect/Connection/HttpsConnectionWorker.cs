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
    /// It implements IConnection to handle HTTPS requests
    /// </summary>
    public class HttpsConnectionWorker : IConnection
    {
        public IConnectionResponse Get(IConnectionRequest connectionRequest)
        {

            if (connectionRequest == null)
            {
                Log.Error(typeof(HttpConnectionWorker).Name, "Get", "Invalid Connection Request.");
                throw new ConnectionException(typeof(HttpConnectionWorker).Name, "Get", "Invalid Connection Request.");
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
            while (queryParameters.MoveNext())
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
            while (headerParameters.MoveNext())
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
            catch (System.Exception exception)
            {
                Log.Error(typeof(HttpConnectionWorker).Name, "Get", "Exception caught while executing request, " + exception.Message);
                throw new ConnectionException(typeof(HttpConnectionWorker).Name, "Get", "Exception caught while executing request, " + exception.Message);
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

        public IConnectionResponse Head(IConnectionRequest connectionRequest)
        {

            if (connectionRequest == null)
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
            while (queryParameters.MoveNext())
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
            while (headerParameters.MoveNext())
            {

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
            catch (System.Exception exception)
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

            if (connectionRequest == null)
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
            while (queryParameters.MoveNext())
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
            while (headerParameters.MoveNext())
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
            catch (System.Exception exception)
            {
                Log.Error(typeof(HttpConnectionWorker).Name, "Post", "Exception caught while executing request, " + exception.Message);
                throw new ConnectionException(typeof(HttpConnectionWorker).Name, "Post", "Exception caught while executing request, " + exception.Message);
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

        public IConnectionResponse Put(IConnectionRequest connectionRequest)
        {

            if (connectionRequest == null)
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
            while (queryParameters.MoveNext())
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
            while (headerParameters.MoveNext())
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
            catch (System.Exception exception)
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

            if (connectionRequest == null)
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
            while (queryParameters.MoveNext())
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
            while (headerParameters.MoveNext())
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
            catch (System.Exception exception)
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

            if (connectionRequest == null)
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
            while (queryParameters.MoveNext())
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
            while (headerParameters.MoveNext())
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
            catch (System.Exception exception)
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

            if (connectionRequest == null)
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
            while (queryParameters.MoveNext())
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
            while (headerParameters.MoveNext())
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
            catch (System.Exception exception)
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

            if (connectionRequest == null)
            {
                Log.Error(typeof(HttpConnectionWorker).Name, "Connect", "Invalid Connection Request.");
                throw new ConnectionException(typeof(HttpConnectionWorker).Name, "Connect", "Invalid Connection Request.");
            }


            return null;
        }

        public IConnectionResponse Patch(IConnectionRequest connectionRequest)
        {

            if (connectionRequest == null)
            {
                Log.Error(typeof(HttpConnectionWorker).Name, "Patch", "Invalid Connection Request.");
                throw new ConnectionException(typeof(HttpConnectionWorker).Name, "Patch", "Invalid Connection Request.");
            }

            return null;
        }

    }
}
