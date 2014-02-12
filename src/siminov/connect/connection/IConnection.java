package siminov.connect.connection;

import siminov.connect.exception.ConnectionException;


public interface IConnection {

	public ConnectionResponse get(final ConnectionRequest connectionRequest) throws ConnectionException;

	
	public ConnectionResponse head(final ConnectionRequest connectionRequest) throws ConnectionException;
	
	
	public ConnectionResponse post(final ConnectionRequest connectionRequest) throws ConnectionException;
	
	
	public ConnectionResponse put(final ConnectionRequest connectionRequest) throws ConnectionException;

	
	public ConnectionResponse delete(final ConnectionRequest connectionRequest) throws ConnectionException;

	
	public ConnectionResponse trace(final ConnectionRequest connectionRequest) throws ConnectionException;

	
	public ConnectionResponse options(final ConnectionRequest connectionRequest) throws ConnectionException;

	
	public ConnectionResponse connect(final ConnectionRequest connectionRequest) throws ConnectionException;

	
	public ConnectionResponse patch(final ConnectionRequest connectionRequest) throws ConnectionException;
}
