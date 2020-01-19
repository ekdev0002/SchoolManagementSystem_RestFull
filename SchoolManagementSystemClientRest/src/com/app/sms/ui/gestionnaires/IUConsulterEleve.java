package com.app.sms.ui.gestionnaires;

import java.awt.event.ActionListener;
import java.util.List;

import com.app.sms.models.Eleve;

public interface IUConsulterEleve {
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
	 * @param data
	 */
	void updateUIForm(String[] data);

	/**
	 * @return
	 */
	String getPicturePath();

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
	String getLibelleClasse();

	/**
	 * 
	 */
	void resetFormUI();

	/**
	 * @param eleves
	 */
	void loadData(List<Eleve> eleves);
}
