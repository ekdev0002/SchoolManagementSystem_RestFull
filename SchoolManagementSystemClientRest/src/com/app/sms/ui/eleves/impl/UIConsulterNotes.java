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
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.xml.bind.JAXBException;

import com.app.sms.exceptions.WSException;
import com.app.sms.models.Devoirs;
import com.app.sms.models.Eleve;
import com.app.sms.models.EleveNoteModel;
import com.app.sms.models.Note;
import com.app.sms.ui.impl.AbstractUIOperation;
import com.app.sms.ui.impl.MainUIApplication;
import com.app.sms.utils.Utilitaire;

public class UIConsulterNotes extends AbstractUIOperation  {
	private JTable listeEleves;
	private EleveNoteModel eleveNotesModel;

	
	private JComboBox devoirs;
	
	private JButton btnValider;
	private JButton btnQuitter;
	protected String[] data;
	
	private List<Devoirs> listDevoirs;
	protected Note note = null;
	protected Eleve eleve;
	private JComboBox coefField;
	
	public UIConsulterNotes() {
		operationIcon.setIcon(new ImageIcon(UIConsulterNotes.class.getResource("/images/saisiNote.png")));
		operationLabel.setText("Planning des devoirs");
		
		JPanel mainPanel = new JPanel();
		centerPanel.add(mainPanel, BorderLayout.CENTER);
		mainPanel.setLayout(new GridLayout(1, 2, 0, 0));
		eleveNotesModel = new EleveNoteModel();
		
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
        Date date = new Date();
		                
        panel_2.setLayout(new GridLayout(3, 2, 0, 0));
		
		JPanel panel_1 = new JPanel();
		panel_7.add(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_15 = new JPanel();
		panel_15.setBorder(null);
		panel_1.add(panel_15);
		panel_15.setLayout(new GridLayout(2, 1, 0, 0));
		notification = new JTextArea();
		notification.setColumns(10);
		notification.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
				
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
		listeDevoirsPanel.setBackground(Color.WHITE);
		
		JScrollPane listeScrollPane = new JScrollPane();
		listeScrollPane.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Votre note", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
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
		listeEleves.setBackground(new Color(153, 255, 255));
		listeEleves.setModel(eleveNotesModel);
		listeScrollPane.setViewportView(listeEleves);
		listeDevoirsPanel.setLayout(listeDevoirsPanelLayout);
						
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
				try {
					note=null;
					
					eleve=Eleve.findById(MainUIApplication.getCurrentUser().getId());
					
					note=Note.findByEleve(Integer.parseInt(idDevoir),MainUIApplication.getCurrentUser().getId());
					System.out.println("des"+note.toString());
					if(note!=null)
					{
						eleveNotesModel.loadData(eleve,note);
					}
					
				} catch (JAXBException | IOException | WSException  exception) {

					eleveNotesModel.clean();
				}
			}
			
		});
	
	}
	
		
	
	public void loadData() throws WSException, JAXBException, IOException , NullPointerException{
		
	
			listDevoirs=Devoirs.listForEleve(MainUIApplication.getCurrentUser().getId());
			if(devoirs.getItemCount()==0)			
			{
				for ( Devoirs devoir : listDevoirs ) {
					devoirs.addItem( devoir.getId() + Utilitaire.SEPARATEUR + devoir.getModule()+Utilitaire.SEPARATEUR+devoir.getIdClasse()+Utilitaire.SEPARATEUR+devoir.getClasse());
					coefField.addItem(String.valueOf(devoir.getCoef()));
			}
		}	
	}

}
