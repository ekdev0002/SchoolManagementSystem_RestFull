package com.app.sms.ui.gestionnaires.impl;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.SystemColor;
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
import javax.swing.border.TitledBorder;

import com.app.sms.exceptions.BadFormatDataException;
import com.app.sms.ui.gestionnaires.IUINouveauModule;
import com.app.sms.ui.gestionnaires.listeners.FilledFieldChecker;
import com.app.sms.ui.impl.AbstractUIOperation;

public class UINouveauModule extends AbstractUIOperation implements IUINouveauModule {
	
	/**
	 * Les éléments du modèle ...
	 **/
	private JTextField coefficient;
	private JTextField libelle;
	private JTextArea description;
	
	/**
	 * 
	 * Les éléments du contrôleur ...
	 */
	private JButton submitButton;
	
	public UINouveauModule() {
		setBackground(new Color(102, 153, 255));
		setPreferredSize(new Dimension(800, 492));
		operationIcon.setIcon(new ImageIcon(UINouveauModule.class.getResource("/images/86b97956377c679f1b4a3c597358036f.jpg")));
		operationLabel.setText("Modules");
		
		JPanel formPanel = new JPanel();
		formPanel.setBackground(new Color(119, 136, 153));
		centerPanel.add(formPanel, BorderLayout.CENTER);
		
		JPanel mainPanel = new JPanel();
		mainPanel.setBackground(SystemColor.inactiveCaption);
		mainPanel.setBorder(new TitledBorder(null, "Nouveau module", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
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
					.addGroup(formPanelLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(mainPanel, GroupLayout.DEFAULT_SIZE, 774, Short.MAX_VALUE)
						.addGroup(Alignment.TRAILING, formPanelLayout.createSequentialGroup()
							.addComponent(submitButton)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(clearButton)
							.addPreferredGap(ComponentPlacement.RELATED)
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
						.addComponent(submitButton)
						.addComponent(clearButton)
						.addComponent(cancelButton))
					.addContainerGap())
		);
		
		JLabel coefficientLabel = new JLabel("Coefficient :");

		coefficient = new JTextField();
		coefficient.setColumns(10);
		
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
					.addGap(41)
					.addComponent(coefficientLabel)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(coefficient, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(libelleLabel)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(libelle, 120, 120, 120)
					.addGap(18)
					.addComponent(descriptionLabel)
					.addGap(42)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 204, GroupLayout.PREFERRED_SIZE)
					.addGap(30))
				.addGroup(mainPanelLayout.createSequentialGroup()
					.addComponent(notificationPanel, GroupLayout.DEFAULT_SIZE, 742, Short.MAX_VALUE)
					.addGap(20))
		);
		mainPanelLayout.setVerticalGroup(
			mainPanelLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(mainPanelLayout.createSequentialGroup()
					.addGroup(mainPanelLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(mainPanelLayout.createSequentialGroup()
							.addContainerGap()
							.addGroup(mainPanelLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(coefficient, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(coefficientLabel)
								.addComponent(libelleLabel)
								.addComponent(libelle, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(descriptionLabel)))
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 79, GroupLayout.PREFERRED_SIZE))
					.addGap(35)
					.addComponent(notificationPanel, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(86, Short.MAX_VALUE))
		);
		notificationPanel.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane_1 = new JScrollPane();
		notificationPanel.add(scrollPane_1, BorderLayout.CENTER);
		
		notification.setFont(new Font("Tahoma", Font.PLAIN, 11));
		scrollPane_1.setViewportView(notification);
		
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
		textFields.add(coefficient);
		textFields.add(libelle);
		FilledFieldChecker submitButtonChecker = new FilledFieldChecker(submitButton, textFields);
		coefficient.getDocument().addDocumentListener(submitButtonChecker);
		libelle.getDocument().addDocumentListener(submitButtonChecker);
	}
	
	@Override
	public void resetUI() {
		notification.setText(null);
		libelle.setText(null);
		coefficient.setText(null);
		description.setText(null);
	}
	
	@Override
	public void addSubmitListener(ActionListener listenForSubmitButton) {
		submitButton.addActionListener(listenForSubmitButton);
	}
	
	@Override
	public int getCoefficient() throws BadFormatDataException  {
		try {
			return Integer.parseInt(this.coefficient.getText());
		} catch (NumberFormatException exception) {
			throw new BadFormatDataException ( "Error in method getCoefficient " + exception.getMessage()) ;
		}
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
