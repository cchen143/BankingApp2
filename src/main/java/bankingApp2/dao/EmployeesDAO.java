package bankingApp2.dao;

import static bankingApp2.dao.Utils.*;
import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class EmployeesDAO extends TrackerDAO {
	
	public EmployeesDAO(String col, String table) {
		super(col, table);
	}
	
	public boolean eAcctExists() {
		System.out.println("Employee account exists.\n");
		return true;
	}
	
	public boolean exist(String eid) {
		Connection con = ConnectionManager.getConnection();
		boolean res = false;
		try (PreparedStatement pstmt = con.prepareStatement(SELECT + ALL + FROM + "employees" + WHERE + "eid = ?;")) {
				pstmt.setString(1, eid);
				try ( ResultSet rs = pstmt.executeQuery()) {
					res = rs.next();
				}
		} catch (SQLException e) { e.printStackTrace(); }
		return res;
	}
	
	public void newEmployee(String eid, String name,String username) {
		//INSERT INTO (Cols) VALUES (vals),	    
		Connection con = ConnectionManager.getConnection();
		try (PreparedStatement  pstmt = con.prepareStatement(INSERT_INTO + "employees (eID, name, username)" + VALUES + "( ?, ?, ?);");) {
			
			pstmt.setString(1, eid);
			pstmt.setString(2, name);
			pstmt.setString(3, username);
			pstmt.executeUpdate();
			
		} catch (SQLException e) { e.printStackTrace(); } 
		super.elements.add(eid);
	}
	
}
