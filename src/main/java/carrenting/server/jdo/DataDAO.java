package carrenting.server.jdo;


import java.util.ArrayList;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.Transaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import carrenting.server.CarRenting;


public class DataDAO{

	private static DataDAO instance = new DataDAO();
	private static PersistenceManagerFactory pmf;
	private Garage garage1= new Garage("Madrid");
	private Garage garage2= new Garage("Barcelona");
	private Garage garage3= new Garage("Bilbao");
	private Date date= new Date(System.currentTimeMillis());  
	private Date datePast1=new GregorianCalendar(2018, Calendar.AUGUST, 20).getTime();
	private Date datePast2=new GregorianCalendar(2018, Calendar.AUGUST, 13).getTime();
	private Date datePast3=new GregorianCalendar(2018, Calendar.AUGUST, 11).getTime();
	private Date datePast5=new GregorianCalendar(2017, Calendar.JUNE, 11).getTime();
	private Date datePast6=new GregorianCalendar(2015, Calendar.AUGUST, 11).getTime();
	private Date datePast7=new GregorianCalendar(2014, Calendar.JULY, 11).getTime();
	
	private Date date6 = new GregorianCalendar(2019, Calendar.AUGUST, 20).getTime();
	private Date date5 = new GregorianCalendar(2019, Calendar.AUGUST, 25).getTime();
	private Date date4 = new GregorianCalendar(2019, Calendar.SEPTEMBER, 20).getTime();
	private Date date3 = new GregorianCalendar(2019, Calendar.SEPTEMBER, 27).getTime();
	private Date date2 = new GregorianCalendar(2019, Calendar.JULY, 1).getTime();
	private Date date1 = new GregorianCalendar(2020, Calendar.JULY, 1).getTime();

	
	/**
	 * Initializes the DB
	 */
	public DataDAO(){
		
		pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
		
//		Initializing staff, garages, cars, rent
		storeStaff();
		storeGarage(garage1.getLocation());
		storeGarage(garage2.getLocation());
		storeGarage(garage3.getLocation());
		storeCar("Madrid","1234QWE","Ford", "Fiesta", 50);
		storeCar("Madrid","1784GSE","Ford", "Fiesta", 50);
		storeCar("Madrid","1934QWE","Ford", "Fiesta", 50);
		
		storeCar("Bilbao","0987KJH","Ford", "Fiesta", 50);
		storeCar("Bilbao","5764DFG","Mercedes", "Clase A", 200);
		storeCar("Bilbao","7653GYU","Mercedes", "Clase A", 200);
		storeCar("Bilbao","0932HJH","Audi", "A7", 180);
		storeCar("Bilbao","0252HJH","Audi", "A4", 180);
		storeCar("Bilbao","0352HTQ","Audi", "A5", 50);
		
		storeCar("Barcelona","8765BCN","Volvo", "XC60", 50);
		//Hystorical rents
		storeRent("12349578B", "0352HTQ",datePast7,datePast6,garage3.getLocation(), garage3.getLocation(), "paypal", 500);
		storeRent("12365678A", "1234QWE",datePast6,datePast5,garage1.getLocation(), garage2.getLocation(), "paypal", 500);
		storeRent("12365678A", "0252HJH",datePast7,datePast3,garage3.getLocation(), garage2.getLocation(), "paypal", 500);
		storeRent("12365678A", "1784GSE",datePast6,datePast5,garage1.getLocation(), garage1.getLocation(), "paypal", 500);
		storeRent("12365678A", "1784GSE",datePast3,datePast2,garage1.getLocation(), garage2.getLocation(), "paypal", 500);
		storeRent("12365678A", "0987KJH",datePast3,datePast2,garage3.getLocation(), garage2.getLocation(), "paypal", 500);
		storeRent("12365678A", "0987KJH",datePast2,datePast1,garage3.getLocation(), garage2.getLocation(), "paypal", 500);
		//Future rents
		storeRent("12365678A", "0987KJH",date6,date5, garage3.getLocation(), garage3.getLocation(), "visa", 500);
		storeRent("12365678A", "0352HTQ",date6,date5, garage3.getLocation(), garage3.getLocation(), "visa", 500);
		storeRent("12365678A", "0252HJH",date4,date3, garage3.getLocation(), garage2.getLocation(), "paypal", 500);
		storeRent("12365678A", "1234QWE",date,date5,garage1.getLocation(), garage2.getLocation(), "paypal", 500);
		
		//Current rents
		storeRent("12365678A", "0987KJH",datePast1,date, garage3.getLocation(), garage3.getLocation(), "paypal", 500);
		storeRent("12365678A", "0352HTQ",date,date, garage3.getLocation(), garage1.getLocation(), "paypal", 500);
		 
	}
	
	/**
	 * Gives the instance of the SAO
	 * @return Instance of the class
	 */
	public static DataDAO getInstance() {
		
		return instance;
	}

	/**
	 * Stores a garage in the DB
	 * @param location Location of the garage
	 */
	public synchronized void storeGarage(String location){
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			pm.makePersistent(new Garage(location));
			tx.commit();
		} catch (Exception ex) {
			CarRenting.getLogger().error("Error storing garage: "+ex.getMessage());
		} finally {
			pm.close();
		}
		CarRenting.getLogger().debug("Initializing garages");
		CarRenting.getLogger().debug("Finishing testBagMultiply");

	}
	
	/**
	 * Stores a rent in the DB
	 * @param userId ID of the user
	 * @param numberPlate Number plate of the car
	 * @param startingDate Starting date of the rent
	 * @param finishingDate Finishing date of the rent
	 * @param garageOrigin Garage of origin
	 * @param garageDestination Garage of destination
	 * @param paymentSystem Payment system
	 * @param totalPrice Total price of the rent
	 */
	public synchronized void storeRent(String userId, String numberPlate, Date startingDate, Date finishingDate, String garageOrigin,
			String garageDestination, String paymentSystem, double totalPrice){
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try {
			tx.begin();
			pm.makePersistent(new Rent(userId, numberPlate, startingDate, finishingDate, garageOrigin,
					 garageDestination,paymentSystem,  totalPrice));
			tx.commit();
		} catch (Exception ex) {
			CarRenting.getLogger().error("Error storing rents: "+ex.getMessage());
		} finally {
			pm.close();
		}
		CarRenting.getLogger().debug("Initializing RENTS");
	}
	
	/**
	 * Initializes the staff in the DB
	 */
	private synchronized void storeStaff(){
		
		PersistenceManager pm = pmf.getPersistenceManager();
		try {
			pm.makePersistent(new Staff("administrator","admin1", "admin1"));
			pm.makePersistent(new Staff("administrator","admin2", "admin2"));
			pm.makePersistent(new Staff("employee","employee1", "employee1"));
		} catch (Exception ex) {
			CarRenting.getLogger().error("Error storing staff:" +ex.getMessage());
		} finally {
			pm.close();
		}

		CarRenting.getLogger().debug("Initializing STAFF");
	}
	
	/**
	 * Stores a car in the DB
	 * @param garage Location where the car is stored
	 * @param numberPlate Number plate of the car
	 * @param brand Brand of the car
	 * @param model Model of the car
	 * @param pricePerDay Price per day of the car
	 */
	public synchronized void storeCar(String garage,String numberPlate, String brand, String model,double pricePerDay){
		PersistenceManager pm = pmf.getPersistenceManager();
		try {
			pm.makePersistent(new Car(garage, numberPlate, brand, model, pricePerDay));
			
		} catch (Exception ex) {
			CarRenting.getLogger().error("Error storing car:" +ex.getMessage());
		} finally {
			pm.close();
		}
		CarRenting.getLogger().debug("Storing cars");
	}
	
	/**
	 * Asks the BD for the list of garages
	 * @return List of garages
	 */
	public synchronized ArrayList<String> getGarages() {
		ArrayList<String> preparedGarages = new ArrayList<>();
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			Query<Garage> query = pm.newQuery(Garage.class);
			@SuppressWarnings("unchecked")
			ArrayList<Garage> garages = new ArrayList<Garage>((List<Garage>) query.execute());
			tx.commit();
			for (Garage a : garages){
				preparedGarages.add(a.getLocation().toString());
			}
			return preparedGarages;

		} catch (Exception ex) {
			CarRenting.getLogger().error("Error retrieving data from the database:" +ex.getMessage());
		} finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}
			CarRenting.getLogger().debug("GETTING GARAGES");
			pm.close();
		}
		return null;
	}
	
	/**
	 * Asks the DB for the list of rents
	 * @return
	 */
	public synchronized ArrayList<Rent> getRents() {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			Query<Rent> query = pm.newQuery(Rent.class);
			@SuppressWarnings("unchecked")
			ArrayList<Rent>rents= new ArrayList<Rent>((List<Rent>) query.execute());
			tx.commit();
			for (Rent rent: rents) {
				CarRenting.getLogger().debug(rent.toString());
			}
			return rents;

		} catch (Exception ex) {
			CarRenting.getLogger().error("Error retrieving data from the database:" +ex.getMessage());
		} finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}
			CarRenting.getLogger().debug("GETTING RENTS");
			pm.close();
		}
		return null;
	}
	
	/**
	 * Deletes a car from the DB given a number plate
	 * @param numberPlate Identification of the car to be deleted
	 */
	public synchronized void deleteCar(String numberPlate) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			Query<Car> query = pm.newQuery(Car.class);
			query.setUnique(true);
			Car carToDelete = (Car) pm.getObjectById(Car.class, numberPlate);	
			pm.deletePersistent(carToDelete);
			tx.commit();
		}catch (Exception ex){
			CarRenting.getLogger().error("Error retrieving data from the database:" +ex.getMessage());
		}finally {
			CarRenting.getLogger().debug("DELETED CAR "+numberPlate);
			pm.close();
		}
	}
	
	/**
	 * Deletes a garage from the DB
	 * @param garage Name of the garage to be deleted
	 */
	public synchronized void deleteGarage(String garage) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			
			Query<Garage> query = pm.newQuery(Garage.class);
			query.setUnique(true);
			Garage garageToDelete = (Garage) pm.getObjectById(Garage.class, garage);
			pm.deletePersistent(garageToDelete);
			
			tx.commit();
		}catch (Exception ex){
			CarRenting.getLogger().error("Error deleting data from the database:" +ex.getMessage());
		}finally {
			CarRenting.getLogger().debug("DELETED GARAGE" + garage);
			pm.close();
		}
	}
	
	/**
	 * Asks the DB of the list of cars in a garage
	 * @return List of cars in the given garage
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<Car> getAllCars() {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		ArrayList<Car> allCars = new ArrayList<>();
		
		try {
			tx.begin();
			Query<Car> query = pm.newQuery(Car.class);
			allCars = new ArrayList<Car>((List<Car>) query.execute());
			
			tx.commit();
			for(Car car: allCars) {
				CarRenting.getLogger().debug(car.toString());
			}
			
			
		} catch (Exception ex) {
			CarRenting.getLogger().error("Error retrieving data from the database:" +ex.getMessage());
			return null;
		} finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}
			CarRenting.getLogger().debug("GETTING CARS");
			pm.close();
			
		}
		return allCars;
	}
	
	
	
	/**
	 * Deletes a garage from the DB
	 * @param garage Name of the garage to be deleted
	 */
	public synchronized void deleteGarageAndCars(String garage) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			
			Query<Garage> query = pm.newQuery(Garage.class);
			query.setUnique(true);
			Garage garageToDelete = (Garage) pm.getObjectById(Garage.class, garage);
			pm.deletePersistent(garageToDelete);
			
			tx.commit();
		}catch (Exception ex){
			CarRenting.getLogger().error("Error deleting data from the database:" +ex.getMessage());
		}finally {
			CarRenting.getLogger().debug("DELETED GARAGE" + garage);
			pm.close();
		}
	}
	
	
	/**
	 * Asks the DB of the list of cars in a garage
	 * @param garage Name of the garage
	 * @return List of cars in the given garage
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<Car> getCars(String garage) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		ArrayList<Car> carsByGarage = new ArrayList<>();
		
		try {
			tx.begin();
			Query<Car> query = pm.newQuery(Car.class);
			query.setFilter("garage=='" + garage + "'");
	
			carsByGarage = new ArrayList<Car>((List<Car>) query.execute());
			
			tx.commit();
			for(Car car: carsByGarage) {
				CarRenting.getLogger().debug(car.toString());
			}
			
			
		} catch (Exception ex) {
			CarRenting.getLogger().error("Error retrieving data from the database:" +ex.getMessage());
			return null;
		} finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}
			CarRenting.getLogger().debug("GETTING CARS");
			pm.close();
			
		}
		return carsByGarage;
	}
	
	/**
	 * Asks for a car to the DB given its number plate
	 * @param numPlate Number plate of the car
	 * @return Car
	 */
	public Car getCar(String numPlate) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			Query<Car> query = pm.newQuery(Car.class);
			query.setFilter("numPlate=='" + numPlate + "'");
			query.setUnique(true);
			Car car = ((Car) query.execute());
			tx.commit();
			CarRenting.getLogger().debug("GETTING CAR BY NUMPLATE"+ car.toString());
			
			
			return car;
		} catch (Exception ex) {
			CarRenting.getLogger().error("Error retrieving data from the database:" +ex.getMessage());
		} finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}
			
			
			pm.close();
		}
		return null;
	}
	 /**
	  * Asks the DB for the information of a user
	  * @param user username of the user
	  * @return Staff
	  */
	public Staff getStaff(String user) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		Staff emptyStaff  = new Staff();
		try {
			tx.begin();
			Query<Staff> query = pm.newQuery(Staff.class);
			query.setFilter("username=='" + user +"'");
			query.setUnique(true);
			Staff staff = (Staff) query.execute();
			tx.commit();
			CarRenting.getLogger().debug(staff.toString());
			return staff;
			
		} catch (Exception ex) {
			CarRenting.getLogger().error("Error retrieving data from the database:" +ex.getMessage());
		} finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}
			CarRenting.getLogger().debug("GETTING STAFF " + user);
			
			pm.close();
		}
		return emptyStaff;
	}
	
	
	public void updateGarage(String numberPlate, String newGarage) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			Query q =pm.newQuery("javax.jdo.query.SQL","UPDATE carrenting.car SET GARAGE ='" + newGarage + "' WHERE NUMPLATE='" + numberPlate + "'");
			q.execute();
			tx.commit();


		} catch (Exception ex) {
			System.out.println("   $ Error updating the availability of a car: " + ex.getMessage());
		} finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}
			System.out.println("Entré en update availability");
			pm.close();
		}
	}
	

	
	
	
	

	
}
