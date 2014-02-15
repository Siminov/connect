package siminov.connect.connection.http;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;

import siminov.connect.authentication.AuthenticationFactory;
import siminov.connect.authentication.Credential;
import siminov.connect.authentication.CredentialManager;
import siminov.connect.authentication.design.IAuthenticate;
import siminov.connect.connection.ConnectionRequest;
import siminov.connect.connection.ConnectionResponse;
import siminov.connect.connection.ConnectionStatusCodes;
import siminov.connect.connection.IConnection;
import siminov.connect.exception.AuthenticationException;
import siminov.connect.exception.ConnectionException;
import siminov.orm.log.Log;

public class Connection implements IConnection{

	public ConnectionResponse get(final ConnectionRequest connectionRequest) throws ConnectionException {

		if(connectionRequest == null) {
			Log.loge(Connection.class.getName(), "get", "Invalid Connection Request.");
			throw new ConnectionException(Connection.class.getName(), "get", "Invalid Connection Request.");
		}
		
		
		/*
		 * Request Parameters
		 */
		String url = connectionRequest.getUrl();
		
		Map<String, String> queryParameters = connectionRequest.getQueryParameters();
		Map<String, String> headerParameters = connectionRequest.getHeaderParameters();
		
		
		/*
		 * Make Request
		 */
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = null;
		
		try {
			httpGet = new HttpGet(url);
		} catch(Exception exception) {
			Log.loge(Connection.class.getName(), "get", "Exception caught while creating http get, URL: " + url + ", " + exception.getMessage());
			throw new ConnectionException(Connection.class.getName(), "get", "Exception caught while creating http get, URL: " + url + ", " + exception.getMessage());
		}

		
		/*
		 * Add Query Parameters
		 */
		HttpParams httpParams = new BasicHttpParams();

		Iterator<String> parameters = queryParameters.keySet().iterator();
		while(parameters.hasNext()) {
			
			String parameter = parameters.next();
			String parameterValue = queryParameters.get(parameter);
			
			httpParams.setParameter(parameter, parameterValue);
		}
		
		
		httpGet.setParams(httpParams);
		
		
		/*
		 * Add Header Parameters
		 */
		Iterator<String> headers = headerParameters.keySet().iterator();
		while(headers.hasNext()) {
			
			String header = headers.next();
			String headerValue = headerParameters.get(header);
			
	        httpGet.setHeader(header, headerValue);
		}
		

		/* Authenticate */
		sign(httpGet);
		
		
        /* execute */
        BasicHttpResponse httpResponse = null;
        
        try {
        	httpResponse = (BasicHttpResponse) httpClient.execute(httpGet);        	 
        } catch(Exception exception) {
        	Log.loge(Connection.class.getName(), "get", "Exception caught while executing request, " + exception.getMessage());
        	throw new ConnectionException(Connection.class.getName(), "get", "Exception caught while executing request, " + exception.getMessage());
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
        	Log.loge(Connection.class.getName(), "get", "Exception caught while getting input stream, " + exception.getMessage());
        	throw new ConnectionException(Connection.class.getName(), "get", "Exception caught while getting input stream, " + exception.getMessage());
        }

        
        return new ConnectionResponse(responseCode, inputStream);
	}
	
	public ConnectionResponse head(final ConnectionRequest connectionRequest) throws ConnectionException {
		
		if(connectionRequest == null) {
			Log.loge(Connection.class.getName(), "head", "Invalid Connection Request.");
			throw new ConnectionException(Connection.class.getName(), "head", "Invalid Connection Request.");
		}


		/*
		 * Request Parameters
		 */
		String url = connectionRequest.getUrl();
		
		Map<String, String> queryParameters = connectionRequest.getQueryParameters();
		Map<String, String> headerParameters = connectionRequest.getHeaderParameters();
		
		
		/*
		 * Make Request
		 */
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpHead httpHead = null;
		
		try {
			httpHead = new HttpHead(url);
		} catch(Exception exception) {
			Log.loge(Connection.class.getName(), "head", "Exception caught while creating http head, URL: " + url + ", " + exception.getMessage());
			throw new ConnectionException(Connection.class.getName(), "head", "Exception caught while creating http head, URL: " + url + ", " + exception.getMessage());
		}


		/*
		 * Add Query Parameters
		 */
		HttpParams httpParams = new BasicHttpParams();

		Iterator<String> parameters = queryParameters.keySet().iterator();
		while(parameters.hasNext()) {
			
			String parameter = parameters.next();
			String parameterValue = queryParameters.get(parameter);
			
			httpParams.setParameter(parameter, parameterValue);
		}
		
		
		httpHead.setParams(httpParams);
		
		
		/*
		 * Add Header Parameters
		 */
		Iterator<String> headers = headerParameters.keySet().iterator();
		while(headers.hasNext()) {
			
			String header = headers.next();
			String headerValue = headerParameters.get(header);
			
	        httpHead.setHeader(header, headerValue);
		}
		

		/* Authenticate */
		sign(httpHead);

		
        /* execute */
        BasicHttpResponse httpResponse = null;
        
        try {
        	httpResponse = (BasicHttpResponse) httpClient.execute(httpHead);        	 
        } catch(Exception exception) {
        	Log.loge(Connection.class.getName(), "head", "Exception caught while executing request, " + exception.getMessage());
        	throw new ConnectionException(Connection.class.getName(), "head", "Exception caught while executing request, " + exception.getMessage());
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
        	Log.loge(Connection.class.getName(), "head", "Exception caught while getting input stream, " + exception.getMessage());
        	throw new ConnectionException(Connection.class.getName(), "head", "Exception caught while getting input stream, " + exception.getMessage());
        }

        
        return new ConnectionResponse(responseCode, inputStream);
	}
	
	public ConnectionResponse post(final ConnectionRequest connectionRequest) throws ConnectionException {
		
		if(connectionRequest == null) {
			Log.loge(Connection.class.getName(), "post", "Invalid Connection Request.");
			throw new ConnectionException(Connection.class.getName(), "post", "Invalid Connection Request.");
		}


		/*
		 * Request Parameters
		 */
		String url = connectionRequest.getUrl();
		
		Map<String, String> queryParameters = connectionRequest.getQueryParameters();
		Map<String, String> headerParameters = connectionRequest.getHeaderParameters();
		
		byte[] dataStream = connectionRequest.getDataStream();
		
		
		/*
		 * Make Request
		 */
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = null;
		
		try {
			httpPost = new HttpPost(url);
		} catch(Exception exception) {
			Log.loge(Connection.class.getName(), "post", "Exception caught while creating http post, URL: " + url + ", " + exception.getMessage());
			throw new ConnectionException(Connection.class.getName(), "post", "Exception caught while creating http post, URL: " + url + ", " + exception.getMessage());
		}

		
		/*
		 * Add Query Parameters
		 */
		List<NameValuePair> httpQueryParams = new ArrayList<NameValuePair>();  

		Iterator<String> parameters = queryParameters.keySet().iterator();
		while(parameters.hasNext()) {
			
			String parameter = parameters.next();
			String parameterValue = queryParameters.get(parameter);
			
			httpQueryParams.add(new BasicNameValuePair(parameter, parameterValue));
		}
		
		
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(httpQueryParams));
		} catch(Exception e) {
			Log.loge(Connection.class.getName(), "post", "Exception caught while setting http query parameters, " + e.getMessage());
			throw new ConnectionException(Connection.class.getName(), "post", "Exception caught while setting http query parameters, " + e.getMessage());
		}
		
		
		/*
		 * Add Header Parameters
		 */
		Iterator<String> headers = headerParameters.keySet().iterator();
		while(headers.hasNext()) {
			
			String header = headers.next();
			String headerValue = headerParameters.get(header);
			
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
        	Log.loge(Connection.class.getName(), "post", "Exception caught while executing request, " + exception.getMessage());
        	throw new ConnectionException(Connection.class.getName(), "post", "Exception caught while executing request, " + exception.getMessage());
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
        	Log.loge(Connection.class.getName(), "post", "Exception caught while getting input stream, " + exception.getMessage());
        	throw new ConnectionException(Connection.class.getName(), "post", "Exception caught while getting input stream, " + exception.getMessage());
        }

        
        return new ConnectionResponse(responseCode, inputStream);
	}
	
	public ConnectionResponse put(final ConnectionRequest connectionRequest) throws ConnectionException {
		
		if(connectionRequest == null) {
			Log.loge(Connection.class.getName(), "put", "Invalid Connection Request.");
			throw new ConnectionException(Connection.class.getName(), "put", "Invalid Connection Request.");
		}



		/*
		 * Request Parameters
		 */
		String url = connectionRequest.getUrl();
		
		Map<String, String> queryParameters = connectionRequest.getQueryParameters();
		Map<String, String> headerParameters = connectionRequest.getHeaderParameters();
		
		byte[] dataStream = connectionRequest.getDataStream();
		
		
		/*
		 * Make Request
		 */
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpPut httpPut = null;
		
		try {
			httpPut = new HttpPut(url);
		} catch(Exception exception) {
			Log.loge(Connection.class.getName(), "put", "Exception caught while creating http put, URL: " + url + ", " + exception.getMessage());
			throw new ConnectionException(Connection.class.getName(), "put", "Exception caught while creating http put, URL: " + url + ", " + exception.getMessage());
		}

		
		/*
		 * Add Query Parameters
		 */
		HttpParams httpParams = new BasicHttpParams();

		Iterator<String> parameters = queryParameters.keySet().iterator();
		while(parameters.hasNext()) {
			
			String parameter = parameters.next();
			String parameterValue = queryParameters.get(parameter);
			
			httpParams.setParameter(parameter, parameterValue);
		}
		
		
		httpPut.setParams(httpParams);
		
		
		/*
		 * Add Header Parameters
		 */
		Iterator<String> headers = headerParameters.keySet().iterator();
		while(headers.hasNext()) {
			
			String header = headers.next();
			String headerValue = headerParameters.get(header);
			
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
        	Log.loge(Connection.class.getName(), "put", "Exception caught while executing request, " + exception.getMessage());
        	throw new ConnectionException(Connection.class.getName(), "put", "Exception caught while executing request, " + exception.getMessage());
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
        	Log.loge(Connection.class.getName(), "put", "Exception caught while getting input stream, " + exception.getMessage());
        	throw new ConnectionException(Connection.class.getName(), "put", "Exception caught while getting input stream, " + exception.getMessage());
        }

        
        return new ConnectionResponse(responseCode, inputStream);
	}

	public ConnectionResponse delete(final ConnectionRequest connectionRequest) throws ConnectionException {
		
		if(connectionRequest == null) {
			Log.loge(Connection.class.getName(), "delete", "Invalid Connection Request.");
			throw new ConnectionException(Connection.class.getName(), "delete", "Invalid Connection Request.");
		}




		/*
		 * Request Parameters
		 */
		String url = connectionRequest.getUrl();
		
		Map<String, String> queryParameters = connectionRequest.getQueryParameters();
		Map<String, String> headerParameters = connectionRequest.getHeaderParameters();
		
		
		/*
		 * Make Request
		 */
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpDelete httpDelete = null;
		
		try {
			httpDelete = new HttpDelete(url);
		} catch(Exception exception) {
			Log.loge(Connection.class.getName(), "delete", "Exception caught while creating http delete, URL: " + url + ", " + exception.getMessage());
			throw new ConnectionException(Connection.class.getName(), "delete", "Exception caught while creating http delete, URL: " + url + ", " + exception.getMessage());
		}

		
		/*
		 * Add Query Parameters
		 */
		HttpParams httpParams = new BasicHttpParams();

		Iterator<String> parameters = queryParameters.keySet().iterator();
		while(parameters.hasNext()) {
			
			String parameter = parameters.next();
			String parameterValue = queryParameters.get(parameter);
			
			httpParams.setParameter(parameter, parameterValue);
		}
		
		
		httpDelete.setParams(httpParams);
		
		
		/*
		 * Add Header Parameters
		 */
		Iterator<String> headers = headerParameters.keySet().iterator();
		while(headers.hasNext()) {
			
			String header = headers.next();
			String headerValue = headerParameters.get(header);
			
	        httpDelete.setHeader(header, headerValue);
		}
		

		/* Authenticate */
		sign(httpDelete);

		
        /* execute */
        BasicHttpResponse httpResponse = null;
        
        try {
        	httpResponse = (BasicHttpResponse) httpClient.execute(httpDelete);        	 
        } catch(Exception exception) {
        	Log.loge(Connection.class.getName(), "delete", "Exception caught while executing request, " + exception.getMessage());
        	throw new ConnectionException(Connection.class.getName(), "delete", "Exception caught while executing request, " + exception.getMessage());
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
        	Log.loge(Connection.class.getName(), "delete", "Exception caught while getting input stream, " + exception.getMessage());
        	throw new ConnectionException(Connection.class.getName(), "delete", "Exception caught while getting input stream, " + exception.getMessage());
        }

        
        return new ConnectionResponse(responseCode, inputStream);
	}

	public ConnectionResponse trace(final ConnectionRequest connectionRequest) throws ConnectionException {
		
		if(connectionRequest == null) {
			Log.loge(Connection.class.getName(), "trace", "Invalid Connection Request.");
			throw new ConnectionException(Connection.class.getName(), "trace", "Invalid Connection Request.");
		}




		/*
		 * Request Parameters
		 */
		String url = connectionRequest.getUrl();
		
		Map<String, String> queryParameters = connectionRequest.getQueryParameters();
		Map<String, String> headerParameters = connectionRequest.getHeaderParameters();
		
		
		
		/*
		 * Make Request
		 */
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpTrace httpTrace = null;
		
		try {
			httpTrace = new HttpTrace(url);
		} catch(Exception exception) {
			Log.loge(Connection.class.getName(), "trace", "Exception caught while creating http trace, URL: " + url + ", " + exception.getMessage());
			throw new ConnectionException(Connection.class.getName(), "trace", "Exception caught while creating http trace, URL: " + url + ", " + exception.getMessage());
		}

		
		/*
		 * Add Query Parameters
		 */
		HttpParams httpParams = new BasicHttpParams();

		Iterator<String> parameters = queryParameters.keySet().iterator();
		while(parameters.hasNext()) {
			
			String parameter = parameters.next();
			String parameterValue = queryParameters.get(parameter);
			
			httpParams.setParameter(parameter, parameterValue);
		}
		
		
		httpTrace.setParams(httpParams);
		
		
		/*
		 * Add Header Parameters
		 */
		Iterator<String> headers = headerParameters.keySet().iterator();
		while(headers.hasNext()) {
			
			String header = headers.next();
			String headerValue = headerParameters.get(header);
			
	        httpTrace.setHeader(header, headerValue);
		}
		

		/* Authenticate */
		sign(httpTrace);
		
		
        /* execute */
        BasicHttpResponse httpResponse = null;
        
        try {
        	httpResponse = (BasicHttpResponse) httpClient.execute(httpTrace);        	 
        } catch(Exception exception) {
        	Log.loge(Connection.class.getName(), "trace", "Exception caught while executing request, " + exception.getMessage());
        	throw new ConnectionException(Connection.class.getName(), "trace", "Exception caught while executing request, " + exception.getMessage());
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
        	Log.loge(Connection.class.getName(), "trace", "Exception caught while getting input stream, " + exception.getMessage());
        	throw new ConnectionException(Connection.class.getName(), "trace", "Exception caught while getting input stream, " + exception.getMessage());
        }

        
        return new ConnectionResponse(responseCode, inputStream);
	}

	public ConnectionResponse options(final ConnectionRequest connectionRequest) throws ConnectionException {
		
		if(connectionRequest == null) {
			Log.loge(Connection.class.getName(), "options", "Invalid Connection Request.");
			throw new ConnectionException(Connection.class.getName(), "options", "Invalid Connection Request.");
		}




		/*
		 * Request Parameters
		 */
		String url = connectionRequest.getUrl();
		
		Map<String, String> queryParameters = connectionRequest.getQueryParameters();
		Map<String, String> headerParameters = connectionRequest.getHeaderParameters();
		
		
		/*
		 * Make Request
		 */
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpOptions httpOptions = null;
		
		try {
			httpOptions = new HttpOptions(url);
		} catch(Exception exception) {
			Log.loge(Connection.class.getName(), "options", "Exception caught while creating http options, URL: " + url + ", " + exception.getMessage());
			throw new ConnectionException(Connection.class.getName(), "options", "Exception caught while creating http options, URL: " + url + ", " + exception.getMessage());
		}

		
		/*
		 * Add Query Parameters
		 */
		HttpParams httpParams = new BasicHttpParams();

		Iterator<String> parameters = queryParameters.keySet().iterator();
		while(parameters.hasNext()) {
			
			String parameter = parameters.next();
			String parameterValue = queryParameters.get(parameter);
			
			httpParams.setParameter(parameter, parameterValue);
		}
		
		
		httpOptions.setParams(httpParams);
		
		
		/*
		 * Add Header Parameters
		 */
		Iterator<String> headers = headerParameters.keySet().iterator();
		while(headers.hasNext()) {
			
			String header = headers.next();
			String headerValue = headerParameters.get(header);
			
	        httpOptions.setHeader(header, headerValue);
		}
		

		
		/* Authenticate */
		sign(httpOptions);

		
        /* execute */
        BasicHttpResponse httpResponse = null;
        
        try {
        	httpResponse = (BasicHttpResponse) httpClient.execute(httpOptions);        	 
        } catch(Exception exception) {
        	Log.loge(Connection.class.getName(), "options", "Exception caught while executing request, " + exception.getMessage());
        	throw new ConnectionException(Connection.class.getName(), "options", "Exception caught while executing request, " + exception.getMessage());
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
        	Log.loge(Connection.class.getName(), "options", "Exception caught while getting input stream, " + exception.getMessage());
        	throw new ConnectionException(Connection.class.getName(), "options", "Exception caught while getting input stream, " + exception.getMessage());
        }

        
        return new ConnectionResponse(responseCode, inputStream);
	}

	public ConnectionResponse connect(final ConnectionRequest connectionRequest) throws ConnectionException {
		
		if(connectionRequest == null) {
			Log.loge(Connection.class.getName(), "connect", "Invalid Connection Request.");
			throw new ConnectionException(Connection.class.getName(), "connect", "Invalid Connection Request.");
		}


		return null;
	}

	public ConnectionResponse patch(final ConnectionRequest connectionRequest) throws ConnectionException {
		
		if(connectionRequest == null) {
			Log.loge(Connection.class.getName(), "patch", "Invalid Connection Request.");
			throw new ConnectionException(Connection.class.getName(), "patch", "Invalid Connection Request.");
		}

		return null;
	}

	
	private void sign(final HttpRequestBase httpRequestBase) throws ConnectionException {

		CredentialManager credentialManager = CredentialManager.getInstance();
		Credential credential = credentialManager.getActiveAccount();
		
		AuthenticationFactory authenticationFactory = AuthenticationFactory.getInstance();
		IAuthenticate authenticate = authenticationFactory.getAuthenticate(credential);

		if(authenticate == null) {
			return;
		}
		
		
		try {
			authenticate.doSignature(httpRequestBase);
		} catch(AuthenticationException authenticationException) {
			Log.loge(Connection.class.getName(), "sign", "Authentication Exception caught while signing http request, " + authenticationException.getMessage());
			throw new ConnectionException(Connection.class.getName(), "sign", "Authentication Exception caught while signing http request, " + authenticationException.getMessage());
		}
	}
}
