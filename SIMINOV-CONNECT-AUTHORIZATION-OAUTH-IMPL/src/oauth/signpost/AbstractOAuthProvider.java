package oauth.signpost;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.exception.OAuthNotAuthorizedException;
import oauth.signpost.http.HttpParameters;
import oauth.signpost.http.HttpRequest;
import oauth.signpost.http.HttpResponse;

public abstract class AbstractOAuthProvider
  implements OAuthProvider
{
  private static final long serialVersionUID = 1L;
  private String requestTokenEndpointUrl;
  private String accessTokenEndpointUrl;
  private String authorizationWebsiteUrl;
  private HttpParameters responseParameters;
  private Map<String, String> defaultHeaders;
  private boolean isOAuth10a;
  private transient OAuthProviderListener listener;
  
  public AbstractOAuthProvider(String requestTokenEndpointUrl, String accessTokenEndpointUrl, String authorizationWebsiteUrl)
  {
    this.requestTokenEndpointUrl = requestTokenEndpointUrl;
    this.accessTokenEndpointUrl = accessTokenEndpointUrl;
    this.authorizationWebsiteUrl = authorizationWebsiteUrl;
    this.responseParameters = new HttpParameters();
    this.defaultHeaders = new HashMap();
  }
  
  public String retrieveRequestToken(OAuthConsumer consumer, String callbackUrl)
    throws OAuthMessageSignerException, OAuthNotAuthorizedException, OAuthExpectationFailedException, OAuthCommunicationException
  {
    consumer.setTokenWithSecret(null, null);
    


    retrieveToken(consumer, this.requestTokenEndpointUrl, new String[] { "oauth_callback", callbackUrl });
    
    String callbackConfirmed = this.responseParameters.getFirst("oauth_callback_confirmed");
    this.responseParameters.remove("oauth_callback_confirmed");
    this.isOAuth10a = Boolean.TRUE.toString().equals(callbackConfirmed);
    if (this.isOAuth10a) {
      return OAuth.addQueryParameters(this.authorizationWebsiteUrl, new String[] { "oauth_token", 
        consumer.getToken() });
    }
    return OAuth.addQueryParameters(this.authorizationWebsiteUrl, new String[] { "oauth_token", 
      consumer.getToken(), "oauth_callback", callbackUrl });
  }
  
  public void retrieveAccessToken(OAuthConsumer consumer, String oauthVerifier)
    throws OAuthMessageSignerException, OAuthNotAuthorizedException, OAuthExpectationFailedException, OAuthCommunicationException
  {
    if ((consumer.getToken() == null) || (consumer.getTokenSecret() == null)) {
      throw new OAuthExpectationFailedException(
        "Authorized request token or token secret not set. Did you retrieve an authorized request token before?");
    }
    if ((this.isOAuth10a) && (oauthVerifier != null)) {
      retrieveToken(consumer, this.accessTokenEndpointUrl, new String[] { "oauth_verifier", oauthVerifier });
    } else {
      retrieveToken(consumer, this.accessTokenEndpointUrl, new String[0]);
    }
  }
  
  protected void retrieveToken(OAuthConsumer consumer, String endpointUrl, String... additionalParameters)
    throws OAuthMessageSignerException, OAuthCommunicationException, OAuthNotAuthorizedException, OAuthExpectationFailedException
  {
    Map<String, String> defaultHeaders = getRequestHeaders();
    if ((consumer.getConsumerKey() == null) || (consumer.getConsumerSecret() == null)) {
      throw new OAuthExpectationFailedException("Consumer key or secret not set");
    }
    HttpRequest request = null;
    HttpResponse response = null;
    try
    {
      request = createRequest(endpointUrl);
      for (String header : defaultHeaders.keySet()) {
        request.setHeader(header, (String)defaultHeaders.get(header));
      }
      if (additionalParameters != null)
      {
        HttpParameters httpParams = new HttpParameters();
        httpParams.putAll(additionalParameters, true);
        consumer.setAdditionalParameters(httpParams);
      }
      if (this.listener != null) {
        this.listener.prepareRequest(request);
      }
      consumer.sign(request);
      if (this.listener != null) {
        this.listener.prepareSubmission(request);
      }
      response = sendRequest(request);
      int statusCode = response.getStatusCode();
      
      boolean requestHandled = false;
      if (this.listener != null) {
        requestHandled = this.listener.onResponseReceived(request, response);
      }
      if (requestHandled) {
        return;
      }
      if (statusCode >= 300) {
        handleUnexpectedResponse(statusCode, response);
      }
      HttpParameters responseParams = OAuth.decodeForm(response.getContent());
      
      String token = responseParams.getFirst("oauth_token");
      String secret = responseParams.getFirst("oauth_token_secret");
      responseParams.remove("oauth_token");
      responseParams.remove("oauth_token_secret");
      
      setResponseParameters(responseParams);
      if ((token == null) || (secret == null)) {
        throw new OAuthExpectationFailedException(
          "Request token or token secret not set in server reply. The service provider you use is probably buggy.");
      }
      consumer.setTokenWithSecret(token, secret);
    }
    catch (OAuthNotAuthorizedException e)
    {
      throw e;
    }
    catch (OAuthExpectationFailedException e)
    {
      throw e;
    }
    catch (Exception e)
    {
      throw new OAuthCommunicationException(e);
    }
    finally
    {
      try
      {
        closeConnection(request, response);
      }
      catch (Exception e)
      {
        throw new OAuthCommunicationException(e);
      }
    }
    try
    {
      closeConnection(request, response);
    }
    catch (Exception e)
    {
      throw new OAuthCommunicationException(e);
    }
  }
  
  protected void handleUnexpectedResponse(int statusCode, HttpResponse response)
    throws Exception
  {
    if (response == null) {
      return;
    }
    BufferedReader reader = new BufferedReader(new InputStreamReader(response.getContent()));
    StringBuilder responseBody = new StringBuilder();
    
    String line = reader.readLine();
    while (line != null)
    {
      responseBody.append(line);
      line = reader.readLine();
    }
    switch (statusCode)
    {
    case 401: 
      throw new OAuthNotAuthorizedException(responseBody.toString());
    }
    throw new OAuthCommunicationException("Service provider responded in error: " + 
      statusCode + " (" + response.getReasonPhrase() + ")", responseBody.toString());
  }
  
  protected abstract HttpRequest createRequest(String paramString)
    throws Exception;
  
  protected abstract HttpResponse sendRequest(HttpRequest paramHttpRequest)
    throws Exception;
  
  protected void closeConnection(HttpRequest request, HttpResponse response)
    throws Exception
  {}
  
  public HttpParameters getResponseParameters()
  {
    return this.responseParameters;
  }
  
  protected String getResponseParameter(String key)
  {
    return this.responseParameters.getFirst(key);
  }
  
  public void setResponseParameters(HttpParameters parameters)
  {
    this.responseParameters = parameters;
  }
  
  public void setOAuth10a(boolean isOAuth10aProvider)
  {
    this.isOAuth10a = isOAuth10aProvider;
  }
  
  public boolean isOAuth10a()
  {
    return this.isOAuth10a;
  }
  
  public String getRequestTokenEndpointUrl()
  {
    return this.requestTokenEndpointUrl;
  }
  
  public String getAccessTokenEndpointUrl()
  {
    return this.accessTokenEndpointUrl;
  }
  
  public String getAuthorizationWebsiteUrl()
  {
    return this.authorizationWebsiteUrl;
  }
  
  public void setRequestHeader(String header, String value)
  {
    this.defaultHeaders.put(header, value);
  }
  
  public Map<String, String> getRequestHeaders()
  {
    return this.defaultHeaders;
  }
  
  public void setListener(OAuthProviderListener listener)
  {
    this.listener = listener;
  }
  
  public void removeListener(OAuthProviderListener listener)
  {
    this.listener = null;
  }
}
