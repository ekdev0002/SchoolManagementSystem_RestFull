package com.app.sms.ui.gestionnaires.impl;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.xml.bind.JAXBException;

import com.app.sms.exceptions.WSException;
import com.app.sms.models.Eleve;
import com.app.sms.models.EleveModel;
import com.app.sms.ui.gestionnaires.IUConsulterEleve;
import com.app.sms.ui.gestionnaires.listeners.FilledFieldChecker;
import com.app.sms.ui.impl.AbstractUIOperation;
import com.app.sms.utils.Utilitaire;

public class UIConsulterEleve extends AbstractUIOperation implements IUConsulterEleve {

	private static final URL DEFAULT_URL_PICTURE = UIConsulterEnseignant.class.getResource("/images/inconnu.png");
	private JTable listeEleve;
	private JTextField id;
	private JTextField nom;
	private JTextField prenom;
	private JTextField telephone;
	private JTextField email;
	private JTextField login;
	private JPasswordField password;
	private JTextField genre;
	private JButton updateButton;
	private JCheckBox radioClasse;
	private JComboBox<String> classe;
	private JLabel picture;
	private JButton selectPicture;
	private JButton deleteButton;
	private String picturePath;
	private String currentEleveClasse;
	
	private EleveModel eleveModel;
	
	public UIConsulterEleve() {

		setMinimumSize(new Dimension(834, 573));
		setSize(new Dimension(834, 583));
		setPreferredSize(new Dimension(834, 573));
		operationIcon.setIcon(new ImageIcon(UIConsulterEleve.class.getResource("/images/téléchargement (3).jpg")));
		operationLabel.setText("Eleves");
		
		JPanel mainPanel = new JPanel();
		mainPanel.setPreferredSize(new Dimension(8, 8));
		centerPanel.add(mainPanel, BorderLayout.CENTER);
		mainPanel.setLayout(new GridLayout(2, 1, 0, 0));
		
		JPanel listeElevePanel = new JPanel();
		listeElevePanel.setBackground(new Color(160, 82, 45));
		listeElevePanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		mainPanel.add(listeElevePanel);
		
		JScrollPane listeScrollPane = new JScrollPane();
		listeScrollPane.setBorder(new TitledBorder(null, "Liste des \u00E9l\u00E8ves", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GroupLayout listeElevePanelLayout = new GroupLayout(listeElevePanel);
		listeElevePanelLayout.setHorizontalGroup(
			listeElevePanelLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(listeElevePanelLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(listeScrollPane, GroupLayout.DEFAULT_SIZE, 812, Short.MAX_VALUE)
					.addContainerGap())
		);
		listeElevePanelLayout.setVerticalGroup(
			listeElevePanelLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(listeElevePanelLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(listeScrollPane, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(19, Short.MAX_VALUE))
		);
		
		listeEleve = new JTable();
		eleveModel = new EleveModel();
		listeEleve.setModel(eleveModel);
		listeScrollPane.setViewportView(listeEleve);
		listeElevePanel.setLayout(listeElevePanelLayout);
		listeEleve.getSelectionModel().addListSelectionListener(new ListSelectionListener() {			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if ( ! radioClasse.isEnabled() ) radioClasse.setEnabled(true);
				int selectedRow = listeEleve.getSelectedRow();
				if ( selectedRow >= 0 ) {
					String [] data = getEleveDataByRow(selectedRow);
					updateUIForm (data);
				}
				setEleveClasse (classe.getItemAt(classe.getSelectedIndex()));
			}
		});
		
		JPanel visuliserElevePanel = new JPanel();
		visuliserElevePanel.setBackground(new Color(47, 79, 79));
		visuliserElevePanel.setBorder(new TitledBorder(null, "Modifier un \u00E9l\u00E8ve", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		mainPanel.add(visuliserElevePanel);
		
		JPanel formPanel = new JPanel();
		
		JPanel buttonPanelLeft = new JPanel();
		buttonPanelLeft.setBackground(new Color(173, 216, 230));
		buttonPanelLeft.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "...", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		
		updateButton = new JButton("Update");
		updateButton.setEnabled(false);
		updateButton.setBounds(27, 16, 80, 20);
		updateButton.setToolTipText("Valider la modification des informations de l'élève");
		updateButton.setMaximumSize(new Dimension(80, 20));
		updateButton.setMinimumSize(new Dimension(80, 20));
		updateButton.setPreferredSize(new Dimension(80, 20));
		JButton annulerButton = new JButton("Quitter");
		annulerButton.setBounds(154, 16, 80, 20);
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
		deleteButton.setBounds(297, 16, 80, 20);
		deleteButton.setToolTipText("Supprimer un élève");
		deleteButton.setPreferredSize(new Dimension(80, 20));
		deleteButton.setMinimumSize(new Dimension(80, 20));
		deleteButton.setMaximumSize(new Dimension(80, 20));
		buttonPanelLeft.setLayout(null);
		buttonPanelLeft.add(updateButton);
		buttonPanelLeft.add(annulerButton);
		buttonPanelLeft.add(deleteButton);
		
		JPanel notificationPanel = new JPanel();
		notificationPanel.setBackground(new Color(173, 216, 230));
		notificationPanel.setBorder(new TitledBorder(null, "Notification", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		notificationPanel.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		notificationPanel.add(scrollPane, BorderLayout.CENTER);

		notification.setFont(new Font("Tahoma", Font.PLAIN, 11));
		scrollPane.setViewportView(notification);
		GroupLayout visuliserElevePanelLayout = new GroupLayout(visuliserElevePanel);
		visuliserElevePanelLayout.setHorizontalGroup(
			visuliserElevePanelLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(visuliserElevePanelLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(visuliserElevePanelLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(visuliserElevePanelLayout.createSequentialGroup()
							.addComponent(buttonPanelLeft, GroupLayout.PREFERRED_SIZE, 398, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(notificationPanel, GroupLayout.PREFERRED_SIZE, 396, GroupLayout.PREFERRED_SIZE))
						.addComponent(formPanel, GroupLayout.DEFAULT_SIZE, 796, Short.MAX_VALUE))
					.addContainerGap())
		);
		visuliserElevePanelLayout.setVerticalGroup(
			visuliserElevePanelLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(visuliserElevePanelLayout.createSequentialGroup()
					.addComponent(formPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(visuliserElevePanelLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(buttonPanelLeft, GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
						.addComponent(notificationPanel, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		formPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel buttonPanel = new JPanel();
		formPanel.add(buttonPanel, BorderLayout.SOUTH);
		buttonPanel.setLayout(new GridLayout(1, 2, 0, 0));
		
		JPanel infoElevePanel = new JPanel();
		formPanel.add(infoElevePanel, BorderLayout.CENTER);
		infoElevePanel.setLayout(new GridLayout(1, 2, 0, 0));
		
		JPanel modelPanel = new JPanel();
		modelPanel.setBackground(new Color(173, 216, 230));
		infoElevePanel.add(modelPanel);
		modelPanel.setLayout(new GridLayout(5, 1, 0, 0));
		
		JPanel idPanel = new JPanel();
		idPanel.setBackground(new Color(173, 216, 230));
		FlowLayout idPanelLayout = (FlowLayout) idPanel.getLayout();
		idPanelLayout.setAlignment(FlowLayout.RIGHT);
		idPanelLayout.setVgap(0);
		modelPanel.add(idPanel);
		
		JLabel idLabel = new JLabel("id :");
		idPanel.add(idLabel);
		
		id = new JTextField();
		id.setEnabled(false);
		id.setEditable(false);
		idPanel.add(id);
		id.setColumns(20);
		
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
		
		JPanel emailPanel = new JPanel();
		emailPanel.setBackground(new Color(173, 216, 230));
		FlowLayout emailPanelLayout = (FlowLayout) emailPanel.getLayout();
		emailPanelLayout.setAlignment(FlowLayout.RIGHT);
		emailPanelLayout.setVgap(0);
		modelPanel.add(emailPanel);
		
		JLabel emailLabel = new JLabel("Email :");
		emailPanel.add(emailLabel);
		
		email = new JTextField();
		emailPanel.add(email);
		email.setColumns(15);
		
		JPanel otherPanel = new JPanel();
		otherPanel.setBackground(new Color(173, 216, 230));
		otherPanel.setMinimumSize(new Dimension(8, 8));
		infoElevePanel.add(otherPanel);
		otherPanel.setLayout(new GridLayout(5, 1, 0, 0));
		
		JPanel loginPanel = new JPanel();
		loginPanel.setBackground(new Color(173, 216, 230));
		FlowLayout loginPanelLayout = (FlowLayout) loginPanel.getLayout();
		loginPanelLayout.setAlignment(FlowLayout.RIGHT);
		loginPanelLayout.setVgap(0);
		otherPanel.add(loginPanel);
		
		JLabel loginLabel = new JLabel("Login :");
		loginPanel.add(loginLabel);
		
		login = new JTextField();
		loginPanel.add(login);
		login.setColumns(15);
		
		JPanel passwordPanel = new JPanel();
		passwordPanel.setBackground(new Color(173, 216, 230));
		FlowLayout passwordPanelLayout = (FlowLayout) passwordPanel.getLayout();
		passwordPanelLayout.setVgap(0);
		passwordPanelLayout.setAlignment(FlowLayout.RIGHT);
		otherPanel.add(passwordPanel);
		
		JLabel passwordLabel = new JLabel("Password :");
		passwordPanel.add(passwordLabel);
		
		password = new JPasswordField();
		password.setColumns(15);
		passwordPanel.add(password);
		
		JPanel classePanel = new JPanel();
		classePanel.setBackground(new Color(173, 216, 230));
		FlowLayout classePanelLayout = (FlowLayout) classePanel.getLayout();
		classePanelLayout.setVgap(0);
		classePanelLayout.setAlignment(FlowLayout.RIGHT);
		otherPanel.add(classePanel);
		
		JLabel classeLabel = new JLabel("Classe :");
		classePanel.add(classeLabel);
		
		classe = new JComboBox<>();
		classe.setToolTipText("idClasse" + Utilitaire.SEPARATEUR + "libelleClasse");
		classe.setEnabled(false);
		classe.setMaximumSize(new Dimension(166, 20));
		classe.setMinimumSize(new Dimension(166, 20));
		classe.setPreferredSize(new Dimension(141, 20));
		try {
			ArrayList<String> libelles = Utilitaire.getListeClasse();
			for ( String libelle : libelles ) {
				classe.addItem(libelle);
			}
		} catch ( WSException | JAXBException | IOException exception) {
			displayErrorMessage( "Error while loading classes list : " + exception.getMessage() );
		}
		
		classePanel.add(classe);
		
		radioClasse = new JCheckBox("");
		radioClasse.setEnabled(true);
		radioClasse.setToolTipText("Modifier la classe ...");
		radioClasse.setPreferredSize(new Dimension(20, 20));
		radioClasse.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				activateClasseSelection ();
			}
		});
		classePanel.add(radioClasse);
		
		JPanel genrePanel = new JPanel();
		genrePanel.setBackground(new Color(173, 216, 230));
		FlowLayout genrePanelLayout = (FlowLayout) genrePanel.getLayout();
		genrePanelLayout.setAlignment(FlowLayout.RIGHT);
		genrePanelLayout.setVgap(0);
		otherPanel.add(genrePanel);
		
		JLabel genreLabel = new JLabel("Genre :");
		genrePanel.add(genreLabel);
		
		genre = new JTextField();
		genre.setEnabled(false);
		genre.setEditable(false);
		genrePanel.add(genre);
		genre.setColumns(15);
		
		JPanel picturePanel = new JPanel();
		picturePanel.setBackground(new Color(173, 216, 230));
		picturePanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		infoElevePanel.add(picturePanel);
		picturePanel.setLayout(null);
		
		picture = new JLabel("");		
		picture.setBounds(10, 6, 88, 88);
		picturePanel.add(picture);
		setDefaultPicture ();
		
		selectPicture = new JButton("Select a new picture ...");
		selectPicture.setBounds(104, 71, 151, 18);
		picturePanel.add(selectPicture);
		selectPicture.setMaximumSize(new Dimension(75, 18));
		selectPicture.setPreferredSize(new Dimension(75, 18));
		selectPicture.setMinimumSize(new Dimension(75, 18));
		selectPicture.setToolTipText("Sélectionnez une nouvelle image ...");
		selectPicture.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
		       selectAndSetPictureFromBrowser ();
			}
		});
		visuliserElevePanel.setLayout(visuliserElevePanelLayout);
		
		/*
		 * Contrôle de la saisie du champ Libelle et Coéfficient avant activation du bouton de validation 
		 * 
		 * */	
		ArrayList<JTextField> textFields = new ArrayList<>();
		FilledFieldChecker updateButtonChecker = new FilledFieldChecker(updateButton, textFields);
		FilledFieldChecker deleteButtonChecker = new FilledFieldChecker(deleteButton, textFields);

		textFields.add(nom);
		textFields.add(prenom);
		textFields.add(telephone);
		textFields.add(email);
		nom.getDocument().addDocumentListener(updateButtonChecker);
		prenom.getDocument().addDocumentListener(updateButtonChecker);
		telephone.getDocument().addDocumentListener(updateButtonChecker);
		email.getDocument().addDocumentListener(updateButtonChecker);
		nom.getDocument().addDocumentListener(deleteButtonChecker);
		prenom.getDocument().addDocumentListener(deleteButtonChecker);
		telephone.getDocument().addDocumentListener(deleteButtonChecker);
		email.getDocument().addDocumentListener(deleteButtonChecker);
		
		Utilitaire.center(this, getPreferredSize());
	}
	
	@Override
	public void updateUIForm(String[] data) {
		try {
			this.setId(data[0]);
			this.setNom(data[1]);
			this.setPrenom(data[2]);
			this.setGenre(data[3]);
			this.setTelephone(data[4]);
			this.setEmail(data[5]);
			System.out.println(data[9]+"ffff"+data[6]);
			this.setClasseOnUI(data[9],data[6]);
			this.setLogin(data[7]);		
			this.setPicturePath(data[8]);
			if (data[8] == null) setDefaultPicture();
			else {
				ImageIcon imageIcon = Utilitaire.getImageIcon (this, picturePath);
				setPicture(imageIcon);
			}
		} catch (NullPointerException ignored) {}
	}
	
	@Override
	public String getPicturePath () {
		return picturePath;
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
	public String getEmail() {
		return email.getText();
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
	public String getGenre() {
		return genre.getText();
	}
	
	@Override
	public String getLibelleClasse() {
		String [] data = classe.getItemAt(classe.getSelectedIndex()).split(Utilitaire.SEPARATEUR);
		return data [1];
	}
	
	@Override
	public void resetFormUI() {
		this.setId("-1");
		this.setPrenom(null);
		this.setNom(null);
		this.setTelephone(null);
		this.setEmail(null);
		this.setGenre(null);
		this.login.setText(null);
		this.password.setText(null);
		
		radioClasse.setSelected(false);
		radioClasse.setEnabled(true);
		classe.setEnabled(false);
		currentEleveClasse=null;
		classe.setSelectedItem(null);
		
		setDefaultPicture ();
		displayNotification(null);
		setDefaultPicture();
	}
	
	@Override
	public void loadData(List<Eleve> eleves) {
		eleveModel.loadData(eleves);
	}
	
	private void setPicture(ImageIcon imageIcon) {
		picture.setIcon(imageIcon);
	}
	
	private void setEleveClasse(String classe) {
		currentEleveClasse = classe;
	}

	private void activateClasseSelection() {
		if ( radioClasse.isSelected() ) {
			classe.setEnabled(true);
		} else {
			classe.setEnabled(false);
			classe.setSelectedItem(currentEleveClasse);
		}		
	}
	
	private void setPicturePath(String path) {
		picturePath = path;
	}
	
	private void setId(String id) {
		this.id.setText(id);
	}
	
	private void setDefaultPicture () {
		picture.setIcon(new ImageIcon(DEFAULT_URL_PICTURE));
	}
	
	private void setNom(String nom) {
		this.nom.setText(nom);
	}
	private void setPrenom(String prenom) {
		this.prenom.setText(prenom);
	}
	
	private void setTelephone(String telephone) {
		this.telephone.setText(telephone);
	}
	private void setEmail(String email) {
		this.email.setText(email);
	}
	
	private void setLogin(String login) {
		this.login.setText(login);
	}

	private void setGenre(String genre) {
		this.genre.setText(genre);
	}
	
	private void setClasseOnUI(String idClasse, String libelleClasse) {
		//this.classe.setSelectedItem(idClasse + Utilitaire.SEPARATEUR + libelleClasse);
		this.classe.getModel().setSelectedItem(idClasse + Utilitaire.SEPARATEUR + libelleClasse);
	}	
	private String[] getEleveDataByRow(int row) {
		
		String [] values = new String [eleveModel.getColumnCount()];
		for ( int i = 0 ; i < eleveModel.getColumnCount() ; i ++ ) {
			values [i] = (String) eleveModel.getValueAt(row, i);
		}
		return values;
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
}
