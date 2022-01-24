package bankingApp2.models;
import java.util.*;

public class Application {
	//Application ID
	private String aid;
	private AcctType atype;
	private Double deposit;
	
	public Application(String aid, String option, double deposit) {
		this.aid = aid;
		this.deposit = deposit;
		this.atype = (option.equals("checking"))? AcctType.CHECKING : AcctType.SAVING;
	}

	public String getAppID() { return this.aid; }
	
	public AcctType getAcctType() { return this.atype; }
	
	
	
	public double getDeposit() { return this.deposit; }

	
	@Override
	public String toString() {
		return "Application ID: " + this.aid + "\n" +
		       "Account Type: " + this.atype + "\n" +
		       "Deposit: " + this.deposit + "\n";
	}

}
