package oauth.signpost;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
import oauth.signpost.basic.UrlStringRequestAdapter;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.http.HttpParameters;
import oauth.signpost.http.HttpRequest;
import oauth.signpost.signature.AuthorizationHeaderSigningStrategy;
import oauth.signpost.signature.HmacSha1MessageSigner;
import oauth.signpost.signature.OAuthMessageSigner;
import oauth.signpost.signature.QueryStringSigningStrategy;
import oauth.signpost.signature.SigningStrategy;

public abstract class AbstractOAuthConsumer
  implements OAuthConsumer
{
  private static final long serialVersionUID = 1L;
  private String consumerKey;
  private String consumerSecret;
  private String token;
  private OAuthMessageSigner messageSigner;
  private SigningStrategy signingStrategy;
  private HttpParameters additionalParameters;
  private HttpParameters requestParameters;
  private boolean sendEmptyTokens;
  
  public AbstractOAuthConsumer(String consumerKey, String consumerSecret)
  {
    this.consumerKey = consumerKey;
    this.consumerSecret = consumerSecret;
    setMessageSigner(new HmacSha1MessageSigner());
    setSigningStrategy(new AuthorizationHeaderSigningStrategy());
  }
  
  public void setMessageSigner(OAuthMessageSigner messageSigner)
  {
    this.messageSigner = messageSigner;
    messageSigner.setConsumerSecret(this.consumerSecret);
  }
  
  public void setSigningStrategy(SigningStrategy signingStrategy)
  {
    this.signingStrategy = signingStrategy;
  }
  
  public void setAdditionalParameters(HttpParameters additionalParameters)
  {
    this.additionalParameters = additionalParameters;
  }
  
  public HttpRequest sign(HttpRequest request)
    throws OAuthMessageSignerException, OAuthExpectationFailedException, OAuthCommunicationException
  {
    if (this.consumerKey == null) {
      throw new OAuthExpectationFailedException("consumer key not set");
    }
    if (this.consumerSecret == null) {
      throw new OAuthExpectationFailedException("consumer secret not set");
    }
    this.requestParameters = new HttpParameters();
    try
    {
      if (this.additionalParameters != null) {
        this.requestParameters.putAll(this.additionalParameters, false);
      }
      collectHeaderParameters(request, this.requestParameters);
      collectQueryParameters(request, this.requestParameters);
      collectBodyParameters(request, this.requestParameters);
      

      completeOAuthParameters(this.requestParameters);
      
      this.requestParameters.remove("oauth_signature");
    }
    catch (IOException e)
    {
      throw new OAuthCommunicationException(e);
    }
    String signature = this.messageSigner.sign(request, this.requestParameters);
    OAuth.debugOut("signature", signature);
    
    this.signingStrategy.writeSignature(signature, request, this.requestParameters);
    OAuth.debugOut("Auth header", request.getHeader("Authorization"));
    OAuth.debugOut("Request URL", request.getRequestUrl());
    
    return request;
  }
  
  public HttpRequest sign(Object request)
    throws OAuthMessageSignerException, OAuthExpectationFailedException, OAuthCommunicationException
  {
    return sign(wrap(request));
  }
  
  public String sign(String url)
    throws OAuthMessageSignerException, OAuthExpectationFailedException, OAuthCommunicationException
  {
    HttpRequest request = new UrlStringRequestAdapter(url);
    

    SigningStrategy oldStrategy = this.signingStrategy;
    this.signingStrategy = new QueryStringSigningStrategy();
    
    sign(request);
    

    this.signingStrategy = oldStrategy;
    
    return request.getRequestUrl();
  }
  
  protected abstract HttpRequest wrap(Object paramObject);
  
  public void setTokenWithSecret(String token, String tokenSecret)
  {
    this.token = token;
    this.messageSigner.setTokenSecret(tokenSecret);
  }
  
  public String getToken()
  {
    return this.token;
  }
  
  public String getTokenSecret()
  {
    return this.messageSigner.getTokenSecret();
  }
  
  public String getConsumerKey()
  {
    return this.consumerKey;
  }
  
  public String getConsumerSecret()
  {
    return this.consumerSecret;
  }
  
  protected void completeOAuthParameters(HttpParameters out)
  {
    if (!out.containsKey("oauth_consumer_key")) {
      out.put("oauth_consumer_key", this.consumerKey, true);
    }
    if (!out.containsKey("oauth_signature_method")) {
      out.put("oauth_signature_method", this.messageSigner.getSignatureMethod(), true);
    }
    if (!out.containsKey("oauth_timestamp")) {
      out.put("oauth_timestamp", generateTimestamp(), true);
    }
    if (!out.containsKey("oauth_nonce")) {
      out.put("oauth_nonce", generateNonce(), true);
    }
    if (!out.containsKey("oauth_version")) {
      out.put("oauth_version", "1.0", true);
    }
    if ((!out.containsKey("oauth_token")) && (
      ((this.token != null) && (!this.token.equals(""))) || (this.sendEmptyTokens))) {
      out.put("oauth_token", this.token, true);
    }
  }
  
  public HttpParameters getRequestParameters()
  {
    return this.requestParameters;
  }
  
  public void setSendEmptyTokens(boolean enable)
  {
    this.sendEmptyTokens = enable;
  }
  
  protected void collectHeaderParameters(HttpRequest request, HttpParameters out)
  {
    HttpParameters headerParams = OAuth.oauthHeaderToParamsMap(request.getHeader("Authorization"));
    out.putAll(headerParams, false);
  }
  
  protected void collectBodyParameters(HttpRequest request, HttpParameters out)
    throws IOException
  {
    String contentType = request.getContentType();
    if ((contentType != null) && (contentType.startsWith("application/x-www-form-urlencoded")))
    {
      InputStream payload = request.getMessagePayload();
      out.putAll(OAuth.decodeForm(payload), true);
    }
  }
  
  protected void collectQueryParameters(HttpRequest request, HttpParameters out)
  {
    String url = request.getRequestUrl();
    int q = url.indexOf('?');
    if (q >= 0) {
      out.putAll(OAuth.decodeForm(url.substring(q + 1)), true);
    }
  }
  
  protected String generateTimestamp()
  {
    return Long.toString(System.currentTimeMillis() / 1000L);
  }
  
  protected String generateNonce()
  {
    return Long.toString(new Random().nextLong());
  }
}
