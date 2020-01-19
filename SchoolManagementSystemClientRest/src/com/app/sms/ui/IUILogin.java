package com.app.sms.ui;

import java.awt.event.ActionListener;

public interface IUILogin {
	
	/**
	 * return type : void 
	 * @param errorMessage
	 * method : displayErrorMessage
	 * 
	 */
	public void displayErrorMessage(String errorMessage) ;
	
	/**
	 * return type : void 
	 * @param message
	 * method : displayNotification
	 * 
	 */
	public void displayNotification(String message) ;

	/**
	 * return type : void 
	 * @param listenForAnnulerButton
	 * method : addValiderListener
	 * 
	 */
	public void addValiderListener(ActionListener listenForAnnulerButton) ;

	/**
	 * return type : void 
	 * @param status
	 * method : setEnableUIButton
	 * 
	 */
	void setEnabledUIButton(boolean status);
	
	/**
	 * return type : String 
	 * @return
	 * method : getUserProfil
	 * 
	 */
	public String getUserProfil() ;

	/**
	 * return type : String 
	 * @return
	 * method : getLogin
	 * 
	 */
	public String getLogin() ;

	/**
	 * return type : String 
	 * @return
	 * method : getPassword
	 * 
	 */
	public String getPassword() ;
}
