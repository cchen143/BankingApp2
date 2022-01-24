package bankingApp2.dao;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

import static bankingApp2.dao.Utils.*;

public abstract class TrackerDAO implements DAO {
	
	protected Set<String> elements;
	
	public TrackerDAO(String col, String table) {
		elements = new HashSet<String>();
		Connection con = ConnectionManager.getConnection();
		try (Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(SELECT + col + FROM + table + " ;")) {
			while (rs.next()) {
				elements.add(rs.getString(col));
			}
		} catch (SQLException e) { e.printStackTrace(); }
	}
	
	//Util Functions
	public String randInt() { 
		String element = "";
		do { element = Integer.toString(0 + (int)(Math.random() * Integer.MAX_VALUE)); } while(this.elements.contains(element));
		return element;
	}
	
	public boolean checkElement(String element) { return this.elements.contains(element); }
	
	//addToTemp
	public void add(String element) { this.elements.add(element); }
	
	public void remove(String element) { this.elements.remove(element); }
	
	public int size() { return this.elements.size(); }
	
	///////TEMP
	public void printSet() {
		for (String e : elements) {
			System.out.println(e);
		}
	}
}

