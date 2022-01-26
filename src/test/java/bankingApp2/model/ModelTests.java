package bankingApp2.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import bankingApp2.models.Account;

public class ModelTests {
	Account checking = new Account(123, "CHECKING", 1.0, true);
	Account saving = new Account(123, "SAVING", 1.0, false);
	

	@Test
	public void getterTests() {
		

		assertEquals(checking.getAcctNum(), 123);
		assertTrue(checking.getAcctType().equals("CHECKING"));
		assertTrue(saving.getAcctType().equals("SAVING"));
		assertTrue(checking.isJoint());
		assertFalse(saving.isJoint());
		
	}
}
