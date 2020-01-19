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
import com.app.sms.models.Enseignant;
import com.app.sms.models.enums.State;
import com.app.sms.ui.gestionnaires.IUConsulterCandidature;
import com.app.sms.ui.gestionnaires.listeners.FilledFieldChecker;
import com.app.sms.ui.impl.AbstractUIOperation;
import com.app.sms.utils.Utilitaire;

public class UIConsulterCandidature extends AbstractUIOperation implements IUConsulterCandidature {
	
	private static final URL DEFAULT_URL_PICTURE = UIConsulterEnseignant.class.getResource("/images/inconnu.png");
	private JTable listeCandidature;
	private JTextField id;
	private JTextField nom;
	private JTextField prenom;
	private JTextField telephone;
	private JTextField email;
	private JTextField status;
	private JButton updateButton;
	private JLabel picture;
	private JButton selectPicture;
	private JButton deleteButton;
	private String path;
	private JTextField genre;
	private JCheckBox valider;
	private JLabel commentaires;
	private CandidatureModel candidatureModel;
	
	private JTextField affectedTo;
	
	private JComboBox<String> responsables;

	private JPanel reaffectationPanel;

	public UIConsulterCandidature() {
		centerPanel.setBackground(Color.GRAY);

		setMinimumSize(new Dimension(834, 590));
		setSize(new Dimension(834, 590));
		setPreferredSize(new Dimension(834, 590));
		operationIcon.setIcon(new ImageIcon(UIConsulterCandidature.class.getResource("/images/images (1).jpg")));
		operationLabel.setText("Candidatures");
		
		JPanel mainPanel = new JPanel();
		mainPanel.setBackground(Color.GRAY);
		mainPanel.setPreferredSize(new Dimension(8, 8));
		centerPanel.add(mainPanel, BorderLayout.CENTER);
		mainPanel.setLayout(new GridLayout(2, 1, 0, 0));
		
		JPanel listeCandidaturePanel = new JPanel();
		listeCandidaturePanel.setBackground(SystemColor.inactiveCaptionBorder);
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
					String [] data = getCandidatureDataByRow(selectedRow);
					updateUIForm (data);
				}
			}
		});
		
		JPanel visuliserCandidaturePanel = new JPanel();
		visuliserCandidaturePanel.setBackground(Color.LIGHT_GRAY);
		visuliserCandidaturePanel.setBorder(new TitledBorder(null, "Modifier un \u00E9l\u00E8ve", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		mainPanel.add(visuliserCandidaturePanel);
		
		JPanel formPanel = new JPanel();
		
		JPanel buttonPanelLeft = new JPanel();
		buttonPanelLeft.setBackground(Color.WHITE);
		buttonPanelLeft.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "...", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		
		updateButton = new JButton("Update");
		updateButton.setEnabled(false);
		updateButton.setBounds(27, 16, 80, 20);
		updateButton.setToolTipText("Valider la modification des informations de l'élève");
		updateButton.setMaximumSize(new Dimension(80, 20));
		updateButton.setMinimumSize(new Dimension(80, 20));
		updateButton.setPreferredSize(new Dimension(80, 20));
		JButton annulerButton = new JButton("Quitter");
		annulerButton.setBounds(163, 16, 80, 20);
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
		notificationPanel.setBackground(Color.WHITE);
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
		
		JPanel modelPanel = new JPanel();
		infoCandidaturePanel.add(modelPanel);
		modelPanel.setLayout(new GridLayout(5, 1, 0, 0));
		
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
		telephoneLabel.setBackground(new Color(173, 216, 230));
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
		infoCandidaturePanel.add(otherPanel);
		otherPanel.setLayout(new GridLayout(5, 1, 0, 0));
		
		JPanel loginPanel = new JPanel();
		loginPanel.setBackground(new Color(173, 216, 230));
		otherPanel.add(loginPanel);
		loginPanel.setLayout(new GridLayout(0, 3, 0, 0));
		
		JLabel statusDossierLabel = new JLabel("Status :");
		loginPanel.add(statusDossierLabel);
	
		status = new JTextField();
		status.setEnabled(false);
		status.setEditable(false);
		loginPanel.add(status);
		status.setColumns(15);
		
		valider = new JCheckBox("Valider");
		valider.setToolTipText("Valider le verdict de l'enseignant et clôturer le dossier");
		loginPanel.add(valider);
		
		JPanel affectationPanel = new JPanel();
		affectationPanel.setBackground(new Color(173, 216, 230));
		FlowLayout affectationPanelLayout = (FlowLayout) affectationPanel.getLayout();
		affectationPanelLayout.setAlignment(FlowLayout.RIGHT);
		affectationPanelLayout.setVgap(0);
		otherPanel.add(affectationPanel);
		
		JLabel affectedToLabel = new JLabel("Affecté à :");
		affectationPanel.add(affectedToLabel);
		
		affectedTo = new JTextField();
		affectationPanel.add(affectedTo);
		affectedTo.setEnabled(false);
		affectedTo.setEditable(false);
		affectedTo.setColumns(15);
		
		reaffectationPanel = new JPanel();
		reaffectationPanel.setBackground(new Color(173, 216, 230));
		FlowLayout fl_reaffectationPanel = (FlowLayout) reaffectationPanel.getLayout();
		fl_reaffectationPanel.setVgap(0);
		fl_reaffectationPanel.setAlignment(FlowLayout.RIGHT);
		otherPanel.add(reaffectationPanel);
		
		JLabel reaffecterLabel = new JLabel("Réaffecter à :");
		reaffectationPanel.add(reaffecterLabel);

		responsables = new JComboBox<>();
		responsables.setToolTipText("Réaffecter le dossier à un autre enseignant ...");
		responsables.setSize(new Dimension(182, 30));
		responsables.setPreferredSize(new Dimension(182, 20));
		responsables.setMinimumSize(new Dimension(180, 20));
		
		reaffectationPanel.add(responsables);
		
		JPanel genrePanel = new JPanel();
		genrePanel.setBackground(new Color(173, 216, 230));
		FlowLayout genrePanelLayout = (FlowLayout) genrePanel.getLayout();
		genrePanelLayout.setAlignment(FlowLayout.RIGHT);
		genrePanelLayout.setVgap(0);
		otherPanel.add(genrePanel);
		
		JLabel genreLabel = new JLabel("Genre :");
		genrePanel.add(genreLabel);
		
		genre = new JTextField();
		genre.setEditable(false);
		genre.setEnabled(false);
		genrePanel.add(genre);
		genre.setColumns(10);
		
		JPanel commentairesPanel = new JPanel();
		commentairesPanel.setBackground(new Color(173, 216, 230));
		FlowLayout commentairesPanelLayout = (FlowLayout) commentairesPanel.getLayout();
		commentairesPanelLayout.setVgap(3);
		otherPanel.add(commentairesPanel);
		
		commentaires = new JLabel("Visualiser le commentaire par survol de cette zone");
		commentaires.setForeground(Color.RED);
		commentaires.setFont(new Font("Tahoma", Font.BOLD, 10));
		commentairesPanel.add(commentaires);
		
		JPanel picturePanel = new JPanel();
		picturePanel.setBackground(new Color(173, 216, 230));
		picturePanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		infoCandidaturePanel.add(picturePanel);
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
		visuliserCandidaturePanel.setLayout(visuliserCandidaturePanelLayout);
		
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
			this.status.setText(data[6]);
			
			if(data[6].equals(State.CLOSE.toString())) {
				this.valider.setSelected(true);
			}
			else
			{
				this.valider.setSelected(false);
			}
		
			
			if (data[7] == null) this.commentaires.setToolTipText("Aucun commentaire pour l'instant sur ce dossier ...");
			else this.commentaires.setToolTipText(data[7]);
			this.setPath(data[8]);
			if (data[8] == null) setDefaultPicture();
			else {
				ImageIcon imageIcon = Utilitaire.getImageIcon (this, path);
				setPicture(imageIcon);
			}
			
			
			this.setEnseignant(data[9]);
			int position=1;
			int i=0;
			
			//responsables.removeAllItems();
			
			if(responsables.getItemCount()==0)
			{
			for ( Enseignant enseignant : Enseignant.list() ) {
				responsables.addItem( enseignant.getId() + Utilitaire.SEPARATEUR + enseignant.getNom() + " " + enseignant.getPrenom());

				if(data[0].equals(String.valueOf(enseignant.getId())))
				{
					position=i;
				}
				//System.out.println("i="+i+" position="+position+" "+ enseignant.getNom() );
				
				i++;
				
			}
			//responsables.setSelectedIndex(position);
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
	public void addGoListener(ActionListener listenerForGoButton) {
		
	}
	
	@Override
	public String getNom() {
		return nom.getText();
	}
	
	@Override
	public String getPrenom() {
		return prenom.getText();
	}

	private void setPrenom(String prenom) {
		this.prenom.setText(prenom);
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
		return status.getText();
	}
	
	@Override
	public String getGenre() {
		return genre.getText();
	}

	@Override
	public String getState() {
		
		if(valider.isSelected())
		{
			return State.CLOSE.toString();
		}
		
		if(status.getText().contentEquals(State.CLOSE.toString()))
		{
			return State.OUVERTE.toString();
		}
		
		return status.getText();
	}
	
	@Override
	public int getSelectedEnseignantId () {
		if ( responsables.isEnabled() ) {
			String selectedEnseignant = responsables.getItemAt(responsables.getSelectedIndex());
			String [] splitInfos = selectedEnseignant.split(Utilitaire.SEPARATEUR);
			return Integer.parseInt(splitInfos[0]);
		}
		return -1;
	}

	
	@Override
	public void resetFormUI() {
		this.setId("-1");
		this.setPrenom(null);
		this.setNom(null);
		this.setTelephone(null);
		this.setEmail(null);
		this.status.setText(null);
		setDefaultPicture ();
		displayNotification(null);
		setDefaultPicture();
	}
	
	@Override
	public void loadData(List<Candidature> candidatures) {
		try {
			candidatureModel.loadData(candidatures);
		} catch (WSException | IOException | JAXBException e) {
			// TODO Auto-generated catch block
			displayErrorMessage(e.getMessage());
		}
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
	
	private void setId(String id) {
		this.id.setText(id);
	}
	
	private void setDefaultPicture () {
		picture.setIcon(new ImageIcon(DEFAULT_URL_PICTURE));
	}
	
	private void setNom(String nom) {
		this.nom.setText(nom);
	}
	
	private void setTelephone(String telephone) {
		this.telephone.setText(telephone);
	}
	
	private void setEmail(String email) {
		this.email.setText(email);
	}
	
	private void setGenre(String genre) {
		this.genre.setText(genre);
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

	public String getCommentaire() {
		// TODO Auto-generated method stub
		return commentaires.getToolTipText();
		}

}
