package carrenting.server.jdo;

import java.util.ArrayList;

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
	
	private DataDAO(){
		pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
		
//		Initializing staff, garages, cars
		storeStaff();
		storeGarage(garage1.getLocation());
		storeGarage(garage2.getLocation());
		storeGarage("Bilbao");
//		storeCar(1,"Madrid","1234QWE","Ford", "Fiesta", 50);
		storeCar(0,"Bilbao","0987KJH","Ford", "Fiesta", 50);
		storeCar(1,"Bilbao","5764DFG","Mercedes", "Clase A", 200);
		storeCar(1,"Bilbao","7653GYU","Mercedes", "Clase A", 200);
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
		System.out.println("Initializing cars");
	}
	
	
	public ArrayList<String> getGarages() {
		ArrayList<String> preparedGarages = new ArrayList<>();
		PersistenceManager pm = pmf.getPersistenceManager();
		//pm.getFetchPlan().setMaxFetchDepth(2);
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
	
	public ArrayList<Car> getCars() {
		PersistenceManager pm = pmf.getPersistenceManager();
		pm.getFetchPlan().setMaxFetchDepth(2);
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			Query<Car> query = pm.newQuery(Car.class);
			@SuppressWarnings("unchecked")
			ArrayList<Car> cars = new ArrayList<Car>((List<Car>) query.execute());
			tx.commit();
			for (Car a : cars){
			System.out.println(a.toString());
		}
			return cars;
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
	
	
	
	
	
	

	
}
