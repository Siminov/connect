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

            if (connectionRequest.GetUrl().Contains("get_liquors"))
            {
                String liquors = "<liquors><liquor><name>Gin</name><description>Gin is a spirit made with juniper berries.</description><histroy>By the 11th century, Italian monks were flavoring crudely distilled spirits with juniper berries. During the Black Death, this drink was used, although ineffectively, as a remedy. As the science of distillation advanced from the Middle Ages into the Renaissance period, juniper was one of many botanicals employed by virtue of its perfume, flavour, and purported medicinal properties</histroy><link>http://en.wikipedia.org/wiki/Gin</link><alchol_content>Gin has a minimum alcohol content of 40 percent in the US (80 proof).</alchol_content></liquor><liquor><name>Rum</name><description>Rum is a distilled liquid made from molasses and sugarcane juice.</description><histroy>The first distillation of rum originated in the Caribbean in the 17th centry.</histroy><link>http://en.wikipedia.org/wiki/Rum</link><alchol_content>Rum typically has an alcohol content in the range of 20 to 80 percent by volume (40 to 160 proof).</alchol_content></liquor><liquor><name>Tequila</name><description>Tequila is a spirit made with Blue Agave.</description><histroy>The first distillation of tequila originated in Mexico in the 16th centry.</histroy><link>http://en.wikipedia.org/wiki/Tequila</link><alchol_content>Tequila typically has an alcohol content in the range of 35 to 55 percent by volume (70 to 110 proof).</alchol_content></liquor><liquor><name>Vodka</name><description>Vodka is a colorless liquid that contains a mixture of water and distilled ethanol. Vodka is made from a fermented substance such as potatoes, rye, wheat or sugar beet molasses.</description><histroy>Vodka originated in Russia inthe 14th centry.</histroy><link>http://en.wikipedia.org/wiki/Vodka</link><alchol_content>Vodka typically has an alcohol content in the range of 35 to 50 percent by volume (70 to 100 proof).</alchol_content></liquor><liquor><name>Whiskey</name><description>Whiskey (or whisky) is a distilled liquid made from fermented grain mash.</description><histroy>Distillation spread from Ireland to Scotland (about one hundred years later) and originated from the European continent in the later medieval centuries</histroy><link>http://en.wikipedia.org/wiki/Whisky</link><alchol_content>Whiskey has varying proof levels depending on the specific whiskey. Read more: http://wiki.answers.com/Q/What_percentage_of_alcohol_is_in_whisky#ixzz25n6vmBTZ</alchol_content></liquor><liquor><name>Beer</name><description>Beer is produced by the saccharification of starch and fermentation of the resulting sugar.</description><histroy>Beer is one of the worlds oldest prepared beverages, possibly dating back to the early Neolithic or 9500 BC, when cereal was first farmed, and is recorded in the written history of ancient Egypt and Mesopotamia.</histroy><link>http://en.wikipedia.org/wiki/Beer</link><alchol_content>A beer that is 4.0 percent by volume is about 3.2 percent by weight</alchol_content></liquor><liquor><name>Wine</name><description>Wine is typically made of fermented grape juice.</description><histroy>Wine dates back to 6000 BC and is believed to have originated in the area now considered Iran and Georgia.</histroy><link>http://en.wikipedia.org/wiki/Wine</link><alchol_content>100 grams (g) of wine is equivalent to 100 milliliters (mL) or 3.4 fluid ounces (fl oz.) of wine. 10.6 g of alcohol in 3.4 fl ounces is 13 percent alcohol by volume.</alchol_content></liquor></liquors>";
                Stream liquorsStream;

                byte[] bytes = new byte[liquors.Length * sizeof(char)];
                System.Buffer.BlockCopy(liquors.ToCharArray(), 0, bytes, 0, bytes.Length);

                try
                {
                    liquorsStream = Utils.Utils.ToStream(bytes);
                    return new ConnectionResponse(200, liquorsStream);
                }
                catch (SiminovException se)
                {

                }
            }
            else
            {
                String liquorBrands = "<brands><brand><name>The Botanist</name><country>Islay</country><description>The Botanist is a brand of gin produced by distilling nine botanicals into the alcohol through direct boiling followed by passing the vapors through a basket containing an additional 22 herbal ingredients.</description><link>http://www.bruichladdich.com</link></brand><brand><name>Tanqueray</name><country>United Kingtom</country><description>Tanqueray is a form of London dry gin, a spirit produced through double-distillation with botanicals added to the spirit during the second distillation phase. Tanqueray was first distilled in 1830 in England, with production moved to Scotland after World War II.</description><link>http://www.tanqueray.com/</link></brand></brands>";
                Stream liquorBrandsStream;

                byte[] bytes = new byte[liquorBrands.Length * sizeof(char)];
                System.Buffer.BlockCopy(liquorBrands.ToCharArray(), 0, bytes, 0, bytes.Length);

                try
                {
                    liquorBrandsStream = Utils.Utils.ToStream(bytes);
                    return new ConnectionResponse(200, liquorBrandsStream);
                }
                catch (SiminovException se)
                {

                }
            }

            return new ConnectionResponse(200, "");
		

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
