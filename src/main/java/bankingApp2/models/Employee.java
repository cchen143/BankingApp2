package bankingApp2.models;

public class Employee {
	private String eid;
	private String name;
	private String username;
	private String eType;
	
	public Employee (String eid, String name, String username, String etype) {
		this.eid = eid;
		this.name = name;
		this.username = username;
		this.eType = etype;
	}
	
	public String getUserType() { return this.eType; }
	
	public String getEmployeeID() { return this.eid; }
	public void setEmployeeID(String eid) { this.eid = eid; }

	public String getName() { return this.name; }
	public void setName(String name) { this.name = name; }
	
	public String getUserName() { return this.username; }
	public void setUserName(String username) { this.username = username; }
	
	@Override
	public String toString() {
		return "Employee ID: " + this.eid + "\n" +
		       "Employee Type: " + this.eType + "\n" +
		       "Name: " + this.name + "\n";
	}
	
	
	
}
