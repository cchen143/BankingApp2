package bankingApp2.console;

import java.sql.*;
import static bankingApp2.dao.Utils.*;
import java.util.Scanner;
import bankingApp2.dao.*;
import bankingApp2.models.Customer;

public class Menu {

	private AccountsDAO acct;
	private UserAcctsDAO user;
	private CustomersDAO cust;
	private EmployeesDAO emp;
	private ApplicationsDAO app;
	private OwnersDAO own;
	private ApplicantsDAO apc;

	public Menu() {
		this.acct = new AccountsDAO("acctNum", "accounts");
		this.user = new UserAcctsDAO("username", "useraccts");
		this.cust = new CustomersDAO("cID", "customers");
		this.emp = new EmployeesDAO("eID", "employees");
		this.app = new ApplicationsDAO("appID", "applications");
		this.own = new OwnersDAO();
		this.apc = new ApplicantsDAO();
	}

	void main(Scanner sc, Connection con) throws SQLException {

		while (true) {
			String option = choose3(sc, "1. Sign up | 2. Sign in | 3. Exit: ");
			switch (option) {
			case "1": 
				signup(sc);
				break;
			case "2":
				signin(sc);
				return;
			case "3":
				return;
			}

		}

	}

	void signup(Scanner sc) throws SQLException {
		String option = choose2(sc, "1. Customer | 2. Employee: "), name, username, pwd;

		switch (option) {
		case "1": 
			System.out.println("Full name: ");
			name = sc.nextLine();

			System.out.println("Address: ");
			String address = sc.nextLine();

			System.out.println("Date of Birth: ");
			String dob = sc.nextLine();

			Customer c = null;
			boolean CE = cust.getCAcct(c, name, address, dob), UE = c.getUserName() != null;
			if (CE && UE) { System.out.println("Account exists."); return; }

			if (!UE) {
				do {
					System.out.println("Username: ");
					username = sc.nextLine();
				} while (username.equals("") && user.checkElement(username) && usernameNA());
				System.out.println("Password: ");
				pwd = sc.nextLine();


				Connection con = ConnectionManager.getConnection();
				try {
					con.setAutoCommit(false);
					//create new customer account
					
					if (!CE) {
						c = new Customer(cust.randInt(), name, address, dob, username);
						cust.newCustomers(con, c);
					}
					//update username on existing customer account
					else cust.updateUsername(con, name, address, dob, username);
					//create new user account
					user.newUser(con, username, pwd, "CUSTOMER");
					con.commit();
					if (c != null) cust.addToTemp(c);
					user.addToTemp(c);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					try { if (con != null) con.rollback(); } catch (SQLException e2) { e2.printStackTrace(); }
					e.printStackTrace();
				}
			}
			break;

		case "2":
			String type = choose2(sc, "1. ADMIN | 2. EMPLOYEE: ");
			type = (type.equals("1")) ? "ADMIN" : "EMPLOYEE";

			System.out.println("Employee ID: ");
			String eid = sc.nextLine();
			if (emp.checkElement(eid) && eAcctExists()) return;

			System.out.println("Full name: ");
			name = sc.nextLine();

			do {
				System.out.println("Username: ");
				username = sc.nextLine();
			} while (user.checkElement(username) && usernameNA());



			System.out.println("Password: ");
			pwd = sc.nextLine();

			Connection con = ConnectionManager.getConnection();
			try {
				con.setAutoCommit(false);
				emp.newEmployee(con, eid, name, username);
				user.newUser(con, username, pwd, type);
				con.commit();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				try { if (con != null) con.rollback(); } catch (SQLException e2) { e2.printStackTrace(); }
				e.printStackTrace();
			}
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
		} while (type.equals("") && doesNotMatch());

		while (true) {
			switch (type) {
			case "CUSTOMER":
				Customer c = cust.getCustomer(username);
				String option = choose6(sc, "1. Apply | 2. Withdraw | 3. Deposit | 4. Transfer | 5. View Statement | 6. Exit: ");
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
				}
				break;

			case "EMPLOYEE":
				String option2 = choose3(sc, "1. Review | 2. View Customer Info | 3. Exit: ");
				switch(option2) {
				case "1":
					Transaction.review(sc, app, apc, acct, own, cust);
					break;
				case "2":
					Transaction.viewCustomerInfo(sc, cust, acct);
					break;
				case "3":
					return;
				}
				break;
			default:
				String option3 = choose7(sc, "1. Review | 2. View Customer Info | 3. Withdraw | 4. Deposit | 5. Transfer | 6. Close Account | 7. Exit: ");
				switch(option3) {
				case "1":
					Transaction.review(sc, app, apc, acct, own, cust);
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
				}
			}
		}
	}
}
