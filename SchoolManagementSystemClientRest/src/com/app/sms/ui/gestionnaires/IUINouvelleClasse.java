package com.app.sms.ui.gestionnaires;

import java.awt.event.ActionListener;

public interface IUINouvelleClasse {
	/**
	 * @param listenForSubmitButton
	 */
	public void addSubmitListener(ActionListener listenForSubmitButton);
	/**
	 * @return
	 */
	public String getDescription();
	/**
	 * @return
	 */
	public String getLibelle();
	/**
	 * 
	 */
	public void resetUI();
}