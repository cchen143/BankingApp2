package bankingApp2.dao;

import static bankingApp2.dao.Utils.INSERT_INTO;
import static bankingApp2.dao.Utils.VALUES;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import bankingApp2.models.Customer;

//joint table; customers and accounts
public class OwnersDAO implements DAO{
	
	public void newOwners(Connection con, String acctNum, Customer... cs) {
		try (PreparedStatement pstmt = con.prepareStatement(INSERT_INTO + "owners (acctNum, cID)" + VALUES + "( ?, ?);");) {
			for (Customer c : cs) {
				pstmt.setString(1, acctNum);
				pstmt.setString(2, c.getCID());
				pstmt.addBatch();
			}
			pstmt.executeBatch();
		} catch (SQLException e) { e.printStackTrace(); }
	}
}
