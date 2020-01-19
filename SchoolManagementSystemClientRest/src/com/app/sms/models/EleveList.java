package com.app.sms.models;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonProperty;

@XmlRootElement
public class EleveList {

	private List<Eleve> eleveList = new ArrayList<>();

    public void setEleveList(List<Eleve> eleveList) {
        this.eleveList = eleveList;
    }
    
    @XmlElement (name="eleve") 
    @JsonProperty("eleveList")
    public List<Eleve> getEleveList() {
        return eleveList;
    }
    
    @Override
    public String toString() {
    	String result = "";
    	for (Eleve eleve : eleveList) {
    		result += eleve.toString() + "\n";
    	}    	
    	return result;
    }
    
    public void add(Eleve eleve) {
    	eleveList.add(eleve);
	}

	public void display() {
		for( Eleve eleve : eleveList) {
			eleve.display();
		}
	}
}
