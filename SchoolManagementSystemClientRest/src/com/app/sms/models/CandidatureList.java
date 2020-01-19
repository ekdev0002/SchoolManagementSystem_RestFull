package com.app.sms.models;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CandidatureList {

	private List<Candidature> candidatureList = new ArrayList<>();

    public void setCandidatureList(List<Candidature> candidatureList) {
        this.candidatureList = candidatureList;
    }
    
    @XmlElement (name="candidature") 
    public List<Candidature> getCandidatureList() {
        return candidatureList;
    }
    
    @Override
    public String toString() {
    	String result = "";
    	for (Candidature candidature : candidatureList) {
    		result += candidature.toString() + "\n";
    	}    	
    	return result;
    }
    
    public void add(Candidature candidature) {
    	candidatureList.add(candidature);
	}

	public void display() {
		for( Candidature candidature : candidatureList) {
			candidature.display();
		}
	}
}
