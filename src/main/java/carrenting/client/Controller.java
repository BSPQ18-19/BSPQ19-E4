package carrenting.client;

import java.net.MalformedURLException;


import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import carrenting.client.gui.WelcomeGUI;
import carrenting.server.ICarRenting;




public class Controller{

	private RMIServiceLocator rsl;
	private String username;
	
	public Controller(String[] args) throws RemoteException, MalformedURLException, NotBoundException{
		rsl = new RMIServiceLocator();
		rsl.setService(args[0], args[1], args[2]);
		
		//Inicializar GUI
		
		new WelcomeGUI(this);
		
		//Pruebas
		loginStaff("alvaroh@opendeusto.es", "Patata");
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