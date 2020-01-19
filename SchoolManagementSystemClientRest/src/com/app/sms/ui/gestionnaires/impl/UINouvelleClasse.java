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
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import com.app.sms.ui.gestionnaires.IUINouvelleClasse;
import com.app.sms.ui.gestionnaires.listeners.FilledFieldChecker;
import com.app.sms.ui.impl.AbstractUIOperation;

public class UINouvelleClasse extends AbstractUIOperation implements IUINouvelleClasse {
	private JTextField libelle;
	private JTextArea description;
	/**
	 * 
	 * Les éléments du contrôleur ...
	 */
	private JButton submitButton;
	
	public UINouvelleClasse() {
		setPreferredSize(new Dimension(800, 492));
		operationIcon.setIcon(new ImageIcon(UINouvelleClasse.class.getResource("/images/1095901-parcourssup-amphijpg.jpg")));
		operationLabel.setText("Classes");
		
		JPanel formPanel = new JPanel();
		formPanel.setBackground(new Color(119, 136, 153));
		centerPanel.add(formPanel, BorderLayout.CENTER);
		
		JPanel mainPanel = new JPanel();
		mainPanel.setBackground(new Color(173, 216, 230));
		mainPanel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Nouvelle classe", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		
		submitButton = new JButton("Submit");
		submitButton.setEnabled(false);
		
		JButton clearButton = new JButton("Clear");
		clearButton.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				resetUI ();
			}
		});
		JButton cancelButton = new JButton("Quitter");
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
					.addContainerGap()
					.addGroup(formPanelLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(mainPanel, GroupLayout.PREFERRED_SIZE, 774, Short.MAX_VALUE)
						.addGroup(formPanelLayout.createSequentialGroup()
							.addComponent(submitButton)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(clearButton)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(cancelButton)))
					.addContainerGap())
		);
		formPanelLayout.setVerticalGroup(
			formPanelLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(formPanelLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(mainPanel, GroupLayout.PREFERRED_SIZE, 319, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(formPanelLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(cancelButton)
						.addComponent(clearButton)
						.addComponent(submitButton))
					.addContainerGap())
		);
		
		JLabel libelleLabel = new JLabel("Libellé :");
		
		libelle = new JTextField();
		libelle.setColumns(10);
		
		JLabel descriptionLabel = new JLabel("Description :");
		
		JScrollPane scrollPane = new JScrollPane();
		
		JPanel notificationPanel = new JPanel();
		notificationPanel.setBackground(new Color(173, 216, 230));
		notificationPanel.setBorder(new TitledBorder(null, "Notification", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GroupLayout mainPanelLayout = new GroupLayout(mainPanel);
		mainPanelLayout.setHorizontalGroup(
			mainPanelLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(mainPanelLayout.createSequentialGroup()
					.addGroup(mainPanelLayout.createParallelGroup(Alignment.LEADING, false)
						.addGroup(mainPanelLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(notificationPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addGroup(mainPanelLayout.createSequentialGroup()
							.addGap(64)
							.addComponent(libelleLabel)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(libelle, GroupLayout.PREFERRED_SIZE, 232, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(descriptionLabel)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 293, GroupLayout.PREFERRED_SIZE)))
					.addGap(45))
		);
		mainPanelLayout.setVerticalGroup(
			mainPanelLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(mainPanelLayout.createSequentialGroup()
					.addGroup(mainPanelLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(mainPanelLayout.createSequentialGroup()
							.addGap(36)
							.addGroup(mainPanelLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(libelleLabel)
								.addComponent(libelle, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(descriptionLabel)))
						.addGroup(mainPanelLayout.createSequentialGroup()
							.addGap(28)
							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)))
					.addGap(67)
					.addComponent(notificationPanel, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		notificationPanel.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPaneNotification = new JScrollPane();
		notificationPanel.add(scrollPaneNotification, BorderLayout.CENTER);
		
		notification.setFont(new Font("Tahoma", Font.PLAIN, 11));
		scrollPaneNotification.setViewportView(notification);
		
		description = new JTextArea();
		description.setFont(new Font("Tahoma", Font.PLAIN, 11));
		scrollPane.setViewportView(description);
		mainPanel.setLayout(mainPanelLayout);
		formPanel.setLayout(formPanelLayout);
		
		/*
		 * Contrôle de la saisie du champ Libelle et Coéfficient avant activation du bouton de validation 
		 * 
		 * */	
		ArrayList<JTextField> textFields = new ArrayList<>();
		textFields.add(libelle);
		FilledFieldChecker submitButtonChecker = new FilledFieldChecker(submitButton, textFields);
		libelle.getDocument().addDocumentListener(submitButtonChecker);
	}

	@Override
	public void resetUI() {
		notification.setText(null);
		libelle.setText(null);
		description.setText(null);
	}

	@Override
	public void addSubmitListener(ActionListener listenForSubmitButton) {
		submitButton.addActionListener(listenForSubmitButton);
	}

	@Override
	public String getLibelle() {
		return libelle.getText();
	}

	@Override
	public String getDescription() {
		return description.getText();
	}
}