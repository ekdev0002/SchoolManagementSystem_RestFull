package com.app.sms.models;

import java.io.IOException;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.app.sms.clientws.ModuleWS;
import com.app.sms.exceptions.WSException;
import com.app.sms.ui.impl.MainUIApplication;
import com.fasterxml.jackson.annotation.JsonProperty;

@XmlRootElement(name = "module")
@XmlType(propOrder = {"id", "coefficient", "libelle", "description","libelleClasse"})
public class Module {
	@JsonProperty
	private int id;
	@JsonProperty
	private int coefficient;
	@JsonProperty
	private String libelle;
	@JsonProperty
	private String description;
	
	private String libelleClasse;
	
	public Module(int coefficient, String libelle, String description) {
		this (libelle, description);
		this.coefficient = coefficient;
	}
	
	public Module( String libelle, String description) {
		this.libelle = libelle;
		this.description = description;
	}
	
	public Module(int id, int coefficient, String libelle, String description) {
		this(coefficient,libelle,description);
		this.id=id;
	}

	public Module() {}

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

	public int getCoefficient() {
		return coefficient;
	}

	public void setCoefficient(int coefficient) {
		this.coefficient = coefficient;
	}

	@Override
	public String toString() {
		return "Module [id="+id+", coefficiant="+coefficient+", libelle="+libelle+", description="+description+"+libelleClasse="+libelleClasse+"]";
	}

	public void display() {
		System.out.println(this);

	}
	
	
	public void create() throws JAXBException, IOException, WSException {
		ModuleWS moduleWS = new ModuleWS ();
			moduleWS.create(this) ;
	}
	public void update(String idLibelleClasse, String idEns) throws JAXBException, IOException, WSException {
		ModuleWS moduleWS = new ModuleWS ();	
			moduleWS.update(this,idLibelleClasse,idEns);	
	}

	public void delete() throws JAXBException, IOException, WSException {
		ModuleWS moduleWS = new ModuleWS ();
			moduleWS.delete(id);
	}

	public static List<Module> list() throws JAXBException, IOException, WSException {
		ModuleWS moduleWS = new ModuleWS ();
		return moduleWS.list();
	}

	public static List<Module> listForEns() throws JAXBException, IOException, WSException{
		ModuleWS moduleWS = new ModuleWS ();
		return moduleWS.listForEns(MainUIApplication.getCurrentUser().getId());
	}

	
	public static List<Module> listForEleve(int id2) throws JAXBException, IOException, WSException{
		ModuleWS moduleWS = new ModuleWS ();
		return moduleWS.listForEleve(MainUIApplication.getCurrentUser().getId());
	}
	
	
	
	public static void setEnseignantLibelleClasse(int idEns, int idLibelleClasse, int idModule) throws JAXBException, IOException, WSException {
		// TODO Auto-generated method stub
		ModuleWS moduleWS = new ModuleWS ();
	 moduleWS.setEnseignantClasse(idEns, idLibelleClasse, idModule);
		
	}

	public String getLibelleClasse() {
		return libelleClasse;
	}

	public void setLibelleClasse(String libelleClasse) {
		this.libelleClasse = libelleClasse;
	}
}
