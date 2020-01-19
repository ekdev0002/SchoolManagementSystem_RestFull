package com.app.sms.ui.eleves.controllers.impl;

import java.io.IOException;
import java.util.List;

import javax.xml.bind.JAXBException;

import com.app.sms.exceptions.WSException;
import com.app.sms.models.Cours;
import com.app.sms.ui.eleves.controllers.IUIPlanningCoursController;
import com.app.sms.ui.eleves.impl.UIPlanningCours;
import com.app.sms.ui.impl.MainUIApplication;

public class UIPlanningCoursController implements IUIPlanningCoursController {
	private UIPlanningCours uiPlanningCours;
	private Cours cours;
	/**
	 * @param uINouveauClasse
	 */
	public UIPlanningCoursController(UIPlanningCours uiPlanningCours,Cours cour) {
		this.uiPlanningCours = uiPlanningCours;
		this.cours=cour;
		try {

			List<Cours> cours = Cours.listForEleve(MainUIApplication.getCurrentUser().getId());
			this.uiPlanningCours.loadData(cours);
			this.uiPlanningCours.displayNotification("Done successfully !");
			
		} catch (JAXBException | IOException | WSException exception) {
			this.uiPlanningCours.displayErrorMessage(exception.getMessage());
		}	
	}
	
	
	public void run() {
		uiPlanningCours.showMe();
	}
	
	
}
