package bankingApp2.dao;

import static bankingApp2.dao.Utils.*;
import java.sql.*;

public class AccountsDAO implements DAO {
	
	
	public boolean invalidAmount() { System.out.println("Invalid amount.\n"); return true; }
	
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

	public void update(String acctNum, double balance, double amount, String type) {
		//UPDATE table_name SET column1 = value1, column2 = value2, ... WHERE condition;
		Connection con = ConnectionManager.getConnection();
		double value = (type.equals("DEPOSIT")) ? balance + amount: balance - amount;
		try (PreparedStatement pstmt = con.prepareStatement(UPDATE + "accounts" + SET + "balance = ?" + WHERE + "acctNum = ?;")) {
			pstmt.setDouble(1, value);
			pstmt.setString(2, acctNum);
			pstmt.executeUpdate();
		} catch (SQLException e) { e.printStackTrace();}
	}
	
	public void transfer(String acctNum1, String acctNum2, double deposit, double withdrawal) {
		//UPDATE table_name SET column1 = value1, column2 = value2, ... WHERE condition;
		Connection con = ConnectionManager.getConnection();
		try (PreparedStatement pstmt = con.prepareStatement(UPDATE + "accounts" + SET + "balance = ?" + WHERE + "acctNum = ?;")) {
			pstmt.setDouble(1, withdrawal);
			pstmt.setString(2, acctNum1);
			pstmt.addBatch();
			pstmt.setDouble(1, deposit);
			pstmt.setString(2, acctNum2);
			pstmt.addBatch();
			pstmt.executeBatch();
		} catch (SQLException e) { e.printStackTrace(); }
	}
	
	public void close(String acctNum, String... tables) {
		Connection con = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(UPDATE + "accounts" + SET + "balance = ?" + WHERE + "acctNum = ?;");
			pstmt.setDouble(1, 0);
			pstmt.setString(2, acctNum);
			pstmt.executeUpdate();
			for (String table : tables) {
				pstmt = con.prepareStatement(DELETE + FROM + table + WHERE + "acctnum = ?");
				pstmt.setString(1, acctNum);
				pstmt.executeUpdate();
			}
			con.commit();
		} catch (SQLException e) { 
			try { con.rollback(); } catch (SQLException e2) { e2.printStackTrace(); }
			e.printStackTrace(); 
		} finally {
			try { if (pstmt != null) pstmt.close(); } catch (SQLException e3) { e3.printStackTrace(); }
		}
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
}
