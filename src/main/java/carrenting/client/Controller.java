package carrenting.client;

import java.net.MalformedURLException;



import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import carrenting.client.gui.ClientDataGUI;
import carrenting.client.gui.PaymentGUI;

import carrenting.client.gui.SelectCarGUI;
import carrenting.client.gui.StaffPanelGUI;
import carrenting.client.gui.WelcomeGUI;
import carrenting.server.jdo.Car;
import carrenting.server.jdo.DataDAO;
import carrenting.server.jdo.Garage;
import carrenting.server.jdo.Rent;
import carrenting.server.jdo.Staff;





public class Controller{
	final  Logger logger = LoggerFactory.getLogger(Controller.class);
	private Rent rent = new Rent(null, null, null, null, null, null, null, 0);
	private ResourceBundle myBundle; //el que gestiona los idiomas
	private Locale currentLocale; //variable para decirle que idioma queremos
	private ArrayList<Rent> rents = new ArrayList<>();
	private Date date6 = new GregorianCalendar(2019, Calendar.AUGUST, 21).getTime();
	private Date date5 = new GregorianCalendar(2019, Calendar.AUGUST, 26).getTime();
	private String backgroundColor = "#FCFEFF";
	private String panelColor= "#F7FFFF";
//	private String backgroundColor = "#FABADA";





	/**
	 * Calls the main constructor of the controller class.
	 * Sets the Locale variable and the Service Locator.
	 * Finally, starts the GUI
	 * 
	 * @param args IP, Port Name of the server and Locale
	 * @throws RemoteException
	 * @throws MalformedURLException
	 * @throws NotBoundException
	 */
	public Controller(String[] args) throws RemoteException, MalformedURLException, NotBoundException{
		if(args[3].equals("en")){
			currentLocale = new Locale("en", "US");
		}else if(args[3].equals("es")){
			currentLocale = new Locale("es", "ES");
		}else if(args[3].equals("eu")){
			currentLocale = new Locale("eu", "ES");
		}
		myBundle = ResourceBundle.getBundle("lang/translations", currentLocale);

		RMIServiceLocator.setService(args[0], args[1], args[2]);
		this.getRents();
		new WelcomeGUI(this, this.rent);

	}
	
	/**
	 * Other Constructor for when using tests.
	 * 
	 * @param ip IP address of the server
	 * @param port Port of the server
	 * @param serviceName Name of the server
	 * @throws MalformedURLException
	 * @throws RemoteException
	 * @throws NotBoundException
	 */
	public Controller(String ip, String port, String serviceName) throws MalformedURLException, RemoteException, NotBoundException {		
		RMIServiceLocator.setService(ip, port, serviceName);	
	}
	
	public void storeRent(String userId, String numberPlate, Date startingDate, Date finishingDate, String garageOrigin,
	String garageDestination, String paymentSystem, double totalPrice) throws RemoteException{
		RMIServiceLocator.getService().storeRent(userId,  numberPlate,  startingDate,  finishingDate,  garageOrigin,
				 garageDestination,  paymentSystem,  totalPrice);
	}

	/**
	 * Calls the server to store the given garage.
	 * 
	 * @param location Where is the garage
	 * @throws RemoteException
	 */
	public void storeGarage(String location) throws RemoteException {
		RMIServiceLocator.getService().storeGarage(location);
	}
	
	/**
	 * Calls the server to delete the given garage.
	 * 
	 * @param garage Where cars are stored
	 * @throws RemoteException
	 */
	public void deleteGarage(String garage) throws RemoteException {
		RMIServiceLocator.getService().deleteGarage(garage);
	}
	
	/**
	 * Calls the server to delete the given garage and its cars.
	 * 
	 * @param garage Where cars are stored
	 * @throws RemoteException
	 */
	public void deleteGarageAndItsCars(String garage) throws RemoteException {
		ArrayList<Car>carsToDelete= this.getCars(garage);
		for (Car c: carsToDelete) {
			this.deleteCar(c.getNumPlate());	
		}
		RMIServiceLocator.getService().deleteGarage(garage);
	}
	
	
	/**
	 * Asks the server for the list of garages.
	 * 
	 * @return List of all garages
	 * @throws RemoteException
	 */
	public ArrayList<String> getGarages() throws RemoteException{
		return RMIServiceLocator.getService().getGarages();
	}
	
	/**
	 * Asks the server for the cars in the given garage.
	 * 
	 * @param garage Where cars are stored
	 * @return List of cars in the garage
	 * @throws RemoteException
	 */
	public ArrayList<Car> getCars(String garage) throws RemoteException{
		return RMIServiceLocator.getService().getCars(garage);	
	}
	
	/**
	 * Asks the server if the given user can log in or not to the system
	 * 
	 * @param user Username of the staff
	 * @param password Password of the staff
	 * @param type Type of the staff
	 * @return
	 * @throws RemoteException
	 */
	public boolean loginStaff(String user, String password, String type) throws RemoteException, NullPointerException{
		return RMIServiceLocator.getService().loginStaff(user, password, type);	
	}
	
	/**
	 * Registers a user
	 * 
	 * @param username Name of the user
	 * @throws RemoteException
	 */
	public void register ( String username) throws RemoteException {
		RMIServiceLocator.getService().registerUser(username);
	}
	
	/**
	 * Asks the server for all the car rents
	 * 
	 * @return List of Rents
	 * @throws RemoteException
	 */
	public ArrayList<Rent> getRents() throws RemoteException {
		rents=RMIServiceLocator.getService().getRents();
		return rents;
	}

	/**
	 * Calculates the days between the 2 given dates
	 * 
	 * @param d1 Date 1
	 * @param d2 Date 2
	 * @return
	 */
    public int daysBetween(Date d1, Date d2){
        return (int)( ((d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24))+1);
    }

    /**
     * Stores a car in the given garage
     * 
     * @param garage Garage of the car
     * @param numberPlate Car identification
     * @param brand Brand of the car
     * @param model Model of the car
     * @param pricePerDay Price per day of the car
     * @throws RemoteException
     */
	public void storeCar(String garage, String numberPlate, String brand, String model, double pricePerDay) throws RemoteException{
		RMIServiceLocator.getService().storeCar( garage, numberPlate, brand, model, pricePerDay);
	}
	
	/**
	 * Deletes a car
	 * 
	 * @param numberPlate 
	 * @throws RemoteException
	 */
	public void deleteCar(String numberPlate) throws RemoteException {
		RMIServiceLocator.getService().deleteCar(numberPlate);
	}
	
	/**
	 * Asks the server for the list of cars in the renting service
	 * 
	 * @return List of cars
	 * @throws RemoteException
	 */
	public ArrayList<Car> getAllCars() throws RemoteException{
		ArrayList<Car> cars = new ArrayList<>();
		cars.addAll(RMIServiceLocator.getService().getAllCars());
		return cars;	
	}
	
	
	
	
	/**
	 * Asks the server for a car given the number plate
	 * 
	 * @param numPlate
	 * @return Car
	 * @throws RemoteException
	 */
	public Car getCar(String numPlate) throws RemoteException{
		return RMIServiceLocator.getService().getCar(numPlate);
	}
	
	/**
	 * Asks the server for the list of cars that are in an active garage
	 * 
	 * @return List of cars
	 * @throws RemoteException
	 */
	public ArrayList<Car> getCars() throws RemoteException{
		ArrayList<String> garages = new ArrayList<>();
		ArrayList<Car> cars = new ArrayList<>();
		garages = getGarages();
		for(String garage: garages) {
			cars.addAll(getCars(garage));
		}
		return cars;	
	}
	
	/**
	 * Asks the server for the whole list of number plates
	 * 
	 * @return List of number plates
	 * @throws RemoteException
	 */
	public ArrayList<String> getAllNumPlates() throws RemoteException{
		ArrayList<Car> cars = new ArrayList<>();
		cars= this.getCars();
		ArrayList<String> numPlates = new ArrayList<>();
		for(Car car: cars) {
			numPlates.add(car.getNumPlate());
		}
		return numPlates;
	}
	
	/**
	 * Asks the server for the list of garages with cars in it
	 * 
	 * @return List of garages
	 * @throws RemoteException
	 */
	public ArrayList<String> garagesWithCars() throws RemoteException{
		ArrayList<String> garages = new ArrayList<>();
		ArrayList<Car> cars = new ArrayList<>();
		cars=this.getCars();
		for(Car car: cars) {
			if(!(garages.contains(car.getGarage()))) {
				garages.add(car.getGarage());
			}	
		}
		return garages;
	}
	
	/**
	 * Asks the server of the list of cars available to rent
	 * 
	 * @param garageOrigin Garage of Origin
	 * @param startingDate Date to start renting the car
	 * @param finishingDate Date to return the car
	 * @return List of cars available to rent
	 * @throws RemoteException
	 */
	public ArrayList<Car> getCarsAvailable(String garageOrigin, Date startingDate, Date finishingDate) throws RemoteException{
		ArrayList<Car> carsAvailable = new ArrayList<>();
		ArrayList<String>carsNotAvailable = new ArrayList<>();
		ArrayList<Car>allCars= new ArrayList<>();
		allCars=getCars(garageOrigin);
		for(int i=0; i<rents.size(); i++) {
			if (rents.get(i).getGarageOrigin().equalsIgnoreCase(garageOrigin)) {
				if(!(finishingDate.before(rents.get(i).getStartingDate()) || startingDate.after(rents.get(i).getFinishingDate()))){
					carsNotAvailable.add(rents.get(i).getNumberPlate());
					logger.info("NOT AVAILABLE"+ getCar(rents.get(i).getNumberPlate()).toString());
				}
			}	
		}
		for (Car car: allCars) {
			if(!(carsNotAvailable.contains(car.getNumPlate()))) {
				carsAvailable.add(car);
			}
		}
		logger.debug("Cars available!!");
		for (Car car: carsAvailable) {
			logger.debug(car.toString());
		}
		return carsAvailable;
	}
	
	/**
	 * Returns the statistics of all garages 
	 * 
	 * @return List of garages and its statistics
	 * @throws RemoteException
	 */
	public Object[][] garagePopularity() throws RemoteException{
		ArrayList<Rent> rents = new ArrayList<>();
		ArrayList<String> garages= new ArrayList<>();
		garages= getGarages();

		//Filling the list
		Object[][] garagePopularity = new Object[garages.size()][3];
		for(int i=0; i<garages.size(); i++) {
			garagePopularity[i][0] =(Object)garages.get(i);
			garagePopularity[i][1] =0;	
			garagePopularity[i][2] =0;	
		}
		rents= this.getRents();
		
		for(Rent rent: rents) {
			String rentGarageOrigin =rent.getGarageOrigin();
			for(int i=0; i<garagePopularity.length; i++) {
				if (garagePopularity[i][0].equals(rentGarageOrigin)){
					garagePopularity[i][1] = (int)garagePopularity[i][1]+1;
					
				}	
			}
		}
		for(Rent rent: rents) {
			String rentGarageDestination =rent.getGarageDestination();
			for(int i=0; i<garagePopularity.length; i++) {
				if (garagePopularity[i][0].equals(rentGarageDestination)){
					garagePopularity[i][2] = (int)garagePopularity[i][2]+1;
					
				}	
			}
		}
		
		return garagePopularity;
	}
	
	
	//TODO
	/**
	 * Returns the statistics of the different payment methods
	 * @return List with the usage of each payment method
	 * @throws RemoteException
	 */
	public Object[][] paymentPopularity() throws RemoteException{
		ArrayList<Rent> rents = new ArrayList<>();
		ArrayList<String> paymentTypes = new ArrayList<>();
		paymentTypes.add("paypal");
		paymentTypes.add("visa");
		//Filling the list
		Object[][] paymentPop = new Object[2][2];
		for(int i=0; i<paymentPop.length; i++) {
			paymentPop[i][0] =(Object)paymentTypes.get(i);
			paymentPop[i][1] =0;	
		}
		
		try {
			rents= this.getRents();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		for(Rent rent: rents) {
			String rentPayment =rent.getPaymentSystem();
			for(int i=0; i<paymentPop.length; i++) {
				if (((String) paymentPop[i][0]).equalsIgnoreCase(rentPayment)){
					paymentPop[i][1] = (int)paymentPop[i][1]+1;
				}	
			}
		}
		return paymentPop;	
	}
	
	//TODO
	/**
	 * Statistics of the car models
	 * @return List of the statistics of the car models
	 * @throws RemoteException
	 */
	public Object[][] carModelPopularity() throws RemoteException{
		ArrayList<Rent> rents = new ArrayList<>();
		ArrayList<Car> cars = new ArrayList<>();
		cars= this.getAllCars();
		ArrayList<String> models= new ArrayList<>();
		for(Car c: cars) {
			if(!models.contains(c.getModel())){
				models.add(c.getModel());
			}
		}
		//Filling the list
		Object[][] carModelPopularity = new Object[models.size()][2];
		for(int i=0; i<carModelPopularity.length; i++) {
			carModelPopularity[i][0] =(Object)models.get(i);
			carModelPopularity[i][1] =0;	
		}

		rents= this.getRents();
				
		for(Rent rent: rents) {
			Car car= this.getCar(rent.getNumberPlate());
			for(int i=0; i<carModelPopularity.length; i++) {
				if (((String) carModelPopularity[i][0]).equalsIgnoreCase(car.getModel())){
					carModelPopularity[i][1] = (int)carModelPopularity[i][1]+1;
				}	
			}
		}

		return carModelPopularity;	
	}
	
	/**
	 * Gets the statistics of the car brands
	 * @return List of the brands of the cars
	 * @throws RemoteException
	 */
	public Object[][] carBrandPopularity() throws RemoteException{
		ArrayList<Rent> rents = new ArrayList<>();
		ArrayList<Car> cars = this.getAllCars();
		ArrayList<String> brands= new ArrayList<>();
		for(Car c: cars) {
			if(!brands.contains(c.getBrand())){
				brands.add(c.getBrand());
			}
		}
		//Filling the list
		Object[][] carBrandPopularity = new Object[brands.size()][2];
		for(int i=0; i<carBrandPopularity.length; i++) {
			carBrandPopularity[i][0] =(Object)brands.get(i);
			carBrandPopularity[i][1] =0;	
		}
		
		rents= this.getRents();
	
		
		for(Rent rent: rents) {
			Car car= this.getCar(rent.getNumberPlate());
			for(int i=0; i<carBrandPopularity.length; i++) {
				if (((String) carBrandPopularity[i][0]).equalsIgnoreCase(car.getBrand())){
					carBrandPopularity[i][1] = (int)carBrandPopularity[i][1]+1;
					
				}	
			}
		}
		return carBrandPopularity;	
	}
	
	
	
	/**
	 * Checks if a number plate already exists
	 * 
	 * @param numPlate number plate that wants to be checked if it already exists or not
	 * @return True if the number plate doesn't exist, False otherwise
	 * @throws RemoteException
	 */
	/**
	 * Checks if the given number plate is free to use
	 * @param numPlate
	 * @return true if it's free, false of otherwise
	 * @throws RemoteException
	 */
	public boolean numberPlateAvailable(String numPlate) throws RemoteException {
		boolean numPlateOK= true;
		ArrayList<String> numPlates= new ArrayList<>();
		numPlates= this.getAllNumPlates();
		
		for(String nP : numPlates){
			if(nP.equalsIgnoreCase(numPlate)) {
				numPlateOK=false;
			}
		}
		return numPlateOK;
	}
	
	/**
	 * Checks if a Garage already exists
	 * @param Location Location of the garage that wants to be checked if it already exists or not
	 * @return Returns true if the garage does not already exist, False otherwise
	 * @throws RemoteException
	 */
	public boolean newGarageAvailable(String location) throws RemoteException {
		boolean locationOK= true;
		ArrayList<String> locations= new ArrayList<>();
		locations= this.getGarages();
		for(String loc : locations){
			if(loc.equals(location)) {
				locationOK=false;
			}
		}
		
		return locationOK;
	}
	
	/**
	 * Returns the logger
	 * @return Logger
	 */
	public Logger getLogger() {
		return logger;
	}
	
	/**
	 * Returns the ResourceBundle
	 * @return ResourceBundle
	 */
	public ResourceBundle getResourcebundle() {
		return myBundle;
	}
	
	/**
	 * Sets the locale to the given parameter
	 * @param locale
	 */
	public void setLocale(String locale){
			if(locale.equals("en")){
				currentLocale = new Locale("en", "US");
			}else if(locale.equals("es")){
				currentLocale = new Locale("es", "ES");
			}else if(locale.equals("eu")){
				currentLocale = new Locale("eu", "ES");
			}
		myBundle = ResourceBundle.getBundle("lang/translations", currentLocale);
	}
	
	/**
	 * Changes one car to another garage
	 * @param numberPlate Number plate of the car
	 * @param newGarage New garage to move the car
	 * @throws RemoteException
	 */
	public void updateGarage(String numberPlate, String newGarage) throws RemoteException {
		Car car= this.getCar(numberPlate);
		this.deleteCar(numberPlate);
		this.storeCar(newGarage, car.getNumPlate(), car.getBrand(), car.getModel(), car.getPricePerDay());
	}
	
	/**
	 * Deletes one rent
	 * @param numPlate Number plate of the car
	 * @param date Date of the start of the rent
	 * @throws RemoteException
	 */
	public void deleteRent(String numPlate, Date date) throws RemoteException {
		RMIServiceLocator.getService().deleteRent(numPlate, date);
	}

	public String getBackgroundColor() {
		return backgroundColor;
	}
	
	public String getPanelColor() {
		return panelColor;
	}

	public boolean checkPayment(String paymentSystem, double price) throws RemoteException {
		return RMIServiceLocator.getService().paymentSystem(paymentSystem,price);
	}
	
	
	/**
	 * Main method of the client and starting point of the program
	 * @param args
	 * @throws RemoteException
	 * @throws MalformedURLException
	 * @throws NotBoundException
	 */
	public static void main(String[] args) throws RemoteException, MalformedURLException, NotBoundException {
		new Controller(args);
	}

	
	
}
