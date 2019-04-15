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
import carrenting.server.jdo.Rent;



public class Controller{

	private Rent rent = new Rent(null, null, null, null, null, null, null, 0);
	private ResourceBundle myBundle; //el que gestiona los diomas
	private Locale currentLocale; //variable para decirle que idioma queremos
	private ArrayList<Rent> rents = new ArrayList<>();
	
	public Controller(String[] args) throws RemoteException, MalformedURLException, NotBoundException{
		
		//asigno la variable currentLocale a uno de los idiiomas que tenemos
		if(args[3].equals("en")){
			currentLocale = new Locale("en", "EU");
		}else if(args[3].equals("es")){
			currentLocale = new Locale("es", "ES");
		}else if(args[3].equals("eu")){
			currentLocale = new Locale("eu", "ES");
		}
		//le paso la ruta donde se encuentran los archivos de los idiomas y el currentLocale
//		myBundle = ResourceBundle.getBundle("src/main/resources", currentLocale);
//		System.out.println("Example of a text in english: "+ myBundle.getString("starting_msg")); //esto coge el texto que tiene la string que le paso. (Debería salir: Starting...)
		
		RMIServiceLocator.setService(args[0], args[1], args[2]);
		
		getRents();
		//getCars("Bilbao", 1);
		//getGarageDestination("0352HTQ");
		//Inicializar GUI
		new WelcomeGUI(this, this.rent);
	}
	
	public Controller() {
		
	}

	public void storeGarage(String location) throws RemoteException {
		RMIServiceLocator.getService().storeGarage(location);
	}
	
	public ArrayList<String> getGarages() throws RemoteException{
		return RMIServiceLocator.getService().getGarages();
	}
	
	public ArrayList<Car> getCars(String garage,int availability) throws RemoteException{
		ArrayList<Car> cars = new ArrayList<>();
		cars = RMIServiceLocator.getService().getCars(garage,availability);
//		System.out.println("CONTROLLER");
//		for(Car car:cars){
//			System.out.println(car.toString());
//		}
		return cars;
		//return RMIServiceLocator.getService().getCars(garage,availability);
		
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

	public void updateAvailability(String numberPlate, int newAvailability, String garageDestination) throws RemoteException{
		RMIServiceLocator.getService().updateAvailability(numberPlate, newAvailability, garageDestination);
	}

    public int daysBetween(Date d1, Date d2){
        return (int)( (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
}

	public void storeCar(int availability, String garage, String numberPlate, String brand, String model, int pricePerDay) throws RemoteException{
		RMIServiceLocator.getService().storeCar(availability, garage, numberPlate, brand, model, pricePerDay);
	}
	
	public void deleteCar(String numberPlate) throws RemoteException {
		RMIServiceLocator.getService().deleteCar(numberPlate);
	}
	
	public static void main(String[] args) throws RemoteException, MalformedURLException, NotBoundException {
		new Controller(args);
	}
	
}