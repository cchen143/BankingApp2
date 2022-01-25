package bankingApp2.controller;

import bankingApp2.dao.UserAcctsDAO;
import bankingApp2.models.UserAcct;
import io.javalin.Javalin;
import io.javalin.http.Handler;

public class UserController {

	Javalin app;
	UserAcctsDAO user = new UserAcctsDAO("username", "useraccts");
	
	public UserController(Javalin app) {
		this.app = app;
		
		app.get("/users/{username}", getUserByUsername);
		//app.post("/users", createNewUser);
		//app.put("/users/{username}", updateUser);
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
		//ctx.json(u);
		
		// Status code 200 means "OK"
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
