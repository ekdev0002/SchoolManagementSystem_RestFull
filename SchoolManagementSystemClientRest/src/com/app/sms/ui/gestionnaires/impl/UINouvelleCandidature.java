package com.app.sms.ui.gestionnaires.impl;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.xml.bind.JAXBException;

import com.app.sms.exceptions.BadFormatDataException;
import com.app.sms.exceptions.DateOutOfBoundsException;
import com.app.sms.exceptions.NotAvailableDateFormatException;
import com.app.sms.exceptions.WSException;
import com.app.sms.models.Enseignant;
import com.app.sms.models.enums.Genre;
import com.app.sms.ui.gestionnaires.IUINouvelleCandidature;
import com.app.sms.ui.gestionnaires.listeners.FilledFieldChecker;
import com.app.sms.ui.gestionnaires.listeners.SelectDiplomeListener;
import com.app.sms.ui.impl.AbstractUIOperation;
import com.app.sms.utils.DateFormatValidator;
import com.app.sms.utils.Utilitaire;

@SuppressWarnings("serial")
public class UINouvelleCandidature extends AbstractUIOperation implements IUINouvelleCandidature {	
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
	private JRadioButton homme;
	private JRadioButton femme;
	private JButton selectPictureButton;
	private JLabel pictureLabel;
	private String path=null;
	private JTextField birthday;
	private HashMap<Integer,String> diplomesUrl = new HashMap<>();
	private HashMap<Integer,JLabel> diplomesFile = new HashMap<>();
	private HashMap<Integer,JButton> diplomesButton = new HashMap<>();
	private JComboBox<String> responsables;
	ArrayList <JPanel> diplomes;
	private String cvUrl;
	private JCheckBox analysable;
	
	public UINouvelleCandidature() {
		setPreferredSize(new Dimension(750, 485));
		operationIcon.setIcon(new ImageIcon(UINouvelleCandidature.class.getResource("/images/images (1).jpg")));
		operationLabel.setText("Candidatures");
		
		JPanel formPanel = new JPanel();
		formPanel.setBackground(new Color(119, 136, 153));
		centerPanel.add(formPanel, BorderLayout.SOUTH);
		
		JPanel mainPanel = new JPanel();
		mainPanel.setBackground(new Color(173, 216, 230));
		mainPanel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Nouvelle candidature", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		
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
				.addGroup(Alignment.TRAILING, formPanelLayout.createSequentialGroup()
					.addContainerGap(523, Short.MAX_VALUE)
					.addComponent(submitButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(clearButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(cancelButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
				.addGroup(formPanelLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(mainPanel, GroupLayout.PREFERRED_SIZE, 728, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(12, Short.MAX_VALUE))
		);
		formPanelLayout.setVerticalGroup(
			formPanelLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(formPanelLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(mainPanel, GroupLayout.PREFERRED_SIZE, 307, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addGroup(formPanelLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(clearButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(cancelButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(submitButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		
		JLabel idLabel = new JLabel("Id :");
		JLabel nomLabel = new JLabel("Nom :");
		JLabel prenomLabel = new JLabel("Prénom :");
		JLabel emailLabel = new JLabel("Email :");
		JLabel telephoneLabel = new JLabel("Téléphone :");
		JLabel birthdayLabel = new JLabel("Date de Naissance :");
		
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
		
		JPanel notificationPanel = new JPanel();
		notificationPanel.setBackground(new Color(173, 216, 230));
		notificationPanel.setBorder(new TitledBorder(null, "Notification", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JPanel diplomesPanel = new JPanel();
		diplomesPanel.setBackground(new Color(173, 216, 230));
		diplomesPanel.setToolTipText(null);
		diplomesPanel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Dipl\u00F4mes", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		
		pictureLabel = new JLabel("");
		pictureLabel.setHorizontalAlignment(SwingConstants.CENTER);
		pictureLabel.setIcon(new ImageIcon(UINouvelleCandidature.class.getResource("/images/scolarite_ws.png")));
		
		JLabel sexeLabel = new JLabel("Sexe :");
		
		homme = new JRadioButton("H");
		homme.setBackground(new Color(173, 216, 230));
		homme.setSelected(true);
		femme = new JRadioButton("F");
		femme.setBackground(new Color(173, 216, 230));
		ButtonGroup genres = new ButtonGroup();
		genres.add(homme);
		genres.add(femme);
		
		selectPictureButton = new JButton("Select picture");
		selectPictureButton.setToolTipText("Sélectionner la photo du candidat ...");
		selectPictureButton.setMinimumSize(new Dimension(88, 20));
		selectPictureButton.setMaximumSize(new Dimension(88, 20));
		selectPictureButton.setPreferredSize(new Dimension(93, 20));
		selectPictureButton.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
		       selectAndSetPictureFromBrowser ();
			}
		});
		
		JPanel cvPanel = new JPanel();
		cvPanel.setBackground(new Color(173, 216, 230));
		cvPanel.setBorder(new TitledBorder(null, "Curriculum Vitae", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		birthday = new JTextField();
		setDefaultBirthdayDateFormat();
		birthday.setColumns(20);
		birthday.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent arg0) {
				if (birthday.getText().isEmpty()) {
					setDefaultBirthdayDateFormat ();
				}				
			}
			@Override
			public void focusGained(FocusEvent arg0) {
				birthday.setForeground(Color.BLACK);
				birthday.setText(null);
			}
		});
		
		JPanel affectationPanel = new JPanel();
		affectationPanel.setBackground(new Color(173, 216, 230));
		FlowLayout fl_affectationPanel = (FlowLayout) affectationPanel.getLayout();
		fl_affectationPanel.setAlignment(FlowLayout.LEFT);
		affectationPanel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Affectation", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		GroupLayout mainPanelLayout = new GroupLayout(mainPanel);
		mainPanelLayout.setHorizontalGroup(
			mainPanelLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(mainPanelLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(mainPanelLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(mainPanelLayout.createSequentialGroup()
							.addGroup(mainPanelLayout.createParallelGroup(Alignment.TRAILING)
								.addComponent(nomLabel)
								.addComponent(idLabel)
								.addGroup(mainPanelLayout.createParallelGroup(Alignment.LEADING)
									.addComponent(diplomesPanel, GroupLayout.PREFERRED_SIZE, 285, GroupLayout.PREFERRED_SIZE)
									.addComponent(prenomLabel)))
							.addPreferredGap(ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
							.addGroup(mainPanelLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(id, 191, 191, 191)
								.addComponent(nom, 191, 191, 191)
								.addComponent(prenom, 191, 191, 191))
							.addGroup(mainPanelLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(mainPanelLayout.createSequentialGroup()
									.addGap(27)
									.addComponent(emailLabel))
								.addGroup(mainPanelLayout.createSequentialGroup()
									.addGap(10)
									.addGroup(mainPanelLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(birthdayLabel)
										.addComponent(telephoneLabel))))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(mainPanelLayout.createParallelGroup(Alignment.LEADING, false)
								.addComponent(email, 191, 191, Short.MAX_VALUE)
								.addComponent(birthday, 191, 191, Short.MAX_VALUE)
								.addComponent(telephone))
							.addPreferredGap(ComponentPlacement.RELATED, 14, Short.MAX_VALUE))
						.addComponent(cvPanel, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 242, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(sexeLabel)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(mainPanelLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(mainPanelLayout.createParallelGroup(Alignment.TRAILING)
							.addComponent(pictureLabel)
							.addGroup(mainPanelLayout.createSequentialGroup()
								.addComponent(homme)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(femme)
								.addGap(10)))
						.addComponent(selectPictureButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
				.addGroup(mainPanelLayout.createSequentialGroup()
					.addGap(96)
					.addComponent(affectationPanel, GroupLayout.PREFERRED_SIZE, 363, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(257, Short.MAX_VALUE))
				.addGroup(mainPanelLayout.createSequentialGroup()
					.addGap(477)
					.addComponent(notificationPanel, GroupLayout.PREFERRED_SIZE, 220, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(19, Short.MAX_VALUE))
		);
		mainPanelLayout.setVerticalGroup(
			mainPanelLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(mainPanelLayout.createSequentialGroup()
					.addGroup(mainPanelLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(mainPanelLayout.createSequentialGroup()
							.addComponent(pictureLabel, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(selectPictureButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(3)
							.addGroup(mainPanelLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(sexeLabel)
								.addComponent(homme)
								.addComponent(femme)))
						.addGroup(mainPanelLayout.createSequentialGroup()
							.addContainerGap()
							.addGroup(mainPanelLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(id, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(idLabel)
								.addComponent(emailLabel)
								.addComponent(email, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(mainPanelLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(nom, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(nomLabel)
								.addComponent(telephoneLabel)
								.addComponent(telephone, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(mainPanelLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(prenom, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(prenomLabel)
								.addComponent(birthdayLabel)
								.addComponent(birthday, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addGroup(mainPanelLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(cvPanel, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE)
								.addComponent(diplomesPanel, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addComponent(affectationPanel, GroupLayout.PREFERRED_SIZE, 53, GroupLayout.PREFERRED_SIZE)
							.addGap(14)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(notificationPanel, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		notificationPanel.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		notificationPanel.add(scrollPane, BorderLayout.CENTER);
		
		notification.setEditable(false);
		notification.setFont(new Font("Tahoma", Font.PLAIN, 11));
		scrollPane.setViewportView(notification);
		
		notification.setEditable(false);
		scrollPane.setViewportView(notification);
		
		JPanel panel = new JPanel();
		notificationPanel.add(panel, BorderLayout.SOUTH);
		
		JLabel analyseParLabel = new JLabel("A analyser par :");
		affectationPanel.add(analyseParLabel);
		
		responsables = new JComboBox<>();
		responsables.setEnabled(false);
		responsables.addItem("Choisissez un enseignant ...");
		responsables.setToolTipText("Cliquez sur le bouton radio pour activer ...");
				
		responsables.setPreferredSize(new Dimension(220, 22));
		affectationPanel.add(responsables);
		
		analysable = new JCheckBox("");
		affectationPanel.add(analysable);
		analysable.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (analysable.isSelected()) {
					responsables.setEnabled(true);
					try {
						for ( Enseignant enseignant : Enseignant.list() ) {
							responsables.addItem( enseignant.getId() + Utilitaire.SEPARATEUR + enseignant.getNom() + " " + enseignant.getPrenom());
						}
					} catch (WSException | IOException | JAXBException e) {
						// TODO Auto-generated catch block
						displayErrorMessage(e.getMessage());
					}
				} else {
					responsables.removeAllItems();
					responsables.addItem("Choisissez un enseignant ...");
					responsables.setEnabled(false);
				}
			}
		});
		cvPanel.setLayout(new GridLayout(2, 1, 0, 0));
		
		JButton ajouterCVButton = new JButton("Ajouter un CV");
		cvPanel.add(ajouterCVButton);
		ajouterCVButton.setMaximumSize(new Dimension(101, 20));
		ajouterCVButton.setMinimumSize(new Dimension(101, 20));
		ajouterCVButton.setPreferredSize(new Dimension(101, 20));
		ajouterCVButton.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ajouterCV ();
			}
		});
		diplomesPanel.setLayout(new GridLayout(5, 1, 0, 0));
		
		diplomes = new ArrayList<>();
		
		for ( int i = 0 ; i < 5 ; i ++ ) {
			JPanel diplome = new JPanel();
			FlowLayout diplomeFlowLayout = (FlowLayout) diplome.getLayout();
			diplomeFlowLayout.setVgap(2);
			diplomeFlowLayout.setAlignment(FlowLayout.LEFT);
			
			JButton addButton = new JButton("+");
			addButton.setToolTipText("Ajouter un diplome (.doc,.pdf,.jpg, ...)");
			addButton.setMinimumSize(new Dimension(43, 20));
			addButton.setMaximumSize(new Dimension(43, 20));
			addButton.setPreferredSize(new Dimension(43, 20));
			addButton.addActionListener(new SelectDiplomeListener (this, i));
			JLabel diplomeUrlLabel = new JLabel("");
			diplomeUrlLabel.setForeground(Color.BLUE);
			diplomeUrlLabel.setFont(new Font("Tahoma", Font.ITALIC, 10));
			diplomesFile.put(i, diplomeUrlLabel);
			diplomesButton.put(i, addButton);
			
			diplome.add(addButton);
			
			diplome.add(diplomeUrlLabel);
			diplomes.add (diplome);
			diplomesPanel.add(diplome);
		}
		
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

	private void setDefaultBirthdayDateFormat() {
		birthday.setText("JJ-MM-AAAA");
		birthday.setForeground(Color.LIGHT_GRAY);
	}

	private void ajouterCV() {
		JFileChooser chooser = new JFileChooser();
        int status = chooser.showOpenDialog(this);
        if (status == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            cvUrl = file.getAbsolutePath();            
            AbstractButton pathCVLabel = null;
			if (file.getName().length() > Utilitaire.MAX_SIZE_FILENAME) {
            	String cvFileName = file.getName();
            	cvFileName = cvFileName.substring(0, Utilitaire.MAX_SIZE_FILENAME - 3) + "...";
            	pathCVLabel.setText(cvFileName);
            } else {
            	pathCVLabel.setText(file.getName());
            }
        }		
	}
	
	private void setPicture(ImageIcon imageIcon) {
		pictureLabel.setIcon(imageIcon);
	}
	
	private void setPicture(URL resource) {
		setPicture(new ImageIcon(resource));
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
	public void resetUI() {
		this.id.setText("-1");
		this.nom.setText(null);
		this.prenom.setText(null);
		this.telephone.setText(null);
		this.email.setText(null);
		displayNotification(null);
		setPicture(UINouvelleCandidature.class.getResource("/images/inconnu.png"));
		
		// diplomesUrl
		Set<Entry<Integer, String>> setUrl = diplomesUrl.entrySet();
		Iterator<Entry<Integer, String>> itUrl = setUrl.iterator();
		while (itUrl.hasNext()) {
			itUrl.next().setValue(null);
		}
		// diplomesFile
		Set<Entry<Integer, JLabel>> setFile = diplomesFile.entrySet();
		Iterator<Entry<Integer, JLabel>> itFile = setFile.iterator();
		while (itFile.hasNext()) {
			itFile.next().getValue().setText(null);
		}
		// diplomesButton
		Set<Entry<Integer, JButton>> setButton = diplomesButton.entrySet();
		Iterator<Entry<Integer, JButton>> itButton = setButton.iterator();
		while (itButton.hasNext()) {
			itButton.next().getValue().setText("+");
		}
		
		AbstractButton pathCVLabel = null;
		pathCVLabel.setText(null);
		setDefaultBirthdayDateFormat();
		
		responsables.removeAllItems();
		responsables.setEnabled(false);
		responsables.addItem("Choisissez un enseignant ...");
		analysable.setSelected(false);
	}
	
	@Override
	public void addSubmitListener(ActionListener listenForSubmitButton) {
		submitButton.addActionListener(listenForSubmitButton);
	}
	
	public void addAnalysableListener(ActionListener listenForAnalysableCheckBox) {
		analysable.addActionListener(listenForAnalysableCheckBox);
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
	public void addSelectPictureListener(ActionListener listenerForSelectPictureButton) {
		selectPictureButton.addActionListener(listenerForSelectPictureButton);
	}

	@Override
	public String getGenre () {
		if (homme.isSelected()) return Genre.Homme.name() ;
		else return Genre.Femme.name() ;
	}
	
	@Override
	public String getPath() {
		return path;
	}
	
	@Override
	public String getBirthday() throws BadFormatDataException, NumberFormatException, DateOutOfBoundsException, NotAvailableDateFormatException {
		if (DateFormatValidator.validate(birthday.getText(), DateFormatValidator.DateFormat.JJ_MM_AAAA)) {
			return birthday.getText();
		} else return null;
	}
	
	@Override
	public String getCVUrl() {
		return cvUrl;
	}
	
	@Override
	public List<String> getDiplomes() {
		List<String> urls = new ArrayList<>();
		Set<Entry<Integer, String>> setUrl = diplomesUrl.entrySet();
		Iterator<Entry<Integer, String>> itUrl = setUrl.iterator();
		while (itUrl.hasNext()) {
			urls.add(itUrl.next().getValue());
		}
		return urls;
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
	
	public void setButtonAt (int index, String text) {
		diplomesButton.get(index).setText(text);
	}
	
	public void addDiplomesUrl(Integer key, String absolutePath) {
		diplomesUrl.put(key, absolutePath);
	}
	
	public void removeDiplomesUrlAt(int index) {
		diplomesUrl.remove(index);
	}
	
	public void setDiplomesFileAt(int index, String texte) {
		if ( texte.length() > Utilitaire.MAX_SIZE_FILENAME ) {
			texte = texte.substring(0, Utilitaire.MAX_SIZE_FILENAME - 3) + "...";
		}
		diplomesFile.get(index).setText(texte);
	}
}