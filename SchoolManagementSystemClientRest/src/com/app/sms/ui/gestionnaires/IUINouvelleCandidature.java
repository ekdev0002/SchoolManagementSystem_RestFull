package com.app.sms.ui.gestionnaires;

import java.awt.event.ActionListener;
import java.util.List;

import com.app.sms.exceptions.BadFormatDataException;
import com.app.sms.exceptions.DateOutOfBoundsException;
import com.app.sms.exceptions.NotAvailableDateFormatException;

public interface IUINouvelleCandidature {

	/**
	 * @param listenForSubmitButton
	 */
	public void addSubmitListener(ActionListener listenForSubmitButton);
	/**
	 * @return
	 */
	public String getTelephone();
	/**
	 * @return
	 */
	public String getEmail();
	/**
	 * @return
	 */
	public String getPrenom();
	/**
	 * @return
	 */
	public String getNom();
	/**
	 * @return
	 */
	public String getId();
	/**
	 * @param listenerForSelectPictureButton
	 */
	public void addSelectPictureListener(ActionListener listenerForSelectPictureButton);
	/**
	 * @return
	 */
	public String getGenre();
	/**
	 * @return
	 */
	public int getSelectedEnseignantId();
	/**
	 * @return
	 */
	public List<String> getDiplomes();
	/**
	 * @return
	 */
	String getCVUrl();
	/**
	 * @return
	 * @throws BadFormatDataException
	 * @throws NumberFormatException
	 * @throws DateOutOfBoundsException
	 * @throws NotAvailableDateFormatException
	 */
	String getBirthday() throws BadFormatDataException, NumberFormatException, DateOutOfBoundsException, NotAvailableDateFormatException;
	/**
	 * @return
	 */
	String getPath();
	
	/**
	 * 
	 */
	void resetUI();
	/**
	 * @param listenForAnalysableCheckBox
	 */
	void addAnalysableListener(ActionListener listenForAnalysableCheckBox);
}
