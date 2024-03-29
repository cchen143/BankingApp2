package bankingApp2.dao;

import static bankingApp2.dao.Utils.*;
import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class EmployeesDAO extends TrackerDAO<Integer> {
	
	//Cache
	//Pulls all employee IDs from DB when a Menu Object is created.
	//Keeps track the employees.eID during each user session.
	//Gets updated when a new employee account is created.

	//protected Set<T> elements; from TrackDAO
	
	public EmployeesDAO(String col, String table) {
		super(col, table);
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
	
	public void newEmployee(Connection con, int eid, String name,String username) {
		//INSERT INTO (Cols) VALUES (vals),	    
		try (PreparedStatement  pstmt = con.prepareStatement(INSERT_INTO + "employees (eID, name, username)" + VALUES + "( ?, ?, ?);");) {
			
			pstmt.setInt(1, eid);
			pstmt.setString(2, name);
			pstmt.setString(3, username);
			pstmt.executeUpdate();
			
		} catch (SQLException e) { e.printStackTrace(); } 
		//in Transaction
		//this.elements.add(eid);
	}
	
}
