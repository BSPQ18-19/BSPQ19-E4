package carrenting.server.jdo;

public interface ILogin {
	
	public boolean authenticate(String name, String password) throws Exception;
	
}




