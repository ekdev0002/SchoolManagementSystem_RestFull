package com.app.sms.ui.gestionnaires.controllers.impl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

import javax.xml.bind.JAXBException;

import com.app.sms.exceptions.WSException;
import com.app.sms.models.Gestionnaire;
import com.app.sms.ui.gestionnaires.controllers.IUIConsulterGestionnaireController;
import com.app.sms.ui.gestionnaires.impl.UIConsulterGestionnaire;

public class UIConsulterGestionnaireController implements IUIConsulterGestionnaireController {
	private UIConsulterGestionnaire uIConsulterGestionnaire;
	private Gestionnaire gestionnaire;
	/**
	 * @param uINouveauGestionnaire
	 */
	public UIConsulterGestionnaireController(UIConsulterGestionnaire uIConsulterGestionnaire, Gestionnaire gestionnaire) {
		this.uIConsulterGestionnaire = uIConsulterGestionnaire;
		this.gestionnaire = gestionnaire;
		
		addUpdateListener();
		addDeleteListener();
		try {

			List<Gestionnaire> gestionnairelist = Gestionnaire.list();			
			this.uIConsulterGestionnaire.loadData(gestionnairelist);
			this.uIConsulterGestionnaire.displayNotification("Done successfully !");
		} catch ( WSException | IOException exception) {
			this.uIConsulterGestionnaire.displayErrorMessage(exception.getMessage());
		}
	}
	
	@Override
	public void addUpdateListener() {
		uIConsulterGestionnaire.addUpdateListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					setGestionnaire (uIConsulterGestionnaire);
					gestionnaire.update ();
					uIConsulterGestionnaire.loadData(Gestionnaire.list());
					uIConsulterGestionnaire.resetFormUI();
					uIConsulterGestionnaire.displayNotification("Done successfully !");
				} catch ( WSException | IOException exception) {
					uIConsulterGestionnaire.displayErrorMessage(exception.getMessage());
				}
			}
		});
	}
	
	@Override
	public void addDeleteListener() {
		uIConsulterGestionnaire.addDeleteListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					setGestionnaire (uIConsulterGestionnaire);
					gestionnaire.delete();
					uIConsulterGestionnaire.loadData(Gestionnaire.list());
					uIConsulterGestionnaire.resetFormUI();
					uIConsulterGestionnaire.displayNotification("Done successfully !");
				} catch (WSException | IOException | JAXBException exception) {
					uIConsulterGestionnaire.displayErrorMessage(exception.getMessage());
				}
				
			}
		});
	}
	
	@Override
	public void addGoListener() {
		uIConsulterGestionnaire.addGoListener (new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {			
				try {
					uIConsulterGestionnaire.loadData(Gestionnaire.list());
					uIConsulterGestionnaire.resetFormUI();
					uIConsulterGestionnaire.displayNotification("Done successfully !");
				} catch (WSException | IOException exception) {
					uIConsulterGestionnaire.displayErrorMessage(exception.getMessage());
				}
			}
		});
	}

	public void run() {
		uIConsulterGestionnaire.showMe();
	}

	public void setGestionnaire (UIConsulterGestionnaire uIConsulterGestionnaire) {
		gestionnaire.setId(Integer.parseInt(uIConsulterGestionnaire.getId()));
		gestionnaire.setNom(uIConsulterGestionnaire.getNom());
		gestionnaire.setPrenom(uIConsulterGestionnaire.getPrenom());
		gestionnaire.setDate_naissance(uIConsulterGestionnaire.getDateNaissance());
		gestionnaire.setTelephone(uIConsulterGestionnaire.getTelephone());
		gestionnaire.setAdresse(uIConsulterGestionnaire.getAdresse());
		gestionnaire.setEmail(uIConsulterGestionnaire.getEmail());
		gestionnaire.setLogin(uIConsulterGestionnaire.getLogin());
		gestionnaire.setPassword(uIConsulterGestionnaire.getPassword());
	}
}
