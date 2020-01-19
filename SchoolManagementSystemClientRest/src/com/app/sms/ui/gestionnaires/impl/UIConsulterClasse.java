package com.app.sms.ui.gestionnaires.impl;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.app.sms.models.Classe;
import com.app.sms.models.ClasseModel;
import com.app.sms.ui.gestionnaires.IUConsulterClasse;
import com.app.sms.ui.gestionnaires.listeners.FilledFieldChecker;
import com.app.sms.ui.impl.AbstractUIOperation;

public class UIConsulterClasse extends AbstractUIOperation implements IUConsulterClasse {
	private JTextField libelle;
	
	private JTextArea description;
	
	private JButton deleteButton;
	private JButton updateButton;
	private JTable listeClasse;
	private JTextField id;
	private ClasseModel classeModel;
	
	public UIConsulterClasse() {

		operationIcon.setIcon(new ImageIcon(UIConsulterClasse.class.getResource("/images/1095901-parcourssup-amphijpg.jpg")));
		operationLabel.setText("Classes");
		
		JPanel mainPanel = new JPanel();
		centerPanel.add(mainPanel, BorderLayout.CENTER);
		mainPanel.setLayout(new GridLayout(2, 1, 0, 0));
		
		JPanel listeClassePanel = new JPanel();
		listeClassePanel.setBackground(new Color(128, 128, 128));
		mainPanel.add(listeClassePanel);
		
		JScrollPane listeScrollPane = new JScrollPane();
		listeScrollPane.setBorder(new TitledBorder(null, "Liste des classes", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GroupLayout listeClassePanelLayout = new GroupLayout(listeClassePanel);
		listeClassePanelLayout.setHorizontalGroup(
			listeClassePanelLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(listeClassePanelLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(listeScrollPane, GroupLayout.DEFAULT_SIZE, 774, Short.MAX_VALUE)
					.addContainerGap())
		);
		listeClassePanelLayout.setVerticalGroup(
			listeClassePanelLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(listeClassePanelLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(listeScrollPane, GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		listeClasse = new JTable();
		classeModel = new ClasseModel();
		listeClasse.setModel(classeModel);
		listeScrollPane.setViewportView(listeClasse);
		listeClassePanel.setLayout(listeClassePanelLayout);
		listeClasse.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				int selectedRow = listeClasse.getSelectedRow();
				if ( selectedRow >= 0 ) {
					String [] data = getClasseDataByRow(selectedRow);
					updateUIForm (data);
				}
			}
		});
		
		JPanel visuliserClassePanel = new JPanel();
		visuliserClassePanel.setBackground(SystemColor.activeCaption);
		mainPanel.add(visuliserClassePanel);
		
		JPanel modifierClassePanel = new JPanel();
		modifierClassePanel.setBackground(new Color(173, 216, 230));
		modifierClassePanel.setBorder(new TitledBorder(null, "Modifier un classe", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GroupLayout visuliserClassePanelLayout = new GroupLayout(visuliserClassePanel);
		visuliserClassePanelLayout.setHorizontalGroup(
			visuliserClassePanelLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, visuliserClassePanelLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(modifierClassePanel, GroupLayout.PREFERRED_SIZE, 774, Short.MAX_VALUE)
					.addContainerGap())
		);
		visuliserClassePanelLayout.setVerticalGroup(
			visuliserClassePanelLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(visuliserClassePanelLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(modifierClassePanel, GroupLayout.DEFAULT_SIZE, 151, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		JLabel libelleLabel = new JLabel("Libellé :");
		
		JLabel descriptionLabel = new JLabel("Description :");
		
		libelle = new JTextField();
		libelle.setColumns(10);
		
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
		GroupLayout modifierClassePanelLayout = new GroupLayout(modifierClassePanel);
		modifierClassePanelLayout.setHorizontalGroup(
			modifierClassePanelLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(modifierClassePanelLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(modifierClassePanelLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(modifierClassePanelLayout.createSequentialGroup()
							.addComponent(descriptionLabel)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(descriptionScrollPane, GroupLayout.PREFERRED_SIZE, 375, GroupLayout.PREFERRED_SIZE))
						.addGroup(modifierClassePanelLayout.createSequentialGroup()
							.addGap(23)
							.addGroup(modifierClassePanelLayout.createParallelGroup(Alignment.TRAILING)
								.addComponent(libelleLabel)
								.addComponent(idLabel))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(modifierClassePanelLayout.createParallelGroup(Alignment.TRAILING)
								.addComponent(id, GroupLayout.PREFERRED_SIZE, 376, GroupLayout.PREFERRED_SIZE)
								.addComponent(libelle, GroupLayout.PREFERRED_SIZE, 375, GroupLayout.PREFERRED_SIZE))))
					.addGap(21)
					.addGroup(modifierClassePanelLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(modifierClassePanelLayout.createSequentialGroup()
							.addComponent(deleteButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(45)
							.addComponent(updateButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
							.addComponent(cancelButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(8))
						.addGroup(modifierClassePanelLayout.createSequentialGroup()
							.addComponent(notificationPanel, GroupLayout.DEFAULT_SIZE, 288, Short.MAX_VALUE)
							.addContainerGap())))
		);
		modifierClassePanelLayout.setVerticalGroup(
			modifierClassePanelLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(modifierClassePanelLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(modifierClassePanelLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(modifierClassePanelLayout.createSequentialGroup()
							.addGroup(modifierClassePanelLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(id, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(idLabel))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(modifierClassePanelLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(modifierClassePanelLayout.createSequentialGroup()
									.addComponent(libelleLabel)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(descriptionLabel))
								.addGroup(modifierClassePanelLayout.createSequentialGroup()
									.addComponent(libelle, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(descriptionScrollPane, GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)))
							.addContainerGap())
						.addGroup(modifierClassePanelLayout.createSequentialGroup()
							.addComponent(notificationPanel, GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(modifierClassePanelLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(cancelButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(deleteButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(updateButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))))
		);
		notificationPanel.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		notificationPanel.add(scrollPane, BorderLayout.CENTER);
		
		notification.setFont(new Font("Tahoma", Font.PLAIN, 11));
		scrollPane.setViewportView(notification);
		
		description = new JTextArea();
		description.setFont(new Font("Tahoma", Font.PLAIN, 11));
		descriptionScrollPane.setViewportView(description);
		modifierClassePanel.setLayout(modifierClassePanelLayout);
		visuliserClassePanel.setLayout(visuliserClassePanelLayout);
		
		/*
		 * Contrôle de la saisie du champ Libelle et Coéfficient avant activation du bouton de validation 
		 * 
		 * */	
		ArrayList<JTextField> textFields = new ArrayList<>();
		textFields.add(libelle);
		
		FilledFieldChecker updateButtonChecker = new FilledFieldChecker(updateButton, textFields);
		libelle.getDocument().addDocumentListener(updateButtonChecker);
		
		FilledFieldChecker deleteButtonChecker = new FilledFieldChecker(deleteButton, textFields);
		libelle.getDocument().addDocumentListener(deleteButtonChecker);
	}
	
	@Override
	public void updateUIForm(String[] data) {
		try {
			this.setId(data[0]);
			this.setLibelle(data [1]);
			this.setDescription(data [2]);
		} catch ( NullPointerException ignored) {}
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
	
	
	public void resetFormUI() {
		setId("-1");
		setDescription(null);
		setLibelle(null);
		displayNotification(null);
	}
	
	@Override
	public void loadData(List<Classe> classes) {
		classeModel.loadData(classes);
	}
	
	private void setDescription(String description) {
		this.description.setText(description);
	}
	
	private void setLibelle(String libelle) {
		this.libelle.setText(libelle);
	}
	
	private void setId(String id) {
		this.id.setText(id);
	}
	
	private String[] getClasseDataByRow(int row) {
		
		String [] values = new String [classeModel.getColumnCount()];
		for ( int i = 0 ; i < classeModel.getColumnCount() ; i ++ ) {
			values [i] = (String) classeModel.getValueAt(row, i);
		}
		return values;
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
