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
		storeGarages();
//		storeCars();
	}

	public static DataDAO getInstance() {
		return instance;
	}
	
//Initialize garages
	private void storeGarages(){
		PersistenceManager pm = pmf.getPersistenceManager();
		try {
			pm.makePersistent(new Book("sadfg","gvkhjbgkuhbkbhu",49.99,"hbkjhbk", "35678", "MyBooks Factory"));

		} catch (Exception ex) {
			System.out.println("   $ Error storing book: " + ex.getMessage());
		} finally {
			pm.close();
		}
		System.out.println("ENTRÉ EN STORAGE GARAGES");
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
