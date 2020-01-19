package com.app.sms.ui.enseignants.impl;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.xml.bind.JAXBException;

import com.app.sms.exceptions.WSException;
import com.app.sms.models.Candidature;
import com.app.sms.models.CandidatureModel;
import com.app.sms.models.Diplome;
import com.app.sms.models.enums.State;
import com.app.sms.ui.enseignants.IUIConsulterCandidature;
import com.app.sms.ui.gestionnaires.impl.UIConsulterEnseignant;
import com.app.sms.ui.gestionnaires.listeners.FilledFieldChecker;
import com.app.sms.ui.impl.AbstractUIOperation;
import com.app.sms.utils.Utilitaire;

public class UIConsulterCandidature extends AbstractUIOperation implements IUIConsulterCandidature {
	
	private static final URL DEFAULT_URL_PICTURE = UIConsulterEnseignant.class.getResource("/images/inconnu.png");
	private JTable listeCandidature;
	private JComboBox<String> status;
	private JButton updateButton;
	private JLabel picture;
	private String path;
	private JTextField genre;
	private JLabel commentaires;
	private CandidatureModel candidatureModel;
	
	private JTextField affectedTo;
	protected String[] data;
	private JTextField textField;
	
	private List<Candidature> candidatures;
	private JList<String> listeDiplomes;

	public UIConsulterCandidature() {

		setMinimumSize(new Dimension(834, 590));
		setSize(new Dimension(834, 590));
		setPreferredSize(new Dimension(834, 590));
		operationIcon.setIcon(new ImageIcon(UIConsulterCandidature.class.getResource("/images/images (1).jpg")));

		operationLabel.setText("Candidatures");
		
		JPanel mainPanel = new JPanel();
		mainPanel.setPreferredSize(new Dimension(8, 8));
		centerPanel.add(mainPanel, BorderLayout.CENTER);
		mainPanel.setLayout(new GridLayout(2, 1, 0, 0));
		
		JPanel listeCandidaturePanel = new JPanel();
		listeCandidaturePanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		mainPanel.add(listeCandidaturePanel);
		
		JScrollPane listeScrollPane = new JScrollPane();
		GroupLayout listeCandidaturePanelLayout = new GroupLayout(listeCandidaturePanel);
		listeCandidaturePanelLayout.setHorizontalGroup(
			listeCandidaturePanelLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(listeCandidaturePanelLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(listeScrollPane, GroupLayout.DEFAULT_SIZE, 774, Short.MAX_VALUE)
					.addContainerGap())
		);
		listeCandidaturePanelLayout.setVerticalGroup(
			listeCandidaturePanelLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(listeCandidaturePanelLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(listeScrollPane, GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		listeCandidature = new JTable();
		candidatureModel = new CandidatureModel();
		listeCandidature.setModel(candidatureModel);
		
		listeScrollPane.setViewportView(listeCandidature);
		listeCandidaturePanel.setLayout(listeCandidaturePanelLayout);
		listeCandidature.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				int selectedRow = listeCandidature.getSelectedRow();
				if ( selectedRow >= 0 ) {
					 data = getCandidatureDataByRow(selectedRow);
					updateUIForm (selectedRow);
				}
			}
		});
		
		JPanel visuliserCandidaturePanel = new JPanel();
		visuliserCandidaturePanel.setBorder(new TitledBorder(null, "Modifier un \u00E9l\u00E8ve", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		mainPanel.add(visuliserCandidaturePanel);
		
		JPanel formPanel = new JPanel();
		
		JPanel buttonPanelLeft = new JPanel();
		buttonPanelLeft.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "...", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		
		updateButton = new JButton("Update");
		updateButton.setEnabled(false);
		updateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		updateButton.setBounds(80, 16, 80, 20);
		updateButton.setToolTipText("Valider la modification des informations de l'élève");
		updateButton.setMaximumSize(new Dimension(80, 20));
		updateButton.setMinimumSize(new Dimension(80, 20));
		updateButton.setPreferredSize(new Dimension(80, 20));
		JButton annulerButton = new JButton("Quitter");
		annulerButton.setBounds(232, 16, 80, 20);
		annulerButton.setToolTipText("Quittez ...");
		annulerButton.setMaximumSize(new Dimension(91, 20));
		annulerButton.setMinimumSize(new Dimension(91, 20));
		annulerButton.setPreferredSize(new Dimension(80, 20));
		annulerButton.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				exitWithConfirmation();
			}
		});
		buttonPanelLeft.setLayout(null);
		buttonPanelLeft.add(updateButton);
		buttonPanelLeft.add(annulerButton);
		
		JPanel notificationPanel = new JPanel();
		notificationPanel.setBorder(new TitledBorder(null, "Notification", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		notificationPanel.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		notificationPanel.add(scrollPane, BorderLayout.CENTER);
		
		notification = new JTextArea();
		notification.setEditable(false);
		notification.setFont(new Font("Tahoma", Font.PLAIN, 11));
		scrollPane.setViewportView(notification);
		GroupLayout visuliserCandidaturePanelLayout = new GroupLayout(visuliserCandidaturePanel);
		visuliserCandidaturePanelLayout.setHorizontalGroup(
			visuliserCandidaturePanelLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(visuliserCandidaturePanelLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(visuliserCandidaturePanelLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(visuliserCandidaturePanelLayout.createSequentialGroup()
							.addComponent(buttonPanelLeft, GroupLayout.PREFERRED_SIZE, 398, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(notificationPanel, GroupLayout.PREFERRED_SIZE, 396, GroupLayout.PREFERRED_SIZE))
						.addComponent(formPanel, GroupLayout.DEFAULT_SIZE, 796, Short.MAX_VALUE))
					.addContainerGap())
		);
		visuliserCandidaturePanelLayout.setVerticalGroup(
			visuliserCandidaturePanelLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(visuliserCandidaturePanelLayout.createSequentialGroup()
					.addComponent(formPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(visuliserCandidaturePanelLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(buttonPanelLeft, GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
						.addComponent(notificationPanel, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		formPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel buttonPanel = new JPanel();
		formPanel.add(buttonPanel, BorderLayout.SOUTH);
		buttonPanel.setLayout(new GridLayout(1, 2, 0, 0));
		
		JPanel infoCandidaturePanel = new JPanel();
		formPanel.add(infoCandidaturePanel, BorderLayout.CENTER);
		infoCandidaturePanel.setLayout(new GridLayout(1, 2, 0, 0));
		
		JPanel otherPanel = new JPanel();
		otherPanel.setMinimumSize(new Dimension(8, 8));
		infoCandidaturePanel.add(otherPanel);
		otherPanel.setLayout(new GridLayout(4, 1, 0, 0));
		
		JPanel loginPanel = new JPanel();
		otherPanel.add(loginPanel);
		loginPanel.setLayout(new GridLayout(0, 2, -150, 0));
		
		JLabel statusDossierLabel = new JLabel("Status :");
		loginPanel.add(statusDossierLabel);
	
		status = new JComboBox<>();
		
		loginPanel.add(status);
		
		JPanel affectationPanel = new JPanel();
		otherPanel.add(affectationPanel);
		affectationPanel.setLayout(new GridLayout(0, 2, -150, 0));
		
		JLabel affectedToLabel = new JLabel("Affecté à :");
		affectationPanel.add(affectedToLabel);
		
		affectedTo = new JTextField();
		affectationPanel.add(affectedTo);
		affectedTo.setEnabled(false);
		affectedTo.setEditable(false);
		affectedTo.setColumns(22);
		
		JPanel genrePanel = new JPanel();
		otherPanel.add(genrePanel);
		genrePanel.setLayout(new GridLayout(0, 2, -150, 0));
		
		JLabel genreLabel = new JLabel("Genre :");
		genrePanel.add(genreLabel);
		
		genre = new JTextField();
		genre.setEditable(false);
		genre.setEnabled(false);
		genrePanel.add(genre);
		genre.setColumns(10);
		
		JPanel commentairesPanel = new JPanel();
		otherPanel.add(commentairesPanel);
		commentairesPanel.setLayout(new BorderLayout(0, 0));
		
		commentaires = new JLabel("Ajouter commentaire");
		commentairesPanel.add(commentaires, BorderLayout.WEST);
		commentaires.setForeground(Color.RED);
		commentaires.setFont(new Font("Tahoma", Font.BOLD, 10));
		
		textField = new JTextField();
		commentairesPanel.add(textField);
		textField.setColumns(10);
		
		JPanel diplomePanel = new JPanel();
		infoCandidaturePanel.add(diplomePanel);
		diplomePanel.setLayout(new BorderLayout(0, 0));
		
		
		JLabel listeDiplomeLabel = new JLabel("  Diplomes : ");
		listeDiplomeLabel.setBackground(new Color(255, 255, 255));
		diplomePanel.add(listeDiplomeLabel, BorderLayout.WEST);
		
		JPanel selectPanel = new JPanel();
		selectPanel.setBackground(Color.WHITE);
		diplomePanel.add(selectPanel, BorderLayout.CENTER);
		
		
	//	selectPanel.setLayout(selectPanelLayout);
		
		JScrollPane scrollPaneListeDiplome = new JScrollPane();
		scrollPaneListeDiplome.setPreferredSize(new Dimension(70, 70));
		scrollPaneListeDiplome.setSize(new Dimension(70, 15));
		scrollPaneListeDiplome.setMaximumSize(new Dimension(70, 15));
		scrollPaneListeDiplome.setMinimumSize(new Dimension(70, 15));
		diplomePanel.add(scrollPaneListeDiplome, BorderLayout.CENTER);

		
		listeDiplomes = new JList<String>();
		listeDiplomes.setVisibleRowCount(4);
		scrollPaneListeDiplome.setViewportView(listeDiplomes);

				
		
		JPanel picturePanel = new JPanel();
		picturePanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		infoCandidaturePanel.add(picturePanel);
		picturePanel.setLayout(null);
		
		picture = new JLabel("");		
		picture.setBounds(10, 6, 88, 88);
		picturePanel.add(picture);
		setDefaultPicture ();
		visuliserCandidaturePanel.setLayout(visuliserCandidaturePanelLayout);
		
		/*
		 * Contrôle de la saisie du champ Libelle et Coéfficient avant activation du bouton de validation 
		 * 
		 * */	
		ArrayList<JTextField> textFields = new ArrayList<>();
		FilledFieldChecker updateButtonChecker = new FilledFieldChecker(updateButton, textFields);
		

		
	}
	
	
	public void updateUIForm(int row) {
		updateButton.setEnabled(true);
		
		try {
			this.setEnseignant(candidatures.get(row).getEnseignant());
			
			if((candidatures.get(row).getState()).equals(State.CLOSE.toString()))
			{
				status.addItem(candidatures.get(row).getState());
				status.setEnabled(false);
				textField.setEnabled(false);
			}
			else {
				status.setEnabled(true);
				status.removeAllItems();

				
				
				status.addItem(State.REJETEE.toString());
				status.addItem(State.RETENUE.toString());
				status.setSelectedItem(candidatures.get(row).getState());
				
				
				if ((candidatures.get(row).getCommentaires()) == null) this.commentaires.setToolTipText("Aucun commentaire pour l'instant sur ce dossier ...");
				else this.commentaires.setToolTipText(data[7]);
				this.setPath(candidatures.get(row).getPath());
				if (candidatures.get(row).getPath() == null) setDefaultPicture();
				else {
					ImageIcon imageIcon = Utilitaire.getImageIcon (this, path);
					setPicture(imageIcon);
				}
		
				clearListeDiplome();
				
				if(listeDiplomes.getModel().getSize()==0)
				{
					List<Diplome> lisDiplome = candidatures.get(row).getDiplomeList().getDiplomeList();
					DefaultListModel<String> model = new DefaultListModel<>();
					System.out.println("count ="+lisDiplome.size());
					for (Diplome diplome : lisDiplome) {				
						model.addElement( diplome.getId() + Utilitaire.SEPARATEUR + diplome.getUrl());
						System.out.println(diplome.getId() + Utilitaire.SEPARATEUR + diplome.getUrl());
					}
					listeDiplomes.setModel(model);
				}
				
			}						

		} catch ( NullPointerException | WSException | IOException | JAXBException e) {
			displayErrorMessage(e.getMessage());
		}
	}
	
	private void setEnseignant(String string) {
		
		affectedTo.setText(string);
		
	}

	@Override
	public String getPath () {
		return path;
	}

	@Override
	public String getId() {
		return data[0];
	}
	
	@Override
	public void addUpdateListener (ActionListener listenerForUpdateButton) {
		updateButton.addActionListener(listenerForUpdateButton);
	}
	
	@Override
	public void addDeleteListener (ActionListener listenerForDeleteButton) {
		
	}
	
		
	@Override
	public String getTypeRecherche() {
		return "";
	}
	
	@Override
	public String getCritere() {
		return "";
	}
	
	@Override
	public String getNom() {
		return "";
	}
	
	@Override
	public String getPrenom() {
		return "";
	}

	private void setPrenom(String prenom) {
		
	}
	
	@Override
	public String getTelephone() {
		return "";
	}
	
	@Override
	public String getEmail() {
		return "";
	}
	
	@Override
	public String getLogin() {
		return "";
	}
	
	@Override
	public String getGenre() {
		return genre.getText();
	}
	
	@Override
	public void resetFormUI() {
		
	}
	
	@Override
	public void loadData(List<Candidature> candidatures) {
		try {
			candidatureModel.loadData(candidatures);
		} catch (WSException | IOException | JAXBException e) {
			// TODO Auto-generated catch block
			displayErrorMessage(e.getMessage());
		}
		this.candidatures=candidatures;
	}
	
	@Override
	public String getState() {
		return status.getSelectedItem().toString();
	}

	private String[] getCandidatureDataByRow(int row) {
		
		String [] values = new String [candidatureModel.getColumnCount()];
		for ( int i = 0 ; i < candidatureModel.getColumnCount() ; i ++ ) {
			values [i] = (String) candidatureModel.getValueAt(row, i);
		}
		return values;
	}
	
	private void setPicture(ImageIcon imageIcon) {
		picture.setIcon(imageIcon);
	}
	
	private void setPath(String path) {
		path = path;
	}
		
	private void setDefaultPicture () {
		picture.setIcon(new ImageIcon(DEFAULT_URL_PICTURE));
	}


	@Override
	public void updateUIForm(String[] data) {
		// TODO Auto-generated method stub
		
	}
	private void clearListeDiplome() {
		DefaultListModel<String> model = new DefaultListModel<>();
		listeDiplomes.setModel(model);
	}


	public String getCommentaire() {
		// TODO Auto-generated method stub
		return textField.getText();
	}

}
