package carrenting.client;

import java.net.MalformedURLException;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

import carrenting.client.gui.WelcomeGUI;
import carrenting.server.jdo.Car;
import carrenting.server.jdo.Rent;



public class Controller{

	private Rent rent = new Rent(null, null, null, null, null, null, null, 0);
	private ResourceBundle resourceBundle; //el que gestiona los diomas
	private Locale currentLocale; //variable para decirle que idioma queremos
	
	
	public Controller(String[] args) throws RemoteException, MalformedURLException, NotBoundException{
		
		//asigno la variable currentLocale a uno de los idiiomas que tenemos
		if(args[3]=="0"){
			currentLocale = new Locale("en", "EU");
		}else if(args[3]=="1"){
			currentLocale = new Locale("es", "ES");
		}else if(args[3]=="2"){
			currentLocale = new Locale("eu", "ES");
		}
		//le paso la ruta donde se encuentran los archivos de los idiomas y el currentLocale
		//resourceBundle = ResourceBundle.getBundle("BSPQ19/src/main/resources/security", currentLocale);
		
		RMIServiceLocator.setService(args[0], args[1], args[2]);
		
		getRents();
		//getGarageDestination("0352HTQ");
		//Inicializar GUI
		new WelcomeGUI(this, this.rent);
	}
	
	
	public void storeGarage(String location) throws RemoteException {
		RMIServiceLocator.getService().storeGarage(location);
	}
	
	public ArrayList<String> getGarages() throws RemoteException{
		return RMIServiceLocator.getService().getGarages();
	}
	
	public ArrayList<Car> getCars(String garage,int availability) throws RemoteException{
//		ArrayList<Car> cars = new ArrayList<>();
//		cars=RMIServiceLocator.getService().getCars(garage,availability);
//		for(Car car:cars){
//			System.out.println(car.toString());
//		}
		return RMIServiceLocator.getService().getCars(garage,availability);
		
	}
	
	public boolean loginStaff(String user, String password, String type) throws RemoteException {
		return RMIServiceLocator.getService().loginStaff(user, password, type);	
	}
	
	public void register ( String username) throws RemoteException {
		RMIServiceLocator.getService().registerUser(username);
	}

	public String getGarageDestination(String numberPlate) throws RemoteException{
		ArrayList<Rent> rents =RMIServiceLocator.getService().getRents();
		ArrayList<Rent> rentsByNumPlate = new ArrayList<>();
		Date currentDate= new Date();
		Date latestDate = new Date();
		for (Rent rent: rents) {
			if(rent.getNumberPlate().equals(numberPlate)) {
				System.out.println(rent);
				rentsByNumPlate.add(rent);
			}
		}
		for(int i=0; i<rentsByNumPlate.size() ; i++) {
			currentDate= rentsByNumPlate.get(i).getStartingDate();
			System.out.println(currentDate);
			if(currentDate.compareTo(latestDate)< 0) {
				latestDate= currentDate;
				System.out.println(latestDate);
			}
			if(i==rentsByNumPlate.size()-1) {
				for(Rent rent: rentsByNumPlate) {
					if(latestDate.equals(rent.getStartingDate())) {
						System.out.println(rent.getGarageDestination());
						return rent.getGarageDestination();
					}
				}
			}
		}

		
		return null;
	}
	
	public void getRents() throws RemoteException {
		ArrayList<Rent> rents = new ArrayList<>();
		rents=RMIServiceLocator.getService().getRents();
		for(Rent rent:rents){
			System.out.println(rent.toString());
		}
	}

	
	public Rent getRent() {
		return rent;
	}


	public void setRent(Rent rent) {
		this.rent = rent;
	}


	public static void main(String[] args) throws RemoteException, MalformedURLException, NotBoundException {
		new Controller(args);
	}
	
}