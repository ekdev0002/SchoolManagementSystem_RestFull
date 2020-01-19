package com.app.sms.ui.gestionnaires;

import java.awt.event.ActionListener;

import com.app.sms.exceptions.BadFormatDataException;

public interface IUINouveauModule {
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
	 * @return
	 * @throws BadFormatDataException
	 */
	public int getCoefficient() throws BadFormatDataException;
	/**
	 * 
	 */
	void resetUI();
}
