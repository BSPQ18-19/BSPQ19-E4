package carrenting.server.jdo;
import java.io.Serializable;

import javax.jdo.annotations.PrimaryKey;



@SuppressWarnings("serial")
public class Rent implements Serializable{
	private int userId;
	@PrimaryKey
	private String numberPlate;
	@PrimaryKey
	private java.util.Date startingDate;
	private java.util.Date finishingDate;
	private String garageOrigin;
	private String garageDestination;
	private String paymentSystem;
	private int totalPrice;
	
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getNumberPlate() {
		return numberPlate;
	}
	public void setNumberPlate(String numberPlate) {
		this.numberPlate = numberPlate;
	}
	
	public java.util.Date getStartingDate() {
		return startingDate;
	}
	public void setStartingDate(java.util.Date startingDate2) {
		this.startingDate = startingDate2;
	}
	
	public java.util.Date getFinishingDate() {
		return finishingDate;
	}
	public void setFinishingDate(java.util.Date finishingDate) {
		this.finishingDate = finishingDate;
	}
	
	public String getGarageOrigin() {
		return garageOrigin;
	}
	public void setGarageOrigin(String garageOrigin) {
		this.garageOrigin = garageOrigin;
	}
	
	public String getGarageDestination() {
		return garageDestination;
	}
	public void setGarageDestination(String garageDestination) {
		this.garageDestination = garageDestination;
	}
	
	public String getPaymentSystem() {
		return paymentSystem;
	}
	public void setPaymentSystem(String paymentSystem) {
		this.paymentSystem = paymentSystem;
	}
	
	public int getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	@Override
	public String toString() {
		return "Rent [userId=" + userId + ", numberPlate=" + numberPlate + ", startingDate=" + startingDate
				+ ", finishingDate=" + finishingDate + ", garageOrigin=" + garageOrigin + ", garageDestination="
				+ garageDestination + ", paymentSystem=" + paymentSystem + ", totalPrice=" + totalPrice + "]";
	}

	
	
}
