package carrenting.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;


public class CarRenting extends UnicastRemoteObject implements ICarRenting{

	
	
	public CarRenting() throws RemoteException {
		
	}

	public boolean loginStaff(String user, String password) throws RemoteException{
	
		System.out.println("Recieved user: " + user);
		System.out.println("Recieved password: " + password);
		
		return false;
	}

	public void registerUser (String username) throws RemoteException{
		System.out.println("Username: " + username);
		
	}

	
	
}
