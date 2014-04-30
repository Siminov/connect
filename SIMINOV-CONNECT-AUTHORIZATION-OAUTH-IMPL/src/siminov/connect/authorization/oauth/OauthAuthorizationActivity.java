package siminov.connect.authorization.oauth;

import oauth.signpost.OAuth;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthProvider;
import siminov.connect.design.authorization.ICredential;
import siminov.connect.events.IAuthenticationEvents;
import siminov.connect.exception.AuthorizationException;
import siminov.connect.resource.Resources;
import siminov.orm.exception.SiminovCriticalException;
import siminov.orm.log.Log;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

public class OauthAuthorizationActivity extends Activity {

	private Resources resources = Resources.getInstance();
	
	private ICredential credential = null;
	
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
		
		credential = (ICredential) intent.getSerializableExtra(OauthConstants.CREDENTIAL);
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
    
        IAuthenticationEvents authenticationEvents = resources.getAuthenticationEventHandler();

        Uri uri = intent.getData();
        if(uri == null) {
        	Log.loge(this.getClass().getName(), "onNewIntent", "Invalid URI Found.");
        
        	if(authenticationEvents != null) {
        		
        		try {
            		authenticationEvents.onAuthenticationTerminate(credential);
        		} catch(AuthorizationException ae) {
        			Log.loge(OauthAuthorizationActivity.class.getName(), "onNewIntent", "AuthenticationException caught while invoking on authentication terminate, " + ae.getMessage());
        		}
        	}
        	
        	finish();
        	return;
        }
        
        String verifier = uri.getQueryParameter(OAuth.OAUTH_VERIFIER);
        if(verifier == null || verifier.length() <= 0) {
        	Log.loge(this.getClass().getName(), "onNewIntent", "Invalid Token Found.");
        	
        	if(authenticationEvents != null) {
        		
        		try {
            		authenticationEvents.onAuthenticationTerminate(credential);
        		} catch(AuthorizationException ae) {
        			Log.loge(OauthAuthorizationActivity.class.getName(), "onNewIntent", "AuthenticationException caught while invoking on authentication terminate, " + ae.getMessage());
        		}
        	}

        	finish();
        	return;
        }


        try {
        	getAccessToken(verifier);
        } catch(AuthorizationException ae) {
        	Log.loge(OauthAuthorizationActivity.class.getName(), "onNewIntent", "AuthenticationException caught while getting access token, " + ae.getMessage());
        	
        	if(authenticationEvents != null) {
        		
        		try {
            		authenticationEvents.onAuthenticationTerminate(credential);
        		} catch(AuthorizationException e) {
        			Log.loge(OauthAuthorizationActivity.class.getName(), "onNewIntent", "AuthenticationException caught while invoking on authentication terminate, " + e.getMessage());
        		}
        	}

        	finish();
        	return;
        }


        if(authenticationEvents != null) {
        	
        	try {
            	authenticationEvents.onAuthenticationFinish(credential);
        	} catch(AuthorizationException ae) {
        		Log.loge(OauthAuthorizationActivity.class.getName(), "onNewIntent", "AuthenticationException caught while calling on authentication finish, " + ae.getMessage());
        	}
        }
        
        
    	finish();
    }


	private String getAuthenticationUrl() {
		
        provider = new CommonsHttpOAuthProvider(requestTokenUrl, accessTokenUrl, authorizeUrl);
        String authUrl = null;
        
        try {
        	authUrl = provider.retrieveRequestToken(consumer, callbackUrl);       
        } catch(Exception exception) {
        	Log.loge(Authorization.class.getName(), "getAuthenticationUrl", "Exception caught while getting request token from goplan, " + exception.getMessage());
        	throw new SiminovCriticalException(Authorization.class.getName(), "getAuthenticationUrl", exception.getMessage());
        }
        
        return authUrl;
	}

	private void getAccessToken(String verifier) throws AuthorizationException {
		
		if(verifier == null || verifier.length() <= 0) {
			Log.loge(OauthAuthorizationActivity.class.getName(), "getAccessToken", "Invalid Verifier Found.");
			throw new SiminovCriticalException(OauthAuthorizationActivity.class.getName(), "getAccessToken", "Invalid Verifier Found.");
		}
		
		 
		try {
			provider.retrieveAccessToken(consumer, verifier);
		} catch(Exception exception) {
			Log.loge(OauthAuthorizationActivity.class.getName(), "getAccessToken", "Exception caught while getting access token from server, " + exception.getMessage());
			throw new AuthorizationException(OauthAuthorizationActivity.class.getName(), "getAccessToken", exception.getMessage());
		}

		
        consumer.setTokenWithSecret(consumer.getToken(), consumer.getTokenSecret());
        credential.setToken(consumer.getToken() + ":" + consumer.getTokenSecret());
	} 
}
