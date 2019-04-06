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
	public String selectedGarageOrigin;
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
		new WelcomeGUI(this);

		loginStaff("staff", "password");

	}
	
	
	public void storeGarage(String location) throws RemoteException {
		RMIServiceLocator.getService().storeGarage(location);
	}
	
	public ArrayList<String> getGarages() throws RemoteException{
		return RMIServiceLocator.getService().getGarages();
	}
	
	public ArrayList<Car> getCars(String garage) throws RemoteException{
		return RMIServiceLocator.getService().getCars(garage);
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