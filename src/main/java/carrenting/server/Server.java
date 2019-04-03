package carrenting.server;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;

import carrenting.server.jdo.DataDAO;

public class Server{

	public static void main(String[] args) {
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}

		String name = "//" + args[0] + ":" + args[1] + "/" + args[2];
		System.out.println(" * Server name: " + name);
		
		try {
			ICarRenting server = new CarRenting();
			Naming.rebind(name, server);

			DataDAO.getInstance();
			//TODO - We must keep the thread open, so the server keeps open
			while(true) {}
			
		} catch (RemoteException re) {
			System.err.println(" # Collector RemoteException: " + re.getMessage());
			re.printStackTrace();
			System.exit(-1);
		} catch (MalformedURLException murle) {
			System.err.println(" # Collector MalformedURLException: " + murle.getMessage());
			murle.printStackTrace();
			System.exit(-1);
		}
		
	}


	
}