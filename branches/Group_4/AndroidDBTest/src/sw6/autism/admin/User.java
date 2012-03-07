package sw6.autism.admin;

public class User {
	
	private long id;
	private String username;
	private String password;
	private String result;
	
	public long getId() {
		return id;
	}
	
	public void setId(long _id) {
		id = _id;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String _username) {
		username = _username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String _password) {
		password = _password;
	}
	
	@Override
	public String toString() {
		result = "Username: " + username + " Password: " + password;
		return result;
	}
}
