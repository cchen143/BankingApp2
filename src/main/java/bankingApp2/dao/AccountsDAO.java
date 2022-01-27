package bankingApp2.dao;

import static bankingApp2.dao.Utils.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import bankingApp2.models.Account;

public class AccountsDAO extends TrackerDAO<Integer> {
	
	//Cache
	//Pulls all account numbers from DB when a Menu Object is created.
	//Keeps track the accounts.acctnum during each user session.
	//Gets updated when a new account is created or closed.
	
	//protected Set<T> elements; from TrackDAO
	
	public AccountsDAO(String acctNum, String accounts) {
		super(acctNum, accounts);
	}
	
	public double exist(int acctNum) {
		Connection con = ConnectionManager.getConnection();
		double res = -1;
		try (PreparedStatement pstmt = con.prepareStatement(SELECT + ALL + FROM + "accounts" + WHERE + "acctNum = ?;")) {
				pstmt.setInt(1, acctNum);
				try ( ResultSet rs = pstmt.executeQuery()) {
					if (rs.next()) res = rs.getDouble("balance");
				}
		} catch (SQLException e) { e.printStackTrace(); }
		return res;
	}

	public void update(Connection con, int acctNum, double balance, double amount, String type) {
		//UPDATE table_name SET column1 = value1, column2 = value2, ... WHERE condition;
		double value = (type.equals("DEPOSIT")) ? balance + amount: balance - amount;
		try (PreparedStatement pstmt = con.prepareStatement(UPDATE + "accounts" + SET + "balance = ?" + WHERE + "acctNum = ?;")) {
			pstmt.setDouble(1, value);
			pstmt.setInt(2, acctNum);
			pstmt.executeUpdate();
		} catch (SQLException e) { e.printStackTrace();}
	}
	
	public double printAcctInfo(int cid) {
		Connection con = ConnectionManager.getConnection();
		double totalBalance = 0;
		try(PreparedStatement pstmt = con.prepareStatement(SELECT + "accounts.*" + FROM + "accounts"  + 
				JOIN + "owners" + ON + "accounts.acctNum = owners.acctNum" + 
				JOIN + "customers" + ON + " customers.cID = owners.cID" + 
				WHERE + "customers.cID = ?")) {
			
			pstmt.setInt(1, cid);
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
	
	public void newAccount(Connection con, int acctNum, String acctType, double balance, boolean isjoint) {
		try (PreparedStatement pstmt = con.prepareStatement(INSERT_INTO + "accounts (acctNum, acctType, balance, isjoint)" + VALUES + "( ?, ?, ?, ?);");) {
			pstmt.setInt(1, acctNum);
			pstmt.setString(2, acctType);
			pstmt.setDouble(3, balance);
			pstmt.setBoolean(4, isjoint);
			pstmt.executeUpdate();
		} catch (SQLException e) { e.printStackTrace(); } 
	}
	
	//--------------------------------------------API-----------------------------------------------//
	public List<Account> getAllAccounts() {
		Connection con = ConnectionManager.getConnection();
		List<Account> accts = new ArrayList<>();
		try (Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM accounts;")) {
			while (rs.next()) {
				accts.add(new Account(rs.getInt("acctNum"), rs.getString("acctType"), rs.getDouble("balance"), rs.getBoolean("isjoint")));
			}
		} catch (SQLException e) { e.printStackTrace(); }
		
		return accts;
	}

}
