package com.app.sms.ui.gestionnaires.controllers.impl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.xml.bind.JAXBException;

import com.app.sms.exceptions.BadFormatDataException;
import com.app.sms.exceptions.DateOutOfBoundsException;
import com.app.sms.exceptions.NotAvailableDateFormatException;
import com.app.sms.exceptions.WSException;
import com.app.sms.models.Candidature;
import com.app.sms.ui.gestionnaires.controllers.IUINouvelleCandidatureController;
import com.app.sms.ui.gestionnaires.impl.UINouvelleCandidature2;

public class UINouvelleCandidatureController implements IUINouvelleCandidatureController {
	private UINouvelleCandidature2 uINouvelleCandidature;
	private Candidature candidature;
	/**
	 * @param uINouvelleCandidature
	 */
	public UINouvelleCandidatureController(UINouvelleCandidature2 uINouvelleCandidature, Candidature candidature) {
		this.uINouvelleCandidature = uINouvelleCandidature;
		this.candidature = candidature;
		addSubmitListener () ;
	}

	@Override
	public void addSubmitListener() {
		uINouvelleCandidature.addSubmitListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					setCandidature ();
					candidature.create();
					candidature.display();
					uINouvelleCandidature.resetUI();
					uINouvelleCandidature.displayNotification("Done successfully !");
				} catch ( BadFormatDataException | NumberFormatException | DateOutOfBoundsException | NotAvailableDateFormatException | WSException | JAXBException | IOException exception) {
					uINouvelleCandidature.displayErrorMessage("An error occurred : " + exception.getMessage());
				}
			}
			
			private void setCandidature () throws BadFormatDataException, NumberFormatException, DateOutOfBoundsException, NotAvailableDateFormatException {
				candidature.setNom (uINouvelleCandidature.getNom());
				candidature.setPrenom (uINouvelleCandidature.getPrenom());
				candidature.setEmail (uINouvelleCandidature.getEmail());
				candidature.setTelephone (uINouvelleCandidature.getTelephone());
				candidature.setGenre(uINouvelleCandidature.getGenre());
				candidature.setBirthday(uINouvelleCandidature.getBirthday());
				candidature.setPath(uINouvelleCandidature.getPath());
				candidature.setCv(uINouvelleCandidature.getCVUrl());
				candidature.setDiplomeList(uINouvelleCandidature.getDiplomes());
				candidature.setIdEnseignant(uINouvelleCandidature.getSelectedEnseignantId());
			}
		});
	}
	
	public void run() {
		uINouvelleCandidature.showMe();
	}
}
