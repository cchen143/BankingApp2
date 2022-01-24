package bankingApp2.dao;

import static bankingApp2.dao.Utils.*;
import java.sql.*;
import bankingApp2.models.Customer;
import bankingApp2.models.Employee;

public class UserAcctsDAO implements DAO {

	public boolean doesNotMatch() {
		System.out.println("Incorrect username or password.\n");
		return true;
	}

	
	public boolean uAcctExists() {
		System.out.println("User account exists.\n");
		return true;
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

	public void newUser(String username, String pwd, String type) {
		//INSERT INTO (Cols) VALUES (vals),	    
		Connection con = ConnectionManager.getConnection();
		try (PreparedStatement pstmt = con.prepareStatement(INSERT_INTO + "useraccts (username, pwd, usertype)" + VALUES + "( ?, ?, ?);");) {
			pstmt.setString(1, username);
			pstmt.setString(2, pwd);
			pstmt.setString(3, type);
			pstmt.executeUpdate();
		} catch (SQLException e) { e.printStackTrace(); } 
	}
	
}
