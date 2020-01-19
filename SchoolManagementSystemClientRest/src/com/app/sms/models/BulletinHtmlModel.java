package com.app.sms.models;


import java.util.List;

public class BulletinHtmlModel {
	private Bulletin bulletin;
	private String bulletinString;
		
	public Bulletin getBulletin() {
		return bulletin;
	}



	public void setBulletin(Bulletin bulletin) {
		this.bulletin = bulletin;
	}



	public String getBulletinString() {
		return bulletinString;
	}



	public void setBulletinString(String bulletinString) {
		this.bulletinString = bulletinString;
	}



	public BulletinHtmlModel(Bulletin bulletin) {
		super();
		this.bulletin = bulletin;
		String partieNotes ="";
	    List<Row> rows =bulletin.getRowList().getRowList();
	
				
			    for(int i=0;i<rows.size();i++)
			    {

			     partieNotes=partieNotes+"        <tr>"
			       +"            <td width='154' valign='top'>"
			       + "                <p>"
			       + "                    "+rows.get(i).getLibelle()+""
			       + "                </p>"
			       + "            </td>"
			       + "            <td width='154' valign='top'>"
			       + "                <p>"
			       + "                    "+rows.get(i).getCoef()+""
			       + "                </p>"
			       + "            </td>"
			       + "            <td width='154' valign='top'>"
			       + "                <p>"
			       + "                    "+rows.get(i).getNote()+""
			       + "                </p>"
			       + "            </td>"
			       + "            <td width='154' valign='top'>"
			       + "                <p>"
			       + "                    "+rows.get(i).getNotePondere()+""
			       + "                </p>"
			       + "            </td>"
			       + "        </tr>";

			    }


			bulletinString="<!DOCTYPE html>"
			               + "<html>"
			               + "<body>"
			               + "<p align='center'>"
			               + "    <strong><u>Groupe Scolaire Excellence School </u></strong>"
			               + "</p>"
			               
			               + "<p align='center'>"
			               + "    <strong><u>Bulletin du: "+bulletin.getSemestre()+"</u></strong>"
			               + "</p> "
	               
			               
			               + "<p align='center'>"
			               + "    <strong><u>Bulletin de note n°: "+bulletin.getId()+"</u></strong>"
			               + "</p> "
			               + "<p>"
			               + "	<strong>Nom : "+bulletin.getNom()+" </p></strong>"
			               + "<p>"
			               + "    <strong>Prénom :"+bulletin.getPrenom()+"</strong>"
			               + "</p>"
			               + "<p>"
			               +"    <strong>Matricule : "+bulletin.getIdEleve()+"</strong>"
			               + "</p>"
			               + ""
			               + "<p>"
			               + "    <strong>Classe: "+bulletin.getClasse()+"</strong>"
			               + "</p>"
			               + ""
			               + ""
			               + "<p>"
			               + "    <strong>Effectif : "+bulletin.getEffectif()+"</strong>"
			               + "</p>"
			               + ""
			               + "<table border='1' cellspacing='0' cellpadding='0'>"
			               + "    <tbody>"
			               + "        <tr>"
			               + "            <td width='154' valign='top'>"
			               + "                <p>"
			               + "                    Matière"
			               + "                </p>"
			               + "            </td>"
			               + "            <td width='154' valign='top'>"
			               + "                <p>"
			               + "                    Coefficient"
			               + "                </p>"
			               + "            </td>"
			               + "            <td width='154' valign='top'>"
			               + "                <p>"
			               + "                    Moy./20"
			               + "                </p>"
			               + "            </td>"
			               + "            <td width='154' valign='top'>"
			               + "                <p>"
			               + "                    Moy. Ponderee"
			               + "                </p>"
			               + "            </td>"
			               + "        </tr>"
			               + partieNotes
			               + "    </tbody>"
			               + "</table>"
			               
			               +"<table border='1' cellspacing='0' cellpadding='0'>"
			               + "    <tbody>"
			               + "        <tr>"
			               + "            <td width='154' valign='top'>"
			               + "                <p>"
			               + "                    <strong>Moyenne de L’élève</strong>"
			               + "                </p>"
			               + "            </td>"
			               + "            <td width='154' valign='top'>"
			               + "                <p align='center'>"
			               + "                    <strong>"+bulletin.getMoyenneGenerale()+"</strong>"
			               + "                </p>"
			               + "                <p align='center'>"
			               + "                    <strong></strong>"
			               + "                </p>"
			               + "                <p align='center'>"
			               + "                    <strong></strong>"
			               + "                </p>"
			               + "            </td>"
			               + "        </tr>"
			               + "        <tr>"
			               + "            <td width='154' valign='top'>"
			               + "                <p>"
			               + "                    <strong>Rang de l’élève</strong>"
			               + "                </p>"
			               + "            </td>"
			               + "            <td width='154' valign='top'>"
			               + "                <p align='center'>"
			               + "                    <strong> "+bulletin.getRang()+"</sup></strong>"
			               + "                </p>"
			               + "                <p align='center'>"
			               + "                    <strong></strong>"
			               + "                </p>"
			               + "            </td>"
			               + "        </tr>"
			               + "        <tr>"
			               + "            <td width='154' valign='top'>"
			               + "                <p>"
			               + "                    <strong>decision</strong>"
			               + "                </p>"
			               + "            </td>"
			               + "            <td width='154' valign='top'>"
			               + "                <p>"
			               + "                    <strong></strong>"
			               + "                </p>"
			               + "                <p>"
			               + "                    <strong>"+bulletin.getDecision()+" </strong>"
			               + "                </p>"
			               + "                <p>"
			               + "                    <strong></strong>"
			               + "                </p>"
			               + "            </td>"
			               + "        </tr>"
			               + "    </tbody>"
			               + "</table>"
			               + "<p>"
			               +"    Dakar le "+bulletin.getDate()+".    Le Directeur"
			               + "</p>"
			               + "<br/>"
			               + "</body>"
			               + "</html>";

		
	}

	
}