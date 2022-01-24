package bankingApp2.dao;

import static bankingApp2.dao.Utils.*;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;
import bankingApp2.models.Customer;
	
public class CustomersDAO extends TrackerDAO {
	
	public CustomersDAO(String col, String table) {
		super(col, table);
	}
	
	// Util Functions
	public boolean cAcctExists() { System.out.println("Customer account exists.\n"); return true; }
	
	public boolean cAcctNotExists() { System.out.println("Customer account does not exist.\n"); return true; }
	
	
	//Data Access
	public boolean exist(String name, String address, String dob) {
		Connection con = ConnectionManager.getConnection();
		boolean res = false;
		try (PreparedStatement pstmt = con.prepareStatement(SELECT + ALL + FROM + "customers" + WHERE + " name = ? and address = ? and dob = ?;")) {
			pstmt.setString(1, name);
			pstmt.setString(2, address);
			pstmt.setString(3, dob);
			try (ResultSet rs = pstmt.executeQuery()) { res = rs.next(); } 
		} catch (SQLException e) { e.printStackTrace(); }
		return res;
	}
	
	public boolean getCAcct(String name, String address, String dob, Customer c) {
		Connection con = ConnectionManager.getConnection();
		boolean res = false;
		try (PreparedStatement pstmt = con.prepareStatement(SELECT + ALL + FROM + "customers" + WHERE + "name = ? and address = ? and dob = ?;")) {
				pstmt.setString(1, name);
				pstmt.setString(2, address);
				pstmt.setString(3, dob);
				try ( ResultSet rs = pstmt.executeQuery()) {
					if (rs.next()) {
						c.setCID(rs.getString("cid"));
						c.setName(rs.getString("name"));
						c.setAddress(rs.getString("address"));
						c.setDOB(rs.getString("dob"));
						c.setUserName(rs.getString("username"));
						res = true;
					}
				}
		} catch (SQLException e) { e.printStackTrace(); }
		return res;
	}
	
	public void newCustomer(String cid, String name, String address, String dob, String username) {
		//INSERT INTO (Cols) VALUES (vals),	    
		Connection con = ConnectionManager.getConnection();
		try (PreparedStatement pstmt = con.prepareStatement(INSERT_INTO + "customers (cID, name, address, dob, username)" + VALUES + "( ?, ?, ?, ?, ?);");) {
			
			pstmt.setString(1, cid);
			pstmt.setString(2, name);
			pstmt.setString(3, address);
			pstmt.setString(4, dob);
			pstmt.setString(5, username);
			pstmt.executeUpdate();
			
		} catch (SQLException e) { e.printStackTrace(); }
		this.elements.add(cid);
	}
	
	public Customer getCustomer(String username) {
		Connection con = ConnectionManager.getConnection();
		Customer c = null;
		try (PreparedStatement pstmt = con.prepareStatement(SELECT + ALL + FROM + "customers" + WHERE + "customers.username = ?;")) {
				pstmt.setString(1, username);
				try ( ResultSet rs = pstmt.executeQuery()) {
					rs.next();
					c = new Customer(rs.getString("cID"), rs.getString("name"), rs.getString("address"), rs.getString("dob"), username);
				}
		} catch (SQLException e) { e.printStackTrace(); }
		return c;
	}
	
	public void updateUsername(String name, String address, String dob, String username) { 
		Connection con = ConnectionManager.getConnection();
		try (PreparedStatement pstmt = con.prepareStatement(UPDATE + "customers" + SET + "username = ?" + WHERE + "name = ? and address = ? and dob = ?;")) {
			pstmt.setString(1, username);
			pstmt.setString(2, name);
			pstmt.setString(3, address);
			pstmt.setString(4, dob);
			pstmt.executeUpdate();
		} catch (SQLException e) { e.printStackTrace();}
	}
	
}
