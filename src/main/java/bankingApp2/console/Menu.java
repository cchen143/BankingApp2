package bankingApp2.console;

import java.sql.*;
import static bankingApp2.dao.Utils.*;
import java.util.Scanner;
import bankingApp2.dao.*;
import bankingApp2.models.Customer;
import bankingApp2.models.Employee;
import bankingApp2.models.UserType;

public class Menu {

	private AccountsDAO acct;
	private UserAcctsDAO user;
	private CustomersDAO cust;
	private EmployeesDAO emp;
	private ApplicationsDAO app;
	private OwnersDAO own;
	private ApplicantsDAO apc;
	
	public Menu() {
		this.acct = new AccountsDAO();
		this.user = new UserAcctsDAO("username", "useraccts");
		this.cust = new CustomersDAO("cID", "customers");
		this.emp = new EmployeesDAO("eID", "employees");
		this.app = new ApplicationsDAO("appID", "applications");
		this.own = new OwnersDAO();
		this.apc = new ApplicantsDAO();
	}
	
	void main(Scanner sc, Connection con) throws SQLException {
		
		while (true) {
			
			System.out.println("1. Sign up | 2. Sign in | 3. Exit: ");
			String option = sc.nextLine();
			
			switch (option) {
			case "1": 
				signup(sc);
				break;
			case "2":
				signin(sc);
				return;
			case "3":
				return;
			default:
				System.out.println("Select an option.\n");
				
			}
			
		}
		
	}
	
	void signup(Scanner sc) throws SQLException {
		System.out.println("1. Customer | 2. Employee: ");
		String option = sc.nextLine(), name, username, pwd;

		switch (option) {
		case "1": 
			System.out.println("Full name: ");
			name = sc.nextLine();
			
			System.out.println("Address: ");
			String address = sc.nextLine();
			
			System.out.println("Date of Birth: ");
			String dob = sc.nextLine();
			
			Customer c = new Customer();
			boolean CE = cust.getCAcct(name, address, dob, c), UE = c.getUserName() != null;
			if (CE && UE) { System.out.println("Account exists."); return; }
			
			if (!UE) {
				do {
					System.out.println("Username: ");
					username = sc.nextLine();
				} while (user.checkElement(username) && user.usernameNA());
				System.out.println("Password: ");
				pwd = sc.nextLine();
				//create new customer account
				if (!CE) cust.newCustomer(cust.randInt(), name, address, dob, username);
				//update username on existing customer account
				else cust.updateUsername(name, address, dob, username);
				//create new user account
				user.newUser(username, pwd, "CUSTOMER");
			}
			break;
			
		case "2":
			String type;
			do {
				System.out.println("ADMIN | EMPLOYEE: ");
				type = sc.nextLine();
			} while (!(type.equals("ADMIN") || type.equals("EMPLOYEE")) && emp.invalidOption());
			
			System.out.println("Employee ID: ");
			String eid = sc.nextLine();
			if (emp.checkElement(eid) && emp.eAcctExists()) return;

			System.out.println("Full name: ");
			name = sc.nextLine();
			
			do {
				System.out.println("Username: ");
				username = sc.nextLine();
			} while (user.checkElement(username) && user.usernameNA());
			
			emp.newEmployee(eid, name, username);
		
			System.out.println("Password: ");
			pwd = sc.nextLine();
			
			user.newUser(username, pwd, type);
			break;
			
		default:
			System.out.println("Select an option.\n");
		}
				
	}
	
	void signin(Scanner sc) throws SQLException {
		
		String username, pwd, type = "";
		do {
			System.out.println("Username: ");
			username = sc.nextLine();
			System.out.println("Password: ");
			pwd = sc.nextLine();
			type = user.validate(username, pwd);
		} while (type.equals("") && user.doesNotMatch());
		
		while (true) {
			switch (type) {
			case "CUSTOMER":
				Customer c = cust.getCustomer(username);
				System.out.println("1. Apply | 2. Withdraw | 3. Deposit | 4. Transfer | 5. View Statement | 6. Exit: ");
				String option = sc.nextLine();
				switch(option) {
				case "1":
					Transaction.apply(sc, c, app, apc, cust);
					break;
				case "2":
					acct.printAcctInfo(c.getCID());
					Transaction.withdraw(sc, acct);
					break;
				case "3":
					acct.printAcctInfo(c.getCID());
					Transaction.deposit(sc, acct);
					break;
				case "4":
					acct.printAcctInfo(c.getCID());
					Transaction.transfer(sc, acct);
					break;
				case "5":
					Transaction.viewStatement(c, acct);
					break;
				case "6":
					return;
				default:
					System.out.println("Invalid option.");
				}
				break;

			case "EMPLOYEE":
				System.out.println("1. Review | 2. View Customer Info | 3. Exit: ");
				String option2 = sc.nextLine();
				switch(option2) {
				case "1":
					Transaction.review(sc);
					break;
				case "2":
					Transaction.viewCustomerInfo(sc, cust, acct);
					break;
				case "3":
					return;
				default:
					System.out.println("Invalid option.");
				}
				break;
			default:
				System.out.println("1. Review | 2. View Customer Info | 3. Withdraw | 4. Deposit | 5. Transfer | 6. Close Account | 7. Exit: ");
				String option3 = sc.nextLine();
				switch(option3) {
				case "1":
					Transaction.review(sc);
					break;
				case "2":
					Transaction.viewCustomerInfo(sc, cust, acct);
					break;
				case "3":
					acct.printAll("accounts");
					Transaction.withdraw(sc, acct);
					break;
				case "4":
					acct.printAll("accounts");
					Transaction.deposit(sc, acct);
					break;
				case "5":
					acct.printAll("accounts");
					Transaction.transfer(sc, acct);
					break;
				case "6":
					acct.printAll("accounts");
					Transaction.close(sc, acct);
					break;
				case "7":
					return;
				default:
					System.out.println("Invalid option.");
				}
			}
		}
	}
	
	
}
