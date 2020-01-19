package com.app.sms.ui.gestionnaires;

import java.awt.event.ActionListener;
import java.util.List;

import com.app.sms.models.Module;

/**
 * @author a459079
 *
 */
public interface IUConsulterModule {
	/**
	 * @param listenerForUpdateButton
	 */
	public void addUpdateListener(ActionListener listenerForUpdateButton);

	/**
	 * @param listenerForDeleteButton
	 */
	public void addDeleteListener(ActionListener listenerForDeleteButton);

	/**
	 * @param listenerForGoButton
	 */
	public void addGoListener(ActionListener listenerForGoButton);

	/**
	 * @return
	 */
	public String getTypeRecherche();

	/**
	 * @return
	 */
	public String getCritere();

	/**
	 * @return
	 */
	public String getId();

	/**
	 * @return
	 */
	public String getCoefficient();

	/**
	 * @return
	 */
	public String getLibelle();

	/**
	 * @return
	 */
	public String getDescription();

	/**
	 * 
	 */
	public void resetUI();
	/**
	 * @param data
	 */
	public void updateUIForm(String[] data);

	/**
	 * 
	 */
	public void resetFormUI();

	/**
	 * @param list
	 */
	void loadData(List<Module> list);
}
