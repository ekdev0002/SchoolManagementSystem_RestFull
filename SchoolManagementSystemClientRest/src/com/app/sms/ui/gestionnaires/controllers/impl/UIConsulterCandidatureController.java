package com.app.sms.ui.gestionnaires.controllers.impl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

import javax.xml.bind.JAXBException;

import com.app.sms.exceptions.WSException;
import com.app.sms.models.Candidature;
import com.app.sms.ui.gestionnaires.controllers.IUIConsulterCandidatureController;
import com.app.sms.ui.gestionnaires.impl.UIConsulterCandidature;

public class UIConsulterCandidatureController implements IUIConsulterCandidatureController {
	private UIConsulterCandidature uIConsulterCandidature;
	private Candidature candidature;
	/**
	 * @param uINouveauCandidature
	 */
	public UIConsulterCandidatureController(UIConsulterCandidature uIConsulterCandidature, Candidature candidature) {
		this.uIConsulterCandidature = uIConsulterCandidature;
		this.candidature = candidature;
		
		addUpdateListener();
		addDeleteListener();
		addGoListener();

		try {

			List<Candidature> candidatures = Candidature.list();
			this.uIConsulterCandidature.loadData(candidatures);
			this.uIConsulterCandidature.displayNotification("Done successfully !");
		} catch ( WSException | IOException | JAXBException exception) {
			this.uIConsulterCandidature.displayErrorMessage(exception.getMessage());
		}

		
		
	}
	
	@Override
	public void addUpdateListener() {
		
		uIConsulterCandidature.addUpdateListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
			
				try {
					setCandidature (uIConsulterCandidature);
					candidature.update();
					uIConsulterCandidature.loadData(Candidature.list());
					uIConsulterCandidature.displayNotification("Done successfully !");
				} catch ( WSException | IOException | JAXBException exception) {
					uIConsulterCandidature.displayErrorMessage(exception.getMessage());
				}
			}
		});
	}
	
	@Override
	public void addDeleteListener() {
		
		uIConsulterCandidature.addDeleteListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					setCandidature (uIConsulterCandidature);
					candidature.delete();
					uIConsulterCandidature.loadData(Candidature.list());
					uIConsulterCandidature.resetFormUI();
					uIConsulterCandidature.displayNotification("Done successfully !");
				} catch ( WSException | JAXBException | IOException exception) {
					uIConsulterCandidature.displayErrorMessage(exception.getMessage());
				}
			}
		});
	}
	
	@Override
	public void addGoListener() {
		uIConsulterCandidature.addGoListener (new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
			
				try {
					List<Candidature> candidatures = Candidature.list();
					uIConsulterCandidature.loadData(candidatures);
					uIConsulterCandidature.displayNotification("Done successfully !");
				} catch ( WSException | IOException | JAXBException exception) {
					uIConsulterCandidature.displayErrorMessage(exception.getMessage());
				}
			}
		});
	}

	public void run() {
		uIConsulterCandidature.showMe();
	}
	
	private void setCandidature (UIConsulterCandidature uIConsulterCandidature) {
		candidature.setId(Integer.parseInt(uIConsulterCandidature.getId()));
		candidature.setNom(uIConsulterCandidature.getNom());
		candidature.setPrenom(uIConsulterCandidature.getPrenom());
		candidature.setGenre(uIConsulterCandidature.getGenre());
		candidature.setTelephone(uIConsulterCandidature.getTelephone());
		candidature.setEmail(uIConsulterCandidature.getEmail());
		candidature.setPath(uIConsulterCandidature.getPath());
		candidature.setState(uIConsulterCandidature.getState());
		candidature.setCommentaires(uIConsulterCandidature.getCommentaire());
		candidature.setIdEnseignant(uIConsulterCandidature.getSelectedEnseignantId());
		}
}
