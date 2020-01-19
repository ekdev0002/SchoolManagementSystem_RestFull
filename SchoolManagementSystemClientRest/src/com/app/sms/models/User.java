package com.app.sms.models;

import javax.xml.bind.annotation.XmlTransient;

@XmlTransient
public abstract class User {
	protected int id;
	protected String nom;
	protected String prenom;
	protected String adresse;
	protected String telephone;
	protected String email;
	protected String genre;
	protected String login;
	protected String password ;
	protected String userProfil;
	protected String path;
	
	public User () {}
	/**
	 * @param id
	 * @param nom
	 * @param prenom
	 * @param adresse
	 * @param date_naissance
	 * @param telephone
	 * @param email
	 * @param login
	 * @param password
	 */
	public User(int id, String nom, String prenom, String adresse, String genre, String telephone,
			String email, String login, String password) {
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
		this.adresse = adresse;
		this.genre = genre;
		this.telephone = telephone;
		this.email = email;
		this.login = login;
		this.password = password;
	}
	
	public User(int id, String nom, String prenom, String genre, String telephone,
			String email, String login, String password) {
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
		this.genre = genre;
		this.telephone = telephone;
		this.email = email;
		this.login = login;
		this.password = password;
	}
	
	/**
	 * @param login
	 * @param password
	 */
	public User(String login, String password) {
		this.login = login;
		this.password = password;
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
	
	public String getAdresse() {
		return adresse;
	}
	
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
	
	public String getGenre() {
		return genre;
	}
	
	public void setGenre(String genre) {
		this.genre = genre;
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
	
	public String getLogin() {
		return login;
	}
	
	public void setLogin(String login) {
		this.login = login;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public int getId() {
		return id;
	}

	public String getUserProfil () {
		return userProfil;
	}

	public void setUserProfil(String userProfil) {
		this.userProfil = userProfil;
	}
	
	public void setPath(String path) {
		this.path = path;
	}
	
	public String getPath() {
		return this.path;
	}
	
	public void setId(String id) {
		this.id = Integer.valueOf(id);
	}
	
	public void setId(int id) {
		this.id = id;
	}

}