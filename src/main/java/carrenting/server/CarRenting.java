package carrenting.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Date;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Transaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import carrenting.server.jdo.Car;
import carrenting.server.jdo.DataDAO;
import carrenting.server.jdo.Rent;
import carrenting.server.jdo.Staff;



public class CarRenting extends UnicastRemoteObject implements ICarRenting{
	final static Logger logger = LoggerFactory.getLogger(CarRenting.class);
	private static final long serialVersionUID = 1L;
	private PersistenceManager pm=null;
	@SuppressWarnings("unused")
	private Transaction tx=null;

	ArrayList<Staff> staffs =this.getAllStaff();
	
	public static Logger getLogger() {
		return logger;
	}

	/**
	 * Asks the DAO for all the staff members
	 * @return List with all the staff
	 */
	private ArrayList<Staff>getAllStaff(){
		staffs = DataDAO.getInstance().getStaff();
		return staffs;
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
	
	public void deleteRent(String numPlate, Date date) {
		DataDAO.getInstance().deleteRent(numPlate, date);
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
	 * @param garage Location where the car is stored
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
	 * @param garage Garage to be deleted
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
		boolean loginSuccessful=false;
		for(Staff staff: staffs ) {
			System.out.println(staff);
			
			
			if(staff.getUsername().equals(user) && staff.getPassword().equals(password) && staff.getType().equals(type)) {
				logger.debug("login_successful " + user);
				loginSuccessful=true;
			}
		}
		if(!loginSuccessful) {
			logger.error("login_unsuccessful");
		}
		return loginSuccessful;
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
	
	public boolean paymentSystem(String payentSystem, int price) {
		
		logger.info("Payment succesfull through "+ payentSystem +" by the quantity of "+ price +"€");		
		
		return true;	
	}

	/**
	 * Asks the DAO for the list of all the cars
	 * @return List of all cars in the DB
	 */
	@Override
	public ArrayList<Car> getAllCars() throws RemoteException {
		return DataDAO.getInstance().getAllCars();
		
	}




	


	
	
	
	
}
