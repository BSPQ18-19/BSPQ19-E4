package carrenting.server.jdo;

import java.sql.Connection;


import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
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
import org.apache.log4j.Logger;

import carrenting.server.logger.ServerLogger;



public class DataDAO{
	
	private static DataDAO instance = new DataDAO();
	private static PersistenceManagerFactory pmf;
	private Logger log;
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

	
	
	private DataDAO(){
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
		storeRent("12005678A", "0352HTQ",datePast5,datePast2,garage3.getLocation(), garage1.getLocation(), "paypal", 500);
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
		 
		
		
//		System.out.println(getCar("0352HTQ").toString());
	}

	public static DataDAO getInstance() {
		return instance;
	}

	public void storeGarage(String location){
		PersistenceManager pm = pmf.getPersistenceManager();
		try {
			pm.makePersistent(new Garage(location));

		} catch (Exception ex) {
			System.out.println("   $ Error storing garage: " + ex.getMessage());
		} finally {
			pm.close();
		}
		System.out.println("Initializing garages");
		System.out.println("Initializing garages");
		System.out.println("Initializing garages");
		System.out.println("Initializing garages");
		ServerLogger.getLogger().info("   * Retrieving an Extent for Hotels.");
	}
	
	public void storeRent(String userId, String numberPlate, Date startingDate, Date finishingDate, String garageOrigin,
			String garageDestination, String paymentSystem, int totalPrice){
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try {
			tx.begin();
			pm.makePersistent(new Rent(userId, numberPlate, startingDate, finishingDate, garageOrigin,
					 garageDestination,paymentSystem,  totalPrice));
			tx.commit();
		} catch (Exception ex) {
			System.out.println("   $ Error storing rent: " + ex.getMessage());
		} finally {
			pm.close();
		}
		System.out.println("Initializing RENTS");
	}
	
	private void storeStaff(){
		
		PersistenceManager pm = pmf.getPersistenceManager();
		try {
			pm.makePersistent(new Staff("administrator","admin1", "admin1"));
			pm.makePersistent(new Staff("administrator","admin2", "admin2"));
			pm.makePersistent(new Staff("employee","employee1", "employee1"));
		} catch (Exception ex) {
			System.out.println("   $ Error storing staff: " + ex.getMessage());
		} finally {
			pm.close();
		}
		System.out.println("Initializing STAFF");
	}
	
	public void storeCar(String garage,String numberPlate, String brand, String model,int pricePerDay){
		PersistenceManager pm = pmf.getPersistenceManager();
		try {
			pm.makePersistent(new Car(garage, numberPlate, brand, model, pricePerDay));
			
		} catch (Exception ex) {
			System.out.println("   $ Error storing staff: " + ex.getMessage());
		} finally {
			pm.close();
		}
		System.out.println("Storing cars");
	}
	
	
	public ArrayList<String> getGarages() {
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
			System.out.println("   $ Error retrieving data from the database: " + ex.getMessage());
		} finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}
			System.out.println("GETTING GARAGES");
			pm.close();
		}
		return null;
	}
	
	public ArrayList<Rent> getRents() {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			Query<Rent> query = pm.newQuery(Rent.class);
			@SuppressWarnings("unchecked")
			ArrayList<Rent>rents= new ArrayList<Rent>((List<Rent>) query.execute());
			tx.commit();
			for (Rent rent: rents) {
				System.out.println(rent.toString());
			}
			return rents;

		} catch (Exception ex) {
			System.out.println("   $ Error retrieving data from the database: " + ex.getMessage());
		} finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}
			System.out.println("GETTING RENTS");
			pm.close();
		}
		return null;
	}
	
	public void deleteCar(String numberPlate) {
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
			System.out.println("   $ Error deleting data from the database: " + ex.getMessage());
		}finally {
			System.out.println("DELETED CAR");
			pm.close();
		}
	}
	
	public void deleteGarage(String garage) {
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
			System.out.println("   $ Error deleting data from the database: " + ex.getMessage());
		}finally {
			System.out.println("DELETED GARAGE");
			pm.close();
		}
	}
	
	public ArrayList<Car> getCars(String garage) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			Query<Car> query = pm.newQuery(Car.class);
			query.setFilter("garage=='" + garage + "'");
			@SuppressWarnings("unchecked")
			ArrayList<Car> carsByGarage = new ArrayList<Car>((List<Car>) query.execute());
			tx.commit();
			for(Car car: carsByGarage) {
				System.out.println(car.toString());
			}
			return carsByGarage;
			
		} catch (Exception ex) {
			System.out.println("   $ Error retrieving data from the database: " + ex.getMessage());
		} finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}
			System.out.println("GETTING CARS");
			pm.close();
		}
		return null;
	}
	
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
			System.out.println("GETTING CAR by numplate"+ car.toString());

			return car;
		} catch (Exception ex) {
			System.out.println("   $ Error retrieving data from the database: " + ex.getMessage());
		} finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}
			
			
			pm.close();
		}
		return null;
	}
	
	public Staff getStaff(String user) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			Query<Staff> query = pm.newQuery(Staff.class);
			query.setFilter("username=='" + user +"'");
			query.setUnique(true);
			Staff staff = (Staff) query.execute();
			tx.commit();
			System.out.println(staff.toString());
			return staff;
		} catch (Exception ex) {
			System.out.println("   $ Error retrieving data from the database: " + ex.getMessage());
		} finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}
			System.out.println("GETTING STAFF");
			
			pm.close();
		}
		return null;
	}
	
	
//	public void updateAvailability(String numberPlate, int newAvailability, String garageDestination) {
//		PersistenceManager pm = pmf.getPersistenceManager();
//		Transaction tx = pm.currentTransaction();
//		try {
//			tx.begin();
//			Query q =pm.newQuery("javax.jdo.query.SQL","UPDATE carrenting.car SET AVAILABILITY =" + newAvailability + " , GARAGE ='"+ garageDestination + "' WHERE NUMPLATE='" + numberPlate + "'");
//			q.execute();
//			tx.commit();
//		} catch (Exception ex) {
//			System.out.println("   $ Error updating the availability of a car: " + ex.getMessage());
//		} finally {
//			if (tx != null && tx.isActive()) {
//				tx.rollback();
//			}
//			System.out.println("Updating availability and garage");
//			pm.close();
//		}	
//	}
	
	

	
	
	
	

	
}
