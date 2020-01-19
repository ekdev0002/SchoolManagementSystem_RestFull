package com.app.sms.models;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RowList {

	private List<Row> rowList = new ArrayList<>();

    public void setRowList(List<Row> rowList) {
        this.rowList = rowList;
    }
    
    @XmlElement (name="row") 
    public List<Row> getRowList() {
        return rowList;
    }
    
    @Override
    public String toString() {
    	String result = "";
    	for (Row row : rowList) {
    		result += row.toString() + "\n";
    	}    	
    	return result;
    }
    
    public void add(Row row) {
    	rowList.add(row);
	}

	public void display() {
		for( Row row : rowList) {
			row.display();
		}
	}
}
