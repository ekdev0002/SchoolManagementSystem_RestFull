package com.app.sms.ui.gestionnaires;

import java.awt.event.ActionListener;

public interface IUINouvelEleve {

	/**
	 * @param listenForSubmitButton
	 */
	public void addSubmitListener(ActionListener listenForSubmitButton);
	/**
	 * @param listenForCreerButton
	 */
	public void addCreerListener(ActionListener listenForCreerButton);
	/**
	 * @return
	 */
	public String getPassword();
	/**
	 * @return
	 */
	public String getLogin();
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
	String getIdClasse();
	/**
	 * @return
	 */
	String getLibelleClasse();
	/**
	 * @return
	 */
	String getPicturePath();
	/**
	 * 
	 */
	void resetUI();
	/**
	 * 
	 */
	void updateListeDeroulanteClasse();
}
