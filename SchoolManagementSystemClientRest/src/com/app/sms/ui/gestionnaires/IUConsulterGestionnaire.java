package com.app.sms.ui.gestionnaires;

import java.awt.event.ActionListener;
import java.util.List;

import com.app.sms.models.Gestionnaire;

public interface IUConsulterGestionnaire {
	/**
	 * @param listenerForUpdateButton
	 */
	void addUpdateListener(ActionListener listenerForUpdateButton);
	/**
	 * @param listenerForDeleteButton
	 */
	void addDeleteListener(ActionListener listenerForDeleteButton);
	/**
	 * @param listenerForGoButton
	 */
	void addGoListener(ActionListener listenerForGoButton);
	/**
	 * @return
	 */
	String getTypeRecherche();
	/**
	 * @return
	 */
	String getCritere();
	/**
	 * @return
	 */
	String getId();
	/**
	 * 
	 */
	void resetUI();
	/**
	 * @return
	 */
	String getDateNaissance();
	/**
	 * @return
	 */
	String getPassword();
	/**
	 * @return
	 */
	String getLogin();
	/**
	 * @return
	 */
	
	String getTelephone();
	/**
	 * @return
	 */
	String getPrenom();
	/**
	 * @return
	 */
	String getAdresse();
	/**
	 * @return
	 */
	String getNom();
	/**
	 * @return
	 */
	String getEmail();
	/**
	 * @return
	 */
	void resetFormUI();
	/**
	 * @param enseignants
	 */
	void loadData(List<Gestionnaire> gestionnaires);
}
