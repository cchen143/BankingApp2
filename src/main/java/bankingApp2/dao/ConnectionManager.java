package bankingApp2.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {

	private static Connection connection;
	
	private static String connectionString = "jdbc:postgresql://kashin.db.elephantsql.com:5432/lbfcfcll",
			username = "lbfcfcll",
			password = "wVNXJofEe4RM7CDtMm_kAlTzP0uIqhIo";
	
	public static Connection getConnection() {
		try {
			if (connection == null) {
			
				Class.forName("org.postgresql.Driver");
				connection = DriverManager.getConnection(connectionString, username, password);
			}
			
			return connection;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	// When the program is stopped, this will trigger and close the connection
	// You have to use the stop button in your IDE. Similar to finally clause in main()
	@Override
	public void finalize() {
		try {
			if (connection != null) connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}