package siminov.connect.connection.design;

import siminov.connect.exception.ConnectionException;


public interface IConnection {

	public IConnectionResponse get(final IConnectionRequest connectionRequest) throws ConnectionException;

	
	public IConnectionResponse head(final IConnectionRequest connectionRequest) throws ConnectionException;
	
	
	public IConnectionResponse post(final IConnectionRequest connectionRequest) throws ConnectionException;
	
	
	public IConnectionResponse put(final IConnectionRequest connectionRequest) throws ConnectionException;

	
	public IConnectionResponse delete(final IConnectionRequest connectionRequest) throws ConnectionException;

	
	public IConnectionResponse trace(final IConnectionRequest connectionRequest) throws ConnectionException;

	
	public IConnectionResponse options(final IConnectionRequest connectionRequest) throws ConnectionException;

	
	public IConnectionResponse connect(final IConnectionRequest connectionRequest) throws ConnectionException;

	
	public IConnectionResponse patch(final IConnectionRequest connectionRequest) throws ConnectionException;
}
