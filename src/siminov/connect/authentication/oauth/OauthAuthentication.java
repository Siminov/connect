package siminov.connect.authentication.oauth;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;

import org.apache.http.client.methods.HttpRequestBase;

import siminov.connect.authentication.Credential;
import siminov.connect.authentication.CredentialManager;
import siminov.connect.authentication.IAuthenticate;
import siminov.connect.model.AuthenticationDescriptor;
import siminov.connect.model.ConnectDescriptor;
import siminov.connect.resource.Resources;
import siminov.orm.exception.SiminovCriticalException;
import siminov.orm.exception.SiminovException;
import siminov.orm.log.Log;
import siminov.orm.utils.ResourceUtils;
import android.content.Context;
import android.content.Intent;

public abstract class OauthAuthentication implements IAuthenticate {

	public void doAuthorization(final Credential credential) throws SiminovException {
		
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
			Log.loge(OauthAuthentication.class.getName(), "Constructor", "Invalid Consumer Key.");
			throw new SiminovException(OauthAuthentication.class.getName(), "Constructor", "Invalid Consumer Key.");
		} else if(consumerSecret == null || consumerSecret.length() <= 0) {
			Log.loge(OauthAuthentication.class.getName(), "Constructor", "Invalid Consumer Secret.");
			throw new SiminovException(OauthAuthentication.class.getName(), "Constructor", "Invalid Consumer Secret.");
		} else if(requestTokenUrl == null || requestTokenUrl.length() <= 0) {
			Log.loge(OauthAuthentication.class.getName(), "Constructor", "Invalid Request Token Url.");
			throw new SiminovException(OauthAuthentication.class.getName(), "Constructor", "Invalid Request Token Url.");
		} else if(accessTokenUrl == null || accessTokenUrl.length() <= 0) {
			Log.loge(OauthAuthentication.class.getName(), "Constructor", "Invalid Access Token Url.");
			throw new SiminovException(OauthAuthentication.class.getName(), "Constructor", "Invalid Access Token Url.");
		} else if(callbackUrl == null || callbackUrl.length() <= 0) {
			Log.loge(OauthAuthentication.class.getName(), "Constructor", "Invalid Callback Url.");
			throw new SiminovException(OauthAuthentication.class.getName(), "Constructor", "Invalid Callback Url.");
		}
		
		
		try {
			consumerKey = ResourceUtils.resolve(OauthConstants.CONSUMER_KEY, consumerKey, authenticationDescriptor);
			consumerSecret = ResourceUtils.resolve(OauthConstants.CONSUMER_SECRET, consumerSecret, authenticationDescriptor);
			requestTokenUrl = ResourceUtils.resolve(OauthConstants.REQUEST_TOKEN_URL, requestTokenUrl, authenticationDescriptor);
			accessTokenUrl = ResourceUtils.resolve(OauthConstants.ACCESS_TOKEN_URL, accessTokenUrl, authenticationDescriptor);
			callbackUrl = ResourceUtils.resolve(OauthConstants.CALLBACK_URL, requestTokenUrl, authenticationDescriptor);
		} catch(SiminovException se) {
			Log.loge(OauthAuthentication.class.getName(), "Constructor", "SiminovException caught while resolving inline values, " + se.getMessage());
			throw new SiminovCriticalException(OauthAuthentication.class.getName(), "Constructor", se.getMessage());
		}

		
		
		Intent intent = new Intent(applicationContext, OauthAuthenticationActivity.class);
		intent.putExtra(OauthConstants.CREDENTIAL, credential);
		intent.putExtra(OauthConstants.CONSUMER_KEY, consumerKey);
		intent.putExtra(OauthConstants.CONSUMER_SECRET, consumerSecret);
		intent.putExtra(OauthConstants.REQUEST_TOKEN_URL, requestTokenUrl);
		intent.putExtra(OauthConstants.ACCESS_TOKEN_URL, accessTokenUrl);
		intent.putExtra(OauthConstants.AUTHORIZE_URL, authorizeUrl);
		intent.putExtra(OauthConstants.CALLBACK_URL, callbackUrl);
		
		applicationContext.startActivity(intent);
	}

	public void doSignature(final HttpRequestBase httpRequestBase) throws SiminovException {
		
    	if(httpRequestBase == null) {
    		Log.loge(OauthAuthentication.class.getName(), "doSignature", "Invalid HttpRequestBase.");
    		throw new SiminovException(OauthAuthentication.class.getName(), "doSignature", "Invalid HttpRequestBase.");
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
    		Log.loge(OauthAuthentication.class.getName(), "doSignature", "No Active User Found.");
    		return;
    	}
    	
    	
    	Credential credential = credentialManager.getActiveAccount();
    	if(credential == null) {
    		Log.loge(OauthAuthentication.class.getName(), "doSignature", "Invalid Credential.");
    		throw new SiminovException(OauthAuthentication.class.getName(), "doSignature", "Invalid Credential.");
    	}
    	
		
		String consumerKey = authenticationDescriptor.getProperty(OauthConstants.CONSUMER_KEY);
		String consumerSecret = authenticationDescriptor.getProperty(OauthConstants.CONSUMER_SECRET);
    	if(consumerKey == null || consumerKey.length() <= 0) {
    		Log.loge(OauthAuthentication.class.getName(), "doSignature", "Inavlid ConsumerKey.");
    		throw new SiminovException(OauthAuthentication.class.getName(), "doSignature", "Inavlid ConsumerKey.");
    	} else if(consumerSecret == null || consumerSecret.length() <= 0) {
    		Log.loge(OauthAuthentication.class.getName(), "doSignature", "Inavlid ConsumerSecret.");
    		throw new SiminovException(OauthAuthentication.class.getName(), "doSignature", "Inavlid ConsumerSecret.");
    	}
    	
    	
    	OAuthConsumer consumer = new CommonsHttpOAuthConsumer(consumerKey, consumerSecret);
    	consumer.setTokenWithSecret(credential.getToken().substring(0, credential.getToken().indexOf(":")), credential.getToken().substring(credential.getToken().indexOf(":") + 1, credential.getToken().length()));
    	
    	try {
    		consumer.sign(httpRequestBase);    		
    	} catch(Exception exception) {
    		Log.loge(OauthAuthentication.class.getName(), "doSignature", "Exception caught while signing request url, " + exception.getMessage());
    		throw new SiminovException(OauthAuthentication.class.getName(), "doSignature", exception.getMessage());
    	}
	}
}
