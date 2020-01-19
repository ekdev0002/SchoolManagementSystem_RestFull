package com.app.sms.ui.gestionnaires;

import java.awt.event.ActionListener;
import java.util.List;

import com.app.sms.models.Enseignant;

public interface IUConsulterEnseignant {
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
	String getGenre();
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
	String getEmail();
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
	String getNom();
	/**
	 * @return
	 */
	String getPath();
	/**
	 * 
	 */
	void resetFormUI();
	/**
	 * @param enseignants
	 */
	void loadData(List<Enseignant> enseignants);
}
