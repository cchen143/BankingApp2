package bankingApp2.models;

public class Customer {
	
	private String cid;
	private String name;
	private String dob;
	private String address;
	
	private String username;
	
	private final UserType cType = UserType.CUSTOMER;
	
	public Customer() { }
	
	public Customer (String cid, String name, String address, String dob, String username) {
		this.cid = cid;
		this.name = name;
		this.dob = dob;
		this.address = address;
		this.username = username;
	}
	
	public UserType getUserType() {
		return this.cType;
	}
	
	public String getUserName() { return this.username; }
	public void setUserName(String uid) { this.username = uid; }
	
	public String getCID() { return this.cid; }
	public void setCID(String cid) { this.cid = cid; }
	
	public void setName(String name) { this.name = name; }
	public String getName() { return this.name; }
	
	public void setDOB(String dob) { this.dob = dob; } 
	public String getDOB() { return this.dob; }
	
	public void setAddress(String address) { this.address = address; }
	public String getAddress() { return this.address; }
	
	
	@Override
	public String toString() {
		return "Customer ID: " + this.cid + "\n" +
			   "Name: " + this.name + "\n" +
			   "Date of Birth: " + this.dob + "\n" +
			   "Address: " + this.address + "\n" +
			   "Username: " + this.username + "\n";
				
	}
}
