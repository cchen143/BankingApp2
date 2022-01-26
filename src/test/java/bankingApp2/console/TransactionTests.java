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
			stmt.executeUpdate("INSERT INTO accounts (acctNum, acctType, balance, isjoint) VALUES (11, \'CHECKING\', 105.0, true), (12, \'SAVING\', 0, true);");
		} catch (SQLException e) { e.printStackTrace(); }

	}
	
	@Test
	public void depositTests() {
		//acctnum 11 amount 5
		
		Transaction.deposit(sc, accts);
		double res = -1;
		try (Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT balance FROM accounts WHERE acctNum = 11");) {
				rs.next();
				res = rs.getDouble("balance");
		} catch (SQLException e) { e.printStackTrace(); }
		assertEquals(105, res);
	}
	@Test
	public void withdrawTests() {
		//acctnum 11 amount 5
		Transaction.withdraw(sc, accts);
		double res = -1;
		try (Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT balance FROM accounts WHERE acctNum = 11");) {
				rs.next();
				res = rs.getDouble("balance");
		} catch (SQLException e) { e.printStackTrace(); }
		assertEquals(100, res);
	}
	
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
