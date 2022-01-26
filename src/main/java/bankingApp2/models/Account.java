package bankingApp2.models;

public class Account {
	
	int acctNum;
	String acctType;
	double balance;
	boolean isjoint;
	
	public Account(int acctNum, String acctType, double balance, boolean isjoint) {
		this.acctNum = acctNum;
		this.acctType = acctType;
		this.balance = balance;
		this.isjoint = isjoint;
		
	}
	
	public void setAcctNum(int acctNum) { this.acctNum = acctNum; }
	public int getAcctNum() { return this.acctNum; }
	
	public void setAcctType(String acctType) { this.acctType = acctType; }
	public String getAcctType() { return this.acctType; }
	
	public void setBalance(double balance) { this.balance = balance; }
	public double getBalance() { return this.balance; }
	
	public boolean isJoint() { return this.isjoint; }
	

}
