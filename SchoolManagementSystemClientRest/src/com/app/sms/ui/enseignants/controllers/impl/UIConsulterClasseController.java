package com.app.sms.ui.enseignants.controllers.impl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

import javax.xml.bind.JAXBException;

import com.app.sms.exceptions.WSException;
import com.app.sms.models.Classe;
import com.app.sms.ui.enseignants.controllers.IUIConsulterClasseController;
import com.app.sms.ui.enseignants.impl.UIConsulterClasse;
import com.app.sms.ui.impl.MainUIApplication;

public class UIConsulterClasseController implements IUIConsulterClasseController {
	private UIConsulterClasse uIConsulterClasse;
	private Classe classe;
	/**
	 * @param uINouveauClasse
	 */
	public UIConsulterClasseController(UIConsulterClasse uIConsulterClasse, Classe classe) {
		this.uIConsulterClasse = uIConsulterClasse;
		this.classe = classe;
		
		addUpdateListener();
		addDeleteListener();
		try {
			List<Classe> classes = Classe.listForEns(MainUIApplication.getCurrentUser().getId());
			this.uIConsulterClasse.loadData(classes);
			this.uIConsulterClasse.displayNotification("Done successfully !");
			
		} catch ( WSException | JAXBException | IOException exception) {
			this.uIConsulterClasse.displayErrorMessage(exception.getMessage());
		}			
	}
	
	@Override
	public void addUpdateListener() {
		uIConsulterClasse.addUpdateListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					setClasse (uIConsulterClasse);
					classe.update ();
					uIConsulterClasse.loadData(Classe.listForEns(MainUIApplication.getCurrentUser().getId()));
					uIConsulterClasse.resetFormUI();
					uIConsulterClasse.displayNotification("Done successfully !");
				} catch ( WSException | JAXBException | IOException exception) {
					uIConsulterClasse.displayErrorMessage(exception.getMessage());
				}
			}
		});
	}
	
	@Override
	public void addDeleteListener() {
		uIConsulterClasse.addDeleteListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {				
				try {					 
					setClasse (uIConsulterClasse);
					classe.delete () ;
					uIConsulterClasse.loadData(Classe.listForEns(MainUIApplication.getCurrentUser().getId()));
					uIConsulterClasse.resetFormUI();
					uIConsulterClasse.displayNotification("Done successfully !");
				} catch ( WSException | JAXBException | IOException exception) {
					uIConsulterClasse.displayErrorMessage(exception.getMessage());
				}
			}
		});		
	}
	

	public void run() {
		uIConsulterClasse.showMe();
	}
	
	private void setClasse (UIConsulterClasse uIConsulterClasse) {
		classe.setId(Integer.parseInt(uIConsulterClasse.getId()));
		classe.setLibelle(uIConsulterClasse.getLibelle());
		classe.setDescription(uIConsulterClasse.getDescription());
	}
}
