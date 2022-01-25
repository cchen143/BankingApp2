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
	
	public static String choose2(Scanner sc, String opt, boolean errFunc) {
		String res;
		do {
			System.out.println(opt);
			res = sc.nextLine();
		} while (!(res.equals("1") || res.equals("2")) && errFunc);
		return res;
	}
	
	public static String choose3(Scanner sc, String opt, boolean errFunc) {
		String res;
		do {
			System.out.println(opt);
			res = sc.nextLine();
		} while (!(res.equals("1") || res.equals("2") || res.equals("3")) && errFunc);
		return res;
	}
	
	public static String choose6(Scanner sc, String opt, boolean errFunc) {
		String res;
		do {
			System.out.println(opt);
			res = sc.nextLine();
		} while (!(res.equals("1") || res.equals("2") || res.equals("3") || res.equals("4") || res.equals("5") || res.equals("6")) && errFunc);
		return res;
	}
	
	public static String choose7(Scanner sc, String opt, boolean errFunc) {
		String res;
		do {
			System.out.println(opt);
			res = sc.nextLine();
		} while (!(res.equals("1") || res.equals("2") || res.equals("3") || res.equals("4") || res.equals("5") || res.equals("6") || res.equals("7")) && errFunc);
		return res;
	}
	
	public static int getPInt(Scanner sc, String text, boolean errFunc) {
		String temp = "";
		do {
			System.out.println(text);
			temp = sc.nextLine();
		} while (isPInt(temp) && errFunc);
		return Integer.parseInt(temp);
	}
	
	public static double getPNum(Scanner sc, String text, boolean errFunc) {
		String temp = "";
		do {
			System.out.println(text);
			temp = sc.nextLine();
		} while (isPInt(temp) && errFunc);
		return Double.parseDouble(temp);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static String getOneInput(Scanner sc, String text, TrackerDAO dao, boolean errFunc) {
		String res = "";
		do {
			System.out.println(text);
			res = sc.nextLine();
		} while (res.equals("") && dao.check(res) && errFunc);
		return res;
	}
	
	
	//////NOT IN USE
	/*
	//set a condition by strings
	public static String setCondVal(String ... strs) {
		StringBuilder sb = new StringBuilder();
		sb.append(strs[0] + " = \'" + strs[1] + "\'");
		Boolean res = false;
		for (int i = 2; i < strs.length; i += 2 ) {
			sb.append(" and " + strs[i] + " = \'" + strs[i + 1] + "\'");
		}
		return sb.toString();
	}
	
	//set a condition by columns
	public static String setCondCol(String ... strs) {
		StringBuilder sb = new StringBuilder();
		sb.append(strs[0] + " = " + strs[1] );
		Boolean res = false;
		for (int i = 2; i < strs.length; i+=2) {
			sb.append(" and " + strs[i] + " = " + strs[i + 1]);
		}
		return sb.toString();
	}
	
	//set columns or tables
	public static String setString(String ... strs) {
		StringBuilder sb = new StringBuilder();
		sb.append(strs[0]);
		for (int i = 1; i < strs.length; i++) {
			sb.append(", " + strs[i]);
		}
		return sb.toString();
		
		static <T extends Serializable> T readObject(String path, Class<T> expectedClass) {
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(path));
			T result = expectedClass.cast(in.readObject());
			in.close();
			return result;
		} catch (IOException | ClassCastException| ClassNotFoundException excp) {
			throw new IllegalArgumentException(excp.getMessage());
		}
	}
	
	static void writeObject(String path, Object o) {
		try {
	         FileOutputStream fileOut =
	         new FileOutputStream(path);
	         ObjectOutputStream out = new ObjectOutputStream(fileOut);
	         out.writeObject(o);
	         out.close();
	         fileOut.close();
	      } catch (IOException i) {
	         i.printStackTrace();
	      }
	}
	
	static String sha1(String val) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(val.getBytes(StandardCharsets.UTF_8));
            Formatter result = new Formatter();
            for (byte b : md.digest()) {
                result.format("%02x", b);
            }
            result.close();
            return result.toString();
        } catch (NoSuchAlgorithmException excp) {
            throw new IllegalArgumentException("System does not support SHA-1");
        }
    }
	}*/
}
