package bankingApp2.models;

public class Customer {
	
	private int cid;
	private String name;
	private String dob;
	private String address;
	private String username;
	
	private final String cType = "CUSTOMER";
	
	public Customer() { }
	
	public Customer (int cid, String name, String address, String dob, String username) {
		this.cid = cid;
		this.name = name;
		this.dob = dob;
		this.address = address;
		this.username = username;
	}
	
	public String getUserType() {
		return this.cType;
	}
	
	public String getUserName() { return this.username; }
	public void setUserName(String uid) { this.username = uid; }
	
	public int getCID() { return this.cid; }
	public void setCID(int cid) { this.cid = cid; }
	
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
	
	@Override
	public int hashCode() {
		return this.name.hashCode() ^ this.address.hashCode() ^ this.dob.hashCode();
	}
	
	@Override
	public boolean equals(Object other) {
		boolean res = true;
		res = res && this.name.equals(((Customer) other).getName());
		res = res && this.address.equals(((Customer) other).getAddress());
		res = res && this.dob.equals(((Customer) other).getDOB());
		return res;
	}
}
