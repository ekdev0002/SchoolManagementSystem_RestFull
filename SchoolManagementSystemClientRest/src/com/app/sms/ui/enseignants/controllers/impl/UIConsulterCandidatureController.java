package com.app.sms.ui.enseignants.controllers.impl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

import javax.xml.bind.JAXBException;

import com.app.sms.clientws.CandidatureWS;
import com.app.sms.exceptions.WSException;
import com.app.sms.models.Candidature;
import com.app.sms.ui.enseignants.controllers.IUIConsulterCandidatureController;
import com.app.sms.ui.enseignants.impl.UIConsulterCandidature;
import com.app.sms.ui.impl.MainUIApplication;

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
		
		try {

			List<Candidature> candidatures = Candidature.list(MainUIApplication.getCurrentUser().getId());
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
					System.out.println("voilaa2"+candidature.toString());
					candidature.update();
					uIConsulterCandidature.loadData(Candidature.list(MainUIApplication.getCurrentUser().getId()));
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
					uIConsulterCandidature.loadData(Candidature.list(MainUIApplication.getCurrentUser().getId()));
					uIConsulterCandidature.resetFormUI();
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
		CandidatureWS candidatureWS = new CandidatureWS();
		try {
			 candidature = candidatureWS.find(Integer.parseInt(uIConsulterCandidature.getId()));
		} catch (NumberFormatException | WSException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("voilaa"+candidature.toString());
		candidature.setState(uIConsulterCandidature.getState());
		candidature.setCommentaires(uIConsulterCandidature.getCommentaire());
		System.out.println("voilaa1"+candidature.toString());
	}
}
