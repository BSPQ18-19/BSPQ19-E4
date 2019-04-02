package carrenting.server.jdo;

import java.io.Serializable;

public class Car implements Serializable{
	
	protected int availability;
	protected String model;
	protected String brand;
	protected String numPlate;
	protected double pricePerDay;
	
	protected Car(){
		
	}
	
	public Car(int availability, String model, String brand, String numPlate, double pricePerDay){
		this.availability=availability;
		this.model=model;
		this.brand=brand;
		this.numPlate=numPlate;
		this.pricePerDay=pricePerDay;
	}
	
	public int getAvailability(){
		return availability;
	}
	
	public void setAvailability(int availability){
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
	
	@Override
	public String toString() {
		return "Car [availability=" + availability + ", model=" + model + ", brand=" + brand + ", numPlate=" + numPlate
				+ ", pricePerDay=" + pricePerDay + "]";
	}
	
}
