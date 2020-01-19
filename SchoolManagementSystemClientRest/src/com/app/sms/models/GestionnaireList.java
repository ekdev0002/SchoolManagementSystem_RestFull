package com.app.sms.models;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class GestionnaireList {

	private List<Gestionnaire> gestionnaireList = new ArrayList<>();

    public void setGestionnaireList(List<Gestionnaire> gestionnaireList) {
        this.gestionnaireList = gestionnaireList;
    }
    
    @XmlElement (name="gestionnaire") 
    public List<Gestionnaire> getGestionnaireList() {
        return gestionnaireList;
    }
    
    @Override
    public String toString() {
    	String result = "";
    	for (Gestionnaire gestionnaire : gestionnaireList) {
    		result += gestionnaire.toString() + "\n";
    	}    	
    	return result;
    }
    
    public void add(Gestionnaire gestionnaire) {
    	gestionnaireList.add(gestionnaire);
	}

	public void display() {
		for( Gestionnaire gestionnaire : gestionnaireList) {
			gestionnaire.display();
		}
	}
}
