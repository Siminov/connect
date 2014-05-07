package oauth.signpost.signature;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.http.HttpParameters;
import oauth.signpost.http.HttpRequest;
import android.util.Base64;

public abstract class OAuthMessageSigner
  implements Serializable
{
  private static final long serialVersionUID = 4445779788786131202L;
  private String consumerSecret;
  private String tokenSecret;
  
  public OAuthMessageSigner()
  {
  }
  
  public abstract String sign(HttpRequest paramHttpRequest, HttpParameters paramHttpParameters)
    throws OAuthMessageSignerException;
  
  public abstract String getSignatureMethod();
  
  public String getConsumerSecret()
  {
    return this.consumerSecret;
  }
  
  public String getTokenSecret()
  {
    return this.tokenSecret;
  }
  
  public void setConsumerSecret(String consumerSecret)
  {
    this.consumerSecret = consumerSecret;
  }
  
  public void setTokenSecret(String tokenSecret)
  {
    this.tokenSecret = tokenSecret;
  }
  
  protected byte[] decodeBase64(String s)
  {
    return Base64.decode(s.getBytes(), Base64.DEFAULT);
  }
  
  protected String base64Encode(byte[] b)
  {
    return new String(Base64.encode(b, Base64.DEFAULT));
  }
  
  private void readObject(ObjectInputStream stream)
    throws IOException, ClassNotFoundException
  {
    stream.defaultReadObject();
  }
}
