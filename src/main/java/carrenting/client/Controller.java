package carrenting.client;

import java.net.MalformedURLException;


import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import carrenting.client.gui.WelcomeGUI;
import carrenting.server.ICarRenting;
import carrenting.server.jdo.Car;
import carrenting.server.jdo.Garage;




public class Controller{

	private RMIServiceLocator rsl;
	private String username;
	
	public Controller(String[] args) throws RemoteException, MalformedURLException, NotBoundException{
		rsl = new RMIServiceLocator();
		rsl.setService(args[0], args[1], args[2]);
		
		//Inicializar GUI
		
		//new WelcomeGUI(this);
		
		//Pruebas
		loginStaff("alvaroh@opendeusto.es", "Patata");
		getGarageNames();
		getCars("Bilbao");
	}
	
	
	public String[] getGarageNames() throws RemoteException {
		String[] ret;
		ret = rsl.getService().getGarageNames();
		
		for(int i = 0; i < ret.length; i++) {
			System.out.println(ret[i]);
		}
		
		return ret;
	}
	
	
	public List<Car> getCars(String garages) throws RemoteException{
		List<Car> ret = new ArrayList<>();
		
		ret = rsl.getService().getCars(garages);
		
		for(Car c : ret) {
			System.out.println(c.toString());	
		}
		
		return ret;
		
	}
	
	public boolean loginStaff(String user, String password) throws RemoteException {
		ICarRenting connection = rsl.getService();
		return connection.loginStaff(user, password);	
	}
	
	public void register ( String username) throws RemoteException {
		rsl.getService().registerUser(username);
	}

	
	public static void main(String[] args) throws RemoteException, MalformedURLException, NotBoundException {
		new Controller(args);
	}
}