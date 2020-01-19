package com.app.sms.models;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.fasterxml.jackson.annotation.JsonProperty;

@XmlRootElement(name = "diplome")
@XmlType(propOrder = {"id", "idCandidature", "url"})
public class Diplome {
	@JsonProperty
	private int id;
	@JsonProperty
	private int idCandidature;
	@JsonProperty
	private String url;	
	
	/**
	 * @param id
	 * @param idCandidature
	 * @param url
	 */
	public Diplome(int id, int idCandidature, String url) {
		this(id, url);
		this.idCandidature = idCandidature;
	}
	/**
	 * @param id
	 * @param url
	 */
	public Diplome(int id, String url) {
		this(url);
		this.id = id;		
	}
	/**
	 * @param url
	 */
	public Diplome(String url) {
		this.url = url;
	}
	/**
	 * 
	 */
	public Diplome() {}
	
	public int getId() {	
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	public void setIdCandidature(int id) {
		idCandidature = id;		
	}
	
	public int getIdCandidature() {
		return idCandidature;
	}
	@Override
	public String toString() {
		return "diplome [id="+id+", idCandidature="+idCandidature+", url="+url+"]";
	}

	public void display() {
		System.out.println(this);

	}

}
