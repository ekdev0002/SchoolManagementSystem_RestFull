package com.app.sms.ui.impl;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EtchedBorder;

import com.app.sms.ui.gestionnaires.observer.Cleaner;
import com.app.sms.ui.gestionnaires.observer.IObservable;
import com.app.sms.ui.gestionnaires.observer.IObserver;
import com.app.sms.utils.Utilitaire;

@SuppressWarnings("serial")
public class AbstractUIOperation extends JPanel implements IObservable {	
	
	protected JPanel headerPanel;
	protected JPanel centerPanel;
	protected JPanel operationPanel;
	protected JLabel operationIcon;
	protected JLabel smsLabel;
	protected JLabel operationLabel;
	protected JTextArea notification = new JTextArea();
	private JButton enabledCleaner;
	private ArrayList<IObserver> observers = new ArrayList<>();
	private Cleaner cleaner;
	
	public AbstractUIOperation() {
		initComponents();
	}
	
	public void initComponents () {
		setPreferredSize(new Dimension(800, 500));
		notification.setEditable(false);
		setLayout(new BorderLayout(0, 0));
		
		headerPanel = new JPanel();
		headerPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		headerPanel.setBackground(Color.WHITE);
		add(headerPanel, BorderLayout.NORTH);
		headerPanel.setLayout(new BorderLayout(0, 0));
		
		operationPanel = new JPanel();
		operationPanel.setBackground(Color.WHITE);
		headerPanel.add(operationPanel, BorderLayout.CENTER);
		
		operationIcon = new JLabel("");
		operationIcon.setMinimumSize(new Dimension(88, 88));
		operationIcon.setMaximumSize(new Dimension(88, 88));
		operationIcon.setPreferredSize(new Dimension(88, 88));
		smsLabel = new JLabel("School Management System");
		
		operationLabel = new JLabel("Operation");
		operationLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		JLabel cleanerStateLabel = new JLabel("Cleaner status :");
		cleanerStateLabel.setFont(new Font("Tahoma", Font.PLAIN, 10));
		
		enabledCleaner = new JButton("Activé");
		enabledCleaner.setFocusable(false);
		enabledCleaner.setForeground(new Color(34, 139, 34));
		enabledCleaner.setPreferredSize(new Dimension(81, 20));
		enabledCleaner.setMaximumSize(new Dimension(81, 20));
		enabledCleaner.setMinimumSize(new Dimension(81, 20));
		enabledCleaner.setToolTipText("Désactiver le nettoyage automatique des notifications ...");
		enabledCleaner.addMouseListener( new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if ( cleaner.isActive() ) {
					cleaner.setActive(false);
					enabledCleaner.setForeground(Color.red);
					enabledCleaner.setText("Désactivé");
					enabledCleaner.setToolTipText("Reactiver le nettoyage automatique des notifications ...");
				} else {
					cleaner.setActive(true);
					enabledCleaner.setForeground(new Color(34, 139, 34));
					enabledCleaner.setText("Activé");
					cleaner.update();
					enabledCleaner.setToolTipText("Désactiver le nettoyage automatique des notifications ...");
				}
			}
		});
		
		GroupLayout operationPanelLayout = new GroupLayout(operationPanel);
		operationPanelLayout.setHorizontalGroup(
			operationPanelLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(operationPanelLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(operationIcon, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(operationPanelLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(operationLabel)
						.addGroup(operationPanelLayout.createSequentialGroup()
							.addComponent(smsLabel)
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
					.addGap(303)
					.addComponent(cleanerStateLabel)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(enabledCleaner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		operationPanelLayout.setVerticalGroup(
			operationPanelLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(operationPanelLayout.createSequentialGroup()
					.addGroup(operationPanelLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(operationPanelLayout.createSequentialGroup()
							.addComponent(smsLabel)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(operationPanelLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(operationLabel)
								.addComponent(enabledCleaner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(cleanerStateLabel)))
						.addComponent(operationIcon, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		operationPanel.setLayout(operationPanelLayout);
		
		centerPanel = new JPanel();
		centerPanel.setFocusable(false);
		add(centerPanel, BorderLayout.CENTER);
		centerPanel.setLayout(new BorderLayout(0, 0));
		
		Utilitaire.center(this, getPreferredSize());
		
		cleaner = new Cleaner (this);
		this.attach(cleaner);
	}

	@Override
	public void attach(IObserver observer) {
		observers.add(observer);
	}

	public void showMe () {
		this.setVisible(true);
	}
	
	public void displayErrorMessage(String errorMessage) {
		notification.setForeground(Color.RED);
		notification.setText(errorMessage);
		notifyAllObservers(observers);
	}
	
	public void displayNotification(String message) {
		notification.setForeground( new Color (32, 166, 72));
		notification.setText(message);
		notifyAllObservers(observers);
	}

	@Override
	public void clear() {
		notification.setText("");
	}

	
	public void exitWithConfirmation() {
		if ( JOptionPane.YES_OPTION == Utilitaire.showConfirmationMessage( "Voulez-vous vraiment retourner à la fenêtre d'accueil ?" ) ) {
			((MainUIApplication)this.getTopLevelAncestor()).ShowPanel("accueil");
		}
	}

	public void resetUI() {
		// TODO Auto-generated method stub
		
	}

	public String getCritere() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getNom() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getPrenom() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getEmail() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getLogin() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getPassword() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getGenre() {
		// TODO Auto-generated method stub
		return null;
	}

	public void resetFormUI() {
		// TODO Auto-generated method stub
		
	}

	

	public void addGoListener(ActionListener listenerForGoButton) {
		// TODO Auto-generated method stub
		
	}

	public String getTypeRecherche() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getTelephone() {
		// TODO Auto-generated method stub
		return null;
	}

	public void addDeleteListener(ActionListener listenerForDeleteButton) {
		// TODO Auto-generated method stub
		
	}

	public String getId() {
		// TODO Auto-generated method stub
		return null;
	}

	public void addUpdateListener(ActionListener listenerForUpdateButton) {
		// TODO Auto-generated method stub
		
	}
}
