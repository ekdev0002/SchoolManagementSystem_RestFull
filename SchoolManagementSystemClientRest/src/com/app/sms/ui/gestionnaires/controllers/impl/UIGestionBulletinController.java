package com.app.sms.ui.gestionnaires.controllers.impl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.io.IOException;
import java.util.List;

import javax.swing.JTextPane;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import javax.xml.bind.JAXBException;

import com.app.sms.exceptions.WSException;
import com.app.sms.models.Bulletin;
import com.app.sms.models.BulletinHtmlModel;
import com.app.sms.ui.gestionnaires.controllers.IUIGestionBulletinController;
import com.app.sms.ui.gestionnaires.impl.UIGestionBulletin;

public class UIGestionBulletinController implements IUIGestionBulletinController {
	private UIGestionBulletin uIGestionBulletin;
	private List<Bulletin> bulletins;
	
	public UIGestionBulletinController(UIGestionBulletin uIGestionBulletin) {
		this.uIGestionBulletin = uIGestionBulletin;
		
		
		
			try {
			bulletins = Bulletin.list();
			this.uIGestionBulletin.loadData(bulletins);
			this.uIGestionBulletin.displayNotification("Done successfully !");
		} catch ( WSException | IOException | JAXBException exception) {
			this.uIGestionBulletin.displayErrorMessage(exception.getMessage());
		}
			addGenererListener();
			addImprimerListener();
			addVisualiserListener();

	}
	
	public void run() {
		uIGestionBulletin.showMe();
	}

	@Override
	public void addGenererListener() {
		// TODO Auto-generated method stub
		uIGestionBulletin.addGenererListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					Bulletin.create(uIGestionBulletin.getSemestre());

					bulletins = Bulletin.list();
					uIGestionBulletin.loadData(bulletins);					
					uIGestionBulletin.displayNotification("Done successfully !");
				} catch ( WSException | IOException | JAXBException e1) {
					// TODO Auto-generated catch block
					uIGestionBulletin.displayErrorMessage(e1.getMessage());
				}
				
			}
			
		});
		
	}

	@Override
	public void addVisualiserListener() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addImprimerListener() {
		// TODO Auto-generated method stub
		uIGestionBulletin.addImprimerListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				imprimer();
			}
		});

	}
	
	public void imprimer() {
		   JTextPane jtp = new JTextPane();
		    jtp.setContentType("text/html");
		    BulletinHtmlModel bulletinHtmlModel = new BulletinHtmlModel(this.bulletins.get(uIGestionBulletin.getSeletedIndexbulletin()));
		    
		    HTMLEditorKit kit = new HTMLEditorKit();
			   StyleSheet styleSheet = kit.getStyleSheet();
			   
			   styleSheet.addRule("body{    border: 3px black outset;    box-shadow: 6px 6px 6px black;}tr:nth-child(even) {background-color: #f2f2f2;}th, td {  padding: 15px;  text-align: left;}table {  width: 90%;   margin: 0px auto;  box-shadow: 6px 6px 6px black;}th {  height: 50px;}");
			   kit.setStyleSheet(styleSheet);  			   
			   
			jtp.setEditorKit(kit);
		    jtp.setText(bulletinHtmlModel.getBulletinString());
		    try {
				jtp.print();
				uIGestionBulletin.displayNotification("Done successfully !");
			} catch (PrinterException e) {
				// TODO Auto-generated catch block
				uIGestionBulletin.displayErrorMessage(e.getMessage());
			}	   

	}
}