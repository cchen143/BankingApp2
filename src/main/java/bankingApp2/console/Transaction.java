package bankingApp2.console;
import java.util.Scanner;
import bankingApp2.dao.*;
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
		} while (!isPosNum(amt) && acct.invalidAmount());
		double amount = Double.parseDouble(amt);
		if (balance < amount) { System.out.println("Not enough balance.\n"); return; }
		acct.update(acctNum, balance, amount, "WITHDRAW");
		
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
		} while (!isPosNum(amt) && acct.invalidAmount());
		acct.update(acctNum, balance, Double.parseDouble(amt), "DEPOSIT");
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
		} while (!isPosNum(amt) && acct.invalidAmount());
		double amount = Double.parseDouble(amt);

		if (balance < amount) { System.out.println("Not enough balance.\n"); return; }
		acct.transfer(acctNum1, acctNum2, balance2 + amount, balance - amount);
	}
	
	static void close(Scanner sc, AccountsDAO acct) {
		System.out.println("Account number: ");
		String acctNum = sc.nextLine();
		double balance = acct.exist(acctNum);
		if ( balance == -1) {
			System.out.println("Invalid account number.\n");
			return;
		}
		acct.close(acctNum, "owners", "accounts");
	}
	
	static void viewStatement(Customer c, AccountsDAO acct) {
		System.out.println("Total Balance: " + acct.printAcctInfo(c.getCID()) + "\n");
	}
	
	static void viewCustomerInfo(Scanner sc, CustomersDAO cust, AccountsDAO acct) {
		String name, address, dob;
		Customer c = new Customer();
		System.out.println("Customer name: ");
		name = sc.nextLine();
		System.out.println("Customer address: ");
		address = sc.nextLine();
		System.out.println("Date of Birth: ");
		dob = sc.nextLine();
		if (!cust.getCAcct(name, address, dob, c) && cust.cAcctNotExists()) return;
		System.out.println("\n" + c.toString());
		viewStatement(c, acct);
	}
	
	//TODO
	static void apply(Scanner sc) {
		System.out.println("1. Single | 2. Joint: ");
		String option = sc.nextLine();
		System.out.println("Initial Deposit: ");
		String deposit = sc.nextLine();
	}
		
	//TODO
	static void review(Scanner sc) {
		boolean i = true;
		if (i) {
			System.out.println("No pending application.");
			return;
		}

		System.out.println("Pending application :\n");


		System.out.println("");
		System.out.println("Application ID: ");
		String aid = sc.nextLine();
		System.out.println("1. Approve | 2. Deny: ");
		String option = sc.nextLine();
	}
}
