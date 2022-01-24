package bankingApp2.console;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import bankingApp2.dao.*;
import bankingApp2.models.Application;
import bankingApp2.models.Customer;
import static bankingApp2.dao.Utils.*;
public class Transaction {

	static void withdraw(Scanner sc, AccountsDAO acct) {
		System.out.println("Account Number: ");
		String acctNum = sc.nextLine();
		double balance = acct.exist(acctNum);
		if ( balance == -1) { System.out.println("Invalid acoount number.\n"); return; }
		String amt = "";
		do {
			System.out.println("Amount: ");
			amt = sc.nextLine();
		} while (!isPosNum(amt) && invalidAmount());
		double amount = Double.parseDouble(amt);
		if (balance < amount) { System.out.println("Not enough balance.\n"); return; }
		Connection con = ConnectionManager.getConnection();
		acct.update(con, acctNum, balance, amount, "WITHDRAW");

	}

	static void deposit(Scanner sc, AccountsDAO acct) {
		System.out.println("Account Number: ");
		String acctNum = sc.nextLine();
		double balance = acct.exist(acctNum);
		if ( balance == -1) {
			System.out.println("Invalid acoount number.\n");
			return;
		}
		String amt = "";
		do {
			System.out.println("Amount: ");
			amt = sc.nextLine();
		} while (!isPosNum(amt) && invalidAmount());
		Connection con = ConnectionManager.getConnection();
		acct.update(con, acctNum, balance, Double.parseDouble(amt), "DEPOSIT");
	}

	static void transfer(Scanner sc, AccountsDAO acct) {
		System.out.println("From Account Number: ");
		String acctNum1 = sc.nextLine();
		System.out.println("To Account Number: ");
		String acctNum2 = sc.nextLine();

		double balance = acct.exist(acctNum1), balance2 = acct.exist(acctNum2);
		if (balance < 0 || balance2 < 0) { System.out.println("Invalid account number.\n"); return; }

		String amt = "";
		do {
			System.out.println("Amount: ");
			amt = sc.nextLine();
		} while (!isPosNum(amt) && invalidAmount());
		double amount = Double.parseDouble(amt);

		if (balance < amount) { System.out.println("Not enough balance.\n"); return; }

		Connection con = ConnectionManager.getConnection();
		try {
			con.setAutoCommit(false);
			acct.update(con, acctNum1, balance, amount, "WITHDRAW");
			acct.update(con, acctNum2, balance, Double.parseDouble(amt), "DEPOSIT");
			con.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			try { if (con != null) con.rollback(); } catch (SQLException e2) { e2.printStackTrace(); }
			e.printStackTrace();
		}
	}

	static void viewStatement(Customer c, AccountsDAO acct) {
		System.out.println("Total Balance: " + acct.printAcctInfo(c.getCID()) + "\n");
	}

	static void viewCustomerInfo(Scanner sc, CustomersDAO cust, AccountsDAO acct) {
		String name, address, dob;
		Customer c = new Customer();
		System.out.println("Name: ");
		name = sc.nextLine();
		System.out.println("Address: ");
		address = sc.nextLine();
		System.out.println("Date of Birth: ");
		dob = sc.nextLine();
		if (!cust.getCAcct(c, name, address, dob) && cAcctNotExists()) return;
		System.out.println("\n" + c.toString());
		viewStatement(c, acct);
	}

	static void apply(Scanner sc, Customer c, ApplicationsDAO app, ApplicantsDAO apc, CustomersDAO cust) {

		apc.add(c);

		String type = choose2(sc, "1. SINGLE | 2. JOINT: "), name, address, dob, deposit;
		type = (type.equals("1")) ? "SINGLE" : "JOINT";
		List<String> info = new ArrayList<>();
		if (type.equals("JOINT")) {
			String flag = "Enter information of co-owners.";
			System.out.println(flag);
			while (!flag.equals("q")) {
				Customer temp = new Customer();
				System.out.println("Name: ");
				info.add(sc.nextLine());
				System.out.println("Address: ");
				info.add(sc.nextLine());
				System.out.println("Date of Birth: ");
				info.add(sc.nextLine());
				System.out.println("Enter q to quit.");
				flag = sc.nextLine();
			}
			apc.addToApcs(info.toArray(new String[info.size()]));
		}

		do {
			System.out.println("Initial Deposit: ");
			deposit = sc.nextLine();
		} while (!isPosNum(deposit) && invalidAmount());

		String appID = app.randInt();

		Connection con = ConnectionManager.getConnection();
		try {
			con.setAutoCommit(false);
			app.newApplictaion(con, appID, type, Double.parseDouble(deposit));
			apc.newApplicants(con, appID);
			con.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			try { if (con != null) con.rollback(); } catch (SQLException e2) { e2.printStackTrace(); }
			e.printStackTrace();
		}
	}

	//TODO
	static void review(Scanner sc, ApplicationsDAO app, ApplicantsDAO apc, AccountsDAO acct, OwnersDAO own, CustomersDAO cust) {
		if (app.size() == 0) { System.out.println("No pending application."); return; }
		System.out.println("Pending application :\n");
		app.printAll("applications");
		
		String appID;
		do {
			System.out.println("Application ID: ");
			appID = sc.nextLine();
		} while (!app.checkElement(appID) && appNotExists());
		String option = choose2(sc, "1. Approve | 2. Deny: ");
		
		
		
		Connection con = ConnectionManager.getConnection();
		try {
			con.setAutoCommit(false);
			//find all applicants
			//check whether a customer exists: name, address, dob
			//add new Customer
			//add new account and new ownership
			if (option.equals("1")) {
				Application ap = app.getApplication(appID);
				List<Customer> apcs = apc.getApplicants(con, appID);
				for (Customer c: apcs) {
					if (c.getCID() == null) {
						cust.newCustomers
					}
				}
			}
			app.delete(con, "appID", "applications", appID);
			apc.delete(con, "appID", "applicants", appID);
			con.commit();
			app.remove(appID);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			try { if (con != null) con.rollback(); } catch (SQLException e2) { e2.printStackTrace(); }
			e.printStackTrace();
		}
	}

	static void close(Scanner sc, AccountsDAO acct) {
		System.out.println("Account number: ");
		String acctNum = sc.nextLine();
		double balance = acct.exist(acctNum);
		if ( balance == -1) {
			System.out.println("Invalid account number.\n");
			return;
		}
		
		Connection con = ConnectionManager.getConnection();
		try {
			con.setAutoCommit(false);
			acct.update(con, acctNum, balance, balance, "WITHDRAW");
			acct.delete(con, "acctNum", "owners", acctNum);
			acct.delete(con, "acctNum", "accounts", acctNum);
			con.commit();
			acct.remove(acctNum);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			try { if (con != null) con.rollback(); } catch (SQLException e2) { e2.printStackTrace(); }
			e.printStackTrace();
		}
	}
}
