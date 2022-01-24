package bankingApp2.dao;

import static bankingApp2.dao.Utils.*;
import java.sql.*;

public class AccountsDAO extends TrackerDAO {
	
	public AccountsDAO(String acctNum, String accounts) {
		super(acctNum, accounts);
	}
	
	public double exist(String acctNum) {
		Connection con = ConnectionManager.getConnection();
		double res = -1;
		try (PreparedStatement pstmt = con.prepareStatement(SELECT + ALL + FROM + "accounts" + WHERE + "acctNum = ?;")) {
				pstmt.setString(1, acctNum);
				try ( ResultSet rs = pstmt.executeQuery()) {
					if (rs.next()) res = rs.getDouble("balance");
				}
		} catch (SQLException e) { e.printStackTrace(); }
		return res;
	}

	public void update(Connection con, String acctNum, double balance, double amount, String type) {
		//UPDATE table_name SET column1 = value1, column2 = value2, ... WHERE condition;
		double value = (type.equals("DEPOSIT")) ? balance + amount: balance - amount;
		try (PreparedStatement pstmt = con.prepareStatement(UPDATE + "accounts" + SET + "balance = ?" + WHERE + "acctNum = ?;")) {
			pstmt.setDouble(1, value);
			pstmt.setString(2, acctNum);
			pstmt.executeUpdate();
		} catch (SQLException e) { e.printStackTrace();}
	}
	
	public double printAcctInfo(String cid) {
		Connection con = ConnectionManager.getConnection();
		double totalBalance = 0;
		try(PreparedStatement pstmt = con.prepareStatement(SELECT + "accounts.*" + FROM + "accounts"  + 
				JOIN + "owners" + ON + "accounts.acctNum = owners.acctNum" + 
				JOIN + "customers" + ON + " customers.cID = owners.cID" + 
				WHERE + "customers.cID = ?")) {
			
			pstmt.setString(1, cid);
			try (ResultSet rs = pstmt.executeQuery()){
				
				ResultSetMetaData rsmd = rs.getMetaData();
				int colNum = rsmd.getColumnCount();
				
				while(rs.next()) {
					for (int i = 1; i <= colNum; i++) {
						System.out.print(rsmd.getColumnName(i).toUpperCase() + ": " + rs.getString(i).trim() + " ");
					}
					totalBalance += rs.getDouble("balance");
					System.out.println("");
				}
			}
		} catch (SQLException e) { e.printStackTrace(); } 
		return totalBalance;
	}
	
	public void newAccount() {
		
	}

}
