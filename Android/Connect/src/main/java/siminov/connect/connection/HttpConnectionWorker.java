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

package siminov.connect.connection;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpOptions;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpTrace;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicNameValuePair;

import siminov.connect.Constants;
import siminov.connect.connection.design.IConnection;
import siminov.connect.connection.design.IConnectionRequest;
import siminov.connect.connection.design.IConnectionResponse;
import siminov.connect.exception.ConnectionException;
import siminov.connect.model.ServiceDescriptor.Request.HeaderParameter;
import siminov.connect.model.ServiceDescriptor.Request.QueryParameter;
import siminov.core.exception.SiminovException;
import siminov.core.log.Log;
import siminov.core.utils.Utils;

/**
 * It implements IConnection to handle HTTP requests
 */
public class HttpConnectionWorker implements IConnection {

	public IConnectionResponse get(final IConnectionRequest connectionRequest) throws ConnectionException {

		if(connectionRequest == null) {
			Log.error(HttpConnectionWorker.class.getName(), "get", "Invalid Connection Request.");
			throw new ConnectionException(HttpConnectionWorker.class.getName(), "get", "Invalid Connection Request.");
		}
		
		if(connectionRequest.getUrl().contains("get_liquors")) {
			String liquors = "<liquors><liquor><name>Gin</name><description>Gin is a spirit made with juniper berries.</description><histroy>By the 11th century, Italian monks were flavoring crudely distilled spirits with juniper berries. During the Black Death, this drink was used, although ineffectively, as a remedy. As the science of distillation advanced from the Middle Ages into the Renaissance period, juniper was one of many botanicals employed by virtue of its perfume, flavour, and purported medicinal properties</histroy><link>http://en.wikipedia.org/wiki/Gin</link><alchol_content>Gin has a minimum alcohol content of 40 percent in the US (80 proof).</alchol_content></liquor><liquor><name>Rum</name><description>Rum is a distilled liquid made from molasses and sugarcane juice.</description><histroy>The first distillation of rum originated in the Caribbean in the 17th centry.</histroy><link>http://en.wikipedia.org/wiki/Rum</link><alchol_content>Rum typically has an alcohol content in the range of 20 to 80 percent by volume (40 to 160 proof).</alchol_content></liquor><liquor><name>Tequila</name><description>Tequila is a spirit made with Blue Agave.</description><histroy>The first distillation of tequila originated in Mexico in the 16th centry.</histroy><link>http://en.wikipedia.org/wiki/Tequila</link><alchol_content>Tequila typically has an alcohol content in the range of 35 to 55 percent by volume (70 to 110 proof).</alchol_content></liquor><liquor><name>Vodka</name><description>Vodka is a colorless liquid that contains a mixture of water and distilled ethanol. Vodka is made from a fermented substance such as potatoes, rye, wheat or sugar beet molasses.</description><histroy>Vodka originated in Russia inthe 14th centry.</histroy><link>http://en.wikipedia.org/wiki/Vodka</link><alchol_content>Vodka typically has an alcohol content in the range of 35 to 50 percent by volume (70 to 100 proof).</alchol_content></liquor><liquor><name>Whiskey</name><description>Whiskey (or whisky) is a distilled liquid made from fermented grain mash.</description><histroy>Distillation spread from Ireland to Scotland (about one hundred years later) and originated from the European continent in the later medieval centuries</histroy><link>http://en.wikipedia.org/wiki/Whisky</link><alchol_content>Whiskey has varying proof levels depending on the specific whiskey. Read more: http://wiki.answers.com/Q/What_percentage_of_alcohol_is_in_whisky#ixzz25n6vmBTZ</alchol_content></liquor><liquor><name>Beer</name><description>Beer is produced by the saccharification of starch and fermentation of the resulting sugar.</description><histroy>Beer is one of the worlds oldest prepared beverages, possibly dating back to the early Neolithic or 9500 BC, when cereal was first farmed, and is recorded in the written history of ancient Egypt and Mesopotamia.</histroy><link>http://en.wikipedia.org/wiki/Beer</link><alchol_content>A beer that is 4.0 percent by volume is about 3.2 percent by weight</alchol_content></liquor><liquor><name>Wine</name><description>Wine is typically made of fermented grape juice.</description><histroy>Wine dates back to 6000 BC and is believed to have originated in the area now considered Iran and Georgia.</histroy><link>http://en.wikipedia.org/wiki/Wine</link><alchol_content>100 grams (g) of wine is equivalent to 100 milliliters (mL) or 3.4 fluid ounces (fl oz.) of wine. 10.6 g of alcohol in 3.4 fl ounces is 13 percent alcohol by volume.</alchol_content></liquor></liquors>";
			InputStream liquorsStream;
			
			try {
				liquorsStream = Utils.toInputStream(liquors); 
				return new ConnectionResponse(200, liquorsStream);
			} catch(SiminovException se) {
				
			}
		} else {
			String liquorBrands = "<brands><brand><name>The Botanist</name><country>Islay</country><description>The Botanist is a brand of gin produced by distilling nine botanicals into the alcohol through direct boiling followed by passing the vapors through a basket containing an additional 22 herbal ingredients.</description><link>http://www.bruichladdich.com</link></brand><brand><name>Tanqueray</name><country>United Kingtom</country><description>Tanqueray is a form of London dry gin, a spirit produced through double-distillation with botanicals added to the spirit during the second distillation phase. Tanqueray was first distilled in 1830 in England, with production moved to Scotland after World War II.</description><link>http://www.tanqueray.com/</link></brand></brands>";
	        InputStream liquorBrandsStream;
			
	        try {
	        	liquorBrandsStream = Utils.toInputStream(liquorBrands);
				return new ConnectionResponse(200, liquorBrandsStream);
	        } catch(SiminovException se) {
	        	
	        }
		}
		
		return new ConnectionResponse(200, "");
		
		/*
		 * Request Parameters
		 */
		/*StringBuffer url = new StringBuffer(connectionRequest.getUrl());
		
		Iterator<String> queryParameters = connectionRequest.getQueryParameters();
		Iterator<String> headerParameters = connectionRequest.getHeaderParameters();
		
		
		/*
		 * Make Request
		 */
		/*DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = null;

		
		/*
		 * Add Query Parameters
		 */
		/*List<NameValuePair> httpParams = new ArrayList<NameValuePair>();
		while(queryParameters.hasNext()) {
			
			String parameter = queryParameters.next();
			QueryParameter queryParameter = connectionRequest.getQueryParameter(parameter);
			
			httpParams.add(new BasicNameValuePair(parameter, queryParameter.getValue()));
		}
		
		String queryParameter = URLEncodedUtils.format(httpParams, "utf-8");
		if(!url.toString().endsWith(Constants.CONNECTION_QUERY_PARAMETER_KEYWORD)) {
	        url.append(Constants.CONNECTION_QUERY_PARAMETER_KEYWORD);
		}

		url.append(queryParameter);

		
		try {
			httpGet = new HttpGet(url.toString());
		} catch(Exception exception) {
			Log.error(HttpConnectionWorker.class.getName(), "get", "Exception caught while creating http get, URL: " + url + ", " + exception.getMessage());
			throw new ConnectionException(HttpConnectionWorker.class.getName(), "get", "Exception caught while creating http get, URL: " + url + ", " + exception.getMessage());
		}

		
		
		/*
		 * Add Header Parameters
		 */
		/*while(headerParameters.hasNext()) {
			
			String header = headerParameters.next();
			HeaderParameter headerParameter = connectionRequest.getHeaderParameter(header);
			
	        httpGet.setHeader(header, headerParameter.getValue());
		}
		

        /* execute */
        /*BasicHttpResponse httpResponse = null;
        
        try {
        	httpResponse = (BasicHttpResponse) httpClient.execute(httpGet);        	 
        } catch(Exception exception) {
        	Log.error(HttpConnectionWorker.class.getName(), "get", "Exception caught while executing request, " + exception.getMessage());
        	throw new ConnectionException(HttpConnectionWorker.class.getName(), "get", "Exception caught while executing request, " + exception.getMessage());
        }


        
        /* return response */
        /*int responseCode = httpResponse.getStatusLine().getStatusCode();
        if(responseCode != HttpsURLConnection.HTTP_OK) {
        	return new ConnectionResponse(responseCode, new ConnectionStatusCodes().getStatusMessage(responseCode));
        }
        
        
        
        InputStream inputStream = null;
        
        try {
        	inputStream = httpResponse.getEntity().getContent();
        } catch(Exception exception) {
        	Log.error(HttpConnectionWorker.class.getName(), "get", "Exception caught while getting input stream, " + exception.getMessage());
        	throw new ConnectionException(HttpConnectionWorker.class.getName(), "get", "Exception caught while getting input stream, " + exception.getMessage());
        }

        
        return new ConnectionResponse(responseCode, inputStream);*/
	}
	
	public IConnectionResponse head(final IConnectionRequest connectionRequest) throws ConnectionException {
		
		if(connectionRequest == null) {
			Log.error(HttpConnectionWorker.class.getName(), "head", "Invalid Connection Request.");
			throw new ConnectionException(HttpConnectionWorker.class.getName(), "head", "Invalid Connection Request.");
		}


		/*
		 * Request Parameters
		 */
		StringBuffer url = new StringBuffer(connectionRequest.getUrl());
		
		Iterator<String> queryParameters = connectionRequest.getQueryParameters();
		Iterator<String> headerParameters = connectionRequest.getHeaderParameters();
		
		
		/*
		 * Make Request
		 */
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpHead httpHead = null;

		/*
		 * Add Query Parameters
		 */
		List<NameValuePair> httpParams = new ArrayList<NameValuePair>();
		while(queryParameters.hasNext()) {
			
			String parameter = queryParameters.next();
			QueryParameter queryParameter = connectionRequest.getQueryParameter(parameter);
			
			httpParams.add(new BasicNameValuePair(parameter, queryParameter.getValue()));
		}
		
		String queryParameter = URLEncodedUtils.format(httpParams, "utf-8");
		if(!url.toString().endsWith(Constants.CONNECTION_QUERY_PARAMETER_KEYWORD)) {
	        url.append(Constants.CONNECTION_QUERY_PARAMETER_KEYWORD);
		}

		url.append(queryParameter);

		
		try {
			httpHead = new HttpHead(url.toString());
		} catch(Exception exception) {
			Log.error(HttpConnectionWorker.class.getName(), "head", "Exception caught while creating http head, URL: " + url + ", " + exception.getMessage());
			throw new ConnectionException(HttpConnectionWorker.class.getName(), "head", "Exception caught while creating http head, URL: " + url + ", " + exception.getMessage());
		}


		/*
		 * Add Header Parameters
		 */
		while(headerParameters.hasNext()) {
			
			String header = headerParameters.next();
			HeaderParameter headerParameter = connectionRequest.getHeaderParameter(header);
			
	        httpHead.setHeader(header, headerParameter.getValue());
		}
		

        /* execute */
        BasicHttpResponse httpResponse = null;
        
        try {
        	httpResponse = (BasicHttpResponse) httpClient.execute(httpHead);        	 
        } catch(Exception exception) {
        	Log.error(HttpConnectionWorker.class.getName(), "head", "Exception caught while executing request, " + exception.getMessage());
        	throw new ConnectionException(HttpConnectionWorker.class.getName(), "head", "Exception caught while executing request, " + exception.getMessage());
        }


        
        /* return response */
        int responseCode = httpResponse.getStatusLine().getStatusCode();
        if(responseCode != HttpsURLConnection.HTTP_OK) {
        	return new ConnectionResponse(responseCode, new ConnectionStatusCodes().getStatusMessage(responseCode));
        }
        
        
        
        InputStream inputStream = null;
        
        try {
        	inputStream = httpResponse.getEntity().getContent();
        } catch(Exception exception) {
        	Log.error(HttpConnectionWorker.class.getName(), "head", "Exception caught while getting input stream, " + exception.getMessage());
        	throw new ConnectionException(HttpConnectionWorker.class.getName(), "head", "Exception caught while getting input stream, " + exception.getMessage());
        }

        
        return new ConnectionResponse(responseCode, inputStream);
	}
	
	public IConnectionResponse post(final IConnectionRequest connectionRequest) throws ConnectionException {
		
		if(connectionRequest == null) {
			Log.error(HttpConnectionWorker.class.getName(), "post", "Invalid Connection Request.");
			throw new ConnectionException(HttpConnectionWorker.class.getName(), "post", "Invalid Connection Request.");
		}


		/*
		 * Request Parameters
		 */
		StringBuffer url = new StringBuffer(connectionRequest.getUrl());
		
		Iterator<String> queryParameters = connectionRequest.getQueryParameters();
		Iterator<String> headerParameters = connectionRequest.getHeaderParameters();
		
		byte[] dataStream = connectionRequest.getDataStream();
		

		
		/*
		 * Add Query Parameters
		 */
		List<NameValuePair> httpParams = new ArrayList<NameValuePair>();
		while(queryParameters.hasNext()) {
			
			String parameter = queryParameters.next();
			QueryParameter queryParameter = connectionRequest.getQueryParameter(parameter);
			
			httpParams.add(new BasicNameValuePair(parameter, queryParameter.getValue()));
		}
		
		String queryParameter = URLEncodedUtils.format(httpParams, "utf-8");
		if(!url.toString().endsWith(Constants.CONNECTION_QUERY_PARAMETER_KEYWORD)) {
	        url.append(Constants.CONNECTION_QUERY_PARAMETER_KEYWORD);
		}

		url.append(queryParameter);
		
		
		/*
		 * Make Request
		 */
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = null;
		
		try {
			httpPost = new HttpPost(url.toString());
		} catch(Exception exception) {
			Log.error(HttpConnectionWorker.class.getName(), "post", "Exception caught while creating http post, URL: " + url + ", " + exception.getMessage());
			throw new ConnectionException(HttpConnectionWorker.class.getName(), "post", "Exception caught while creating http post, URL: " + url + ", " + exception.getMessage());
		}

		
		/*
		 * Add Header Parameters
		 */
		while(headerParameters.hasNext()) {
			
			String header = headerParameters.next();
			HeaderParameter headerParameter = connectionRequest.getHeaderParameter(header);
			
	        httpPost.setHeader(header, headerParameter.getValue());
		}
		

		
		if(dataStream != null && dataStream.length > 0) {
			httpPost.setEntity(new ByteArrayEntity(dataStream));	
		}
		
		
		/* execute */
        BasicHttpResponse httpResponse = null;
        
        try {
        	httpResponse = (BasicHttpResponse) httpClient.execute(httpPost);        	 
        } catch(Exception exception) {
        	Log.error(HttpConnectionWorker.class.getName(), "post", "Exception caught while executing request, " + exception.getMessage());
        	throw new ConnectionException(HttpConnectionWorker.class.getName(), "post", "Exception caught while executing request, " + exception.getMessage());
        }


        
        /* return response */
        int responseCode = httpResponse.getStatusLine().getStatusCode();
        if(responseCode != HttpsURLConnection.HTTP_OK) {
        	return new ConnectionResponse(responseCode, new ConnectionStatusCodes().getStatusMessage(responseCode));
        }
        
        
        
        InputStream inputStream = null;
        
        try {
        	inputStream = httpResponse.getEntity().getContent();
        } catch(Exception exception) {
        	Log.error(HttpConnectionWorker.class.getName(), "post", "Exception caught while getting input stream, " + exception.getMessage());
        	throw new ConnectionException(HttpConnectionWorker.class.getName(), "post", "Exception caught while getting input stream, " + exception.getMessage());
        }

        
        return new ConnectionResponse(responseCode, inputStream);
	}
	
	public IConnectionResponse put(final IConnectionRequest connectionRequest) throws ConnectionException {
		
		if(connectionRequest == null) {
			Log.error(HttpConnectionWorker.class.getName(), "put", "Invalid Connection Request.");
			throw new ConnectionException(HttpConnectionWorker.class.getName(), "put", "Invalid Connection Request.");
		}



		/*
		 * Request Parameters
		 */
		StringBuffer url = new StringBuffer(connectionRequest.getUrl());
		
		Iterator<String> queryParameters = connectionRequest.getQueryParameters();
		Iterator<String> headerParameters = connectionRequest.getHeaderParameters();
		
		byte[] dataStream = connectionRequest.getDataStream();
		
		
		/*
		 * Add Query Parameters
		 */
		List<NameValuePair> httpParams = new ArrayList<NameValuePair>();
		while(queryParameters.hasNext()) {
			
			String parameter = queryParameters.next();
			QueryParameter queryParameter = connectionRequest.getQueryParameter(parameter);
			
			httpParams.add(new BasicNameValuePair(parameter, queryParameter.getValue()));
		}
		
		String queryParameter = URLEncodedUtils.format(httpParams, "utf-8");
		if(!url.toString().endsWith(Constants.CONNECTION_QUERY_PARAMETER_KEYWORD)) {
	        url.append(Constants.CONNECTION_QUERY_PARAMETER_KEYWORD);
		}

		url.append(queryParameter);

		
		/*
		 * Make Request
		 */
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpPut httpPut = null;
		
		try {
			httpPut = new HttpPut(url.toString());
		} catch(Exception exception) {
			Log.error(HttpConnectionWorker.class.getName(), "put", "Exception caught while creating http put, URL: " + url + ", " + exception.getMessage());
			throw new ConnectionException(HttpConnectionWorker.class.getName(), "put", "Exception caught while creating http put, URL: " + url + ", " + exception.getMessage());
		}

		
		/*
		 * Add Header Parameters
		 */
		while(headerParameters.hasNext()) {
			
			String header = headerParameters.next();
			HeaderParameter headerParameter = connectionRequest.getHeaderParameter(header);
			
	        httpPut.setHeader(header, headerParameter.getValue());
		}
		

		
		if(dataStream != null && dataStream.length > 0) {
			httpPut.setEntity(new ByteArrayEntity(dataStream));	
		}

		
        /* execute */
        BasicHttpResponse httpResponse = null;
        
        try {
        	httpResponse = (BasicHttpResponse) httpClient.execute(httpPut);        	 
        } catch(Exception exception) {
        	Log.error(HttpConnectionWorker.class.getName(), "put", "Exception caught while executing request, " + exception.getMessage());
        	throw new ConnectionException(HttpConnectionWorker.class.getName(), "put", "Exception caught while executing request, " + exception.getMessage());
        }


        
        /* return response */
        int responseCode = httpResponse.getStatusLine().getStatusCode();
        if(responseCode != HttpsURLConnection.HTTP_OK) {
        	return new ConnectionResponse(responseCode, new ConnectionStatusCodes().getStatusMessage(responseCode));
        }
        
        
        
        InputStream inputStream = null;
        
        try {
        	inputStream = httpResponse.getEntity().getContent();
        } catch(Exception exception) {
        	Log.error(HttpConnectionWorker.class.getName(), "put", "Exception caught while getting input stream, " + exception.getMessage());
        	throw new ConnectionException(HttpConnectionWorker.class.getName(), "put", "Exception caught while getting input stream, " + exception.getMessage());
        }

        
        return new ConnectionResponse(responseCode, inputStream);
	}

	public IConnectionResponse delete(final IConnectionRequest connectionRequest) throws ConnectionException {
		
		if(connectionRequest == null) {
			Log.error(HttpConnectionWorker.class.getName(), "delete", "Invalid Connection Request.");
			throw new ConnectionException(HttpConnectionWorker.class.getName(), "delete", "Invalid Connection Request.");
		}




		/*
		 * Request Parameters
		 */
		StringBuffer url = new StringBuffer(connectionRequest.getUrl());
		
		Iterator<String> queryParameters = connectionRequest.getQueryParameters();
		Iterator<String> headerParameters = connectionRequest.getHeaderParameters();
		
		
		/*
		 * Add Query Parameters
		 */
		List<NameValuePair> httpParams = new ArrayList<NameValuePair>();
		while(queryParameters.hasNext()) {
			
			String parameter = queryParameters.next();
			QueryParameter queryParameter = connectionRequest.getQueryParameter(parameter);
			
			httpParams.add(new BasicNameValuePair(parameter, queryParameter.getValue()));
		}
		
		String queryParameter = URLEncodedUtils.format(httpParams, "utf-8");
		if(!url.toString().endsWith(Constants.CONNECTION_QUERY_PARAMETER_KEYWORD)) {
	        url.append(Constants.CONNECTION_QUERY_PARAMETER_KEYWORD);
		}

		url.append(queryParameter);

		
		/*
		 * Make Request
		 */
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpDelete httpDelete = null;
		
		try {
			httpDelete = new HttpDelete(url.toString());
		} catch(Exception exception) {
			Log.error(HttpConnectionWorker.class.getName(), "delete", "Exception caught while creating http delete, URL: " + url + ", " + exception.getMessage());
			throw new ConnectionException(HttpConnectionWorker.class.getName(), "delete", "Exception caught while creating http delete, URL: " + url + ", " + exception.getMessage());
		}

		
		
		/*
		 * Add Header Parameters
		 */
		while(headerParameters.hasNext()) {
			
			String header = headerParameters.next();
			HeaderParameter headerParameter = connectionRequest.getHeaderParameter(header);
			
	        httpDelete.setHeader(header, headerParameter.getValue());
		}
		

        /* execute */
        BasicHttpResponse httpResponse = null;
        
        try {
        	httpResponse = (BasicHttpResponse) httpClient.execute(httpDelete);        	 
        } catch(Exception exception) {
        	Log.error(HttpConnectionWorker.class.getName(), "delete", "Exception caught while executing request, " + exception.getMessage());
        	throw new ConnectionException(HttpConnectionWorker.class.getName(), "delete", "Exception caught while executing request, " + exception.getMessage());
        }


        
        /* return response */
        int responseCode = httpResponse.getStatusLine().getStatusCode();
        if(responseCode != HttpsURLConnection.HTTP_OK) {
        	return new ConnectionResponse(responseCode, new ConnectionStatusCodes().getStatusMessage(responseCode));
        }
        
        
        
        InputStream inputStream = null;
        
        try {
        	inputStream = httpResponse.getEntity().getContent();
        } catch(Exception exception) {
        	Log.error(HttpConnectionWorker.class.getName(), "delete", "Exception caught while getting input stream, " + exception.getMessage());
        	throw new ConnectionException(HttpConnectionWorker.class.getName(), "delete", "Exception caught while getting input stream, " + exception.getMessage());
        }

        
        return new ConnectionResponse(responseCode, inputStream);
	}

	public IConnectionResponse trace(final IConnectionRequest connectionRequest) throws ConnectionException {
		
		if(connectionRequest == null) {
			Log.error(HttpConnectionWorker.class.getName(), "trace", "Invalid Connection Request.");
			throw new ConnectionException(HttpConnectionWorker.class.getName(), "trace", "Invalid Connection Request.");
		}


		/*
		 * Request Parameters
		 */
		StringBuilder url = new StringBuilder(connectionRequest.getUrl());
		
		Iterator<String> queryParameters = connectionRequest.getQueryParameters();
		Iterator<String> headerParameters = connectionRequest.getHeaderParameters();
		
		
		/*
		 * Add Query Parameters
		 */
		List<NameValuePair> httpParams = new ArrayList<NameValuePair>();
		while(queryParameters.hasNext()) {
			
			String parameter = queryParameters.next();
			QueryParameter queryParameter = connectionRequest.getQueryParameter(parameter);
			
			httpParams.add(new BasicNameValuePair(parameter, queryParameter.getValue()));
		}
		
		String queryParameter = URLEncodedUtils.format(httpParams, "utf-8");
		if(!url.toString().endsWith(Constants.CONNECTION_QUERY_PARAMETER_KEYWORD)) {
	        url.append(Constants.CONNECTION_QUERY_PARAMETER_KEYWORD);
		}

		url.append(queryParameter);

		
		/*
		 * Make Request
		 */
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpTrace httpTrace = null;
		
		try {
			httpTrace = new HttpTrace(url.toString());
		} catch(Exception exception) {
			Log.error(HttpConnectionWorker.class.getName(), "trace", "Exception caught while creating http trace, URL: " + url + ", " + exception.getMessage());
			throw new ConnectionException(HttpConnectionWorker.class.getName(), "trace", "Exception caught while creating http trace, URL: " + url + ", " + exception.getMessage());
		}

		
		/*
		 * Add Header Parameters
		 */
		while(headerParameters.hasNext()) {
			
			String header = headerParameters.next();
			HeaderParameter headerParameter = connectionRequest.getHeaderParameter(header);
			
	        httpTrace.setHeader(header, headerParameter.getValue());
		}
		

        /* execute */
        BasicHttpResponse httpResponse = null;
        
        try {
        	httpResponse = (BasicHttpResponse) httpClient.execute(httpTrace);        	 
        } catch(Exception exception) {
        	Log.error(HttpConnectionWorker.class.getName(), "trace", "Exception caught while executing request, " + exception.getMessage());
        	throw new ConnectionException(HttpConnectionWorker.class.getName(), "trace", "Exception caught while executing request, " + exception.getMessage());
        }


        
        /* return response */
        int responseCode = httpResponse.getStatusLine().getStatusCode();
        if(responseCode != HttpsURLConnection.HTTP_OK) {
        	return new ConnectionResponse(responseCode, new ConnectionStatusCodes().getStatusMessage(responseCode));
        }
        
        
        
        InputStream inputStream = null;
        
        try {
        	inputStream = httpResponse.getEntity().getContent();
        } catch(Exception exception) {
        	Log.error(HttpConnectionWorker.class.getName(), "trace", "Exception caught while getting input stream, " + exception.getMessage());
        	throw new ConnectionException(HttpConnectionWorker.class.getName(), "trace", "Exception caught while getting input stream, " + exception.getMessage());
        }

        
        return new ConnectionResponse(responseCode, inputStream);
	}

	public IConnectionResponse options(final IConnectionRequest connectionRequest) throws ConnectionException {
		
		if(connectionRequest == null) {
			Log.error(HttpConnectionWorker.class.getName(), "options", "Invalid Connection Request.");
			throw new ConnectionException(HttpConnectionWorker.class.getName(), "options", "Invalid Connection Request.");
		}




		/*
		 * Request Parameters
		 */
		StringBuilder url = new StringBuilder(connectionRequest.getUrl());
		
		Iterator<String> queryParameters = connectionRequest.getQueryParameters();
		Iterator<String> headerParameters = connectionRequest.getHeaderParameters();
		
		
		/*
		 * Add Query Parameters
		 */
		List<NameValuePair> httpParams = new ArrayList<NameValuePair>();
		while(queryParameters.hasNext()) {
			
			String parameter = queryParameters.next();
			QueryParameter queryParameter = connectionRequest.getQueryParameter(parameter);
			
			httpParams.add(new BasicNameValuePair(parameter, queryParameter.getValue()));
		}
		
		String queryParameter = URLEncodedUtils.format(httpParams, "utf-8");
		if(!url.toString().endsWith(Constants.CONNECTION_QUERY_PARAMETER_KEYWORD)) {
	        url.append(Constants.CONNECTION_QUERY_PARAMETER_KEYWORD);
		}

		url.append(queryParameter);
		
		/*
		 * Make Request
		 */
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpOptions httpOptions = null;
		
		try {
			httpOptions = new HttpOptions(url.toString());
		} catch(Exception exception) {
			Log.error(HttpConnectionWorker.class.getName(), "options", "Exception caught while creating http options, URL: " + url + ", " + exception.getMessage());
			throw new ConnectionException(HttpConnectionWorker.class.getName(), "options", "Exception caught while creating http options, URL: " + url + ", " + exception.getMessage());
		}

		
		
		/*
		 * Add Header Parameters
		 */
		while(headerParameters.hasNext()) {
			
			String header = headerParameters.next();
			HeaderParameter headerParameter = connectionRequest.getHeaderParameter(header);
			
	        httpOptions.setHeader(header, headerParameter.getValue());
		}
		

		
        /* execute */
        BasicHttpResponse httpResponse = null;
        
        try {
        	httpResponse = (BasicHttpResponse) httpClient.execute(httpOptions);        	 
        } catch(Exception exception) {
        	Log.error(HttpConnectionWorker.class.getName(), "options", "Exception caught while executing request, " + exception.getMessage());
        	throw new ConnectionException(HttpConnectionWorker.class.getName(), "options", "Exception caught while executing request, " + exception.getMessage());
        }


        
        /* return response */
        int responseCode = httpResponse.getStatusLine().getStatusCode();
        if(responseCode != HttpsURLConnection.HTTP_OK) {
        	return new ConnectionResponse(responseCode, new ConnectionStatusCodes().getStatusMessage(responseCode));
        }
        
        
        
        InputStream inputStream = null;
        
        try {
        	inputStream = httpResponse.getEntity().getContent();
        } catch(Exception exception) {
        	Log.error(HttpConnectionWorker.class.getName(), "options", "Exception caught while getting input stream, " + exception.getMessage());
        	throw new ConnectionException(HttpConnectionWorker.class.getName(), "options", "Exception caught while getting input stream, " + exception.getMessage());
        }

        
        return new ConnectionResponse(responseCode, inputStream);
	}

	public IConnectionResponse connect(final IConnectionRequest connectionRequest) throws ConnectionException {
		
		if(connectionRequest == null) {
			Log.error(HttpConnectionWorker.class.getName(), "connect", "Invalid Connection Request.");
			throw new ConnectionException(HttpConnectionWorker.class.getName(), "connect", "Invalid Connection Request.");
		}


		return null;
	}

	public IConnectionResponse patch(final IConnectionRequest connectionRequest) throws ConnectionException {
		
		if(connectionRequest == null) {
			Log.error(HttpConnectionWorker.class.getName(), "patch", "Invalid Connection Request.");
			throw new ConnectionException(HttpConnectionWorker.class.getName(), "patch", "Invalid Connection Request.");
		}

		return null;
	}
}
