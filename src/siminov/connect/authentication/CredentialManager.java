package siminov.connect.authentication;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import siminov.orm.exception.SiminovCriticalException;
import siminov.orm.exception.SiminovException;
import siminov.orm.log.Log;
import siminov.orm.utils.EmptyIterator;

public class CredentialManager {

	private static CredentialManager credentialManager = null;
	private Credential activeAccount = null;
	
	private CredentialManager() {
		
	}
	
	public static CredentialManager getInstance() {
		
		if(credentialManager == null) {
			credentialManager = new CredentialManager();
		}
		
		return credentialManager;
	}
	
	public boolean isAnyActiveAccount() {
		
		int activeAccountCount = 0;
		
		try {
			activeAccountCount = (Integer) new Credential().count().where(Credential.ACTIVE).equalTo(true).execute();
		} catch(SiminovException se) {
			Log.loge(CredentialManager.class.getName(), "isAnyActiveAccount", "SiminovException caught while getting active account count, " + se.getMessage());
			throw new SiminovCriticalException(CredentialManager.class.getName(), "isAnyActiveAccount", "SiminovException caught while getting active account count, " + se.getMessage());
		}
		
		
		if(activeAccountCount <= 0) {
			return false;
		} else {
			return true;
		}
	}

	public Credential getActiveAccount() {
		
		if(activeAccount != null) {
			return activeAccount;
		}
		
		
		Credential[] credentials = null;
		try {
			credentials = (Credential[]) new Credential().select().
					where(Credential.ACTIVE).equalTo(true).
					fetch();
		} catch(SiminovException se) {
			Log.loge(CredentialManager.class.getName(), "getActiveAccount", "SiminovException caught while getting active account, " + se.getMessage());
			throw new SiminovCriticalException(CredentialManager.class.getName(), "getActiveAccount", "SiminovException caught while getting active account, " + se.getMessage());
		}
		
		if(credentials == null || credentials.length <= 0) {
			Log.loge(CredentialManager.class.getName(), "getActiveAccount", "No Account Found.");
			return null;
		}
		
		
		if(credentials.length > 1) {
			Log.loge(CredentialManager.class.getName(), "getActiveAccount", "More Then One Active Account Found.");
			throw new SiminovCriticalException(CredentialManager.class.getName(), "getActiveAccount", "More Then One Active Account Found.");
		}
		
		
		activeAccount = credentials[0];
		return activeAccount;
	}
	
	public Iterator<Credential> getAccounts() {
		
		Credential[] credentials = null;
		try {
			credentials = (Credential[]) new Credential().select().
					where(Credential.ACTIVE).equalTo(true).
					fetch();
		} catch(SiminovException se) {
			Log.loge(CredentialManager.class.getName(), "getAccounts", "SiminovException caught while getting active account, " + se.getMessage());
			throw new SiminovCriticalException(CredentialManager.class.getName(), "getAccounts", "SiminovException caught while getting active account, " + se.getMessage());
		}
	
		if(credentials == null || credentials.length <= 0) {
			Log.logd(CredentialManager.class.getName(), "getAccounts", "No Account Found.");
			return new EmptyIterator<Credential>();
		}

		
		Collection<Credential> accounts = new ArrayList<Credential>();
		for(int i = 0;i < credentials.length;i++) {
			accounts.add(credentials[i]);
		}
		
		return accounts.iterator();
	}
	
	public void setActiveAccount() {
		
	}
}
