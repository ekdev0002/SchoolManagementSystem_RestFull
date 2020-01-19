package com.app.sms.models;

import java.io.IOException;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.app.sms.clientws.GestionnaireWS;
import com.app.sms.exceptions.WSException;

@XmlRootElement(name = "gestionnaire")
@XmlType(propOrder = {"id", "nom","prenom","adresse","date_naissance","telephone","email","login","password","path","genre","userProfil"})

public class Gestionnaire extends User {
	
	private String date_naissance;
	
	public Gestionnaire(int id, String nom, String prenom, String adresse, String date_naissance, String telephone,
			String email, String login, String password) {
		super(id, nom, prenom, adresse, date_naissance, telephone, email, login, password);
		this.date_naissance = date_naissance;
	}

	public String getDate_naissance() {
		return date_naissance;
	}

	public void setDate_naissance(String date_naissance) {
		this.date_naissance = date_naissance;
	}

	/**
	 * @param login
	 * @param password
	 */
	public Gestionnaire(String login, String password) {
		super(login, password);
	}

	public Gestionnaire(String login, String password, String userProfil) {
		this(login, password);
		this.userProfil = userProfil;
	}
	
	public Gestionnaire() {};
	

	
	@Override
	public String toString() {
		return "gestionnaire [id="+id+", nom="+nom+",prenom="+prenom+", adresse="+adresse+",date_naissance"+",telephone="+telephone+", email="+email+",login="+login+", password="+password+"]";
	}

	public void display() {
		System.out.println(this);
		
	}	

	
	public void create() throws WSException {
		GestionnaireWS gestionnaireWS = new GestionnaireWS();
			gestionnaireWS.create(this) ;
	}
	
	public void update() throws  WSException {
		GestionnaireWS gestionnaireWS = new GestionnaireWS();
			gestionnaireWS.update(this);
	}

	public void delete() throws  WSException, IOException, JAXBException {
		GestionnaireWS gestionnaireWS = new GestionnaireWS();
			gestionnaireWS.delete(id);
	}
	
	public static List<Gestionnaire> list() throws  WSException, IOException {
		GestionnaireWS gestionnaireWS = new GestionnaireWS();
		return gestionnaireWS.list();
	}
	
	public static Gestionnaire getGestionnaireById(int id) throws IOException {
		GestionnaireWS gestionnaireWS = new GestionnaireWS();
		return gestionnaireWS.find(id);
	}
}