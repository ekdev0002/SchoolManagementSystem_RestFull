package com.app.sms.ui.eleves.controllers.impl;

import java.io.IOException;
import java.util.List;

import javax.xml.bind.JAXBException;

import com.app.sms.exceptions.WSException;
import com.app.sms.models.Note;
import com.app.sms.ui.eleves.impl.UIConsulterNotes;

public class UIConsulterNoteController  {
	private UIConsulterNotes uiConsulterNotes;
	/**
	 * @param uINouveauClasse
	 */
	private List<Note> notes;
	
	public UIConsulterNoteController(UIConsulterNotes uiConsulterNotes) {
		this.uiConsulterNotes = uiConsulterNotes;
		
			try {
				this.uiConsulterNotes.loadData();
				this.uiConsulterNotes.resetFormUI();
				this.uiConsulterNotes.displayNotification("Done successfully !");

			} catch (WSException | JAXBException | IOException |  NullPointerException e) {
				uiConsulterNotes.displayErrorMessage(e.getMessage());

			}
				
	}
	
	public void run() {
		uiConsulterNotes.showMe();
	}
}
