package models;
public class Definition {
	private String sense;
	private String notes;
	
	public Definition(String sense, String notes) {
		this.sense = sense;
		this.notes = notes;
	}
	
	public String getSense() {
		return sense;
	}
	
	public void setSense(String sense) {
		this.sense = sense;
	}
	
	public String getNotes() {
		return notes;
	}
	
	public void setNotes(String notes) {
		this.notes = notes;
	}
}
