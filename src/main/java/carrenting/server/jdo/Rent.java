package carrenting.server.jdo;
import java.io.Serializable;



import javax.jdo.annotations.PrimaryKey;

import org.datanucleus.store.types.wrappers.Date;

@SuppressWarnings("serial")
public class Rent implements Serializable{
	private int userId;
	@PrimaryKey
	private String numberPlate;
	@PrimaryKey
	private Date startingDate;
	private Date finishingDate;
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
	
	public Date getStartingDate() {
		return startingDate;
	}
	public void setStartingDate(Date startingDate) {
		this.startingDate = startingDate;
	}
	
	public Date getFinishingDate() {
		return finishingDate;
	}
	public void setFinishingDate(Date finishingDate) {
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
