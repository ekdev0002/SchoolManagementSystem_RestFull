package com.app.sms.utils;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.app.sms.clientws.ClasseWS;
import com.app.sms.clientws.DiplomeWS;
import com.app.sms.exceptions.AlreadyExistDataException;
import com.app.sms.exceptions.WSException;
import com.app.sms.models.Classe;
import com.app.sms.models.Diplome;

public class Utilitaire {
	
	public static final String SEPARATEUR = "::";
	public static final int MAX_SIZE_FILENAME = 44;
	
	public static final String DEFAULT_MAINTENANCE_MESSAGE = "Process in maintenance.\nPlease Try Later ...";
	
	// Durée d'attente davant suppression de la notification en ms ...
	public static final long DEFAULT_WAITING_TIME = 15000;

	
	public static String getWebServiceUrl() {
		
		String url= "";
		try {
			FileReader lecture = new FileReader ("urlWebService.txt");
			BufferedReader reader= new BufferedReader(lecture);
			url= reader.readLine();
				
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return url;

	}
	
	public static void showMessage(Component component, String message) {
		JOptionPane.showMessageDialog ( component, message,"Information", JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static void setLookAndFeel (Component component) { 
		
		try {
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		} catch (Exception e) {
		    // If Nimbus is not available, you can set the GUI to another look and feel.
		}
				
	}
	
	public static void center (Component component, Dimension dimension) {
		component.setSize(dimension);

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = component.getSize();

		if (frameSize.height > screenSize.height)
			frameSize.height = screenSize.height;

		if (frameSize.width > screenSize.width)
			frameSize.width = screenSize.width;

		component.setLocation((screenSize.width - frameSize.width) / 2,
				(screenSize.height - frameSize.height) / 2);
	}
		

	public static int showConfirmationMessage(String message) {
		int response = JOptionPane.showConfirmDialog ( null, message,
	             "Confirmation", JOptionPane.YES_NO_OPTION);
		
		return response;
	}
	
	public static void displayDefaultMaintenanceMessage () {
		showMessage(null, Utilitaire.DEFAULT_MAINTENANCE_MESSAGE);
	}
	

	public static ImageIcon getImageIcon(Component component, String picturePath) {
		return new ImageIcon (component.getToolkit().createImage(picturePath)
                .getScaledInstance(88, 88, Image.SCALE_SMOOTH));
	}
	
	public static List<Classe> rechercherClasse() throws WSException, JAXBException, IOException {
		ClasseWS classeWS = new ClasseWS ();
		return classeWS.list() ;
	}
	
	public static ArrayList<String> getListeClasse () throws WSException, JAXBException, IOException {
		List<Classe> classes = rechercherClasse();
		ArrayList<String> libelles = new  ArrayList<>() ;
		
		for ( Classe classe : classes ) {
			libelles.add(classe.getId() + SEPARATEUR + classe.getLibelle()); 
		}		
		return libelles;
	}
	
	public static int findIdClasseByLibelle(String libelleClasse) throws JAXBException , IOException, WSException {
		ClasseWS classeWS = new ClasseWS ();
		return classeWS.findIdClasseByLibelle (libelleClasse);
	}
	
	public static void ajouterDiplome(Diplome diplome) throws AlreadyExistDataException, WSException, JAXBException, IOException {
		DiplomeWS diplomeWS = new DiplomeWS ();
			diplomeWS.create(diplome) ;	
	}
	
	static class JAXB {
		private static Marshaller marshaller;
		private static Unmarshaller unMarshaller;
		private static JAXBContext context;
		
		public static void init (Class<?> classe) throws JAXBException {
			context = JAXBContext.newInstance(classe);			
		}
		
		public static <T> void marshal (T items, String xmlFileName) throws FileNotFoundException, JAXBException {
			// Write to output File
			marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			FileOutputStream file = new FileOutputStream(xmlFileName); 
			marshaller.marshal(items, file);
		}
		
		public static <T> T unmarshal(String xmlFileName) throws JAXBException, FileNotFoundException {
			// Write to output File
			unMarshaller = context.createUnmarshaller();
			@SuppressWarnings("unchecked")
			T items = (T) unMarshaller.unmarshal(new FileReader (xmlFileName));
			return items;
		}
	}
}
