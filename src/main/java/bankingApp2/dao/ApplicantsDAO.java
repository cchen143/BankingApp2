package bankingApp2.dao;

import static bankingApp2.dao.Utils.*;
import java.sql.*;
import java.util.HashSet;
import java.util.Set;

import bankingApp2.models.Customer;

//joint table; customers and applications
public class ApplicantsDAO implements DAO {
	Set<Customer> temp;
	
	public ApplicantsDAO() {
		this.temp = new HashSet<>();
	}
	
	public void add(Customer c) { this.temp.add(c); }
	
	public void newApplicants(Connection con, int appID) {
		//INSERT INTO (Cols) VALUES (vals),	    
		try (PreparedStatement  pstmt = con.prepareStatement(INSERT_INTO + "applicants (appID, cID, name, address, dob)" + VALUES + "( ?, ?, ?, ? ,?);");) {
			for (Customer c: this.temp) {
				pstmt.setInt(1, appID);
				pstmt.setInt(2, c.getCID());
				pstmt.setString(3, c.getName());
				pstmt.setString(4, c.getAddress());
				pstmt.setString(5, c.getDOB());
				pstmt.addBatch();
			}
			pstmt.executeBatch();
		} catch (SQLException e) { e.printStackTrace(); }
	}
	
	//
	public Customer[] getApplicants() { return this.temp.toArray(new Customer[temp.size()]); }
	
	public void cacheApplicants(Connection con, int appID) {
		try (PreparedStatement pstmt = con.prepareStatement(SELECT + ALL + FROM + "applicants" + WHERE + "appID = ?;")) {
				pstmt.setInt(1, appID);
				try ( ResultSet rs = pstmt.executeQuery()) {
					while(rs.next()) {
						this.temp.add(new Customer(rs.getInt("cID"), rs.getString("name"), rs.getString("address"), rs.getString("dob"), null));
					}
				}
		} catch (SQLException e) { e.printStackTrace(); }
	}
	
	
	
	public boolean check(Customer c) { return this.temp.contains(c); }
	
	public void removeAll() {
		this.temp = new HashSet<>();
	}
	
}
