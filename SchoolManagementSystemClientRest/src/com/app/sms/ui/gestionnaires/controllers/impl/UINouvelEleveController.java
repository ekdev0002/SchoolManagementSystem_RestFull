package com.app.sms.ui.gestionnaires.controllers.impl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.xml.bind.JAXBException;

import com.app.sms.exceptions.WSException;
import com.app.sms.models.Classe;
import com.app.sms.models.Eleve;
import com.app.sms.ui.gestionnaires.controllers.IUINouvelEleveController;
import com.app.sms.ui.gestionnaires.impl.UINouvelEleve;
import com.app.sms.ui.gestionnaires.impl.UINouvelleClasse;

public class UINouvelEleveController implements IUINouvelEleveController {
	private UINouvelEleve uINouvelEleve;
	private Eleve eleve;
	/**
	 * @param uINouvelEleve
	 */
	public UINouvelEleveController(UINouvelEleve uINouvelEleve, Eleve eleve) {
		this.uINouvelEleve = uINouvelEleve;
		this.eleve = eleve;
		addSubmitListener () ;
		addCreerListener ();
	}
	
	@Override
	public void addSubmitListener() {
		uINouvelEleve.addSubmitListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					setEleve ();
					eleve.create();
					uINouvelEleve.resetUI();
					uINouvelEleve.displayNotification("Done successfully !");
				} catch (JAXBException | IOException | WSException exception) {
					uINouvelEleve.displayErrorMessage(exception.getMessage());
				}				
			}
			
			private void setEleve () {
				eleve.setId (uINouvelEleve.getId());
				eleve.setNom (uINouvelEleve.getNom());
				eleve.setPrenom (uINouvelEleve.getPrenom());
				eleve.setEmail (uINouvelEleve.getEmail());
				eleve.setTelephone (uINouvelEleve.getTelephone());
				eleve.setLogin (uINouvelEleve.getLogin());
				eleve.setPassword (uINouvelEleve.getPassword());
				eleve.setGenre(uINouvelEleve.getGenre());
				eleve.setIdClasse(Integer.parseInt(uINouvelEleve.getIdClasse()));
				eleve.setLibelleClasse(uINouvelEleve.getLibelleClasse());
				eleve.setPath(uINouvelEleve.getPicturePath());
			}
		});
	}

	public void addCreerListener () {
		uINouvelEleve.addCreerListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				UINouvelleClasse uINouvelleClasse = new UINouvelleClasse () ;
				Classe classe = new Classe ();
				UINouvelleClasseController uINouvelleClasseController = new UINouvelleClasseController(uINouvelleClasse, classe);
				uINouvelleClasseController.run();
				uINouvelEleve.updateListeDeroulanteClasse();
			}
		});
	}
	public void run() {
		uINouvelEleve.showMe();
	}
}
