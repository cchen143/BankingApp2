package bankingApp2.dao;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Formatter;

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
	}
	
	public static boolean isPosNum(String str) {
		  return str.matches("[1-9]\\d*(\\.*\\d*)?") || str.matches("0\\.\\d*[1-9]");
	}
	
	
	
	
}
