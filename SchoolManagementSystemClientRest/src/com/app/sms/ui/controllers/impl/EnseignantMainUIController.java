package com.app.sms.ui.controllers.impl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.app.sms.models.Candidature;
import com.app.sms.models.Classe;
import com.app.sms.models.Cours;
import com.app.sms.models.Devoirs;
import com.app.sms.models.Eleve;
import com.app.sms.models.Module;
import com.app.sms.ui.controllers.IEnseignantMainUIController;
import com.app.sms.ui.enseignants.controllers.impl.UIConsulterCandidatureController;
import com.app.sms.ui.enseignants.controllers.impl.UIConsulterClasseController;
import com.app.sms.ui.enseignants.controllers.impl.UIConsulterEleveController;
import com.app.sms.ui.enseignants.controllers.impl.UIConsulterModuleController;
import com.app.sms.ui.enseignants.controllers.impl.UIPlanningCoursController;
import com.app.sms.ui.enseignants.controllers.impl.UIPlanningDevoirsController;
import com.app.sms.ui.enseignants.controllers.impl.UISaisieNoteController;
import com.app.sms.ui.enseignants.impl.UIConsulterCandidature;
import com.app.sms.ui.enseignants.impl.UIConsulterClasse;
import com.app.sms.ui.enseignants.impl.UIConsulterEleve;
import com.app.sms.ui.enseignants.impl.UIConsulterModule;
import com.app.sms.ui.enseignants.impl.UIPlanningCours;
import com.app.sms.ui.enseignants.impl.UIPlanningDevoirs;
import com.app.sms.ui.enseignants.impl.UISaisieNotes;
import com.app.sms.ui.impl.MainUIApplication;
import com.app.sms.ui.impl.UILogin;
import com.app.sms.utils.Utilitaire;

public class EnseignantMainUIController implements IEnseignantMainUIController {
	
	private MainUIApplication mainUIApplication;
	private UILogin uILogin;
	/**
	 * @param enseignantMainUIApplication
	 */
	public EnseignantMainUIController(UILogin uILogin,MainUIApplication mainUIApplication) {
		this.mainUIApplication = mainUIApplication;
		this.uILogin = uILogin;
		mainUIApplication.activateEnseignantComponent();
		
		addBatchProcessComponentsListener ();
		addConsulterCandidatureComponentsListener();
		addConsulterModuleComponentsListener();
		addConsulterClasseComponentsListener();
		addConsulterEleveComponentsListener();		
		addPlanningCoursComponentsListener();	
		addPlanningDevoirComponentsListener();
		addSaisiNoteComponentsListener();
		this.addDeconnexionListener();
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
	public void addBatchProcessComponentsListener() {
		mainUIApplication.addBatchProcessComponentsListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Utilitaire.displayDefaultMaintenanceMessage();
			}
		});
	}

	@Override
	public void addConsulterCandidatureComponentsListener() {
		mainUIApplication.addConsulterCandidatureComponentsListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Candidature candidature = new Candidature ();
				UIConsulterCandidature uIConsulterCandidature = new UIConsulterCandidature () ;
				UIConsulterCandidatureController uIConsulterCandidatureController = new UIConsulterCandidatureController(uIConsulterCandidature, candidature);
				mainUIApplication.addPanel(uIConsulterCandidature, "uIConsulterCandidature");
				mainUIApplication.ShowPanel("uIConsulterCandidature");			
			}
		});
	}

	public void addConsulterModuleComponentsListener() {
		mainUIApplication.addConsulterModuleComponentsListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Module module = new Module ();
				UIConsulterModule uIConsulterModule = new UIConsulterModule () ;
				UIConsulterModuleController uIConsulterModuleController = new UIConsulterModuleController(uIConsulterModule, module);
				mainUIApplication.addPanel(uIConsulterModule, "uIConsulterModule");
				mainUIApplication.ShowPanel("uIConsulterModule");			
			}
		});
	}
	@Override
	public void addConsulterClasseComponentsListener() {
		mainUIApplication.addConsulterClasseComponentsListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Classe classe = new Classe () ;
				UIConsulterClasse uIConsulterClasse = new UIConsulterClasse () ;
				UIConsulterClasseController uIConsulterClasseController = new UIConsulterClasseController(uIConsulterClasse, classe);
				mainUIApplication.addPanel(uIConsulterClasse, "uIConsulterClasse");
				mainUIApplication.ShowPanel("uIConsulterClasse");			
			}
		});
	}
	@Override
	public void addConsulterEleveComponentsListener() {
		mainUIApplication.addConsulterEleveComponentsListener( new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Eleve eleve = new Eleve ();
				UIConsulterEleve uIConsulterEleve = new UIConsulterEleve () ;
				UIConsulterEleveController uIConsulterEleveController = new UIConsulterEleveController(uIConsulterEleve, eleve);
				mainUIApplication.addPanel(uIConsulterEleve, "uIConsulterEleve");
				mainUIApplication.ShowPanel("uIConsulterEleve");			
			}
		});
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

	public void addSaisiNoteComponentsListener() {
		mainUIApplication.addSaisieNoteComponentsListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {			
				UISaisieNotes uISaisieNotes  = new UISaisieNotes() ;
				UISaisieNoteController uISaisieNoteController = new UISaisieNoteController(uISaisieNotes);
				mainUIApplication.addPanel(uISaisieNotes, "uISaisieNotes");
				mainUIApplication.ShowPanel("uISaisieNotes");			
				}
		});
	}

	
	
	public void run () {
		mainUIApplication.setVisible(true);
	}
}
