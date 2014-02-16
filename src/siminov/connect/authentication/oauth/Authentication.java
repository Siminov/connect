package siminov.connect.authentication.oauth;

import java.util.Iterator;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;

import org.apache.http.client.methods.HttpRequestBase;

import siminov.connect.authentication.Credential;
import siminov.connect.authentication.CredentialManager;
import siminov.connect.authentication.design.IAuthenticate;
import siminov.connect.authentication.design.IAuthenticationEvents;
import siminov.connect.exception.AuthenticationException;
import siminov.connect.exception.ServiceException;
import siminov.connect.model.AuthenticationDescriptor;
import siminov.connect.model.ConnectDescriptor;
import siminov.connect.resource.Resources;
import siminov.connect.service.resource.ResourceUtils;
import siminov.orm.exception.SiminovCriticalException;
import siminov.orm.log.Log;
import android.content.Context;
import android.content.Intent;

public class Authentication implements IAuthenticate {

	private Resources resources = Resources.getInstance();

	private Credential credential = null;
	
	public Authentication(Credential credential) {
		this.credential = credential;
	}
	
	public void doAuthorization() throws AuthenticationException {
		
		IAuthenticationEvents authenticationEvents = resources.getAuthenticationEventHandler();
		if(authenticationEvents != null) {
			authenticationEvents.onAuthenticationStart(credential);
		}
		
		
		siminov.orm.resource.Resources ormResources = siminov.orm.resource.Resources.getInstance();
		Resources connectResources = Resources.getInstance();

		Context applicationContext = ormResources.getApplicationContext();
		
		ConnectDescriptor connectDescriptor = connectResources.getConnectDescriptor();
		AuthenticationDescriptor authenticationDescriptor = connectDescriptor.getAuthenticationDescriptor();

		
		String consumerKey = authenticationDescriptor.getProperty(OauthConstants.CONSUMER_KEY);
		String consumerSecret = authenticationDescriptor.getProperty(OauthConstants.CONSUMER_SECRET);
		String requestTokenUrl = authenticationDescriptor.getProperty(OauthConstants.REQUEST_TOKEN_URL);
		String accessTokenUrl = authenticationDescriptor.getProperty(OauthConstants.ACCESS_TOKEN_URL);
		String authorizeUrl = authenticationDescriptor.getProperty(OauthConstants.AUTHORIZE_URL);
		String callbackUrl = authenticationDescriptor.getProperty(OauthConstants.CALLBACK_URL);
		
		if(consumerKey == null || consumerKey.length() <= 0) {
			Log.loge(Authentication.class.getName(), "Constructor", "Invalid Consumer Key.");
			throw new AuthenticationException(Authentication.class.getName(), "Constructor", "Invalid Consumer Key.");
		} else if(consumerSecret == null || consumerSecret.length() <= 0) {
			Log.loge(Authentication.class.getName(), "Constructor", "Invalid Consumer Secret.");
			throw new AuthenticationException(Authentication.class.getName(), "Constructor", "Invalid Consumer Secret.");
		} else if(requestTokenUrl == null || requestTokenUrl.length() <= 0) {
			Log.loge(Authentication.class.getName(), "Constructor", "Invalid Request Token Url.");
			throw new AuthenticationException(Authentication.class.getName(), "Constructor", "Invalid Request Token Url.");
		} else if(accessTokenUrl == null || accessTokenUrl.length() <= 0) {
			Log.loge(Authentication.class.getName(), "Constructor", "Invalid Access Token Url.");
			throw new AuthenticationException(Authentication.class.getName(), "Constructor", "Invalid Access Token Url.");
		} else if(callbackUrl == null || callbackUrl.length() <= 0) {
			Log.loge(Authentication.class.getName(), "Constructor", "Invalid Callback Url.");
			throw new AuthenticationException(Authentication.class.getName(), "Constructor", "Invalid Callback Url.");
		}
		
		
		Iterator<String> inlineResources = credential.getInlineResources();
		while(inlineResources.hasNext()) {
			String inlineResourceKey = inlineResources.next();
			String inlineResourceValue = credential.getInlineResource(inlineResourceKey);
			
			authenticationDescriptor.addProperty(inlineResourceKey, inlineResourceValue);
		}
		
		
		try {
			consumerKey = ResourceUtils.resolve(consumerKey, authenticationDescriptor);
			consumerSecret = ResourceUtils.resolve(consumerSecret, authenticationDescriptor);
			requestTokenUrl = ResourceUtils.resolve(requestTokenUrl, authenticationDescriptor);
			accessTokenUrl = ResourceUtils.resolve(accessTokenUrl, authenticationDescriptor);
			authorizeUrl = ResourceUtils.resolve(authorizeUrl, authenticationDescriptor);
			callbackUrl = ResourceUtils.resolve(callbackUrl, authenticationDescriptor);
		} catch(ServiceException se) {
			Log.loge(Authentication.class.getName(), "Constructor", "ServiceException caught while resolving inline values, " + se.getMessage());
			throw new SiminovCriticalException(Authentication.class.getName(), "Constructor", se.getMessage());
		}
		
		
		Intent intent = new Intent(applicationContext, OauthAuthenticationActivity.class);
		intent.putExtra(OauthConstants.CREDENTIAL, credential);
		intent.putExtra(OauthConstants.CONSUMER_KEY, consumerKey);
		intent.putExtra(OauthConstants.CONSUMER_SECRET, consumerSecret);
		intent.putExtra(OauthConstants.REQUEST_TOKEN_URL, requestTokenUrl);
		intent.putExtra(OauthConstants.ACCESS_TOKEN_URL, accessTokenUrl);
		intent.putExtra(OauthConstants.AUTHORIZE_URL, authorizeUrl);
		intent.putExtra(OauthConstants.CALLBACK_URL, callbackUrl);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		
		
		applicationContext.startActivity(intent);
	}

	public void doSignature(final HttpRequestBase httpRequestBase) throws AuthenticationException {
		
    	if(httpRequestBase == null) {
    		Log.loge(Authentication.class.getName(), "doSignature", "Invalid HttpRequestBase.");
    		throw new AuthenticationException(Authentication.class.getName(), "doSignature", "Invalid HttpRequestBase.");
    	}
    	
		Resources connectResources = Resources.getInstance();
		ConnectDescriptor connectDescriptor = connectResources.getConnectDescriptor();
		if(!connectDescriptor.containAuthenticationDescriptor())	 {
			return;
		}
		
		
		AuthenticationDescriptor authenticationDescriptor = connectDescriptor.getAuthenticationDescriptor();
    	CredentialManager credentialManager = CredentialManager.getInstance();
    	
    	boolean anyActiveUser = credentialManager.isAnyActiveAccount();
    	if(!anyActiveUser) {
    		Log.loge(Authentication.class.getName(), "doSignature", "No Active User Found.");
    		return;
    	}
    	
    	
    	Credential credential = credentialManager.getActiveAccount();
    	if(credential == null) {
    		Log.loge(Authentication.class.getName(), "doSignature", "Invalid Credential.");
    		throw new AuthenticationException(Authentication.class.getName(), "doSignature", "Invalid Credential.");
    	}
    	
		
		String consumerKey = authenticationDescriptor.getProperty(OauthConstants.CONSUMER_KEY);
		String consumerSecret = authenticationDescriptor.getProperty(OauthConstants.CONSUMER_SECRET);
    	if(consumerKey == null || consumerKey.length() <= 0) {
    		Log.loge(Authentication.class.getName(), "doSignature", "Invalid ConsumerKey.");
    		throw new AuthenticationException(Authentication.class.getName(), "doSignature", "Invalid ConsumerKey.");
    	} else if(consumerSecret == null || consumerSecret.length() <= 0) {
    		Log.loge(Authentication.class.getName(), "doSignature", "Invalid ConsumerSecret.");
    		throw new AuthenticationException(Authentication.class.getName(), "doSignature", "Invalid ConsumerSecret.");
    	}
    	
    	
    	OAuthConsumer consumer = new CommonsHttpOAuthConsumer(consumerKey, consumerSecret);
    	consumer.setTokenWithSecret(credential.getToken().substring(0, credential.getToken().indexOf(":")), credential.getToken().substring(credential.getToken().indexOf(":") + 1, credential.getToken().length()));
    	
    	try {
    		consumer.sign(httpRequestBase);    		
    	} catch(Exception exception) {
    		Log.loge(Authentication.class.getName(), "doSignature", "Exception caught while signing request url, " + exception.getMessage());
    		throw new AuthenticationException(Authentication.class.getName(), "doSignature", exception.getMessage());
    	}
	}
}
