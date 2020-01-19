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
import java.util.Date;
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
import com.app.sms.models.Devoirs;
import com.app.sms.models.DevoirsModel;
import com.app.sms.ui.eleves.IUIPlanningDevoir;
import com.app.sms.ui.impl.AbstractUIOperation;

public class UIPlanningDevoirs extends AbstractUIOperation implements IUIPlanningDevoir {
	private JTable listeDevoirs;
	private DevoirsModel devoirsModel;

	private JTextField idTextField2;
	private JButton btnQuitter;
	protected String[] data;
	
	public UIPlanningDevoirs() {
		operationIcon.setIcon(new ImageIcon(UIPlanningDevoirs.class.getResource("/images/planning-devoir-icone.png")));
		operationLabel.setText("Planning des devoirs");
		
		JPanel mainPanel = new JPanel();
		centerPanel.add(mainPanel, BorderLayout.CENTER);
		mainPanel.setLayout(new GridLayout(2, 1, 0, 0));
		
		JPanel listeDevoirsPanel = new JPanel();
		listeDevoirsPanel.setBackground(new Color(51, 204, 102));
		mainPanel.add(listeDevoirsPanel);
		
		JScrollPane listeScrollPane = new JScrollPane();
		listeScrollPane.setBorder(new TitledBorder(null, "Liste des devoirs", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GroupLayout listeDevoirsPanelLayout = new GroupLayout(listeDevoirsPanel);
		listeDevoirsPanelLayout.setHorizontalGroup(
			listeDevoirsPanelLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(listeDevoirsPanelLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(listeScrollPane, GroupLayout.DEFAULT_SIZE, 774, Short.MAX_VALUE)
					.addContainerGap())
		);
		listeDevoirsPanelLayout.setVerticalGroup(
			listeDevoirsPanelLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(listeDevoirsPanelLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(listeScrollPane, GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		listeDevoirs = new JTable();
		devoirsModel = new DevoirsModel();
		listeDevoirs.setModel(devoirsModel);
		listeScrollPane.setViewportView(listeDevoirs);
		listeDevoirsPanel.setLayout(listeDevoirsPanelLayout);
		
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
        
        Date date1 = new Date();
		
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
	
	
	@Override
	public void loadData(List<Devoirs> devoirs) {
		try {
			devoirsModel.loadData(devoirs);
		} catch (WSException | JAXBException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	private String[] getDevoirsDataByRow(int row) {
		
		String [] values = new String [devoirsModel.getColumnCount()];
		for ( int i = 0 ; i < devoirsModel.getColumnCount() ; i ++ ) {
			values [i] = (String) devoirsModel.getValueAt(row, i);
		}
		return values;
	}

}
