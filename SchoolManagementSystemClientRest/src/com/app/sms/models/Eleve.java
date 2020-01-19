package com.app.sms.models;

import java.io.IOException;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.app.sms.clientws.EleveWS;
import com.app.sms.exceptions.NotFoundDataException;
import com.app.sms.exceptions.WSException;

/**
 * @author a459079
 *
 */

@XmlRootElement(name = "eleve")
@XmlType(propOrder = {"id","prenom","nom","adresse","telephone","email","genre","login","password","userProfil","path","idClasse","libelleClasse"})
public class Eleve extends User {

	private int idClasse;
	private String libelleClasse;
	
	public Eleve(int id, String nom, String prenom, String adresse, String genre, String telephone,
			String email, String login, String password) {
		super(id, nom, prenom, adresse, genre, telephone, email, login, password);
	}
	
	public Eleve(int id, String nom, String prenom, String genre, String telephone,
			String email, String login, String password) {
		super(id, nom, prenom, genre, telephone, email, login, password);
	}
	
	/**
	 * @param login
	 * @param password
	 */
	public Eleve(String login, String password) {
		super (login, password);
	}

	public Eleve() {}

	public void setIdClasse(int idClasse) {
		this.idClasse = idClasse;
		
	}
	
	public void setLibelleClasse(String libelleClasse) {
		this.libelleClasse = libelleClasse;
	}
	
	public String getLibelleClasse () {
		return libelleClasse;
	}
	
	public int getIdClasse() {
		return idClasse;
	}


	@Override
	public String toString() {
		return "Eleve [id="+id+",idClasse="+idClasse+", nom"+nom+",prenom="+prenom+",genre="+genre+",telephone="+telephone+",login="+login+", password="+password+", email="+email+",path="+path+libelleClasse+"]";
	}

	public void display() {
		System.out.println(this);
		
	}	
	
	
	public void create() throws JAXBException , IOException, WSException {
		EleveWS eleveWS = new EleveWS ();	
		eleveWS.create(this) ;
	}
	
	public void update() throws JAXBException , IOException, WSException, NotFoundDataException{
		EleveWS eleveWS = new EleveWS ();
		eleveWS.update(this);
	}

	public void delete()throws JAXBException , IOException, WSException{
		EleveWS eleveWS = new EleveWS ();
		eleveWS.delete(id);
	}
	
	public static List<Eleve> list() throws JAXBException , IOException, WSException {
		EleveWS eleveWS = new EleveWS ();
		return eleveWS.list();
	}
	
	public static List<Eleve> list(String idClasse) throws JAXBException , IOException, WSException {
		EleveWS eleveWS = new EleveWS ();
		return eleveWS.listByClasse(idClasse);
	}

	public static Eleve findById(int idEleve) throws JAXBException , IOException, WSException {
		EleveWS eleveWS = new EleveWS ();
		return eleveWS.findById(idEleve);
	}

	public static List<Eleve> list(int id)throws JAXBException , IOException, WSException {
		EleveWS eleveWS = new EleveWS ();
		return eleveWS.listByEnseignant(id);
	}
}