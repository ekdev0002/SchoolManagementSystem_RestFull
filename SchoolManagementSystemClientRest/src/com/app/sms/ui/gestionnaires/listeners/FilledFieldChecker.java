package com.app.sms.ui.gestionnaires.listeners;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class FilledFieldChecker implements DocumentListener {

	private JButton button;
	private boolean state;
	private ArrayList<JTextField> textFields;
	/**
	 * @param validerButton
	 * @param textFields
	 */
	public FilledFieldChecker (JButton button, ArrayList<JTextField> textFields) {
		this.button = button;
		this.textFields = textFields;
	}

	@Override
	public void changedUpdate(DocumentEvent arg0) {}

	@Override
	public void insertUpdate(DocumentEvent arg0) {
		checkTextFields();
	}

	@Override
	public void removeUpdate(DocumentEvent arg0) {
		checkTextFields();
	}
	
	private void checkTextFields() {
		state = true;
		
		for ( JTextField textField : textFields ) {
			if ( textField.getText().isEmpty() ) {
				state = false;
				break;
			}
		}
		button.setEnabled(state);
	}
}
