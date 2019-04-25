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

import carrenting.client.gui.ClientDataGUI;
import carrenting.client.gui.PaymentGUI;

import carrenting.client.gui.SelectCarGUI;
import carrenting.client.gui.StaffPanelGUI;
import carrenting.client.gui.WelcomeGUI;
import carrenting.server.jdo.Car;
import carrenting.server.jdo.Garage;
import carrenting.server.jdo.Rent;
import carrenting.server.jdo.Staff;



public class Controller{

	private Rent rent = new Rent(null, null, null, null, null, null, null, 0);
	private ResourceBundle myBundle; //el que gestiona los idiomas
	private Locale currentLocale; //variable para decirle que idioma queremos
	private ArrayList<Rent> rents = new ArrayList<>();
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
//		new WelcomeGUI(this, this.rent);
//		new ClientDataGUI(this, this.rent);
//		new PaymentGUI(this, this.rent);
		new StaffPanelGUI(this, "employee", this.rent);
//		new RemoveCarGUI(this, "admin", this.rent);
//		new AddCarGUI(this, "admin", this.rent);
//		deleteCar("8765BCN");
//		garagesWithCars();

	}
	
	public Controller(String ip, String port, String serviceName) throws MalformedURLException, RemoteException, NotBoundException {
		
		RMIServiceLocator.setService(ip, port, serviceName);
		
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
//		ArrayList<Car> cars = new ArrayList<>();
//		cars = RMIServiceLocator.getService().getCars(garage);
//		return cars;
		return RMIServiceLocator.getService().getCars(garage);	
	}
	
	public boolean loginStaff(String user, String password, String type) throws RemoteException {
		return RMIServiceLocator.getService().loginStaff(user, password, type);	
	}
	
	public void register ( String username) throws RemoteException {
		RMIServiceLocator.getService().registerUser(username);
	}
	
	public ArrayList<Rent> getRents() throws RemoteException {
		rents=RMIServiceLocator.getService().getRents();
//		for(Rent rent:rents){
//			System.out.println(rent.toString());
//		}
		return rents;
	}

    public int daysBetween(Date d1, Date d2){
        return (int)( ((d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24))+1);
}

	public void storeCar(String garage, String numberPlate, String brand, String model, double pricePerDay) throws RemoteException{
		RMIServiceLocator.getService().storeCar( garage, numberPlate, brand, model, pricePerDay);
	}
	
	public void deleteCar(String numberPlate) throws RemoteException {
		RMIServiceLocator.getService().deleteCar(numberPlate);
	}
	
	public Car getCar(String numPlate) throws RemoteException{
		return RMIServiceLocator.getService().getCar(numPlate);
	}
	
	public ArrayList<Car> getAllCars() throws RemoteException{
		ArrayList<String> garages = new ArrayList<>();
		ArrayList<Car> cars = new ArrayList<>();
		garages = getGarages();
		for(String garage: garages) {
			cars.addAll(getCars(garage));
		}
		return cars;	
	}
	
	public ArrayList<String> getAllNumPlates() throws RemoteException{
		ArrayList<Car> cars = new ArrayList<>();
		cars= this.getAllCars();
		ArrayList<String> numPlates = new ArrayList<>();
		for(Car car: cars) {
			numPlates.add(car.getNumPlate());
		}
		return numPlates;
	}
	
	public ArrayList<String> garagesWithCars() throws RemoteException{
		ArrayList<String> garages = new ArrayList<>();
		ArrayList<Car> cars = new ArrayList<>();
		cars=this.getAllCars();
		for(Car car: cars) {
			if(!(garages.contains(car.getGarage()))) {
				garages.add(car.getGarage());
			}	
		}
		return garages;
	}
	
	public ArrayList<Car> getCarsAvailable(String garageOrigin, Date startingDate, Date finishingDate) throws RemoteException{
		ArrayList<Car> carsAvailable = new ArrayList<>();
		ArrayList<String>carsNotAvailable = new ArrayList<>();
		ArrayList<Car>allCars= new ArrayList<>();
		allCars=getCars(garageOrigin);
		for(int i=0; i<rents.size(); i++) {
			if (rents.get(i).getGarageOrigin().equalsIgnoreCase(garageOrigin)) {
				if(!(finishingDate.before(rents.get(i).getStartingDate()) || startingDate.after(rents.get(i).getFinishingDate()))){
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
		System.out.println("Cars available!!");
		for (Car car: carsAvailable) {
			System.out.println(car.toString());
		}
		return carsAvailable;
	}
	
	
	public Object[][] garageOriginPopularity(){
		ArrayList<Rent> rents = new ArrayList<>();
		ArrayList<String> garages= new ArrayList<>();
		try {
			garages= getGarages();
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//Filling the list
		Object[][] garagePopularity = new Object[garages.size()][3];
		for(int i=0; i<garages.size(); i++) {
			garagePopularity[i][0] =(Object)garages.get(i);
			garagePopularity[i][1] =0;	
			garagePopularity[i][2] =0;	
		}
		try {
			rents= this.getRents();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
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
		for(int i=0; i<garagePopularity.length; i++) {
			for(int j=0; j<garagePopularity[i].length; j++) {
				System.out.println(garagePopularity[i][j]);
				
			}
		}
		return garagePopularity;
	}
	
	public boolean checkExistingNumPlate(String numPlate) throws RemoteException {
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
	
	
	
	public ResourceBundle getResourcebundle() {
		return myBundle;
	}
	
	
	public static void main(String[] args) throws RemoteException, MalformedURLException, NotBoundException {
		new Controller(args);
	}

	
	
}
