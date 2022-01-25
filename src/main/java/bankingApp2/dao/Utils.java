package bankingApp2.dao;

import java.util.Scanner;

public class Utils {
	public static final String SELECT = "SELECT ";
	
	public static final String INSERT_INTO = "INSERT INTO ";
	
	public static final String UPDATE = "UPDATE ";
	
	public static final String DELETE = "DELETE";
	
	public static final String ALL = "*";
	
	public static final String WHERE = " WHERE ";
	
	public static final String FROM = " FROM ";
	
	public static final String VALUES = " VALUES ";
	
	public static final String SET = " SET ";
	
	public static final String ON = " ON ";
	
	public static final String JOIN = " JOIN ";
	
	public static final String DOESNOTMATCH = "Incorrect username or password.\n";
	
	public static final String USERNAMENA = "Username is not available.\n";
	
	public static final String EACCTEXISTS = "Employee account exists.\n";
	
	public static final String CACCTNOTEXIST = "Customer account does not exist.\n";
	
	public static final String INVALIDAMOUNT = "Invalid amount.\n";
	
	public static final String INVALIDOPTION = "Invalid option.\n";
	
	public static final String APPNOTEXISTS = "Application does not exist.\n"; 
	
	public static final String INVALIDINPUT = "Invalid input.\n";
	
	public static boolean errFunc(String errormessage) { System.out.println(errormessage); return true; }

	public static boolean isPNum(String str) { return str.matches("[1-9]\\d*(\\.*\\d*)?") || str.matches("0\\.\\d*[1-9]"); }
	
	public static boolean isPInt(String str) { return str.matches("[1-9]\\d*"); }
	
	public static String choose2(Scanner sc, String opt, String err) {
		String res;
		do {
			System.out.println(opt);
			res = sc.nextLine();
			if (!(res.equals("1") || res.equals("2"))) System.out.println(err);
		} while (!(res.equals("1") || res.equals("2")));
		return res;
	}
	
	public static String choose3(Scanner sc, String opt, String err) {
		
		String res;
		do {
			System.out.println(opt);
			res = sc.nextLine();
			if (!(res.equals("1") || res.equals("2") || res.equals("3"))) System.out.println(err);
		} while (!(res.equals("1") || res.equals("2") || res.equals("3")));
		return res;
	}
	
	public static String choose6(Scanner sc, String opt, String err) {
		String res;
		do {
			System.out.println(opt);
			res = sc.nextLine();
			if (!(res.equals("1") || res.equals("2") || res.equals("3") || res.equals("4") || res.equals("5") || res.equals("6"))) System.out.println(err);
		} while (!(res.equals("1") || res.equals("2") || res.equals("3") || res.equals("4") || res.equals("5") || res.equals("6")));
		return res;
	}
	
	public static String choose7(Scanner sc, String opt, String err) {
		String res;
		do {
			System.out.println(opt);
			res = sc.nextLine();
			if (!(res.equals("1") || res.equals("2") || res.equals("3") || res.equals("4") || res.equals("5") || res.equals("6") || res.equals("7"))) System.out.println(err);
		} while (!(res.equals("1") || res.equals("2") || res.equals("3") || res.equals("4") || res.equals("5") || res.equals("6") || res.equals("7")));
		return res;
	}
	
	public static int getPInt(Scanner sc, String text, String err) {
		String temp = "";
		do {
			System.out.println(text);
			temp = sc.nextLine();
			if (!isPInt(temp)) System.out.println(err);
		} while (!isPInt(temp));
		return Integer.parseInt(temp);
	}
	
	public static double getPNum(Scanner sc, String text, String err) {
		String temp = "";
		do {
			System.out.println(text);
			temp = sc.nextLine();
			if (!isPNum(temp)) System.out.println(err);
		} while (!isPNum(temp));
		return Double.parseDouble(temp);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static String getOneInput(Scanner sc, String text, TrackerDAO dao, String err) {
		String res = "";
		do {
			System.out.println(text);
			res = sc.nextLine();
			if (res.equals("") && dao.check(res)) System.out.println(err);
		} while (res.equals("") && dao.check(res));
		return res;
	}
}

