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
		int acctNum = isPosInt(sc.nextLine());
		double balance = acct.exist(acctNum);
		if ( balance == -1) { System.out.println("Invalid acoount number.\n"); return; }
		double amt = -1;
		do {
			System.out.println("Amount: ");
			amt = isPosNum(sc.nextLine());
		} while (amt == -1 && invalidAmount());
		if (balance < amt) { System.out.println("Not enough balance.\n"); return; }
		Connection con = ConnectionManager.getConnection();
		acct.update(con, acctNum, balance, amt, "WITHDRAW");

	}

	static void deposit(Scanner sc, AccountsDAO acct) {
		System.out.println("Account Number: ");
		int acctNum = isPosInt(sc.nextLine());
		double balance = acct.exist(acctNum);
		if ( balance == -1) {
			System.out.println("Invalid acoount number.\n");
			return;
		}
		double amt = -1;
		do {
			System.out.println("Amount: ");
			amt = isPosNum(sc.nextLine());
		} while (amt == -1 && invalidAmount());
		Connection con = ConnectionManager.getConnection();
		acct.update(con, acctNum, balance, amt, "DEPOSIT");
	}

	static void transfer(Scanner sc, AccountsDAO acct) {
		System.out.println("From Account Number: ");
		int acctNum1 = isPosInt(sc.nextLine());
		System.out.println("To Account Number: ");
		int acctNum2 = isPosInt(sc.nextLine());

		double balance = acct.exist(acctNum1), balance2 = acct.exist(acctNum2);
		if (balance < 0 || balance2 < 0) { System.out.println("Invalid account number.\n"); return; }

		double amt = -1;
		do {
			System.out.println("Amount: ");
			amt = isPosNum(sc.nextLine());
		} while (amt == -1 && invalidAmount());

		if (balance < amt) { System.out.println("Not enough balance.\n"); return; }

		Connection con = ConnectionManager.getConnection();
		try {
			con.setAutoCommit(false);
			acct.update(con, acctNum1, balance, amt, "WITHDRAW");
			acct.update(con, acctNum2, balance, amt, "DEPOSIT");
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

		String option = choose2(sc, "1. SINGLE | 2. JOINT: "), name, address, dob;
		double deposit = -1;
		option = (option.equals("1")) ? "SINGLE" : "JOINT";
		List<String> info = new ArrayList<>();
		if (option.equals("JOINT")) {
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
			apc.addToTemp(info.toArray(new String[info.size()]));
		}
		
		String type = choose2(sc, "1. CHECKING | 2. SAVING: ");
		type = (type.equals("1")) ? "CHECKING" : "SAVING";

		do {
			System.out.println("Initial Deposit: ");
			deposit = isPosNum(sc.nextLine());
		} while (deposit == -1 && invalidAmount());

		int appID = app.randInt();

		Connection con = ConnectionManager.getConnection();
		try {
			con.setAutoCommit(false);
			app.newApplictaion(con, appID, type, deposit);
			apc.newApplicants(con, appID);
			con.commit();
			apc.clearTemp();
			app.add(appID);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			try { if (con != null) con.rollback(); } catch (SQLException e2) { e2.printStackTrace(); }
			e.printStackTrace();
		}
	}

	static void review(Scanner sc, ApplicationsDAO app, ApplicantsDAO apc, AccountsDAO acct, OwnersDAO own, CustomersDAO cust) {
		if (app.size() == 0) { System.out.println("No pending application."); return; }
		System.out.println("Pending application :\n");
		app.printAll("applications");
		
		int appID = -1;
		app.printSet();
		do {
			System.out.println("Application ID: ");
			appID = isPosInt(sc.nextLine());
			System.out.println(appID);
			app.printSet();
		} while (!app.checkElement(appID) && appNotExists());
		String option = choose2(sc, "1. Approve | 2. Deny: ");
		
		
		
		Connection con = ConnectionManager.getConnection();
		try {
			con.setAutoCommit(false);
			//find all applicants
			//check whether a customer exists: name, address, dob
			//add new Customer
			//add new account and new ownership
			Customer[] cs = new Customer[0];
			int acctNum = acct.randInt();
			if (option.equals("1")) {
				Application ap = app.getApplication(appID);
				apc.cacheApplicants(con, appID);
				cs = apc.getApplicants();
				cust.newNonExistingCustomers(con, cs);
				acct.newAccount(con, acctNum, ap.getAcctType(), ap.getDeposit(), cs.length > 1);
				own.newOwners(con, acctNum, cs);
			}
			apc.delete(con, "appID", "applicants", appID);
			app.delete(con, "appID", "applications", appID);
			con.commit();
			app.remove(appID);
			apc.clearTemp();
			if (option.equals("1")) {
				cust.addToTemp(cs);
				acct.add(acctNum);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			try { if (con != null) con.rollback(); } catch (SQLException e2) { e2.printStackTrace(); }
			e.printStackTrace();
		}
	}

	static void close(Scanner sc, AccountsDAO acct) {
		System.out.println("Account number: ");
		int acctNum = isPosInt(sc.nextLine());
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
