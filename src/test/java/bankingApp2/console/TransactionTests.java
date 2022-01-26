package bankingApp2.console;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.jupiter.api.BeforeAll;

import bankingApp2.dao.AccountsDAO;
import bankingApp2.dao.ConnectionManager;

public class TransactionTests {
	
	@BeforeAll
	public static void setupAllTests() {
		Connection con = ConnectionManager.getConnection();
		try (Statement stmt = con.createStatement()) {
			stmt.executeUpdate("INSERT INTO accounts (acctNum, acctType, balance, isjoint) VALUES (11, \'CHECKING\', 100.01, true), (12, \\'SAVING\\', 100.01, false);");
		} catch (SQLException e) { e.printStackTrace(); }
	}
	
	public void depositTests() {
		AccountsDAO accts = new AccountsDAO("acctNum", "accounts");
		Connection con = ConnectionManager.getConnection();
		accts.update(con, 11, 100.01, 0.99, "DEPOSIT");
		double res = -1;
		try (Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT balance FROM accounts WHERE acctNum = 11");) {
				rs.next();
				res = rs.getDouble("balance");
		} catch (SQLException e) { e.printStackTrace(); }
		

		assertEquals(res, 101);
	}
	
}
