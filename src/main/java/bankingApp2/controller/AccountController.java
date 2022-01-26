package bankingApp2.controller;

import java.sql.Connection;
import java.util.List;
import static bankingApp2.dao.Utils.*;
import bankingApp2.dao.AccountsDAO;
import bankingApp2.models.Account;
import bankingApp2.models.Customer;
import io.javalin.Javalin;
import io.javalin.http.Handler;

public class AccountController {
	Javalin app;
	AccountsDAO acct;
	Connection con;
	
	public AccountController(Javalin app, Connection con) {
		this.app = app;
		this.acct = new AccountsDAO("acctNum", "accounts");
		this.con = con;
		
		app.get("/accounts/{acctNum}", getAcctInfo);
		app.get("accounts/", printAll);
		app.post("/accounts/{acctNum_acctType_balance_isjoint}", createNewAccount);
		app.put("/accounts/deposit/{acctNum_balance_amount}", deposit);
		app.delete("/accounts/{acctNum}", deleteAccount);
	}
	
	
	public Handler createNewAccount = ctx -> {
			
		String[] ary = ctx.pathParam("acctNum_acctType_balance_isjoint").split("_");
		int acctNum = (isPInt(ary[0])) ? Integer.parseInt(ary[0]) : -1;
		String acctType = (ary[1].equals("CHECKING")) ? "CHECKING" : (ary[1].equals("SAVING")) ? "SAVING" : "";
		double balance = (isPNum(ary[2]))? Double.parseDouble(ary[2]) : -1;
		boolean isjoint = (ary[3].equals("true")) ? true : false;
		if (acctNum != -1 && !acctType.equals("") && balance > 0) {
			acct.newAccount(con, acctNum, acctType, balance, isjoint);
			ctx.status(200);
		}
			
		else {
			ctx.result("Invalid inputs.\n");
			ctx.status(400);
		}
	};
	
	public Handler getAcctInfo = ctx -> {
		String s = ctx.pathParam("acctNum");
		int acctNum = -1;
		if (isPInt(s)) {
			acctNum = Integer.parseInt(s);
			ctx.result("Account Number: " + acctNum + ", Balance: " + acct.exist(acctNum));
			ctx.status(200);
		}
			
		else {
			ctx.result("Invalid inputs.\n");
			ctx.status(400);
		}
	};
	
	public Handler deposit = ctx -> {
		String[] ary = ctx.pathParam("acctNum_balance_amount").split("_");
		int acctNum = -1;
		double balance = -1, amount = -1;
		if (isPInt(ary[0])) acctNum = Integer.parseInt(ary[0]);
		if (isPNum(ary[1])) balance = Double.parseDouble(ary[1]);
		if (isPNum(ary[2])) amount = Double.parseDouble(ary[2]);
		
		if (acctNum != -1 && balance != -1 && amount > 0) {
			acct.update(con, acctNum, balance, amount, "DEPOSIT");
			ctx.status(204);
		}
		else {
			ctx.result("Invalid inputs.\n");
			ctx.status(400);
		}
		
	};
	
	public Handler deleteAccount = ctx -> {
		String s = ctx.pathParam("acctNum");
		int acctNum = -1;
		if (isPInt(s)) {
			acctNum = Integer.parseInt(s);
			acct.delete(con, "acctNum", "accounts", acctNum);
			ctx.status(200);
		}
			
		else {
			ctx.result("Invalid inputs.\n");
			ctx.status(400);
		}
	};
	
	public Handler printAll = ctx -> {
		ctx.json(acct.getAllAccounts());
		ctx.status(200);
	};
}
