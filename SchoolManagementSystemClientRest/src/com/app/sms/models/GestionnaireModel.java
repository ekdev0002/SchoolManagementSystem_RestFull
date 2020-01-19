package com.app.sms.models;

import java.util.List;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

@SuppressWarnings("serial")
public class GestionnaireModel extends AbstractTableModel {

	private String[] nomColumns = {"Id", "Nom", "Prenom", "DateDeNaissance", "Telephone", "Login", "Password"};
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
	
	public void loadData (List<Gestionnaire> gestionnaires) {
		rows.clear();
		for ( Gestionnaire gestionnaire : gestionnaires ) {
			rows.add( new String [] {
				String.valueOf(gestionnaire.getId()),
				gestionnaire.getNom(),
				gestionnaire.getPrenom(),
				gestionnaire.getDate_naissance(),
				gestionnaire.getTelephone(),
				gestionnaire.getLogin(),
				gestionnaire.getPassword()
			}) ;
			fireTableDataChanged();
		}
		
	}
	public void clear() {
		rows.clear();
		fireTableDataChanged();
	}
}
