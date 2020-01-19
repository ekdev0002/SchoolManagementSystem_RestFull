package com.app.sms.models;

import java.util.List;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

public class EleveModel extends AbstractTableModel {

	private String[] nomColumns = {"Id", "Nom", "Prenom", "Genre", "Telephone", "Email", "LibelleClasse", "Login", "PicturePath", "idClasse"};
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
	
	public void loadData (List<Eleve> eleves) {
		rows.clear();
		for (Eleve eleve : eleves ){
			rows.add( new String [] {
					String.valueOf(eleve.getId()), 
					eleve.getNom(), 
					eleve.getPrenom(),
					eleve.getGenre(),
					eleve.getTelephone(),
					eleve.getEmail(),
					eleve.getLibelleClasse(),
					eleve.getLogin(),
					eleve.getPath(),
					String.valueOf(eleve.getIdClasse())
					}) ;
		}		
		fireTableDataChanged();
	}

	public void clear() {
		rows.clear();
		fireTableDataChanged();
	}
}