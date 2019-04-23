package carrenting.client;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import carrenting.server.ICarRenting;
import org.apache.log4j.Logger;

public class RMIServiceLocator {

	private static ICarRenting service;
	
	public RMIServiceLocator() {
		
	}
	
	public static void setService(String ip, String port, String serviceName) throws MalformedURLException, RemoteException, NotBoundException {
		String name = "//" + ip + ":" + port + "/" + serviceName;
		service = (ICarRenting) java.rmi.Naming.lookup(name);
		
	}
	
	public static ICarRenting getService() {
		return service;
		
	}
	
}
