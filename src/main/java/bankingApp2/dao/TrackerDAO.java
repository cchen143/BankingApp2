package bankingApp2.dao;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

import static bankingApp2.dao.Utils.*;

public abstract class TrackerDAO implements DAO {
	
	protected Set<Integer> elements;
	
	public TrackerDAO(String col, String table) {
		elements = new HashSet<Integer>();
		Connection con = ConnectionManager.getConnection();
		try (Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(SELECT + col + FROM + table + " ;")) {
			while (rs.next()) {
				elements.add(rs.getInt(col));
			}
		} catch (SQLException e) { e.printStackTrace(); }
	}
	
	//Util Functions
	public int randInt() { 
		int element = -1;
		do { element = 0 + (int)(Math.random() * Integer.MAX_VALUE); } while(this.elements.contains(element));
		return element;
	}
	
	public boolean checkElement(int element) { return this.elements.contains(element); }
	
	//addToTemp
	public void add(int element) { this.elements.add(element); }
	
	public void remove(int element) { this.elements.remove(element); }
	
	public int size() { return this.elements.size(); }
	
	///////TEMP
	public void printSet() {
		for (int e : elements) {
			System.out.println(e);
		}
	}
}

