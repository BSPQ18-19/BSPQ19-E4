package carrenting.client;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import carrenting.server.ICarRenting;



public class RMIServiceLocator {

	private ICarRenting service;
	
	public RMIServiceLocator() {
		
	}
	
	public void setService(String ip, String port, String serviceName) throws MalformedURLException, RemoteException, NotBoundException {
		
		String name = "//" + ip + ":" + port + "/" + serviceName;
		service = (ICarRenting) java.rmi.Naming.lookup(name);
		
	}
	
	public ICarRenting getService() {
		return service;
		
	}
	
}
