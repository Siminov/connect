package siminov.connect.worker.connection;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpOptions;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpTrace;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;

import siminov.connect.Constants;
import siminov.connect.authorization.AuthorizationFactory;
import siminov.connect.connection.ConnectionResponse;
import siminov.connect.connection.ConnectionStatusCodes;
import siminov.connect.design.authorization.IAuthorization;
import siminov.connect.design.connection.IConnection;
import siminov.connect.design.connection.IConnectionRequest;
import siminov.connect.design.connection.IConnectionResponse;
import siminov.connect.exception.AuthorizationException;
import siminov.connect.exception.ConnectionException;
import siminov.orm.log.Log;

public class HttpConnectionWorker implements IConnection {

	public IConnectionResponse get(final IConnectionRequest connectionRequest) throws ConnectionException {

		if(connectionRequest == null) {
			Log.loge(HttpConnectionWorker.class.getName(), "get", "Invalid Connection Request.");
			throw new ConnectionException(HttpConnectionWorker.class.getName(), "get", "Invalid Connection Request.");
		}
		
		
		/*
		 * Request Parameters
		 */
		String url = connectionRequest.getUrl();
		
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
			String parameterValue = connectionRequest.getQueryParameter(parameter);
			
			httpParams.add(new BasicNameValuePair(parameter, parameterValue));
		}
		
		String queryParameter = URLEncodedUtils.format(httpParams, "utf-8");
		if(!url.endsWith(Constants.CONNECTION_QUERY_PARAMETER_KEYWORD)) {
	        url += Constants.CONNECTION_QUERY_PARAMETER_KEYWORD;
		}

		url += queryParameter;

		
		try {
			httpGet = new HttpGet(url);
		} catch(Exception exception) {
			Log.loge(HttpConnectionWorker.class.getName(), "get", "Exception caught while creating http get, URL: " + url + ", " + exception.getMessage());
			throw new ConnectionException(HttpConnectionWorker.class.getName(), "get", "Exception caught while creating http get, URL: " + url + ", " + exception.getMessage());
		}

		
		
		/*
		 * Add Header Parameters
		 */
		while(headerParameters.hasNext()) {
			
			String header = headerParameters.next();
			String headerValue = connectionRequest.getHeaderParameter(header);
			
	        httpGet.setHeader(header, headerValue);
		}
		

		/* Authenticate */
		sign(httpGet);
		
		
        /* execute */
        BasicHttpResponse httpResponse = null;
        
        try {
        	httpResponse = (BasicHttpResponse) httpClient.execute(httpGet);        	 
        } catch(Exception exception) {
        	Log.loge(HttpConnectionWorker.class.getName(), "get", "Exception caught while executing request, " + exception.getMessage());
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
        	Log.loge(HttpConnectionWorker.class.getName(), "get", "Exception caught while getting input stream, " + exception.getMessage());
        	throw new ConnectionException(HttpConnectionWorker.class.getName(), "get", "Exception caught while getting input stream, " + exception.getMessage());
        }

        
        return new ConnectionResponse(responseCode, inputStream);
	}
	
	public IConnectionResponse head(final IConnectionRequest connectionRequest) throws ConnectionException {
		
		if(connectionRequest == null) {
			Log.loge(HttpConnectionWorker.class.getName(), "head", "Invalid Connection Request.");
			throw new ConnectionException(HttpConnectionWorker.class.getName(), "head", "Invalid Connection Request.");
		}


		/*
		 * Request Parameters
		 */
		String url = connectionRequest.getUrl();
		
		Iterator<String> queryParameters = connectionRequest.getQueryParameters();
		Iterator<String> headerParameters = connectionRequest.getHeaderParameters();
		
		
		/*
		 * Make Request
		 */
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpHead httpHead = null;
		
		try {
			httpHead = new HttpHead(url);
		} catch(Exception exception) {
			Log.loge(HttpConnectionWorker.class.getName(), "head", "Exception caught while creating http head, URL: " + url + ", " + exception.getMessage());
			throw new ConnectionException(HttpConnectionWorker.class.getName(), "head", "Exception caught while creating http head, URL: " + url + ", " + exception.getMessage());
		}


		/*
		 * Add Query Parameters
		 */
		HttpParams httpParams = new BasicHttpParams();

		while(queryParameters.hasNext()) {
			
			String parameter = queryParameters.next();
			String parameterValue = connectionRequest.getQueryParameter(parameter);
			
			httpParams.setParameter(parameter, parameterValue);
		}
		
		
		httpHead.setParams(httpParams);
		
		
		/*
		 * Add Header Parameters
		 */
		while(headerParameters.hasNext()) {
			
			String header = headerParameters.next();
			String headerValue = connectionRequest.getHeaderParameter(header);
			
	        httpHead.setHeader(header, headerValue);
		}
		

		/* Authenticate */
		sign(httpHead);

		
        /* execute */
        BasicHttpResponse httpResponse = null;
        
        try {
        	httpResponse = (BasicHttpResponse) httpClient.execute(httpHead);        	 
        } catch(Exception exception) {
        	Log.loge(HttpConnectionWorker.class.getName(), "head", "Exception caught while executing request, " + exception.getMessage());
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
        	Log.loge(HttpConnectionWorker.class.getName(), "head", "Exception caught while getting input stream, " + exception.getMessage());
        	throw new ConnectionException(HttpConnectionWorker.class.getName(), "head", "Exception caught while getting input stream, " + exception.getMessage());
        }

        
        return new ConnectionResponse(responseCode, inputStream);
	}
	
	public IConnectionResponse post(final IConnectionRequest connectionRequest) throws ConnectionException {
		
		if(connectionRequest == null) {
			Log.loge(HttpConnectionWorker.class.getName(), "post", "Invalid Connection Request.");
			throw new ConnectionException(HttpConnectionWorker.class.getName(), "post", "Invalid Connection Request.");
		}


		/*
		 * Request Parameters
		 */
		String url = connectionRequest.getUrl();
		
		Iterator<String> queryParameters = connectionRequest.getQueryParameters();
		Iterator<String> headerParameters = connectionRequest.getHeaderParameters();
		
		byte[] dataStream = connectionRequest.getDataStream();
		
		
		/*
		 * Make Request
		 */
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = null;
		
		try {
			httpPost = new HttpPost(url);
		} catch(Exception exception) {
			Log.loge(HttpConnectionWorker.class.getName(), "post", "Exception caught while creating http post, URL: " + url + ", " + exception.getMessage());
			throw new ConnectionException(HttpConnectionWorker.class.getName(), "post", "Exception caught while creating http post, URL: " + url + ", " + exception.getMessage());
		}

		
		/*
		 * Add Query Parameters
		 */
		List<NameValuePair> httpQueryParams = new ArrayList<NameValuePair>();  

		while(queryParameters.hasNext()) {
			
			String parameter = queryParameters.next();
			String parameterValue = connectionRequest.getQueryParameter(parameter);
			
			httpQueryParams.add(new BasicNameValuePair(parameter, parameterValue));
		}
		
		
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(httpQueryParams));
		} catch(Exception e) {
			Log.loge(HttpConnectionWorker.class.getName(), "post", "Exception caught while setting http query parameters, " + e.getMessage());
			throw new ConnectionException(HttpConnectionWorker.class.getName(), "post", "Exception caught while setting http query parameters, " + e.getMessage());
		}
		
		
		/*
		 * Add Header Parameters
		 */
		while(headerParameters.hasNext()) {
			
			String header = headerParameters.next();
			String headerValue = connectionRequest.getHeaderParameter(header);
			
	        httpPost.setHeader(header, headerValue);
		}
		

		
		if(dataStream != null && dataStream.length > 0) {
			httpPost.setEntity(new ByteArrayEntity(dataStream));	
		}
		
		
		/* Authenticate */
		sign(httpPost);

		
		/* execute */
        BasicHttpResponse httpResponse = null;
        
        try {
        	httpResponse = (BasicHttpResponse) httpClient.execute(httpPost);        	 
        } catch(Exception exception) {
        	Log.loge(HttpConnectionWorker.class.getName(), "post", "Exception caught while executing request, " + exception.getMessage());
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
        	Log.loge(HttpConnectionWorker.class.getName(), "post", "Exception caught while getting input stream, " + exception.getMessage());
        	throw new ConnectionException(HttpConnectionWorker.class.getName(), "post", "Exception caught while getting input stream, " + exception.getMessage());
        }

        
        return new ConnectionResponse(responseCode, inputStream);
	}
	
	public IConnectionResponse put(final IConnectionRequest connectionRequest) throws ConnectionException {
		
		if(connectionRequest == null) {
			Log.loge(HttpConnectionWorker.class.getName(), "put", "Invalid Connection Request.");
			throw new ConnectionException(HttpConnectionWorker.class.getName(), "put", "Invalid Connection Request.");
		}



		/*
		 * Request Parameters
		 */
		String url = connectionRequest.getUrl();
		
		Iterator<String> queryParameters = connectionRequest.getQueryParameters();
		Iterator<String> headerParameters = connectionRequest.getHeaderParameters();
		
		byte[] dataStream = connectionRequest.getDataStream();
		
		
		/*
		 * Make Request
		 */
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpPut httpPut = null;
		
		try {
			httpPut = new HttpPut(url);
		} catch(Exception exception) {
			Log.loge(HttpConnectionWorker.class.getName(), "put", "Exception caught while creating http put, URL: " + url + ", " + exception.getMessage());
			throw new ConnectionException(HttpConnectionWorker.class.getName(), "put", "Exception caught while creating http put, URL: " + url + ", " + exception.getMessage());
		}

		
		/*
		 * Add Query Parameters
		 */
		HttpParams httpParams = new BasicHttpParams();

		while(queryParameters.hasNext()) {
			
			String parameter = queryParameters.next();
			String parameterValue = connectionRequest.getQueryParameter(parameter);
			
			httpParams.setParameter(parameter, parameterValue);
		}
		
		
		httpPut.setParams(httpParams);
		
		
		/*
		 * Add Header Parameters
		 */
		while(headerParameters.hasNext()) {
			
			String header = headerParameters.next();
			String headerValue = connectionRequest.getHeaderParameter(header);
			
	        httpPut.setHeader(header, headerValue);
		}
		

		
		if(dataStream != null && dataStream.length > 0) {
			httpPut.setEntity(new ByteArrayEntity(dataStream));	
		}
		
		/* Authenticate */
		sign(httpPut);

		
        /* execute */
        BasicHttpResponse httpResponse = null;
        
        try {
        	httpResponse = (BasicHttpResponse) httpClient.execute(httpPut);        	 
        } catch(Exception exception) {
        	Log.loge(HttpConnectionWorker.class.getName(), "put", "Exception caught while executing request, " + exception.getMessage());
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
        	Log.loge(HttpConnectionWorker.class.getName(), "put", "Exception caught while getting input stream, " + exception.getMessage());
        	throw new ConnectionException(HttpConnectionWorker.class.getName(), "put", "Exception caught while getting input stream, " + exception.getMessage());
        }

        
        return new ConnectionResponse(responseCode, inputStream);
	}

	public IConnectionResponse delete(final IConnectionRequest connectionRequest) throws ConnectionException {
		
		if(connectionRequest == null) {
			Log.loge(HttpConnectionWorker.class.getName(), "delete", "Invalid Connection Request.");
			throw new ConnectionException(HttpConnectionWorker.class.getName(), "delete", "Invalid Connection Request.");
		}




		/*
		 * Request Parameters
		 */
		String url = connectionRequest.getUrl();
		
		Iterator<String> queryParameters = connectionRequest.getQueryParameters();
		Iterator<String> headerParameters = connectionRequest.getHeaderParameters();
		
		
		/*
		 * Make Request
		 */
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpDelete httpDelete = null;
		
		try {
			httpDelete = new HttpDelete(url);
		} catch(Exception exception) {
			Log.loge(HttpConnectionWorker.class.getName(), "delete", "Exception caught while creating http delete, URL: " + url + ", " + exception.getMessage());
			throw new ConnectionException(HttpConnectionWorker.class.getName(), "delete", "Exception caught while creating http delete, URL: " + url + ", " + exception.getMessage());
		}

		
		/*
		 * Add Query Parameters
		 */
		HttpParams httpParams = new BasicHttpParams();

		while(queryParameters.hasNext()) {
			
			String parameter = queryParameters.next();
			String parameterValue = connectionRequest.getQueryParameter(parameter);
			
			httpParams.setParameter(parameter, parameterValue);
		}
		
		
		httpDelete.setParams(httpParams);
		
		
		/*
		 * Add Header Parameters
		 */
		while(headerParameters.hasNext()) {
			
			String header = headerParameters.next();
			String headerValue = connectionRequest.getHeaderParameter(header);
			
	        httpDelete.setHeader(header, headerValue);
		}
		

		/* Authenticate */
		sign(httpDelete);

		
        /* execute */
        BasicHttpResponse httpResponse = null;
        
        try {
        	httpResponse = (BasicHttpResponse) httpClient.execute(httpDelete);        	 
        } catch(Exception exception) {
        	Log.loge(HttpConnectionWorker.class.getName(), "delete", "Exception caught while executing request, " + exception.getMessage());
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
        	Log.loge(HttpConnectionWorker.class.getName(), "delete", "Exception caught while getting input stream, " + exception.getMessage());
        	throw new ConnectionException(HttpConnectionWorker.class.getName(), "delete", "Exception caught while getting input stream, " + exception.getMessage());
        }

        
        return new ConnectionResponse(responseCode, inputStream);
	}

	public IConnectionResponse trace(final IConnectionRequest connectionRequest) throws ConnectionException {
		
		if(connectionRequest == null) {
			Log.loge(HttpConnectionWorker.class.getName(), "trace", "Invalid Connection Request.");
			throw new ConnectionException(HttpConnectionWorker.class.getName(), "trace", "Invalid Connection Request.");
		}




		/*
		 * Request Parameters
		 */
		String url = connectionRequest.getUrl();
		
		Iterator<String> queryParameters = connectionRequest.getQueryParameters();
		Iterator<String> headerParameters = connectionRequest.getHeaderParameters();
		
		
		
		/*
		 * Make Request
		 */
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpTrace httpTrace = null;
		
		try {
			httpTrace = new HttpTrace(url);
		} catch(Exception exception) {
			Log.loge(HttpConnectionWorker.class.getName(), "trace", "Exception caught while creating http trace, URL: " + url + ", " + exception.getMessage());
			throw new ConnectionException(HttpConnectionWorker.class.getName(), "trace", "Exception caught while creating http trace, URL: " + url + ", " + exception.getMessage());
		}

		
		/*
		 * Add Query Parameters
		 */
		HttpParams httpParams = new BasicHttpParams();

		while(queryParameters.hasNext()) {
			
			String parameter = queryParameters.next();
			String parameterValue = connectionRequest.getQueryParameter(parameter);
			
			httpParams.setParameter(parameter, parameterValue);
		}
		
		
		httpTrace.setParams(httpParams);
		
		
		/*
		 * Add Header Parameters
		 */
		while(headerParameters.hasNext()) {
			
			String header = headerParameters.next();
			String headerValue = connectionRequest.getHeaderParameter(header);
			
	        httpTrace.setHeader(header, headerValue);
		}
		

		/* Authenticate */
		sign(httpTrace);
		
		
        /* execute */
        BasicHttpResponse httpResponse = null;
        
        try {
        	httpResponse = (BasicHttpResponse) httpClient.execute(httpTrace);        	 
        } catch(Exception exception) {
        	Log.loge(HttpConnectionWorker.class.getName(), "trace", "Exception caught while executing request, " + exception.getMessage());
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
        	Log.loge(HttpConnectionWorker.class.getName(), "trace", "Exception caught while getting input stream, " + exception.getMessage());
        	throw new ConnectionException(HttpConnectionWorker.class.getName(), "trace", "Exception caught while getting input stream, " + exception.getMessage());
        }

        
        return new ConnectionResponse(responseCode, inputStream);
	}

	public IConnectionResponse options(final IConnectionRequest connectionRequest) throws ConnectionException {
		
		if(connectionRequest == null) {
			Log.loge(HttpConnectionWorker.class.getName(), "options", "Invalid Connection Request.");
			throw new ConnectionException(HttpConnectionWorker.class.getName(), "options", "Invalid Connection Request.");
		}




		/*
		 * Request Parameters
		 */
		String url = connectionRequest.getUrl();
		
		Iterator<String> queryParameters = connectionRequest.getQueryParameters();
		Iterator<String> headerParameters = connectionRequest.getHeaderParameters();
		
		
		/*
		 * Make Request
		 */
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpOptions httpOptions = null;
		
		try {
			httpOptions = new HttpOptions(url);
		} catch(Exception exception) {
			Log.loge(HttpConnectionWorker.class.getName(), "options", "Exception caught while creating http options, URL: " + url + ", " + exception.getMessage());
			throw new ConnectionException(HttpConnectionWorker.class.getName(), "options", "Exception caught while creating http options, URL: " + url + ", " + exception.getMessage());
		}

		
		/*
		 * Add Query Parameters
		 */
		HttpParams httpParams = new BasicHttpParams();

		while(queryParameters.hasNext()) {
			
			String parameter = queryParameters.next();
			String parameterValue = connectionRequest.getQueryParameter(parameter);
			
			httpParams.setParameter(parameter, parameterValue);
		}
		
		
		httpOptions.setParams(httpParams);
		
		
		/*
		 * Add Header Parameters
		 */
		while(headerParameters.hasNext()) {
			
			String header = headerParameters.next();
			String headerValue = connectionRequest.getHeaderParameter(header);
			
	        httpOptions.setHeader(header, headerValue);
		}
		

		
		/* Authenticate */
		sign(httpOptions);

		
        /* execute */
        BasicHttpResponse httpResponse = null;
        
        try {
        	httpResponse = (BasicHttpResponse) httpClient.execute(httpOptions);        	 
        } catch(Exception exception) {
        	Log.loge(HttpConnectionWorker.class.getName(), "options", "Exception caught while executing request, " + exception.getMessage());
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
        	Log.loge(HttpConnectionWorker.class.getName(), "options", "Exception caught while getting input stream, " + exception.getMessage());
        	throw new ConnectionException(HttpConnectionWorker.class.getName(), "options", "Exception caught while getting input stream, " + exception.getMessage());
        }

        
        return new ConnectionResponse(responseCode, inputStream);
	}

	public IConnectionResponse connect(final IConnectionRequest connectionRequest) throws ConnectionException {
		
		if(connectionRequest == null) {
			Log.loge(HttpConnectionWorker.class.getName(), "connect", "Invalid Connection Request.");
			throw new ConnectionException(HttpConnectionWorker.class.getName(), "connect", "Invalid Connection Request.");
		}


		return null;
	}

	public IConnectionResponse patch(final IConnectionRequest connectionRequest) throws ConnectionException {
		
		if(connectionRequest == null) {
			Log.loge(HttpConnectionWorker.class.getName(), "patch", "Invalid Connection Request.");
			throw new ConnectionException(HttpConnectionWorker.class.getName(), "patch", "Invalid Connection Request.");
		}

		return null;
	}

	
	private void sign(final HttpRequestBase httpRequestBase) throws ConnectionException {

		AuthorizationFactory authorizationFactory = AuthorizationFactory.getInstance();
		IAuthorization authorization = authorizationFactory.getAuthorization();

		if(authorization == null) {
			return;
		}
		
		
		try {
			authorization.doSignature(httpRequestBase);
		} catch(AuthorizationException authorizationException) {
			Log.loge(HttpConnectionWorker.class.getName(), "sign", "Authozation Exception caught while signing http request, " + authorizationException.getMessage());
			throw new ConnectionException(HttpConnectionWorker.class.getName(), "sign", "Authozation Exception caught while signing http request, " + authorizationException.getMessage());
		}
	}
}