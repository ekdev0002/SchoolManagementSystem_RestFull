package com.app.sms.ui.gestionnaires.listeners;

import javax.swing.JButton;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class ValiderListener implements DocumentListener {

	private JButton validerButton;
	private JTextField login;
	private JPasswordField password;
	private boolean state;
	/**
	 * @param validerButton
	 * @param textFields
	 */
	public ValiderListener (JButton validerButton, JTextField login, JPasswordField password) {
		this.validerButton = validerButton;
		this.login = login;
		this.password = password;
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
		
		if ( login.getText().isEmpty() ) {
			state = false;
		} else if (String.valueOf(password.getPassword()).isEmpty()) {
			state = false;
		}
		
		validerButton.setEnabled(state);
	}
}
