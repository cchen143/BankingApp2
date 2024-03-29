package bankingApp2.dao;

import static bankingApp2.dao.Utils.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import bankingApp2.models.UserAcct;

public interface DAO {
	
	

	default boolean exist(String table, String col, Object val) {
		Connection con = ConnectionManager.getConnection();
		boolean res = false;
		try (PreparedStatement pstmt = con.prepareStatement(SELECT + ALL + FROM + table + WHERE + col + " = ?;")) {
			if (val instanceof Integer) pstmt.setInt(1,(Integer) val);
			else pstmt.setString(1, (String) val);
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
			System.out.println("");
		} catch (SQLException e) { e.printStackTrace(); }
	}
	
	default void delete(Connection con, String col, String table, Object val) {
		try (PreparedStatement pstmt = con.prepareStatement(DELETE + FROM + table + WHERE + col + " = ?");) {
			if (val instanceof Integer) pstmt.setInt(1,(Integer) val);
			else pstmt.setString(1, (String) val);
			pstmt.executeUpdate();
		} catch (SQLException e) { e.printStackTrace(); }
	}
	
	
	//--------------------------------------------API-----------------------------------------------//
	
}
