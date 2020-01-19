package com.app.sms.ui.gestionnaires.impl;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import com.app.sms.ui.gestionnaires.IUINouveauGestionnaire;
import com.app.sms.ui.gestionnaires.listeners.FilledFieldChecker;
import com.app.sms.ui.impl.AbstractUIOperation;

@SuppressWarnings("serial")
public class UINouveauGestionnaire extends AbstractUIOperation implements IUINouveauGestionnaire {
	private JTextField nom;
	private JTextField prenom;
	private JTextField email;
	private JTextField telephone;
	private JTextField id;
	private JTextField login;
	private JPasswordField password;
	private JTextField dateDeNaissance;
	private JTextField adresse;
	private JButton submit;
	private JButton clear;
	private JButton cancel;
	public UINouveauGestionnaire() {
		operationIcon.setIcon(new ImageIcon(UINouveauGestionnaire.class.getResource("/images/téléchargement.jpg")));
		operationLabel.setText("Gestionnaire");
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(105, 105, 105));
		centerPanel.add(panel, BorderLayout.CENTER);
		
		JPanel authentificationPanel = new JPanel();
		authentificationPanel.setBackground(new Color(173, 216, 230));
		authentificationPanel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Nouvel enseignant", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		
		JLabel lblAdresse = new JLabel("Adresse :");
		
		JLabel label_1 = new JLabel("Téléphone :");
		
		JLabel label_2 = new JLabel("Email :");
		
		JLabel label_3 = new JLabel("Prénom :");
		
		JLabel label_4 = new JLabel("Nom :");
		
		JLabel label_5 = new JLabel("Id :");
		
		nom = new JTextField();
		nom.setColumns(10);
		
		prenom = new JTextField();
		prenom.setColumns(10);
		
		email = new JTextField();
		email.setColumns(10);
		
		telephone = new JTextField();
		telephone.setColumns(10);
		
		id = new JTextField();
		id.setText("-1");
		id.setEnabled(false);
		id.setEditable(false);
		id.setColumns(10);
		
		JLabel label_9 = new JLabel("");
		
		JPanel notificationPanel = new JPanel();
		notificationPanel.setBackground(new Color(173, 216, 230));
		notificationPanel.setBorder(new TitledBorder(null, "Notification", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		notificationPanel.setLayout(new BorderLayout(0, 0));
		
		JLabel lblDatenaiss = new JLabel("DateNaiss :");
		
		dateDeNaissance = new JTextField();
		dateDeNaissance.setColumns(10);
		
		adresse = new JTextField();
		adresse.setColumns(10);
		
		JLabel label_7 = new JLabel("Login :");
		
		login = new JTextField();
		login.setColumns(20);
		
		JLabel label_8 = new JLabel("Password :");
		label_8.setBackground(new Color(102, 153, 255));
		
		password = new JPasswordField();
		password.setColumns(20);
		
		JLabel lblSexe = new JLabel("Sexe:");
		
		JRadioButton rdbtnNewRadioButton = new JRadioButton("Femme");
		rdbtnNewRadioButton.setBackground(new Color(173, 216, 230));
		
		JRadioButton rdbtnHomme = new JRadioButton("Homme");
		rdbtnHomme.setBackground(new Color(173, 216, 230));
		rdbtnHomme.setSelected(true);
		GroupLayout gl_panel_1 = new GroupLayout(authentificationPanel);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGap(12)
							.addGroup(gl_panel_1.createParallelGroup(Alignment.TRAILING)
								.addComponent(label_2)
								.addComponent(label_3)
								.addComponent(label_4)
								.addComponent(label_5)
								.addComponent(lblDatenaiss))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING, false)
								.addComponent(email)
								.addComponent(dateDeNaissance)
								.addComponent(prenom)
								.addComponent(nom)
								.addComponent(id, GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel_1.createSequentialGroup()
									.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING, false)
										.addGroup(gl_panel_1.createSequentialGroup()
											.addComponent(label_7)
											.addGap(29)
											.addComponent(login, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
										.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
											.addGroup(gl_panel_1.createSequentialGroup()
												.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
													.addComponent(lblAdresse)
													.addComponent(label_1))
												.addPreferredGap(ComponentPlacement.UNRELATED)
												.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
													.addComponent(telephone, GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE)
													.addComponent(adresse)
													.addGroup(gl_panel_1.createSequentialGroup()
														.addComponent(rdbtnHomme, GroupLayout.PREFERRED_SIZE, 66, GroupLayout.PREFERRED_SIZE)
														.addPreferredGap(ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
														.addComponent(rdbtnNewRadioButton, GroupLayout.PREFERRED_SIZE, 66, GroupLayout.PREFERRED_SIZE))))
											.addGroup(gl_panel_1.createSequentialGroup()
												.addComponent(label_8)
												.addPreferredGap(ComponentPlacement.UNRELATED)
												.addComponent(password, GroupLayout.PREFERRED_SIZE, 167, GroupLayout.PREFERRED_SIZE))))
									.addGap(18)
									.addComponent(label_9))
								.addComponent(lblSexe))
							.addGap(14))
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGap(45)
							.addComponent(notificationPanel, GroupLayout.DEFAULT_SIZE, 659, Short.MAX_VALUE)))
					.addGap(36))
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addComponent(label_9, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
								.addComponent(id, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(label_5)
								.addComponent(label_7)
								.addComponent(login, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
								.addComponent(nom, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(label_4)
								.addComponent(label_8)
								.addComponent(password, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
								.addComponent(prenom, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(label_3)
								.addComponent(label_1)
								.addComponent(telephone, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblDatenaiss)
								.addComponent(dateDeNaissance, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblAdresse)
								.addComponent(adresse, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
								.addComponent(email, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(label_2)
								.addComponent(lblSexe)
								.addComponent(rdbtnHomme)
								.addComponent(rdbtnNewRadioButton))))
					.addPreferredGap(ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
					.addComponent(notificationPanel, GroupLayout.PREFERRED_SIZE, 99, GroupLayout.PREFERRED_SIZE)
					.addGap(35))
		);
		
		JScrollPane scrollPaneNotification = new JScrollPane();
		notificationPanel.add(scrollPaneNotification, BorderLayout.CENTER);
		authentificationPanel.setLayout(gl_panel_1);
		
		
		submit = new JButton("Submit");
		submit.setPreferredSize(new Dimension(67, 20));
		submit.setMinimumSize(new Dimension(67, 20));
		submit.setMaximumSize(new Dimension(67, 20));
		submit.setEnabled(false);
		
		clear = new JButton("Clear");
		clear.setPreferredSize(new Dimension(59, 20));
		clear.setMinimumSize(new Dimension(59, 20));
		clear.setMaximumSize(new Dimension(59, 20));
		clear.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				resetUI ();
				
			}
		});
		
		cancel = new JButton("Cancel");
		cancel.setPreferredSize(new Dimension(67, 20));
		cancel.setMinimumSize(new Dimension(67, 20));
		cancel.setMaximumSize(new Dimension(67, 20));
		cancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				exitWithConfirmation();
				
			}
		});
		
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap(650, Short.MAX_VALUE)
					.addComponent(submit, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(clear, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(cancel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(authentificationPanel, GroupLayout.PREFERRED_SIZE, 752, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(93, Short.MAX_VALUE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(authentificationPanel, GroupLayout.PREFERRED_SIZE, 319, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(submit, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(clear, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(cancel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		panel.setLayout(gl_panel);
		
		
		notification.setFont(new Font("Tahoma", Font.PLAIN, 11));
		scrollPaneNotification.setViewportView(notification);
		
		ArrayList<JTextField> textFields = new ArrayList<>();
		textFields.add(nom);
		textFields.add(prenom);
		textFields.add(email);
		textFields.add(telephone);
		textFields.add(dateDeNaissance);
		FilledFieldChecker submitButtonChecker = new FilledFieldChecker(submit, textFields);
		nom.getDocument().addDocumentListener(submitButtonChecker);
		prenom.getDocument().addDocumentListener(submitButtonChecker);
		email.getDocument().addDocumentListener(submitButtonChecker);
		telephone.getDocument().addDocumentListener(submitButtonChecker);
		dateDeNaissance.getDocument().addDocumentListener(submitButtonChecker);
	}

	@Override
	public void addSubmitListener(ActionListener listenForSubmitButton) {
		
		submit.addActionListener(listenForSubmitButton);
	}

	@Override
	public void addCreerListener(ActionListener listenForCreerButton) {
		

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
	public String getDateNaissance() {
		// TODO Auto-generated method stub
		return dateDeNaissance.getText();
	}

	@Override
	public String getAdresse() {
		// TODO Auto-generated method stub
		return adresse.getText();
	}
	
	@Override
	public void addSelectPictureListener(ActionListener listenerForSelectPictureButton) {
		// TODO Auto-generated method stub

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
		this.dateDeNaissance.setText(null);
		this.adresse.setText(null);
		displayNotification(null);
	}
}
