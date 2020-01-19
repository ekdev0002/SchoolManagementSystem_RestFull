package com.app.sms.models;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.app.sms.clientws.EnseignantWS;
import com.app.sms.exceptions.AlreadyExistDataException;
import com.app.sms.exceptions.WSException;


@XmlRootElement
@XmlType(propOrder = {"id", "nom","prenom","genre","telephone","login","password","email","path","idClasseList","classeList","candidatureList","adresse","userProfil"})
public class Enseignant extends User {
	
	private List<Integer> idClasseList = new ArrayList<>();

	private ClasseList classeList ;
	private CandidatureList candidatureList ;
	
	public Enseignant() {}
	
	public Enseignant(int id, String nom, String prenom, String adresse, String telephone,
			String email, String login, String password) {
		super(id, nom, prenom, adresse, telephone, email, login, password);
	}

	/**
	 * @param login
	 * @param password
	 */
	public Enseignant(String login, String password) {
		super(login, password);
	}

	public Enseignant(String login, String password, String userProfil) {
		super(login, password);
		this.userProfil = userProfil;
	}
	

	@XmlElementWrapper
    @XmlElement(name="IdClasse", type=Integer.class)
	public List<Integer> getIdClasseList() {
		return idClasseList ;
	}

	public void setIdClasseList(List<Integer> idClasseList) {
		this.idClasseList = idClasseList;
	}

	public ClasseList getClasseList() {
		return classeList;
	}

	public void setClasseList(ClasseList classeList) {
		this.classeList = classeList;
	}

	public CandidatureList getCandidatureList() {
		return candidatureList;
	}

	public void setCandidatureList(CandidatureList candidatureList) {
		this.candidatureList = candidatureList;
	}
	
	@Override
	public String toString() {
		return "Enseignant [id="+id+", nom="+nom+",prenom="+prenom+",genre="+genre+",telephone="+telephone+",login="+login+", password="+password+", email="+email+",path="+path+", idClasseList="+idClasseList+" classeList="+classeList+", candidatureList="+candidatureList;
	}

	public void display() {
		System.out.println(this);
		
	}	

	
	public void create() throws AlreadyExistDataException{
		EnseignantWS enseignantWS = new EnseignantWS ();
		
			enseignantWS.create(this) ;
		
	}
		
	public void update() throws WSException, JAXBException, IOException {
		EnseignantWS enseignantWS = new EnseignantWS ();
			enseignantWS.update(this);
		
	}

	public void delete() throws WSException, JAXBException, IOException {
		EnseignantWS enseignantWS = new EnseignantWS ();		
			enseignantWS.delete(id);
		
	}
	
	public static List<Enseignant> list() throws WSException, IOException, JAXBException  {
		EnseignantWS enseignantWS = new EnseignantWS ();
		
			return enseignantWS.list();
		
	}
	
	public static Enseignant getEnseignantById(int id) throws WSException, IOException {
		EnseignantWS enseignantWS = new EnseignantWS ();
		return enseignantWS.find(id);
	}
}