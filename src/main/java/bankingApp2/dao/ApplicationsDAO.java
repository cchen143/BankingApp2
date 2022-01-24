package bankingApp2.dao;

import static bankingApp2.dao.Utils.ALL;
import static bankingApp2.dao.Utils.DELETE;
import static bankingApp2.dao.Utils.FROM;
import static bankingApp2.dao.Utils.INSERT_INTO;
import static bankingApp2.dao.Utils.SELECT;
import static bankingApp2.dao.Utils.SET;
import static bankingApp2.dao.Utils.UPDATE;
import static bankingApp2.dao.Utils.VALUES;
import static bankingApp2.dao.Utils.WHERE;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bankingApp2.models.Application;
import bankingApp2.models.Customer;

public class ApplicationsDAO extends TrackerDAO {
	
	public ApplicationsDAO(String col, String table) {
		super(col, table);
	}
	
	public void newApplictaion(Connection con, String appID, String acctType, double deposit) {
		//INSERT INTO (Cols) VALUES (vals),
		try (PreparedStatement  pstmt = con.prepareStatement(INSERT_INTO + "applications (appID, acctType, deposit)" + VALUES + "( ?, ?, ?);");) {
			
			pstmt.setString(1, appID);
			pstmt.setString(2, acctType);
			pstmt.setDouble(3, deposit);
			pstmt.executeUpdate();
			
		} catch (SQLException e) { e.printStackTrace(); } 
		super.elements.add(appID);
	}
	
	public Application getApplication(String appID) {
		Connection con = ConnectionManager.getConnection();
		Application app = null;
		try (PreparedStatement pstmt = con.prepareStatement(SELECT + ALL + FROM + "applications" + WHERE + "appID = ?;")) {
				pstmt.setString(1, appID);
				try ( ResultSet rs = pstmt.executeQuery()) {
					rs.next();
					app = new Application(rs.getString("appID"), rs.getString("acctType"), rs.getDouble("deposit"));
				}
		} catch (SQLException e) { e.printStackTrace(); }
		return app;
	}
	
}