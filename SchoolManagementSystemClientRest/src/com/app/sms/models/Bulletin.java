package com.app.sms.models;

import java.io.IOException;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.app.sms.clientws.BulletinWS;
import com.app.sms.exceptions.WSException;

@XmlRootElement(name = "bulletin")
@XmlType(propOrder = {"id","idEleve","nom","prenom","genre","date","decision","moyenneGenerale","effectif","rang","classe","semestre","rowList"})
public class Bulletin {
	private int id;
	private int idEleve;
	private String nom;
	private String prenom;
	private String genre;
	private String date;
	private String decision;
	private Double moyenneGenerale;
	private String effectif;
	private String rang;
	private String classe;
	private String semestre;
	private RowList rowList;
	private String[] nomColumns = {"Matière", "Coef", "Moyenne", "Moyenne Ponderée"};
	
	
	
	
	public Bulletin() {
		super();
	}
	

	public Bulletin(int id, int idEleve, String nom, String prenom, String genre, String date, String decision,
			Double moyenneGenerale, String effectif, String rang, String classe,String semestre) {
		super();
		this.id = id;
		this.idEleve = idEleve;
		this.nom = nom;
		this.prenom = prenom;
		this.genre = genre;
		this.date = date;
		this.decision = decision;
		this.moyenneGenerale = moyenneGenerale;
		this.effectif = effectif;
		this.rang = rang;
		this.classe = classe;
		this.semestre=semestre;
	}



	public int getIdEleve() {
		return idEleve;
	}

	public void setIdEleve(int idEleve) {
		this.idEleve = idEleve;
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

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDecision() {
		return decision;
	}

	public void setDecision(String decision) {
		this.decision = decision;
	}

	public Double getMoyenneGenerale() {
		return moyenneGenerale;
	}

	public void setMoyenneGenerale(Double moyenneGenerale) {
		this.moyenneGenerale = moyenneGenerale;
	}

	public String getEffectif() {
		return effectif;
	}

	public void setEffectif(String effectif) {
		this.effectif = effectif;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

//	public String[] getNomColumns() {
//		return nomColumns;
//	}
//
//	public void setNomColumns(String[] nomColumns) {
//		this.nomColumns = nomColumns;
//	}

	
	@Override
	public String toString() {
		return "Bulletin [id="+id+ ", idEleve=" +idEleve+", nom="+nom+ ", prenom="+prenom+", genre="+genre+", date="+date+", decision="+decision+"moyenneGenerale="+moyenneGenerale+", effectif="+effectif+", rang="+rang+", classe="+classe+", nomColumns="+nomColumns+", rows="+rowList+"]";
	}

	public void display() {
		System.out.println(this);
		
	}


	public static void create(String semestre)  {
		BulletinWS bulletinWS = new BulletinWS ();	
		bulletinWS.create(semestre);	
	}

	public static List<Bulletin> list() throws  WSException, IOException, JAXBException {
		// TODO Auto-generated method stub
		BulletinWS bulletinWS = new BulletinWS ();	
		return bulletinWS.list();	
	}

	public String getRang() {
		return rang;
	}

	public void setRang(String rang) {
		this.rang = rang;
	}

	public String getClasse() {
		return classe;
	}

	public void setClasse(String classe) {
		this.classe = classe;
	}



	public static List<Bulletin> list(int id2) throws  JAXBException, IOException {
		BulletinWS bulletinWS = new BulletinWS ();	
		return bulletinWS.list(id2);	
	}



	public String getSemestre() {
		return semestre;
	}



	public void setSemestre(String semestre) {
		this.semestre = semestre;
	}



	public RowList getRowList() {
		return rowList;
	}



	public void setRowList(RowList rowList) {
		this.rowList = rowList;
	}





}