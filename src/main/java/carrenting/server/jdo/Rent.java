package carrenting.server.jdo;
import java.sql.Date;

public class Rent extends Garage{
	
	private int userId=0;
	private Date startingDate=null;
	private Date finishingDate=null;
	private double totalPrice=0.0;
	private String paymentSystem=null;

	protected Rent(){
		
	}
	
	public Rent(int userId, String numPlate, Date startingDate, Date finishingDate, double totalPrice, String paymentSystem, String garage){
		this.userId=userId;
		this.numPlate=numPlate;
		this.startingDate=startingDate;
		this.finishingDate=finishingDate;
		this.totalPrice=totalPrice;
		this.paymentSystem=paymentSystem;
		//trying to get the garage from the class "garage"
		this.city=garage;
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

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getPaymentSystem() {
		return paymentSystem;
	}

	public void setPaymentSystem(String paymentSystem) {
		this.paymentSystem = paymentSystem;
	}

	@Override
	public String toString() {
		return "Rent [userId=" + userId + ", startingDate=" + startingDate + ", finishingDate=" + finishingDate
				+ ", totalPrice=" + totalPrice + ", paymentSystem=" + paymentSystem + "]";
	}
	
	
}
