package bankingApp2.controller;

import java.sql.Connection;
import java.util.List;

import bankingApp2.dao.UserAcctsDAO;
import bankingApp2.models.UserAcct;
import io.javalin.Javalin;
import io.javalin.http.Handler;

public class UserController {

	Javalin app;
	UserAcctsDAO user;
	Connection con;
	
	public UserController(Javalin app, Connection con) {
		this.app = app;
		this.con = con;
		this.user = new UserAcctsDAO("username", "useraccts");
		
		app.get("/users/{username}", getUserByUsername);
		app.get("/users", getAllUserAccts);
		app.post("/users/{username_pwd_usertype}", createNewUser);
		app.put("/users/{username_pwd}", resetPWD);
		//app.delete("/users/{username}", deleteUser);
	}
	
	// Create a new User from a given User object
	/*public Handler createNewUser = ctx -> {
		// This line deserializes a JSON object from the body and creates a Java object out of it
		UserAcct u = ctx.bodyAsClass(User.class);
		
		if (user.createUser(u)) ctx.status(201); // Status code 201 means "created"
		else ctx.status(400);
	};*/
	
	// Retreive a User based on their username
	public Handler getUserByUsername = ctx -> {
		ctx.result(ctx.pathParam("username") + " exists :" + user.exist(ctx.pathParam("username")));
		// Status code 200 means "OK"
		ctx.status(200);
	};
	
	public Handler createNewUser = ctx -> {
		String[] ary = ctx.pathParam("username_pwd_usertype").split("_");
		user.newUser(con, ary[0], ary[1], ary[2]);
		
		// Status code 200 means "OK"
		ctx.status(200);
	};
	
	public Handler getAllUserAccts = ctx -> {
		List<UserAcct> users = user.getAllUserAccts();
		ctx.json(users);
		// Status code 200 means "OK"
		ctx.status(200);
	};
	
	public Handler resetPWD = ctx -> {
		String[] ary = ctx.pathParam("username_pwd").split("_");
		user.resetPWD(ary[0], ary[1]);
		ctx.status(200);
	};
	// Update a User using the data provided in the body
	/*public Handler updateUser = ctx -> {
		UserAcct u = ctx.bodyAsClass(User.class);
		if (user.updateUser(u)) ctx.status(204); // Status code 204 means "Successfully updated"
		// Status code 400 means "Error occurred"
		else ctx.status(400);
	};
	
	// Delete a User using the data provided in the body
	public Handler deleteUser = ctx -> {
		UserAcct u = ctx.bodyAsClass(User.class);
		if (user.deleteUser(u)) ctx.status(204); // Status code 204 means "Successfully updated"
		else ctx.status(400);
	};*/
}
