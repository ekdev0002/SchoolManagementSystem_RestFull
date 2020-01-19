package com.app.sms.models;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonProperty;

@XmlRootElement
public class BulletinList {

	private List<Bulletin> bulletinList = new ArrayList<>();

    public void setBulletinList(List<Bulletin> bulletinList) {
        this.bulletinList = bulletinList;
    }
    
    @XmlElement (name="bulletin") 
    @JsonProperty("bulletinList")
    public List<Bulletin> getBulletinList() {
        return bulletinList;
    }
    
    @Override
    public String toString() {
    	String result = "";
    	for (Bulletin bulletin : bulletinList) {
    		result += bulletin.toString() + "\n";
    	}    	
    	return result;
    }
    
    public void add(Bulletin bulletin) {
    	bulletinList.add(bulletin);
	}

	public void display() {
		for( Bulletin bulletin : bulletinList) {
			bulletin.display();
		}
	}
}
