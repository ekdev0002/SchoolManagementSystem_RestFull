package com.app.sms.models;

import java.io.IOException;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.app.sms.clientws.CandidatureWS;
import com.app.sms.exceptions.WSException;
/**
 * @author a459079
 *
 */

@XmlRootElement(name = "candidature")
@XmlType(propOrder = {"id", "idEnseignant", "nom", "prenom","telephone","email","genre","birthday","path","cv","diplomeList","state","commentaires"})
public class Candidature {

	private int id;
	private int idEnseignant;
	private String nom;
	private String prenom;
	private String telephone;
	private String email;
	private String genre;
	private String birthday ;
	private String path;
	private String cv=null;
	@XmlElement
	private DiplomeList diplomeList;
	private String state;
	private String commentaires=null;
	
	public static final String REJETEE = "Rejetée" ;
	public static final String RETENUE = "Retenue" ;
	public static final String EN_COURS = "Analyse en cours" ;
	public static final String CLOSE = "Clôturée" ;
	public static final String OUVERTE = "Ouverte - Non encore affectée" ;
	
	public Candidature() {
		super();
		diplomeList = new DiplomeList();
		state = OUVERTE;
	}

	
	public Candidature(int id, int idEnseignant, String nom, String prenom, String telephone, String email,
			String genre, String birthday, String path, String cv,DiplomeList diplomeList, String state,
			String commentaires) {
		super();
		this.id = id;
		this.idEnseignant = idEnseignant;
		this.nom = nom;
		this.prenom = prenom;
		this.telephone = telephone;
		this.email = email;
		this.genre = genre;
		this.birthday = birthday;
		this.path = path;
		this.cv = cv;
		this.diplomeList = diplomeList;
		this.state = state;
		this.commentaires = commentaires;
	}
	
	
	

	public Candidature(int id, int idEnseignant, String nom, String prenom, String telephone, String email,
			String genre, String birthday, String commentaires) {
		super();
		this.id = id;
		this.idEnseignant = idEnseignant;
		this.nom = nom;
		this.prenom = prenom;
		this.telephone = telephone;
		this.email = email;
		this.genre = genre;
		this.birthday = birthday;
		this.commentaires = commentaires;
	}


	public Candidature(int idEnseignant, String nom, String prenom, String telephone, String email, String genre,
			String birthday, String path, String cv, DiplomeList diplomeList, String state,
			String commentaires) {
		super();
		this.idEnseignant = idEnseignant;
		this.nom = nom;
		this.prenom = prenom;
		this.telephone = telephone;
		this.email = email;
		this.genre = genre;
		this.birthday = birthday;
		this.path = path;
		this.cv = cv;
		this.diplomeList = diplomeList;
		this.state = state;
		this.commentaires = commentaires;
	}
	
	
	

//	public Candidature(int id2, int idEns, String nom2, String prenom2, String email2, String telephone2, String genre2,
	//		String path, String birthday2, String cv2, String state2, String commentaires2) {
		// TODO Auto-generated constructor stub
//	}
	
	


	public void setId(String id) {
		this.id = Integer.valueOf(id);
	}

	public Candidature(int id, int idEnseignant, String nom, String prenom, String telephone, String email,
			String genre, String birthday, String state, String commentaires) {
		super();
		this.id = id;
		this.idEnseignant = idEnseignant;
		this.nom = nom;
		this.prenom = prenom;
		this.telephone = telephone;
		this.email = email;
		this.genre = genre;
		this.birthday = birthday;
		this.state = state;
		this.commentaires = commentaires;
	}


	public Candidature(int id, int idEnseignant, String nom, String prenom, String telephone, String email, String genre,
		String birthday, String path, String cv, String state, String commentaires) {
	super();
	this.id = id;
	this.idEnseignant = idEnseignant;
	this.nom = nom;
	this.prenom = prenom;
	this.telephone = telephone;
	this.email = email;
	this.genre = genre;
	this.birthday = birthday;
	this.path = path;
	this.cv = cv;
	this.state = state;
	this.commentaires = commentaires;
}


	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getCV() {
		return cv;
	}
	
	public DiplomeList getDiplomeList() {
		return diplomeList;
	}
	
	public void addDiplome (Diplome diplome) {
		diplomeList.add(diplome);
	}

	public String getCv() {
		return cv;
	}

	public void setCv(String cv) {
		this.cv = cv;
	}

	public void setDiplomeList(List<String> urls) {
		for (String url : urls) {
			this.diplomeList.add( new Diplome (-1, this.id, url));
		}		
	}
	
	public void setState(String state) {
		this.state = state;
	}
	
	public String getState() {
		return state;
	}

	/**
	 * @return the idEnseignant
	 */
	public int getIdEnseignant() { 
		return idEnseignant;
	}

	/**
	 * @param idEnseignant the idEnseignant to set
	 */
	public void setIdEnseignant(int idEnseignant) {
		this.idEnseignant = idEnseignant;
		/*if ( idEnseignant != -1 ) {
			setState(EN_COURS);
		}*/
	}

	public String getCommentaires() {
		return commentaires;
	}

	public void setCommentaires(String commentaires) {
		this.commentaires = commentaires;
	}

	public void create() throws WSException, JAXBException, IOException {
		CandidatureWS candidatureWS = new CandidatureWS ();
			candidatureWS.create(this) ;
	}
	
	@Override
	public String toString() {
		return "Candidature [id="+id+ ", idEnseignant=" +idEnseignant+", nom="+nom+ ", prenom="+prenom+", telephone="+telephone+", email="+email+", genre="+genre+"birthday="+birthday+", path="+path+", cv="+cv+", diplomeList="+diplomeList+", state="+state+", commentaires="+commentaires+"]";

	}

	public void display() {
		System.out.println(this);
		
	}

	
	public static List<Candidature> list() throws WSException, IOException, JAXBException {
		CandidatureWS candidatureWS = new CandidatureWS ();
		return candidatureWS.list();
	}


	public static List<Candidature> list(int selectedIdEnseignant) throws  WSException, IOException, JAXBException {
		CandidatureWS candidatureWS = new CandidatureWS ();
		return candidatureWS.listForEns(selectedIdEnseignant);
	}
	
	
	public void update() throws  WSException, JAXBException, IOException {
		CandidatureWS candidatureWS = new CandidatureWS ();	
			candidatureWS.update(this);
	}

	public void delete() throws  WSException, JAXBException, IOException {
		CandidatureWS candidatureWS = new CandidatureWS ();
			candidatureWS.delete(id);
	}

	public String getEnseignant() throws WSException, IOException, JAXBException {
		
			for ( Enseignant enseignant : Enseignant.list() ) {
				
				if(this.idEnseignant == enseignant.getId() ) 
				{
					return enseignant.getNom()+" "+enseignant.getPrenom();
				}
			}
		
		return null;
	}
}