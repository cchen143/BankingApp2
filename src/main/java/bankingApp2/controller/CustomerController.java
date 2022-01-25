package bankingApp2.controller;

import java.sql.Connection;

import bankingApp2.dao.ConnectionManager;
import bankingApp2.dao.CustomersDAO;
import bankingApp2.models.Customer;
import io.javalin.Javalin;
import io.javalin.http.Handler;

public class CustomerController {
	Javalin app;
	CustomersDAO cust;
	Connection con;
	
	public CustomerController(Javalin app, Connection con) {
		this.app = app;
		this.con = con;
		this.cust = new CustomersDAO("cID", "customers");
		app.get("/customers/{name_address_dob}", getCustomerInfo);
		//app.post("/users", createNewUser);
		//app.put("/users/{username}", updateUser);
		app.delete("/customers/{name}", deleteCustomerByName);
	}
	
	public Handler getCustomerInfo = ctx -> {
		String[] ary = ctx.pathParam("name_address_dob").split("_");
		Customer c = new Customer();
		cust.getCAcct(c, ary[0], ary[1], ary[2]);
		ctx.json(c);
		
		// Status code 200 means "OK"
		ctx.status(200);
	};
	
	public Handler deleteCustomerByName = ctx -> {
		String val = ctx.pathParam("name");
		if (cust.exist(val)) {
			cust.delete(con, "name", "customers", val);
			ctx.result("Customer file removed.\n");
			ctx.status(204); // Status code 204 means "Successfully updated"
		} else {
			ctx.result("Not a registered customer.\n");
			ctx.status(400);
		}
	};
}
