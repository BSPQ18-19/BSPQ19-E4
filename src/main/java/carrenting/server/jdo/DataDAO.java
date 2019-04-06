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


public class DataDAO {
	
	private static DataDAO instance = new DataDAO();
	private static PersistenceManagerFactory pmf;
	private Garage garage1= new Garage("Madrid");
	private Garage garage2= new Garage("Barcelona");
	private Garage garage3= new Garage("Bilbao");
	private Date date= new Date(System.currentTimeMillis());  
	private Date date1=new GregorianCalendar(2014, Calendar.FEBRUARY, 11).getTime();
	private Date date2=new GregorianCalendar(2017, Calendar.FEBRUARY, 11).getTime();
	
	private DataDAO(){
		pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
		
//		Initializing staff, garages, cars, rent
		storeStaff();
		storeGarage(garage1.getLocation());
		storeGarage(garage2.getLocation());
		storeGarage(garage3.getLocation());
		storeCar(0,"Madrid","1234QWE","Ford", "Fiesta", 50);
		storeCar(0,"Madrid","1784QWE","Ford", "Fiesta", 50);
		storeCar(1,"Madrid","1934QWE","Ford", "Fiesta", 50);
		storeCar(0,"Bilbao","0987KJH","Ford", "Fiesta", 50);
		storeCar(1,"Bilbao","5764DFG","Mercedes", "Clase A", 200);
		storeCar(1,"Bilbao","7653GYU","Mercedes", "Clase A", 200);
		storeCar(1,"Bilbao","0932HJH","Audi", "A7", 180);
		storeCar(0,"Bilbao","0252HJH","Audi", "A7", 180);
		storeCar(0,"Bilbao","0352HTQ","Audi", "A7", 180);
		storeRent("12345678A", "0352HTQ",date,date,garage3.getLocation(), garage1.getLocation(), "paypal", 500);
		storeRent("12345678A", "0352HTQ",date1,date,garage3.getLocation(), garage2.getLocation(), "paypal", 500);
		storeRent("12345678A", "1234QWE",date1,date,garage3.getLocation(), garage2.getLocation(), "paypal", 500);
		
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
	}
	
	public void storeRent(String userId, String numberPlate, Date startingDate, Date finishingDate, String garageOrigin,
			String garageDestination, String paymentSystem, int totalPrice){
		PersistenceManager pm = pmf.getPersistenceManager();
		try {
			pm.makePersistent(new Rent(userId, numberPlate, startingDate, finishingDate, garageOrigin,
					 garageDestination,paymentSystem,  totalPrice));

		} catch (Exception ex) {
			System.out.println("   $ Error storing rent: " + ex.getMessage());
		} finally {
			pm.close();
		}
		System.out.println("Initializing garages");
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
	
	public void storeCar(int availability,String garage,String numPlate, String brand, String model,int pricePerDay){
		PersistenceManager pm = pmf.getPersistenceManager();
		try {
			pm.makePersistent(new Car(availability, garage, numPlate, brand, model, pricePerDay));
			
		} catch (Exception ex) {
			System.out.println("   $ Error storing staff: " + ex.getMessage());
		} finally {
			pm.close();
		}
		System.out.println("Storing cars cars");
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
			System.out.println("Entré en GET GARAGES");
			pm.close();
		}
		return null;
	}
	
	public ArrayList<Rent> getRents() {
		PersistenceManager pm = pmf.getPersistenceManager();
		pm.getFetchPlan().setMaxFetchDepth(2);
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			Query<Rent> query = pm.newQuery(Rent.class);
			@SuppressWarnings("unchecked")
			ArrayList<Rent> rents = new ArrayList<Rent>((List<Rent>) query.execute());
			tx.commit();
			for (Rent rent: rents) {
				System.out.println(rent);
			}
			return rents;

		} catch (Exception ex) {
			System.out.println("   $ Error retrieving data from the database: " + ex.getMessage());
		} finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}
			System.out.println("Entré en GET RENTS");
			pm.close();
		}
		return null;
	}
	
	public ArrayList<Car> getCars(String garage,int availability) {
		ArrayList<Car> carsFiltered = new ArrayList<>();
		PersistenceManager pm = pmf.getPersistenceManager();
		pm.getFetchPlan().setMaxFetchDepth(2);
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			Query<Car> query = pm.newQuery(Car.class);
			query.setFilter("garage=='" + garage + "'");
			@SuppressWarnings("unchecked")
			ArrayList<Car> carsByGarage = new ArrayList<Car>((List<Car>) query.execute());
			tx.commit();
//			System.out.println("Cars by garage");
//			for (Car a : carsByGarage){
//				System.out.println(a.toString());
//			}
//			System.out.println("Cars fully filtered");
			for(Car car: carsByGarage) {
				if (car.getAvailability()==availability) {
					carsFiltered.add(car);
//					System.out.println(car.toString());
				}
			}

			return carsFiltered;
		} catch (Exception ex) {
			System.out.println("   $ Error retrieving data from the database: " + ex.getMessage());
		} finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}
			System.out.println("Entré en GET CARS");
			
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
			@SuppressWarnings("unchecked")
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
			System.out.println("Entré en GET STAFF");
			
			pm.close();
		}
		return null;
	}
	
	
	
	
	
	

	
}
