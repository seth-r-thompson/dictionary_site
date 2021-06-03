package models;

import helpers.Database;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class User {
	private String userID;
	private String fullName;
		
	public String getUserID() {
		return userID;
	}
	
	public void setUserID(String username) {
		this.userID = username;
	}
	
	public String getFullName() {
		return fullName;
	}
	
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
	public boolean find(String user_id, String password) {	
		boolean found = false;
		
		String query = "select * from USERS "
				+ "where USER_ID = '" + user_id + "'"
				+ "and PASSWORD = '" + password + "'";
		
		Connection connection = Database.connectToPool();
		if (connection != null) {
			ResultSet results = Database.query(connection, query);
			if (results != null) {
				try {
					if (results.next() == true) {
						this.userID = results.getString("USER_ID");
						this.fullName = results.getString("FIRSTNAME") + " " + results.getString("LASTNAME");
						found = true;
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			Database.disconnect(connection);
		}
		
		return found;
	}
}
