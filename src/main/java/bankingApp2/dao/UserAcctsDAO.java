package bankingApp2.dao;

import static bankingApp2.dao.Utils.*;
import java.sql.*;

import bankingApp2.models.Customer;

public class UserAcctsDAO extends TrackerDAO<String> {
	
	public UserAcctsDAO(String col, String table) {
		super(col, table);
	}
	
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

	public void newUser(Connection con, String username, String pwd, String type) {
		//INSERT INTO (Cols) VALUES (vals),	    
		try (PreparedStatement pstmt = con.prepareStatement(INSERT_INTO + "useraccts (username, pwd, usertype)" + VALUES + "( ?, ?, ?);");) {
			pstmt.setString(1, username);
			pstmt.setString(2, pwd);
			pstmt.setString(3, type);
			pstmt.executeUpdate();
		} catch (SQLException e) { e.printStackTrace(); } 
		
		// add new username to set when an user account is created.
	}
	
	public void addToTemp(Customer... cs) {
		for (Customer c: cs) {
			this.elements.add(c.getUserName());
		}
	}
	
	//--------------------------------------------API-----------------------------------------------//
	public boolean exist(String val) {
		Connection con = ConnectionManager.getConnection();
		boolean res = false;
		try (PreparedStatement pstmt = con.prepareStatement(SELECT + ALL + FROM + "useraccts" + WHERE + "username" + " = ?;")) {
			pstmt.setString(1, val);
			try (ResultSet rs = pstmt.executeQuery()) { res = rs.next(); } 
		} catch (SQLException e) { e.printStackTrace(); }
		return res;
	}
	
}
