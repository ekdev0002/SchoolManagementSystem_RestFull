package com.app.sms.models;

import java.io.IOException;
import java.util.List;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;
import javax.xml.bind.JAXBException;

import com.app.sms.exceptions.WSException;

public class CoursModel extends AbstractTableModel {

	private String[] nomColumns = {"Id", "Module", "Classe","Date-Heure","duree","Description"};
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
	
	public void loadData (List<Cours> cours) throws JAXBException, IOException, WSException {
		rows.clear();
		for (Cours cour : cours ){
			rows.add( new String [] {
					String.valueOf(cour.getId()), 
					cour.getModule(),
					cour.getClasse(),
					cour.getDateHeure(), 
					String.valueOf(cour.getDuree()),
					cour.getDescription()
					}) ;
		}
		fireTableDataChanged();
	}

	public void clear() {
		rows.clear();
		fireTableDataChanged();
	}
}