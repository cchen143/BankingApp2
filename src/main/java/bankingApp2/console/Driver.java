package bankingApp2.console;

import java.sql.*;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import bankingApp2.dao.ConnectionManager;

public class Driver {
	
	public static void main(String[] args) throws Exception{
		
		Scanner sc  = new Scanner(System.in);
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
	}		
}

	
