package oauth.signpost;

import java.io.Serializable;
import java.util.Map;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.exception.OAuthNotAuthorizedException;
import oauth.signpost.http.HttpParameters;

public abstract interface OAuthProvider
  extends Serializable
{
  public abstract String retrieveRequestToken(OAuthConsumer paramOAuthConsumer, String paramString)
    throws OAuthMessageSignerException, OAuthNotAuthorizedException, OAuthExpectationFailedException, OAuthCommunicationException;
  
  public abstract void retrieveAccessToken(OAuthConsumer paramOAuthConsumer, String paramString)
    throws OAuthMessageSignerException, OAuthNotAuthorizedException, OAuthExpectationFailedException, OAuthCommunicationException;
  
  public abstract HttpParameters getResponseParameters();
  
  public abstract void setResponseParameters(HttpParameters paramHttpParameters);
  
  @Deprecated
  public abstract void setRequestHeader(String paramString1, String paramString2);
  
  @Deprecated
  public abstract Map<String, String> getRequestHeaders();
  
  public abstract void setOAuth10a(boolean paramBoolean);
  
  public abstract boolean isOAuth10a();
  
  public abstract String getRequestTokenEndpointUrl();
  
  public abstract String getAccessTokenEndpointUrl();
  
  public abstract String getAuthorizationWebsiteUrl();
  
  public abstract void setListener(OAuthProviderListener paramOAuthProviderListener);
  
  public abstract void removeListener(OAuthProviderListener paramOAuthProviderListener);
}
