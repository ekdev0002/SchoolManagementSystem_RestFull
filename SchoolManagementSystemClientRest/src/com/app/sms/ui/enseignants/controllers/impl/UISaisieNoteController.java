package com.app.sms.ui.enseignants.controllers.impl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

import javax.xml.bind.JAXBException;

import com.app.sms.exceptions.WSException;
import com.app.sms.models.Note;
import com.app.sms.ui.enseignants.controllers.IUISaisieNoteController;
import com.app.sms.ui.enseignants.impl.UISaisieNotes;

public class UISaisieNoteController implements IUISaisieNoteController {
	private UISaisieNotes uiSaisieNotes;
	/**
	 * @param uINouveauClasse
	 */
	private List<Note> notes;
	
	public UISaisieNoteController(UISaisieNotes uiSaisieNotes) {
		this.uiSaisieNotes = uiSaisieNotes;
		
		addValiderListener();
		
			try {
				this.uiSaisieNotes.loadData();
				this.uiSaisieNotes.resetFormUI();
				this.uiSaisieNotes.displayNotification("Done successfully !");

			} catch (WSException | JAXBException | IOException |  NullPointerException e) {
				uiSaisieNotes.displayErrorMessage(e.getMessage());

			}
				
	}
	
	@Override
	public void addValiderListener() {
		uiSaisieNotes.addValiderListener( new ActionListener() {
			
			
			@Override
			public void actionPerformed(ActionEvent e) {
				notes=uiSaisieNotes.getNotes();
				
				try {	
					
					if(notes.get(0).getId()==0)
					{
						for(int i=0;i<notes.size();i++)
						  (notes.get(i)).create ();
					}
					else
					{
						for(int i=0;i<notes.size();i++)
							  (notes.get(i)).update();
					}
					
					uiSaisieNotes.resetFormUI();
					
					uiSaisieNotes.displayNotification("Done successfully !");
				} catch (WSException | JAXBException | IOException exception) {
					uiSaisieNotes.displayErrorMessage(exception.getMessage());
				}
			}
		});
	}
	
	public void run() {
		uiSaisieNotes.showMe();
	}
}
