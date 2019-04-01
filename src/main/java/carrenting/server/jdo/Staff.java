package carrenting.server.jdo;

public class Staff {
	
	protected String type;
	protected String password;
	protected String username;
	
	
	public Staff(String type, String password, String username){
		this.type=type;
		this.password=password;
		this.username=username;
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
	
	public String getUsername(){
		return username;
	}
	
	public void setUsername(String username){
		this.username=username;
	}

	@Override
	public String toString() {
		return "Staff [type=" + type + ", password=" + password + ", username=" + username + "]";
	}

}
