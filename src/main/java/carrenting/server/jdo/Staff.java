package carrenting.server.jdo;

import java.io.Serializable;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
@SuppressWarnings("serial")
public class Staff implements Serializable {
	
	protected String type;
	@PrimaryKey
	protected String username;
	protected String password;
	
	
	public Staff(String type, String username, String password){
		this.type=type;
		this.username=username;
		this.password=password;
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
