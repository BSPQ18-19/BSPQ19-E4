package es.deusto.computing.sd.util.observer.RMI;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public abstract class RemoteObserver extends UnicastRemoteObject implements IRemoteObserver {

	private static final long serialVersionUID = 1L;

	public RemoteObserver() throws RemoteException {
		super();
	}
	
	/*
	 * INSTRUCTIONS: 
	 * Clients can inherit from this class and they simply need to implement update();
	 * Clients that can´t use inheritance, they should use aggregation by means of creating
	 * an object that really inherits from this abstract class and implements update().
	 *  
	 * Inheriting from this class, RMI rules are fulfilled, but it is not compulsory.
	 * It is perfectly valid to use IRemoteObserver directly.
	 */
	
}
