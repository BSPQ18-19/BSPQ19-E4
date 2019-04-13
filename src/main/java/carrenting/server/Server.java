package carrenting.server;

import java.io.IOException;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;

import carrenting.server.jdo.DataDAO;

public class Server{
	private static ArrayList<String> garages= new ArrayList();
	
	public static void main(String[] args) throws IOException {
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}

		String name = "//" + args[0] + ":" + args[1] + "/" + args[2];
		System.out.println(" * Server name: " + name);
		
		try {
			ICarRenting server = new CarRenting();
			Naming.rebind(name, server);
			DataDAO.getInstance();
			
//			garages= DataDAO.getInstance().getGarages();
//			for (String garage : garages) {
//				System.out.println(garage);
//			}
//			
			java.io.InputStreamReader inputStreamReader = new java.io.InputStreamReader ( System.in );
			java.io.BufferedReader stdin = new java.io.BufferedReader ( inputStreamReader );
			@SuppressWarnings("unused")
			String line  = stdin.readLine();
			
			
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