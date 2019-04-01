package carrenting.server.jdo;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.Join;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;


public class Garage{
	@PrimaryKey
	private String location;
	@Persistent(mappedBy="garage", dependentElement="true")
	@Join  
	private List<Car> cars = new ArrayList<>();
	
	public Garage(String location){
		this.location=location;	
	}
	
	public String getLocation(){
		return location;
	}
	
	public void setLocation(String location){
		this.location=location;
	}

	@Override
	public String toString() {
		return "Garage [location=" + location + ", cars=" + cars + "]";
	}
		
}
