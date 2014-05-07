package oauth.signpost.signature;

import oauth.signpost.OAuth;
import oauth.signpost.http.HttpParameters;
import oauth.signpost.http.HttpRequest;

public class QueryStringSigningStrategy
  implements SigningStrategy
{
  private static final long serialVersionUID = 1L;
  
  public String writeSignature(String signature, HttpRequest request, HttpParameters requestParameters)
  {
    StringBuilder sb = new StringBuilder(
      OAuth.addQueryParameters(request.getRequestUrl(), new String[] {"oauth_signature", signature }));
    if (requestParameters.containsKey("oauth_token"))
    {
      sb.append("&");
      sb.append(requestParameters.getAsQueryString("oauth_token"));
    }
    if (requestParameters.containsKey("oauth_callback"))
    {
      sb.append("&");
      sb.append(requestParameters.getAsQueryString("oauth_callback"));
    }
    if (requestParameters.containsKey("oauth_verifier"))
    {
      sb.append("&");
      sb.append(requestParameters.getAsQueryString("oauth_verifier"));
    }
    sb.append("&");
    sb.append(requestParameters.getAsQueryString("oauth_consumer_key"));
    sb.append("&");
    sb.append(requestParameters.getAsQueryString("oauth_version"));
    sb.append("&");
    sb.append(requestParameters.getAsQueryString("oauth_signature_method"));
    sb.append("&");
    sb.append(requestParameters.getAsQueryString("oauth_timestamp"));
    sb.append("&");
    sb.append(requestParameters.getAsQueryString("oauth_nonce"));
    
    String signedUrl = sb.toString();
    
    request.setRequestUrl(signedUrl);
    
    return signedUrl;
  }
}
