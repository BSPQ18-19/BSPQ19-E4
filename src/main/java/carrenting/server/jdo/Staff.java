package carrenting.jdo;

public class Staff {
	
	protected String type=null;
	protected String password=null;
	protected String email=null;
	
	protected Staff(){
		
	}
	
	public Staff(String type, String password, String email){
		this.type=type;
		this.password=password;
		this.email=email;
	}
	
	public String getType(){
		return type;
	}
	
	public void setType(String type){
		this.type=type;
	}
	
	public String getPass(){
		return password;
	}
	
	public void setPass(String password){
		this.password=password;
	}
	
	public String getEmail(){
		return email;
	}
	
	public void setEmail(String email){
		this.email=email;
	}
	
	public String toString(){
		return "Staff: "+ type +", "+ password +", "+email;
	}
	

}
