package bankingApp2.models;

public class Application {
	//Application ID
	private int appid;
	private String acctType;
	private Double deposit;
	
	public Application(int appid, String acctType, double deposit) {
		this.appid = appid;
		this.deposit = deposit;
		this.acctType = acctType;
	}

	public int getAppID() { return this.appid; }
	
	public String getAcctType() { return this.acctType; }
	
	public double getDeposit() { return this.deposit; }

	
	@Override
	public String toString() {
		return "Application ID: " + this.appid + "\n" +
		       "Account Type: " + this.acctType + "\n" +
		       "Deposit: " + this.deposit + "\n";
	}

}
