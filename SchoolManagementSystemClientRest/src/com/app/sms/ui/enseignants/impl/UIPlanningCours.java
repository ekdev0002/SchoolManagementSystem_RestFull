package com.app.sms.ui.enseignants.impl;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.xml.bind.JAXBException;

import com.app.sms.exceptions.WSException;
import com.app.sms.models.Classe;
import com.app.sms.models.Cours;
import com.app.sms.models.CoursModel;
import com.app.sms.models.Module;
import com.app.sms.ui.enseignants.IUIPlanningCours;
import com.app.sms.ui.impl.AbstractUIOperation;
import com.app.sms.utils.DateTimePicker;
import com.app.sms.utils.Utilitaire;

public class UIPlanningCours extends AbstractUIOperation implements IUIPlanningCours {
	private JTable listeCours;
	private CoursModel coursModel;
	private JSpinner spinner;
	private JTextArea textArea;

	private JTextField idTextField2;
	private JSpinner spinner2;
	private JTextArea textArea2;
	private JComboBox module;
	private JComboBox module2;
	
	private JButton btnValider;
	private JButton btnSupprimer;
	private JButton btnQuitter;
	private JTabbedPane tabPane;
	private DateTimePicker picker1;
	private DateTimePicker picker;
	private JComboBox<Object> classes;
	private JComboBox<Object> classes2;
	protected String[] data;
	
	public UIPlanningCours() {
		operationIcon.setIcon(new ImageIcon(UIPlanningCours.class.getResource("/images/planning-cours-logo.png")));
		operationLabel.setText("Planning des cours");
		
		JPanel mainPanel = new JPanel();
		centerPanel.add(mainPanel, BorderLayout.CENTER);
		mainPanel.setLayout(new GridLayout(2, 1, 0, 0));
		
		JPanel listeCoursPanel = new JPanel();
		listeCoursPanel.setBackground(Color.RED);
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
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null, "Ajouter Cours", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		JPanel panel_22 = new JPanel();
		panel_22.setBorder(new TitledBorder(null, "Modifier Cours", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		
		
		tabPane=new JTabbedPane();
		tabPane.add("Ajouter",panel_2);
		tabPane.add("Modifer",panel_22);
		
		panel.add(tabPane);
		
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
        
        JPanel panel_6 = new JPanel();
        FlowLayout flowLayout_1 = (FlowLayout) panel_6.getLayout();
        flowLayout_1.setHgap(30);
        flowLayout_1.setAlignment(FlowLayout.LEFT);
        panel_2.add(panel_6);
        
        JLabel lblNewLabel = new JLabel("Module:");
        panel_6.add(lblNewLabel);
        
        
        module = new JComboBox<>();
        panel_2.add(module);
        
		try {
			for ( Module modul : Module.listForEns()) {
				module.addItem(  modul.getId() + Utilitaire.SEPARATEUR + modul.getLibelle());
			}
		} catch (WSException | JAXBException | IOException e1) {
			// TODO Auto-generated catch block
			displayErrorMessage(e1.getMessage());
		}
        
        JPanel panel_5 = new JPanel();
        FlowLayout flowLayout = (FlowLayout) panel_5.getLayout();
        flowLayout.setHgap(30);
        flowLayout.setAlignment(FlowLayout.LEFT);
        panel_2.add(panel_5);
        
        JLabel lblClasse = new JLabel("Classe");
        lblClasse.setHorizontalAlignment(SwingConstants.LEFT);
        panel_5.add(lblClasse);
        
        classes = new JComboBox<Object>();
        panel_2.add(classes);
        
		try {
			for ( Classe classe : Classe.list() ) {
				classes.addItem(  classe.getId() + Utilitaire.SEPARATEUR + classe.getLibelle());
			}
		} catch (WSException | JAXBException | IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}


        

        JPanel panel_9 = new JPanel();
        FlowLayout flowLayout_3 = (FlowLayout) panel_9.getLayout();
        flowLayout_3.setHgap(30);
        flowLayout_3.setAlignment(FlowLayout.LEFT);
        panel_2.add(panel_9);
        
        JLabel lblDateEtHeure = new JLabel("Date et Heure:");
        panel_9.add(lblDateEtHeure);
        Date date = new Date();
		picker = new DateTimePicker();
		panel_2.add(picker);
		picker.getEditor().setHorizontalAlignment(SwingConstants.RIGHT);
		picker.setFormats( DateFormat.getDateTimeInstance( DateFormat.SHORT, DateFormat.MEDIUM ) );
		picker.setTimeFormat( DateFormat.getTimeInstance( DateFormat.MEDIUM ) );   
		picker.setDate(date);
		picker.getEditor().setColumns(18);
        
        
        JPanel panel_11 = new JPanel();
        FlowLayout flowLayout_4 = (FlowLayout) panel_11.getLayout();
        flowLayout_4.setHgap(30);
        flowLayout_4.setAlignment(FlowLayout.LEFT);
        panel_2.add(panel_11);
        
        JLabel lblDuree = new JLabel("Durée (en mn) :");
        panel_11.add(lblDuree);
        
        JPanel panel_12 = new JPanel();
        FlowLayout flowLayout_10 = (FlowLayout) panel_12.getLayout();
        flowLayout_10.setAlignment(FlowLayout.RIGHT);
        panel_2.add(panel_12);
        
        spinner = new JSpinner();
        spinner.setModel(new SpinnerNumberModel(new Integer(60), null, null, new Integer(0)));
        panel_12.add(spinner);
        
        JPanel panel_13 = new JPanel();
        FlowLayout flowLayout_8 = (FlowLayout) panel_13.getLayout();
        flowLayout_8.setHgap(30);
        flowLayout_8.setAlignment(FlowLayout.LEFT);
        panel_2.add(panel_13);
        
        JLabel lblDescription = new JLabel("Description:");
        panel_13.add(lblDescription);
        
        textArea = new JTextArea();
        panel_2.add(textArea);
        textArea.setRows(2);
        textArea.setColumns(22);
        textArea.setFont(new Font("Tahoma", Font.PLAIN, 11));
	
		
		
		
        JPanel panel_81 = new JPanel();
        FlowLayout flowLayout1 = (FlowLayout) panel_81.getLayout();
        flowLayout1.setHgap(30);
        flowLayout1.setAlignment(FlowLayout.LEFT);
        panel_22.add(panel_81);
        
        JLabel lblId1 = new JLabel("Id:");
        panel_81.add(lblId1);
        
        idTextField2 = new JTextField();
        panel_22.add(idTextField2);
        idTextField2.setColumns(22);
        idTextField2.setEnabled(false);
        
        JPanel panel_61 = new JPanel();
        FlowLayout flowLayout_11 = (FlowLayout) panel_61.getLayout();
        flowLayout_11.setHgap(30);
        flowLayout_11.setAlignment(FlowLayout.LEFT);
        panel_22.add(panel_61);
        
        JLabel lblNewLabel1 = new JLabel("Module:");
        panel_61.add(lblNewLabel1);
        
        
        
        module2 = new JComboBox<>();
        panel_22.add(module2);
        
		try {
			for ( Module module : Module.listForEns() ) {
				module2.addItem( module.getId() + Utilitaire.SEPARATEUR + module.getLibelle());
			}
		} catch ( WSException | JAXBException | IOException e1) {
			// TODO Auto-generated catch block
			displayErrorMessage(e1.getMessage());
		}


        JPanel panel_51 = new JPanel();
        FlowLayout flowLayout_2 = (FlowLayout) panel_51.getLayout();
        flowLayout_2.setHgap(30);
        flowLayout_2.setAlignment(FlowLayout.LEFT);
        FlowLayout flowLayou = (FlowLayout) panel_51.getLayout();
        flowLayou.setHgap(30);
        flowLayou.setAlignment(FlowLayout.LEFT);
        panel_22.add(panel_51);
        
        JLabel lblClasse2 = new JLabel("Classe");
        lblClasse2.setHorizontalAlignment(SwingConstants.LEFT);
        panel_51.add(lblClasse2);
        
        classes2 = new JComboBox<Object>();
        panel_22.add(classes2);
        
		try {
			for ( Classe classe : Classe.list() ) {
				classes2.addItem(  classe.getId() + Utilitaire.SEPARATEUR + classe.getLibelle());
			}
		} catch ( WSException | JAXBException | IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
        JPanel panel_91 = new JPanel();
        FlowLayout flowLayout_31 = (FlowLayout) panel_91.getLayout();
        flowLayout_31.setHgap(30);
        flowLayout_31.setAlignment(FlowLayout.LEFT);
        panel_22.add(panel_91);
        
        JLabel lblDateEtHeure1 = new JLabel("Date et Heure:");
        panel_91.add(lblDateEtHeure1);
        Date date1 = new Date();
		picker1 = new DateTimePicker();
		panel_22.add(picker1);
		picker1.getEditor().setHorizontalAlignment(SwingConstants.RIGHT);
		picker1.setFormats( DateFormat.getDateTimeInstance( DateFormat.SHORT, DateFormat.MEDIUM ) );
		picker1.setTimeFormat( DateFormat.getTimeInstance( DateFormat.MEDIUM ) );   
		picker1.setDate(date1);
		picker1.getEditor().setColumns(18);
        
        
        JPanel panel_111 = new JPanel();
        FlowLayout flowLayout_41 = (FlowLayout) panel_111.getLayout();
        flowLayout_41.setHgap(30);
        flowLayout_41.setAlignment(FlowLayout.LEFT);
        panel_22.add(panel_111);
        
        JLabel lblDuree1 = new JLabel("Durée (en mn) :");
        panel_111.add(lblDuree1);
        
        JPanel panel_121 = new JPanel();
        FlowLayout flowLayout_101 = (FlowLayout) panel_121.getLayout();
        flowLayout_101.setAlignment(FlowLayout.RIGHT);
        panel_22.add(panel_121);
        
        spinner2 = new JSpinner();
        spinner2.setModel(new SpinnerNumberModel(new Integer(60), null, null, new Integer(0)));
        panel_121.add(spinner2);
        
        JPanel panel_131 = new JPanel();
        FlowLayout flowLayout_81 = (FlowLayout) panel_131.getLayout();
        flowLayout_81.setHgap(30);
        flowLayout_81.setAlignment(FlowLayout.LEFT);
        panel_22.add(panel_131);
        
        JLabel lblDescription1 = new JLabel("Description:");
        panel_131.add(lblDescription1);
        
        textArea2 = new JTextArea();
        panel_22.add(textArea2);
        textArea2.setRows(2);
        textArea2.setColumns(22);
        textArea2.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		btnValider = new JButton("Valider");

		btnValider.setPreferredSize(new Dimension(65, 20));
		btnValider.setMinimumSize(new Dimension(65, 20));
		btnValider.setMaximumSize(new Dimension(65, 20));
		btnValider.setEnabled(true);
		
		btnSupprimer = new JButton("Supprimer");
		btnSupprimer.setPreferredSize(new Dimension(69, 20));
		btnSupprimer.setMinimumSize(new Dimension(69, 20));
		btnSupprimer.setMaximumSize(new Dimension(69, 20));
		btnSupprimer.setEnabled(false);
		
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
		panel_3.add(btnValider);
		panel_3.add(btnSupprimer);
		panel_3.add(btnQuitter);
		
        panel_2.setLayout(new GridLayout(5, 2, 0, 0));
        panel_22.setLayout(new GridLayout(6, 2, 0, 0));
        
		
		listeCours.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				int selectedRow = listeCours.getSelectedRow();
				if ( selectedRow >= 0 ) {
					data = getCoursDataByRow(selectedRow);
					updateUIForm (data);
					if(!ajouterSelected()) {
						btnSupprimer.setEnabled(true);
					}
					else
						btnSupprimer.setEnabled(false);
						
				}
			}
		});
		
		
		tabPane.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent arg0) {
				// TODO Auto-generated method stub
				if(ajouterSelected())
				{
					btnSupprimer.setEnabled(false);
				}
				}
				
			
		});
		
		
		
		notification.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		/*
		 * Contrôle de la saisie du champ Libelle et Coéfficient avant activation du bouton de validation 
		 * 
		 * */	
		ArrayList<JTextField> idTextFields = new ArrayList<>();
	}
	
	@Override
	public void updateUIForm(String[] data) {
		try {
			if(ajouterSelected())
			{
				resetFormUI();
			}
			else
			{
				this.setId(data[0]);
				this.setModule(data[1]);
				this.setClasse(data[2]);
				this.setDateHeure(data[3]);
				this.setDuree(data[4]);
				this.setDescription(data[5]);
				
			}
		} catch ( NullPointerException ignored) {}
	}
	

	private void setClasse(String string) {
		// TODO Auto-generated method stub
		if(ajouterSelected())
		{
		classes.setEnabled(false);
		
		for (int i=0;i<classes.getItemCount();i++)
		{ 
			String [] splitInfos = classes.getItemAt(i).toString().split("::");	
			if((splitInfos[1]).equals(string))
			{
				classes.setEnabled(true);
				classes.setSelectedItem(classes.getItemAt(i));
			}
		}
		}
		else
		{
			classes2.setEnabled(false);
			
			for (int i=0;i<classes2.getItemCount();i++)
			{ 
				String [] splitInfos = classes2.getItemAt(i).toString().split("::");	
				if((splitInfos[1]).equals(string))
				{
					classes2.setEnabled(true);
					classes2.setSelectedItem(classes2.getItemAt(i));
				}
			}
			
		}
		
	}

	public void resetFormUI() {
		Date now = new Date();
		setId(" ");
		setIdModule(1);
		setDateHeure(now.toString());
		setDuree("60");
		setDescription(null);
		displayNotification(null);
	}
	
	@Override
	public void loadData(List<Cours> cours) {
		try {
			coursModel.loadData(cours);
		} catch (WSException | JAXBException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	private void setId(String id) {
		if(!ajouterSelected())
			this.idTextField2.setText(id);
	}


	private void setIdModule(int id) {
		if(ajouterSelected())
		{
		module.setEnabled(false);
		
		for (int i=0;i<module.getItemCount();i++)
		{ 
			String [] splitInfos = module.getItemAt(i).toString().split("::");	
			if(Integer.parseInt(splitInfos[0])==id)
			{
				module.setEnabled(true);
				module.setSelectedItem(module.getItemAt(i));
			}
		}
		}
		else
		{
			module2.setEnabled(false);
			
			for (int i=0;i<module2.getItemCount();i++)
			{ 
				String [] splitInfos = module2.getItemAt(i).toString().split("::");	
				if(Integer.parseInt(splitInfos[0])==id)
				{
					module2.setEnabled(true);
					module2.setSelectedItem(module2.getItemAt(i));
				}
			}
			
		}
	}
	
	private void setModule(String modul) {
		if(ajouterSelected())
		{
		module.setEnabled(false);
		
		for (int i=0;i<module.getItemCount();i++)
		{ 
			String [] splitInfos = module.getItemAt(i).toString().split("::");	
			if((splitInfos[1]).equals(modul))
			{
				module.setEnabled(true);
				module.setSelectedItem(module.getItemAt(i));
			}
		}
		}
		else
		{
			module2.setEnabled(false);
			
			for (int i=0;i<module2.getItemCount();i++)
			{ 
				String [] splitInfos = module2.getItemAt(i).toString().split("::");	
				if((splitInfos[1]).equals(modul))
				{
					module2.setEnabled(true);
					module2.setSelectedItem(module2.getItemAt(i));
				}
			}
			
		}
		
	}

	
	
	private void setDuree(String valeur){
		if(ajouterSelected())
			spinner.setValue(Integer.valueOf(valeur));
		else
			spinner2.setValue(Integer.valueOf(valeur));
	}
	
	
	private void setDateHeure(String time) {
		Date now =new Date();
		if(ajouterSelected())
			picker.setDate(now);
		else
			picker1.setDate(now);
	}
	
	
	private void setDescription(String description) {
		if(ajouterSelected())
			textArea.setText(description);
		else
			textArea2.setText(description);
	}
	
	
	
	private String[] getCoursDataByRow(int row) {
		
		String [] values = new String [coursModel.getColumnCount()];
		for ( int i = 0 ; i < coursModel.getColumnCount() ; i ++ ) {
			values [i] = (String) coursModel.getValueAt(row, i);
		}
		return values;
	}
	
	public void addValiderListener(ActionListener actionListener) {
		btnValider.addActionListener(actionListener);
	}

	public void addSupprimerListener(ActionListener actionListener) {
		btnSupprimer.addActionListener(actionListener);
	}
	
	public boolean ajouterSelected() {
		return (tabPane.getSelectedIndex() == 0) ? true : false;		
	}

	public String getId() {	
		return ajouterSelected() ? "-1" : data[0];		
	}

	public String getIdModule() {
		String [] splitInfos = module.getSelectedItem().toString().split("::");
		String [] splitInfos2 = module2.getSelectedItem().toString().split("::");
		return ajouterSelected() ? splitInfos[0]: splitInfos2[0];
	}

	public String getDateHeure() {
		// TODO Auto-generated method stub
		return ajouterSelected() ? picker.getDate().toString(): picker1.getDate().toString();
	}

	public int getDuree() {
		// TODO Auto-generated method stub
		return ajouterSelected() ? (Integer)spinner.getValue(): (Integer)spinner2.getValue();
	}

	public String getDescription() {
		// TODO Auto-generated method stub
		return ajouterSelected() ? textArea.getText() : textArea2.getText();
	}

	public String getIdClasse() {
		String [] splitInfos = classes.getSelectedItem().toString().split("::");
		String [] splitInfos2 = classes2.getSelectedItem().toString().split("::");
		return ajouterSelected() ? splitInfos[0]: splitInfos2[0];
	}
}
