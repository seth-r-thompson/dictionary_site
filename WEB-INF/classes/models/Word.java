package models;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import helpers.Database;

public class Word {
	private String lemma;
	private String sound;
	private String wordClass; // part of speech
	private List<Definition> senses;
	
	public Word(String lemma, String sound, String pos) {
		super();
		this.lemma = lemma;
		this.sound = sound;
		this.wordClass = pos;
		this.senses = new ArrayList<Definition>();
	}

	public String getLemma() {
		return lemma;
	}
	
	public void setLemma(String lemma) {
		this.lemma = lemma;
	}
	
	public String getSound() {
		return sound;
	}
	
	public void setSound(String sound) {
		this.sound = sound;
	}
	
	public String getWordClass() {
		return wordClass;
	}
	
	public void setWordClass(String pos) {
		this.wordClass = pos;
	}
	
	public List<Definition> getSenses() {
		return senses;
	}
	
	public void setSenses(List<Definition> senses) {
		this.senses = senses;
	}
	
	public void findDefinitions() {
		String query = "select * from DEFINITIONS "
				+ "join WORDS on WORDS.WORD_ID = DEFINITIONS.WORD_ID "
				+ "where WORDS.LEMMA like '%" + lemma + "%'";
		
		Connection connection = Database.connectToPool();
		if (connection != null) {
			ResultSet results = Database.query(connection, query);
			if (results != null) {
				try {
					while (results.next() == true) {
						Definition definition = new Definition(results.getString("SENSE"),
								results.getString("NOTES"));
						senses.add(definition);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			Database.disconnect(connection);
		}
	}
}
