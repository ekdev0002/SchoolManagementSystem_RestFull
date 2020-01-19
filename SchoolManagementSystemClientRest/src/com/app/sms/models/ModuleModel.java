package com.app.sms.models;

import java.util.List;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

public class ModuleModel extends AbstractTableModel {

	private String[] nomColumns = {"Id", "Coefficient", "Libelle", "Description"};
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
	
	public void loadData (List<Module> modules) {
		rows.clear();
		for (Module module : modules ){
			rows.add( new String [] {
					String.valueOf(module.getId()), 
					String.valueOf(module.getCoefficient()),
					module.getLibelle(), 
					module.getDescription(),
					}) ;
		}
		fireTableDataChanged();
	}

	public void clear() {
		rows.clear();
		fireTableDataChanged();
	}
}