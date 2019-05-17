package carrenting.server;

import java.rmi.RemoteException;


import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

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
	
	private HashMap<String, Staff> users = new HashMap<String, Staff>();
	
	public static Logger getLogger() {
		return logger;
	}


	/**
	 * Initializes CarRenting
	 * @throws RemoteException
	 */
	public CarRenting() throws RemoteException {
		PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
		this.pm = pmf.getPersistenceManager();
		this.tx = pm.currentTransaction();
	}
	
	/**
	 * Tells the DAO to store the given location of the garage
	 * @param location Location of the garage
	 */
	public void storeGarage(String location) {
		DataDAO.getInstance().storeGarage(location);
	}
	
	/**
	 * Gets all the garages that are stored in the DB and passes them to the Client
	 * @return List of garages
	 */
	public ArrayList<String> getGarages() throws RemoteException{
		return DataDAO.getInstance().getGarages();
	}
	
	/**
	 * Stores a Car in a garage into the DB
	 * @param garage Locatin where the car is stored
	 * @param numberPlate Number plate of the car
	 * @param brand Brand of the car
	 * @param model Model of the car
	 * @param pricePerDay Cost of the car per day
	 */
	public void storeCar(String garage, String numberPlate, String brand, String model, double pricePerDay) {
		DataDAO.getInstance().storeCar( garage, numberPlate, brand, model, pricePerDay);
	}
	
	/**
	 * Deletes a car from the DB given a Number Plate
	 * @param numberPlate Number plate of the car
	 */
	public void deleteCar(String numberPlate) throws RemoteException {
		DataDAO.getInstance().deleteCar(numberPlate);
	}
	
	/**
	 * Deletes a Garage from the DB
	 * @param garage Garage to eb deleted
	 */
	public void deleteGarage(String garage) throws RemoteException{
		DataDAO.getInstance().deleteGarage(garage);
	}
	
	/**
	 * Method for logging staff.
	 * @param user username of the staff
	 * @param password password of the staff
	 * @param type type of the staff
	 */
	public boolean loginStaff(String user, String password, String type) throws RemoteException{
		Staff staff = DataDAO.getInstance().getStaff(user);
		if(staff.getPassword().equals(password) && staff.getType().equals(type)) {
			logger.debug("login_successful " + user);
			return true;
		}
		logger.error("login_unsuccessful");
		return false;
	}

	/**
	 * Registers a normal user
	 * @param username Username of the user
	 */
	public void registerUser (String username) throws RemoteException{
		logger.info("Username: "+username);	
	}

	/**
	 * Asks the DAO for the list of cars stored in a garage
	 * @param garage Location where cars are stored
	 * @return List of cars in a garage
	 */
	public ArrayList<Car> getCars(String garage) throws RemoteException {
		return DataDAO.getInstance().getCars(garage);
	}
	
	/**
	 * Asks the server for a car by giving the number plate
	 * @param numPlate Number plate of the car
	 * @return Car
	 */
	public Car getCar(String numPlate) throws RemoteException{
		return DataDAO.getInstance().getCar(numPlate);
	}
	
	/**
	 * Asks the server for the list of rents
	 * @return List of rents
	 */
	public ArrayList<Rent> getRents() throws RemoteException {
		return DataDAO.getInstance().getRents();
	}

	/**
	 * Finishes the connection with the DB
	 */
//	protected void finalize() throws Throwable {
//		if (tx.isActive()) {
//            tx.rollback();
//        }
//        pm.close();
//	}

	public void storeRent(String userId, String numberPlate, Date startingDate, Date finishingDate, String garageOrigin,
			String garageDestination, String paymentSystem, double totalPrice) throws RemoteException {
		DataDAO.getInstance().storeRent(userId,  numberPlate,  startingDate,  finishingDate,  garageOrigin,
				 garageDestination,  paymentSystem,  totalPrice);
		
	}


	@Override
	public ArrayList<Car> getAllCars() throws RemoteException {
		return DataDAO.getInstance().getAllCars();
		
	}



	


	
	
	
	
}
