package carrenting.server.jdo;

public class Car {
	
	protected String availability=null;
	protected String model=null;
	protected String brand=null;
	protected String numPlate=null;
	protected double pricePerDay=0.0;
	
	protected Car(){
		
	}
	
	public Car(String availability, String model, String brand, String numPlate, double pricePerDay){
		this.availability=availability;
		this.model=model;
		this.brand=brand;
		this.numPlate=numPlate;
		this.pricePerDay=pricePerDay;
	}
	
	public String getAvailability(){
		return availability;
	}
	
	public void setAvailability(String availability){
		this.availability=availability;
	}
	
	public String getModel(){
		return model;
	}
	
	public void setModel(String model){
		this.model=model;
	}
	
	public String getBrand(){
		return brand;
	}
	
	public void setBrand(String brand){
		this.brand=brand;
	}
	
	public String getNumPlate(){
		return numPlate;
	}
	
	public void setNumPlate(String numPlate){
		this.numPlate=numPlate;
	}
	
	public double getPricePerDay(){
		return pricePerDay;
	}
	
	public void setPricePerDay(double pricePerDay){
		this.pricePerDay=pricePerDay;
	}
	
	public String toString(){
		return "Car: "+ availability + ", "+ model +", "+ brand+", "+numPlate+", "+pricePerDay;
	}
}
