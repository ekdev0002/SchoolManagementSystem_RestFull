package com.app.sms.models;

import java.util.List;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

public class BulletinModel extends AbstractTableModel {

	private String[] nomColumns = {"N :", "Matricule", "Nom", "Prenom", "Date de Generation", "Moyenne Generale","Semestre"};
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
	
	public void loadData (List<Bulletin> bulletins) {
		rows.clear();
		for (Bulletin bulletin : bulletins ){
			rows.add( new String [] {
					String.valueOf(bulletin.getId()), 
					String.valueOf(bulletin.getIdEleve()), 
					bulletin.getNom(),
					bulletin.getPrenom(),
					bulletin.getDate(),
					String.valueOf(bulletin.getMoyenneGenerale()),
					bulletin.getSemestre()
					}) ;
		}
		fireTableDataChanged();
	}

	public void clear() {
		rows.clear();
		fireTableDataChanged();
	}
}