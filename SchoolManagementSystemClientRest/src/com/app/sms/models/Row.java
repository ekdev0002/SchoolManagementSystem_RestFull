package com.app.sms.models;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "row")
@XmlType(propOrder = {"libelle", "coef","note", "notePondere"})
public class Row {

	private String libelle;
	
	private int coef;
	
	private double note;
	
	private double notePondere;
	
	
	public Row() {}

	public Row(String libelle, int coef, double note, double notePondere) {
		super();
		this.libelle = libelle;
		this.coef = coef;
		this.note = note;
		this.notePondere = notePondere;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public int getCoef() {
		return coef;
	}

	public void setCoef(int coef) {
		this.coef = coef;
	}

	public double getNote() {
		return note;
	}

	public void setNote(double note) {
		this.note = note;
	}

	public double getNotePondere() {
		return notePondere;
	}

	public void setNotePondere(double notePondere) {
		this.notePondere = notePondere;
	}

	@Override
	public String toString() {
		return "Row [libelle="+libelle+ ", coef="+coef+", note="+note+ ", notePondere="+notePondere+"]";
	}


	public void display() {
		System.out.println(this);

	}

	

}
