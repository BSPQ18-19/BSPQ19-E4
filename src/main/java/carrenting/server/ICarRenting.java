package carrenting.server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import carrenting.server.jdo.Car;
import carrenting.server.jdo.Garage;
import carrenting.server.jdo.Rent;


public interface ICarRenting extends Remote  {
	public boolean loginStaff(String user, String password, String type) throws RemoteException;
	public void registerUser(String username) throws RemoteException;
	public void storeGarage(String location) throws RemoteException;
	public ArrayList<String> getGarages() throws RemoteException;
	public ArrayList<Car> getCars(String garage) throws RemoteException;
	public Car getCar(String numPlate) throws RemoteException;
	public ArrayList<Rent> getRents() throws RemoteException;
	public void storeCar(String garage, String numberPlate, String brand, String model, int pricePerDay) throws RemoteException;
	public void deleteCar(String numberPlate) throws RemoteException;
	public void deleteGarage(String garage) throws RemoteException;
}