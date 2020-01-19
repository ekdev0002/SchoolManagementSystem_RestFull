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

import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.xml.bind.JAXBException;

import com.app.sms.exceptions.WSException;
import com.app.sms.models.Candidature;
import com.app.sms.models.CandidatureModel;
import com.app.sms.models.Classe;
import com.app.sms.models.Enseignant;
import com.app.sms.models.EnseignantModel;
import com.app.sms.ui.gestionnaires.IUConsulterEnseignant;
import com.app.sms.ui.gestionnaires.listeners.FilledFieldChecker;
import com.app.sms.ui.impl.AbstractUIOperation;
import com.app.sms.utils.Utilitaire;

public class UIConsulterEnseignant extends AbstractUIOperation implements IUConsulterEnseignant {
	
	private static final URL DEFAULT_URL_PICTURE = UIConsulterEnseignant.class.getResource("/images/inconnu.png");
	private JTable listeEnseignant;
	private JTextField id;
	private JTextField nom;
	private JTextField prenom;
	private JTextField telephone;
	private JTextField email;
	private JTextField login;
	private JPasswordField password;
	private JTextField genre;
	private JButton updateButton;
	private JLabel picture;
	private JButton selectPicture;
	private JButton deleteButton;
	private String path;
	private JTable listeCandidature;

	private JList<String> listClassesOut;
	private JList<String> listeClasses;

	private JButton ajouterClasse;
	private JButton retirerClasse;
	
	private EnseignantModel enseignantModel;
	private CandidatureModel candidatureModel;
	

	public UIConsulterEnseignant() {

		setMinimumSize(new Dimension(834, 700));
		setSize(new Dimension(834, 700));
		setPreferredSize(new Dimension(834, 600));
		operationIcon.setIcon(new ImageIcon(UIConsulterEnseignant.class.getResource("/images/enseignant-logo.jpg")));
		operationLabel.setText("Enseignants");
		
		JPanel mainPanel = new JPanel();
		mainPanel.setPreferredSize(new Dimension(8, 8));
		centerPanel.add(mainPanel, BorderLayout.CENTER);
		mainPanel.setLayout(new GridLayout(2, 1, 0, 0));
		
		JPanel listeEnseignantPanel = new JPanel();
		listeEnseignantPanel.setBackground(new Color(210, 180, 140));
		listeEnseignantPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		mainPanel.add(listeEnseignantPanel);
		
		JScrollPane listeEnseignantScrollPane = new JScrollPane();
		listeEnseignantScrollPane.setBorder(new TitledBorder(null, "Liste des enseignants", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		listeEnseignant = new JTable();
		listeEnseignant.setBackground(new Color(222, 184, 135));
		enseignantModel = new EnseignantModel();
		listeEnseignant.setModel(enseignantModel);
		listeEnseignantPanel.setLayout(new GridLayout(2, 1, 0, 0));
		listeEnseignantScrollPane.setViewportView(listeEnseignant);
		listeEnseignantPanel.add(listeEnseignantScrollPane);
		
		JScrollPane ListeCandidatureScrollPane = new JScrollPane();
		ListeCandidatureScrollPane.setBorder(new TitledBorder(null, "Liste des candidatures", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		listeEnseignantPanel.add(ListeCandidatureScrollPane);
		
		listeCandidature = new JTable();
		listeCandidature.setBackground(new Color(210, 180, 140));
		candidatureModel = new CandidatureModel ();
		
		listeCandidature.setModel(candidatureModel);

		ListeCandidatureScrollPane.setViewportView(listeCandidature);
		listeEnseignant.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				try {
					ListSelectionModel selectionModel = listeEnseignant.getSelectionModel() ;
					for ( int i = selectionModel.getMinSelectionIndex() ; i <= selectionModel.getMaxSelectionIndex() ; i ++ ) {
						if (selectionModel.isSelectedIndex(i)) {
							int enseignantId = Integer.parseInt(getValueListeEnseignantAt(i, 0));
				
							Enseignant enseignant = Enseignant.getEnseignantById (enseignantId);
							
							updateUIEnseignant (enseignant);
							
							List<Candidature> candidatures = enseignant.getCandidatureList().getCandidatureList();
							
							candidatureModel.loadData(candidatures);
							
							List<Classe> classes = enseignant.getClasseList().getClasseList();
							List<Classe> touteLesclasses = null;
							try {
								touteLesclasses = Classe.list();
							} catch (WSException | JAXBException | IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							
							DefaultListModel<String> model = new DefaultListModel<>();
							for (Classe classe : classes) {
								model.addElement( classe.getId() + Utilitaire.SEPARATEUR + classe.getLibelle());
							}
							
							DefaultListModel<String> model2 = new DefaultListModel<>();
							for (Classe classe : touteLesclasses) {
								model2.addElement( classe.getId() + Utilitaire.SEPARATEUR + classe.getLibelle());
							}
							listeClasses.setModel(model);
							listClassesOut.setModel(model2);

							
							break;
						}
					}
				} catch ( WSException | IOException | JAXBException exception) {
					displayErrorMessage( "Error in the model : " + exception.getMessage() );
				}
			}
		});
		
		JPanel visualiserEnseignantPanel = new JPanel();
		visualiserEnseignantPanel.setBackground(Color.GRAY);
		visualiserEnseignantPanel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Modifier les donn\u00E9es de l'enseignant", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		mainPanel.add(visualiserEnseignantPanel);
		
		JPanel formPanel = new JPanel();
		formPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel infoEnseignantPanel = new JPanel();
		formPanel.add(infoEnseignantPanel, BorderLayout.CENTER);
		infoEnseignantPanel.setLayout(new GridLayout(1, 2, 0, 0));
		
		JPanel modelPanel = new JPanel();
		infoEnseignantPanel.add(modelPanel);
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
		otherPanel.setMinimumSize(new Dimension(8, 8));
		infoEnseignantPanel.add(otherPanel);
		otherPanel.setLayout(new GridLayout(2, 1, 0, 0));
		
		JPanel classePanel = new JPanel();
		otherPanel.add(classePanel);
		classePanel.setLayout(new BorderLayout(0, 0));
		
		JLabel listeClassesLabel = new JLabel("  Classes selectionnées : ");
		listeClassesLabel.setBackground(new Color(255, 255, 255));
		classePanel.add(listeClassesLabel, BorderLayout.WEST);
		
		JPanel selectPanel = new JPanel();
		selectPanel.setBackground(Color.WHITE);
		classePanel.add(selectPanel, BorderLayout.EAST);
		
		ajouterClasse = new JButton("<");
		ajouterClasse.setToolTipText("Ajouter la sélection");
		ajouterClasse.setFocusable(false);
		ajouterClasse.setMinimumSize(new Dimension(43, 20));
		ajouterClasse.setMaximumSize(new Dimension(43, 20));
		ajouterClasse.setPreferredSize(new Dimension(43, 20));
		
		retirerClasse = new JButton(">");
		retirerClasse.setToolTipText("Rétirer la sélection");
		retirerClasse.setFocusable(false);
		retirerClasse.setMaximumSize(new Dimension(43, 20));
		retirerClasse.setMinimumSize(new Dimension(43, 20));
		retirerClasse.setPreferredSize(new Dimension(43, 20));
		
		ajouterClasse.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				AjouterClasse();
				
			}
			
		});
		
		retirerClasse.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				RetirerClasse();
			}
			
		});
		GroupLayout selectPanelLayout = new GroupLayout(selectPanel);
		selectPanelLayout.setHorizontalGroup(
			selectPanelLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(selectPanelLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(selectPanelLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(retirerClasse, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(ajouterClasse, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		selectPanelLayout.setVerticalGroup(
			selectPanelLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(selectPanelLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(ajouterClasse, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
					.addComponent(retirerClasse, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		selectPanel.setLayout(selectPanelLayout);
		
		JScrollPane scrollPaneListeClasses = new JScrollPane();
		scrollPaneListeClasses.setPreferredSize(new Dimension(70, 70));
		scrollPaneListeClasses.setSize(new Dimension(70, 15));
		scrollPaneListeClasses.setMaximumSize(new Dimension(101, 15));
		scrollPaneListeClasses.setMinimumSize(new Dimension(70, 15));
		classePanel.add(scrollPaneListeClasses, BorderLayout.CENTER);
		
		listeClasses = new JList<String>();
		listeClasses.setVisibleRowCount(4);
		scrollPaneListeClasses.setViewportView(listeClasses);
		
		JPanel notificationPanel = new JPanel();
		notificationPanel.setBackground(Color.WHITE);
		otherPanel.add(notificationPanel);
		notificationPanel.setBorder(new TitledBorder(null, "Notification", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		notificationPanel.setLayout(new BorderLayout(0, 0));
		
		JScrollPane notificationScrollPane = new JScrollPane();
		notificationPanel.add(notificationScrollPane, BorderLayout.CENTER);
		
		notification.setFont(new Font("Tahoma", Font.PLAIN, 11));
		notificationScrollPane.setViewportView(notification);
		
		JPanel eastPanel = new JPanel();
		infoEnseignantPanel.add(eastPanel);
		eastPanel.setLayout(new GridLayout(1, 2, 0, 0));
		
		JPanel updateClassePanel = new JPanel();
		updateClassePanel.setBackground(new Color(173, 216, 230));
		eastPanel.add(updateClassePanel);
		updateClassePanel.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel = new JLabel("Liste de toutes les classes");
		updateClassePanel.add(lblNewLabel, BorderLayout.NORTH);
		
		JScrollPane newClassesJScrollPane = new JScrollPane();
		updateClassePanel.add(newClassesJScrollPane);
		
		listClassesOut = new JList<>();
		listClassesOut.setVisibleRowCount(4);
		newClassesJScrollPane.setViewportView(listClassesOut);
		
		JPanel picturePanel = new JPanel();
		picturePanel.setBackground(new Color(173, 216, 230));
		picturePanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		eastPanel.add(picturePanel);
		picturePanel.setLayout(null);
		
		picture = new JLabel("");
		picture.setBounds(21, 5, 88, 88);
		picturePanel.add(picture);
		
		selectPicture = new JButton("Modifier ...");
		selectPicture.setBounds(21, 95, 88, 18);
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
		setDefaultPicture ();
		
		/*
		 * Contrôle de la saisie du champ Libelle et Coéfficient avant activation du bouton de validation 
		 * 
		 * */	
		ArrayList<JTextField> textFields = new ArrayList<>();

		textFields.add(nom);
		textFields.add(prenom);
		textFields.add(telephone);
		textFields.add(email);
		
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
		
		JPanel genrePanel = new JPanel();
		genrePanel.setBackground(new Color(173, 216, 230));
		modelPanel.add(genrePanel);
		FlowLayout genrePanelLayout = (FlowLayout) genrePanel.getLayout();
		genrePanelLayout.setAlignment(FlowLayout.RIGHT);
		genrePanelLayout.setVgap(0);
		
		JLabel genreLabel = new JLabel("Genre :");
		genrePanel.add(genreLabel);
		
		genre = new JTextField();
		genre.setEnabled(false);
		genre.setEditable(false);
		genrePanel.add(genre);
		genre.setColumns(15);
		visualiserEnseignantPanel.setLayout(new BorderLayout(0, 0));
		visualiserEnseignantPanel.add(formPanel);
		
		JPanel panel = new JPanel();
		visualiserEnseignantPanel.add(panel, BorderLayout.SOUTH);
		
		updateButton = new JButton("Update");
		panel.add(updateButton);
		updateButton.setEnabled(false);
		updateButton.setToolTipText("Valider la modification des informations de l'élève");
		updateButton.setMaximumSize(new Dimension(80, 20));
		updateButton.setMinimumSize(new Dimension(80, 20));
		updateButton.setPreferredSize(new Dimension(80, 20));
		
				FilledFieldChecker updateButtonChecker = new FilledFieldChecker(updateButton, textFields);
				JButton annulerButton = new JButton("Quitter");
				panel.add(annulerButton);
				annulerButton.setToolTipText("Quittez ...");
				annulerButton.setMaximumSize(new Dimension(91, 20));
				annulerButton.setMinimumSize(new Dimension(91, 20));
				annulerButton.setPreferredSize(new Dimension(80, 20));
				
				deleteButton = new JButton("Delete");
				panel.add(deleteButton);
				deleteButton.setEnabled(false);
				deleteButton.setToolTipText("Supprimer un élève");
				deleteButton.setPreferredSize(new Dimension(80, 20));
				deleteButton.setMinimumSize(new Dimension(80, 20));
				deleteButton.setMaximumSize(new Dimension(80, 20));
				FilledFieldChecker deleteButtonChecker = new FilledFieldChecker(deleteButton, textFields);
				annulerButton.addActionListener( new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						exitWithConfirmation();
					}
				});

		nom.getDocument().addDocumentListener(updateButtonChecker);
		prenom.getDocument().addDocumentListener(updateButtonChecker);
		telephone.getDocument().addDocumentListener(updateButtonChecker);
		email.getDocument().addDocumentListener(updateButtonChecker);
		nom.getDocument().addDocumentListener(deleteButtonChecker);
		prenom.getDocument().addDocumentListener(deleteButtonChecker);
		telephone.getDocument().addDocumentListener(deleteButtonChecker);
		email.getDocument().addDocumentListener(deleteButtonChecker);
		
		//Utilitaire.center(this, getPreferredSize());
		
	}

	
	public void AjouterClasse() {
		DefaultListModel<String> model;
		model=(DefaultListModel<String>) listeClasses.getModel();

		if(model.indexOf(listClassesOut.getSelectedValue()) == -1)
		{
			model.addElement(listClassesOut.getSelectedValue());			
		}


	}


	public void RetirerClasse() {
		DefaultListModel<String> model;
		model=(DefaultListModel<String>) listeClasses.getModel();
		model.removeElementAt(listeClasses.getSelectedIndex());
	}
	
	
	
	
	@Override
	public String getPath() {
		return path;
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
	public void resetFormUI() {
		this.id.setText("-1");
		this.prenom.setText(null);
		this.nom.setText(null);
		this.telephone.setText(null);
		this.email.setText(null);
		this.login.setText(null);
		this.password.setText(null);
		setDefaultPicture ();
		displayNotification(null);
		this.genre.setText(null);
		clearListeClasses();
		setDefaultPicture();
		candidatureModel.clear();
		clearAllListeClasses();
	}
	
	@Override
	public void loadData(List<Enseignant> enseignants) {
		enseignantModel.loadData(enseignants);
	}
	
	private String getValueListeEnseignantAt(int row, int i) {
		return (String) listeEnseignant.getValueAt(row, i);
	}
	
	private void updateUIEnseignant(Enseignant enseignant) {
		try {
			
			this.id.setText(String.valueOf(enseignant.getId()));
			this.nom.setText(enseignant.getNom());
			this.prenom.setText(enseignant.getPrenom());
			this.genre.setText(enseignant.getGenre());
			this.telephone.setText(enseignant.getTelephone());
			this.email.setText(enseignant.getEmail());
			this.login.setText(enseignant.getLogin());
			this.path = enseignant.getPath();
			if (enseignant.getPath() == null) setDefaultPicture();
			else {
				ImageIcon imageIcon = Utilitaire.getImageIcon (this, path);
				setPicture(imageIcon);
			}
		} catch ( NullPointerException ignored ) {}
	}
	
	private void clearListeClasses() {
		DefaultListModel<String> model = new DefaultListModel<>();
		listeClasses.setModel(model);
	}
	
	private void clearAllListeClasses() {
		DefaultListModel<String> model = new DefaultListModel<>();
		listeClasses.setModel(model);
		listClassesOut.setModel(model);
	}

	
	private void selectAndSetPictureFromBrowser() {
		JFileChooser chooser = new JFileChooser();
        int status = chooser.showOpenDialog(this);
        if (status == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            path = file.getAbsolutePath();
            ImageIcon imageIcon = Utilitaire.getImageIcon (this, path ); 
            setPicture(imageIcon);
        }
	}
	
	private void setDefaultPicture () {
		picture.setIcon(new ImageIcon(DEFAULT_URL_PICTURE));
	}
	
	private void setPicture(ImageIcon imageIcon) {
		picture.setIcon(imageIcon);
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


	public List<Integer> getSelectedIdClasses() {
			ArrayList<Integer> idClasses = new ArrayList<>();
			for ( int i=0;i<listeClasses.getModel().getSize();i++ ) {
				String [] splitInfos = listeClasses.getModel().getElementAt(i).toString().split(Utilitaire.SEPARATEUR);	
				idClasses.add(Integer.parseInt(splitInfos[0]));
				System.out.println(Integer.parseInt(splitInfos[0]));
			}
			return idClasses;
		}	
}