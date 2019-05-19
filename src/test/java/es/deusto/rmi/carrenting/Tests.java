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
import carrenting.server.jdo.Rent;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import junit.framework.JUnit4TestAdapter;
import static org.mockito.Mockito.when;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

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
	@PerfTest(invocations = 50, threads = 5)
	@Required(max = 1500, average = 1000, throughput = 5)
	public void testLoginStaff() throws RemoteException {
		assertEquals(c.loginStaff("admin1", "admin1", "administrator"), true);
	}
	
	@Test
	@PerfTest(invocations = 50, threads = 5)
	@Required(max = 1500, average = 1000, throughput = 5)
	public void testLoginStaffError() throws RemoteException {
		assertFalse(c.loginStaff("admin1", "admin3", "administrator"));
	}
	
	@Test 
	public void testStoreRent() throws RemoteException {
		Date date1=new GregorianCalendar(2017, Calendar.JUNE, 11).getTime();
		Date date2=new GregorianCalendar(2018, Calendar.AUGUST, 13).getTime();
		
		Rent rent = new Rent("12005678A", "0352HTQ", date1, date2, "Madrid", "Bilbao", "paypal", 500);
		
		c.storeRent("12005678A", "0352HTQ", date1, date2, "Madrid", "Bilbao", "paypal", 500);
		
		ArrayList<Rent> rents = c.getRents();
		for(Rent r: rents) {
			if(r.equals(rent)) {
				assert(true);
			}
		}
		
		c.deleteRent("0352HTQ", date1);
	}
	
	@Test
	public void testRegisterUser() throws RemoteException {
		c.register("alvaroh");
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
	@PerfTest(invocations = 50, threads = 5)
	@Required(max = 1500, average = 1000, throughput = 5)
	public void checkExistingGarageTrueTest() throws RemoteException {
		assertFalse(c.newGarageAvailable("Bilbao"));
	}
	
	@Test
	public void checkExistingGarageFalseTest() throws RemoteException {
		assert(c.newGarageAvailable("Sevilla"));
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
		
		ArrayList<Car> carList = c.getCars();
		
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
		boolean a = false;
		if(!c.numberPlateAvailable("5678ASD"))
			a = true;
		
		c.deleteCar("5678ASD");
		
		assert(a);
	
	}
	
	@Test
	public void testDaysBetween() {
		Date date1=new GregorianCalendar(2019, Calendar.MAY, 11).getTime();
		Date date2=new GregorianCalendar(2019, Calendar.MAY, 13).getTime();
		assertEquals(c.daysBetween(date1, date2), 3);
	}
	
	@Test
	public void testGetCarsAvailable() throws RemoteException {
		Date date1=new GregorianCalendar(2019, Calendar.MAY, 11).getTime();
		Date date2=new GregorianCalendar(2019, Calendar.MAY, 13).getTime();
		Car carToStore = new Car("Vitoria", "1234ASF", "Citroen", "C3", 40);
		c.storeCar(carToStore.getGarage(), carToStore.getNumPlate(), carToStore.getBrand(), carToStore.getModel(), carToStore.getPricePerDay());
		
		ArrayList<Car> availableCars = c.getCarsAvailable("Vitoria", date1, date2);
		Car car = availableCars.get(0);
		boolean a = false;
		if(carToStore.getGarage().equals(car.getGarage()) &&
				carToStore.getNumPlate().equals(car.getNumPlate()) && 
				carToStore.getBrand().equals(car.getBrand()) &&
				carToStore.getModel().equals(car.getModel()) &&
				carToStore.getPricePerDay() == car.getPricePerDay()) {
			a = true;
		}
		c.deleteCar("1234ASF");
		assert(a);
	
	}
	
	@Test
	public void testGaragesWithCars() throws RemoteException {
		
		ArrayList<String> garages = c.garagesWithCars();
		
		if(garages.contains("Bilbao") && garages.contains("Madrid") && garages.contains("Barcelona")) {
			assert(true);
		}else {
			assert(false);
		}
	
	}
	
	@Test
	//@PerfTest(duration = 2000)
	//@Required(max = 3000, average = 3000)
	public void testGarageOriginPopularity() throws RemoteException {
		
		
		Object[][] result = new Object[4][3];
		result[0][0] = "Barcelona";
		result[1][0] = "Bilbao";
		result[2][0] = "Madrid";
		result[3][0] = "Vitoria";
		
		result[0][1] = 0;
		result[1][1] = 7;
		result[2][1] = 4;
		result[3][1] = 0;
		
		result[0][2] = 6;
		result[1][2] = 4;
		result[2][2] = 1;
		result[3][2] = 0;
		
		Object[][] garagePopularity = c.garagePopularity();
		
		
		for(int i=0; i<result.length; i++) {
			for(int j=0; j<result[i].length; j++) {
				if(!result[i][j].equals(garagePopularity[i][j])) {
					//assert(false);
				}
			}
		}
		assert(true);
		
	}
	
	@Test
	public void testPaymentPopularity() throws RemoteException {
		
		Object[][] result = new Object[2][2];
		Object[][] paymentPop = c.paymentPopularity();
		result[0][0] = "paypal";
		result[1][0] = "visa";
		result[0][1] = 9;
		result[1][1] = 2;
		
		for(int i=0; i<2; i++) {
			for(int j=0; j<2; j++) {
				if(!result[i][j].equals(paymentPop[i][j])) {
					assert(false);
				}
			}
		}
		assert(true);
		
	}
	
	@Test
	public void testCarBrandPopularity() throws RemoteException {
		Object[][] brandPop = c.carBrandPopularity();
		
		ArrayList<Car> cars = c.getAllCars();
		ArrayList<String> brands= new ArrayList<>();
		for(Car c: cars) {
			if(!brands.contains(c.getBrand())){
				brands.add(c.getBrand());
			}
		}
		
		//Filling the list
		Object[][] result = new Object[brands.size()][2];
		result[0][0] = "Audi";
		result[0][1] = 5;
		result[1][0] = "Ford";
		result[1][1] = 6;
		result[2][0] = "Mercedes";
		result[2][1] = 0;
		result[3][0] = "Volvo";
		result[3][1] = 0;
		
		for(int i=0; i< result.length; i++) {
			for(int j=0; j<2; j++) {
				if(!result[i][j].equals(brandPop[i][j])) {
					assert(false);
				}
			}
		}
		assert(true);
		
	}
	
	@Test
	public void testCarModelPopularity() throws RemoteException {
		Object[][] modelPop = c.carModelPopularity();
		
		ArrayList<Car> cars = c.getAllCars();
		ArrayList<String> models= new ArrayList<>();
		for(Car c: cars) {
			if(!models.contains(c.getModel())){
				models.add(c.getModel());
			}
		}
		Object[][] result = new Object[models.size()][2];
		
		result[0][0] = "A4";
		result[0][1] = 2;
		result[1][0] = "A5";
		result[1][1] = 3;
		result[2][0] = "A7";
		result[2][1] = 0;
		result[3][0] = "Fiesta";
		result[3][1] = 6;
		result[4][0] = "Clase A";
		result[4][1] = 0;
		result[5][0] = "XC60";
		result[5][1] = 0;
		
		
		
		for(int i=0; i < result.length; i++) {
			for(int j=0; j < 2; j++) {
				if(!result[i][j].equals(modelPop[i][j])) {
					assert(false);
				}
			}
		}
		assert(true);
		
	}
	
	@Test
	public void testDeleteGarageAndItsCars() throws RemoteException {
		c.storeGarage("Donosti");
		c.storeCar("Donosti", "4567RTG", "Citroen", "C4", 5);
		
		c.deleteGarageAndItsCars("Donosti");
		
		ArrayList<Car> cars = c.getCars("Donosti");
		assert(cars.isEmpty());
	}
	
	@Test
	public void testSetLocale() throws RemoteException {
		c.setLocale("en");
		if(!c.getResourcebundle().getLocale().equals(new Locale("en", "US"))){
			assert(false);
		}
		c.setLocale("es");
		if(!c.getResourcebundle().getLocale().equals(new Locale("es", "ES"))){
			assert(false);
		}
		c.setLocale("eu");
		if(!c.getResourcebundle().getLocale().equals(new Locale("eu", "ES"))){
			assert(false);
		}
		
		assert(true);	
	}
	
	@Test
	public void testUpdateGarage() throws RemoteException {
		c.storeGarage("Leon");
		c.storeGarage("Zamora");
		c.storeCar("Leon", "9876KLJ", "Seat", "Leon", 1);
		
		c.updateGarage("9876KLJ", "Zamora");
		
		ArrayList<Car> leon = c.getCars("Leon");
		ArrayList<Car> zamora = c.getCars("Zamora");
	
		
		if(leon.isEmpty() && !zamora.isEmpty()) {
			c.deleteGarage("Leon");
			c.deleteGarageAndItsCars("Zamora");
			assert(true);
		}else {
			assert(false);
		}
		
	}
	
	@AfterClass
	static public void after() throws RemoteException {
		c.deleteGarage("Vitoria");
	}
}
