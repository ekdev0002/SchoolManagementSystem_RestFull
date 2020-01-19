package com.app.sms.models;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DiplomeList {

	private List<Diplome> diplomeList = new ArrayList<>();

    public void setDiplomeList(List<Diplome> diplomeList) {
        this.diplomeList = diplomeList;
    }
    
    @XmlElement (name="diplome")
    public List<Diplome> getDiplomeList() {
        return diplomeList;
    }
    
    @Override
    public String toString() {
    	String result = "";
    	for (Diplome diplome : diplomeList) {
    		result += diplome.toString() + "\n";
    	}    	
    	return result;
    }
    
    public void add(Diplome diplome) {
    	diplomeList.add(diplome);
	}

	public void display() {
		for( Diplome diplome : diplomeList) {
			diplome.display();
		}
	}
}
