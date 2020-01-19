package com.app.sms.ui.enseignants.controllers.impl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

import javax.xml.bind.JAXBException;

import com.app.sms.exceptions.WSException;
import com.app.sms.models.Devoirs;
import com.app.sms.ui.enseignants.controllers.IUIPlanningDevoirsController;
import com.app.sms.ui.enseignants.impl.UIPlanningDevoirs;
import com.app.sms.ui.impl.MainUIApplication;

public class UIPlanningDevoirsController implements IUIPlanningDevoirsController {
	private UIPlanningDevoirs uiPlanningDevoirs;
	private Devoirs devoirs;
	/**
	 * @param uINouveauClasse
	 */
	public UIPlanningDevoirsController(UIPlanningDevoirs uiPlanningDevoirs,Devoirs cour) {
		this.uiPlanningDevoirs = uiPlanningDevoirs;
		this.devoirs=cour;
		
		addValiderListener();
		addSupprimerListener();
		try {
			List<Devoirs> devoirs = Devoirs.listForEns(MainUIApplication.getCurrentUser().getId());
			this.uiPlanningDevoirs.loadData(devoirs);
			this.uiPlanningDevoirs.displayNotification("Done successfully !");
			
		} catch (JAXBException | IOException | WSException exception) {
			this.uiPlanningDevoirs.displayErrorMessage(exception.getMessage());
		}	
	}
	
	@Override
	public void addValiderListener() {
		uiPlanningDevoirs.addValiderListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
	
				try {
					setDevoirs (uiPlanningDevoirs);
					
					
					if(uiPlanningDevoirs.ajouterSelected())
					{
						
						devoirs.create ();
						
					}
					else
					{
						devoirs.update ();
					}
						uiPlanningDevoirs.loadData(Devoirs.listForEns(MainUIApplication.getCurrentUser().getId()));
						uiPlanningDevoirs.resetFormUI();
						uiPlanningDevoirs.displayNotification("Done successfully !");
				} catch (JAXBException | IOException | WSException exception) {
					uiPlanningDevoirs.displayErrorMessage(exception.getMessage());
				}
			}
		});
	}
	
	@Override
	public void addSupprimerListener() {
		uiPlanningDevoirs.addSupprimerListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {				
				try {					 
					setDevoirs (uiPlanningDevoirs);
					devoirs.delete () ;
					uiPlanningDevoirs.loadData(Devoirs.listForEns(MainUIApplication.getCurrentUser().getId()));
					uiPlanningDevoirs.resetFormUI();
					uiPlanningDevoirs.displayNotification("Done successfully !");
				} catch (JAXBException | IOException | WSException exception) {
					uiPlanningDevoirs.displayErrorMessage(exception.getMessage());
				}
			}
		});		
	}
	
	public void run() {
		uiPlanningDevoirs.showMe();
	}
	
	private void setDevoirs (UIPlanningDevoirs uiPlanningDevoirs) {
	
			devoirs.setId(Integer.parseInt(uiPlanningDevoirs.getId()));
			devoirs.setIdModule(Integer.parseInt(uiPlanningDevoirs.getIdModule()));
			devoirs.setIdClasse(Integer.parseInt(uiPlanningDevoirs.getIdClasse()));
			devoirs.setStatut(uiPlanningDevoirs.getStatut());
			devoirs.setDateHeure(uiPlanningDevoirs.getDateHeure());
			devoirs.setDuree(uiPlanningDevoirs.getDuree());
			devoirs.setDescription(uiPlanningDevoirs.getDescription());
	}
}
