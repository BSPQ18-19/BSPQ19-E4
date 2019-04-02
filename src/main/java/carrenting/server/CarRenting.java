package carrenting.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import carrenting.server.jdo.Car;
import carrenting.server.jdo.Garage;



public class CarRenting extends UnicastRemoteObject implements ICarRenting{

	private List<Garage> garageList;
	private List<Car> cars;
	private HashMap<String, Garage> garages; 
	
	public CarRenting() throws RemoteException {
		garageList = new ArrayList<>();
		garages = new HashMap<>();
		List<Car> carsB = new ArrayList<>();
		List<Car> carsM = new ArrayList<>();
		
		//Create Test Garages
		
		//Create Test Cars
		carsM.add(new Car(0, "A7", "Audi", "3223 GVV", 30));
		carsB.add(new Car(0, "A8", "Audi", "3223 GVV", 30));
		
		garageList.add(new Garage("Bilbao", carsB));
		garageList.add(new Garage("Madrid", carsM));
		
		for(Garage g : garageList) {
			garages.put(g.getLocation(), g);
		}
		
	}
	
	public String[] getGarageNames() throws RemoteException {
		String[] ret = new String[garages.size()];
		
		int i = 0;
		for(Garage g : garageList) {
			ret[i] = g.getLocation();
			i++;
		}
		
		return ret;
	}

	public List<Car> getCars(String garages) throws RemoteException{
		List<Car> ret = new ArrayList<>();
		
		ret.addAll(this.garages.get(garages).getCars());
		
		return ret;
	}
	
	public boolean loginStaff(String user, String password) throws RemoteException{
	
		System.out.println("Recieved user: " + user);
		System.out.println("Recieved password: " + password);
		
		return false;
	}

	public void registerUser (String username) throws RemoteException{
		System.out.println("Username: " + username);
		
	}
	

	
	
}
