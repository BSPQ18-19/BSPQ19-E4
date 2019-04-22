package carrenting.server.jdo;

import java.io.Serializable;


import javax.jdo.annotations.Column;
import javax.jdo.annotations.ForeignKey;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
@SuppressWarnings("serial")
public class Car implements Serializable{
	private String garage; 
	@PrimaryKey
	protected String numPlate;
	protected String brand;
	protected String model;
	protected double pricePerDay;

	public Car(String garage, String numPlate, String brand, String model, double pricePerDay) {
		super();
		this.garage = garage;
		this.numPlate = numPlate;
		this.brand = brand;
		this.model = model;
		this.pricePerDay = pricePerDay;
	}

	public String getGarage() {
		return garage;
	}

	public void setGarage(String garage) {
		this.garage = garage;
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
	

	public double getPricePerDay() {
		return pricePerDay;
	}

	public void setPricePerDay(double pricePerDay) {
		this.pricePerDay = pricePerDay;
	}

	@Override
	public String toString() {
		return "Car [garage=" + garage + ", numPlate=" + numPlate + ", brand="
				+ brand + ", model=" + model + ", pricePerDay=" + pricePerDay + "]";
	}
}
