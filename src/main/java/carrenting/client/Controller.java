package carrenting.client;

import java.net.MalformedURLException;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.ResourceBundle;

import carrenting.client.gui.WelcomeGUI;
import carrenting.server.jdo.Car;
import carrenting.server.jdo.DataDAO;
import carrenting.server.jdo.Rent;



public class Controller{

	private Rent rent = new Rent(null, null, null, null, null, null, null, 0);
	private ResourceBundle myBundle; //el que gestiona los idiomas
	private Locale currentLocale; //variable para decirle que idioma queremos
	private ArrayList<Rent> rents = new ArrayList<>();
	private ArrayList<Car> carsAvailable = new ArrayList<>();
	private Date date6 = new GregorianCalendar(2019, Calendar.AUGUST, 21).getTime();
	private Date date5 = new GregorianCalendar(2019, Calendar.AUGUST, 26).getTime();
	
	public Controller(String[] args) throws RemoteException, MalformedURLException, NotBoundException{
		
		//asigno la variable currentLocale a uno de los idiomas que tenemos
		if(args[3].equals("en")){
			currentLocale = new Locale("en", "EU");
		}else if(args[3].equals("es")){
			currentLocale = new Locale("es", "ES");
		}else if(args[3].equals("eu")){
			currentLocale = new Locale("eu", "ES");
		}
		//le paso la ruta donde se encuentran los archivos de los idiomas y el currentLocale
		myBundle = ResourceBundle.getBundle("lang/translations", currentLocale);
//		System.out.println("Example of a text in english: "+ myBundle.getString("starting_msg")); //esto coge el texto que tiene la string que le paso. (Debería salir: Starting...)
		
		RMIServiceLocator.setService(args[0], args[1], args[2]);
		
		this.getRents();
//		getCars("Bilbao");
		//getGarageDestination("0352HTQ");
		//Inicializar GUI
		System.out.println("controller");		
		getCars("Bilbao");
		System.out.println("CONTROLLER 2ª VEZ");
		getCars("Bilbao");
		new WelcomeGUI(this, this.rent);

		carsAvailable=getCarsAvailable("Bilbao", date6, date5);
		

//		System.out.println("Searching by numplate" +getCar("0352HTQ").toString());
	}
	
	public void storeGarage(String location) throws RemoteException {
		RMIServiceLocator.getService().storeGarage(location);
	}
	
	public void deleteGarage(String garage) throws RemoteException {
		RMIServiceLocator.getService().deleteGarage(garage);
	}
	
	public ArrayList<String> getGarages() throws RemoteException{
		return RMIServiceLocator.getService().getGarages();
	}
	
	public ArrayList<Car> getCars(String garage) throws RemoteException{
		ArrayList<Car> cars = new ArrayList<>();
		cars = RMIServiceLocator.getService().getCars(garage);
		System.out.println("CONTROLLER");
//		for(Car car:cars){
//			System.out.println(car.toString());
//		}
		return cars;
		//return RMIServiceLocator.getService().getCars(garage);
		
	}
	
	public boolean loginStaff(String user, String password, String type) throws RemoteException {
		return RMIServiceLocator.getService().loginStaff(user, password, type);	
	}
	
	public void register ( String username) throws RemoteException {
		RMIServiceLocator.getService().registerUser(username);
	}

	public Rent getLatestRent(String numberPlate) throws RemoteException{
		ArrayList<Rent> rentsByNumPlate = new ArrayList<>();
		Date currentDate= new Date();
		Date latestDate = new GregorianCalendar(1970, Calendar.FEBRUARY, 11).getTime();
		for (Rent rent: rents) {
			//System.out.println(rent);
			if(rent.getNumberPlate().equals(numberPlate)) {
				//System.out.println(rent);
				rentsByNumPlate.add(rent);
			}
		}
		for(int i=0; i<rentsByNumPlate.size() ; i++) {
			currentDate= rentsByNumPlate.get(i).getStartingDate();
//			System.out.println("CURRENT DATE  " + currentDate);
//			System.out.println("LATEST DATE   " +latestDate);
			if(currentDate.compareTo(latestDate)> 0) {
//				System.out.println(currentDate +"es más reciente que" + latestDate);
				latestDate= currentDate;

			}	
		}
		for(Rent rent: rentsByNumPlate) {
			//System.out.println(rent);
			if(latestDate.equals(rent.getStartingDate())) {
				//System.out.println(latestDate);
				//System.out.println(rent.getGarageDestination());
				return rent;
			}
		}
		return null;
	}
	
	public ArrayList<Rent> getRents() throws RemoteException {
		rents=RMIServiceLocator.getService().getRents();
//		for(Rent rent:rents){
//			System.out.println(rent.toString());
//		}
		return rents;
	}

//TODO
    public int daysBetween(Date d1, Date d2){
        return (int)( ((d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24))+1);
}

	public void storeCar(String garage, String numberPlate, String brand, String model, int pricePerDay) throws RemoteException{
		RMIServiceLocator.getService().storeCar( garage, numberPlate, brand, model, pricePerDay);
	}
	
	public void deleteCar(String numberPlate) throws RemoteException {
		RMIServiceLocator.getService().deleteCar(numberPlate);
	}
	

	public Car getCar(String numPlate) throws RemoteException{
		return RMIServiceLocator.getService().getCar(numPlate);
	}
	
	public ArrayList<Car> getCarsAvailable(String garageOrigin, Date startingDate, Date finishingDate) throws RemoteException{
		ArrayList<Car> carsAvailable = new ArrayList<>();
		ArrayList<String>carsNotAvailable = new ArrayList<>();
		ArrayList<Car>allCars= new ArrayList<>();
		allCars=getCars(garageOrigin);
//		carsAvailable= getCars("Bilbao");
		for(int i=0; i<rents.size(); i++) {
//			System.out.println(rent.toString());
			if (rents.get(i).getGarageOrigin().equalsIgnoreCase(garageOrigin)) {
				if(!(finishingDate.before(rents.get(i).getStartingDate()) || startingDate.after(rents.get(i).getFinishingDate()))){
//					System.out.println("Start date " + startingDate + "\n Finish date " + finishingDate + 
//							"\n startingDateRENT" +rents.get(i).getStartingDate()+ 
//							"\n finishingdateRENT" +rents.get(i).getFinishingDate());
					carsNotAvailable.add(rents.get(i).getNumberPlate());
					System.out.println("NOT AVAILABLE");
					System.out.println(getCar(rents.get(i).getNumberPlate()));
				}

			}
			
			
		}
		
		for (Car car: allCars) {
			if(!(carsNotAvailable.contains(car.getNumPlate()))) {
				carsAvailable.add(car);
			}
		}
		
		
//		for(Car carAv: carsAvailable) {
//			for (Car carNotAv: carsNotAvailable) {
//				if (carAv.getNumPlate().equals(carNotAv.getNumPlate())){
//					System.out.println("Removing this car " + carAv.toString());
//					//carsAvailable.remove(carAv);
//				}
//			}
//		}
		System.out.println("Cars available!!");
		for (Car car: carsAvailable) {
			System.out.println(car.toString());
		}

		return carsAvailable;
	}
	
	public ResourceBundle getResourcebundle() {
		return myBundle;
}
	
	
	public static void main(String[] args) throws RemoteException, MalformedURLException, NotBoundException {
		new Controller(args);
	}
	
	
}