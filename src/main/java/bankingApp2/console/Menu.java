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
			String option = choose3(sc, "1. Sign up | 2. Sign in | 3. Exit: ", INVALIDOPTION);
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
		String option = choose2(sc, "1. Customer | 2. Employee: ", INVALIDOPTION), name, username, pwd;

		switch (option) {
		case "1": 
			System.out.println("Full name: ");
			name = sc.nextLine();

			System.out.println("Address: ");
			String address = sc.nextLine();

			System.out.println("Date of Birth: ");
			String dob = sc.nextLine();

			Customer c = new Customer();
			boolean CE = cust.getCAcct(c, name, address, dob), UE = CE && c.getUserName() != null;
			if (CE && UE) { System.out.println("Account exists."); return; }

			if (!UE) {
				
				username = getOneInput(sc, "Username: ", user, USERNAMENA);
				
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
			String type = choose2(sc, "1. ADMIN | 2. EMPLOYEE: ", INVALIDOPTION);
			type = (type.equals("1")) ? "ADMIN" : "EMPLOYEE";

			int eid = getPInt(sc, "Employee ID: ", INVALIDINPUT);
			
			if (emp.check(eid) && errFunc(EACCTEXISTS)) return;

			System.out.println("Full name: ");
			name = sc.nextLine();

			username = getOneInput(sc, "Username: ", user, USERNAMENA);

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
		if (user.size() == 0) {System.out.println("No user in the system.\n"); return; }
		
		String username, pwd, type = "";
		do {
			System.out.println("Username: ");
			username = sc.nextLine();
			System.out.println("Password: ");
			pwd = sc.nextLine();
			type = user.validate(username, pwd);
		} while (type.equals("") && errFunc(DOESNOTMATCH));

		while (true) {
			switch (type) {
			case "CUSTOMER":
				Customer c = cust.getCustomer(username);
				String option = choose6(sc, "1. Apply | 2. Withdraw | 3. Deposit | 4. Transfer | 5. View Statement | 6. Exit: ", INVALIDOPTION);
				switch(option) {
				case "1":
					Transaction.apply(sc, c, app, apc, cust);
					break;
				case "2":
					if (acct.size() == 0) { System.out.println("No active account.\n"); break; }
					acct.printAcctInfo(c.getCID());
					Transaction.withdraw(sc, acct);
					break;
				case "3":
					if (acct.size() == 0) { System.out.println("No active account.\n"); break; }
					acct.printAcctInfo(c.getCID());
					Transaction.deposit(sc, acct);
					break;
				case "4":
					if (acct.size() == 0) { System.out.println("No active account.\n"); break; }
					acct.printAcctInfo(c.getCID());
					Transaction.transfer(sc, acct);
					break;
				case "5":
					if (acct.size() == 0) { System.out.println("No active account.\n"); break; }
					Transaction.viewStatement(c, acct);
					break;
				case "6":
					return;
				}
				break;

			case "EMPLOYEE":
				String option2 = choose3(sc, "1. Review | 2. View Customer Info | 3. Exit: ", INVALIDOPTION);
				switch(option2) {
				case "1":
					if (app.size() == 0) { System.out.println("No pending application."); break; }
					Transaction.review(sc, app, apc, acct, own, cust);
					break;
				case "2":
					if (cust.size() == 0) { System.out.println("No customer in the system.\n"); break; }
					Transaction.viewCustomerInfo(sc, cust, acct);
					break;
				case "3":
					return;
				}
				break;
			default:
				String option3 = choose7(sc, "1. Review | 2. View Customer Info | 3. Withdraw | 4. Deposit | 5. Transfer | 6. Close Account | 7. Exit: ", INVALIDOPTION);
				switch(option3) {
				case "1":
					if (app.size() == 0) { System.out.println("No pending application."); break; }
					Transaction.review(sc, app, apc, acct, own, cust);
					break;
				case "2":
					if (cust.size() == 0) { System.out.println("No customer in the system.\n"); break; }
					Transaction.viewCustomerInfo(sc, cust, acct);
					break;
				case "3":
					if (acct.size() == 0) { System.out.println("No active account.\n"); break; }
					acct.printAll("accounts");
					Transaction.withdraw(sc, acct);
					break;
				case "4":
					if (acct.size() == 0) { System.out.println("No active account.\n"); break; }
					acct.printAll("accounts");
					Transaction.deposit(sc, acct);
					break;
				case "5":
					if (acct.size() == 0) { System.out.println("No active account.\n"); break; }
					acct.printAll("accounts");
					Transaction.transfer(sc, acct);
					break;
				case "6":
					if (acct.size() == 0) { System.out.println("No active account.\n"); break; }
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