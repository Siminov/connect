package siminov.connect.authorization.oauth;

import java.util.Iterator;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;

import org.apache.http.client.methods.HttpRequestBase;

import siminov.connect.authorization.AuthorizationFactory;
import siminov.connect.design.authorization.IAuthorization;
import siminov.connect.design.authorization.ICredential;
import siminov.connect.design.authorization.ICredentialManager;
import siminov.connect.events.IAuthenticationEvents;
import siminov.connect.exception.AuthorizationException;
import siminov.connect.exception.ServiceException;
import siminov.connect.model.ApplicationDescriptor;
import siminov.connect.model.AuthorizationDescriptor;
import siminov.connect.resource.ResourceUtils;
import siminov.connect.resource.Resources;
import siminov.orm.exception.SiminovCriticalException;
import siminov.orm.log.Log;
import android.content.Context;
import android.content.Intent;

public class Authorization implements IAuthorization {

	private Resources resources = Resources.getInstance();

	private ICredential credential = null;
	
	public Authorization() {
		
		AuthorizationFactory authorizationFactory = AuthorizationFactory.getInstance();
		ICredentialManager credentialManager = authorizationFactory.getAuthorizationProvider();
		if(credentialManager == null) {
			return;
		}
		
		credential = credentialManager.getActiveCredential();
	}
	
	public void doAuthentication() throws AuthorizationException {
		
		IAuthenticationEvents authenticationEvents = resources.getAuthenticationEventHandler();
		if(authenticationEvents != null) {
			authenticationEvents.onAuthenticationStart(credential);
		}
		
		
		siminov.orm.resource.Resources ormResources = siminov.orm.resource.Resources.getInstance();
		Resources connectResources = Resources.getInstance();

		Context applicationContext = ormResources.getApplicationContext();
		
		ApplicationDescriptor applicationDescriptor = connectResources.getApplicationDescriptor();
		AuthorizationDescriptor authorizationDescriptor = applicationDescriptor.getAuthorizationDescriptor();

		
		String consumerKey = authorizationDescriptor.getProperty(OauthConstants.CONSUMER_KEY);
		String consumerSecret = authorizationDescriptor.getProperty(OauthConstants.CONSUMER_SECRET);
		String requestTokenUrl = authorizationDescriptor.getProperty(OauthConstants.REQUEST_TOKEN_URL);
		String accessTokenUrl = authorizationDescriptor.getProperty(OauthConstants.ACCESS_TOKEN_URL);
		String authorizeUrl = authorizationDescriptor.getProperty(OauthConstants.AUTHORIZE_URL);
		String callbackUrl = authorizationDescriptor.getProperty(OauthConstants.CALLBACK_URL);
		
		if(consumerKey == null || consumerKey.length() <= 0) {
			Log.loge(Authorization.class.getName(), "Constructor", "Invalid Consumer Key.");
			throw new AuthorizationException(Authorization.class.getName(), "Constructor", "Invalid Consumer Key.");
		} else if(consumerSecret == null || consumerSecret.length() <= 0) {
			Log.loge(Authorization.class.getName(), "Constructor", "Invalid Consumer Secret.");
			throw new AuthorizationException(Authorization.class.getName(), "Constructor", "Invalid Consumer Secret.");
		} else if(requestTokenUrl == null || requestTokenUrl.length() <= 0) {
			Log.loge(Authorization.class.getName(), "Constructor", "Invalid Request Token Url.");
			throw new AuthorizationException(Authorization.class.getName(), "Constructor", "Invalid Request Token Url.");
		} else if(accessTokenUrl == null || accessTokenUrl.length() <= 0) {
			Log.loge(Authorization.class.getName(), "Constructor", "Invalid Access Token Url.");
			throw new AuthorizationException(Authorization.class.getName(), "Constructor", "Invalid Access Token Url.");
		} else if(callbackUrl == null || callbackUrl.length() <= 0) {
			Log.loge(Authorization.class.getName(), "Constructor", "Invalid Callback Url.");
			throw new AuthorizationException(Authorization.class.getName(), "Constructor", "Invalid Callback Url.");
		}
		
		
		Iterator<String> inlineResources = credential.getInlineResources();
		while(inlineResources.hasNext()) {
			String inlineResourceKey = inlineResources.next();
			String inlineResourceValue = credential.getInlineResource(inlineResourceKey);
			
			authorizationDescriptor.addProperty(inlineResourceKey, inlineResourceValue);
		}
		
		
		try {
			consumerKey = ResourceUtils.resolve(consumerKey, authorizationDescriptor);
			consumerSecret = ResourceUtils.resolve(consumerSecret, authorizationDescriptor);
			requestTokenUrl = ResourceUtils.resolve(requestTokenUrl, authorizationDescriptor);
			accessTokenUrl = ResourceUtils.resolve(accessTokenUrl, authorizationDescriptor);
			authorizeUrl = ResourceUtils.resolve(authorizeUrl, authorizationDescriptor);
			callbackUrl = ResourceUtils.resolve(callbackUrl, authorizationDescriptor);
		} catch(ServiceException se) {
			Log.loge(Authorization.class.getName(), "Constructor", "ServiceException caught while resolving inline values, " + se.getMessage());
			throw new SiminovCriticalException(Authorization.class.getName(), "Constructor", se.getMessage());
		}
		
		
		Intent intent = new Intent(applicationContext, OauthAuthorizationActivity.class);
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

	public void doSignature(final HttpRequestBase httpRequestBase) throws AuthorizationException {
		
    	if(httpRequestBase == null) {
    		Log.loge(Authorization.class.getName(), "doSignature", "Invalid HttpRequestBase.");
    		throw new AuthorizationException(Authorization.class.getName(), "doSignature", "Invalid HttpRequestBase.");
    	}
    	
		Resources connectResources = Resources.getInstance();
		ApplicationDescriptor applicationDescriptor = connectResources.getApplicationDescriptor();
		if(!applicationDescriptor.containAuthenticationDescriptor())	 {
			return;
		}
		
		
		AuthorizationDescriptor authorizationDescriptor = applicationDescriptor.getAuthorizationDescriptor();
    	AuthorizationFactory authorizationFactory = AuthorizationFactory.getInstance();
    	
		ICredentialManager credentialManager = authorizationFactory.getAuthorizationProvider();
		if(credentialManager == null) {
			Log.loge(Authorization.class.getName(), "doSignature", "No Credential Manager Found.");
			return;
		}
		
    	boolean anyActiveUser = credentialManager.isAnyActiveCredential();
    	if(!anyActiveUser) {
    		Log.loge(Authorization.class.getName(), "doSignature", "No Active User Found.");
    		return;
    	}
    	
    	
    	ICredential credential = credentialManager.getActiveCredential();
    	if(credential == null) {
    		Log.loge(Authorization.class.getName(), "doSignature", "Invalid Credential.");
    		throw new AuthorizationException(Authorization.class.getName(), "doSignature", "Invalid Credential.");
    	}
    	
		
		String consumerKey = authorizationDescriptor.getProperty(OauthConstants.CONSUMER_KEY);
		String consumerSecret = authorizationDescriptor.getProperty(OauthConstants.CONSUMER_SECRET);
    	if(consumerKey == null || consumerKey.length() <= 0) {
    		Log.loge(Authorization.class.getName(), "doSignature", "Invalid ConsumerKey.");
    		throw new AuthorizationException(Authorization.class.getName(), "doSignature", "Invalid ConsumerKey.");
    	} else if(consumerSecret == null || consumerSecret.length() <= 0) {
    		Log.loge(Authorization.class.getName(), "doSignature", "Invalid ConsumerSecret.");
    		throw new AuthorizationException(Authorization.class.getName(), "doSignature", "Invalid ConsumerSecret.");
    	}
    	
    	
    	OAuthConsumer consumer = new CommonsHttpOAuthConsumer(consumerKey, consumerSecret);
    	consumer.setTokenWithSecret(credential.getToken().substring(0, credential.getToken().indexOf(":")), credential.getToken().substring(credential.getToken().indexOf(":") + 1, credential.getToken().length()));
    	
    	try {
    		consumer.sign(httpRequestBase);    		
    	} catch(Exception exception) {
    		Log.loge(Authorization.class.getName(), "doSignature", "Exception caught while signing request url, " + exception.getMessage());
    		throw new AuthorizationException(Authorization.class.getName(), "doSignature", exception.getMessage());
    	}
	}
}
