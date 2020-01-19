package com.app.sms.models;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CoursList {

	private List<Cours> coursList = new ArrayList<>();

    public void setCoursList(List<Cours> coursList) {
        this.coursList = coursList;
    }
    
    @XmlElement (name="cours") 
    public List<Cours> getCoursList() {
        return coursList;
    }
    
    @Override
    public String toString() {
    	String result = "";
    	for (Cours cours : coursList) {
    		result += cours.toString() + "\n";
    	}    	
    	return result;
    }
    
    public void add(Cours cours) {
    	coursList.add(cours);
	}

	public void display() {
		for( Cours cours : coursList) {
			cours.display();
		}
	}
}
