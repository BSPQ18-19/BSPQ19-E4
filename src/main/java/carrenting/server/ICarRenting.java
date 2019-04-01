package carrenting.server;

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface ICarRenting extends Remote {
	public boolean loginStaff(String user, String password) throws RemoteException;
	public void registerUser(String username) throws RemoteException;
	
}