package com.app.sms.ui.eleves.controllers.impl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.io.IOException;
import java.util.List;

import javax.swing.JTextPane;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import javax.xml.bind.JAXBException;

import com.app.sms.models.Bulletin;
import com.app.sms.models.BulletinHtmlModel;
import com.app.sms.ui.eleves.controllers.IUIConsulterBulletinController;
import com.app.sms.ui.eleves.impl.UIConsulterBulletin;
import com.app.sms.ui.impl.MainUIApplication;

public class UIConsulterBulletinController implements IUIConsulterBulletinController {
	private UIConsulterBulletin uIConsulterBulletin;
	private List<Bulletin> bulletins;
	
	public UIConsulterBulletinController(UIConsulterBulletin uIConsulterBulletin) {
		this.uIConsulterBulletin = uIConsulterBulletin;
		
			try {
			 bulletins = Bulletin.list(MainUIApplication.getCurrentUser().getId());
			this.uIConsulterBulletin.loadData(bulletins);
			this.uIConsulterBulletin.displayNotification("Done successfully !");
		} catch ( JAXBException | IOException exception) {
			this.uIConsulterBulletin.displayErrorMessage(exception.getMessage());
		}
			addImprimerListener();
			addVisualiserListener();

	}
	
	public void run() {
		uIConsulterBulletin.showMe();
	}
	

	@Override
	public void addVisualiserListener() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addImprimerListener() {
		// TODO Auto-generated method stub
		uIConsulterBulletin.addImprimerListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				imprimer();
			}
		});

	}
	
	public void imprimer() {
		   JTextPane jtp = new JTextPane();
		    jtp.setContentType("text/html");
		    BulletinHtmlModel bulletinHtmlModel = new BulletinHtmlModel(this.bulletins.get(uIConsulterBulletin.getSeletedIndexbulletin()));
		    
		    HTMLEditorKit kit = new HTMLEditorKit();
			   StyleSheet styleSheet = kit.getStyleSheet();
			   
			   styleSheet.addRule("body{    border: 3px black outset;    box-shadow: 6px 6px 6px black;}tr:nth-child(even) {background-color: #f2f2f2;}th, td {  padding: 15px;  text-align: left;}table {  width: 90%;   margin: 0px auto;  box-shadow: 6px 6px 6px black;}th {  height: 50px;}");
			   kit.setStyleSheet(styleSheet);  			   
			   
			jtp.setEditorKit(kit);
		    jtp.setText(bulletinHtmlModel.getBulletinString());
		    try {
				jtp.print();
				uIConsulterBulletin.displayNotification("Done successfully !");
			} catch (PrinterException e) {
				// TODO Auto-generated catch block
				uIConsulterBulletin.displayErrorMessage(e.getMessage());
			}	   

	}
}