package bankingApp2.controller;


public class UserController {

	Javalin app;
	UserDao userDao;
	
	public UserController(Javalin app) {
		this.app = app;
		this.conn = conn;
		userDao = new UserDao(conn);
		
		app.get("/users/{username}", getUserByUsername);
		app.post("/users", createNewUser);
		app.put("/users/{username}", updateUser);
		app.delete("/users/{username}", deleteUser);
	}
	
	// Create a new User from a given User object
	public Handler createNewUser = ctx -> {
		// This line deserializes a JSON object from the body and creates a Java object out of it
		User u = ctx.bodyAsClass(User.class);
		
		if (userDao.createUser(u))
			ctx.status(201); // Status code 201 means "created"
		else ctx.status(400);
	};
	
	// Retreive a User based on their username
	public Handler getUserByUsername = ctx -> {
		User u = userDao.getUserByUsername(ctx.pathParam("username"));
		ctx.json(u);
		
		// Status code 200 means "OK"
		ctx.status(200);
	};
	
	// Update a User using the data provided in the body
	public Handler updateUser = ctx -> {
		User u = ctx.bodyAsClass(User.class);
		if (userDao.updateUser(u))
			// Status code 204 means "Successfully updated"
			ctx.status(204);
		// Status code 400 means "Error occurred"
		else ctx.status(400);
	};
	
	// Delete a User using the data provided in the body
	public Handler deleteUser = ctx -> {
		User u = ctx.bodyAsClass(User.class);
		if (userDao.deleteUser(u))
			ctx.status(204);
		else ctx.status(400);
	};
}
