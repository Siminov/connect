package siminov.connect.authorization.design;

import java.io.Serializable;

import siminov.orm.database.design.IDatabase;

public interface ICredentialResource extends IDatabase, Serializable {

	//Column Names.
	public static final String NAME = "NAME";
	public static final String VALUE = "VALUE";
	
	
	public ICredential getCredential();

	public void setCredential(ICredential credential);

	public String getName();

	public void setName(String name);

	public String getValue();

	public void setValue(String value);
}
