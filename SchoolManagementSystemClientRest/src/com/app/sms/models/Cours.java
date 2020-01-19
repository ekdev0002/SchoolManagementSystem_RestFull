package com.app.sms.models;

import java.io.IOException;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.app.sms.clientws.CoursWS;
import com.app.sms.exceptions.WSException;
import com.fasterxml.jackson.annotation.JsonProperty;

@XmlRootElement(name = "cours")
@XmlType(propOrder = {"id","idModule","idClasse","dateHeure","description","duree"})
public class Cours {
	@JsonProperty
	private int id;
	@JsonProperty
	private int idModule;
	@JsonProperty
	private int idClasse;
	@JsonProperty
	private String dateHeure;
	@JsonProperty
	private String description;
	@JsonProperty
	private int duree;
	
	public Cours(int id, int idModule,int idClasse,String dateHeure, String description,int duree) {
		this (idModule,idClasse,dateHeure, description,duree);
		this.id = id;
		this.idModule=idModule;
	}
	
	public Cours( int idModule,int idClasse,String dateHeure, String description,int duree) {
		this.dateHeure = dateHeure;
		this.description = description;
		this.duree = duree;
		this.idModule=idModule;
		this.idClasse=idClasse;

	}

	public Cours() {}


	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}

	public int getIdModule() {
		return this.idModule;
	}

	public void setIdModule(int idModule) {
		this.idModule = idModule;
	}
		
	public int getIdClasse() {
		return idClasse;
	}

	public void setIdClasse(int idClasse) {
		this.idClasse = idClasse;
	}
	
	public String getDateHeure() {
		return dateHeure;
	}
	
	public void setDateHeure(String dateHeure) {
		this.dateHeure = dateHeure;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}

	public void create() throws WSException, JAXBException, IOException {
	
		CoursWS coursWS = new CoursWS ();
			coursWS.create(this) ;
	
	}

	public void update() throws WSException, JAXBException, IOException {
		CoursWS coursWS = new CoursWS ();
		coursWS.update(this);
	}

	public void delete() throws WSException, JAXBException, IOException {
		CoursWS coursWS = new CoursWS ();
		coursWS.delete(this.id);
		
	}

	public static List<Cours> listForEns(int idEns) throws JAXBException , IOException , WSException{
		CoursWS coursWS = new CoursWS ();
		return coursWS.listForEns(idEns);
	}

	public int getDuree() {
		return duree;
	}

	public void setDuree(int duree) {
		this.duree = duree;
	}

	@Override
	public String toString() {
		return "Cours [id="+id+ ", idModule="+idModule+", idClasse="+idClasse+ ", dateHeure="+dateHeure+", description="+description+", duree="+duree+"]";
	}

	public void display() {
		System.out.println(this);
		
	}
	
	public String getModule() throws JAXBException, IOException, WSException {
		
		for ( Module module : Module.list() ) {
			
			if(this.idModule == module.getId() ) 
			{
				return module.getLibelle();
			}
		}
	
	return null;
	}
	
	public String getClasse() throws JAXBException , IOException, WSException{
		for ( Classe classe : Classe.list() ) {
			
			if(this.idClasse == classe.getId() ) 
			{
				return classe.getLibelle();
			}
		}
		return null;
	}



	public static List<Cours> listForEleve(int idEleve)throws JAXBException , IOException, WSException {
		CoursWS coursWS = new CoursWS ();
		return coursWS.listForEleve(idEleve);
	}
}
