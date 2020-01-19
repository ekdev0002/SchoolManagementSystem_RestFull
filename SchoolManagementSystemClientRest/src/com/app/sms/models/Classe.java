package com.app.sms.models;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.app.sms.clientws.ClasseWS;
import com.app.sms.exceptions.WSException;
import com.fasterxml.jackson.annotation.JsonProperty;

@XmlRootElement(name = "classe")
@XmlType(propOrder = {"id","libelle",  "description"})
public class Classe {
	@JsonProperty
	private int id;
	@JsonProperty
	private String libelle;
	@JsonProperty
	private String description;
	
	List<Enseignant> enseignantList;
	
	List<Eleve> eleveList;
	
	public Classe(int id, String libelle, String description) {
		this (libelle, description);
		this.id = id;
	}
	
	public Classe( String libelle, String description) {
		this.libelle = libelle;
		this.description = description;
		enseignantList = new ArrayList<>();
		eleveList = new ArrayList<>();
	}

	public Classe() {}


	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}

	public String getLibelle() {
		return libelle;
	}
	
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}

	
	
	@Override
	public String toString() {
		return "Classe [id="+id+", libelle="+libelle+", description="+description+", enseignantList="+enseignantList+", eleveList="+eleveList+"]";
	}

	public void display() {
		System.out.println(this);

	}

	
	
	
	
	public void addEnseignant (Enseignant enseignant) {
		enseignantList.add(enseignant);
	}

	public void create() throws JAXBException , IOException, WSException {
		ClasseWS classeWS = new ClasseWS ();
		classeWS.create(this) ;	
	}

	public void update()throws JAXBException , IOException, WSException{
		ClasseWS classeWS = new ClasseWS ();

			classeWS.update(this);

	}

	public void delete() throws JAXBException , IOException, WSException {
		ClasseWS classeWS = new ClasseWS ();
		
			classeWS.delete(id);
	}

	public static List<Classe> list()throws JAXBException , IOException, WSException{
		ClasseWS classeWS = new ClasseWS ();
		return classeWS.list();
	}

	public static List<Classe> listForEns(int idEns)throws JAXBException , IOException, WSException{
		ClasseWS classeWS = new ClasseWS ();
		return classeWS.listForEns(idEns);
	}

	
}
