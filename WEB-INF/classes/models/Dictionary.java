package models;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import helpers.Database;

public class Dictionary {
	private List<Word> entries;

	public Dictionary() {
		this.entries = new ArrayList<Word>();
	}
	
	public List<Word> getEntries() {
		return entries;
	}
	
	public void findAll() {
		find("", "", "");
	}
	
	public void find(String lemma, String pronunciation, String wordClass) {
		if (wordClass.equals("select")) wordClass = ""; // Clear default value
		
		String query = "select * from WORDS "
				+ "where LEMMA like '%" + lemma + "%'"
				+ "and PRONUNCIATION like '%" + pronunciation + "%'"
				+ "and CLASS like '%" + wordClass + "%'";
				
		Connection connection = Database.connectToPool();
		if (connection != null) {
			ResultSet results = Database.query(connection, query);
			if (results != null) {
				try {
					while (results.next() == true) {
						Word word = new Word(results.getString("LEMMA"), 
								results.getString("PRONUNCIATION"), 
								results.getString("CLASS"));
						word.findDefinitions();
						entries.add(word);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			Database.disconnect(connection);		
		}		
	}

	public void addWord(String lemma, String pronunciation, String wordClass) {		
		Connection connection = Database.connectToPool();
		if (connection != null) {
			ResultSet result = Database.query(connection, "select WORD_ID from WORDS order by WORD_ID desc");
			if (result != null) {
				try {
					if (result.next() == true) {
						String query = "insert into WORDS values "
								+ "('" + (result.getInt("WORD_ID") + 1)
								+ "', '" + lemma + "', '" + pronunciation + "', '" + wordClass + "')";
						Database.update(connection, query);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			Database.disconnect(connection);
		}
	}
	
	public void updateWord(String lemma, String pronunciation, String wordClass) {
		if (wordClass.equals("select")) wordClass = ""; // Clear default value
		
		String query = "update WORDS set "
				+ "PRONUNCIATION = '" + pronunciation + "', CLASS = '" + wordClass + "' "
				+ "where LEMMA like '%" + lemma + "%'";		
		
		Connection connection = Database.connectToPool();
		if (connection != null) {
			Database.update(connection, query);
			Database.disconnect(connection);
		}
	}

	public void deleteWord(String lemma, String pronunciation, String wordClass) {
		if (wordClass.equals("select")) wordClass = ""; // Clear default value
		
		String query = "delete from WORDS "
				+ "where LEMMA like '%" + lemma + "%'"
				+ "and PRONUNCIATION like '%" + pronunciation + "%'"
				+ "and CLASS like '%" + wordClass + "%'";
		
		Connection connection = Database.connectToPool();
		if (connection != null) {
			Database.delete(connection, query);
			Database.disconnect(connection);
		}
	}
	
	public boolean isEmpty() {
		return entries.isEmpty();
	}
}
