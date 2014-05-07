package oauth.signpost.signature;

import oauth.signpost.OAuth;
import oauth.signpost.http.HttpParameters;
import oauth.signpost.http.HttpRequest;

public class AuthorizationHeaderSigningStrategy
  implements SigningStrategy
{
  private static final long serialVersionUID = 1L;
  
  public String writeSignature(String signature, HttpRequest request, HttpParameters requestParameters)
  {
    StringBuilder sb = new StringBuilder();
    
    sb.append("OAuth ");
    if (requestParameters.containsKey("realm"))
    {
      sb.append(requestParameters.getAsHeaderElement("realm"));
      sb.append(", ");
    }
    if (requestParameters.containsKey("oauth_token"))
    {
      sb.append(requestParameters.getAsHeaderElement("oauth_token"));
      sb.append(", ");
    }
    if (requestParameters.containsKey("oauth_callback"))
    {
      sb.append(requestParameters.getAsHeaderElement("oauth_callback"));
      sb.append(", ");
    }
    if (requestParameters.containsKey("oauth_verifier"))
    {
      sb.append(requestParameters.getAsHeaderElement("oauth_verifier"));
      sb.append(", ");
    }
    sb.append(requestParameters.getAsHeaderElement("oauth_consumer_key"));
    sb.append(", ");
    sb.append(requestParameters.getAsHeaderElement("oauth_version"));
    sb.append(", ");
    sb.append(requestParameters.getAsHeaderElement("oauth_signature_method"));
    sb.append(", ");
    sb.append(requestParameters.getAsHeaderElement("oauth_timestamp"));
    sb.append(", ");
    sb.append(requestParameters.getAsHeaderElement("oauth_nonce"));
    sb.append(", ");
    sb.append(OAuth.toHeaderElement("oauth_signature", signature));
    
    String header = sb.toString();
    request.setHeader("Authorization", header);
    
    return header;
  }
}
