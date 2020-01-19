package com.app.sms.models;

import java.io.IOException;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.app.sms.clientws.DevoirsWS;
import com.app.sms.exceptions.WSException;
import com.fasterxml.jackson.annotation.JsonProperty;

@XmlRootElement(name = "devoirs")
@XmlType(propOrder = {"id", "idModule", "idClasse","statut", "dateHeure","description","duree","coef"})
public class Devoirs {

	@JsonProperty
	private int id;
	@JsonProperty
	private int idModule;
	@JsonProperty
	private int idClasse;
	@JsonProperty
	private String statut;
	@JsonProperty
	private String dateHeure;
	@JsonProperty
	private String description;
	@JsonProperty
	private int duree;
	@JsonProperty
	private int coef;
	
	public Devoirs(int id, int idModule,int idClasse,String statut,String dateHeure, String description,int duree,int coef) {
		this (idModule,idClasse,statut,dateHeure, description,duree,coef);
		this.id = id;
	}
	
	public Devoirs( int idModule,int idClasse,String statut,String dateHeure, String description,int duree,int coef) {
		this.dateHeure = dateHeure;
		this.description = description;
		this.duree = duree;
		this.idModule=idModule;
		this.statut = statut;
		this.idClasse=idClasse;
		this.coef=coef;

	}

	public String getStatut() {
		return statut;
	}

	public void setStatut(String statut) {
		this.statut = statut;
	}

	public Devoirs() {}


	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
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

	public void create() throws WSException, JAXBException, IOException  {
		
		
		DevoirsWS devoirsWS = new DevoirsWS ();			
		devoirsWS.create(this) ;
		
	}

	public void update() throws WSException, JAXBException, IOException {
		DevoirsWS devoirsWS = new DevoirsWS ();
		devoirsWS.update(this);
	}

	public void delete()throws WSException, JAXBException, IOException{
		DevoirsWS devoirsWS = new DevoirsWS ();
		devoirsWS.delete(id);
	}


	public int getDuree() {
		return duree;
	}

	public void setDuree(int duree) {
		this.duree = duree;
	}

	public int getIdModule() {
		return idModule;
	}

	public void setIdModule(int idModule) {
		this.idModule = idModule;
	}

	@Override
	public String toString() {
		return "Devoires [id="+id+ ", idModule=" +idModule+", idClasse="+idClasse+ ", statut="+statut+", dateHeure="+dateHeure+", description="+description+", duree="+duree+", coef="+coef+"]";
	}

	public void display() {
		System.out.println(this);
		
	}


	
	public String getModule() throws JAXBException, IOException, WSException{
		
		for ( Module module : Module.list() ) {
			
			if(this.idModule == module.getId() ) 
			{
				return module.getLibelle();
			}
		}
	
	return null;
	}
	
	public String getClasse()throws JAXBException , IOException, WSException {
		for ( Classe classe : Classe.list() ) {
			
			if(this.idClasse == classe.getId() ) 
			{
				return classe.getLibelle();
			}
		}
		return null;
	}


	public int getIdClasse() {
		return idClasse;
	}

	public void setIdClasse(int idClasse) {
		this.idClasse = idClasse;
	}

	public int getCoef() {
		return coef;
	}

	public void setCoef(int coef) {
		this.coef = coef;
	}


	public static List<Devoirs> listForEns(int id2) throws JAXBException , IOException, WSException {
		DevoirsWS devoirsWS = new DevoirsWS ();
		return devoirsWS.listForEns(id2);
	}
	
	public static List<Devoirs> listForEleve(int idEleve)throws JAXBException , IOException, WSException {
		DevoirsWS devoirsWS = new DevoirsWS ();
		return devoirsWS.listForEleve(idEleve);
	}
}
