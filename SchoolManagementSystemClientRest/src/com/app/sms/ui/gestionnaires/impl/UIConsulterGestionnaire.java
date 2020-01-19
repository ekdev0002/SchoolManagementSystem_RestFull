package com.app.sms.ui.gestionnaires.impl;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.app.sms.models.Gestionnaire;
import com.app.sms.models.GestionnaireModel;
import com.app.sms.ui.gestionnaires.IUConsulterGestionnaire;
import com.app.sms.ui.gestionnaires.listeners.FilledFieldChecker;
import com.app.sms.ui.impl.AbstractUIOperation;


public class UIConsulterGestionnaire extends AbstractUIOperation implements IUConsulterGestionnaire {
	
	private JTable listeGestionnaire;
	private JTextField id;
	private JTextField nom;
	private JTextField prenom;
	private JTextField telephone;
	private JTextField dateNaissance;
	private JTextField adresse;
	private JTextField login;
	private JPasswordField password;
	private JButton updateButton;
	private JButton deleteButton;
	
	private GestionnaireModel gestionnaireModel;
	private JTextField textEmail;
	

	public UIConsulterGestionnaire() {
		operationIcon.setIcon(new ImageIcon(UIConsulterGestionnaire.class.getResource("/images/enseignant-logo.jpg")));
		operationLabel.setText("Gestionnaires");
		
		JPanel mainPanel = new JPanel();
		centerPanel.add(mainPanel, BorderLayout.CENTER);
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		
		JPanel listeGestionnairePanel = new JPanel();
		listeGestionnairePanel.setBackground(new Color(210, 180, 140));
		listeGestionnairePanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		mainPanel.add(listeGestionnairePanel);
		
		JScrollPane listeGestionnaireScrollPane = new JScrollPane();
		listeGestionnaireScrollPane.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Liste des gestionnaires", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		
		
		
		
		
//		listeClasse = new JTable();
//		classeModel = new ClasseModel();
//		listeClasse.setModel(classeModel);
//		listeScrollPane.setViewportView(listeClasse);
//		listeClassePanel.setLayout(listeClassePanelLayout);
//		listeClasse.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
//			@Override
//			public void valueChanged(ListSelectionEvent e) {
//				int selectedRow = listeClasse.getSelectedRow();
//				if ( selectedRow >= 0 ) {
//					String [] data = getClasseDataByRow(selectedRow);
//					updateUIForm (data);
//				}
//			}
//		});

		
		
		listeGestionnaire = new JTable();
		gestionnaireModel = new GestionnaireModel();
		listeGestionnaire.setModel(gestionnaireModel);
		listeGestionnairePanel.setLayout(new GridLayout(0, 1, 0, 0));
		listeGestionnaireScrollPane.setViewportView(listeGestionnaire);
		listeGestionnairePanel.add(listeGestionnaireScrollPane);
		
		listeGestionnaire.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				try {
					ListSelectionModel selectionModel = listeGestionnaire.getSelectionModel() ;
					for ( int i = selectionModel.getMinSelectionIndex() ; i <= selectionModel.getMaxSelectionIndex() ; i ++ ) {
						if (selectionModel.isSelectedIndex(i)) {
							int gestionnaireId = Integer.parseInt(getValueListeGestionnaireAt(i, 0));
							Gestionnaire gestionnaire = Gestionnaire.getGestionnaireById(gestionnaireId);
							updateUIGestionnaire (gestionnaire);
							break;
						}
					}
				} catch ( IOException e) {
					displayErrorMessage( "Error in the model : " + e.getMessage() );
				}
			}
		});
		
		JPanel visualiserGestionnairePanel = new JPanel();
		visualiserGestionnairePanel.setBackground(SystemColor.activeCaption);
		visualiserGestionnairePanel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Modifier les donn\u00E9es du gestionnaire", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		mainPanel.add(visualiserGestionnairePanel);
		
		JPanel formPanel = new JPanel();
		
		updateButton = new JButton("Update");
		updateButton.setEnabled(false);
		updateButton.setToolTipText("Valider la modification des informations du gestionnaie");
		updateButton.setMaximumSize(new Dimension(80, 20));
		updateButton.setMinimumSize(new Dimension(80, 20));
		updateButton.setPreferredSize(new Dimension(80, 20));
		
		JButton clearButton = new JButton("Clear");
		clearButton.setToolTipText("Réinitialiser la vue");
		clearButton.setPreferredSize(new Dimension(80, 20));
		clearButton.setMinimumSize(new Dimension(91, 20));
		clearButton.setMaximumSize(new Dimension(91, 20));
		clearButton.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				resetUI();
			}
		});
		JButton annulerButton = new JButton("Annuler");
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
		
		deleteButton = new JButton("Delete");
		deleteButton.setEnabled(false);
		deleteButton.setToolTipText("Supprimer un gestionnaire");
		deleteButton.setPreferredSize(new Dimension(80, 20));
		deleteButton.setMinimumSize(new Dimension(80, 20));
		deleteButton.setMaximumSize(new Dimension(80, 20));
		GroupLayout gl_visualiserGestionnairePanel = new GroupLayout(visualiserGestionnairePanel);
		gl_visualiserGestionnairePanel.setHorizontalGroup(
			gl_visualiserGestionnairePanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_visualiserGestionnairePanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_visualiserGestionnairePanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_visualiserGestionnairePanel.createSequentialGroup()
							.addGap(451)
							.addComponent(updateButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(clearButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(annulerButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(deleteButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(formPanel, GroupLayout.DEFAULT_SIZE, 789, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_visualiserGestionnairePanel.setVerticalGroup(
			gl_visualiserGestionnairePanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_visualiserGestionnairePanel.createSequentialGroup()
					.addComponent(formPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_visualiserGestionnairePanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(annulerButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(deleteButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(clearButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(updateButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
		);
		formPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel infoGestionnairePanel = new JPanel();
		formPanel.add(infoGestionnairePanel, BorderLayout.CENTER);
		infoGestionnairePanel.setLayout(new GridLayout(1, 2, 0, 0));
		
		JPanel modelPanel = new JPanel();
		infoGestionnairePanel.add(modelPanel);
		modelPanel.setLayout(new GridLayout(8, 1, 0, 0));
		
		JPanel idPanel = new JPanel();
		idPanel.setBackground(new Color(173, 216, 230));
		FlowLayout idPanelLayout = (FlowLayout) idPanel.getLayout();
		idPanelLayout.setAlignment(FlowLayout.RIGHT);
		idPanelLayout.setVgap(0);
		modelPanel.add(idPanel);
		
		JLabel idLabel = new JLabel("id :");
		idPanel.add(idLabel);
		
		id = new JTextField("-1");
		id.setEnabled(false);
		id.setEditable(false);
		idPanel.add(id);
		id.setColumns(15);
		
		JPanel nomPanel = new JPanel();
		nomPanel.setBackground(new Color(173, 216, 230));
		FlowLayout nomPanelLayout = (FlowLayout) nomPanel.getLayout();
		nomPanelLayout.setAlignment(FlowLayout.RIGHT);
		nomPanelLayout.setVgap(0);
		modelPanel.add(nomPanel);
		
		JLabel nomLabel = new JLabel("Nom :");
		nomPanel.add(nomLabel);
		
		nom = new JTextField();
		nomPanel.add(nom);
		nom.setColumns(15);
		
		JPanel prenomPanel = new JPanel();
		prenomPanel.setBackground(new Color(173, 216, 230));
		FlowLayout prenomPanelLayout = (FlowLayout) prenomPanel.getLayout();
		prenomPanelLayout.setAlignment(FlowLayout.RIGHT);
		prenomPanelLayout.setVgap(0);
		modelPanel.add(prenomPanel);
		
		JLabel prenomLabel = new JLabel("Prénom :");
		prenomPanel.add(prenomLabel);
		
		prenom = new JTextField();
		prenomPanel.add(prenom);
		prenom.setColumns(15);
		
		JPanel telephonePanel = new JPanel();
		telephonePanel.setBackground(new Color(173, 216, 230));
		FlowLayout telephonePanelLayout = (FlowLayout) telephonePanel.getLayout();
		telephonePanelLayout.setAlignment(FlowLayout.RIGHT);
		telephonePanelLayout.setVgap(0);
		modelPanel.add(telephonePanel);
		
		JLabel telephoneLabel = new JLabel("Téléphone :");
		telephonePanel.add(telephoneLabel);
		
		telephone = new JTextField();
		telephonePanel.add(telephone);
		telephone.setColumns(15);
		
		JPanel DateNaissancePanel = new JPanel();
		DateNaissancePanel.setBackground(new Color(173, 216, 230));
		FlowLayout fl_DateNaissancePanel = (FlowLayout) DateNaissancePanel.getLayout();
		fl_DateNaissancePanel.setAlignment(FlowLayout.RIGHT);
		fl_DateNaissancePanel.setVgap(0);
		modelPanel.add(DateNaissancePanel);
		
		JLabel dateNaissanceLabel = new JLabel("Date de Naissance :");
		DateNaissancePanel.add(dateNaissanceLabel);
		
		dateNaissance = new JTextField();
		DateNaissancePanel.add(dateNaissance);
		dateNaissance.setColumns(15);
		
		JPanel adressePanel = new JPanel();
		adressePanel.setBackground(new Color(173, 216, 230));
		FlowLayout fl_adressePanel = (FlowLayout) adressePanel.getLayout();
		fl_adressePanel.setAlignment(FlowLayout.RIGHT);
		fl_adressePanel.setVgap(0);
		modelPanel.add(adressePanel);
		
		JLabel adresseLabel = new JLabel("Adresse :");
		adressePanel.add(adresseLabel);
		
		adresse = new JTextField();
		adressePanel.add(adresse);
		adresse.setColumns(15);
		
		JPanel otherPanel = new JPanel();
		otherPanel.setMinimumSize(new Dimension(8, 8));
		infoGestionnairePanel.add(otherPanel);
		otherPanel.setLayout(new GridLayout(2, 1, 0, 0));
		
		JPanel emailPanel = new JPanel();
		emailPanel.setBackground(new Color(173, 216, 230));
		otherPanel.add(emailPanel);
		emailPanel.setLayout(new GridLayout(3, 1, 0, 0));
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(173, 216, 230));
		emailPanel.add(panel);
		panel.setLayout(new GridLayout(0, 2, 0, 0));
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(176, 224, 230));
		FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		panel.add(panel_1);
		
		JLabel lblNewLabel = new JLabel("Email:");
		panel_1.add(lblNewLabel);
		
		textEmail = new JTextField();
		panel.add(textEmail);
		textEmail.setColumns(10);
		
		
		JScrollPane notificationScrollPane = new JScrollPane();
		otherPanel.add(notificationScrollPane);
		notificationScrollPane.setViewportView(notification);
		
		JPanel notificationPanel = new JPanel();
		notificationScrollPane.setViewportView(notificationPanel);
		notificationPanel.setBackground(Color.WHITE);
		notificationPanel.setBorder(new TitledBorder(null, "Notification", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		notificationPanel.setLayout(new BorderLayout(0, 0));
		
		notification.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		visualiserGestionnairePanel.setLayout(gl_visualiserGestionnairePanel);
		
		/*
		 * Contrôle de la saisie du champ Libelle et Coéfficient avant activation du bouton de validation 
		 * 
		 * */	
		ArrayList<JTextField> textFields = new ArrayList<>();

		textFields.add(nom);
		textFields.add(prenom);
		textFields.add(telephone);
		textFields.add(dateNaissance);
		textFields.add(adresse);
		
		JPanel loginPanel = new JPanel();
		loginPanel.setBackground(new Color(173, 216, 230));
		modelPanel.add(loginPanel);
		FlowLayout loginPanelLayout = (FlowLayout) loginPanel.getLayout();
		loginPanelLayout.setAlignment(FlowLayout.RIGHT);
		loginPanelLayout.setVgap(0);
		
		JLabel loginLabel = new JLabel("Login :");
		loginPanel.add(loginLabel);
		
		login = new JTextField();
		loginPanel.add(login);
		login.setColumns(15);
		
		JPanel passwordPanel = new JPanel();
		passwordPanel.setBackground(new Color(173, 216, 230));
		modelPanel.add(passwordPanel);
		FlowLayout passwordPanelLayout = (FlowLayout) passwordPanel.getLayout();
		passwordPanelLayout.setVgap(0);
		passwordPanelLayout.setAlignment(FlowLayout.RIGHT);
		
		JLabel passwordLabel = new JLabel("Password :");
		passwordPanel.add(passwordLabel);
		
		password = new JPasswordField();
		password.setColumns(15);
		passwordPanel.add(password);

		FilledFieldChecker updateButtonChecker = new FilledFieldChecker(updateButton, textFields);
		FilledFieldChecker deleteButtonChecker = new FilledFieldChecker(deleteButton, textFields);

		nom.getDocument().addDocumentListener(updateButtonChecker);
		prenom.getDocument().addDocumentListener(updateButtonChecker);
		telephone.getDocument().addDocumentListener(updateButtonChecker);
		dateNaissance.getDocument().addDocumentListener(updateButtonChecker);
		adresse.getDocument().addDocumentListener(updateButtonChecker);
		nom.getDocument().addDocumentListener(deleteButtonChecker);
		prenom.getDocument().addDocumentListener(deleteButtonChecker);
		telephone.getDocument().addDocumentListener(deleteButtonChecker);
		dateNaissance.getDocument().addDocumentListener(deleteButtonChecker);
		adresse.getDocument().addDocumentListener(deleteButtonChecker);
		
		//Utilitaire.center(this, getPreferredSize());
		
	}
	
	
	
	
	
	
	@Override
	public String getId() {
		return id.getText();
	}
	
	@Override
	public void addUpdateListener (ActionListener listenerForUpdateButton) {
		updateButton.addActionListener(listenerForUpdateButton);
	}
	
	@Override
	public void addDeleteListener (ActionListener listenerForDeleteButton) {
		deleteButton.addActionListener(listenerForDeleteButton);
	}
	
	

	@Override
	public void resetUI() {
		resetFormUI();
	}

	@Override
	public String getNom() {
		return nom.getText();
	}
	
	@Override
	public String getPrenom() {
		return prenom.getText();
	}
	
	@Override
	public String getTelephone() {
		return telephone.getText();
	}
	
	
	@Override
	public String getLogin() {
		return login.getText();
	}
	
	@Override
	public String getPassword() {
		return String.valueOf(password.getPassword());
	}

	public void setEmail(String text) {
		textEmail.setText(text);
	}

	
	public String getEmail() {
		return textEmail.getText();
	}
	
	
	@Override
	public void resetFormUI() {
		this.id.setText("-1");
		this.prenom.setText(null);
		this.nom.setText(null);
		this.telephone.setText(null);
		this.dateNaissance.setText(null);
		this.adresse.setText(null);
		this.login.setText(null);
		this.password.setText(null);
		this.textEmail.setText("");
		displayNotification(null);
	}
	
	
	
	private String getValueListeGestionnaireAt(int row, int i) {
		return (String) listeGestionnaire.getValueAt(row, i);
	}
	
	private void updateUIGestionnaire(Gestionnaire gestionnaire) {
		try {
			this.id.setText(String.valueOf(gestionnaire.getId()));
			this.nom.setText(gestionnaire.getNom());
			this.prenom.setText(gestionnaire.getPrenom());
			this.telephone.setText(gestionnaire.getTelephone());
			this.adresse.setText(gestionnaire.getAdresse());
			this.dateNaissance.setText(gestionnaire.getDate_naissance());
			this.login.setText(gestionnaire.getLogin());
			setEmail(gestionnaire.getEmail());
		} catch ( NullPointerException ignored ) {}
	}
	
	
	

	@Override
	public void addGoListener(ActionListener listenerForGoButton) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getTypeRecherche() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCritere() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String getDateNaissance() {
		
		return dateNaissance.getText();
	}


	@Override
	public void loadData(List<Gestionnaire> gestionnaires) {
		
		System.out.println(gestionnaires.toString());
		for(Gestionnaire ges : gestionnaires)
		{
			System.out.println(ges.getNom()+"  "+ges.getPrenom());
		}
		gestionnaireModel.loadData(gestionnaires);
	}






	@Override
	public String getAdresse() {
		return adresse.getText();
	}
}