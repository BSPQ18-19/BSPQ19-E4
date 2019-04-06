package carrenting.server.jdo;

import java.io.Serializable;


import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.Join;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@SuppressWarnings("serial")
@PersistenceCapable
public class Garage implements Serializable{
	@PrimaryKey
	private String location;
//	@Persistent(mappedBy="garage", dependentElement="true")
//	@Join  
//	private List<Car> cars = new ArrayList<>();
	
	public Garage(String location){
		this.location=location;	
	}
	
	public String getLocation(){
		return location;
	}
	
	public void setLocation(String location){
		this.location=location;
	}

//	public List<Car> getCars(){
//		return cars;
//	}
//	
//	
//	public void setCars(List<Car> cars) {
//		this.cars = cars;
//	}
//	
//	public void addCar(Car car) {
//		cars.add(car);
//	}

	@Override
	public String toString() {
		return "Garage [location=" + location + ", cars=" + "]";
	}


}
