package com.app.sms.ui.eleves.impl;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.xml.bind.JAXBException;

import com.app.sms.exceptions.WSException;
import com.app.sms.models.Cours;
import com.app.sms.models.CoursModel;
import com.app.sms.ui.eleves.IUIPlanningCours;
import com.app.sms.ui.impl.AbstractUIOperation;

public class UIPlanningCours extends AbstractUIOperation implements IUIPlanningCours {
	private JTable listeCours;
	private CoursModel coursModel;
	private JButton btnQuitter;
	protected String[] data;
	
	public UIPlanningCours() {
		operationIcon.setIcon(new ImageIcon(UIPlanningCours.class.getResource("/images/planning-cours-logo.png")));
		operationLabel.setText("Planning des cours");
		
		JPanel mainPanel = new JPanel();
		centerPanel.add(mainPanel, BorderLayout.CENTER);
		mainPanel.setLayout(new GridLayout(2, 1, 0, 0));
		
		JPanel listeCoursPanel = new JPanel();
		listeCoursPanel.setBackground(new Color(255, 102, 102));
		mainPanel.add(listeCoursPanel);
		
		JScrollPane listeScrollPane = new JScrollPane();
		listeScrollPane.setBorder(new TitledBorder(null, "Liste des cours", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GroupLayout listeCoursPanelLayout = new GroupLayout(listeCoursPanel);
		listeCoursPanelLayout.setHorizontalGroup(
			listeCoursPanelLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(listeCoursPanelLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(listeScrollPane, GroupLayout.DEFAULT_SIZE, 774, Short.MAX_VALUE)
					.addContainerGap())
		);
		listeCoursPanelLayout.setVerticalGroup(
			listeCoursPanelLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(listeCoursPanelLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(listeScrollPane, GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		listeCours = new JTable();
		coursModel = new CoursModel();
		listeCours.setModel(coursModel);
		listeScrollPane.setViewportView(listeCours);
		listeCoursPanel.setLayout(listeCoursPanelLayout);
		
		JPanel panel = new JPanel();
		mainPanel.add(panel);
		panel.setLayout(new GridLayout(0, 2, 0, 0));
		
		JPanel panel_1 = new JPanel();
		panel.add(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_15 = new JPanel();
		panel_15.setBorder(new TitledBorder(null, "Notification", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.add(panel_15);
		panel_15.setLayout(new GridLayout(2, 1, 0, 0));
		
		JPanel panel_4 = new JPanel();
		panel_15.add(panel_4);
		
		panel_4.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		notification = new JTextArea();
		panel_4.add(scrollPane, BorderLayout.CENTER);
		notification.setColumns(10);
		notification.setFont(new Font("Tahoma", Font.PLAIN, 11));
		scrollPane.setViewportView(notification);

		
		JPanel panel_3 = new JPanel();
		panel_15.add(panel_3);
        

		
		btnQuitter = new JButton("Quitter");
		btnQuitter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				exitWithConfirmation();
					}	
		});
		btnQuitter.setPreferredSize(new Dimension(67, 20));
		btnQuitter.setMinimumSize(new Dimension(67, 20));
		btnQuitter.setMaximumSize(new Dimension(67, 20));
		panel_3.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		panel_3.add(btnQuitter);
	
		notification.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		/*
		 * Contrôle de la saisie du champ Libelle et Coéfficient avant activation du bouton de validation 
		 * 
		 * */	
		ArrayList<JTextField> idTextFields = new ArrayList<>();
	}
	

	public void loadData(List<Cours> cours) {
		try {
			coursModel.loadData(cours);
		} catch (WSException | JAXBException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
	private String[] getCoursDataByRow(int row) {
		
		String [] values = new String [coursModel.getColumnCount()];
		for ( int i = 0 ; i < coursModel.getColumnCount() ; i ++ ) {
			values [i] = (String) coursModel.getValueAt(row, i);
		}
		return values;
	}
	}
