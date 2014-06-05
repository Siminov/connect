package siminov.connect.authorization;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import siminov.connect.authorization.design.ICredential;
import siminov.connect.authorization.design.ICredentialManager;
import siminov.connect.model.AuthorizationDescriptor;
import siminov.connect.resource.Resources;
import siminov.orm.exception.SiminovCriticalException;
import siminov.orm.exception.SiminovException;
import siminov.orm.log.Log;
import siminov.orm.utils.ClassUtils;
import siminov.orm.utils.EmptyIterator;

public class CredentialManager implements ICredentialManager {

	private static ICredentialManager credentialManager = null;
	
	private ICredential activeCredential = null;
	private Resources resources = Resources.getInstance();
	
	private CredentialManager() {
		
	}
	
	public static ICredentialManager getInstance() {
		
		if(credentialManager == null) {
			credentialManager = new CredentialManager();
		}
		
		return credentialManager;
	}
	
	
	public boolean isAnyActiveCredential() {

		AuthorizationDescriptor authorizationDescriptor = resources.getAuthenticatorDescription();
		ICredential credential = (ICredential) ClassUtils.createClassInstance(authorizationDescriptor.getCredential());
		if(credential == null) {
			Log.error(CredentialManager.class.getName(), "isAnyActiveCredential", "NO USER DEFINED CREDENTIAL.");
			throw new SiminovCriticalException(CredentialManager.class.getName(), "isAnyActiveCredential", "NO USER DEFINED CREDENTIAL.");
		}
		
		
		int activeAccountCount = 0;

		try {
			activeAccountCount = (Integer) credential.count().where(Credential.ACTIVE).equalTo(true).execute();
		} catch(SiminovException se) {
			Log.error(CredentialManager.class.getName(), "isAnyActiveCredential", "SiminovException caught while getting active account count, " + se.getMessage());
			throw new SiminovCriticalException(CredentialManager.class.getName(), "isAnyActiveCredential", "SiminovException caught while getting active account count, " + se.getMessage());
		}


		if(activeAccountCount <= 0) {
			return false;
		} else {
			return true;
		}
	}

	public ICredential getActiveCredential() {

		AuthorizationDescriptor authorizationDescriptor = resources.getAuthenticatorDescription();
		ICredential credential = (ICredential) ClassUtils.createClassInstance(authorizationDescriptor.getCredential());
		if(credential == null) {
			Log.error(CredentialManager.class.getName(), "isAnyActiveCredential", "NO USER DEFINED CREDENTIAL.");
			throw new SiminovCriticalException(CredentialManager.class.getName(), "isAnyActiveCredential", "NO USER DEFINED CREDENTIAL.");
		}

		
		if(activeCredential != null) {
			return activeCredential;
		}
		
		
		Credential[] credentials = null;
		try {
			credentials = (Credential[]) credential.select().
					where(Credential.ACTIVE).equalTo(true).
					execute();
		} catch(SiminovException se) {
			Log.error(CredentialManager.class.getName(), "getActiveCredential", "SiminovException caught while getting active account, " + se.getMessage());
			throw new SiminovCriticalException(CredentialManager.class.getName(), "getActiveCredential", "SiminovException caught while getting active account, " + se.getMessage());
		}

		if(credentials == null || credentials.length <= 0) {
			Log.error(CredentialManager.class.getName(), "getActiveCredential", "No Account Found.");
			return null;
		}


		if(credentials.length > 1) {
			Log.error(CredentialManager.class.getName(), "getActiveCredential", "More Then One Active Account Found.");
			throw new SiminovCriticalException(CredentialManager.class.getName(), "getActiveCredential", "More Then One Active Account Found.");
		}


		activeCredential = credentials[0];
		return activeCredential;
	}

	public Iterator<ICredential> getCredentials() {

		AuthorizationDescriptor authorizationDescriptor = resources.getAuthenticatorDescription();
		ICredential credential = (ICredential) ClassUtils.createClassInstance(authorizationDescriptor.getCredential());
		if(credential == null) {
			Log.error(CredentialManager.class.getName(), "isAnyActiveCredential", "NO USER DEFINED CREDENTIAL.");
			throw new SiminovCriticalException(CredentialManager.class.getName(), "isAnyActiveCredential", "NO USER DEFINED CREDENTIAL.");
		}

		
		ICredential[] credentials = null;
		try {
			credentials = (ICredential[]) credential.select().
					where(Credential.ACTIVE).equalTo(true).
					execute();
		} catch(SiminovException se) {
			Log.error(CredentialManager.class.getName(), "getCredentials", "SiminovException caught while getting active account, " + se.getMessage());
			throw new SiminovCriticalException(CredentialManager.class.getName(), "getCredentials", "SiminovException caught while getting active account, " + se.getMessage());
		}

		if(credentials == null || credentials.length <= 0) {
			Log.debug(CredentialManager.class.getName(), "getCredentials", "No Account Found.");
			return new EmptyIterator<ICredential>();
		}


		Collection<ICredential> accounts = new ArrayList<ICredential>();
		for(int i = 0;i < credentials.length;i++) {
			accounts.add(credentials[i]);
		}

		return accounts.iterator();
	}

	public void setActiveCredential(ICredential credential) {
		
	}
}
