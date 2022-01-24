package bankingApp2.dao;

import static bankingApp2.dao.Utils.ALL;
import static bankingApp2.dao.Utils.FROM;
import static bankingApp2.dao.Utils.INSERT_INTO;
import static bankingApp2.dao.Utils.SELECT;
import static bankingApp2.dao.Utils.VALUES;
import static bankingApp2.dao.Utils.WHERE;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bankingApp2.models.Application;
import bankingApp2.models.Customer;

//joint table; customers and applications
public class ApplicantsDAO implements DAO {
	List<Customer> apcs;
	
	public ApplicantsDAO() {
		apcs = new ArrayList<>();
	}
	
	public void add(Customer c) { apcs.add(c); }
	
	public void newApplicants(Connection con, String appID) {
		//INSERT INTO (Cols) VALUES (vals),	    
		try (PreparedStatement  pstmt = con.prepareStatement(INSERT_INTO + "applicants (appID, cID, name, address, dob)" + VALUES + "( ?, ?, ?, ? ,?);");) {
			for (Customer c: apcs) {
				pstmt.setString(1, appID);
				pstmt.setString(2, c.getCID());
				pstmt.setString(3, c.getName());
				pstmt.setString(4, c.getAddress());
				pstmt.setString(5, c.getDOB());
				pstmt.addBatch();
			}
			pstmt.executeBatch();
			apcs = new ArrayList<>();
		} catch (SQLException e) { e.printStackTrace(); }
	}
	
	public List<Customer> getApplicants(Connection con, String appID) {
		List<Customer> cs = null;
		try (PreparedStatement pstmt = con.prepareStatement(SELECT + ALL + FROM + "applicants" + WHERE + "appID = ?;")) {
				pstmt.setString(1, appID);
				try ( ResultSet rs = pstmt.executeQuery()) {
					while(rs.next()) {
						cs.add(new Customer(rs.getString("cID"), rs.getString("name"), rs.getString("address"), rs.getString("dob"), null));
					}
				}
		} catch (SQLException e) { e.printStackTrace(); }
		return cs;
	}
	
	public boolean addToApcs(String... cs) {
		Connection con = ConnectionManager.getConnection();
		boolean res = false;
		try (PreparedStatement pstmt = con.prepareStatement(SELECT + ALL + FROM + "customers" + WHERE + "name = ? and address = ? and dob = ?;")) {
			for (int i = 0; i < cs.length; i += 3) {
				pstmt.setString(1, cs[i]);
				pstmt.setString(2, cs[i + 1]);
				pstmt.setString(3, cs[i + 2]);
				try ( ResultSet rs = pstmt.executeQuery()) {
					boolean flag = rs.next();
					res =  res | flag;
					if (flag) {
						this.apcs.add( new Customer(rs.getString("cid"), rs.getString("name"), rs.getString("address"),  rs.getString("dob"), rs.getString("username")));
					} else {
						this.apcs.add( new Customer(null, cs[i], cs[i + 1],  cs[i + 2], null));
					}
				}
			}
		} catch (SQLException e) { e.printStackTrace(); }
		return res;
	}
}
