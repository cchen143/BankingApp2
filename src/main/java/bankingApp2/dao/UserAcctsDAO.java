package bankingApp2.dao;

import static bankingApp2.dao.Utils.*;
import java.sql.*;
import java.util.HashSet;
import java.util.Set;

import bankingApp2.models.Customer;
import bankingApp2.models.Employee;

public class UserAcctsDAO implements DAO {
	
	Set<String> usernames;
	
	public UserAcctsDAO() {
		usernames = new HashSet<String>();
		Connection con = ConnectionManager.getConnection();
		try (Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(SELECT + "username" + FROM + "useraccts;")) {
			while (rs.next()) {
				usernames.add(rs.getString("username"));
			}
		} catch (SQLException e) { e.printStackTrace(); }
	}

	public boolean doesNotMatch() {
		System.out.println("Incorrect username or password.\n");
		return true;
	}

	
	public boolean usernameNA() {
		System.out.println("Username is not available.\n");
		return true;
	}
	
	public boolean checkUsername(String username) { return usernames.contains(username); }
	
	public String validate(String username, String pwd) {
		Connection con = ConnectionManager.getConnection();
		String res = "";
		try (PreparedStatement pstmt = con.prepareStatement(SELECT + ALL + FROM + "useraccts" + WHERE + "username = ? and pwd = ?;")) {
				pstmt.setString(1, username);
				pstmt.setString(2, pwd);
				try ( ResultSet rs = pstmt.executeQuery()) {
					if (rs.next()) res = rs.getString("usertype");
				}
		} catch (SQLException e) { e.printStackTrace(); }
		return res;
	}

	public void newUser(String username, String pwd, String type) {
		//INSERT INTO (Cols) VALUES (vals),	    
		Connection con = ConnectionManager.getConnection();
		try (PreparedStatement pstmt = con.prepareStatement(INSERT_INTO + "useraccts (username, pwd, usertype)" + VALUES + "( ?, ?, ?);");) {
			pstmt.setString(1, username);
			pstmt.setString(2, pwd);
			pstmt.setString(3, type);
			pstmt.executeUpdate();
		} catch (SQLException e) { e.printStackTrace(); } 
		
		// add new username to set when an user account is created.
		usernames.add(username);
	}
	
}
