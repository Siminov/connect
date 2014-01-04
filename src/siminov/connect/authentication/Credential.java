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

import siminov.orm.database.Database;

//@Table(tableName=Credential.TABLE_NAME)
public class Credential extends Database implements Serializable {

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
}
