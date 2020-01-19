package com.app.sms.ui.enseignants.controllers.impl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

import javax.xml.bind.JAXBException;

import com.app.sms.exceptions.WSException;
import com.app.sms.models.Module;
import com.app.sms.ui.enseignants.controllers.IUIConsulterModuleController;
import com.app.sms.ui.enseignants.impl.UIConsulterModule;

public class UIConsulterModuleController implements IUIConsulterModuleController {
	private UIConsulterModule uIConsulterModule;
	private Module module;

	/**
	 * @param uINouveauModule
	 */
	public UIConsulterModuleController(UIConsulterModule uIConsulterModule, Module module) {
		this.uIConsulterModule = uIConsulterModule;
		this.module = module;
		
		addUpdateListener();
		addDeleteListener();
		try {
			List<Module> modules = Module.listForEns();
			this.uIConsulterModule.loadData(modules);
			this.uIConsulterModule.displayNotification("Done successfully !");
			
		} catch (JAXBException | IOException | WSException exception) {
			this.uIConsulterModule.displayErrorMessage(exception.getMessage());
		}			

	
	}
	
	@Override
	public void addUpdateListener() {
		uIConsulterModule.addUpdateListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
			
				try {
					setModule (uIConsulterModule);
					//module.update();
					uIConsulterModule.loadData(Module.listForEns());
					uIConsulterModule.resetFormUI();
					uIConsulterModule.displayNotification("Done successfully !");
				} catch (JAXBException | IOException | WSException exception) {
					uIConsulterModule.displayErrorMessage(exception.getMessage());
				}
			}
		});
	}
	
	@Override
	public void addDeleteListener() {
		uIConsulterModule.addDeleteListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					setModule (uIConsulterModule);
					module.delete();
					uIConsulterModule.loadData(Module.listForEns());
					uIConsulterModule.resetFormUI();
					uIConsulterModule.displayNotification("Done successfully !");
				} catch (JAXBException | IOException | WSException exception) {
					uIConsulterModule.displayErrorMessage(exception.getMessage());
				}
			}
		});
	}

	public void run() {
		uIConsulterModule.showMe();
	}
	
	private void setModule (UIConsulterModule uIConsulterModule) {
		module.setId(Integer.parseInt(uIConsulterModule.getId()));
		module.setCoefficient(Integer.parseInt(uIConsulterModule.getCoefficient()));
		module.setLibelle(uIConsulterModule.getLibelle());
		module.setDescription(uIConsulterModule.getDescription());
	}
}
