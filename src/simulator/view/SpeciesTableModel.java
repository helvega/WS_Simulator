package simulator.view;

import java.util.Arrays;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.AnimalInfo;
import simulator.model.EcoSysObserver;
import simulator.model.MapInfo;
import simulator.model.RegionInfo;

class SpeciesTableModel extends AbstractTableModel implements EcoSysObserver {

	  // TODO Declare necessary fields.
	  Controller ctrl_;
	  String[] columns = {"Species", "NORMAL", "MATE", "HUNGER", "DANGER", "DEAD"};
	  Object[][] animal_data;
	  static final int numRows = 2;
	  
	  SpeciesTableModel(Controller ctrl) {
	    // TODO Initialize corresponding data structures.
		  this.ctrl_ = ctrl;

		  animal_data = new Object[numRows][columns.length]; 
		  
		  animal_data[0][0] = "Wolfs";
		  animal_data[1][0] = "Sheeps";
		  
		  for(int i = 0; i < animal_data.length; i++) 
			  for(int j = 1; j < columns.length; j++) 
				  animal_data[i][j] = 0;
		  
	    // TODO Register the 'this' object as an observer.
		  ctrl.addObserver(this);
	  }
	  
	  // TODO The rest of methods.
	  
	  @Override
	  public String getColumnName(int columnIndex) {
		  return columns[columnIndex];
	  }

	  @Override
	  public int getRowCount() {
		// TODO Auto-generated method stub
		  
		return numRows;
	  }

	  @Override
	  public int getColumnCount() {
		// TODO Auto-generated method stub
		  
		return columns.length;
	  }

	  @Override
	  public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		
		return animal_data[rowIndex][columnIndex];
	  }
	  
	  @Override
	  public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		  this.animal_data[rowIndex][columnIndex] = aValue;
	  };

	  @Override
	  public void onRegister(double time, MapInfo map, List<AnimalInfo> animals) {
		// TODO Auto-generated method stub
		  int row = 0, col = 0;
		  
		  for(int i = 0; i < animal_data.length; i++) 
			  for(int j = 1; j < columns.length; j++) 
				  this.animal_data[i][j] = 0;
		  
		  for (int k = 0; k < animals.size(); k++) {
			  
			  switch(animals.get(k).getGeneticCode()) {
			  case "wolf":
				  row = 0;
				  break;
				  
			  case "sheep":
				  row = 1;
				  break;
				  
			  }
			  
			  switch(animals.get(k).getState()) {
			  case NORMAL:
				  col = 1;
				  break;
				  
			  case MATE:
				  col = 2;
				  break;
				  
			  case HUNGER:
				  col = 3;
				  break;
				  
			  case DANGER:
				  col = 4;
				  break;
				  
			  case DEAD:
				  col = 5;
				  break;
			  }
			  
			  int aux = (int)animal_data[row][col] + 1 ;
			  this.animal_data[row][col] = aux;
			  fireTableCellUpdated(row, col);
		  }
	  }

	  @Override
	  public void onReset(double time, MapInfo map, List<AnimalInfo> animals) {
		// TODO Auto-generated method stub
		  for(int i = 0; i < animal_data.length; i++) 
			  for(int j = 1; j < columns.length; j++) 
				  animal_data[i][j] = 0;
	  }

	  @Override
	  public void onAnimalAdded(double time, MapInfo map, List<AnimalInfo> animals, AnimalInfo a) {
		// TODO Auto-generated method stub
		  int row = 0, col = 0; 
		  switch(a.getGeneticCode()) {
		  case "wolf":
			  row = 0;
			  break;
			  
		  case "sheep":
			  row = 1;
			  break;
			  
		  }
		  
		  switch(a.getState()) {
		  case NORMAL:
			  col = 1;
			  break;
			  
		  case MATE:
			  col = 2;
			  break;
			  
		  case HUNGER:
			  col = 3;
			  break;
			  
		  case DANGER:
			  col = 4;
			  break;
			  
		  case DEAD:
			  col = 5;
			  break;
		  }
		  
		  int aux = (int)animal_data[row][col] + 1 ;
		  animal_data[row][col] = aux;
		  fireTableCellUpdated(row, col);
	  }

	  @Override
	  public void onRegionSet(int row, int col, MapInfo map, RegionInfo r) {
		// TODO Auto-generated method stub
		
	  }

	  @Override
	  public void onAdvance(double time, MapInfo map, List<AnimalInfo> animals, double dt) {
		// TODO Auto-generated method stub
		  
		  int row = 0, col = 0;
		  
		  for(int i = 0; i < animal_data.length; i++) 
			  for(int j = 1; j < columns.length; j++) 
				  this.animal_data[i][j] = 0;
		  
		  for (int k = 0; k < animals.size(); k++) {
			  
			  switch(animals.get(k).getGeneticCode()) {
			  case "wolf":
				  row = 0;
				  break;
				  
			  case "sheep":
				  row = 1;
				  break;
				  
			  }
			  
			  switch(animals.get(k).getState()) {
			  case NORMAL:
				  col = 1;
				  break;
				  
			  case MATE:
				  col = 2;
				  break;
				  
			  case HUNGER:
				  col = 3;
				  break;
				  
			  case DANGER:
				  col = 4;
				  break;
				  
			  case DEAD:
				  col = 5;
				  break;
			  }
			  
			  int aux = (int)animal_data[row][col] + 1 ;
			  animal_data[row][col] = aux;
			  fireTableCellUpdated(row, col);
		  }
	  }
	}