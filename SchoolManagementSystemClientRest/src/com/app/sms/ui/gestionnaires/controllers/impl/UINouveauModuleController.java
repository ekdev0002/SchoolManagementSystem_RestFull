package com.app.sms.ui.gestionnaires.controllers.impl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.xml.bind.JAXBException;

import com.app.sms.exceptions.BadFormatDataException;
import com.app.sms.exceptions.WSException;
import com.app.sms.models.Module;
import com.app.sms.ui.gestionnaires.controllers.IUINouveauModuleController;
import com.app.sms.ui.gestionnaires.impl.UINouveauModule;

public class UINouveauModuleController implements IUINouveauModuleController {
	private UINouveauModule uINouveauModule;
	private Module module;
	/**
	 * @param uINouveauModule
	 */
	public UINouveauModuleController(UINouveauModule uINouveauModule, Module module) {
		this.uINouveauModule = uINouveauModule;
		this.module = module;
		addSubmitListener () ;
	}
	
	@Override
	public void addSubmitListener() {
		uINouveauModule.addSubmitListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					module.setCoefficient(uINouveauModule.getCoefficient());
					module.setLibelle(uINouveauModule.getLibelle());
					module.setDescription(uINouveauModule.getDescription());
					module.create();
					uINouveauModule.resetUI();
					uINouveauModule.displayNotification("Done successfully !");
				} catch (JAXBException | IOException | WSException | BadFormatDataException exception) {
					uINouveauModule.displayErrorMessage(exception.getMessage());
				}				
			}
		});
	}

	public void run() {
		uINouveauModule.showMe();
	}
}
