package carrenting.server;

import java.rmi.RemoteException;


import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Transaction;

import carrenting.client.RMIServiceLocator;
import carrenting.server.jdo.Car;
import carrenting.server.jdo.DataDAO;
import carrenting.server.jdo.Garage;
import carrenting.server.jdo.Rent;
import carrenting.server.jdo.Staff;




public class CarRenting extends UnicastRemoteObject implements ICarRenting{
	private static final long serialVersionUID = 1L;
	private PersistenceManager pm=null;
	private Transaction tx=null;
	

	
	public CarRenting() throws RemoteException {
		PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
		this.pm = pmf.getPersistenceManager();
		this.tx = pm.currentTransaction();
		
		//PRUEBAS
		//loginStaff("admin1", "admin1");
//		getCars("Bilbao");
//System.out.println("2ª VEZ");
//getCars("Bilbao");
		//getRents();
		//deleteCar("0252HJH");
//		System.out.println(this.getCar("0352HTQ").toString());
	}
	
	
	public void storeGarage(String location) {
		DataDAO.getInstance().storeGarage(location);
	}
	

	public ArrayList<String> getGarages() throws RemoteException{
		return DataDAO.getInstance().getGarages();
	}
	
	public void storeCar(String garage, String numberPlate, String brand, String model, double pricePerDay) {
		DataDAO.getInstance().storeCar( garage, numberPlate, brand, model, pricePerDay);
	}
	
	public void deleteCar(String numberPlate) throws RemoteException {
		DataDAO.getInstance().deleteCar(numberPlate);
	}
	
	public void deleteGarage(String garage) throws RemoteException{
		DataDAO.getInstance().deleteGarage(garage);
	}
	
	public boolean loginStaff(String user, String password, String type) throws RemoteException{
	
		Staff staff = DataDAO.getInstance().getStaff(user);
		System.out.println(staff.toString());
		if(staff.getPassword().equals(password) && staff.getType().equals(type)) {
			System.out.println("Login successful");
			return true;
		}
		System.out.println("Login unsuccessful");
		return false;
	}

	public void registerUser (String username) throws RemoteException{
		System.out.println("Username: " + username);
		
	}

	public ArrayList<Car> getCars(String garage) throws RemoteException {
		ArrayList<Car>cars = new ArrayList<>();
		cars=DataDAO.getInstance().getCars(garage);
		for(Car car: cars) {
			System.out.println(car);
		}
		return cars;
	}
	
	public Car getCar(String numPlate) throws RemoteException{
		return DataDAO.getInstance().getCar(numPlate);
	}
	
	public ArrayList<Rent> getRents() throws RemoteException {
//		ArrayList<Rent> rents= new ArrayList<>();
//		rents.addAll(DataDAO.getInstance().getRents());
//		System.out.println("SERVER");
//		for(Rent rent: rents) {
//			System.out.println(rent);
//		}
//		return rents;
		return DataDAO.getInstance().getRents();
	}

	

	protected void finalize() throws Throwable {
		if (tx.isActive()) {
            tx.rollback();
        }
        pm.close();
	}



	


	
	
	
	
}
