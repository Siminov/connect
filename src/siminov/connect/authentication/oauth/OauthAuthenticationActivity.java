package siminov.connect.authentication.oauth;

import oauth.signpost.OAuth;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthProvider;
import siminov.connect.authentication.Credential;
import siminov.orm.exception.SiminovCriticalException;
import siminov.orm.exception.SiminovException;
import siminov.orm.log.Log;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

public class OauthAuthenticationActivity extends Activity {

	private Credential credential = null;
	
	private String consumerKey = null;
	private String consumerSecret = null;
	private String requestTokenUrl = null;
	private String accessTokenUrl = null;
	private String authorizeUrl = null;
	private String callbackUrl = null;
	
	private OAuthConsumer consumer = null;
	private OAuthProvider provider = null;
	
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		doInBackground();
	}

	public void onResume() {
		super.onResume();
	}
	
	protected void doInBackground() {

		Intent intent = getIntent();
		
		credential = intent.getParcelableExtra(OauthConstants.CREDENTIAL);
		consumerKey = intent.getStringExtra(OauthConstants.CONSUMER_KEY);
		consumerSecret = intent.getStringExtra(OauthConstants.CONSUMER_SECRET);
		requestTokenUrl = intent.getStringExtra(OauthConstants.REQUEST_TOKEN_URL);
		accessTokenUrl = intent.getStringExtra(OauthConstants.ACCESS_TOKEN_URL);
		authorizeUrl = intent.getStringExtra(OauthConstants.AUTHORIZE_URL);
		callbackUrl = intent.getStringExtra(OauthConstants.CALLBACK_URL);
		
		consumer = new CommonsHttpOAuthConsumer(consumerKey, consumerSecret);

		
		String url = getAuthenticationUrl();
		startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_FROM_BACKGROUND));
	}
	
	public void onNewIntent(Intent intent) {
        super.onNewIntent(intent); 
    
        Uri uri = intent.getData();
        if(uri == null) {
        	Log.loge(this.getClass().getName(), "onNewIntent", "Invalid URI Found.");
        	
        	finish();
        	
        	return;
        }
        
        String verifier = uri.getQueryParameter(OAuth.OAUTH_VERIFIER);
        if(verifier == null || verifier.length() <= 0) {
        	Log.loge(this.getClass().getName(), "onNewIntent", "Invalid Token Found.");
        	
        	finish();
        	
        	return;
        }


        try {
        	getAccessToken(verifier);
        } catch(SiminovException se) {
        	Log.loge(OauthAuthenticationActivity.class.getName(), "onNewIntent", "SiminovException caught while getting access token, " + se.getMessage());
        	
        	finish();
        	
        	return;
        }

    	finish();
    }


	private String getAuthenticationUrl() {
		
        provider = new CommonsHttpOAuthProvider(requestTokenUrl, accessTokenUrl, authorizeUrl);
        String authUrl = null;
        
        try {
        	authUrl = provider.retrieveRequestToken(consumer, callbackUrl);       
        } catch(Exception exception) {
        	Log.loge(OauthAuthentication.class.getName(), "getAuthenticationUrl", "Exception caught while getting request token from goplan, " + exception.getMessage());
        	throw new SiminovCriticalException(OauthAuthentication.class.getName(), "getAuthenticationUrl", exception.getMessage());
        }
        
        return authUrl;
	}

	private void getAccessToken(String verifier) throws SiminovException {
		
		if(verifier == null || verifier.length() <= 0) {
			Log.loge(OauthAuthenticationActivity.class.getName(), "getAccessToken", "Invalid Verifier Found.");
			throw new SiminovCriticalException(OauthAuthenticationActivity.class.getName(), "getAuthenticationUrl", "Invalid Verifier Found.");
		}
		
		 
		try {
			provider.retrieveAccessToken(consumer, verifier);
		} catch(Exception exception) {
			Log.loge(OauthAuthenticationActivity.class.getName(), "getAccessToken", "Exception caught while getting access token from server, " + exception.getMessage());
			throw new SiminovException(OauthAuthenticationActivity.class.getName(), "getAuthenticationUrl", exception.getMessage());
		}

		
        consumer.setTokenWithSecret(consumer.getToken(), consumer.getTokenSecret());
        credential.setToken(consumer.getToken() + ":" + consumer.getTokenSecret());
        
        credential.saveOrUpdate();
	} 
}
