package bankingApp2.controller;

import java.sql.Connection;

import bankingApp2.dao.AccountsDAO;
import bankingApp2.dao.UserAcctsDAO;
import io.javalin.Javalin;

public class AccountController {
	Javalin app;
	AccountsDAO acct;
	Connection con;
	
	public AccountController(Javalin app, Connection con) {
		this.app = app;
		this.acct = new AccountsDAO("acctNum", "accounts");
		this.con = con;
		
		//app.get("/accounts/{accountnumber}", getAccount);
		//app.post("/users", createNewUser);
		//app.put("/users/{username}", updateUser);
		//app.delete("/users/{username}", deleteUser);
	}
	
	//public handler get
}
