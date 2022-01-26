package bankingApp2.console;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import bankingApp2.dao.AccountsDAO;
import bankingApp2.dao.ConnectionManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TransactionTests {
	Connection con;
	AccountsDAO accts;
	Scanner sc = new Scanner(System.in);
	
	@BeforeAll
	public void setupAllTests() {
		con = ConnectionManager.getConnection();
		accts = new AccountsDAO("acctNum", "accounts");
		try (Statement stmt = con.createStatement()) {
			stmt.executeUpdate("INSERT INTO accounts (acctNum, acctType, balance, isjoint) VALUES (11, \'CHECKING\', 100.01, true), (12, \'SAVING\', 0, true);");
		} catch (SQLException e) { e.printStackTrace(); }
		
		

	

	}
	
	@Test
	public void depositTests() {
		//acctnum 11 amount 0.99
		String input = "11\n0.99\n";
		InputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		Transaction.deposit(sc, accts);
		double res = -1;
		try (Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT balance FROM accounts WHERE acctNum = 11");) {
				rs.next();
				res = rs.getDouble("balance");
		} catch (SQLException e) { e.printStackTrace(); }
		assertEquals(res, 101);
	}
	/*
	@Test
	public void withdrawTests() {
		//acctnum 11 amount 1
		Transaction.withdraw(sc, accts);
		double res = -1;
		try (Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT balance FROM accounts WHERE acctNum = 11");) {
				rs.next();
				res = rs.getDouble("balance");
		} catch (SQLException e) { e.printStackTrace(); }
		assertEquals(res, 100);
	}
	/*
	@Test
	public void transferTests() {
		//acctnum1 11 acctnum2 12 amount 50
		Transaction.transfer(sc, accts);
		double[] res = new double[2];
		try (Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT balance FROM accounts WHERE acctNum = 11 or acctNum");) {
				int i = 0;
				while(rs.next()) {
					res[i++] = rs.getDouble("balance");
				}
		} catch (SQLException e) { e.printStackTrace(); }
		assertEquals(res[0], 50);
		assertEquals(res[1], 50);
	}
	
	*/
	@AfterAll
	public void cleanupForAllTests() {

		try (Statement stmt = con.createStatement()) {
			stmt.executeUpdate("DELETE FROM accounts WHERE acctNum = 11 or acctNum = 12;");
		} catch (SQLException e) { e.printStackTrace(); }
		
		try {
			if (con != null) con.close();
		} catch (SQLException e) { e.printStackTrace(); }
	}
	
}
