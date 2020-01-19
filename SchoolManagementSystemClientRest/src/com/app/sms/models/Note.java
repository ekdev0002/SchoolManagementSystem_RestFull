package com.app.sms.models;

import java.io.IOException;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.app.sms.clientws.NoteWS;
import com.app.sms.exceptions.WSException;
import com.fasterxml.jackson.annotation.JsonProperty;

@XmlRootElement(name = "note")
@XmlType(propOrder = {"id", "idDevoirs", "note","coef", "idEleve"})

public class Note {

	@JsonProperty
	private int id;
	@JsonProperty
	private int idDevoirs;
	@JsonProperty
	private double note;
	@JsonProperty
	private int coef;
	@JsonProperty
	private int idEleve;
	
	
	
	public Note() {
		super();
	}

	public Note(int id, int idDevoirs, double note, int coef, int idEleve) {
		this(idDevoirs, note,coef,idEleve);
		this.id = id;
	}

	public Note(int idDevoirs, double note, int coef, int idEleve) {
		this.idDevoirs = idDevoirs;
		this.note = note;
		this.coef = coef;
		this.idEleve = idEleve;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdDevoirs() {
		return idDevoirs;
	}

	public void setIdDevoirs(int idDevoirs) {
		this.idDevoirs = idDevoirs;
	}

	public double getNote() {
		return note;
	}

	public void setNote(double note) {
		this.note = note;
	}

	public int getCoef() {
		return coef;
	}

	public void setCoef(int coef) {
		this.coef = coef;
	}

	public int getIdEleve() {
		return idEleve;
	}

	public void setIdEleve(int idEleve) {
		this.idEleve = idEleve;
	}

	@Override
	public String toString() {
		return "Note [id= "+id+",idDevoirs+"+idDevoirs+", note="+note+",coef="+coef+", idEleve="+idEleve+"]";
		
	}

	public void display() {
		System.out.println(this);
		
	}
	
	
	
	public void create() throws WSException, JAXBException, IOException {		
		
		NoteWS noteWS = new NoteWS ();
		
			noteWS.create(this) ;
		
	}

	public void update() throws  WSException, JAXBException, IOException {
		NoteWS noteWS = new NoteWS ();
			noteWS.update(this);
		
	}

	public void delete() throws  WSException, JAXBException, IOException {
		NoteWS noteWS = new NoteWS ();	
			noteWS.delete(id);
	}

	public static List<Note> list(String idDevoirs) throws WSException, IOException, JAXBException {
		NoteWS noteWS = new NoteWS ();
		return noteWS.listByDev(idDevoirs);
	}

	public static Note findByEleve(int idDevoir, int idEleve) throws JAXBException, IOException {
		NoteWS noteWS = new NoteWS ();
		return noteWS.findByEleve(idDevoir,idEleve);

	}
}
