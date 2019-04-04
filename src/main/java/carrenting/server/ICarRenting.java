package carrenting.server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import carrenting.server.jdo.Car;
import carrenting.server.jdo.Garage;


public interface ICarRenting extends Remote  {
	public boolean loginStaff(String user, String password) throws RemoteException;
	public void registerUser(String username) throws RemoteException;
//	public List<Car> getCars(String garages) throws RemoteException;
	public String[] getGarageNames() throws RemoteException;
	public void storeGarage(String location) throws RemoteException;
	public ArrayList<String> getGarages() throws RemoteException;
	
	
}