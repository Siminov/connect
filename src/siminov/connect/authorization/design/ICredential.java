package siminov.connect.authorization.design;

import java.io.Serializable;

import siminov.connect.service.design.IInlineResource;
import siminov.orm.database.design.IDatabase;

public interface ICredential extends IDatabase, Serializable, IInlineResource {

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
