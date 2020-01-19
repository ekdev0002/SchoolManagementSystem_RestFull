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
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.xml.bind.JAXBException;

import com.app.sms.exceptions.WSException;
import com.app.sms.models.Devoirs;
import com.app.sms.models.Eleve;
import com.app.sms.models.EleveNoteModel;
import com.app.sms.models.Note;
import com.app.sms.ui.enseignants.IUISaisieNote;
import com.app.sms.ui.impl.AbstractUIOperation;
import com.app.sms.ui.impl.MainUIApplication;
import com.app.sms.utils.DateTimePicker;
import com.app.sms.utils.Utilitaire;

public class UISaisieNotes extends AbstractUIOperation implements IUISaisieNote {
	private JTable listeEleves;
	private EleveNoteModel elevesNotesModel;

	
	private JComboBox devoirs;
	
	private JButton btnValider;
	private JButton btnQuitter;
	private DateTimePicker picker;
	protected String[] data;
	
	private List<Devoirs> listDevoirs;
	protected List<Note> notes = new ArrayList<Note>();
	protected List<Eleve> eleves;
	private JComboBox coefField;
	
	public UISaisieNotes() {
		operationIcon.setIcon(new ImageIcon(UISaisieNotes.class.getResource("/images/saisiNote.png")));
		operationLabel.setText("Planning des devoirs");
		
		JPanel mainPanel = new JPanel();
		centerPanel.add(mainPanel, BorderLayout.CENTER);
		mainPanel.setLayout(new GridLayout(1, 2, 0, 0));
		elevesNotesModel = new EleveNoteModel();
		
		JPanel panel = new JPanel();
		mainPanel.add(panel);
		panel.setLayout(new GridLayout(1, 2, 0, 0));
		
		JPanel panel_7 = new JPanel();
		panel.add(panel_7);
		panel_7.setLayout(new GridLayout(2, 1, 0, 0));
		
		JPanel panel_2 = new JPanel();
		panel_7.add(panel_2);
		panel_2.setBorder(new TitledBorder(null, "Ajouter Devoirs", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JPanel panel_6 = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panel_6.getLayout();
		flowLayout_1.setHgap(30);
		flowLayout_1.setAlignment(FlowLayout.LEFT);
		panel_2.add(panel_6);
		
		JLabel lblNewLabel = new JLabel("Devoirs:");
		panel_6.add(lblNewLabel);
		
		
		devoirs = new JComboBox<>();
		panel_2.add(devoirs);
        
        JPanel panel_8 = new JPanel();
        FlowLayout flowLayout = (FlowLayout) panel_8.getLayout();
        flowLayout.setHgap(30);
        flowLayout.setAlignment(FlowLayout.LEFT);
        panel_2.add(panel_8);
        
        JLabel lblNewLabel_1 = new JLabel("Coef:");
        panel_8.add(lblNewLabel_1);
        
        JPanel panel_5 = new JPanel();
        FlowLayout flowLayout_2 = (FlowLayout) panel_5.getLayout();
        flowLayout_2.setAlignment(FlowLayout.RIGHT);
        panel_2.add(panel_5);
        
        coefField = new JComboBox<>();
        panel_5.add(coefField);
        
		        
		        
        JPanel panel_9 = new JPanel();
        FlowLayout flowLayout_3 = (FlowLayout) panel_9.getLayout();
        flowLayout_3.setHgap(30);
        flowLayout_3.setAlignment(FlowLayout.LEFT);
        panel_2.add(panel_9);
        
        JLabel lblDateEtHeure = new JLabel("Date et Heure:");
        panel_9.add(lblDateEtHeure);
        picker = new DateTimePicker();
        Date date = new Date();
        panel_2.add(picker);
        picker.getEditor().setHorizontalAlignment(SwingConstants.RIGHT);
        picker.setFormats( DateFormat.getDateTimeInstance( DateFormat.SHORT, DateFormat.MEDIUM ) );
        picker.setTimeFormat( DateFormat.getTimeInstance( DateFormat.MEDIUM ) );   
        picker.setDate(date);
        picker.getEditor().setColumns(18);
		                
        panel_2.setLayout(new GridLayout(3, 2, 0, 0));
		
		JPanel panel_1 = new JPanel();
		panel_7.add(panel_1);
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
		notification.setColumns(10);
		notification.setFont(new Font("Tahoma", Font.PLAIN, 11));
		panel_4.add(scrollPane, BorderLayout.CENTER);
		scrollPane.setViewportView(notification);
		
				
		JPanel panel_3 = new JPanel();
		panel_15.add(panel_3);
		
		btnValider = new JButton("Valider");
		
		btnValider.setPreferredSize(new Dimension(65, 20));
		btnValider.setMinimumSize(new Dimension(65, 20));
		btnValider.setMaximumSize(new Dimension(65, 20));
		btnValider.setEnabled(true);
		
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
		panel_3.add(btnQuitter);
		
		JPanel listeDevoirsPanel = new JPanel();
		panel.add(listeDevoirsPanel);
		listeDevoirsPanel.setBackground(new Color(102, 205, 170));
		
		JScrollPane listeScrollPane = new JScrollPane();
		listeScrollPane.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Liste des \u00E9l\u00E8ve", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
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
		
		listeEleves = new JTable();
		listeEleves.setBackground(new Color(0, 255, 255));
		listeEleves.setModel(elevesNotesModel);
		listeScrollPane.setViewportView(listeEleves);
		listeDevoirsPanel.setLayout(listeDevoirsPanelLayout);
						
		
//		listeEleves.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
//			@Override
//			public void valueChanged(ListSelectionEvent e) {
//				int selectedRow = listeDevoirs.getSelectedRow();
//				if ( selectedRow >= 0 ) {
//					data = getDevoirsDataByRow(selectedRow);
//					updateUIForm (data);
//						
//				}
//			}
//		});
		
		/*
		 * Contrôle de la saisie du champ Libelle et Coéfficient avant activation du bouton de validation 
		 * 
		 * */	
		ArrayList<JTextField> idTextFields = new ArrayList<>();
		
		try {
			loadData();
		} catch (WSException | JAXBException | NullPointerException |IOException e1) {
			// TODO Auto-generated catch block
			displayErrorMessage(e1.getMessage());
		}

		devoirs.addActionListener(new ActionListener () {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
				String [] splitInfos = devoirs.getSelectedItem().toString().split("::");
				String idClasse=splitInfos[2];
				String idDevoir=splitInfos[0];
				coefField.setSelectedIndex(devoirs.getSelectedIndex());
				
				
				notes.clear();
				try {
					notes=Note.list(idDevoir);
				} catch (WSException | IOException | JAXBException e) {
					// TODO Auto-generated catch block
				}
				
				
				
				try {
					
					eleves=Eleve.list(idClasse);
					
					System.out.println("bloc");
					
					if(notes.size()==0)
					{
						
						for(Eleve eleve : eleves)
						{
							Note note = new Note(Integer.parseInt(idDevoir),0,Integer.parseInt(coefField.getSelectedItem().toString()),eleve.getId());
							notes.add(note);
						}
					}
					
					elevesNotesModel.loadData(eleves,notes);
					
				} catch (JAXBException | IOException | WSException  exception) {
					// TODO Auto-generated catch block
					displayErrorMessage(exception.getMessage());
				}
				
			}
			
		});
	
	}
	
	@Override
	public void updateUIForm(String[] data) {
		try {
				
		} catch ( NullPointerException ignored) {}
	}
	
	
	public void loadData() throws WSException, JAXBException, IOException , NullPointerException{
		
	
			listDevoirs=Devoirs.listForEns(MainUIApplication.getCurrentUser().getId());
			if(devoirs.getItemCount()==0)
			
			{
			for ( Devoirs devoir : listDevoirs ) {
				devoirs.addItem( devoir.getId() + Utilitaire.SEPARATEUR + devoir.getModule()+Utilitaire.SEPARATEUR+devoir.getIdClasse()+Utilitaire.SEPARATEUR+devoir.getClasse());
				coefField.addItem(String.valueOf(devoir.getCoef()));
			}
			}

	
	}
	
	
	private void setDateHeure(String time) {
		Date now =new Date();
		picker.setDate(now);

	}
			
	
	public List<Note> getNotes(){
		
		String [] values = new String [elevesNotesModel.getRowCount()];
		if(notes.size()>0)
		{
			for ( int i = 0 ; i < elevesNotesModel.getRowCount() ; i ++ ) {
				values [i] = (String) elevesNotesModel.getValueAt(i, 3);
				double valeur = Double.parseDouble(values[i]);
				notes.get(i).setNote(valeur);
			}			
		}
		return notes;
	}
	
	public void addValiderListener(ActionListener actionListener) {
		btnValider.addActionListener(actionListener);
			
	}

	public void addSupprimerListener(ActionListener actionListener) {
		//btnSupprimer.addActionListener(actionListener);
	}
	

	public void resetFormUI() {
		devoirs.setSelectedIndex(0);
	}


	

}
