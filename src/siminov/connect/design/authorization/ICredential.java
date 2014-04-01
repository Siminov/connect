package siminov.connect.design.authorization;

import java.io.Serializable;

import siminov.connect.design.service.IResource;
import siminov.orm.database.design.IDatabase;

public interface ICredential extends IDatabase, Serializable, IResource {

	//Column Names.
	public static final String ACCOUNT_ID = "ACCOUNT_ID";
	public static final String TOKEN = "TOKEN";
	public static final String ACTIVE = "ACTIVE";

	public String getAccountId();
	
	public void setAccountId(String accountId);
	
	public String getToken();
	
	public void setToken(String token);
	
	public boolean isActive();

	public boolean getActive();
	
	public void setActive(boolean active);
}
