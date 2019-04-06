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

import carrenting.server.jdo.Car;
import carrenting.server.jdo.DataDAO;
import carrenting.server.jdo.Garage;
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
		loginStaff("admin1", "admin1");
	}
	
	
	public void storeGarage(String location) {
		DataDAO.getInstance().storeGarage(location);
	}
	

	public ArrayList<String> getGarages() throws RemoteException{
		return DataDAO.getInstance().getGarages();
	}
	
	
	public boolean loginStaff(String user, String password) throws RemoteException{
	
		System.out.println("Received user: " + user);
		System.out.println("Received password: " + password);
		
		Staff staff = DataDAO.getInstance().getStaff(user);
		
		if(staff.getPass().equals(password)) {
			System.out.println("Login successful");
			return true;	
		}
		System.out.println("Login unsuccessful");
		return false;
	}
	

	public void registerUser (String username) throws RemoteException{
		System.out.println("Username: " + username);
		
	}
	
	protected void finalize() throws Throwable {
		if (tx.isActive()) {
            tx.rollback();
        }
        pm.close();
	}


	public ArrayList<Car> getCars(String garage,int availability) throws RemoteException {
		return DataDAO.getInstance().getCars(garage,availability);
	}


	
	
	
	
	
}
