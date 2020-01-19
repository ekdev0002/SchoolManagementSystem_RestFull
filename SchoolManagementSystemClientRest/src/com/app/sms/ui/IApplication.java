package com.app.sms.ui;

import java.awt.event.ActionListener;

import javax.swing.JPanel;

public interface IApplication {
	/**
	 * @param listenerForPlanningCoursComponent
	 */
	public void addPlanningDevoirComponentsListener (ActionListener listenerForPlanningCoursComponent);
	/**
	 * @param listenerForPlanningDevoirComponent
	 */
	public void addPlanningCoursComponentsListener (ActionListener listenerForPlanningDevoirComponent);
	/**
	 * @param listenerForImpressionBulletinComponent
	 */
	public void addImpressionBulletinComponentsListener (ActionListener listenerForImpressionBulletinComponent);
	/**
	 * @param listenerForBatchProcessComponent
	 */
	public void addBatchProcessComponentsListener (ActionListener listenerForBatchProcessComponent);
	/**
	 * @param listenerForConsulterCandidatureComponent
	 */
	public void addConsulterCandidatureComponentsListener(ActionListener listenerForConsulterCandidatureComponent);
	/**
	 * @param listenerForConsulterModuleComponent
	 */
	public void addConsulterModuleComponentsListener(ActionListener listenerForConsulterModuleComponent);
	/**
	 * @param listenerForConsulterClasseComponent
	 */
	public void addConsulterClasseComponentsListener(ActionListener listenerForConsulterClasseComponent);
	/**
	 * @param listenerForConsulterEleveComponent
	 */
	public void addConsulterEleveComponentsListener(ActionListener listenerForConsulterEleveComponent);
	/**
	 * @param listenerForConsulterEnseignantComponent
	 */
	public void addConsulterEnseignantComponentsListener(ActionListener listenerForConsulterEnseignantComponent);
	/**
	 * @param listenerForConsulterGestionnaireComponent
	 */
	public void addConsulterGestionnaireComponentsListener(ActionListener listenerForConsulterGestionnaireComponent);
	/**
	 * @param listenerForNouvelleCandidatureComponent
	 */
	public void addNouvelleCandidatureComponentsListener(ActionListener listenerForNouvelleCandidatureComponent);
	/**
	 * @param listenerForNouveauModuleComponent
	 */
	public void addNouveauModuleComponentsListener(ActionListener listenerForNouveauModuleComponent);
	/**
	 * @param listenerForNouvelleClasseComponent
	 */
	public void addNouvelleClasseComponentsListener(ActionListener listenerForNouvelleClasseComponent);
	/**
	 * @param listenerForNouvelEleveComponent
	 */
	public void addNouvelEleveComponentsListener(ActionListener listenerForNouvelEleveComponent);
	/**
	 * @param listenerForNouvelEnseignantComponent
	 */
	public void addNouvelEnseignantComponentsListener(ActionListener listenerForNouvelEnseignantComponent);
	/**
	 * @param listenerForNouveauGestionnaireComponent
	 */
	public void addNouveauGestionnaireComponentsListener(ActionListener listenerForNouveauGestionnaireComponent);
	/**
	 * 
	 */
	
	public void addConsulterCandidatureComponentsListener(JPanel panel);
	
	void activateGestionnaireComponent();
	/**
	 * 
	 */
	void activateEnseignantComponent();
	/**
	 * 
	 */
	void activateEleveComponent();
}
