package com.app.sms.ui.controllers.impl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.app.sms.models.Candidature;
import com.app.sms.models.Classe;
import com.app.sms.models.Eleve;
import com.app.sms.models.Enseignant;
import com.app.sms.models.Gestionnaire;
import com.app.sms.models.Module;
import com.app.sms.ui.controllers.IGestionnaireMainUIController;
import com.app.sms.ui.gestionnaires.controllers.impl.UIConsulterCandidatureController;
import com.app.sms.ui.gestionnaires.controllers.impl.UIConsulterClasseController;
import com.app.sms.ui.gestionnaires.controllers.impl.UIConsulterEleveController;
import com.app.sms.ui.gestionnaires.controllers.impl.UIConsulterEnseignantController;
import com.app.sms.ui.gestionnaires.controllers.impl.UIConsulterGestionnaireController;
import com.app.sms.ui.gestionnaires.controllers.impl.UIConsulterModuleController;
import com.app.sms.ui.gestionnaires.controllers.impl.UIGestionBulletinController;
import com.app.sms.ui.gestionnaires.controllers.impl.UINouveauGestionnaireController;
import com.app.sms.ui.gestionnaires.controllers.impl.UINouveauModuleController;
import com.app.sms.ui.gestionnaires.controllers.impl.UINouvelEleveController;
import com.app.sms.ui.gestionnaires.controllers.impl.UINouvelEnseignantController;
import com.app.sms.ui.gestionnaires.controllers.impl.UINouvelleCandidatureController;
import com.app.sms.ui.gestionnaires.controllers.impl.UINouvelleClasseController;
import com.app.sms.ui.gestionnaires.impl.UIConsulterCandidature;
import com.app.sms.ui.gestionnaires.impl.UIConsulterClasse;
import com.app.sms.ui.gestionnaires.impl.UIConsulterEleve;
import com.app.sms.ui.gestionnaires.impl.UIConsulterEnseignant;
import com.app.sms.ui.gestionnaires.impl.UIConsulterGestionnaire;
import com.app.sms.ui.gestionnaires.impl.UIConsulterModule;
import com.app.sms.ui.gestionnaires.impl.UIGestionBulletin;
import com.app.sms.ui.gestionnaires.impl.UINouveauGestionnaire;
import com.app.sms.ui.gestionnaires.impl.UINouveauModule;
import com.app.sms.ui.gestionnaires.impl.UINouvelEleve;
import com.app.sms.ui.gestionnaires.impl.UINouvelEnseignant;
import com.app.sms.ui.gestionnaires.impl.UINouvelleCandidature2;
import com.app.sms.ui.gestionnaires.impl.UINouvelleClasse;
import com.app.sms.ui.impl.MainUIApplication;
import com.app.sms.ui.impl.UILogin;
import com.app.sms.utils.Utilitaire;

public class GestionnaireMainUIController implements IGestionnaireMainUIController {
	
	private MainUIApplication mainUIApplication;
	protected UILogin uILogin;
	/**
	 * @param gestionnaireMainUIApplication
	 */
	public GestionnaireMainUIController(UILogin uILogin,MainUIApplication mainUIApplication) {
		this.mainUIApplication = mainUIApplication;
		this.uILogin = uILogin;
		mainUIApplication.activateGestionnaireComponent();
		
		this.addBatchProcessComponentsListener ();
		this.addConsulterCandidatureComponentsListener();
		this.addConsulterModuleComponentsListener();
		this.addConsulterClasseComponentsListener();
		this.addConsulterEleveComponentsListener();
		this.addConsulterEnseignantComponentsListener();
		this.addConsulterGestionnaireComponentsListener();
		this.addNouvelleCandidatureComponentsListener();
		this.addNouveauModuleComponentsListener();
		this.addNouvelleClasseComponentsListener();
		this.addNouvelEleveComponentsListener();
		this.addNouvelEnseignantComponentsListener();
		this.addNouveauGestionnaireComponentsListener();
		this.addGesionBulletinsComponentsListener();
		
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
				mainUIApplication.addPanel(uIConsulterCandidature, "consulerterC");
				mainUIApplication.ShowPanel("consulerterC");
			}
		});
	}
	@Override
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
				mainUIApplication.addPanel(uIConsulterEleve, "uIConsulterEleve");
				mainUIApplication.ShowPanel("uIConsulterEleve");
				UIConsulterEleveController uIConsulterEleveController = new UIConsulterEleveController(uIConsulterEleve, eleve);
			}
		});
	}
	@Override
	public void addConsulterEnseignantComponentsListener() {
		mainUIApplication.addConsulterEnseignantComponentsListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Enseignant enseignant = new Enseignant () ;
				UIConsulterEnseignant uIConsulterEnseignant = new UIConsulterEnseignant () ;
				mainUIApplication.addPanel(uIConsulterEnseignant, "uIConsulterEnseignant");
				mainUIApplication.ShowPanel("uIConsulterEnseignant");
				UIConsulterEnseignantController uIConsulterEnseignantController = new UIConsulterEnseignantController(uIConsulterEnseignant, enseignant);
			}
		});
	}
	@Override
	public void addConsulterGestionnaireComponentsListener() {
		mainUIApplication.addConsulterGestionnaireComponentsListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("riiir");
				Gestionnaire gestionnaire = new Gestionnaire();
				UIConsulterGestionnaire uiConsulterGestionnaire = new UIConsulterGestionnaire();
				UIConsulterGestionnaireController uiConsulterGestionnaireController = new UIConsulterGestionnaireController(uiConsulterGestionnaire, gestionnaire);
				mainUIApplication.addPanel(uiConsulterGestionnaire, "uiConsulterGestionnaire");
				mainUIApplication.ShowPanel("uiConsulterGestionnaire");
			}
		});
	}

	@Override
	public void addNouvelleCandidatureComponentsListener() {
		mainUIApplication.addNouvelleCandidatureComponentsListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				UINouvelleCandidature2 uINouvelleCandidature = new UINouvelleCandidature2 () ;
				Candidature candidature = new Candidature ();
				UINouvelleCandidatureController uINouvelleCandidatureController = new UINouvelleCandidatureController(uINouvelleCandidature, candidature);
				mainUIApplication.addPanel(uINouvelleCandidature, "uINouvelleCandidature");
				mainUIApplication.ShowPanel("uINouvelleCandidature");			
				}
		});
	}
	@Override
	public void addNouveauModuleComponentsListener() {
		mainUIApplication.addNouveauModuleComponentsListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				UINouveauModule uINouveauModule = new UINouveauModule () ;
				Module module = new Module ();
				UINouveauModuleController uINouveauModuleController = new UINouveauModuleController(uINouveauModule, module);
				mainUIApplication.addPanel(uINouveauModule, "uINouveauModule");
				mainUIApplication.ShowPanel("uINouveauModule");			
				
			}
		});
	}
	@Override
	public void addNouvelleClasseComponentsListener() {
		mainUIApplication.addNouvelleClasseComponentsListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				UINouvelleClasse uINouvelleClasse = new UINouvelleClasse () ;
				Classe classe = new Classe ();
				UINouvelleClasseController uINouvelleClasseController = new UINouvelleClasseController(uINouvelleClasse, classe);
				mainUIApplication.addPanel(uINouvelleClasse, "uINouvelleClasse");
				mainUIApplication.ShowPanel("uINouvelleClasse");			
			}
		});
	}
	@Override
	public void addNouvelEleveComponentsListener() {
		mainUIApplication.addNouvelEleveComponentsListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				UINouvelEleve uINouvelEleve = new UINouvelEleve () ;
				Eleve eleve = new Eleve ();
				UINouvelEleveController uINouvelEleveController = new UINouvelEleveController(uINouvelEleve, eleve);
				mainUIApplication.addPanel(uINouvelEleve, "uINouvelEleve");
				mainUIApplication.ShowPanel("uINouvelEleve");			
			}
		});
	}
	@Override
	public void addNouvelEnseignantComponentsListener() {
		mainUIApplication.addNouvelEnseignantComponentsListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				UINouvelEnseignant uINouvelEnseignant = new UINouvelEnseignant() ;
				Enseignant enseignant = new Enseignant ();
				UINouvelEnseignantController uINouvelEnseignantController = new UINouvelEnseignantController(uINouvelEnseignant, enseignant);
				mainUIApplication.addPanel(uINouvelEnseignant, "uINouvelEnseignant");
				mainUIApplication.ShowPanel("uINouvelEnseignant");			
			}
		});
	}
	@Override
	public void addNouveauGestionnaireComponentsListener() {
		mainUIApplication.addNouveauGestionnaireComponentsListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				UINouveauGestionnaire uiNouveauGestionnaire = new UINouveauGestionnaire();
				Gestionnaire gestionnaire = new Gestionnaire();
				UINouveauGestionnaireController uiNouveauGestionnaireController = new UINouveauGestionnaireController(uiNouveauGestionnaire, gestionnaire);
				mainUIApplication.addPanel(uiNouveauGestionnaire, "uiNouveauGestionnaire");
				mainUIApplication.ShowPanel("uiNouveauGestionnaire");			
			}
		});
	}
	public void run () {
		mainUIApplication.setVisible(true);
	}

	@Override
	public void addGesionBulletinsComponentsListener() {
		// TODO Auto-generated method stub
		mainUIApplication.addGestionBulletinsComponentsListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				UIGestionBulletin uiGestionBulletin = new UIGestionBulletin();
				UIGestionBulletinController uiNouveauGestionnaireController = new UIGestionBulletinController(uiGestionBulletin);
				mainUIApplication.addPanel(uiGestionBulletin, "uiGestionBulletin");
				mainUIApplication.ShowPanel("uiGestionBulletin");			
			}
		});		
	}
}
