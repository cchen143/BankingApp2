package bankingApp2.models;

public class UserAcct {
	String username;
	String pwd;
	String usertype;
	
	public UserAcct(String username, String pwd, String usertype) {
		this.username = username;
		this.pwd = pwd;
		this.usertype = usertype;
	}
	
	public void setUserName(String username) {
		this.username = username;
	}
	
	public String getUserName() {
		return this.username;
	}
	
	public void setPWD(String pwd) {
		this.pwd = pwd;
	}
	
	public String getPWD() {
		return this.pwd;
	}
	
	public void setUserType(String usertype) {
		this.usertype = usertype;
	}
	
	public String getUserType() {
		return this.usertype;
	}
}
