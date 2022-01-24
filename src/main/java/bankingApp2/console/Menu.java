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
	private ApplicationsDAO appl;
	private OwnersDAO own;
	private ApplicantsDAO app;
	
	public Menu() {
		this.acct = new AccountsDAO();
		this.user = new UserAcctsDAO();
		this.cust = new CustomersDAO();
		this.emp = new EmployeesDAO();
		this.appl = new ApplicationsDAO();
		this.own = new OwnersDAO();
		this.app = new ApplicantsDAO();
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
	
	//TODO existing customer sign up for a user account 
	//1. C and U DE new C and U
	//2. C E and U DE New U
	//3. C and U E return;
	
	
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
			//TODO
			
			/*if (cust.exist(name, address, dob)) {
			if (user.exist()true)
			else 
			}*/
			int cid;
			do {
				cid = randInt();
			} while(cust.exist("customers", "cID", Integer.toString(cid)));
			
			do {
				System.out.println("Username: ");
				username = sc.nextLine();
			} while (user.exist("useraccts", "username", username) && user.uAcctExists());
			System.out.println("Password: ");
			pwd = sc.nextLine();
			//TODO
			cust.newCustomer(Integer.toString(cid), name, address, dob, username);
			break;
			
		case "2":
			String type;
			do {
				System.out.println("ADMIN | EMPLOYEE: ");
				type = sc.nextLine();
			} while (!(type.equals("ADMIN") || type.equals("EMPLOYEE")) && emp.invalidOPT());
			System.out.println("Employee ID: ");
			String eid = sc.nextLine();
			
			//TODO
			if (emp.exist(eid) && emp.eAcctExists()) return;

			System.out.println("Full name: ");
			name = sc.nextLine();
			
			do {
				System.out.println("Username: ");
				username = sc.nextLine();
			} while (user.exist("useraccts", "username", username) && user.uAcctExists());
			
			System.out.println("Password: ");
			pwd = sc.nextLine();
			//TODO
			emp.newEmployee(eid, name, username);
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
		
		Customer c = null;
		if (user.exist("useraccts", "username", username)) c = cust.getCustomer(username);
		
		
		while (true) {
			switch (type) {
			case "CUSTOMER":
				System.out.println("1. Apply | 2. Withdraw | 3. Deposit | 4. Transfer | 5. View Statement | 6. Exit: ");
				String option = sc.nextLine();
				switch(option) {
				case "1":
					Transaction.apply(sc);
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
