package siminov.connect.connection;

import siminov.orm.exception.SiminovException;

public interface IConnection {

	public ConnectionResponse get(final ConnectionRequest connectionRequest) throws SiminovException;
	
	public ConnectionResponse head(final ConnectionRequest connectionRequest) throws SiminovException;
	
	public ConnectionResponse post(final ConnectionRequest connectionRequest) throws SiminovException;
	
	public ConnectionResponse put(final ConnectionRequest connectionRequest) throws SiminovException;

	public ConnectionResponse delete(final ConnectionRequest connectionRequest) throws SiminovException;

	public ConnectionResponse trace(final ConnectionRequest connectionRequest) throws SiminovException;

	public ConnectionResponse options(final ConnectionRequest connectionRequest) throws SiminovException;

	public ConnectionResponse connect(final ConnectionRequest connectionRequest) throws SiminovException;

	public ConnectionResponse patch(final ConnectionRequest connectionRequest) throws SiminovException;

}
