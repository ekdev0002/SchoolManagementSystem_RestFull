package com.app.sms.models;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class EnseignantList {

	private List<Enseignant> enseignantList = new ArrayList<>();

    public void setEnseignantList(List<Enseignant> enseignantList) {
        this.enseignantList = enseignantList;
    }
    
    @XmlElement (name="enseignant") 
    public List<Enseignant> getEnseignantList() {
        return enseignantList;
    }
    
    @Override
    public String toString() {
    	String result = "";
    	for (Enseignant enseignant : enseignantList) {
    		result += enseignant.toString() + "\n";
    	}    	
    	return result;
    }
    
    public void add(Enseignant enseignant) {
    	enseignantList.add(enseignant);
	}

	public void display() {
		for( Enseignant enseignant : enseignantList) {
			enseignant.display();
		}
	}
}
