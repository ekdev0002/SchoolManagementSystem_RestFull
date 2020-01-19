package com.app.sms.ui.gestionnaires.impl;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.xml.bind.JAXBException;

import com.app.sms.exceptions.WSException;
import com.app.sms.models.Classe;
import com.app.sms.models.Enseignant;
import com.app.sms.models.Module;
import com.app.sms.models.ModuleModel;
import com.app.sms.ui.gestionnaires.IUConsulterModule;
import com.app.sms.ui.gestionnaires.listeners.FilledFieldChecker;
import com.app.sms.ui.impl.AbstractUIOperation;
import com.app.sms.utils.Utilitaire;

public class UIConsulterModule extends AbstractUIOperation implements IUConsulterModule {
	private JTextField coefficient;
	private JTextField libelle;
	
	private JTextArea description;

	private JButton deleteButton;
	private JButton updateButton;
	private JTable listeModule;
	private JTextField id;
	
	private ModuleModel moduleModel;
	private JComboBox listeClasse;
	private JComboBox listeEnseignant;
	private JCheckBox activer;

	public UIConsulterModule() {
		operationIcon.setIcon(new ImageIcon(UIConsulterModule.class.getResource("/images/86b97956377c679f1b4a3c597358036f.jpg")));
		operationLabel.setText("Modules");
		
		JPanel mainPanel = new JPanel();
		centerPanel.add(mainPanel, BorderLayout.CENTER);
		mainPanel.setLayout(new GridLayout(2, 1, 0, 0));
		
		JPanel listeModulePanel = new JPanel();
		listeModulePanel.setBackground(Color.WHITE);
		mainPanel.add(listeModulePanel);
		
		JScrollPane listeModuleScrollPane = new JScrollPane();
		listeModuleScrollPane.setBorder(new TitledBorder(null, "Liste des modules", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GroupLayout listeModulePanelLayout = new GroupLayout(listeModulePanel);
		listeModulePanelLayout.setHorizontalGroup(
			listeModulePanelLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(listeModulePanelLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(listeModuleScrollPane, GroupLayout.DEFAULT_SIZE, 774, Short.MAX_VALUE)
					.addContainerGap())
		);
		listeModulePanelLayout.setVerticalGroup(
			listeModulePanelLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(listeModulePanelLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(listeModuleScrollPane, GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		listeModule = new JTable();
		moduleModel = new ModuleModel();
		listeModule.setModel(moduleModel);
		listeModuleScrollPane.setViewportView(listeModule);
		listeModulePanel.setLayout(listeModulePanelLayout);
		listeModule.getSelectionModel().addListSelectionListener(new ListSelectionListener() {			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				int selectedRow = listeModule.getSelectedRow();
				if ( selectedRow >= 0 ) {
					String [] data = getModuleDataByRow(selectedRow);
					updateUIForm (data);
				}
			}
		});
		
		JPanel visuliserModulePanel = new JPanel();
		visuliserModulePanel.setBackground(new Color(189, 183, 107));
		mainPanel.add(visuliserModulePanel);
		
		JPanel modifierModulePanel = new JPanel();
		modifierModulePanel.setBackground(new Color(222, 184, 135));
		modifierModulePanel.setBorder(new TitledBorder(null, "Modifier un module", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GroupLayout visuliserModulePanelLayout = new GroupLayout(visuliserModulePanel);
		visuliserModulePanelLayout.setHorizontalGroup(
			visuliserModulePanelLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(visuliserModulePanelLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(modifierModulePanel, GroupLayout.DEFAULT_SIZE, 790, Short.MAX_VALUE))
		);
		visuliserModulePanelLayout.setVerticalGroup(
			visuliserModulePanelLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, visuliserModulePanelLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(modifierModulePanel, GroupLayout.DEFAULT_SIZE, 241, Short.MAX_VALUE)
					.addGap(4))
		);
		
		JLabel coefficientLabel = new JLabel("Coefficient :");
		
		JLabel libelleLabel = new JLabel("Libellé :");
		
		JLabel descriptionLabel = new JLabel("Description :");
		
		coefficient = new JTextField();
		coefficient.setColumns(10);
		
		libelle = new JTextField();
		libelle.setColumns(20);
		
		JScrollPane descriptionScrollPane = new JScrollPane();
		
		updateButton = new JButton("Update");
		updateButton.setEnabled(false);
		updateButton.setPreferredSize(new Dimension(69, 20));
		updateButton.setMinimumSize(new Dimension(69, 20));
		updateButton.setMaximumSize(new Dimension(69, 20));
		
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
		
		deleteButton = new JButton("Delete");
		deleteButton.setEnabled(false);
		deleteButton.setMaximumSize(new Dimension(65, 20));
		deleteButton.setMinimumSize(new Dimension(65, 20));
		deleteButton.setPreferredSize(new Dimension(65, 20));
		
		JPanel notificationPanel = new JPanel();
		notificationPanel.setBackground(Color.WHITE);
		notificationPanel.setBorder(new TitledBorder(null, "Notification", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JLabel idLabel = new JLabel("Id :");
		
		id = new JTextField();
		id.setText("-1");
		id.setEnabled(false);
		id.setEditable(false);
		id.setColumns(10);
		
		JPanel panel = new JPanel();
		GroupLayout modifierModulePanelLayout = new GroupLayout(modifierModulePanel);
		modifierModulePanelLayout.setHorizontalGroup(
			modifierModulePanelLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(modifierModulePanelLayout.createSequentialGroup()
					.addGroup(modifierModulePanelLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(modifierModulePanelLayout.createSequentialGroup()
							.addContainerGap()
							.addGroup(modifierModulePanelLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(modifierModulePanelLayout.createSequentialGroup()
									.addComponent(descriptionLabel)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(descriptionScrollPane, GroupLayout.PREFERRED_SIZE, 375, GroupLayout.PREFERRED_SIZE))
								.addGroup(modifierModulePanelLayout.createSequentialGroup()
									.addGroup(modifierModulePanelLayout.createParallelGroup(Alignment.TRAILING)
										.addComponent(libelleLabel)
										.addComponent(coefficientLabel))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(modifierModulePanelLayout.createParallelGroup(Alignment.LEADING, false)
										.addGroup(modifierModulePanelLayout.createSequentialGroup()
											.addComponent(coefficient, GroupLayout.PREFERRED_SIZE, 194, GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(ComponentPlacement.UNRELATED)
											.addComponent(idLabel)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(id, GroupLayout.PREFERRED_SIZE, 151, GroupLayout.PREFERRED_SIZE))
										.addComponent(libelle))))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(modifierModulePanelLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(modifierModulePanelLayout.createSequentialGroup()
									.addComponent(deleteButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addGap(18)
									.addComponent(updateButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(cancelButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addComponent(notificationPanel, GroupLayout.PREFERRED_SIZE, 268, GroupLayout.PREFERRED_SIZE)))
						.addComponent(panel, GroupLayout.PREFERRED_SIZE, 402, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(55, Short.MAX_VALUE))
		);
		modifierModulePanelLayout.setVerticalGroup(
			modifierModulePanelLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(modifierModulePanelLayout.createSequentialGroup()
					.addGap(6)
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(modifierModulePanelLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING, modifierModulePanelLayout.createSequentialGroup()
							.addComponent(notificationPanel, GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE)
							.addGap(18)
							.addGroup(modifierModulePanelLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(deleteButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(updateButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(cancelButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addGroup(modifierModulePanelLayout.createSequentialGroup()
							.addGroup(modifierModulePanelLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(coefficientLabel)
								.addComponent(coefficient, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(idLabel)
								.addComponent(id, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(modifierModulePanelLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(libelleLabel)
								.addGroup(modifierModulePanelLayout.createSequentialGroup()
									.addComponent(libelle, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(modifierModulePanelLayout.createParallelGroup(Alignment.BASELINE)
										.addComponent(descriptionScrollPane, GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
										.addComponent(descriptionLabel))))
							.addContainerGap())))
		);
		panel.setLayout(new GridLayout(0, 5, 0, 0));
		
		activer = new JCheckBox("Activer");
		panel.add(activer);
		
		JLabel lblEnseignant = new JLabel("Enseignant");
		lblEnseignant.setHorizontalAlignment(SwingConstants.RIGHT);
		panel.add(lblEnseignant);
		
		listeEnseignant = new JComboBox();
		panel.add(listeEnseignant);
		
		JLabel lblClasse = new JLabel("Classe");
		lblClasse.setHorizontalAlignment(SwingConstants.RIGHT);
		panel.add(lblClasse);
		try {
			for ( Enseignant enseignant : Enseignant.list() ) {
				listeEnseignant.addItem(  enseignant.getId() + Utilitaire.SEPARATEUR + enseignant.getNom()+ Utilitaire.SEPARATEUR + enseignant.getPrenom());
			}
		} catch (WSException | IOException | JAXBException e2) {
			// TODO Auto-generated catch block
			displayErrorMessage(e2.getMessage());
		}
		
	    listeClasse = new JComboBox();
		panel.add(listeClasse);
		notificationPanel.setLayout(new BorderLayout(0, 0));
		  
		try {
			for ( Classe classe : Classe.list() ) {
				listeClasse.addItem(  classe.getId() + Utilitaire.SEPARATEUR + classe.getLibelle());
			}
		} catch ( WSException | JAXBException | IOException e1) {
			
			displayErrorMessage(e1.getMessage());
		}
		
		listeEnseignant.setEnabled(false);
		listeClasse.setEnabled(false);

		
		activer.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (activer.isSelected()) {
					listeEnseignant.setEnabled(true);
					listeClasse.setEnabled(true);
				} else {
					listeEnseignant.setEnabled(false);
					listeClasse.setEnabled(false);
//					responsables.addItem("Choisissez un enseignant ...");
//					responsables.setEnabled(false);
				}
			}
		});
		
		JScrollPane scrollPane = new JScrollPane();
		notificationPanel.add(scrollPane, BorderLayout.CENTER);
		
		notification.setFont(new Font("Tahoma", Font.PLAIN, 11));
		scrollPane.setViewportView(notification);
		
		description = new JTextArea();
		description.setColumns(20);
		description.setFont(new Font("Tahoma", Font.PLAIN, 11));
		descriptionScrollPane.setViewportView(description);
		modifierModulePanel.setLayout(modifierModulePanelLayout);
		visuliserModulePanel.setLayout(visuliserModulePanelLayout);
		
		/*
		 * Contrôle de la saisie du champ Libelle et Coéfficient avant activation du bouton de validation 
		 * 
		 * */	
		ArrayList<JTextField> textFields = new ArrayList<>();
		textFields.add(coefficient);
		textFields.add(libelle);
		
		FilledFieldChecker updateButtonChecker = new FilledFieldChecker(updateButton, textFields);
		coefficient.getDocument().addDocumentListener(updateButtonChecker);
		libelle.getDocument().addDocumentListener(updateButtonChecker);
		
		FilledFieldChecker deleteButtonChecker = new FilledFieldChecker(deleteButton, textFields);
		coefficient.getDocument().addDocumentListener(deleteButtonChecker);
		libelle.getDocument().addDocumentListener(deleteButtonChecker);
	}
	
	@Override
	public void updateUIForm(String[] data) {
		try {
			this.setId(data[0]);
			this.setCoefficient(data[1]);
			this.setLibelle(data [2]);
			this.setDescription(data [3]);
		} catch (NullPointerException ignored) {}
	}

	@Override
	public String getDescription() {
		return description.getText();
	}
	
	@Override
	public String getLibelle() {
		return libelle.getText();
	}

	@Override
	public String getCoefficient() {
		return coefficient.getText();
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
	public void resetFormUI() {
		setId("-1");
		setDescription(null);
		setLibelle(null);
		setCoefficient(null);
		displayNotification(null);
	}
	
	@Override
	public void loadData(List<Module> modules) {
		moduleModel.loadData(modules);
	}

	private void setDescription(String description) {
		this.description.setText(description);
	}
	
	private String[] getModuleDataByRow(int row) {
		String [] values = new String [moduleModel.getColumnCount()] ;
		for ( int i = 0 ; i < moduleModel.getColumnCount() ; i ++ ) {
			values [i] = (String) moduleModel.getValueAt(row, i);
		}
		return values;
	}
	
	private void setLibelle(String libelle) {
		this.libelle.setText(libelle);
	}
	
	private void setCoefficient(String coefficient) {
		this.coefficient.setText(coefficient);
	}
	
	private void setId(String id) {
		this.id.setText(id);
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
	public String getIdClasse() {
		String [] splitInfos = listeClasse.getSelectedItem().toString().split("::");
		return splitInfos[0];
	}
	public String getIdEns() {
		String [] splitInfos = listeEnseignant.getSelectedItem().toString().split("::");
		return splitInfos[0];
	}
	public boolean getCheckboxStatut() {
		return activer.isSelected();
	}
}
