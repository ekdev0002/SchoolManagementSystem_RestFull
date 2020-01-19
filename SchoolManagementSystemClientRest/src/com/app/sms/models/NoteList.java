package com.app.sms.models;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonProperty;

@XmlRootElement
public class NoteList {

	private List<Note> noteList = new ArrayList<>();

    public void setNoteList(List<Note> noteList) {
        this.noteList = noteList;
    }
    
    @XmlElement (name="note") 
    @JsonProperty("noteList")
    public List<Note> getNoteList() {
        return noteList;
    }
    
    @Override
    public String toString() {
    	String result = "";
    	for (Note note : noteList) {
    		result += note.toString() + "\n";
    	}    	
    	return result;
    }
    
    public void add(Note note) {
    	noteList.add(note);
	}

	public void display() {
		for( Note note : noteList) {
			note.display();
		}
	}
}
