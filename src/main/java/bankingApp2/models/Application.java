package bankingApp2.models;
import java.util.*;

public class Application {
	//Application ID
	private String appid;
	private String acctype;
	private Double deposit;
	
	public Application(String appid, String acctype, double deposit) {
		this.appid = appid;
		this.deposit = deposit;
		this.acctype = acctype;
	}

	public String getAppID() { return this.appid; }
	
	public String getAcctType() { return this.acctype; }
	
	public double getDeposit() { return this.deposit; }

	
	@Override
	public String toString() {
		return "Application ID: " + this.appid + "\n" +
		       "Account Type: " + this.atype + "\n" +
		       "Deposit: " + this.deposit + "\n";
	}

}
