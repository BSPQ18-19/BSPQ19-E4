package carrenting.server.jdo;

import java.util.ArrayList;


import java.util.List;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.Transaction;

import carrenting.db.Book;



public class DataDAO {
	
	private static DataDAO instance = new DataDAO();
	private static PersistenceManagerFactory pmf;
 

	private DataDAO(){
		pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
		
//		storeCars();
	}

	public static DataDAO getInstance() {
		return instance;
	}
	
//Initialize garages

	public void storeGarage(String location){
		PersistenceManager pm = pmf.getPersistenceManager();
		try {
			pm.makePersistent(new Garage(location));

		} catch (Exception ex) {
			System.out.println("   $ Error storing garage: " + ex.getMessage());
		} finally {
			pm.close();
		}
		System.out.println("ENTRÉ EN STORAGE GARAGES");
	}

	public ArrayList<String> getGarages() {
		ArrayList<String> preparedGarages = new ArrayList<>();
		PersistenceManager pm = pmf.getPersistenceManager();
		pm.getFetchPlan().setMaxFetchDepth(2);
		Transaction tx = pm.currentTransaction();

		try {
			tx.begin();
			Query<Garage> query = pm.newQuery(Garage.class);
			@SuppressWarnings("unchecked")
			ArrayList<Garage> garages = new ArrayList<Garage>((List<Garage>) query.execute());
			tx.commit();
			for (Garage a : garages){
				preparedGarages.add(a.toString());
			}
			return preparedGarages;

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

	
	
	public ArrayList<String> getLocations() {
		ArrayList<String> locations = new ArrayList<>();
		PersistenceManager pm = pmf.getPersistenceManager();
		pm.getFetchPlan().setMaxFetchDepth(2);
		Transaction tx = pm.currentTransaction();

		try {
			tx.begin();
			Query<Garage> query = pm.newQuery(Garage.class);
			@SuppressWarnings("unchecked")
			ArrayList<Garage> garages = new ArrayList<Garage>((List<Garage>) query.execute());
			tx.commit();
			for (Garage a : garages){
				locations.add(a.getLocation());
			}
			return locations;

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
	
}
