package com.app.sms.ui.controllers.impl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.xml.bind.JAXBException;

import com.app.sms.clientws.EleveWS;
import com.app.sms.clientws.EnseignantWS;
import com.app.sms.clientws.GestionnaireWS;
import com.app.sms.exceptions.WSException;
import com.app.sms.models.Eleve;
import com.app.sms.models.Gestionnaire;
import com.app.sms.models.Profils;
import com.app.sms.models.User;
import com.app.sms.ui.impl.MainUIApplication;
import com.app.sms.ui.impl.UILogin;
import com.app.sms.utils.Utilitaire;

public class UILoginController {
	
	private static final String API_URI = Utilitaire.getWebServiceUrl();
	
	protected UILogin uILogin;
	protected User user=null;
	
	
	public UILoginController (UILogin uILogin) {
		uILogin.displayNotification(/*"connecting to web service \""+*/API_URI+"\"");
		this.uILogin = uILogin;
		addActionListener () ;
	}
	
	public void addActionListener () {
		
		this.uILogin.addValiderListener ( new ActionListener () {

			@Override
			public void actionPerformed(ActionEvent arg0) {			
				String login = uILogin.getLogin();
				String password = uILogin.getPassword();
				String userProfil = uILogin.getUserProfil() ;
				if ( userProfil == Profils.Gestionnaire.name ()) {
					GestionnaireWS userWS = new GestionnaireWS() ;
					
					try {
						Gestionnaire user = userWS.findGestionnaireByLoginPassword(login, password);

						if ( user != null ) {
							uILogin.dispose();
							
							MainUIApplication mainApplication = new MainUIApplication(user,userProfil) ;
							GestionnaireMainUIController gestionnaireMainUIController = new GestionnaireMainUIController (uILogin, mainApplication );
							gestionnaireMainUIController.run();
						} else {
							uILogin.displayErrorMessage ( "Authentification failure ! Please try again or contact admin ..." );
						}

					} catch (WSException | JAXBException | IOException e) {
						// TODO Auto-generated catch block
						uILogin.displayErrorMessage ( "Fatal Error : " + e.getMessage() );
					}
					
				} else if ( userProfil == Profils.Enseignant.name ()) {
					User user=null ;
					try {
						EnseignantWS userWS = new EnseignantWS() ;
						user = userWS.findEnseignantByLoginPassword(login, password) ;
						if ( user!=null ) {
							uILogin.dispose();			
							MainUIApplication mainApplication = new MainUIApplication(user,userProfil) ;
							EnseignantMainUIController enseignantMainUIController = new EnseignantMainUIController (uILogin, mainApplication );
							enseignantMainUIController.run();
						} else {
							uILogin.displayErrorMessage ( "Authentification failure ! Please try again or contact admin ..." );
						}
					} catch ( WSException | IOException | JAXBException e) {
						
						uILogin.displayErrorMessage ( "Fatal Error : " + e.getMessage() );
					}
				}
				else if ( userProfil == Profils.Eleve.name ()) {
					
					try {
						EleveWS userWS = new EleveWS() ;
						Eleve user = userWS.findEleveByLoginPassword(login, password);
						if ( user != null ) {
							uILogin.dispose();
							MainUIApplication mainApplication = new MainUIApplication(user,userProfil) ;
							EleveMainUIController eleveMainUIController = new EleveMainUIController (uILogin, mainApplication );
							eleveMainUIController.run();
						} else {
							uILogin.displayErrorMessage ( "Authentification failure ! Please try again or contact admin ..." );
						}
					} catch ( WSException | JAXBException | IOException e) {
						
						uILogin.displayErrorMessage ( "Fatal Error : " + e.getMessage() );
					}
				}
			}
		});
	}

}
