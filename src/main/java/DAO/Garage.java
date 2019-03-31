package DAO;

public class Garage extends Car{

	private String city;
	//los coches disponibles los cogemos de la clase "Car"
	
	protected Garage(){
		
	}
	
	public Garage(String city, String availability){
		this.city=city;
		//la disponibilidad la cogemos de la clase coche
		this.availability=availability;
	}
	
	public String getCity(){
		return city;
	}
	
	public void setCity(String city){
		this.city=city;
	}
	
	public String toString(){
		return "Garage: "+ city +", "+availability;
	}
	
	
}
