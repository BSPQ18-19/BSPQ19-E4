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
	private ResourceBundle myBundle; //el que gestiona los diomas
	private Locale currentLocale; //variable para decirle que idioma queremos
	private ArrayList<Rent> rents = new ArrayList<>();
	private ArrayList<Car> carsAvailable = new ArrayList<>();
	private Date date6 = new GregorianCalendar(2021, Calendar.AUGUST, 20).getTime();
	private Date date5 = new GregorianCalendar(2021, Calendar.AUGUST, 25).getTime();
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
		
		this.getRents();
//		getCars("Bilbao");
		//getGarageDestination("0352HTQ");
		//Inicializar GUI
//		new WelcomeGUI(this, this.rent);

		this.getCarsAvailable("Bilbao", date6, date5);
//		for (Car car: carsAvailable) {
//			System.out.println(car.toString());
//		}

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
		for(Car car:cars){
			System.out.println(car.toString());
		}
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


    public int daysBetween(Date d1, Date d2){
        return (int)( (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
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
		for(Rent rent: rents) {
//			System.out.println(rent.getGarageOrigin());
			if (rent.getGarageOrigin().equalsIgnoreCase(garageOrigin)) {
//				if(!(rent.getStartingDate().after(startingDate) && rent.getStartingDate().before(finishingDate)) ||
//					(rent.getFinishingDate().after(startingDate) && rent.getFinishingDate().before(finishingDate)))	{
//					try {
//						carsAvailable.add(getCar(rent.getNumberPlate()));
//
//						
//					} catch (RemoteException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				}
				if(!(startingDate.after(rent.getStartingDate())&& startingDate.before(rent.getFinishingDate())) ){
					
				}
				
			}
		}
		for(Car car: carsAvailable) {
			System.out.println(car.toString());
		}
		return null;

	}
	
	
	public static void main(String[] args) throws RemoteException, MalformedURLException, NotBoundException {
		new Controller(args);
	}
	
	
}