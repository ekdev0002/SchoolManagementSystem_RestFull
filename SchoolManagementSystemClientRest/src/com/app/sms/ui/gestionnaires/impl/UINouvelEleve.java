package com.app.sms.ui.gestionnaires.impl;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.xml.bind.JAXBException;

import com.app.sms.exceptions.WSException;
import com.app.sms.models.enums.Genre;
import com.app.sms.ui.gestionnaires.IUINouvelEleve;
import com.app.sms.ui.gestionnaires.listeners.FilledFieldChecker;
import com.app.sms.ui.impl.AbstractUIOperation;
import com.app.sms.utils.Utilitaire;

public class UINouvelEleve extends AbstractUIOperation implements IUINouvelEleve {
	
	/**
	 * 
	 * Les éléments du contrôleur ...
	 */
	private JButton submitButton;
	private JTextField id;
	private JTextField nom;
	private JTextField prenom;
	private JTextField email;
	private JTextField telephone;
	private JTextField login;
	private JPasswordField password;
	private JRadioButton homme;
	private JRadioButton femme;
	private JButton selectPictureButton;
	private JLabel pictureLabel;
	private JButton creerButton;
	private JComboBox<String> classes;
	private String picturePath=null;
	
	public UINouvelEleve() {
		setPreferredSize(new Dimension(800, 488));
		operationIcon.setIcon(new ImageIcon(UINouvelEleve.class.getResource("/images/téléchargement (3).jpg")));
		operationLabel.setText("Elèves");
		
		JPanel formPanel = new JPanel();
		formPanel.setBackground(UIManager.getColor("Button.shadow"));
		centerPanel.add(formPanel, BorderLayout.CENTER);
		
		JPanel mainPanel = new JPanel();
		mainPanel.setBackground(new Color(173, 216, 230));
		mainPanel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Nouvel élève", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		
		submitButton = new JButton("Submit");
		submitButton.setPreferredSize(new Dimension(67, 20));
		submitButton.setMinimumSize(new Dimension(67, 20));
		submitButton.setMaximumSize(new Dimension(67, 20));
		submitButton.setEnabled(false);
		
		JButton clearButton = new JButton("Clear");
		clearButton.setPreferredSize(new Dimension(59, 20));
		clearButton.setMinimumSize(new Dimension(59, 20));
		clearButton.setMaximumSize(new Dimension(59, 20));
		clearButton.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				resetUI ();
			}
		});
		
		JButton cancelButton = new JButton("Quitter");
		cancelButton.setPreferredSize(new Dimension(67, 20));
		cancelButton.setMinimumSize(new Dimension(67, 20));
		cancelButton.setMaximumSize(new Dimension(67, 20));
		cancelButton.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				exitWithConfirmation();
			}
		});
		GroupLayout formPanelLayout = new GroupLayout(formPanel);
		formPanelLayout.setHorizontalGroup(
			formPanelLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(formPanelLayout.createSequentialGroup()
					.addContainerGap(579, Short.MAX_VALUE)
					.addComponent(submitButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(clearButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(cancelButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
				.addGroup(Alignment.TRAILING, formPanelLayout.createSequentialGroup()
					.addGap(20)
					.addComponent(mainPanel, GroupLayout.DEFAULT_SIZE, 774, Short.MAX_VALUE))
		);
		formPanelLayout.setVerticalGroup(
			formPanelLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(formPanelLayout.createSequentialGroup()
					.addGap(6)
					.addComponent(mainPanel, GroupLayout.PREFERRED_SIZE, 319, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(formPanelLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(submitButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(clearButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(cancelButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		
		JLabel idLabel = new JLabel("Id :");
		JLabel nomLabel = new JLabel("Nom :");
		JLabel prenomLabel = new JLabel("Prénom :");
		JLabel emailLabel = new JLabel("Email :");
		JLabel telephoneLabel = new JLabel("Téléphone :");
		JLabel classeLabel = new JLabel("Classe :");
		
		id = new JTextField();
		id.setText("-1");
		id.setEnabled(false);
		id.setEditable(false);
		id.setColumns(10);
		
		nom = new JTextField();
		nom.setColumns(10);
		
		prenom = new JTextField();
		prenom.setColumns(10);
		
		email = new JTextField();
		email.setColumns(10);
		
		telephone = new JTextField();
		telephone.setColumns(10);
		
		classes = new JComboBox<>();
		updateListeDeroulanteClasse() ;
		
		creerButton = new JButton("Créer");
		creerButton.setPreferredSize(new Dimension(61, 20));
		creerButton.setMinimumSize(new Dimension(61, 20));
		creerButton.setMaximumSize(new Dimension(61, 20));
		
		JPanel notificationPanel = new JPanel();
		notificationPanel.setBackground(new Color(173, 216, 230));
		notificationPanel.setBorder(new TitledBorder(null, "Notification", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		pictureLabel = new JLabel("");
		pictureLabel.setIcon(new ImageIcon(UINouvelEleve.class.getResource("/images/scolarite_ws.png")));
		
		JLabel sexeLabel = new JLabel("Sexe :");
		
		homme = new JRadioButton(Genre.Homme.name());
		homme.setBackground(new Color(173, 216, 230));
		homme.setSelected(true);
		femme = new JRadioButton(Genre.Femme.name());
		femme.setBackground(new Color(173, 216, 230));
		ButtonGroup genres = new ButtonGroup();
		genres.add(homme);
		genres.add(femme);
		
		selectPictureButton = new JButton("Select picture");
		selectPictureButton.setToolTipText("Sélectionner une image ...");
		selectPictureButton.setMinimumSize(new Dimension(88, 20));
		selectPictureButton.setMaximumSize(new Dimension(88, 20));
		selectPictureButton.setPreferredSize(new Dimension(93, 20));
		selectPictureButton.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
		       selectAndSetPictureFromBrowser ();
			}
		});
		
		JPanel loginPanel = new JPanel();
		loginPanel.setBackground(new Color(173, 216, 230));
		FlowLayout loginPanelLayout = (FlowLayout) loginPanel.getLayout();
		loginPanelLayout.setVgap(2);
		loginPanelLayout.setAlignment(FlowLayout.RIGHT);
		
		JLabel loginLabel = new JLabel("Login :");
		loginPanel.add(loginLabel);
		
		login = new JTextField();
		loginPanel.add(login);
		login.setColumns(20);
		
		JPanel passwordPanel = new JPanel();
		passwordPanel.setBackground(new Color(173, 216, 230));
		FlowLayout passwordPanelLayout = (FlowLayout) passwordPanel.getLayout();
		passwordPanelLayout.setVgap(2);
		passwordPanelLayout.setAlignment(FlowLayout.RIGHT);
		
		JLabel passwordLabel = new JLabel("Password :");
		passwordLabel.setBackground(new Color(173, 216, 230));
		passwordPanel.add(passwordLabel);
		
		password = new JPasswordField();
		password.setColumns(20);
		passwordPanel.add(password);
		GroupLayout mainPanelLayout = new GroupLayout(mainPanel);
		mainPanelLayout.setHorizontalGroup(
			mainPanelLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(mainPanelLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(mainPanelLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(mainPanelLayout.createSequentialGroup()
							.addGroup(mainPanelLayout.createParallelGroup(Alignment.TRAILING)
								.addGroup(mainPanelLayout.createSequentialGroup()
									.addGap(14)
									.addGroup(mainPanelLayout.createParallelGroup(Alignment.TRAILING)
										.addComponent(emailLabel)
										.addComponent(prenomLabel)
										.addComponent(nomLabel)
										.addComponent(idLabel))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(mainPanelLayout.createParallelGroup(Alignment.LEADING, false)
										.addComponent(email)
										.addComponent(prenom)
										.addComponent(nom)
										.addComponent(id, GroupLayout.PREFERRED_SIZE, 152, GroupLayout.PREFERRED_SIZE))
									.addGap(32)
									.addGroup(mainPanelLayout.createParallelGroup(Alignment.LEADING)
										.addGroup(mainPanelLayout.createSequentialGroup()
											.addGroup(mainPanelLayout.createParallelGroup(Alignment.LEADING)
												.addComponent(telephoneLabel)
												.addComponent(classeLabel))
											.addPreferredGap(ComponentPlacement.RELATED)
											.addGroup(mainPanelLayout.createParallelGroup(Alignment.LEADING)
												.addGroup(mainPanelLayout.createSequentialGroup()
													.addComponent(classes, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
													.addGap(18)
													.addComponent(creerButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
												.addComponent(telephone, GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE)))
										.addGroup(mainPanelLayout.createParallelGroup(Alignment.TRAILING, false)
											.addComponent(loginPanel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
											.addComponent(passwordPanel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
									.addGap(95))
								.addGroup(mainPanelLayout.createSequentialGroup()
									.addComponent(sexeLabel)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(homme)
									.addPreferredGap(ComponentPlacement.RELATED)))
							.addGroup(mainPanelLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(mainPanelLayout.createSequentialGroup()
									.addComponent(pictureLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addGap(120))
								.addComponent(selectPictureButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addGroup(mainPanelLayout.createSequentialGroup()
									.addGap(18)
									.addComponent(femme))))
						.addComponent(notificationPanel, GroupLayout.PREFERRED_SIZE, 654, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		mainPanelLayout.setVerticalGroup(
			mainPanelLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(mainPanelLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(mainPanelLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(mainPanelLayout.createSequentialGroup()
							.addComponent(pictureLabel, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(selectPictureButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(mainPanelLayout.createSequentialGroup()
							.addGroup(mainPanelLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(mainPanelLayout.createParallelGroup(Alignment.BASELINE)
									.addComponent(id, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(idLabel))
								.addComponent(loginPanel, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(mainPanelLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(mainPanelLayout.createParallelGroup(Alignment.BASELINE)
									.addComponent(nom, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(nomLabel))
								.addComponent(passwordPanel, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(mainPanelLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(mainPanelLayout.createSequentialGroup()
									.addGroup(mainPanelLayout.createParallelGroup(Alignment.BASELINE)
										.addComponent(prenom, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(prenomLabel))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(mainPanelLayout.createParallelGroup(Alignment.BASELINE)
										.addComponent(email, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(emailLabel)
										.addComponent(classeLabel)))
								.addGroup(mainPanelLayout.createSequentialGroup()
									.addGroup(mainPanelLayout.createParallelGroup(Alignment.BASELINE)
										.addComponent(telephoneLabel)
										.addComponent(telephone, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addGroup(mainPanelLayout.createParallelGroup(Alignment.BASELINE)
										.addComponent(classes, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(creerButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
							.addGap(20)
							.addGroup(mainPanelLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(homme)
								.addComponent(femme)
								.addComponent(sexeLabel))))
					.addGap(25)
					.addComponent(notificationPanel, GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE)
					.addGap(20))
		);
		notificationPanel.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		notificationPanel.add(scrollPane, BorderLayout.CENTER);
		
		notification.setFont(new Font("Tahoma", Font.PLAIN, 11));
		scrollPane.setViewportView(notification);
		mainPanel.setLayout(mainPanelLayout);
		formPanel.setLayout(formPanelLayout);
		
		/*
		 * Contrôle de la saisie du champ Libelle et Coéfficient avant activation du bouton de validation 
		 * 
		 * */	
		ArrayList<JTextField> textFields = new ArrayList<>();
		textFields.add(nom);
		textFields.add(prenom);
		textFields.add(email);
		textFields.add(telephone);
		FilledFieldChecker submitButtonChecker = new FilledFieldChecker(submitButton, textFields);
		nom.getDocument().addDocumentListener(submitButtonChecker);
		prenom.getDocument().addDocumentListener(submitButtonChecker);
		email.getDocument().addDocumentListener(submitButtonChecker);
		telephone.getDocument().addDocumentListener(submitButtonChecker);
	}
	
	@Override
	public void updateListeDeroulanteClasse() {
		classes.removeAllItems();
		try {
			ArrayList<String> libelles = Utilitaire.getListeClasse();
			for ( String libelle : libelles ) {
				classes.addItem(libelle);
			}
			
		} catch ( WSException | JAXBException | IOException exception) {
			displayErrorMessage( "Error while loading classes list : " + exception.getMessage() );
		}
	}

	@Override
	public void resetUI() {
		this.id.setText("-1");
		this.nom.setText(null);
		this.prenom.setText(null);
		this.telephone.setText(null);
		this.email.setText(null);
		this.login.setText(null);
		this.password.setText(null);
		displayNotification(null);
		setPicture(UINouvelEleve.class.getResource("/images/inconnu.png"));
		classes.setSelectedItem(null);
	}

	@Override
	public void addSubmitListener(ActionListener listenForSubmitButton) {
		submitButton.addActionListener(listenForSubmitButton);
	}
	
	@Override
	public void addCreerListener(ActionListener listenForCreerButton) {
		creerButton.addActionListener(listenForCreerButton);
	}

	@Override
	public String getId() {
		return id.getText();
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
	public String getEmail() {
		return email.getText();
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
	
	@Override
	public void addSelectPictureListener(ActionListener listenerForSelectPictureButton) {
		selectPictureButton.addActionListener(listenerForSelectPictureButton);
	}

	@Override
	public String getGenre () {
		if (homme.isSelected()) return Genre.Homme.name() ;
		else return Genre.Femme.name() ;
	}
	
	@Override
	public String getPicturePath() {
		return picturePath;
	}
	
	@Override
	public String getLibelleClasse () {
		String [] data = classes.getItemAt(classes.getSelectedIndex()).split(Utilitaire.SEPARATEUR);
		return data [1];
	}
	
	@Override
	public String getIdClasse () {
		String [] data = classes.getItemAt(classes.getSelectedIndex()).split(Utilitaire.SEPARATEUR);
		return data [0];
	}
	
	private void selectAndSetPictureFromBrowser() {
		JFileChooser chooser = new JFileChooser();
        int status = chooser.showOpenDialog(this);
        if (status == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            picturePath = file.getAbsolutePath();
            ImageIcon imageIcon = Utilitaire.getImageIcon (this, picturePath ); 
            setPicture(imageIcon);
        }	   
	}
	
	private void setPicture(ImageIcon imageIcon) {
		pictureLabel.setIcon(imageIcon);
	}
	
	private void setPicture(URL resource) {
		setPicture(new ImageIcon(resource));
	}
}
