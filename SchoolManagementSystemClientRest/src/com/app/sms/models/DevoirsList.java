package com.app.sms.models;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonProperty;

@XmlRootElement
public class DevoirsList {

	private List<Devoirs> devoirsList = new ArrayList<>();

    public void setDevoirsList(List<Devoirs> devoirsList) {
        this.devoirsList = devoirsList;
    }
    
    @XmlElement (name="devoirs") 
    @JsonProperty("devoirsList")
    public List<Devoirs> getDevoirsList() {
        return devoirsList;
    }
    
    @Override
    public String toString() {
    	String result = "";
    	for (Devoirs devoirs : devoirsList) {
    		result += devoirs.toString() + "\n";
    	}    	
    	return result;
    }
    
    public void add(Devoirs devoirs) {
    	devoirsList.add(devoirs);
	}

	public void display() {
		for( Devoirs devoirs : devoirsList) {
			devoirs.display();
		}
	}
}
