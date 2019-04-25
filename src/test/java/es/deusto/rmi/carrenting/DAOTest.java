package es.deusto.rmi.carrenting;


import junit.framework.JUnit4TestAdapter;

import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

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

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

@RunWith(MockitoJUnitRunner.class)
public class DAOTest {
	
	private static String cwd = DAOTest.class.getProtectionDomain().getCodeSource().getLocation().getFile();
	private static Thread rmiRegistryThread = null;
	private static Thread rmiServerThread = null;
	static Controller c;
	
	public static junit.framework.Test suite() {
		return new JUnit4TestAdapter(DAOTest.class);
	}
	
	@BeforeClass
	static public void setUp() throws MalformedURLException, RemoteException, NotBoundException {
	
		class RMIRegistryRunnable implements Runnable {
			
			public void run() {
				try {
					java.rmi.registry.LocateRegistry.createRegistry(10990);
					System.out.println("RMI registry ready.");
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
				System.out.println("This is a test to check if mvn test -Pserver executes this tests; JVM properties by program");
				System.out.println("**************: " + cwd);
				System.setProperty("java.rmi.server.codebase", "file:" + cwd);
				System.setProperty("java.security.policy", "target\\classes\\security\\java.policy");
	
				String name = "//127.0.0.1:10990/TestCarServer";
				System.out.println(" * TestServer name: " + name);
				
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
	
	@Test
	public void testLoginStaff() throws RemoteException {
		assertEquals(c.loginStaff("admin1", "admin1", "administrator"), true);
	}
	
	
	
	@Test
	public void testStoreDeleteGarage() throws RemoteException {
		c.storeGarage("Portugalete");
		
		ArrayList<String> garages = c.getGarages();
		
		boolean a = false;
		
		for(String s : garages) {
			System.out.println(s);
			if(s.equals("Portugalete")){
				a = true;
			}
		}
		
		c.deleteGarage("Portugalete");
		
		garages = c.getGarages();
		
		for(String s : garages) {
			System.out.println(s);
			if(s.equals("Portugalete")){
				assert(false);
			}
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
	
	
	@AfterClass
	static public void after() throws RemoteException {
		c.deleteGarage("Vitoria");
	}
}
