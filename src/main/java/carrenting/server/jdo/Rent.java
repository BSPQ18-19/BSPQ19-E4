package carrenting.server.jdo;
import java.sql.Date;

import javax.jdo.annotations.PrimaryKey;

public class Rent{
	
	private int userId;
	@PrimaryKey
	private Date startingDate;
	private Date finishingDate;
	private String paymentSystem;
	@PrimaryKey
	private String numPlate;
	private String location;
	private double totalPrice;

	
	public Rent(int userId, String numPlate, Date startingDate, Date finishingDate, double totalPrice, String paymentSystem, String location){
		this.userId=userId;
		this.numPlate=numPlate;
		this.startingDate=startingDate;
		this.finishingDate=finishingDate;
		this.paymentSystem=paymentSystem;
		this.location=location;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
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

//	public double getTotalPrice() {
//		return totalPrice;
//	}



	public String getPaymentSystem() {
		return paymentSystem;
	}

	public void setPaymentSystem(String paymentSystem) {
		this.paymentSystem = paymentSystem;
	}

//	@Override
//	public String toString() {
//		return "Rent [userId=" + userId + ", startingDate=" + startingDate + ", finishingDate=" + finishingDate
//				+ ", totalPrice=" + totalPrice + ", paymentSystem=" + paymentSystem + "]";
//	}
//	
	
}
