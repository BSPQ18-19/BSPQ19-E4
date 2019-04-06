package carrenting.server.jdo;
import java.io.Serializable;
import java.util.Date;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.PrimaryKey;


@PersistenceCapable
@SuppressWarnings("serial")
public class Rent implements Serializable{
	private String userId;
	@PrimaryKey
	private String numberPlate;
	@PrimaryKey
	private java.util.Date startingDate;
	private java.util.Date finishingDate;
	private String garageOrigin;
	private String garageDestination;
	private String paymentSystem;
	private int totalPrice;
	
	
	
	public Rent(String userId, String numberPlate, Date startingDate, Date finishingDate, String garageOrigin,
			String garageDestination, String paymentSystem, int totalPrice) {
		super();
		this.userId = userId;
		this.numberPlate = numberPlate;
		this.startingDate = startingDate;
		this.finishingDate = finishingDate;
		this.garageOrigin = garageOrigin;
		this.garageDestination = garageDestination;
		this.paymentSystem = paymentSystem;
		this.totalPrice = totalPrice;
	}
	

	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
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
