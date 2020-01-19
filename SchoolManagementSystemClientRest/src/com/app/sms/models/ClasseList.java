package com.app.sms.models;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonProperty;

@XmlRootElement
public class ClasseList {

	private List<Classe> classeList = new ArrayList<>();

    public void setClasseList(List<Classe> classeList) {
        this.classeList = classeList;
    }
    
    @XmlElement (name="classe") 
    @JsonProperty("classeList")
    public List<Classe> getClasseList() {
        return classeList;
    }
    
    @Override
    public String toString() {
    	String result = "";
    	for (Classe classe : classeList) {
    		result += classe.toString() + "\n";
    	}    	
    	return result;
    }
    
    public void add(Classe classe) {
    	classeList.add(classe);
	}

	public void display() {
		for( Classe classe : classeList) {
			classe.display();
		}
	}
}
