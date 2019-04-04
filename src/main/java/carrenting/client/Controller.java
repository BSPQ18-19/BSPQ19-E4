package carrenting.client;

import java.net.MalformedURLException;



import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import carrenting.client.gui.LogInStaffGUI;
import carrenting.client.gui.WelcomeGUI;
import carrenting.server.ICarRenting;
import carrenting.server.jdo.Car;
import carrenting.server.jdo.Garage;




public class Controller{

	private RMIServiceLocator rsl;
	private String username;
	
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
		
		//Inicializar GUI
		
		//new LogInStaffGUI(this);
		new WelcomeGUI(this);
		
		//Pruebas
		loginStaff("staff", "password");
		getGarageNames();
//		getCars("Bilbao");
	}
	
	
	public String[] getGarageNames() throws RemoteException {
		String[] ret;
		ret=RMIServiceLocator.getService().getGarageNames();
		System.out.println("List of Garages:");
		for(int i = 0; i < ret.length; i++) {
			System.out.println(ret[i]);
		}
		
		return ret;
	}
	
	
//	public List<Car> getCars(String garage) throws RemoteException{
//		List<Car> ret = new ArrayList<>();
//		
//		ret = RMIServiceLocator.getService().getCars(garage);
//		
//		System.out.println("Cars in " + garage +":");
//		for(Car c : ret) {
//			System.out.println(c.toString());	
//		}
//		
//		return ret;
//		
//	}
	
	public void storeGarage(String location) throws RemoteException {
		RMIServiceLocator.getService().storeGarage(location);
	}
	
	public ArrayList<String> getGarages() throws RemoteException{
		return RMIServiceLocator.getService().getGarages();
	}
	
	
	public boolean loginStaff(String user, String password) throws RemoteException {
		ICarRenting connection = RMIServiceLocator.getService();
		return connection.loginStaff(user, password);	
	}
	
	public void register ( String username) throws RemoteException {
		RMIServiceLocator.getService().registerUser(username);
	}

	
	public static void main(String[] args) throws RemoteException, MalformedURLException, NotBoundException {
		new Controller(args);
	}
	
}