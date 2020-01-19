package com.app.sms.ui.gestionnaires.controllers.impl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.app.sms.exceptions.WSException;
import com.app.sms.models.Gestionnaire;
import com.app.sms.ui.gestionnaires.controllers.IUINouveauGestionnaireController;
import com.app.sms.ui.gestionnaires.impl.UINouveauGestionnaire;

public class UINouveauGestionnaireController implements IUINouveauGestionnaireController {

	private UINouveauGestionnaire uINouveauGestionnaire;
	private Gestionnaire gestionnaire;
	
	public UINouveauGestionnaireController(UINouveauGestionnaire uINouveauGestionnaire, Gestionnaire gestionnaire) {
		// gestinnaire
		this.uINouveauGestionnaire = uINouveauGestionnaire;
		this.gestionnaire = gestionnaire;
		addSubmitListener();
	}
	
	@Override
	public void addSubmitListener() {
		uINouveauGestionnaire.addSubmitListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				setGestionnaire();
				try {
					gestionnaire.create();
					uINouveauGestionnaire.resetUI();
					uINouveauGestionnaire.displayNotification("Done successfully !");
				} catch ( WSException exception) {
					uINouveauGestionnaire.displayErrorMessage(exception.getMessage());
				}
				
			}
			
			private void setGestionnaire() {
				gestionnaire.setAdresse(uINouveauGestionnaire.getAdresse());
				gestionnaire.setDate_naissance(uINouveauGestionnaire.getDateNaissance());
				gestionnaire.setEmail(uINouveauGestionnaire.getEmail());
				gestionnaire.setId(uINouveauGestionnaire.getId());
				gestionnaire.setLogin(uINouveauGestionnaire.getLogin());
				gestionnaire.setPassword(uINouveauGestionnaire.getPassword());
				gestionnaire.setNom(uINouveauGestionnaire.getNom());
				gestionnaire.setPrenom(uINouveauGestionnaire.getPrenom());
				gestionnaire.setTelephone(uINouveauGestionnaire.getTelephone());
			}
		});

	}
	
	public void run() {
		uINouveauGestionnaire.showMe();
	}

}
