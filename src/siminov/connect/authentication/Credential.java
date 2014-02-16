/**
 * 
 * 						SIMINOV SOFTWARE SOLUTIONS CONFIDENTIAL
 * ____________________________________________________________________
 * 
 *  Copyright [2013] [Siminov Software Solutions|support@siminov.com] 
 *  All Rights Reserved.
 * ____________________________________________________________________
 * NOTICE:  All information contained herein is, and remains the 
 * property of Siminov Software Solutions and its suppliers, if any. 
 * The intellectual and technical concepts contained herein are 
 * proprietary to Siminov Software Solutions and its suppliers and may be 
 * covered by India and Foreign Patents, patents in process, and are 
 * protected by trade secret or copyright law. Dissemination of this 
 * information or reproduction of this material  is strictly forbidden 
 * unless prior written permission is obtained from Siminov Software Solutions.
 * ____________________________________________________________________
 */

package siminov.connect.authentication;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import siminov.connect.service.design.IInlineResource;
import siminov.orm.database.Database;

public class Credential extends Database implements Serializable, IInlineResource {

	//Table Name.
	public static final String TABLE_NAME = "SIMINOV_CONNECT_CREDENTIAL";
	
	
	//Column Names.
	public static final String ACCOUNT_ID = "ACCOUNT_ID";
	public static final String TOKEN = "TOKEN";
	public static final String ACTIVE = "ACTIVE";

	
	//Class Variables.
	private String accountId = null;
	private String token = null;
	private boolean active;
	
	private Map<String, CredentialResource> credentialResources = new HashMap<String, CredentialResource>();
	
	public String getAccountId() {
		return this.accountId;
	}
	
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	
	public String getToken() {
		return this.token;
	}
	
	public void setToken(String token) {
		this.token = token;
	}
	
	public boolean getActive() {
		return this.active;
	}
	
	public void setActive(boolean active) {
		this.active = active;
	}
	
	public Iterator<CredentialResource> getCredentialResources() {
		return this.credentialResources.values().iterator();
	}
	
	public CredentialResource getCredentialResource(String credentialResourceName) {
		return this.credentialResources.get(credentialResourceName);
	}
	
	public void addCredentialResource(CredentialResource credentialResource) {
		this.credentialResources.put(credentialResource.getName(), credentialResource);
	}
	
	public void setCredentialResources(Iterator<CredentialResource> credentialResources) {
		
		while(credentialResources.hasNext()) {
			CredentialResource credentialResource = credentialResources.next();
			this.credentialResources.put(credentialResource.getName(), credentialResource);
		}
	}

	public boolean containCredentialResource(CredentialResource credentialResource) {
		return this.credentialResources.containsKey(credentialResource.getName());
	}
	
	public void removeCredentialResource(CredentialResource credentialResource) {
		this.credentialResources.remove(credentialResource.getName());
	}

	public Iterator<String> getInlineResources() {
		return this.credentialResources.keySet().iterator();
	}

	public String getInlineResource(String name) {
		return this.credentialResources.get(name).getValue();
	}

	public void addInlineResource(String name, String value) {
		
		CredentialResource credentialResource = new CredentialResource();
		credentialResource.setName(name);
		credentialResource.setValue(value);
		
		credentialResource.setCredential(this);
		
		this.credentialResources.put(name, credentialResource);
	}

	public boolean containInlineResource(String name) {
		return this.credentialResources.containsKey(name);
	}
}
