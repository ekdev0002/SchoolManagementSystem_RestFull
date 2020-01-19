package com.app.sms.ui.controllers.impl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.app.sms.models.Cours;
import com.app.sms.models.Devoirs;
import com.app.sms.ui.controllers.IEleveMainUIController;
import com.app.sms.ui.eleves.controllers.impl.UIConsulterBulletinController;
import com.app.sms.ui.eleves.controllers.impl.UIConsulterNoteController;
import com.app.sms.ui.eleves.controllers.impl.UIPlanningCoursController;
import com.app.sms.ui.eleves.controllers.impl.UIPlanningDevoirsController;
import com.app.sms.ui.eleves.impl.UIConsulterBulletin;
import com.app.sms.ui.eleves.impl.UIConsulterNotes;
import com.app.sms.ui.eleves.impl.UIPlanningCours;
import com.app.sms.ui.eleves.impl.UIPlanningDevoirs;
import com.app.sms.ui.impl.MainUIApplication;
import com.app.sms.ui.impl.UILogin;

public class EleveMainUIController implements IEleveMainUIController {

	private MainUIApplication mainUIApplication;
	private UILogin uILogin;
	/**
	 * @param enseignantMainUIApplication
	 */
	public EleveMainUIController(UILogin uILogin,MainUIApplication mainUIApplication) {
		this.mainUIApplication = mainUIApplication;
		this.uILogin = uILogin;
		mainUIApplication.activateEleveComponent();
		
		addPlanningCoursComponentsListener();	
		addPlanningDevoirComponentsListener();
		addConsulterBulletinsComponentsListener();
		addConsulterNotesComponentsListener();
		this.addDeconnexionListener();
	}

	private void addConsulterNotesComponentsListener() {
		mainUIApplication.addConsulterNotesComponentsListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				UIConsulterNotes uiConsulterNotes = new UIConsulterNotes();
				UIConsulterNoteController uiConsulterNotesController = new UIConsulterNoteController(uiConsulterNotes);
				mainUIApplication.addPanel(uiConsulterNotes, "uiConsulterNotes");
				mainUIApplication.ShowPanel("uiConsulterNotes");			
			}
		});		
		
	}

	private void addDeconnexionListener() {
		this.mainUIApplication.addDeconnexionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				deconnexion();			
			}
		});
	}

	private void deconnexion() {
		this.mainUIApplication.dispose();
		this.uILogin.setVisible(true);
	}	
	


	@Override
	public void addPlanningCoursComponentsListener() {
		mainUIApplication.addPlanningCoursComponentsListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Cours cours = new Cours ();
				UIPlanningCours uIPlanningCours  = new UIPlanningCours() ;
				UIPlanningCoursController uIPlanningCoursController = new UIPlanningCoursController(uIPlanningCours, cours);
				mainUIApplication.addPanel(uIPlanningCours, "uIPlanningCours");
				mainUIApplication.ShowPanel("uIPlanningCours");			
				}
		});
	}

	@Override
	public void addPlanningDevoirComponentsListener() {
		mainUIApplication.addPlanningDevoirComponentsListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Devoirs devoirs = new Devoirs ();
				UIPlanningDevoirs uIPlanningDevoirs  = new UIPlanningDevoirs() ;
				UIPlanningDevoirsController uIPlanningDevoirsController = new UIPlanningDevoirsController(uIPlanningDevoirs, devoirs);
				mainUIApplication.addPanel(uIPlanningDevoirs, "uIPlanningDevoirs");
				mainUIApplication.ShowPanel("uIPlanningDevoirs");			
				}
		});
	}



	@Override
	public void addConsulterBulletinsComponentsListener() {
		// TODO Auto-generated method stub
		mainUIApplication.addGestionBulletinsComponentsListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				UIConsulterBulletin uiGestionBulletin = new UIConsulterBulletin();
				UIConsulterBulletinController uiNouveauGestionnaireController = new UIConsulterBulletinController(uiGestionBulletin);
				mainUIApplication.addPanel(uiGestionBulletin, "uiGestionBulletin");
				mainUIApplication.ShowPanel("uiGestionBulletin");			
			}
		});		
	}
	
	public void run () {
		mainUIApplication.setVisible(true);
	}

}
