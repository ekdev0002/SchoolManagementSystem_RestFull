package com.app.sms.models;

import java.io.IOException;
import java.util.List;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;
import javax.xml.bind.JAXBException;

import com.app.sms.exceptions.WSException;

public class DevoirsModel extends AbstractTableModel {

	private String[] nomColumns = {"Id", "Module","Classe", "Statut","Date-Heure","duree","Description","Coef"};
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
	
	public void loadData (List<Devoirs> devoirs) throws JAXBException, IOException, WSException{
		rows.clear();
		for (Devoirs devoir : devoirs ){
			rows.add( new String [] {
					String.valueOf(devoir.getId()), 
					devoir.getModule(),
					devoir.getClasse(),
					devoir.getStatut(),
					devoir.getDateHeure(), 
					String.valueOf(devoir.getDuree()),
					devoir.getDescription(),
					String.valueOf(devoir.getCoef())
					}) ;
		}
		fireTableDataChanged();
	}

	public void clear() {
		rows.clear();
		fireTableDataChanged();
	}
}