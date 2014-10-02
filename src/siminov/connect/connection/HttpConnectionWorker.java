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
import org.apache.http.client.methods.HttpGet;
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
import siminov.orm.log.Log;

/**
 * It implements IConnection to handle HTTP requests
 */
public class HttpConnectionWorker implements IConnection {

	public IConnectionResponse get(final IConnectionRequest connectionRequest) throws ConnectionException {

		if(connectionRequest == null) {
			Log.error(HttpConnectionWorker.class.getName(), "get", "Invalid Connection Request.");
			throw new ConnectionException(HttpConnectionWorker.class.getName(), "get", "Invalid Connection Request.");
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
		HttpGet httpGet = null;

		
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
			httpGet = new HttpGet(url.toString());
		} catch(Exception exception) {
			Log.error(HttpConnectionWorker.class.getName(), "get", "Exception caught while creating http get, URL: " + url + ", " + exception.getMessage());
			throw new ConnectionException(HttpConnectionWorker.class.getName(), "get", "Exception caught while creating http get, URL: " + url + ", " + exception.getMessage());
		}

		
		
		/*
		 * Add Header Parameters
		 */
		while(headerParameters.hasNext()) {
			
			String header = headerParameters.next();
			HeaderParameter headerParameter = connectionRequest.getHeaderParameter(header);
			
	        httpGet.setHeader(header, headerParameter.getValue());
		}
		

        /* execute */
        BasicHttpResponse httpResponse = null;
        
        try {
        	httpResponse = (BasicHttpResponse) httpClient.execute(httpGet);        	 
        } catch(Exception exception) {
        	Log.error(HttpConnectionWorker.class.getName(), "get", "Exception caught while executing request, " + exception.getMessage());
        	throw new ConnectionException(HttpConnectionWorker.class.getName(), "get", "Exception caught while executing request, " + exception.getMessage());
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
        	Log.error(HttpConnectionWorker.class.getName(), "get", "Exception caught while getting input stream, " + exception.getMessage());
        	throw new ConnectionException(HttpConnectionWorker.class.getName(), "get", "Exception caught while getting input stream, " + exception.getMessage());
        }

        
        return new ConnectionResponse(responseCode, inputStream);
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
