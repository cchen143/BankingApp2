package bankingApp2.dao;

import static bankingApp2.dao.Utils.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bankingApp2.models.Application;

public class ApplicationsDAO extends TrackerDAO<Integer> {
	
		//Cache
		//Pulls all application IDs from DB when a Menu Object is created.
		//Keeps track the applications.appID during each user session.
		//Gets updated when a new application is created or reviewed.
		
		
		//protected Set<T> elements; from TrackDAO
	public ApplicationsDAO(String col, String table) {
		super(col, table);
	}
	
	public void newApplictaion(Connection con, int appID, String acctType, double deposit) {
		//INSERT INTO (Cols) VALUES (vals),
		try (PreparedStatement  pstmt = con.prepareStatement(INSERT_INTO + "applications (appID, acctType, deposit)" + VALUES + "( ?, ?, ?);");) {
			
			pstmt.setInt(1, appID);
			pstmt.setString(2, acctType);
			pstmt.setDouble(3, deposit);
			pstmt.executeUpdate();
			
		} catch (SQLException e) { e.printStackTrace(); } 
		super.elements.add(appID);
	}
	
	public Application getApplication(int appID) {
		Connection con = ConnectionManager.getConnection();
		Application app = null;
		try (PreparedStatement pstmt = con.prepareStatement(SELECT + ALL + FROM + "applications" + WHERE + "appID = ?;")) {
				pstmt.setInt(1, appID);
				try ( ResultSet rs = pstmt.executeQuery()) {
					rs.next();
					app = new Application(rs.getInt("appID"), rs.getString("acctType"), rs.getDouble("deposit"));
				}
		} catch (SQLException e) { e.printStackTrace(); }
		return app;
	}
	
	public void addToTemp(int appID) {this.elements.add(appID); }
}