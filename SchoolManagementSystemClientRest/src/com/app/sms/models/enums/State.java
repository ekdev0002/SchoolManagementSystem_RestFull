package com.app.sms.models.enums;

public enum State {
	
	REJETEE("Rejetée"),
	RETENUE("Retenue"),
	CLOSE("Clôturée"),
	EN_COURS("Analyse en cours"),
	OUVERTE("Ouverte - Non encore affectée"); 
	
	private String state;
	 
	  State(String state){
	    this.state = state;
	  }
	   
	  public String toString(){
	    return state;
	  }

}
