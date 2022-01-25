package bankingApp2.console;

import java.sql.*;
import java.util.Scanner;

import bankingApp2.controller.UserController;
import bankingApp2.dao.ConnectionManager;
import io.javalin.Javalin;

public class Driver {
	
	public static void main(String[] args) throws Exception{
		
		Scanner sc  = new Scanner(System.in);
		try {
			Connection con = ConnectionManager.getConnection();
			Menu menu = new Menu();
			menu.main(sc, con);
			System.out.println("here");
			Javalin app = Javalin.create().start(7070);
			UserController controller = new UserController(app);
			
		} finally {
			sc.close();
			try {
				ConnectionManager.getConnection().close();
			} catch (SQLException e) { e.printStackTrace(); }
		}
		
	}		
}

	
