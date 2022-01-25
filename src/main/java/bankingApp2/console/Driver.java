package bankingApp2.console;

import java.sql.*;
import java.util.Scanner;

import bankingApp2.controller.AccountController;
import bankingApp2.controller.CustomerController;
import bankingApp2.controller.UserController;
import bankingApp2.dao.ConnectionManager;
import io.javalin.Javalin;

public class Driver {
	
	public static void main(String[] args) throws Exception{
		
		Scanner sc  = new Scanner(System.in);
		System.out.println("1. API | 2. Banking app");
		String option = sc.nextLine();
		
		if (option.equals("2")) {
			try {
				Connection con = ConnectionManager.getConnection();
				Menu menu = new Menu();
				menu.main(sc, con);
				
				
			} finally {
				sc.close();
				try {
					ConnectionManager.getConnection().close();
				} catch (SQLException e) { e.printStackTrace(); }
			}
			
		} else {
			Javalin app = Javalin.create().start(7070);
			Connection con = ConnectionManager.getConnection();
			UserController control1 = new UserController(app, con);
			CustomerController control2 = new CustomerController(app, con);
			AccountController control3 = new AccountController(app, con);

		}
		
		
	}		
}

	
