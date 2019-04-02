package carrenting.server.jdo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.Join;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;


public class Garage implements Serializable{
	@PrimaryKey
	private String location;
	@Persistent(mappedBy="garage", dependentElement="true")
	@Join  
	private List<Car> cars = new ArrayList<>();
	
	public Garage(String location, List<Car> cars){
		this.location=location;	
		this.cars = cars;
	}
	
	public String getLocation(){
		return location;
	}
	
	public void setLocation(String location){
		this.location=location;
	}

	public List<Car> getCars(){
		return cars;
	}
	
	@Override
	public String toString() {
		return "Garage [location=" + location + ", cars=" + cars + "]";
	}
		
}
