package com.app.sms.models;

import java.util.List;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

public class EleveNoteModel extends AbstractTableModel {

	private String[] nomColumns = {"Id", "Nom","Prenom", "Note/20"};
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
	
	public void loadData (List<Eleve> eleves,List<Note> notes) {
		rows.clear();
		
		if(notes.size()>0)
		{
			System.out.println("bloc1"+notes.toString()+" "+eleves.size());
			for (int i=0;i<eleves.size();i++){
				for (int j=0;j<notes.size();j++ ){
					if(notes.get(j).getIdEleve()==eleves.get(i).getId())
					{
						rows.add( new String [] {
								String.valueOf(eleves.get(i).getId()), 
								eleves.get(i).getNom(),
								eleves.get(i).getPrenom(),
								String.valueOf(notes.get(j).getNote())
						});
					}
				}
			}	
		}
		else
		{
			
			for (int i=0;i<eleves.size();i++)
			{
				rows.add( new String [] {
						String.valueOf(eleves.get(i).getId()), 
						eleves.get(i).getNom(),
						eleves.get(i).getPrenom(),
						"0"});
			}
		}	
		fireTableDataChanged();
	}

	public void clear() {
		rows.clear();
		fireTableDataChanged();
	}
	
	   @Override
	    public boolean isCellEditable(int rowIndex, int columnIndex)
	    {	
	        return true;
	    }
	    
	    @Override
	    public void setValueAt(Object aValue, int rowIndex, int columnIndex)
	    {   	
	        
	        if(3 == columnIndex) {
	            rows.get(rowIndex)[columnIndex]=((String) aValue);
	        }

	    }

		public void loadData(Eleve eleve, Note note) {
			rows.clear();
			System.out.println(eleve.toString()+note.toString());
			rows.add( new String [] {
					String.valueOf(eleve.getId()), 
					eleve.getNom(),
					eleve.getPrenom(),
					String.valueOf(note.getNote())
			});
			fireTableDataChanged();
		}

		public void clean() {
			rows.clear();
			fireTableDataChanged();

			
		}
}