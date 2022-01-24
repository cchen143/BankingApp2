package bankingApp2.models;

public interface Account {
	
	//returns account type
	public AcctType getAcctType();
	
	// returns account number
	public int getAcctNum();
	
	//prints account information
	public void printAcctInfo();
	
	// returns account balance
	public double getBal();
}
