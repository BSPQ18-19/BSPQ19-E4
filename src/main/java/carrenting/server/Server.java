package carrenting.server;

import java.io.IOException;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import carrenting.client.Controller;
import carrenting.server.jdo.DataDAO;

public class Server{
	


	public static void main(String[] args) throws IOException {
		Logger logger = LoggerFactory.getLogger(Server.class);
		
		
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}

		String name = "//" + args[0] + ":" + args[1] + "/" + args[2];
		logger.info(" * Server name: " + name);
		
		
		
		try {

			ICarRenting server = new CarRenting();
			Naming.rebind(name, server);
			DataDAO.getInstance();
			
			java.io.InputStreamReader inputStreamReader = new java.io.InputStreamReader ( System.in );
			java.io.BufferedReader stdin = new java.io.BufferedReader ( inputStreamReader );
			@SuppressWarnings("unused")
			String line  = stdin.readLine();
			
			
		} catch (RemoteException re) {
			logger.error(" # Collector RemoteException: " + re.getMessage());
			re.printStackTrace();
			System.exit(-1);
		} catch (MalformedURLException murle) {
			logger.error(" # Collector MalformedURLException: " + murle.getMessage());
			murle.printStackTrace();
			System.exit(-1);
		}		
	}

	
}