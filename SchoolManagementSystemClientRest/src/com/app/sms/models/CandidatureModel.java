package com.app.sms.models;

import java.io.IOException;
import java.util.List;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;
import javax.xml.bind.JAXBException;

import com.app.sms.exceptions.WSException;

public class CandidatureModel extends AbstractTableModel {

	private String[] nomColumns = {"Id", "Nom", "Prenom", "Genre", "Telephone", "Email", "State", "Commentaires", "Path","Enseignant"};
	private Vector<String[]> rows = new Vector<>();
	
	@Override
	public int getColumnCount() {
		return nomColumns.length;
	}

	@Override
	public int getRowCount() {
		return rows.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return rows.get(rowIndex)[columnIndex];
	}
	
	@Override
	public String getColumnName(int column) {
		return nomColumns[column];
	}
	
	public void loadData (List<Candidature> candidatures) throws WSException, IOException, JAXBException {
		rows.clear();
		for (Candidature candidature : candidatures ){
			rows.add( new String [] {
					String.valueOf(candidature.getId()), 
					candidature.getNom(), 
					candidature.getPrenom(),
					candidature.getGenre(),
					candidature.getTelephone(),
					candidature.getEmail(),
					candidature.getState(),
					candidature.getCommentaires(),
					candidature.getPath(),
					candidature.getEnseignant()
					}) ;
		}
		fireTableDataChanged();
	}

	public void clear() {
		rows.clear();
		fireTableDataChanged();
	}
}