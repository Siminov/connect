package siminov.connect.worker.connection;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
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
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.SingleClientConnManager;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;

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

public class HttpsConnectionWorker implements IConnection {

	public IConnectionResponse get(final IConnectionRequest connectionRequest) throws ConnectionException {

		if(connectionRequest == null) {
			Log.loge(HttpsConnectionWorker.class.getName(), "get", "Invalid Connection Request.");
			throw new ConnectionException(HttpsConnectionWorker.class.getName(), "get", "Invalid Connection Request.");
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
		certification(httpClient);
		
		HttpGet httpGet = null;
		
		try {
			httpGet = new HttpGet(url);
		} catch(Exception exception) {
			Log.loge(HttpsConnectionWorker.class.getName(), "get", "Exception caught while creating http get, URL: " + url + ", " + exception.getMessage());
			throw new ConnectionException(HttpsConnectionWorker.class.getName(), "get", "Exception caught while creating http get, URL: " + url + ", " + exception.getMessage());
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
		
		
		httpGet.setParams(httpParams);
		
		
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
        	Log.loge(HttpsConnectionWorker.class.getName(), "get", "Exception caught while executing request, " + exception.getMessage());
        	throw new ConnectionException(HttpsConnectionWorker.class.getName(), "get", "Exception caught while executing request, " + exception.getMessage());
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
        	Log.loge(HttpsConnectionWorker.class.getName(), "get", "Exception caught while getting input stream, " + exception.getMessage());
        	throw new ConnectionException(HttpsConnectionWorker.class.getName(), "get", "Exception caught while getting input stream, " + exception.getMessage());
        }

        
        return new ConnectionResponse(responseCode, inputStream);
	}
	
	public IConnectionResponse head(final IConnectionRequest connectionRequest) throws ConnectionException {
		
		if(connectionRequest == null) {
			Log.loge(HttpsConnectionWorker.class.getName(), "head", "Invalid Connection Request.");
			throw new ConnectionException(HttpsConnectionWorker.class.getName(), "head", "Invalid Connection Request.");
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
		certification(httpClient);

		HttpHead httpHead = null;
		
		try {
			httpHead = new HttpHead(url);
		} catch(Exception exception) {
			Log.loge(HttpsConnectionWorker.class.getName(), "head", "Exception caught while creating http head, URL: " + url + ", " + exception.getMessage());
			throw new ConnectionException(HttpsConnectionWorker.class.getName(), "head", "Exception caught while creating http head, URL: " + url + ", " + exception.getMessage());
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
        	Log.loge(HttpsConnectionWorker.class.getName(), "head", "Exception caught while executing request, " + exception.getMessage());
        	throw new ConnectionException(HttpsConnectionWorker.class.getName(), "head", "Exception caught while executing request, " + exception.getMessage());
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
        	Log.loge(HttpsConnectionWorker.class.getName(), "head", "Exception caught while getting input stream, " + exception.getMessage());
        	throw new ConnectionException(HttpsConnectionWorker.class.getName(), "head", "Exception caught while getting input stream, " + exception.getMessage());
        }

        
        return new ConnectionResponse(responseCode, inputStream);
	}
	
	public IConnectionResponse post(final IConnectionRequest connectionRequest) throws ConnectionException {
		
		if(connectionRequest == null) {
			Log.loge(HttpsConnectionWorker.class.getName(), "post", "Invalid Connection Request.");
			throw new ConnectionException(HttpsConnectionWorker.class.getName(), "post", "Invalid Connection Request.");
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
		certification(httpClient);

		HttpPost httpPost = null;
		
		try {
			httpPost = new HttpPost(url);
		} catch(Exception exception) {
			Log.loge(HttpsConnectionWorker.class.getName(), "post", "Exception caught while creating http post, URL: " + url + ", " + exception.getMessage());
			throw new ConnectionException(HttpsConnectionWorker.class.getName(), "post", "Exception caught while creating http post, URL: " + url + ", " + exception.getMessage());
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
			Log.loge(HttpsConnectionWorker.class.getName(), "post", "Exception caught while setting http query parameters, " + e.getMessage());
			throw new ConnectionException(HttpsConnectionWorker.class.getName(), "post", "Exception caught while setting http query parameters, " + e.getMessage());
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
        	Log.loge(HttpsConnectionWorker.class.getName(), "post", "Exception caught while executing request, " + exception.getMessage());
        	throw new ConnectionException(HttpsConnectionWorker.class.getName(), "post", "Exception caught while executing request, " + exception.getMessage());
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
        	Log.loge(HttpsConnectionWorker.class.getName(), "post", "Exception caught while getting input stream, " + exception.getMessage());
        	throw new ConnectionException(HttpsConnectionWorker.class.getName(), "post", "Exception caught while getting input stream, " + exception.getMessage());
        }

        
        return new ConnectionResponse(responseCode, inputStream);
	}
	
	public IConnectionResponse put(final IConnectionRequest connectionRequest) throws ConnectionException {
		
		if(connectionRequest == null) {
			Log.loge(HttpsConnectionWorker.class.getName(), "put", "Invalid Connection Request.");
			throw new ConnectionException(HttpsConnectionWorker.class.getName(), "put", "Invalid Connection Request.");
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
		certification(httpClient);

		HttpPut httpPut = null;
		
		try {
			httpPut = new HttpPut(url);
		} catch(Exception exception) {
			Log.loge(HttpsConnectionWorker.class.getName(), "put", "Exception caught while creating http put, URL: " + url + ", " + exception.getMessage());
			throw new ConnectionException(HttpsConnectionWorker.class.getName(), "put", "Exception caught while creating http put, URL: " + url + ", " + exception.getMessage());
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
        	Log.loge(HttpsConnectionWorker.class.getName(), "put", "Exception caught while executing request, " + exception.getMessage());
        	throw new ConnectionException(HttpsConnectionWorker.class.getName(), "put", "Exception caught while executing request, " + exception.getMessage());
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
        	Log.loge(HttpsConnectionWorker.class.getName(), "put", "Exception caught while getting input stream, " + exception.getMessage());
        	throw new ConnectionException(HttpsConnectionWorker.class.getName(), "put", "Exception caught while getting input stream, " + exception.getMessage());
        }

        
        return new ConnectionResponse(responseCode, inputStream);
	}

	public IConnectionResponse delete(final IConnectionRequest connectionRequest) throws ConnectionException {
		
		if(connectionRequest == null) {
			Log.loge(HttpsConnectionWorker.class.getName(), "delete", "Invalid Connection Request.");
			throw new ConnectionException(HttpsConnectionWorker.class.getName(), "delete", "Invalid Connection Request.");
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
		certification(httpClient);

		HttpDelete httpDelete = null;
		
		try {
			httpDelete = new HttpDelete(url);
		} catch(Exception exception) {
			Log.loge(HttpsConnectionWorker.class.getName(), "delete", "Exception caught while creating http delete, URL: " + url + ", " + exception.getMessage());
			throw new ConnectionException(HttpsConnectionWorker.class.getName(), "delete", "Exception caught while creating http delete, URL: " + url + ", " + exception.getMessage());
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
        	Log.loge(HttpsConnectionWorker.class.getName(), "delete", "Exception caught while executing request, " + exception.getMessage());
        	throw new ConnectionException(HttpsConnectionWorker.class.getName(), "delete", "Exception caught while executing request, " + exception.getMessage());
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
        	Log.loge(HttpsConnectionWorker.class.getName(), "delete", "Exception caught while getting input stream, " + exception.getMessage());
        	throw new ConnectionException(HttpsConnectionWorker.class.getName(), "delete", "Exception caught while getting input stream, " + exception.getMessage());
        }

        
        return new ConnectionResponse(responseCode, inputStream);
	}

	public IConnectionResponse trace(final IConnectionRequest connectionRequest) throws ConnectionException {
		
		if(connectionRequest == null) {
			Log.loge(HttpsConnectionWorker.class.getName(), "trace", "Invalid Connection Request.");
			throw new ConnectionException(HttpsConnectionWorker.class.getName(), "trace", "Invalid Connection Request.");
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
		certification(httpClient);

		HttpTrace httpTrace = null;
		
		try {
			httpTrace = new HttpTrace(url);
		} catch(Exception exception) {
			Log.loge(HttpsConnectionWorker.class.getName(), "trace", "Exception caught while creating http trace, URL: " + url + ", " + exception.getMessage());
			throw new ConnectionException(HttpsConnectionWorker.class.getName(), "trace", "Exception caught while creating http trace, URL: " + url + ", " + exception.getMessage());
		}

		
		/*
		 * Add Query Parameters
		 */
		HttpParams httpParams = new BasicHttpParams();

		while(headerParameters.hasNext()) {
			
			String parameter = headerParameters.next();
			String parameterValue = connectionRequest.getHeaderParameter(parameter);
			
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
        	Log.loge(HttpsConnectionWorker.class.getName(), "trace", "Exception caught while executing request, " + exception.getMessage());
        	throw new ConnectionException(HttpsConnectionWorker.class.getName(), "trace", "Exception caught while executing request, " + exception.getMessage());
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
        	Log.loge(HttpsConnectionWorker.class.getName(), "trace", "Exception caught while getting input stream, " + exception.getMessage());
        	throw new ConnectionException(HttpsConnectionWorker.class.getName(), "trace", "Exception caught while getting input stream, " + exception.getMessage());
        }

        
        return new ConnectionResponse(responseCode, inputStream);
	}

	public IConnectionResponse options(final IConnectionRequest connectionRequest) throws ConnectionException {
		
		if(connectionRequest == null) {
			Log.loge(HttpsConnectionWorker.class.getName(), "options", "Invalid Connection Request.");
			throw new ConnectionException(HttpsConnectionWorker.class.getName(), "options", "Invalid Connection Request.");
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
		certification(httpClient);

		HttpOptions httpOptions = null;
		
		try {
			httpOptions = new HttpOptions(url);
		} catch(Exception exception) {
			Log.loge(HttpsConnectionWorker.class.getName(), "options", "Exception caught while creating http options, URL: " + url + ", " + exception.getMessage());
			throw new ConnectionException(HttpsConnectionWorker.class.getName(), "options", "Exception caught while creating http options, URL: " + url + ", " + exception.getMessage());
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
        	Log.loge(HttpsConnectionWorker.class.getName(), "options", "Exception caught while executing request, " + exception.getMessage());
        	throw new ConnectionException(HttpsConnectionWorker.class.getName(), "options", "Exception caught while executing request, " + exception.getMessage());
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
        	Log.loge(HttpsConnectionWorker.class.getName(), "options", "Exception caught while getting input stream, " + exception.getMessage());
        	throw new ConnectionException(HttpsConnectionWorker.class.getName(), "options", "Exception caught while getting input stream, " + exception.getMessage());
        }

        
        return new ConnectionResponse(responseCode, inputStream);
	}

	public IConnectionResponse connect(final IConnectionRequest connectionRequest) throws ConnectionException {
		
		if(connectionRequest == null) {
			Log.loge(HttpsConnectionWorker.class.getName(), "connect", "Invalid Connection Request.");
			throw new ConnectionException(HttpsConnectionWorker.class.getName(), "connect", "Invalid Connection Request.");
		}


		return null;
	}

	public IConnectionResponse patch(final IConnectionRequest connectionRequest) throws ConnectionException {
		
		if(connectionRequest == null) {
			Log.loge(HttpsConnectionWorker.class.getName(), "patch", "Invalid Connection Request.");
			throw new ConnectionException(HttpsConnectionWorker.class.getName(), "patch", "Invalid Connection Request.");
		}

		return null;
	}

	
	private void certification(DefaultHttpClient defaultHttpClient) {
		
		HostnameVerifier hostnameVerifier = org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;

		SchemeRegistry registry = new SchemeRegistry();
		SSLSocketFactory socketFactory = SSLSocketFactory.getSocketFactory();

		socketFactory.setHostnameVerifier((X509HostnameVerifier) hostnameVerifier);
		registry.register(new Scheme("https", socketFactory, 443));
		
		SingleClientConnManager singleClientConnManager = new SingleClientConnManager(defaultHttpClient.getParams(), registry);
		DefaultHttpClient httpClient = new DefaultHttpClient(singleClientConnManager, defaultHttpClient.getParams());

		// Set verifier     
		HttpsURLConnection.setDefaultHostnameVerifier(hostnameVerifier);
		
		defaultHttpClient = httpClient;
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
			Log.loge(HttpsConnectionWorker.class.getName(), "sign", "Authozation Exception caught while signing http request, " + authorizationException.getMessage());
			throw new ConnectionException(HttpsConnectionWorker.class.getName(), "sign", "Authozation Exception caught while signing http request, " + authorizationException.getMessage());
		}
	}
}
