package oauth.signpost.signature;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.Set;
import oauth.signpost.OAuth;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.http.HttpParameters;
import oauth.signpost.http.HttpRequest;

public class SignatureBaseString
{
  private HttpRequest request;
  private HttpParameters requestParameters;
  
  public SignatureBaseString(HttpRequest request, HttpParameters requestParameters)
  {
    this.request = request;
    this.requestParameters = requestParameters;
  }
  
  public String generate()
    throws OAuthMessageSignerException
  {
    try
    {
      String normalizedUrl = normalizeRequestUrl();
      String normalizedParams = normalizeRequestParameters();
      
      return this.request.getMethod() + '&' + OAuth.percentEncode(normalizedUrl) + '&' + OAuth.percentEncode(normalizedParams);
    }
    catch (Exception e)
    {
      throw new OAuthMessageSignerException(e);
    }
  }
  
  public String normalizeRequestUrl()
    throws URISyntaxException
  {
    URI uri = new URI(this.request.getRequestUrl());
    String scheme = uri.getScheme().toLowerCase();
    String authority = uri.getAuthority().toLowerCase();
    boolean dropPort = ((scheme.equals("http")) && (uri.getPort() == 80)) || ((scheme.equals("https")) && (uri.getPort() == 443));
    if (dropPort)
    {
      int index = authority.lastIndexOf(":");
      if (index >= 0) {
        authority = authority.substring(0, index);
      }
    }
    String path = uri.getRawPath();
    if ((path == null) || (path.length() <= 0)) {
      path = "/";
    }
    return scheme + "://" + authority + path;
  }
  
  public String normalizeRequestParameters()
    throws IOException
  {
    if (this.requestParameters == null) {
      return "";
    }
    StringBuilder sb = new StringBuilder();
    Iterator<String> iter = this.requestParameters.keySet().iterator();
    for (int i = 0; iter.hasNext(); i++)
    {
      String param = (String)iter.next();
      if ((!"oauth_signature".equals(param)) && (!"realm".equals(param)))
      {
        if (i > 0) {
          sb.append("&");
        }
        sb.append(this.requestParameters.getAsQueryString(param, false));
      }
    }
    return sb.toString();
  }
}
