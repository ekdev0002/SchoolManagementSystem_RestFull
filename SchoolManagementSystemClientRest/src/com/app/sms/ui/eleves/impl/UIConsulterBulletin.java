package com.app.sms.ui.eleves.impl;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.app.sms.models.Bulletin;
import com.app.sms.models.BulletinModel;
import com.app.sms.ui.gestionnaires.IUGestionBulletin;
import com.app.sms.ui.impl.AbstractUIOperation;
import com.app.sms.utils.Utilitaire;


public class UIConsulterBulletin extends AbstractUIOperation implements IUGestionBulletin{
	
	private static final URL DEFAULT_URL_PICTURE = UIConsulterBulletin.class.getResource("/images/inconnu.png");
	
	private BulletinModel bulletinModel;
	private JButton btnVisualiserButton;
	private JButton btnImprimerButton;
	private JTable listeBulletin;
	

	public UIConsulterBulletin() {

		setMinimumSize(new Dimension(834, 573));
		setSize(new Dimension(834, 582));
		setPreferredSize(new Dimension(834, 573));
		operationIcon.setIcon(new ImageIcon(UIConsulterBulletin.class.getResource("/images/imageBulletin.png")));
		operationLabel.setText("Gestion des bulletins");
		
		JPanel rechercherPanel = new JPanel();
		centerPanel.add(rechercherPanel, BorderLayout.NORTH);
		rechercherPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		
		JPanel mainPanel = new JPanel();
		mainPanel.setPreferredSize(new Dimension(8, 8));
		centerPanel.add(mainPanel, BorderLayout.CENTER);
		mainPanel.setLayout(new GridLayout(2, 1, 0, 0));
		
		JPanel listeBulletinPanel = new JPanel();
		listeBulletinPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		mainPanel.add(listeBulletinPanel);
		
		JScrollPane listeBulletinScrollPane = new JScrollPane();
		listeBulletinScrollPane.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Liste des bulletins g\u00E9n\u00E9r\u00E9s", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		
		listeBulletin = new JTable();
		bulletinModel = new BulletinModel();
		listeBulletin.setModel(bulletinModel);
		listeBulletinPanel.setLayout(new GridLayout(1, 1, 0, 0));
		listeBulletinScrollPane.setViewportView(listeBulletin);
		listeBulletinPanel.add(listeBulletinScrollPane);
		listeBulletin.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {				
				btnImprimerButton.setEnabled(true);
			}
		});
		
		JPanel visualiserEnseignantPanel = new JPanel();
		visualiserEnseignantPanel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Visualisation et Impression", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		mainPanel.add(visualiserEnseignantPanel);
		
		JPanel formPanel = new JPanel();
		GroupLayout visualiserEnseignantPanelLayout = new GroupLayout(visualiserEnseignantPanel);
		visualiserEnseignantPanelLayout.setHorizontalGroup(
			visualiserEnseignantPanelLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(visualiserEnseignantPanelLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(formPanel, GroupLayout.DEFAULT_SIZE, 802, Short.MAX_VALUE)
					.addContainerGap())
		);
		visualiserEnseignantPanelLayout.setVerticalGroup(
			visualiserEnseignantPanelLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(visualiserEnseignantPanelLayout.createSequentialGroup()
					.addComponent(formPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(26))
		);
		formPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel infoEnseignantPanel = new JPanel();
		formPanel.add(infoEnseignantPanel, BorderLayout.CENTER);
		infoEnseignantPanel.setLayout(new GridLayout(1, 2, 0, 0));
		
		JPanel modelPanel = new JPanel();
		infoEnseignantPanel.add(modelPanel);
		modelPanel.setLayout(new GridLayout(7, 1, 0, 0));
		
		JPanel idPanel = new JPanel();
		FlowLayout idPanelLayout = (FlowLayout) idPanel.getLayout();
		idPanelLayout.setVgap(0);
		modelPanel.add(idPanel);
		
		btnVisualiserButton = new JButton("Visualiser");
		btnVisualiserButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnVisualiserButton.setHorizontalAlignment(SwingConstants.LEFT);
		idPanel.add(btnVisualiserButton);
		
		btnImprimerButton = new JButton("Imprimer");
		idPanel.add(btnImprimerButton);
		
		JButton btnQuitter = new JButton("Quitter");
		btnQuitter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				exitWithConfirmation();
			}
		});
		idPanel.add(btnQuitter);
		
		JPanel otherPanel = new JPanel();
		otherPanel.setMinimumSize(new Dimension(8, 8));
		infoEnseignantPanel.add(otherPanel);
		otherPanel.setLayout(new GridLayout(2, 1, 0, 0));
		
		JPanel notificationPanel = new JPanel();
		otherPanel.add(notificationPanel);
		notificationPanel.setBorder(new TitledBorder(null, "Notification", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		notificationPanel.setLayout(new BorderLayout(0, 0));
		
		JScrollPane notificationScrollPane = new JScrollPane();
		notificationPanel.add(notificationScrollPane, BorderLayout.CENTER);
		
		notification.setFont(new Font("Tahoma", Font.PLAIN, 11));
		notificationScrollPane.setViewportView(notification);
		visualiserEnseignantPanel.setLayout(visualiserEnseignantPanelLayout);
		
		ArrayList<JTextField> textFields = new ArrayList<>();
		
		Utilitaire.center(this, getPreferredSize());
	}
	
	public void loadData(List<Bulletin> bulletins) {
		bulletinModel.loadData(bulletins);
	}
	
	private String getValueListeBulletinAt(int row, int i) {
		return (String) listeBulletin.getValueAt(row, i);
	}
	
	
	@Override
	public void addViusaliserListener(ActionListener listenerForViualiserButton) {
		// TODO Auto-generated method stub
		btnVisualiserButton.addActionListener(listenerForViualiserButton);
		
	}

	@Override
	public void addGenererListener(ActionListener listenerForGenererButton) {
		// TODO Auto-generated method stub
		//genererButton.addActionListener(listenerForGenererButton);

		
	}

	@Override
	public void addImprimerListener(ActionListener listenerForImprimerButton) {
		// TODO Auto-generated method stub
		btnImprimerButton.addActionListener(listenerForImprimerButton);
		
	}
	public int getSeletedIndexbulletin()
	{

			ListSelectionModel selectionModel = listeBulletin.getSelectionModel() ;
			for ( int i = selectionModel.getMinSelectionIndex() ; i <= selectionModel.getMaxSelectionIndex() ; i ++ ) {
				if (selectionModel.isSelectedIndex(i)) {
					return i;
				}
				}
			return -1;
	}
}