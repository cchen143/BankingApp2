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
	}

	public Handler getUserByUsername = ctx -> {
		ctx.result(ctx.pathParam("username") + " exists :" + user.exist(ctx.pathParam("username")));
		ctx.status(200);
	};
	
	public Handler createNewUser = ctx -> {
		String[] ary = ctx.pathParam("username_pwd_usertype").split("_");
		user.newUser(con, ary[0], ary[1], ary[2]);
		ctx.status(200);
	};
	
	public Handler getAllUserAccts = ctx -> {
		List<UserAcct> users = user.getAllUserAccts();
		ctx.json(users);
		ctx.status(200);
	};
	
	public Handler resetPWD = ctx -> {
		String[] ary = ctx.pathParam("username_pwd").split("_");
		user.resetPWD(ary[0], ary[1]);
		ctx.status(200);
	};
}
