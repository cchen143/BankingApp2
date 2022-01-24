package bankingApp2.dao;

import java.sql.*;
import static bankingApp2.dao.Utils.*;

public interface DAO {
	
	default boolean exist(String table, String col, String value) {
		Connection con = ConnectionManager.getConnection();
		boolean res = false;
		try (PreparedStatement pstmt = con.prepareStatement(SELECT + ALL + FROM + table + WHERE + col + " = ?;")) {
			pstmt.setString(1, value);
			try (ResultSet rs = pstmt.executeQuery()) { res = rs.next(); } 
		} catch (SQLException e) { e.printStackTrace(); }
		return res;
	}
	
	//Prints all records in a given table 
	default void printAll (String table) {
		Connection con = ConnectionManager.getConnection();
		try (Statement pstmt = con.createStatement();
				ResultSet rs = pstmt.executeQuery(SELECT + ALL + FROM + table + " ;")) { 
				ResultSetMetaData rsmd = rs.getMetaData();
				int colNum = rsmd.getColumnCount();
				while(rs.next()) {
				    for (int i = 1; i <= colNum; i++) {
				        System.out.print(rsmd.getColumnName(i).toUpperCase() + " : " + rs.getString(i).trim() + " ");
				    }
				    System.out.println("");
				}
		} catch (SQLException e) { e.printStackTrace(); }
	}
}

