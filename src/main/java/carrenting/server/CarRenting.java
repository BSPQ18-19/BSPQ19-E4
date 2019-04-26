package carrenting.server;

import java.rmi.RemoteException;


import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Transaction;

import carrenting.server.jdo.Car;
import carrenting.server.jdo.DataDAO;
import carrenting.server.jdo.Rent;
import carrenting.server.jdo.Staff;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class CarRenting extends UnicastRemoteObject implements ICarRenting{
	final static Logger logger = LoggerFactory.getLogger(CarRenting.class);
	private static final long serialVersionUID = 1L;
	private PersistenceManager pm=null;
	private Transaction tx=null;
	

	
	public static Logger getLogger() {
		return logger;
	}

	public CarRenting() throws RemoteException {
		PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
		this.pm = pmf.getPersistenceManager();
		this.tx = pm.currentTransaction();
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
		if(staff.getPassword().equals(password) && staff.getType().equals(type)) {
			logger.info("login_successful" + user);
			return true;
		}
		logger.error("login_unsuccessful");
		return false;
	}

	public void registerUser (String username) throws RemoteException{
		logger.info("Username: "+username);	
	}

	public ArrayList<Car> getCars(String garage) throws RemoteException {
		return DataDAO.getInstance().getCars(garage);
	}
	
	public Car getCar(String numPlate) throws RemoteException{
		return DataDAO.getInstance().getCar(numPlate);
	}
	
	public ArrayList<Rent> getRents() throws RemoteException {
		return DataDAO.getInstance().getRents();
	}

	
	protected void finalize() throws Throwable {
		if (tx.isActive()) {
            tx.rollback();
        }
        pm.close();
	}



	


	
	
	
	
}
