package com.app.sms.models;

public enum Profils {
	
	Gestionnaire ("Gestionnaire"),
	Enseignant("Enseignant"),
	Eleve("Eleve");
	
	private String name;
	
	private Profils(String name) {
		this.name = name;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
}
