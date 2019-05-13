package es.deusto.rmi.carrenting;


import junit.framework.JUnit4TestAdapter;

import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import carrenting.client.Controller;
import carrenting.server.CarRenting;
import carrenting.server.ICarRenting;
import carrenting.server.jdo.Car;
import carrenting.server.jdo.DataDAO;

import static org.junit.Assert.assertEquals;
import junit.framework.JUnit4TestAdapter;
import static org.mockito.Mockito.when;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

import org.databene.contiperf.PerfTest;
import org.databene.contiperf.Required;
import org.databene.contiperf.junit.ContiPerfRule;
import org.databene.contiperf.junit.ContiPerfSuiteRunner;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

@RunWith(MockitoJUnitRunner.class)
public class Tests {
	
	private static String cwd = Tests.class.getProtectionDomain().getCodeSource().getLocation().getFile();
	private static Thread rmiRegistryThread = null;
	private static Thread rmiServerThread = null;
	static Controller c;
	final static Logger logger = LoggerFactory.getLogger(Test.class);
	public static junit.framework.Test suite() {
		return new JUnit4TestAdapter(Tests.class);
	}
	
	@BeforeClass
	static public void setUp() throws MalformedURLException, RemoteException, NotBoundException {
	
		class RMIRegistryRunnable implements Runnable {
			
			public void run() {
				try {
					java.rmi.registry.LocateRegistry.createRegistry(10990);
					logger.info("RMI registry ready.");
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
			
		}
		
		rmiRegistryThread = new Thread(new RMIRegistryRunnable());
		rmiRegistryThread.start();
		
		try {
			Thread.sleep(2000);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		class RMIServerRunnable implements Runnable {
			
			public void run() {
				System.setProperty("java.rmi.server.codebase", "file:" + cwd);
				System.setProperty("java.security.policy", "target\\classes\\security\\java.policy");
	
				String name = "//127.0.0.1:10990/TestCarServer";
				logger.info(" * TestServer name: " + name);
				
				try {
					ICarRenting carRenting = new CarRenting();
					Naming.rebind(name, carRenting);
					DataDAO.getInstance();
										
				}catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		}
		
		rmiServerThread = new Thread(new RMIServerRunnable());
		rmiServerThread.start();
		
		
		try {
			Thread.sleep(15000);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
		c = new Controller("127.0.0.1", "10990", "TestCarServer");
		c.storeGarage("Vitoria");
	}
	
	
	@Rule
	public ContiPerfRule t = new ContiPerfRule();
	@Test
	@PerfTest(invocations = 5, threads = 5)
	@Required(max = 1500, average = 1000, throughput = 5)
	public void testLoginStaff() throws RemoteException {
		assertEquals(c.loginStaff("admin1", "admin1", "administrator"), true);
	}
	
	
	@Test
	@PerfTest(duration = 2000)
	@Required(max = 3000, average = 3000)
	public void testStoreDeleteGarage() throws RemoteException {
		c.storeGarage("Portugalete");
		
		ArrayList<String> garages = c.getGarages();
		
		boolean a = false;
		
		for(String s : garages) {
			if(s.equals("Portugalete"))
				a = true;
		}
		
		c.deleteGarage("Portugalete");
		
		garages = c.getGarages();
		
		for(String s : garages) {
			if(s.equals("Portugalete"))
				assert(false);
		}
		
		assert(a);
		
	}
	
	@Test
	public void testStoreDeleteCar() throws RemoteException {
		Car storedCar = new Car("Vitoria", "3223GVV", "Citroen", "C4", 40);
		c.storeCar(storedCar.getGarage(), storedCar.getNumPlate(), storedCar.getBrand(), storedCar.getModel(), storedCar.getPricePerDay());
		
		Car retrievedCar = c.getCar(storedCar.getNumPlate());
		
		boolean a = false;
		
		if(storedCar.getNumPlate().equals(retrievedCar.getNumPlate()))
			a = true;
		
		
		c.deleteCar(retrievedCar.getNumPlate());
		
		ArrayList<Car> carList = c.getAllCars();
		
		for(Car c : carList) {
			if(c.equals(storedCar))
				assert(false);
		}
		
		assert(a);
		
	}
	
	
	@Test
	public void testCheckExistingNumPlate() throws RemoteException {
		Car storedCar = new Car("Vitoria", "5678ASD", "Citroen", "C3", 40);
		c.storeCar(storedCar.getGarage(), storedCar.getNumPlate(), storedCar.getBrand(), storedCar.getModel(), storedCar.getPricePerDay());
		
		System.out.println(c.checkExistingNumPlate("5678ASD"));
		assert(c.checkExistingNumPlate("5678ASD"));
	
	}
	
	
	@Test
	@PerfTest(duration = 2000)
	@Required(max = 3000, average = 3000)
	public void testGarageOriginPopularity() throws RemoteException {
		
		
		Object[][] result = new Object[4][3];
		result[0][0] = "Barcelona";
		result[1][0] = "Bilbao";
		result[2][0] = "Madrid";
		result[3][0] = "Vitoria";
		
		result[0][1] = 0;
		result[1][1] = 10;
		result[2][1] = 4;
		result[3][1] = 0;
		
		result[0][2] = 7;
		result[1][2] = 4;
		result[2][2] = 3;
		result[3][2] = 0;
		
		Object[][] garagePopularity = c.garageOriginPopularity();
		
		
		for(int i=0; i<result.length; i++) {
			for(int j=0; j<result[i].length; j++) {
				if(!result[i][j].equals(garagePopularity[i][j])) {
					assert(false);
				}
			}
		}
		assert(true);
		
	}
	
	
	
	
	@AfterClass
	static public void after() throws RemoteException {
		c.deleteGarage("Vitoria");
	}
}
