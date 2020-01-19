package com.app.sms.ui.enseignants;


import java.awt.event.ActionListener;
import java.util.List;

import com.app.sms.models.Candidature;

public interface IUIConsulterCandidature {
	/**
	 * @param listenerForUpdateButton
	 */
	void addUpdateListener(ActionListener listenerForUpdateButton);

	/**
	 * @param listenerForDeleteButton
	 */
	void addDeleteListener(ActionListener listenerForDeleteButton);

	
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
	 * 
	 */
	void resetFormUI();

	/**
	 * @param data
	 */
	void updateUIForm(String[] data);

	/**
	 * @return
	 */
	String getPath();

	/**
	 * @return
	 */
	String getNom();

	/**
	 * @return
	 */
	String getPrenom();

	/**
	 * @return
	 */
	String getTelephone();

	/**
	 * @return
	 */
	String getEmail();

	/**
	 * @return
	 */
	String getLogin();

	/**
	 * @return
	 */
	String getGenre();

	/**
	 * @param candidatures
	 */
	
	String getState();
	
	void loadData(List<Candidature> candidatures);
}
