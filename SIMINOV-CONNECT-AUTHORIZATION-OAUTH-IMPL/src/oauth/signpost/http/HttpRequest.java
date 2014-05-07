package oauth.signpost.http;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public abstract interface HttpRequest
{
  public abstract String getMethod();
  
  public abstract String getRequestUrl();
  
  public abstract void setRequestUrl(String paramString);
  
  public abstract void setHeader(String paramString1, String paramString2);
  
  public abstract String getHeader(String paramString);
  
  public abstract Map<String, String> getAllHeaders();
  
  public abstract InputStream getMessagePayload()
    throws IOException;
  
  public abstract String getContentType();
  
  public abstract Object unwrap();
}
