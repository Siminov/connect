package oauth.signpost.basic;

import java.net.HttpURLConnection;
import oauth.signpost.AbstractOAuthConsumer;
import oauth.signpost.http.HttpRequest;

public class DefaultOAuthConsumer
  extends AbstractOAuthConsumer
{
  private static final long serialVersionUID = 1L;
  
  public DefaultOAuthConsumer(String consumerKey, String consumerSecret)
  {
    super(consumerKey, consumerSecret);
  }
  
  protected HttpRequest wrap(Object request)
  {
    if (!(request instanceof HttpURLConnection)) {
      throw new IllegalArgumentException(
        "The default consumer expects requests of type java.net.HttpURLConnection");
    }
    return new HttpURLConnectionRequestAdapter((HttpURLConnection)request);
  }
}
