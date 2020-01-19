package com.app.sms.models;

import java.util.List;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

public class EnseignantModel extends AbstractTableModel {
	private String[] nomColumns = {"Id", "Nom", "Prenom", "Genre", "Telephone", "Email", "Login", "PicturePath"};
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
	
	public void loadData (List<Enseignant> enseignants) {
		rows.clear();
		for (Enseignant enseignant : enseignants ){
			rows.add( new String [] {
					String.valueOf(enseignant.getId()), 
					enseignant.getNom(), 
					enseignant.getPrenom(),
					enseignant.getGenre(),
					enseignant.getTelephone(),
					enseignant.getEmail(),
					enseignant.getLogin(),
					enseignant.getPath()
					}) ;
		}		
		fireTableDataChanged();
	}

	public void clear() {
		rows.clear();
		fireTableDataChanged();
	}
}