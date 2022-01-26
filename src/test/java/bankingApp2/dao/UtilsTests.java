package bankingApp2.dao;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static bankingApp2.dao.Utils.*;

public class UtilsTests {
	
		@Test
		public void isPNumTests() {
			
			assertFalse(isPNum("0"));
			assertFalse(isPNum("0."));
			assertFalse(isPNum("0.0"));
			assertFalse(isPNum("a"));
			assertFalse(isPNum("@*^"));
			assertFalse(isPNum("0.*"));
			assertTrue(isPNum("01"));
			assertEquals(Double.parseDouble("1"), 1);
			assertTrue(isPNum("010"));
			assertEquals(Double.parseDouble("010"), 10);
			assertTrue(isPNum("0.1"));
			assertEquals(Double.parseDouble("0.1"), 0.1);
			assertTrue(isPNum("0.10"));
			assertEquals(Double.parseDouble("0.10"), 0.1);
			assertTrue(isPNum("1.0"));
			assertEquals(Double.parseDouble("1.0"), 1.0);
			assertTrue(isPNum("10"));
			assertEquals(Double.parseDouble("10"), 10);
			assertTrue(isPNum("100"));
			
		}
		
		@Test
		public void isPIntTests() {
			assertFalse(isPInt("0"));
			assertFalse(isPInt("0."));
			assertFalse(isPInt("0.0"));
			assertFalse(isPInt("a"));
			assertFalse(isPInt("@*^"));
			assertFalse(isPInt("0.*"));
			assertFalse(isPInt("0.1"));
			assertFalse(isPInt("1.0"));
			assertTrue(isPInt("01"));
			assertEquals(Integer.parseInt("01"), 1);
			assertTrue(isPInt("010"));
			assertEquals(Integer.parseInt("010"), 10);
			assertTrue(isPInt("10"));
			assertEquals(Integer.parseInt("10"), 10);
			assertTrue(isPInt("100"));
			
		}
		
		
		/*
		 //assertNotNull(machine);
		 assertNotEquals(x, 0);
		assertEquals(x, 5);
		@Test
		public void myThirdTest() {
			assertThrows(ArithmeticException.class, () -> machine.divide(1, 0));
		}
		
		// The @BeforeEach annotation marks a method that should run before each test is run
		@BeforeEach
		public void setupEachTest() {
			
			machine = new ArithmeticMachine();
			System.out.println("Im in a before each!");
		}
		
		@AfterEach
		public void cleanupEachTest() {
			System.out.println("In an after each!");
		}
		
		@BeforeAll
		public static void setupAllTests() {
			System.out.println("Im in a before all!");
		}
		
		@AfterAll
		public static void cleanupForAllTests() {
			System.out.println("Im in an after all!");
		}*/
}
