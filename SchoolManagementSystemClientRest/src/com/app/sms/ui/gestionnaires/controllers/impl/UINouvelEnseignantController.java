package com.app.sms.ui.gestionnaires.controllers.impl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.app.sms.exceptions.AlreadyExistDataException;
import com.app.sms.models.Classe;
import com.app.sms.models.Enseignant;
import com.app.sms.ui.gestionnaires.controllers.IUINouvelEnseignantController;
import com.app.sms.ui.gestionnaires.impl.UINouvelEnseignant;
import com.app.sms.ui.gestionnaires.impl.UINouvelleClasse;

public class UINouvelEnseignantController implements IUINouvelEnseignantController {
	private UINouvelEnseignant uINouvelEnseignant;
	private Enseignant enseignant;
	/**
	 * @param uINouvelEnseignant
	 */
	public UINouvelEnseignantController(UINouvelEnseignant uINouvelEnseignant, Enseignant enseignant) {
		this.uINouvelEnseignant = uINouvelEnseignant;
		this.enseignant = enseignant;
		addSubmitListener () ;
		addCreerListener ();
	}
	
	@Override
	public void addSubmitListener() {
		uINouvelEnseignant.addSubmitListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setEnseignant ();
				try {
					enseignant.create();
					uINouvelEnseignant.resetUI();
					uINouvelEnseignant.displayNotification("Done successfully !");
				} catch (AlreadyExistDataException  exception) {
					uINouvelEnseignant.displayErrorMessage(exception.getMessage());
				}
			}
			
			private void setEnseignant () {
				enseignant.setId (uINouvelEnseignant.getId());
				enseignant.setNom (uINouvelEnseignant.getNom());
				enseignant.setPrenom (uINouvelEnseignant.getPrenom());
				enseignant.setEmail (uINouvelEnseignant.getEmail());
				enseignant.setTelephone (uINouvelEnseignant.getTelephone());
				enseignant.setLogin (uINouvelEnseignant.getLogin());
				enseignant.setPassword (uINouvelEnseignant.getPassword());
				enseignant.setGenre(uINouvelEnseignant.getGenre());
				enseignant.setPath(uINouvelEnseignant.getPicturePath());
			//	enseignant.setIdClasses(uINouvelEnseignant.getSelectedIdClasses());
			}
		});
	}

	public void addCreerListener () {
		uINouvelEnseignant.addCreerListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				UINouvelleClasse uINouvelleClasse = new UINouvelleClasse () ;
				Classe classe = new Classe ();
				UINouvelleClasseController uINouvelleClasseController = new UINouvelleClasseController(uINouvelleClasse, classe);
				uINouvelleClasseController.run();
				uINouvelEnseignant.updateListeClasse();
			}
		});
	}
	public void run() {
		uINouvelEnseignant.showMe();
	}
}
