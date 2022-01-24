package bankingApp2.dao;

import static bankingApp2.dao.Utils.*;
import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class EmployeesDAO implements DAO {
	
	Set<String> eids;
	
	public EmployeesDAO() {
		eids = new HashSet<>();
		Connection con = ConnectionManager.getConnection();
		try (Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(SELECT + "eID" + FROM + "employees;")) {
			while (rs.next()) {
				eids.add(rs.getString("eID"));
			}
		} catch (SQLException e) { e.printStackTrace(); }
	}
	
	public boolean invalidOPT() {
		System.out.println("Invalid option.\n");
		return true;
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
	
	public boolean checkEID(String eid) { return eids.contains(eid); } 
	
	public void newEmployee(String eid, String name,String username) {
		//INSERT INTO (Cols) VALUES (vals),	    
		Connection con = ConnectionManager.getConnection();
		try (PreparedStatement  pstmt = con.prepareStatement(INSERT_INTO + "employees (eID, name, username)" + VALUES + "( ?, ?, ?);");) {
			
			pstmt.setString(1, eid);
			pstmt.setString(2, name);
			pstmt.setString(3, username);
			pstmt.executeUpdate();
			
		} catch (SQLException e) { e.printStackTrace(); } 
		eids.add(eid);
	}
}
