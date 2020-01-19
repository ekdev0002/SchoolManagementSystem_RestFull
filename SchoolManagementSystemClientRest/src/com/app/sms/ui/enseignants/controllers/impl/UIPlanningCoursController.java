package com.app.sms.ui.enseignants.controllers.impl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

import javax.xml.bind.JAXBException;

import com.app.sms.exceptions.WSException;
import com.app.sms.models.Cours;
import com.app.sms.ui.enseignants.controllers.IUIPlanningCoursController;
import com.app.sms.ui.enseignants.impl.UIPlanningCours;
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
		
		addValiderListener();
		addSupprimerListener();
		try {
			List<Cours> cours = Cours.listForEns(MainUIApplication.getCurrentUser().getId());
			this.uiPlanningCours.loadData(cours);
			this.uiPlanningCours.displayNotification("Done successfully !");
			
		} catch (JAXBException | IOException | WSException exception) {
			this.uiPlanningCours.displayErrorMessage(exception.getMessage());
		}	
	}
	
	@Override
	public void addValiderListener() {
		uiPlanningCours.addValiderListener( new ActionListener() {
			
			
			@Override
			public void actionPerformed(ActionEvent e) {
	
				try {
					setCours(uiPlanningCours);
						
					if(uiPlanningCours.ajouterSelected())
					{
						cours.create ();
					}
					else
					{
						cours.update ();
					}
						uiPlanningCours.loadData(Cours.listForEns(MainUIApplication.getCurrentUser().getId()));
						//uiPlanningCours.resetFormUI();
						uiPlanningCours.displayNotification("Done successfully !");
				} catch (JAXBException | IOException | WSException  exception) {
					uiPlanningCours.displayErrorMessage(exception.getMessage());
				}
			}
		});
	}
	
	@Override
	public void addSupprimerListener() {
		uiPlanningCours.addSupprimerListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {				
				try {					 
					setCours (uiPlanningCours);
					cours.delete () ;
					uiPlanningCours.loadData(Cours.listForEns(MainUIApplication.getCurrentUser().getId()));
					uiPlanningCours.resetFormUI();
					uiPlanningCours.displayNotification("Done successfully !");
				} catch (JAXBException | IOException | WSException exception) {
					uiPlanningCours.displayErrorMessage(exception.getMessage());
				}
			}
		});		
	}
	
	public void run() {
		uiPlanningCours.showMe();
	}
	
	private void setCours (UIPlanningCours uiPlanningCours) {
		try
		{
			System.out.println("dd="+Integer.parseInt(uiPlanningCours.getIdModule())+"  "+cours.getIdModule());
			cours.setId(Integer.parseInt(uiPlanningCours.getId()));
			cours.setIdModule(Integer.parseInt(uiPlanningCours.getIdModule()));		
			System.out.println("dd="+Integer.parseInt(uiPlanningCours.getIdModule())+"  "+cours.getIdModule());
			cours.setIdClasse(Integer.parseInt(uiPlanningCours.getIdClasse()));
			cours.setDateHeure(uiPlanningCours.getDateHeure());
			cours.setDuree(uiPlanningCours.getDuree());
			cours.setDescription(uiPlanningCours.getDescription());
		}
		catch(NullPointerException exception) {
			uiPlanningCours.displayErrorMessage(exception.getMessage());
		}
	}
}
